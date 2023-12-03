package scenario.more;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

public class ExhibitorFeed extends ExhibitorRepresentation
{	
	public static String representation = "exhibitorfeed";
    public String getRepresentation() { return representation; }
    	
    public String getXml() throws RepresentationException
    {
    	String feedTitle = getResourceId();
        if ( feedTitle == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Log.infoln( "ExhibitorFeed.feedTitle=" + feedTitle );			
			Log.infoln( "ExhibitorFeed.url=" + getRequest().getRequestURL() );
			Log.infoln( "ExhibitorFeed.qs=" + getRequest().getQueryString() );
//	    	reportParameters( getRequest() );
			
//			String csv = "C:\\Workspace\\Ultra\\Nick\\" + app.toUpperCase() + "\\speaker_log.csv";
			String csv = getRequest().getParameter( "csv" );
//			String pav = getRequest().getParameter( "pav" );
//			if( pav == null || pav.trim().length() == 0 ) { pav = app; }
//			else { app = pav.toLowerCase(); }
//			Log.infoln( "pav=" + pav );
			Log.infoln( "ExhibitorFeed.csv=" + csv );
			
			Iterator rows = Util.getArrayListFromFile( csv ).iterator();

			String regex = "\\p{Punct}";
			
			CsvRowOCE row = null;
			while(rows.hasNext())
			{
				String[] vals = ((String) rows.next()).split(",");
				if( vals.length > 1)
				{
					row = new CsvRowOCE( vals );
					if( row.category.equalsIgnoreCase("Category") ) { continue; }
//					Log.info( "[" + row.name + "]");
//					Log.info( "[" + feedTitle + "]");
//					Log.infoln( "[" + row.url + "]");
					if( row.name.replaceAll(regex, "").indexOf( feedTitle) == 0 ) 
					{ 
						Log.infoln( "found row.url=" + row.url);
						break; 
					}
				}
			}
			if( row == null ) { throw new Exception( "Row not found for feed: " + feedTitle + " in " + csv ); }
			
			Element root = new Element( "channel" );
	   		root.addContent( new Element( "title" ).addContent( new CDATA( "" + feedTitle ) ) );
			root.addContent( new Element( "link" ).addContent( new CDATA( "" + row.url ) ) );
			root.addContent( new Element( "hall" ).addContent( new CDATA( row.hall ) ) );
			root.addContent( new Element( "stand" ).addContent( new CDATA( row.stand ) ) );
			
			String feedFolder = Util.getCleanFileName( row.name );	//.replaceAll(" ", "_");			
			File folder = new File( "C:\\Server\\tomcat6\\webapps\\scenario\\data\\" + row.pavilion + "\\text\\" + row.category + "\\" + feedFolder );
			if( folder.exists() ) 
			{
				Log.infoln( "folder exists=" + folder.getAbsolutePath() );
				File[] pages = folder.listFiles();			
				if( pages != null )
				{
					Log.infoln( "  pages=" + pages.length );
					
//					for(int i=0; i<pages.length; i++)
//					{
//						File page = pages[i];
//						Log.infoln( "  page=" + page.getName() );

						Element item = new Element( "item" );
//						item.addContent( new Element( "title" ).addContent( new CDATA( page.getName() ) ) );
						item.addContent( new Element( "title" ).addContent( new CDATA( "text" ) ) );
//						item.addContent( new Element( "link" ).addContent( new CDATA( "http://" + feedUrl ) ) );
						item.addContent( new Element( "link" ).addContent( new CDATA( row.url ) ) );
						item.addContent( new Element( "pubDate" ).addContent( new CDATA( "" + (new Date()).toString() ) ) );
	
						StringBuffer buf = new StringBuffer();
						for(int i=0; i<pages.length; i++)
						{
							File page = pages[i];
							buf.append(Util.fileToString(page, "\n") + "\n");
						}
//						item.addContent( new Element( "description" ).addContent( new CDATA( buf.toString().getBytes("UTF-8").toString() ) ) );
						item.addContent( new Element( "description" ).addContent( new CDATA( sanitizeXmlString( buf.toString()) ) ) );
						
//						item.addContent( new Element( "description" ).addContent( new CDATA( sanitizeXmlString( Util.fileToString(page, "\n") ) ) ) );
						root.addContent( item );
//					}
				}
			}
			else
			{
				Log.infoln( "folder missing=" + folder.getAbsolutePath() );
			}					
			Log.infoln( "" );
	
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    public String sanitizeXmlString(String xml)
    {
        StringBuilder buffer = new StringBuilder(xml.length());
        byte[] bytes = xml.getBytes();
        for(int i=0; i<bytes.length; i++ )
        {
        	char c = (char) bytes[i];
            if (isLegalXmlChar(c))
            {
                buffer.append(c);
            }
        }
        return buffer.toString();
    }

    public boolean isLegalXmlChar(int character)
    {
        return
        (
             character == 0x9 /* == '\t' == 9   */          ||
             character == 0xA /* == '\n' == 10  */          ||
             character == 0xD /* == '\r' == 13  */          ||
            (character >= 0x20    && character <= 0xD7FF  ) ||
            (character >= 0xE000  && character <= 0xFFFD  ) ||
            (character >= 0x10000 && character <= 0x10FFFF)
        );
    }

	
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			String csv = "C:\\Workspace\\Ultra\\Nick\\OCE\\final_davis.csv";
			Iterator rows = Util.getArrayListFromFile( csv ).iterator();

			CsvRowOCE row = null;
			while(rows.hasNext())
			{
				String[] vals = ((String) rows.next()).split(",");
				if( vals.length > 1)
				{
					row = new CsvRowOCE( vals );
					Log.infoln(row.name + " = " + row.url);
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
