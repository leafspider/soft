package soft.assetlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.batch.BatchAgent;
import soft.batch.BatchProject;
import soft.report.CrushMap;

public class AssetlistCrush extends AssetlistRepresentation
{
	public static String representation = "crush";
    public String getRepresentation() { return representation; }

    /*
    public String getHtm() throws RepresentationException
    {
    	String id = getResourceId();
        if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
    		String endParm = getRequest().getParameter( "end" );
    		String yearsParm = getRequest().getParameter( "years" );
    		String monthsParm = getRequest().getParameter( "months" );
    		String daysParm = getRequest().getParameter( "days" );
//    		Log.infoln( "name=" + name );
    		
//			Element root = new Element( "crush" );
//	   		root.addContent( new Element( "project" ).addContent( new CDATA( getCollection() ) ) );
//			root.addContent( new Element( "name" ).addContent( new CDATA( getResourceId() ) ) );

			DateFormat format = Asset.defaultDateFormat;	
			Calendar end = parseDate( endParm, format );
			Calendar start = getStart( end, yearsParm, monthsParm, daysParm );	

//			root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
//			root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );

//			String[] tickers = getTickerList( name, end );
//			AlistCrusher crusher = new AlistCrusher( name );
//			File mapFile = crusher.crush( tickers, start, end );			// Do CrushMap analysis on ticker list

			String title = getRequest().getParameter("title");
    		String ticks = getRequest().getParameter("ticks");
    		if(ticks != null) { ticks = ticks.replaceAll(",", " "); }
    		
			Alist list = Alist.instance( id, end, title, ticks );
			list.populate();

			File mapFile = list.doCrush( start, end );			// Do CrushMap analysis on ticker list

			String mapUrl = Util.replaceSubstring( mapFile.getAbsolutePath(), "\\", "/" );
			int pos = mapUrl.indexOf( ServerContext.getApplicationName() );
			mapUrl = "/" + mapUrl.substring( pos, mapUrl.length() );
//			root.addContent( new Element( "mapUrl" ).addContent( new CDATA( mapUrl ) ) );

//			return getJdomWriter().writeToString( root );
			
			String html = "<html><body><script>location.href='" + mapUrl + "';</script></body></html>";
			return html;
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    */

	public String getXml() throws RepresentationException
	{
		if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }

		try {

//			String endParm = getRequest().getParameter( "end" );
			String topParm = getRequest().getParameter( "top" );

			Element root = new Element( "crushpicks" );
			root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );

			if ( getResourceId().equals( "new" ) ) {

				String title = getRequest().getParameter( "title" );
				String hierarchy = getRequest().getParameter( "hierarchy" );
				String ticks = getRequest().getParameter( "ticks" );
				ticks = ticks.replaceAll(",", " " );

				Alist alist = AlistBatch.instance(title, hierarchy, ticks);
				setResourceId( alist.getId() );
				alist.writePropertiesFile();
				Log.infoln( "Created list: " + alist.getTitle() );

				BatchAgent agent = new BatchAgent();
				agent.doBatchCrush( alist.getId() );
			}

			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );

			/*
			Properties props = new Properties();
			props.loadTimeline( new FileInputStream( BatchProject.getConfigFile( getResourceId() ) ) );
			String lag = props.getProperty("lag");
			Calendar end = Calendar.getInstance();
			if ( lag != null ) {
				int lagDays = Integer.parseInt(lag);
				end.add( Calendar.DATE, 0-lagDays);
			}
			String endParm = Asset.defaultDateFormat.format( end.getTime() );
			root.addContent( new Element( "end" ).addContent( new CDATA( endParm ) ) );
			*/

			CrushMap page = new CrushMap();
			page.setResourceId( getResourceId() );
			if ( topParm != null ) { page.setTop( Integer.parseInt(topParm) ); }

			page.populate();

			Element xml = page.getRoot();
			root.addContent( Timestamp.getElement( page.getOutputFolder() ) );
			root.addContent( xml );

			return getJdomWriter().writeToString( root );
		}
		catch( Exception e )
		{
			throw new RepresentationException( Util.getStackTrace( e ) );
		}
	}

	private String[] obpopulate( String url, Calendar end )
	{
//    	Log.infoln( "url=" + url );
		String[] list = null;
		if ( url != null )
		{
			try
			{
				Alist alist = AlistVariable.instance(url);
				if (alist != null)
				{
					alist.populate();
					list = alist.tickers.toArray(new String[] {});
					Log.infoln( "alist.title=" + alist.getTitle());
        	    	/*
        	    	System.out.println( "id=" + alist.id);
        	    	Iterator list1 = alist.tickers.iterator();
        	    	while( list1.hasNext())
        	    	{
        	    		System.out.println( "ticker=" + list1.next());
        	    	}
        	    	*/
				}
				else
				{
					File folder = Asset.croutFolder( end );
					File file = Downloader.downloadFile( url, folder );

					AlistBarcharts parser = new AlistBarcharts();
					parser.setFile(file);
//					list = parser.getTickers( file );
				}
				Log.infoln( "alist.length=" + list.length );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}
		return list;
	}

}
