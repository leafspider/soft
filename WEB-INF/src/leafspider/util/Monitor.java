package leafspider.util;

import java.util.Date;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import leafspider.util.*;


public class Monitor 
{
    private static String debug = null;
    public static boolean isDebug()
    {
    	if ( debug == null )
    	{
    		debug = "OFF";	//ServerContext.getContextParameter( "kos_monitor", "OFF" );
    	}
    	return debug.equalsIgnoreCase( "ON" );
    }
    public static void obdebug( String st )
    {
    	if ( isDebug() )
    	{
        	if ( st == null )
        	{
        		startDate = new Date();
        	}
        	else
       		{
        		Log.infoln( " [" + getDuration() + "] " + st );
       		}
    	}
    }

	private static Date startDate = null;
	private static synchronized float getDuration()
	{
		if ( startDate == null ) { startDate = new Date(); }
		Date endDate = new Date();
		long duration = endDate.getTime() - startDate.getTime();
		float secs = (float) duration / 1000;
		startDate = new Date();
		return secs;
	}	

	private static Hashtable table = new Hashtable();
    public static void debug( String st )
    {
    	if ( isDebug() )
    	{
    		if ( st == null )
        	{
        		startDate = new Date();
        	}
        	else
       		{
    			table.put( st, new Float( getDuration() ) );   
       		}
    	}
    }

    public static void report()
    {
    	if ( isDebug() )
    	{   
	    	String out = "DBG " + table.keySet().size() + ",";
	    	Iterator keys = table.keySet().iterator();
    		if ( isNew )
    		{
    			while( keys.hasNext() )
    			{
    				String key = (String) keys.next();
    		    	out = out + key + ",";
    			}
		    	Log.infoln( out );    			
		    	isNew = false;
    		}
	    	keys = table.keySet().iterator();
	    	out = "DBG " + table.keySet().size() + ",";
			while( keys.hasNext() )
			{
				String key = (String) keys.next();				
				Float val = (Float) table.get( key );
		    	out =  out + val.floatValue() + ",";
			}
	    	Log.infoln( out );    			
    	}
    }
	
    private static boolean isNew = true;
    public static void reset()
    {
    	debug = null;
		startDate = new Date();
		isNew = true;		
    }
	
    private static long stamp = -1;
    public static void println( String st )
    {
    	if ( isDebug() )
    	{   
    		long now = Util.getNow();
    		long diff;
    		if ( stamp == -1 ) { diff = 0; }
    		else { diff = now - stamp; }
	    	Log.infoln( "MO " + diff + "\t" + st );    			
    		stamp = now; 
    	}
    }
}
