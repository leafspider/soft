package leafspider.util;

import java.io.*;

import org.apache.log4j.*;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Iterator;

public class Log implements Serializable
{
	public static boolean debug = false;
	
	public static void main(String[] args)
	{		
		try
		{
			Log.getLogger();			
			Log.infoln( "trout" );

			/*
			ArrayList list = CIRILogger.getLogTail( 30 );			
			System.out.println( "bobbins" );
//			System.out.println( new NodeManager().getDocFilterXml( "canadian , energy" ) );
 			*/
		}
		catch ( Exception e ) { e.printStackTrace(); }
	}
	
	private static String lineSeparator = System.getProperty("line.separator", "\r\n");

	private static Logger logger = null;
	private synchronized static Logger getLogger()
	{
		if (logger == null)
		{
			logger = Logger.getLogger("SoftLogger");
//			Object eum = logger.getAllAppenders();
//			System.out.println("D");
		}		
		return logger;
	}

	public synchronized static void infoln(Object obj)
	{
		info(obj + lineSeparator);
	}

	public synchronized static void infoln(Object obj, Throwable t)
	{
		info(obj + lineSeparator, t);
	}

	public synchronized static void debugln(Object obj)
	{
		getLogger().debug(obj + lineSeparator);
	}

	public synchronized static void debugln(Object obj, Throwable t)
	{
		getLogger().debug(obj + lineSeparator, t);
	}

	public synchronized static void warnln(Object obj)
	{
		getLogger().warn(obj + lineSeparator);
	}

	public synchronized static void warnln(Object obj, Throwable t)
	{
		getLogger().warn(obj + lineSeparator, t);
	}

	public synchronized static void errorln(Object obj)
	{
		getLogger().error(obj + lineSeparator);
	}

	public synchronized static void errorln(Object obj, Throwable t)
	{
		getLogger().error(obj + lineSeparator, t);
	}

	public synchronized static void fatalln(Object obj)
	{
		getLogger().fatal(obj + lineSeparator);
	}

	public synchronized static void fatalln(Object obj, Throwable t)
	{
		getLogger().fatal(obj + lineSeparator, t);
	}

	public static PrintStream report = null;
	public synchronized static void info(Object obj)
	{
		getLogger().info(obj);
		if(debug) 
		{ 
			try
			{
				if( report == null )
				{
//					report = Util.getPrintStream("C:\\Workspace\\Ultra\\Susan\\globalpowergen\\report.csv");
					report = Util.getPrintStream("C:\\tmp\\ibc.xml");
				}
				report.print(obj);
			}
			catch( Exception e)
			{
				e.printStackTrace();
			}
//			System.out.print("" + obj); 
		}
	}

	public synchronized static void info(Object obj, Throwable t)
	{
		getLogger().info(obj, t);
//		if(debug) { System.out.print("" + obj); }
	}

	public synchronized static void debug(Object obj)
	{
		getLogger().debug(obj);
	}

	public synchronized static void debug(Object obj, Throwable t)
	{
		getLogger().debug(obj, t);
	}

	public synchronized static void warn(Object obj)
	{
		getLogger().warn(obj);
	}

	public synchronized static void warn(Object obj, Throwable t)
	{
		getLogger().warn(obj, t);
	}

	public synchronized static void error(Object obj)
	{
		getLogger().error(obj);
	}

	public synchronized static void error(Object obj, Throwable t)
	{
		getLogger().error(obj, t);
	}

	public synchronized static void fatal(Object obj)
	{
		getLogger().fatal(obj);
	}

	public synchronized static void fatal(Object obj, Throwable t)
	{
		getLogger().fatal(obj, t);
	}

	public synchronized static File getLogFile()
	{
		Enumeration en = getLogger().getAllAppenders();
		DailyRollingFileAppender app = (DailyRollingFileAppender) en.nextElement();
		return new File( app.getFile() );
	}

	public synchronized static ArrayList getLogTail( int numLines ) throws Exception
	{
		Iterator log = Util.getFileTail( getLogFile(), numLines ).iterator();
		ArrayList list = new ArrayList();
		while( log.hasNext() )
		{
			String txt = (String) log.next();
			int pos = txt.indexOf( " | " );		
			if ( pos > -1 )
			{
				list.add( txt.substring( pos + 3, txt.length() ) );
			}
			else
			{
				list.add( "" );
			}
		}		
		return list;
	}

	public synchronized static ArrayList getTimedLogTail( int numLines ) throws Exception
	{
		Iterator log = Util.getFileTail( getLogFile(), numLines ).iterator();
		ArrayList list = new ArrayList();
		while( log.hasNext() )
		{
			String txt = (String) log.next();
			list.add( txt );
		}		
		return list;
	}
	
	public synchronized static File getLogFolder()
	{
		return getLogFile().getParentFile();		
	}
	
}
