package soft.toffee;

import leafspider.util.Duration;
import leafspider.util.Log;
import leafspider.util.Timestamp;
import leafspider.util.Util;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.util.List;

public class StatusAgent extends Thread {

    public int frequencyHours = 2;
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
            StatusAgent agent = new StatusAgent();
            agent.populate();
        }
        catch (Exception e) { e.printStackTrace(); }
    }

    public void run() {
        populate();
    }

    public void populate() {

        ToffeeStore store = new ToffeeStore();
        File timestampFolder = new File( store.agentFolder().getAbsolutePath() + "\\status" );

        while( isKeepRunning() ) {

            try {

                Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.agent );

                long last = Timestamp.readTimestamp( timestampFolder );

                if( Duration.hours(last, Util.getNow() ) > frequencyHours ) {

                    Log.infoln("StatusAgent populate");

                    List<String> tweeters = store.loadAllNetworks();
                    store.updateStatuses( twitter, tweeters, true );

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
