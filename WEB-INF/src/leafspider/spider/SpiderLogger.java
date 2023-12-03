package leafspider.spider;
import java.io.*;

import org.apache.log4j.*;

import java.util.Enumeration;
import java.util.ArrayList;
import java.util.Iterator;

import leafspider.util.*;

//import com.cirilab.kos.node.NodeManager;

public class SpiderLogger extends Logger
{
	public static void main(String[] args)
	{		
		try
		{
			FileAppender appender = null;
			try { appender = new FileAppender( new PatternLayout(),"C:\\Temp\\spider.log" );} 
			catch(Exception e) {}			
			Logger log = Logger.getLogger( "SpiderLog" );
			log.addAppender( appender );			
			log.info( "trout" );
			
			/*
			SpiderLogAppender appender = (SpiderLogAppender) log.getAppender( "SpiderFile" );
			if (appender instanceof SpiderLogAppender) 
			{ 
				System.out.println( appender.getFile() );
			} 
			else
			{
				System.out.println( appender.getClass().getCanonicalName() );				
			}

			/*
			ArrayList list = Log.getLogTail( 30 );			
			System.out.println( "bobbins" );
//			System.out.println( new NodeManager().getDocFilterXml( "canadian , energy" ) );
 			*/
		}
		catch ( Exception e ) { e.printStackTrace(); }
	}

	protected SpiderLogger( String name ) 
	{
		super( name );
//		setLogFile( file );		
	}

	protected SpiderLogger( String name, File file ) 
	{
		super( name );
		setLogFile( file );		
	}
	
	private File logFile = null; 
	public synchronized File getLogFile() { 
		return logFile; 
	}
	public void setLogFile( File thisFile ) { 
		logFile = thisFile; 
	}

}
