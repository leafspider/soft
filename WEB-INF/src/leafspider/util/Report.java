package leafspider.util;

import java.io.*;
import java.util.*;

/** 
 * @since KOS 1.00.
 * @author Mark Hurst.
 */
public class Report 
{
	public static void main( String[] args )
	{
		try
		{
//			reportFileHandles( ".exe", "", soft.ServerContext.getApplicationRootFolder() + "\\log\\handles.log" );
			for ( int i = 0; i < 100; i++ )
			{
				System.out.println( reportMemory() );
				Thread.sleep( 2000 );
			}
		}
		catch ( Exception e ) { e.printStackTrace(); }
	}

	public Report()
	{		
		resetTimer();
	}
	
	public static boolean debug = false;

	public synchronized static void reportThreads()
	{
		try
		{
			ThreadGroup current = Thread.currentThread().getThreadGroup();
			Thread[] threads = new Thread[ current.activeCount() ];
			current.enumerate( threads );
			for( int i = 0; i < threads.length; i++ ) { Log.infoln( "Threads: alive=" + threads[i].getName() ); }
		}
		catch ( Exception e ) { Log.warnln("Exception: ", e); }
	}

	public synchronized static void reportThreads( PrintStream out ) { reportThreadGroup( out, Thread.currentThread().getThreadGroup() ); }	
	public synchronized static void reportThreadGroup( PrintStream out, ThreadGroup group )
	{
		if ( group.activeCount() > 0 )
		{
			Thread[] threads = new Thread[ group.activeCount() ];
			group.enumerate( threads );
			for( int i = 0; i < threads.length; i++ ) 
			{ 
				try
				{ 
					out.println( "Live Thread: " + threads[i].getName() ); 
					StackTraceElement[] ste = threads[i].getStackTrace();
					for (int w = 0; w < ste.length; w++)
					{
						out.println("\t" + ste[w].toString());
					}
				}
				catch ( Exception e ) { continue; }
			}
		}
		if ( group.activeGroupCount() > 0 ) 
		{ 
			ThreadGroup[] groups = new ThreadGroup[ group.activeGroupCount() ];
			group.enumerate( groups );			
			for( int i = 0; i < groups.length; i++ ) 
			{ 
				try { reportThreadGroup( out, groups[ i ] ); }
				catch ( Exception e ) { continue; }
			}
		}
	}
	
	public synchronized static String reportMemory()
	{	
		String unit = " kb";
		long totalMem = Runtime.getRuntime().totalMemory() / 1024;
		long freeMem = Runtime.getRuntime().freeMemory() / 1024;
		long maxMem = Runtime.getRuntime().maxMemory() / 1024;
		return ( "Memory: Total=" + totalMem + unit + ", Free=" + freeMem + unit + ", Max=" + maxMem + unit);
		//return ( "Memory: Total=" + ( (double) Runtime.getRuntime().totalMemory() / 1024 / 1024 ) + "mb,  Free=" + ( (double) Runtime.getRuntime().freeMemory() / 1024 / 1024 ) + "mb" );
	}		
	public synchronized static String reportMemory( String tag ) 
	{ 
		return ( tag + ", " + reportMemory() );
		// return ( tag + "," + ( (double) Runtime.getRuntime().freeMemory() / 1000 ) );
	}
	
	/** Requires handle.exe file in <Application Bin>\themes folder */
	public synchronized static void reportFileHandles( String process, String filter, String log ) 
	{
		try
		{
			File handleFile = new File( "\\bin\\handle.exe" );
			if ( !handleFile.exists() ) { return; }
			
			String[] cmdList = { "cmd.exe", "/c", "handle", "-p", process, filter, ">", log };
			File logFile = new File( log );
			logFile.getParentFile().mkdirs();
			Execute.execWait( cmdList );
			
			// Remove empty files (ie header only)
			Log.infoln( "Report.reportFileHandles length=" + logFile.length() + " " + filter + " " + Util.removePathFromFileName( log ) );
			if ( logFile.length() == 97 ) { logFile.delete(); }
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); }			
	}
	/** Requires handle.exe file in <Application Bin>\themes folder */
	public synchronized static void reportFileHandles( String filter, String log ) 
	{
		try { reportFileHandles( "java.exe", filter, log ); }
		catch( Exception e ) { Log.warnln("Exception: ", e); }			
	}

	private Date startDate;
	public void resetTimer() { startDate = new Date(); }
	public synchronized long getTimer()
	{
		Date endDate = new Date();
		if ( endDate == null ) { return ( new Date() ).getTime() - startDate.getTime(); }
		else { return endDate.getTime() - startDate.getTime(); }
	}	
	
	public synchronized float getTimerSeconds()
	{
		float secs = (float) getTimer() / 1000;
		return secs;
	}	

}
