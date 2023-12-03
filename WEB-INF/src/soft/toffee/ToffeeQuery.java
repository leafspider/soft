package soft.toffee;

import leafspider.util.Log;
import leafspider.util.Util;
import soft.formula.FormulaQuery;
import soft.report.Folders;
import twitter4j.Query;
import twitter4j.Twitter;
import twitter4j.Status;

import java.io.File;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToffeeQuery {

    public Twitter twitter;

    public TreeSet<Signal> signals = new TreeSet();
    public TreeSet<String> userNames = new TreeSet();
    public TreeSet<String> tickers = new TreeSet();
    public HashMap<String, Tweeter> tweeters = new HashMap();

    public static void main(String[] args) {

        try {

            Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.search );

            ToffeeQuery toffeeQuery = new ToffeeQuery( twitter );
            //String query = "(\"Collagen\" AND \"Chaga\") OR (\"Collagen\" AND \"Kiwi\")";
            String query = "MDGL buyout";
            toffeeQuery.doQuery( query );
            Log.infoln(toffeeQuery);
        }
        catch( Exception e ) { e.printStackTrace(); }
    }
    public ToffeeQuery( Twitter twitter ) {
        this.twitter = twitter;
    }

    public void doQuery( String query ) {

        //if (query.indexOf("$") != 0 && query.indexOf("#") != 0) { query = "$" + query; }	// jmh 2022-11-02
        boolean isTickerQuery = false;
        if (query.indexOf("$") == 0 ) {
            isTickerQuery = true;
        }
        Log.infoln("query=" + query );

        //String userNamesString = " (";
        //while (userNamesList.hasNext()) {
        //	String name = (String) userNamesList.next();
        //	if ( userNamesList.hasNext() ) { userNamesString += "from:" + name + " OR "; }
        //	else { userNamesString += "from:" + name; }
        //}
        //userNamesString += ")";
        //query = query + userNamesString;

        Query tquery = new Query();
        tquery.resultType(Query.RECENT);
        tquery.setLang("en");		//tquery.setQuery(query + " lang:en -filter:replies" );
        tquery.setQuery(query);

        TwitterResults results = new TwitterResults(twitter, tquery);
        try {
            results.load();
            List<Status> statuses = results.getStatuses();
            Collections.reverse(statuses);

            //Log.infoln( "" + statuses.size() + " results for: " + query );

            for (Status status : statuses) {

                String userName = status.getUser().getScreenName().toLowerCase();
                Tweeter tweeter = new Tweeter(twitter, userName);

                String url = "http://twitter.com/" + userName + "/status/" + status.getId();	// String url = "https://api.twitter.com/" + userName + "/status/" + status.getId();

                String text = status.getText();

                if ( isTickerQuery ) {

                    if (text.toUpperCase().indexOf(query) < 0) { continue; }

                    Tweet tweet = parseTweet(status.getText(), userName, url);

                    /*
                    tweet.retweetCount = status.getRetweetCount();
                    if (results.topRetweet == null || tweet.retweetCount > results.topRetweet.retweetCount) {
                        results.topRetweet = tweet;
                    }
                    */

                    tweet.retweetCount = status.getRetweetCount();
                    if (tweeter.topRetweet == null || tweet.retweetCount > tweeter.topRetweet.retweetCount) {
                        tweeter.topRetweet = tweet;
                    }

                    for (Signal signal : tweet.signals) {
                        signal.created = status.getCreatedAt();
                        signals.add(signal);
                        tickers.add(signal.tickerName);
                        tweeter.signals.add(signal);
                    }
                }
                else {
                    Signal signal = querySignal( text, query, userName, url );
                    signal.created = status.getCreatedAt();
                    signals.add( signal );
                    tickers.add( signal.tickerName );
                    tweeter.signals.add(signal);
                }

                //if ( tweeter.signals.size() > 0 ) {
                    userNames.add(userName);
                    tweeters.put(userName, tweeter);
                //}

                if (tweeter.topRetweet == null) {
                    Tweet tweet = new Tweet();
                    tweet.retweetCount = 0;
                    tweeter.topRetweet = tweet;
                }
            }
        }
        catch (Exception e) {
            Log.infoln("Twitter error for query '" + query + "':");
            Log.infoln(e.getMessage());
        }
    }

    public Signal querySignal(String text, String query, String userName, String url ) throws Exception {

        query = query.replaceAll( "\"", "" );
        query = query.replaceAll( "AND", "." );

        Signal signal = new Signal();
        signal.userName = userName;
        signal.tickerName = query;
        signal.url = url;
        signal.text = text;

        return signal;
    }

    public Tweet parseTweet( String text, String userName, String url ) throws Exception {

        Tweet tweet = new Tweet();
        tweet.userName = userName.toLowerCase();
        tweet.text = text;
        tweet.signals = new ArrayList();
        tweet.url = url;

        Pattern pattern = Pattern.compile("[$][a-zA-Z]+\\w");
        Matcher matcher = pattern.matcher(" " + text + " ");
        while (matcher.find()) {

            String tickerName = matcher.group();
            tickerName = tickerName.substring(1, tickerName.length()).trim();
            Signal signal = new Signal();
            signal.userName = tweet.userName;
            signal.tickerName = tickerName.toUpperCase();
            signal.url = url;
            signal.text = text;

            text = text.toLowerCase();
            if( isBuy(text) ) {

                if( text.indexOf( "call" ) > -1 ) { signal.longCall += 1; }
                if( text.indexOf( "put" ) > -1 ) { signal.longPut += 1; }
                if( signal.longCall + signal.longPut == 0 ) { signal.longEquity += 1; }
            }
            else if( isSell(text) ) {

                if( text.indexOf( "call" ) > -1 ) { signal.shortCall += 1; }
                if( text.indexOf( "put" ) > -1 ) { signal.shortPut += 1; }
                if( signal.shortCall + signal.shortPut == 0 ) { signal.shortEquity += 1; }
            }

            tweet.signals.add(signal);
        }
        return tweet;
    }

    private boolean isBuy(String text)
    {
        if( text.indexOf("buy") > -1 ) { return true; }
        if( text.indexOf("long") > -1 ) { return true; }
        return false;
    }

    private boolean isSell(String text)
    {
        if( text.indexOf("sell") > -1 ) { return true; }
        if( text.indexOf("short") > -1 ) { return true; }
        return false;
    }
}
