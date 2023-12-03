package leafspider.util;

public class Timer
{
	private long timeBefore = 0, timeAfter = 0;
	public synchronized void start()
	{
		timeBefore = System.currentTimeMillis();
//		Log.infoln("Time Before: " + timeBefore);
	}

	public synchronized void end()
	{
		timeAfter = System.currentTimeMillis();		
		float timeDiff = (float) (timeAfter - timeBefore) / 1000;	// Convert milliseconds to seconds		
		Log.infoln("Time Before: " + timeBefore);
		Log.infoln("Time After: " + timeAfter);
		Log.infoln("Time Diff: " + timeDiff + " secs");
	}

	public synchronized void start( String message )
	{
		timeBefore = System.currentTimeMillis();
		if ( message != null ) { Log.infoln( message + ": " + timeBefore ); }
	}

	public synchronized void end( String message )
	{
		timeAfter = System.currentTimeMillis();
		float timeDiff = (float) (timeAfter - timeBefore) / 1000;
		if ( message != null ) { Log.infoln( message + ": " + timeDiff + " secs"); }
	}
	
	public synchronized void reportMean( String message, int num )
	{
		timeAfter = System.currentTimeMillis();
		float timeDiff = (float) (timeAfter - timeBefore) / 1000;
		float mean = timeDiff / num;
		if ( message != null ) { Log.infoln( message + ": " + timeDiff + " (" + mean + ")" ); }
	}
	
}
