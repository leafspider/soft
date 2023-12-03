package soft.assetlist;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.hibernate.mapping.Set;
import org.jdom2.CDATA;
import org.jdom2.Element;

import leafspider.util.DateUtils;
import soft.asset.Asset;
import soft.performance.Trade;
import soft.portfolio.Stock;
import soft.report.BarchartForex;
import soft.report.BarchartFutureOptions;
import soft.report.BarchartFutures;

public class AssetlistStratvol extends AssetlistRepresentation
{	
	public static String representation = "stratvol";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( representation );
	   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );

			BarchartFutureOptions page = new BarchartFutureOptions();
			page.setResourceId("futureoptions");

			Properties prop = page.getProperties();
			String title = prop.getProperty("title");
			
			ArrayList<String> urls = new ArrayList<String>();
			
			for( int i=0; i< 30; i++) {
				String val = prop.getProperty("url" + i);
				if( val != null ) { urls.add( val ); }
			}
			
			Iterator<String> urlsit = urls.iterator();
			while( urlsit.hasNext() ) {
				page.populate( urlsit.next() );
			}			
			
//			Log.infoln(page.getHtml());
			
			File file = new File( page.getOutputFolder().getAbsolutePath() + "\\stratvol.csv");
			String fileUrl = "/soft/data/report/" + representation + "/" + page.getResourceId() + "/" + file.getName();
			
			PrintStream out = Util.getPrintStream( file.getAbsolutePath() );
			out.print(page.getHtml());
			
			String html = "<table class=\"datatable\"><tr>";
			html += page.getHtml().replaceAll("\t\n", "</td></tr><tr><td>");
			html = html.replaceAll("\n", "</td></tr><tr><td>");
			html = html.replaceAll("\t","</td><td>" );
			html = html.replaceAll("<td></td>","<td><br/></td>" );
			html += "</td></tr></table>";

//			Log.infoln(html);
			
			root.addContent( new Element( "html" ).addContent( html ) );
			root.addContent( new Element( "csvUrl" ).addContent( fileUrl ) );
						
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
