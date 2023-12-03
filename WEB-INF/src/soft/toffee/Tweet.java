package soft.toffee;

import java.util.List;

import leafspider.util.Log;
import twitter4j.Status;

public class Tweet 
{
	public String userName;
	public String text;
	public List<Signal> signals;	
	public String url;
	public int retweetCount;

	public static void main(String[] args) {
		System.out.println( Tweet.handleFromUrl( "http://twitter.com/45kgthinmints/status/1586882400197349376" ));
	}

	public void report() throws Exception
	{
		Log.info( userName + ": " );
        for (Signal signal : signals) { Log.info( signal.tickerName + "(" + signal.mention + ") " ); }
		Log.infoln( "" );
	}

	public static String handleFromUrl( String url ) {

		String[] vals = url.split("/");
		return vals[3];
	}
}
