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
import soft.report.BarchartFutures;

public class AssetlistForex extends AssetlistRepresentation
{	
	public static String representation = "forex";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( representation );
	   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );

			BarchartForex page = new BarchartForex();
			page.setResourceId( getResourceId() );
//			Log.debug = true;
			
//			String summaryDate = page.getSummaryDate();
			String html = "";			
			
			File outFile = new File( page.getOutputFolder() + "\\output.htm" );
			if( outFile.exists() )
			{
				html = Util.fileToString(outFile, "\n");
			}
			else
			{
				page.populate();
				html = page.getHtml();
				
				PrintStream out = Util.getPrintStream( outFile.getAbsolutePath() );
				out.print(html);
			}
			
//			root.addContent( new Element( "summaryDate" ).addContent( summaryDate ) );
			root.addContent( new Element( "html" ).addContent( html ) );

			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
