package soft.toffee;

import leafspider.util.Duration;
import leafspider.util.Log;
import leafspider.util.Util;
import soft.report.Folders;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.*;
import java.util.*;

public class ToffeeStore {

    public int friendSampleSize = 10;
    public int followerSampleSize = 10;
    public int windowMins = 15;

    public static void main( String[] args ) {

        try {
            ToffeeStore store = new ToffeeStore();

            List<String> toffees = store.getAdvancedToffees();
            Log.infoln("toffees=" + toffees.size() );

            Twitter twitter = TwitterCreds.twitterInstance(TwitterCreds.agent);

            String toffee = "Pensions";
            store.updateNetwork(twitter, toffee, 0, false);
        }
        catch( Exception e) {
            e.printStackTrace();
        }
    }

    public int updateNetwork(Twitter twitter, String toffee, int requests, boolean forceUpdate ) throws Exception {

        List tweeters = tweeters( toffee );
        Iterator iterator = tweeters.iterator();

        ArrayList skips = new ArrayList();

        while ( iterator.hasNext() ) {

            String userName = (String) iterator.next();
            System.out.println("updateNetwork " + userName + "");

            if ( exception( userName ).exists() ) {
                String message = readException( userName );
                String statusCode = message.substring( 0, message.indexOf( ":") );
                if ( saveException( statusCode ) ) {
                    skips.add( userName + "[" + statusCode + "]" );
                    continue;
                }
                else {
                    deleteException( userName );
                }
            }

            try {

                Tweeter tweeter = new Tweeter(twitter, userName);

                File folder = tweeterFolder(userName);
                folder.mkdirs();

                File friendsFile = friendsFile(userName);

                if (forceUpdate || !friendsFile.exists() || Duration.mins(friendsFile.lastModified(), Util.getNow()) > 60) {

                    if (!friendsFile.exists() || forceUpdate) {
                        List friends = tweeter.getFriends();
                        Util.writeAsFile(friends, friendsFile.getAbsolutePath());
                    }

                    File followersFile = followersFile(userName);
                    if (!followersFile.exists() || forceUpdate) {
                        List followers = tweeter.getFollowers();
                        Util.writeAsFile(followers, followersFile.getAbsolutePath());
                    }

                    requests++;
                }

                if (requests >= 15) {
                    Log.infoln("" + requests + " friends updates, wait " + windowMins + " mins");
                    Thread.sleep(windowMins * 60 * 1000);
                    requests = 0;
                }
            }
            catch (TwitterException te) {
                if ( saveException( te.getStatusCode() ) ) {
                    writeException(userName, te.getMessage());
                }
                else {
                    Log.infoln("Twitter exception " + te.getStatusCode() + " for " + userName);
                    if ( te.getStatusCode() == 429 ) {
                        break;  // exceeded rate limit
                    }
                }
            }
        }

        if ( skips.size() > 0 ) {
            String msg = "Skipped";
            Iterator iter = skips.iterator();
            while (iter.hasNext()) { msg += " " + iter.next(); }
            Log.infoln(msg);
        }

        return requests;
    }

    public List loadNetwork(List userNamesListFromFile ) throws Exception {

        List userNames = new ArrayList();
        Iterator iterator = userNamesListFromFile.iterator();
        while ( iterator.hasNext()) {

            String userName = (String) iterator.next();

            List friends = loadFriends( userName );
            friends = friends.subList(0, Math.min(friends.size(), friendSampleSize));
            userNames.addAll( friends );

            List followers = loadFollowers( userName );
            followers = followers.subList(0, Math.min(followers.size(), followerSampleSize));
            userNames.addAll( followers );

            //System.out.println( "" + userName + " friends(" + friends.size() + ") followers(" + followers.size() + ")" );
        }
        return userNames;
    }

    public List loadAllNetworks() throws Exception {

        List<String> toffees = getAdvancedToffees();
        Iterator<String> iterator = toffees.iterator();

        Set<String> userNamesSet = new HashSet<String>();
        while (iterator.hasNext()) {

            String toffee = iterator.next();

            List userNamesFromFile = tweeters( toffee );
            userNamesSet.addAll( userNamesFromFile );

            List network = loadNetwork( userNamesFromFile );
            userNamesSet.addAll( network );
        }

        List<String> userNamesList = new ArrayList();
        Iterator<String> setIterator = userNamesSet.iterator();
        while ( setIterator.hasNext() ) {
            userNamesList.add( setIterator.next() );
        }
        return userNamesList;
    }

    public static File tweeterFile( String toffee) {
        return new File(Folders.toffeeConfigFolder() + "\\" + toffee + ".txt");
    }

    public static List tweeters(String toffee ) throws Exception {
        return Util.getArrayListFromFile(tweeterFile(toffee).getAbsolutePath());
    }

    public File tickerFile( String toffee ) {
        return new File(Folders.toffeeConfigFolder() + "\\" + toffee + "\\tickers.txt");
    }

    public List tickers( String toffee ) throws Exception {
        File file = tickerFile(toffee);
        if ( file.exists() ) { return Util.getArrayListFromFile( file.getAbsolutePath() ); }
        else { return new ArrayList(); }
    }

    public List allAdvancedTickers() throws Exception {

        List allTickers = new ArrayList();
        List<String> toffees = getAdvancedToffees();
        Iterator<String> iter = toffees.iterator();
        while( iter.hasNext()) {
            String toffee = iter.next();
            List tickers = tickers( toffee );
            allTickers.addAll( tickers );
        }
        return allTickers;
    }

    public boolean isAdvancedToffee( String toffee ) {

        if ( tickerFile( toffee ).exists() ) { return true; }
        return false;
    }

    public boolean isStoredToffee( String toffee ) {

        if ( tweeterFile( toffee ).exists() ) { return true; }
        return false;
    }

    public List<String> getAdvancedToffees() {

        File folder = Folders.toffeeConfigFolder();
        File[] files = folder.listFiles( new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith(".txt");
            }
        });

        List<String> toffees = new ArrayList<String>();
        for( int i=0; i<files.length; i++) {
            String toffee = Util.removeFileExtension(files[i].getName());
            if ( isAdvancedToffee( toffee ) ) {
                toffees.add(toffee);
            }
        }
        return toffees;
    }

    public static File dbFolder() {
        return new File(Folders.toffeeFolder() + "\\db" );
    }

    public static File agentFolder() {
        return new File(Folders.toffeeFolder() + "\\agent" );
    }

    public File tweeterFolder( String userName ) {
        return new File(dbFolder().getAbsolutePath() + "\\" + userName );
    }

    public File friendsFile( String userName ) {
        return new File(tweeterFolder( userName ).getAbsolutePath() + "\\friends.txt");
    }

    public File followersFile( String userName ) {
        return new File(tweeterFolder( userName ).getAbsolutePath() + "\\followers.txt");
    }

    public File statusFile( String userName ) {
        return new File(tweeterFolder( userName ).getAbsolutePath() + "\\statuses.txt");
    }

    public List loadFriends( String userName ) throws Exception {

        List friends = new ArrayList();
        File friendsFile = friendsFile( userName );
        if ( friendsFile.exists() ) {
            friends = Util.getArrayListFromFile( friendsFile.getAbsolutePath() );
        }
        return friends;
    }

    public List loadFollowers( String userName ) throws Exception {

        List followers = new ArrayList();
        File followersFile = followersFile( userName );
        if ( followersFile.exists() ) {
            followers = Util.getArrayListFromFile( followersFile.getAbsolutePath() );
        }
        return followers;
    }

    public void updateStatuses(Twitter twitter, List<String> tweeters, boolean forceUpdate ) throws Exception {

        Iterator<String> iterator = tweeters.iterator();

        int requests = 0;

        ArrayList skips = new ArrayList();

        while ( iterator.hasNext() ) {

            String userName = iterator.next();

            if ( exception( userName ).exists() ) {
                String message = readException( userName );
                String statusCode = message.substring( 0, message.indexOf( ":") );
                if ( saveException( statusCode ) ) {
                    skips.add( userName + "[" + statusCode + "]" );
                    continue;
                }
                else {
                    deleteException( userName );
                }
            }
            //System.out.println("Statuses " + userName + "");

            Tweeter tweeter = new Tweeter(twitter, userName);

            File folder = tweeterFolder( userName );
            folder.mkdirs();

            File statusFile = statusFile( userName );
            if ( !statusFile.exists() || forceUpdate ) {
                try {
                    List<Status> statuses = tweeter.getStatuses();
                    writeStatusesToFile(statuses, statusFile);
                    requests++;
                }
                catch (TwitterException te) {
                    if ( saveException( te.getStatusCode() ) ) {
                        writeException(userName, te.getMessage());
                    }
                    else {
                        Log.infoln("Twitter exception " + te.getStatusCode() + " for " + userName);
                        if ( te.getStatusCode() == 429 ) {
                            break;  // exceeded rate limit
                        }
                    }
                }
            }

            if ( requests >= 900 ) {
                Log.infoln("" + requests + " status updates, wait " + windowMins + " mins" );
                Thread.sleep(windowMins * 60 * 1000 );
                requests = 0;
            }
        }

        if ( skips.size() > 0 ) {
            String msg = "Skipped";
            Iterator iter = skips.iterator();
            while (iter.hasNext()) { msg += " " + iter.next(); }
            Log.infoln(msg);
        }

        Log.infoln("" + requests + " status updates, wait " + windowMins + " mins" );
        Thread.sleep(windowMins * 60 * 1000 );
    }

    public List loadStatuses( String userName ) throws Exception {

        List<Status> statuses = new ArrayList();
        File statusFile = statusFile( userName );
        if ( statusFile.exists() ) {
            statuses = readStatusesFromFile( statusFile );
            if ( statuses.size() > 0 ) {
                statuses.addAll(statuses);
            }
        }
        return statuses;
    }

    private void writeStatusesToFile(List<Status> list, File file )  throws Exception {

        FileOutputStream fos = new FileOutputStream( file.getAbsolutePath() );
        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject(list);

        out.close();
        fos.close();
    }

    private List readStatusesFromFile(File file ) throws Exception {

        FileInputStream fis = new FileInputStream(file.getAbsolutePath());
        ObjectInputStream in = new ObjectInputStream(fis);

        List<Status> list = (List<Status>) in.readObject();

        in.close();
        fis.close();

        return list;
    }

    public File exception(String userName ) {
        return new File(tweeterFolder( userName ).getAbsolutePath() + "\\exception.txt");
    }

    public void writeException( String userName, String message ) throws Exception {

        File file = exception( userName );
        file.getParentFile().mkdirs();

        FileOutputStream fos = new FileOutputStream( file.getAbsolutePath() );
        ObjectOutputStream out = new ObjectOutputStream(fos);

        out.writeObject( message );

        out.close();
        fos.close();
    }

    public String readException( String userName ) throws Exception {

        FileInputStream fis = new FileInputStream( exception( userName ).getAbsolutePath());
        ObjectInputStream in = new ObjectInputStream(fis);

        String message = (String) in.readObject();

        in.close();
        fis.close();

        return message;
    }

    public void deleteException( String userName ) {

        File file = exception( userName );
        if ( file.exists() ) {
            file.delete();
            Log.infoln("Deleted exception for " + userName );
        }
    }

    public boolean saveException( int statusCode ) {
        if ( statusCode == 401 || statusCode == 404 ) { return true; }
        return false;
    }

    public boolean saveException( String statusCode ) {
        return saveException( Integer.parseInt( statusCode ) );
    }
}
