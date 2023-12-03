package soft.asset;

import leafspider.rest.RepresentationException;
import leafspider.util.Timestamp;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import soft.assetlist.AssetlistRepresentation;
import soft.report.ViperReport;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AssetViper extends AssetRepresentation
{	
	public static String representation = "viper";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException {

		String ticker = getResourceId();
		if ( ticker == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try {

			String endParm = getRequest().getParameter( "end" );
			String yearsParm = getRequest().getParameter( "years" );
			String monthsParm = getRequest().getParameter( "months" );
			String daysParm = getRequest().getParameter( "days" );
			String startParm = getRequest().getParameter( "start" );

			DateFormat format = Asset.defaultDateFormat;
			// Calendar end = parseDate( endParm, format );		// jmh 2021-03-26 Don't yet support custom dates for viper
			Calendar end = new GregorianCalendar();
			Calendar start = getStart( end, yearsParm, monthsParm, daysParm );
			if( startParm != null ) {
				start = parseDate( startParm, format );
			}

			Element root = new Element( "viper" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			//root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );
			root.addContent( new Element( "ticker" ).addContent( new CDATA( getResourceId() ) ) );

			Asset asset = Asset.instance( ticker );
			root.addContent( new Element( "chartUrl" ).addContent( new CDATA( asset.chartUrl() ) ) );
			root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
			root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );

			ViperReport page = new ViperReport();
			page.setResourceId( getResourceId() );
			page.getTickerList().add( getResourceId() );

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

}
