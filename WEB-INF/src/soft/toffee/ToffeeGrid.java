package soft.toffee;

import leafspider.rest.RepresentationException;
import leafspider.util.Duration;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import soft.report.Folders;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToffeeGrid extends ToffeeRepresentation
{
    private boolean debug = false;

    public static String representation = "grid";
	public String getRepresentation() { return representation; }

	private String query = null;
    private static DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd (HH:mm)" );

    public static void main(String[] args) throws IOException, TwitterException  {

    	try {

    		ToffeeGrid grid = new ToffeeGrid();

    		/* regex
			String text = "Give me $DC anytime";
			//List tickerFilterList = store.tickers( "Gold microcaps" );
			List tickerFilterList = store.allAdvancedTickers();
			System.out.println( "size=" + tickerFilterList.size() );
			long start = Util.getNow();
			System.out.println( grid.isContainsTicker(text, tickerFilterList) );
			long dif = Util.getNow() - start;
			System.out.println( "" + dif );
    		*/

    		/* matcher
    		//String text = "A story in 2 Parts $CLOV\uD83D\uDE05";
			String text = "$cLov said cLov while $cLOV and bug,$cLOv. But not gCLov or CLOve or aCLoVa however $cLOv.to  And $CLOV";
			String ticker = "CLOV";
			Pattern pattern = Pattern.compile("[$][a-zA-Z]+\\w");
			//Pattern pattern = Pattern.compile("(^|[$\\W])" + ticker + "(\\W|$)", Pattern.CASE_INSENSITIVE);
			Matcher matcher = pattern.matcher("" + text + "");
			while (matcher.find()) {
				String tickerName = matcher.group();
				//tickerName = tickerName.substring(1, tickerName.length()).trim();
				System.out.println(tickerName);
			}
			//*/

			//* grid
    		// -Djavax.net.ssl.trustStore="C:\Server\tomcat6\webapps\soft\jssecacerts"
			// -Djavax.net.ssl.trustStorePassword=changeit
    		//grid.setQuery( "$TWLO" );
			//grid.setQuery( "#blueTick" );
			//grid.setQuery( "#blueTick" );
			//grid.setQuery("chaga AND kiwi");
			//grid.setQuery("$MDGL");
			grid.setResourceId("Pensions");
			//grid.setResourceId("Gold talk");
			//grid.debug = true;
    		System.out.println( grid.getXml() );
    		//*/

			/* friends
			Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.search );
			System.out.println( "Accessed" );
			Tweeter tweeter = new Tweeter(twitter, "LadeBackk");
			ArrayList friends = tweeter.getFriends();
			System.out.print(friends.size());
			*/
		}
		catch( Exception e ) { e.printStackTrace(); }
    }
    
    public ToffeeGrid() {

		System.setProperty("javax.net.ssl.trustStore", ServerContext.getConfigFolder() + "\\jssecacerts.jks" );
		System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
    }
    
    public String getXml() throws RepresentationException {

    	try {

    		String toffee = getResourceId();
    		String xml = "";

			File toffeeXmlFile = new File(Folders.toffeeFolder() + "\\" + toffee.toUpperCase() + ".xml");
			toffeeXmlFile.getParentFile().mkdirs();

			ToffeeStore store = new ToffeeStore();
			boolean isStoredToffee = store.isStoredToffee( toffee );
			boolean isAdvancedToffee = store.isAdvancedToffee( toffee );

			int frequencyHours = 24;
			if ( isAdvancedToffee ) { frequencyHours = 1; }

			if ( isStoredToffee && toffeeXmlFile.exists() && Duration.hours( toffeeXmlFile.lastModified(), Util.getNow() ) < frequencyHours ) {
				return Util.fileToString( toffeeXmlFile, "\n" );
			}

			TreeSet<Signal> signals = new TreeSet();
			TreeSet<String> userNames = new TreeSet();
			TreeSet<String> tickers = new TreeSet();
			HashMap<String, Tweeter> tweeters = new HashMap();

			if( debug ) {	// DEBUG
				Iterator statusit = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Jake Tiley\\Toffee\\tweets2.txt").iterator();
				while(statusit.hasNext()) {
					String text = (String) statusit.next();
					int pos = text.indexOf(":");
					String userName = text.substring(0,pos);
					userNames.add( userName );
					text = text.substring(pos+2,text.length());
					if( isContainsTicker(text) ) {

						ToffeeQuery toffeeQuery = new ToffeeQuery( null );
						Tweet tweet = toffeeQuery.parseTweet(text, userName, "http://twitter.com/leafspider" );
						for (Signal signal : tweet.signals) {
							signal.created = new Date();
							signals.add( signal );
							tickers.add( signal.tickerName );
						}
					}
				}
			}
			else {		// LIVE

				//Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.search );
				Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.agent );		// jmh 2023-06-21

				ToffeeQuery toffeeQuery = new ToffeeQuery( twitter );

				if ( isStoredToffee ) {	// it's a user timeline query

					List userNamesFromFile = store.tweeters( toffee );

					List tickerFilterList = null;
					if ( isAdvancedToffee ) {		// it's an advanced user query

						tickerFilterList = store.tickers( toffee );
						if ( tickerFilterList.size() == 0 ) {
							tickerFilterList = store.allAdvancedTickers();		// eg Gold talk
						}
						Log.infoln("tickerFilterList=" + tickerFilterList.size());

						List friendsAndFollowers = store.loadNetwork( userNamesFromFile );
						userNamesFromFile.addAll( friendsAndFollowers );
					}
					Set<String> userNamesSet = new HashSet<String>( userNamesFromFile );		// unique

					int totalStatuses = 0;

					ArrayList<String> skips = new ArrayList();

					for ( String userName : userNamesSet ) {

						userName = userName.toLowerCase();

						if ( store.exception( userName ).exists() ) {
							String message = store.readException( userName );
							String statusCode = message.substring( 0, message.indexOf( ":") );
							if ( store.saveException( statusCode ) ) {
								skips.add( userName + "[" + statusCode + "]" );
								continue;
							}
							else {
								store.deleteException( userName );
							}
						}

						Tweeter tweeter = new Tweeter(twitter, userName);
						try {

							List<Status> statuses;
							if ( isAdvancedToffee ) { statuses = store.loadStatuses(userName); }
							else { statuses = tweeter.getStatuses(); }
							Collections.reverse(statuses);

							totalStatuses += statuses.size();

							for (Status status : statuses) {

								String url = "http://twitter.com/" + userName + "/status/" + status.getId();

								//Log.infoln( status.getText() );

								Tweet tweet = toffeeQuery.parseTweet(status.getText(), userName, url);

								tweet.retweetCount = status.getRetweetCount();
								if (tweeter.topRetweet == null || tweet.retweetCount > tweeter.topRetweet.retweetCount) {
									tweeter.topRetweet = tweet;
								}

								for (Signal signal : tweet.signals) {
									if ( !isAdvancedToffee || tickerFilterList.contains( signal.tickerName ) ){
										signal.created = status.getCreatedAt();
										signals.add(signal);
										tickers.add(signal.tickerName);
										tweeter.signals.add(signal);
									}
								}
							}

							if ( tweeter.signals.size() > 0 ) {
								userNames.add(userName);
								tweeters.put(userName, tweeter);
							}

							if (tweeter.topRetweet == null) {
								Tweet tweet = new Tweet();
								tweet.retweetCount = 0;				// tweet.text = "trout"; tweet.url = "http://trout";
								tweeter.topRetweet = tweet;
							}
						}
						catch (TwitterException te) {
							Log.infoln("Twitter exception " + te.getStatusCode() + " for " + userName);
							if ( store.saveException( te.getStatusCode() ) ) {
								store.writeException(userName, te.getMessage());
							}
						}
					}

					Log.infoln( "[" + toffee + "] statuses=" + totalStatuses + ", users=" + userNamesSet.size() + ", skips=" + skips.size() + ", signals=" + signals.size()+ ", tickers=" + tickers.size() );
					if ( skips.size() > 0 ) {
						String msg = "skipped";
						for ( String skip : skips ) { msg += " " + skip.replaceAll( "\\[401\\]", ""); }
						Log.infoln(msg);
					}
				}
				else {		// it's a text query

					query = query.toUpperCase();

					toffeeQuery.doQuery( query );

					tickers = toffeeQuery.tickers;
					userNames = toffeeQuery.userNames;
					signals = toffeeQuery.signals;
					tweeters = toffeeQuery.tweeters;
				}
			}	// end if debug

			xml = buildXml(tickers, userNames, signals, tweeters);

			if ( isStoredToffee ) {
				PrintStream out = Util.getPrintStream(toffeeXmlFile.getAbsolutePath());
				out.print(xml);
				out.close();
			}

			return xml;
		}
	    catch( Exception e ) { throw new RepresentationException( Util.getStackTrace( e ) ); }
    }

    public String buildXml(TreeSet<String> tickers, TreeSet<String> userNames, TreeSet<Signal> signals, HashMap<String, Tweeter> tweeters) {

		String[][] map = new String[tickers.size()][userNames.size()];

		Element root = new Element("toffee");
		root.addContent(new Element("project").addContent(new CDATA(getProject())));
		Element grid = new Element("grid");
		Element queryEl = new Element("query");
		if (query == null) {
			queryEl.setText(getResourceId().trim().toUpperCase());
		}
		else {
			queryEl.setText(query.replace("$", "").trim().toUpperCase());
		}
		root.addContent(queryEl);

		Element firstrow = new Element("firstrow");
		firstrow.addContent(new Element("firstrowcol").addContent(""));
		for ( String userName : userNames ) {
			firstrow.addContent(new Element("firstrowcol").addContent(Util.toTitleCase(userName)));
		}
		grid.addContent(firstrow);

		Element rtrow = new Element("rtrow");
		rtrow.addContent(new Element("rtrowcol").addContent(""));

		for ( String userName : userNames ) {

			try {
				Tweeter tweeter = tweeters.get(userName);
				Element rtcol = new Element("rtrowcol");
				rtcol.addContent(new Element("url").addContent(tweeter.topRetweet.url));
				rtcol.addContent(new Element("count").addContent("" + tweeter.topRetweet.retweetCount));
				rtcol.addContent(new Element("text").addContent(tweeter.topRetweet.text));
				rtrow.addContent(rtcol);
			}
			catch (Exception ex) {
				//Log.infoln("Warning: " + ex.getMessage());
			}
		}
		grid.addContent(rtrow);

		for (Signal signal : signals) {
			map[getIndex(tickers, signal.tickerName)][getIndex(userNames, signal.userName)] = "" + signal.getNature() + "<>" + signal.url + "<>" + dateFormat.format(signal.created) + " " + signal.text;
		}

		Iterator tickersit = tickers.iterator();
		int i = 0;
		while (tickersit.hasNext()) {

			String ticker = (String) tickersit.next();
			Element row = new Element("row");
			row.addContent(new Element("firstcol").addContent(ticker));

			for (int j = 0; j < userNames.size(); j++) {

				String val = map[i][j];
				Element col = new Element("col");

				if (val != null) {

					String[] vals = val.split("<>");
					String nats[] = vals[0].split(":");
					String str = "";
					for (int k = 2; k < vals.length; k++) {
						str += vals[k];
					}
					String statusUrl = vals[1];
					String handle = Tweet.handleFromUrl( statusUrl );
					col.addContent(new Element("id").addContent("" + i + "_" + j));
					// col.addContent( new Element( "created" ).addContent( map))
					col.addContent(new Element("mention").addContent(nats[0]));
					col.addContent(new Element("longEquity").addContent(nats[1]));
					col.addContent(new Element("longCall").addContent(nats[2]));
					col.addContent(new Element("shortPut").addContent(nats[3]));
					col.addContent(new Element("shortEquity").addContent(nats[4]));
					col.addContent(new Element("shortCall").addContent(nats[5]));
					col.addContent(new Element("longPut").addContent(nats[6]));
					col.addContent(new Element("url").addContent( statusUrl ));
					col.addContent(new Element("handle").addContent( handle ));
					col.addContent(new Element("text").addContent(str));
				}
				row.addContent(col);
			}
			grid.addContent(row);
			i++;
		}
		root.addContent(grid);

		return getJdomWriter().writeToString(root);
	}

    public static int getIndex(Set<? extends Object> set, Object value) {

	   int result = 0;
	   for (Object entry:set) {
		   if (entry.equals(value)) return result;
		   result++;
	   }
	   return -1;
    }

    public boolean isContainsTicker( String text ) {
		return text.matches(".+?[$][a-zA-Z]+?\\W.+?");	// We have at least one $ticker
    }

	public boolean isContainsTicker( String text, List tickerFilterList ) throws Exception {

		if ( tickerFilterList == null ) {
			return isContainsTicker( text );
		}
		else {	// we have a ticker filter

			Iterator tickerFilter = tickerFilterList.iterator();
			while( tickerFilter.hasNext() ) {

				String ticker = ((String) tickerFilter.next()).trim();
				//if ( text.matches(".+?[$]" + ticker + "+?\\W.+?") ) {    // We have at least one ticker match
				//if ( text.matches("(^|[$\\W])" + ticker + "(\\W|$)") ) {		// We have at least one ticker match
				//Pattern pattern = Pattern.compile("(^|[$\\W])" + ticker + "(\\W|$)", Pattern.CASE_INSENSITIVE);	// We have at least one ticker match
				Pattern pattern = Pattern.compile("([$])" + ticker + "(\\.|\\W|$)", Pattern.CASE_INSENSITIVE);		//
				Matcher matcher = pattern.matcher("" + text + "");
				if ( matcher.find() ) {
					return true;
				}
			}
			return false;
		}
	}

	public String getQuery() {
		return query;
	}
	public void setQuery(String query) {
		this.query = query;
	}

}


    /*
	public static int mention = 0;
	public static int longEquity = 1;
	public static int longCall = 2;
	public static int shortPut = 3;
	public static int shortEquity = 4;
	public static int shortCall = 5;
	public static int longPut = 6;
     */

    /*
        //Instantiate and initialize a new twitter status update
        StatusUpdate statusUpdate = new StatusUpdate(
                //your tweet or status message
                "H-1B Transfer Jobs | Java Developer | Harrison, NY | 2 Years" +
                " - http://h1b-work-visa-usa.blogspot.com/2013/07/h-1b-transfer-jobs-java-developer_19.html");
        //attach any media, if you want to
        statusUpdate.setMedia(
                //title of media
                "http://h1b-work-visa-usa.blogspot.com"
                , new URL("http://lh6.ggpht.com/-NiYLR6SkOmc/Uen_M8CpB7I/AAAAAAAAEQ8/tO7fufmK0Zg/h-1b%252520transfer%252520jobs%25255B4%25255D.png?imgmax=800").openStream());
 
        //tweet or update status
        Status status = twitter.updateStatus(statusUpdate);
 
        //response from twitter server
        System.out.println("status.toString() = " + status.toString());
        System.out.println("status.getInReplyToScreenName() = " + status.getInReplyToScreenName());
        System.out.println("status.getSource() = " + status.getSource());
        System.out.println("status.getText() = " + status.getText());
        System.out.println("status.getContributors() = " + Arrays.toString(status.getContributors()));
        System.out.println("status.getCreatedAt() = " + status.getCreatedAt());
        System.out.println("status.getCurrentUserRetweetId() = " + status.getCurrentUserRetweetId());
        System.out.println("status.getGeoLocation() = " + status.getGeoLocation());
        System.out.println("status.getId() = " + status.getId());
        System.out.println("status.getInReplyToStatusId() = " + status.getInReplyToStatusId());
        System.out.println("status.getInReplyToUserId() = " + status.getInReplyToUserId());
        System.out.println("status.getPlace() = " + status.getPlace());
        System.out.println("status.getRetweetCount() = " + status.getRetweetCount());
        System.out.println("status.getRetweetedStatus() = " + status.getRetweetedStatus());
        System.out.println("status.getUser() = " + status.getUser());
        System.out.println("status.getAccessLevel() = " + status.getAccessLevel());
        System.out.println("status.getHashtagEntities() = " + Arrays.toString(status.getHashtagEntities()));
        System.out.println("status.getMediaEntities() = " + Arrays.toString(status.getMediaEntities()));
        if(status.getRateLimitStatus() != null)
        {
            System.out.println("status.getRateLimitStatus().getLimit() = " + status.getRateLimitStatus().getLimit());
            System.out.println("status.getRateLimitStatus().getRemaining() = " + status.getRateLimitStatus().getRemaining());
            System.out.println("status.getRateLimitStatus().getResetTimeInSeconds() = " + status.getRateLimitStatus().getResetTimeInSeconds());
            System.out.println("status.getRateLimitStatus().getSecondsUntilReset() = " + status.getRateLimitStatus().getSecondsUntilReset());
            System.out.println("status.getRateLimitStatus().getRemainingHits() = " + status.getRateLimitStatus().getRemainingHits());
        }
        System.out.println("status.getURLEntities() = " + Arrays.toString(status.getURLEntities()));
        System.out.println("status.getUserMentionEntities() = " + Arrays.toString(status.getUserMentionEntities()));
    */
