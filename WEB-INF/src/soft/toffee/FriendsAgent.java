package soft.toffee;

import leafspider.util.*;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class FriendsAgent extends Thread {

    public int frequencyHours = 4;
    public int sleepSeconds = 60;

    boolean keepRunning = true;
    public boolean isKeepRunning()
    {
        return keepRunning;
    }
    public void setKeepRunning(boolean keep)
    {
        this.keepRunning = keep;
    }

    public static void main(String[] args) {

        try {
            FriendsAgent agent = new FriendsAgent();
            agent.populate();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void run() {
        populate();
    }

    public void populate() {

        ToffeeStore store = new ToffeeStore();
        File timestampFolder = new File( store.agentFolder().getAbsolutePath() + "\\friends" );

        while( isKeepRunning() ) {

            try {

                Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.agent );

                long last = Timestamp.readTimestamp( timestampFolder );

                if( Duration.hours(last, Util.getNow() ) > frequencyHours ) {

                    Log.infoln("FriendsAgent populate");

                    List<String> toffees = store.getAdvancedToffees();
                    Iterator<String> iterator = toffees.iterator();

                    int requests = 0;
                    while (iterator.hasNext()) {

                        String toffee = iterator.next();
                        requests = store.updateNetwork( twitter, toffee, requests, false );
                    }

                    Timestamp.writeTimestamp( timestampFolder );
                }

                sleep(sleepSeconds * 1000 );
            }
            catch( Exception e ) {
                e.printStackTrace();
            }
        }
    }

}
