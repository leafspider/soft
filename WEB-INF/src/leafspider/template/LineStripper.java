package leafspider.template;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leafspider.util.*;

public class LineStripper extends Template
{
	public static void main(String[] args) 
	{
		try
		{
			TemplateProperties props = new TemplateProperties();
			props.load( new FileInputStream( new File( "C:\\CIRILab\\kgs\\admin\\templates\\export.properties" ) ) );

			LineStripper stripper = new LineStripper();
			stripper.setProperties(props);
			
			String txt = "FEMA Leads Effort; Federal Emergency Borders Tightened";
			System.out.println( txt );
			System.out.println( "" + stripper.stripLine( txt ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void stripBoilerPlateFromText( File textFile ) throws Exception
	{
		char lineFeed = 10;
		char carriageReturn = 13;
		Iterator lines = Util.getArrayListFromFile( textFile.getAbsolutePath() ).iterator();
		StringBuffer buffer = new StringBuffer();
		boolean isWriting = false;
		if ( getStart().trim().length() > 0 ) { isWriting = true; }
		while ( lines.hasNext() )
		{
			String line = (String) lines.next();
			if ( getStart().trim().length() < 1 || line.matches( getStart() ) )
			{
				isWriting = true;
			}
			if ( getStop().trim().length() > 0 && line.matches( getStop() ) )
			{
				isWriting = false;
				break;
			}
			if ( isWriting )
			{
				if ( !stripLine( line ) )
				{
//					buffer.append( line + lineFeed + carriageReturn );
					buffer.append( line + carriageReturn );
				}
			}
		}
		Util.writeAsFile( buffer.toString(), textFile.getAbsolutePath() );
	}

	private String start = null;
	public String getStart() 
	{
		if ( start == null )
		{
			start = getProperties().getProperty( "start" );
		}
		return start;
	}

	private String stop = null;
	public String getStop() 
	{
		if ( stop == null )
		{
			stop = getProperties().getProperty( "stop" );
		}
		return stop;
	}
	
	public ArrayList obgetStrips() 
	{
		if ( strips == null )
		{
			strips = new ArrayList();
			Iterator keys = getProperties().keySet().iterator();
			while ( keys.hasNext() )
			{
				String key = (String) keys.next();
				if ( key.matches( "strip\\d+" ) )
				{
					String value = getProperties().getProperty( key );
					if ( value.trim().length() > 0 )
					{
						strips.add( getProperties().getProperty( key ) );
					}
				}
			}
		}
		return strips;
	}

	public ArrayList getForces() 
	{
		return forces;
	}
	
	private boolean stripLine( String line )
	{
//		if ( line.equals( "" ) ) { return false; } 
		Iterator list = getStrips().iterator();
		while( list.hasNext() )
		{
			String strip = (String) list.next();
//			CIRILogger.infoln( "strip \"" + strip + "\" " + line );
			if ( line.matches( strip ) )
			{
				/*
				if ( !line.equals( "" ) )
				{
					CIRILogger.infoln( "MATCH \"" + line + "\" matches " + strip );
				}
				*/
				return true;
			}
		}
		return false;				
	}

	public ArrayList getStrips() 
	{
		if ( strips == null )
		{
			strips = new ArrayList();
			Iterator keys = getProperties().keySet().iterator();
			while ( keys.hasNext() )
			{
				String key = (String) keys.next();
				if ( key.matches( "templateName" ) 
					|| key.matches( "template" )  
					|| key.matches( "path" ) 
					|| key.matches( "start" )  
					|| key.matches( "stop" ) )
				{ continue; }
				else
				{
					String value = getProperties().getProperty( key );
					if ( value.trim().length() == 0 ) 
					{
						value = "false";
						getProperties().setProperty( key, value );
					}
					if ( value.equals( "false" ) ) 
					{
//						System.out.println( "Strip " + key + "=" + value );
						strips.add( key );
					}
				}
			}
		}
		return strips;
	}
	
}
