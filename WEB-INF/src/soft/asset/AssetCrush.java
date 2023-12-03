package soft.asset;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.db.DatabaseManager;
import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.input.SAXBuilder;

import soft.batch.BatchProject;


public class AssetCrush extends AssetRepresentation
{	
	public static String representation = "crush";
    public String getRepresentation() { return representation; }

	public static void main ( String[] args )
	{
		Log.debug = true;
		test();
//		testFuture();
		/*
		try
		{
			String ticker = "SIZ16|8500P";	//"UWE.V";//"FDC.V";	//"ALN.V";
			
			String endParm = null;//"2010-11-04";
    		String yearsParm = null;
    		String monthsParm = "12";
    		String daysParm = null;

			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );	
			Calendar end = parseDate( endParm, format );
			Calendar start = getStart( end, yearsParm, monthsParm, daysParm );	

			Log.infoln( "start=" + format.format( start.getTime() ) );
			Log.infoln( "end=" + format.format( end.getTime() ) );

//			Asset asset = new Asset( ticker );

			Option asset = new Option(ticker);
			asset.loadPrices( start, end );
//			Log.infoln( option.getXml() );

			Asset.debug = false;
			if ( asset.doCrush( start, end ) )
			{
//				Log.outln( ticker + "=" + asset.crushes[0] );
				/*
				root.addContent( new Element( "crush10" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[0] ) ) ) );
				root.addContent( new Element( "crush20" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[1] ) ) ) );
				root.addContent( new Element( "crush30" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[2] ) ) ) );			
				root.addContent( new Element( "close" ).addContent( new CDATA( getSeriesValues( asset.nClose ) ) ) );
				root.addContent( new Element( "flow" ).addContent( new CDATA( getSeriesValues( asset.nVol ) ) ) );
				root.addContent( new Element( "peel" ).addContent( new CDATA( getSeriesValues( asset.nsdLong) ) ) );
				root.addContent( new Element( "jam" ).addContent( new CDATA( getSeriesValues( asset.nsdMedium ) ) ) );
				root.addContent( new Element( "press" ).addContent( new CDATA( getSeriesValues( asset.nsdShort ) ) ) );
				root.addContent( new Element( "crush" ).addContent( new CDATA( getSeriesValues( asset.crushSeries ) ) ) );
				*/
		/*
			}		
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		*/
	}

	public static void test()
	{
		try
		{
			Asset.debug = false;
//			Asset asset = Asset.instance( "AF^C" );
//			Asset asset = Asset.instance( "AIG/WS" );
			//Asset asset = Asset.instance( "SPY" );
			//Asset asset = Asset.instance( "BAYK" );
			Asset asset = Asset.instance( "VSBN" );

			String endParm = null;
    		String yearsParm = "1";
    		String monthsParm = null;
    		String daysParm = null;
    		
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );	
			Calendar end = parseDate( endParm, format );
			Calendar start = getStart( end, yearsParm, monthsParm, daysParm );	
			
//			Log.infoln( "start=" + format.format( start.getTime() ) );
//			Log.infoln( "end=" + format.format( end.getTime() ) );
			
//			asset.loadPrices(start, end);
//			Log.infoln( asset.getXml() );

			asset.doCrush( start, end );
			Log.infoln( "size=" + asset.prices.size() );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void testFuture()
	{
		try
		{		
//			Asset asset = new Future( "Crude_Oil_WTI_Futures/CLX12" );
			Asset asset = new Future( "ZCZ12" );

			String endParm = null;	//"2010-11-04";
    		String yearsParm = null;
    		String monthsParm = "12";
    		String daysParm = null;
    		
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );	
			Calendar end = parseDate( endParm, format );
			Calendar start = getStart( end, yearsParm, monthsParm, daysParm );	

//			Log.infoln( "start=" + format.format( start.getTime() ) );
//			Log.infoln( "end=" + format.format( end.getTime() ) );
			
			asset.loadPrices(start, end);			

//			Log.infoln( asset.getXml() );

			Asset.debug = false;
			asset.doCrush( start, end );				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
    public String getXml() throws RepresentationException
    {
    	String ticker = getResourceId();
        if ( ticker == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
    		String endParm = getRequest().getParameter( "end" );
    		String yearsParm = getRequest().getParameter( "years" );
    		String monthsParm = getRequest().getParameter( "months" );
    		String daysParm = getRequest().getParameter( "days" );
    		String startParm = getRequest().getParameter( "start" );
//    		Log.outln( "yearsParm=" + yearsParm );
    		
			Element root = null;

			/*
			CrushRecord record = null;
    		if(yearsParm != null && yearsParm.equals("1"))
    		{
				DatabaseManager dbm = CrusherProject.getDatabaseManager();
				String select = "from CrushRecord where ticker='" + ticker + "'";				
				List<CrushRecord> list = dbm.selectRecords( select );
//				dbm.reportRecords(list);
				if( list == null || list.size() < 1 ) { record = new CrushRecord(); }
				else if( list.size() > 1 ) { throw new Exception("Duplicate CrushRecord ticker=" + ticker); }
				else { record = list.get(0); }
    		}
    		
    		if( record != null)
    		{
    			root = record.getElement();
    		}
    		else
    		*/
    		{
				root = new Element( representation );
		   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
				root.addContent( new Element( "ticker" ).addContent( new CDATA( getResourceId() ) ) );
	//			root.addContent( new Element( "url" ).addContent( theme.getUrl() ) );
	
				/*
				Calendar end = new GregorianCalendar();			
	//			end.set( 2010, 3, 29 );
				Calendar start = new GregorianCalendar();
				start.set( end.get(Calendar.YEAR)-1, end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH) );
				*/
		
				DateFormat format = Asset.defaultDateFormat;	
				Calendar end = parseDate( endParm, format );
				Calendar start = null;
				if( startParm != null )
				{
					start = parseDate( startParm, format );
				}
				else
				{
					start = getStart( end, yearsParm, monthsParm, daysParm );
//					Log.infoln( "start=" + format.format( start.getTime() ) );
				}
				
				Asset asset = Asset.instance( ticker );

				root.addContent( new Element( "chartUrl" ).addContent( new CDATA( asset.chartUrl() ) ) );
				root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
				root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );
				
				Asset.debug = false;
				/*
				if ( asset.doCrush( start, end ) )
				{					
					root.addContent( new Element( "crush10" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[0] ) ) ) );
					root.addContent( new Element( "crush20" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[1] ) ) ) );
					root.addContent( new Element( "crush30" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[2] ) ) ) );			
					root.addContent( new Element( "close" ).addContent( new CDATA( getSeriesValues( asset.nClose ) ) ) );
					root.addContent( new Element( "flow" ).addContent( new CDATA( getSeriesValues( asset.nVol ) ) ) );
	//				root.addContent( new Element( "peel" ).addContent( new CDATA( getSeriesValues( asset.nsdLong) ) ) );
	//				root.addContent( new Element( "jam" ).addContent( new CDATA( getSeriesValues( asset.nsdMedium ) ) ) );
					root.addContent( new Element( "plate" ).addContent( new CDATA( getSeriesValues( asset.plate ) ) ) );
	//				root.addContent( new Element( "press" ).addContent( new CDATA( getSeriesValues( asset.nsdShort ) ) ) );
					root.addContent( new Element( "pear" ).addContent( new CDATA( getSeriesValues( asset.crushGradient ) ) ) );
					root.addContent( new Element( "crush" ).addContent( new CDATA( getSeriesValues( asset.crushSeries ) ) ) );
					root.addContent( new Element( "cherryWidth" ).addContent( new CDATA( "" + asset.cherryWidth ) ) );
					root.addContent( new Element( "cherryColor" ).addContent( new CDATA( "" + asset.getCherryColor() ) ) );
					root.addContent( new Element( "volume" ).addContent( new CDATA( getVolSeriesValues( asset.volumeSeries ) ) ) );					
				}	
				else
				{
					root.addContent( new Element( "error" ).addContent( new CDATA( "Error: Data not found for " + ticker ) ) );
				}
				*/
				
				BarchartQuote quote = downloadDailyQuote( ticker );
				root.addContent( new Element( "symname" ).addContent( quote.symname ) );
				root.addContent( new Element( "last" ).addContent( quote.last ) );
				root.addContent( new Element( "up" ).addContent( quote.up ) );
				root.addContent( new Element( "down" ).addContent( quote.down ) );
				root.addContent( new Element( "grey" ).addContent( quote.grey ) );
				root.addContent( new Element( "dailyQuoteHtm" ).addContent( quote.dailyHtm ) );
    		}
			
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    public String getJson() throws RepresentationException
    {
    	String ticker = getResourceId();
        if ( ticker == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
		
    	try
    	{
    		String endParm = getRequest().getParameter( "end" );
    		String yearsParm = getRequest().getParameter( "years" );
    		String monthsParm = getRequest().getParameter( "months" );
    		String daysParm = getRequest().getParameter( "days" );
    		String startParm = getRequest().getParameter( "start" );
    		String fillParm = getRequest().getParameter( "fill" );
			boolean fill = true; if ( fillParm != null ) { try { fill = Boolean.parseBoolean(getRequest().getParameter("fill")); } catch (Exception e) {} }

			DateFormat format = Asset.defaultDateFormat;	
			Calendar end = parseDate( endParm, format );
			Calendar start = null;
			if( startParm != null )
			{
				start = parseDate( startParm, format );
			}
			else
			{
				start = getStart( end, yearsParm, monthsParm, daysParm );
//				Log.infoln( "start=" + format.format( start.getTime() ) );
			}

    		String json = "[";
    		
			Asset asset = Asset.instance( ticker );
			Asset.debug = false;
			if ( asset.doCrush( start, end ) ) {

    			json += "{\"title\": { " +
							"\"ticker\": \"" + asset.getTicker() + "\"," +
							"\"useHTML\": true," +
        					"\"text\": \"<span id='populate1'>" + asset.getTicker() + "</span>\"," + 
        					"\"style\": { \"color\": \"#" + Asset.getCherryColor(asset.cherryWidth) + "\" }" +                 
        				"}," +
						"\"crushVal\": " + asset.crushSeries[asset.crushSeries.length-1] * 100 + "," +
						"\"pearVal\": " + asset.crushGradient[asset.crushGradient.length-1] * 100 + "," +
        				"\"series\": [" +
            				"{\"name\": \"crush\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.crushSeries ) + "]}," + 	            				
            				"{\"name\": \"close\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.nClose ) + "]}," + 	            				
            				"{\"name\": \"flow\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.nVol ) + "]},";

    			if ( fill ) {
					json += "{\"name\": \"plate\"," +
							"\"data\": [" + AssetRepresentation.getSeriesValues(asset.plate) + "]},";
				}

    			json += "{\"name\": \"porter\"," +
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.crushGradient ) + "]}";
				json += "]},";
				
    			json += "{\"series\": [" + 
		        			"{\"name\": \"volume\"," + 
	        				"\"data\": [" + AssetRepresentation.getVolSeriesValues( asset.volumeSeries ) + "]}";
				json += "]}";

				double[] prices = asset.parsePrices( 5 );
				json += ",{\"series\": [" +
						"{\"name\": \"price\"," +
						"\"data\": [" + AssetRepresentation.getSeriesValues( prices ) + "]}";
				json += "]}";

				//String[] dates = asset.parseStringsFromPrices( 0 );
				json += ",{\"series\": [" +
						"{\"name\": \"date\"," +
						"\"data\": [" + AssetRepresentation.getSeriesValues( asset.dateSeries ) + "]}";
				json += "]}";
			}

			json += "]";			
			return json;
    	}
	    catch( Exception e ) 
	    { 
	    	throw new RepresentationException( Util.getStackTrace( e ) ); 
	    }    	
    }
    
}
