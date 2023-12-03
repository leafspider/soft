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
import soft.report.BarchartReport;

public class AssetlistReport extends AssetlistRepresentation
{	
	public static String representation = "report";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( representation );
	   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );

			BarchartReport page = new BarchartReport();
			page.setResourceId( getResourceId() );
//			Log.debug = true;
			
			String html = "";			

			File outFile = page.getOutputFile();	//new File( page.getOutputFolder() + "\\output.htm" );
//			Log.infoln( "outFile=" + outFile.getAbsolutePath() );
			if( outFile.exists() )
			{
				page.setHtml( Util.fileToString(outFile, "\n") );
			}
			else
			{
				page.populate();
				
				PrintStream out = Util.getPrintStream( outFile.getAbsolutePath() );
				out.print(page.getHtml());
				out.close();
			}
			
//			root.addContent( new Element( "reportTitle" ).addContent( page.getReportTitle() ) );
//			root.addContent( new Element( "reportDate" ).addContent( page.getReportDate() ) );
			root.addContent( new Element( "html" ).addContent( page.getHtml() ) );
			
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    /*
    public String getHtm() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			BarchartReport page = new BarchartReport();
			page.setResourceId( getResourceId() );
			
			File outFile = page.getOutputFile();
			if( outFile.exists() )
			{
				page.setHtml( Util.fileToString(outFile, "\n") );
			}
			else
			{
				page.populate();

				Element root = new Element( representation );
		   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
				root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );
				
				PrintStream out = Util.getPrintStream( outFile.getAbsolutePath() );
				out.print(page.getHtml());
				out.close();

//				root.addContent( new Element( "reportTitle" ).addContent( page.getReportTitle() ) );
//				root.addContent( new Element( "reportDate" ).addContent( page.getReportDate() ) );
				root.addContent( new Element( "html" ).addContent( page.getHtml() ) );
			}

			Log.infoln( "Serving report" );
			
			return Util.fileToString(outFile, "\n");
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    */

    private String reportId = null;
	public String getReportId() {
		return reportId;
	}
	public void setReportId(String reportId) {
		this.reportId = reportId;
	}
}
