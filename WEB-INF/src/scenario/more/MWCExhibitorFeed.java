package scenario.more;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

public class MWCExhibitorFeed extends ExhibitorRepresentation
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
//	    	reportParameters( getRequest() );
		
			Element root = new Element( "channel" );
	   		root.addContent( new Element( "title" ).addContent( new CDATA( "" + feedTitle ) ) );
			root.addContent( new Element( "link" ).addContent( new CDATA( "" + "http://linkedin.com" ) ) );
			
			int hall = (new Random()).nextInt((9 - 1) + 1) + 1;
			int stand = (new Random()).nextInt((99 - 1) + 1) + 1;			
			DecimalFormat df = new DecimalFormat("00");	        
			root.addContent( new Element( "hall" ).addContent( new CDATA( "H" + hall ) ) );
			root.addContent( new Element( "stand" ).addContent( new CDATA( "S" + df.format(stand) ) ) );
			
			String feedFolder = Util.getCleanFileName(feedTitle);
			File folder = new File( "C:\\Workspace\\Ultra\\Nick Trendov\\MWC\\exhibitor_text\\" + feedFolder );
			if( folder.exists() ) 
			{
				Log.infoln( "folder exists=" + folder.getAbsolutePath() );
				File[] pages = folder.listFiles();			
				if( pages != null )
				{
					Log.infoln( "  pages=" + pages.length );
					
					Element item = new Element( "item" );
					item.addContent( new Element( "title" ).addContent( new CDATA( "text" ) ) );
					item.addContent( new Element( "link" ).addContent( new CDATA( "http://linkedin.com/" + feedTitle ) ) );
					item.addContent( new Element( "pubDate" ).addContent( new CDATA( "" + (new Date()).toString() ) ) );

					StringBuffer buf = new StringBuffer();
					for(int i=0; i<pages.length; i++)
					{
						File page = pages[i];
						buf.append(Util.fileToString(page, "\n") + "\n");
					}
					item.addContent( new Element( "description" ).addContent( new CDATA( sanitizeXmlString( buf.toString()) ) ) );

					root.addContent( item );
				}
			}
			else		// It's a file!
			{
				File page = new File( folder.getAbsolutePath() + ".txt" );
				if(page.exists())
				{ 
					Log.infoln( "file exists=" + page.getAbsolutePath() );
					
					Element item = new Element( "item" );
					item.addContent( new Element( "title" ).addContent( new CDATA( "text" ) ) );
					item.addContent( new Element( "link" ).addContent( new CDATA( "http://linkedin.com/" + feedTitle ) ) );
					item.addContent( new Element( "pubDate" ).addContent( new CDATA( "" + (new Date()).toString() ) ) );
	
					StringBuffer buf = new StringBuffer();
					buf.append(Util.fileToString(page, "\n") + "\n");
					item.addContent( new Element( "description" ).addContent( new CDATA( sanitizeXmlString( buf.toString()) ) ) );
	
					root.addContent( item );
				}
				else
				{
					Log.infoln( "[] path missing=" + folder.getAbsolutePath() );
				}
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
