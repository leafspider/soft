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

import leafspider.rest.RepresentationException;
import leafspider.stats.Statistics;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.input.SAXBuilder;



public class AssetSnapshot extends AssetRepresentation
{	
	public static String representation = "snapshot";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
//    	Log.infoln("GET snapshot");
    	
    	String ticker = getResourceId();
        if ( ticker == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
    		String endParm = getRequest().getParameter( "end" );
    		String yearsParm = getRequest().getParameter( "years" );
    		String monthsParm = getRequest().getParameter( "months" );
    		String daysParm = getRequest().getParameter( "days" );
    		String startParm = getRequest().getParameter( "start" );
    		String targetPrice = getRequest().getParameter( "targetPrice" );
    		String strategy = getRequest().getParameter( "strategy" ); 
//    		String tradeTime = getRequest().getParameter( "tradeTime" ); 
    		String exit = getRequest().getParameter( "exit" ); 
    		String pal = getRequest().getParameter( "pal" ); 
    		String amount = getRequest().getParameter( "amount" ); 
    		String commission = getRequest().getParameter( "commission" ); 
    		String exit5 = getRequest().getParameter( "exit5" ); 
    		String pal5 = getRequest().getParameter( "pal5" ); 
    		String exit30 = getRequest().getParameter( "exit30" ); 
    		String pal30 = getRequest().getParameter( "pal30" ); 
    		String portfolio = getRequest().getParameter( "portfolio" ); 
    		String comment = getRequest().getParameter( "comment" ); 
    		String watched = getRequest().getParameter( "watched" ); 
    		String entry = getRequest().getParameter( "entry" ); 
    		String instrument = getRequest().getParameter( "instrument" ); 
//    		Log.infoln( "amount=" + amount );
    		
			Element root = new Element( representation );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "ticker" ).addContent( new CDATA( getResourceId() ) ) );

			Asset asset = Asset.instance( ticker );
			Asset.debug = false;

			DateFormat format = asset.defaultDateFormat;	
			Calendar end = parseDate( endParm, format );
			Calendar start = null;
			if( startParm != null ) { start = parseDate( startParm, format ); }
			else { start = getStart( end, yearsParm, monthsParm, daysParm ); }

			if( asset.doCrush( start, end ) ) {

				CrushRecord record = null;			
	
				String fmdStart = asset.defaultDateFormat.format(start.getTime());
				String fmdEnd = asset.defaultDateFormat.format(end.getTime());
	
				String select = "from CrushRecord where ticker='" + ticker + "' and startDate='" + fmdStart + "' and endDate='" + fmdEnd + "'";
				List list = null;
				try {
					list = CrushProject.getDatabaseManager().selectRecords( select );
				}
				catch( Exception e) { e.printStackTrace(); } 
	
				if( list == null || list.size() < 1 )
				{
					record = new CrushRecord();
					record.setTicker( ticker );
					record.setStartDate( fmdStart );
					record.setEndDate( fmdEnd );
					record.setClose( asset.nClose[asset.prices.size()-1] );
					record.setFlow( asset.nVol[asset.prices.size()-1] );
					record.setPeel( asset.nsdLong[asset.prices.size()-1] );
					record.setJam( asset.nsdMedium[asset.prices.size()-1] );
					record.setPress( asset.nsdShort[asset.prices.size()-1] );
					record.setCrush( asset.crushSeries[asset.prices.size()-1] );				
					record.setPlate( asset.plate[asset.prices.size()-1] );
					record.setPear( asset.crushGradient[asset.crushGradient.length-1] );
					record.setCherry( asset.cherryWidth );
					record.setPortfolio(portfolio);
					record.setComment(comment);
					record.setWatched(watched);
					record.setWatched(watched);
					record.setInstrument( ticker );
				}
				else {	
					record = (CrushRecord) list.get(0);
				}
	
				if( record != null ) {
	
	//				if( exit != null && record.getExit() == null )		// jmh 2014-06-22 TODO Only for while Jake's editing snapshots
					{
			    		strategy = fieldVal( strategy, record.getStrategy() );
			    		targetPrice = fieldVal( targetPrice, record.getTargetPrice() );
//			    		tradeTime = fieldVal( tradeTime, record.getTradeTime() );
			    		exit = fieldVal( exit, record.getExit() );
			    		pal = fieldVal( pal, record.getPal() );
			    		amount = fieldVal( amount, record.getAmount() );
			    		commission = fieldVal( commission, record.getCommission() );
			    		pal5 = fieldVal( pal5, record.getPal5() );
			    		pal30 = fieldVal( pal30, record.getPal30() );
			    		exit5 = fieldVal( exit5, record.getExit5() );
			    		exit30 = fieldVal( exit30, record.getExit30() );
			    		portfolio = fieldVal( portfolio, record.getPortfolio() );
			    		comment = fieldVal( comment, record.getComment() );
			    		watched = fieldVal( watched, record.getWatched() );
			    		entry = fieldVal( entry, record.getEntry() );
			    		instrument = fieldVal( instrument, record.getInstrument() );
					}
					
					record.setStrategy(strategy);
					record.setTargetPrice(targetPrice);
//					record.setTradeTime( tradeTime );
					record.setExit( exit );
					record.setPal( pal );
					record.setAmount( amount );
					record.setCommission( commission );
					record.setExit5( exit5 );
					record.setPal5( pal5 );
					record.setExit30( exit30 );
					record.setPal30( pal30 );
					record.setPortfolio(portfolio);
					record.setComment(comment);
					record.setWatched(watched);
					record.setEntry(entry);
					record.setInstrument(instrument);
	
					record.saveOrUpdateAndCommit();			
	
	//	    		Log.infoln( "amount=" + record.getAmount() );
		    		
					root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
					root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );
					
					root.addContent( new Element( "crush10" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[0] ) ) ) );
					root.addContent( new Element( "crush20" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[1] ) ) ) );
					root.addContent( new Element( "crush30" ).addContent( new CDATA( "" + decimalFormat.format( asset.crushes[2] ) ) ) );		
					root.addContent( new Element( "close" ).addContent( new CDATA( getSeriesValues( asset.nClose ) ) ) );
					root.addContent( new Element( "flow" ).addContent( new CDATA( getSeriesValues( asset.nVol ) ) ) );
					root.addContent( new Element( "plate" ).addContent( new CDATA( getSeriesValues( asset.plate ) ) ) );
					root.addContent( new Element( "pear" ).addContent( new CDATA( getSeriesValues( asset.crushGradient ) ) ) );
					root.addContent( new Element( "crush" ).addContent( new CDATA( getSeriesValues( asset.crushSeries ) ) ) );
					root.addContent( new Element( "cherryWidth" ).addContent( new CDATA( "" + asset.cherryWidth ) ) );
					root.addContent( new Element( "cherryColor" ).addContent( new CDATA( "" + asset.getCherryColor() ) ) );
					root.addContent( new Element( "crushVal" ).addContent( new CDATA( "" + asset.crushSeries[asset.crushSeries.length-1] * 100 ) ) );
					root.addContent( new Element( "plateVal" ).addContent( new CDATA( "" + asset.plate[asset.plate.length-1] * 100 ) ) );
					root.addContent( new Element( "pearVal" ).addContent( new CDATA( "" + asset.crushGradient[asset.crushGradient.length-1] * 100 ) ) );
					root.addContent( new Element( "cherryVal" ).addContent( new CDATA( "" + asset.cherryWidth * 100 ) ) );
																		
					root.addContent( new Element( "targetPrice" ).addContent( new CDATA( "" + record.getTargetPrice() ) ) );
					root.addContent( new Element( "strategy" ).addContent( new CDATA( "" + record.getStrategy() ) ) );
					root.addContent( new Element( "tradeTime" ).addContent( new CDATA( "" + asset.detailedDateFormat.format( record.getTradeTime() ) ) ) );
					root.addContent( new Element( "exit" ).addContent( new CDATA( "" + record.getExit() ) ) );
					root.addContent( new Element( "pal" ).addContent( new CDATA( "" + record.getPal() ) ) );
					root.addContent( new Element( "amount" ).addContent( new CDATA( "" + record.getAmount() ) ) );
					root.addContent( new Element( "commission" ).addContent( new CDATA( "" + record.getCommission() ) ) );
					root.addContent( new Element( "exit5" ).addContent( new CDATA( "" + record.getExit5() ) ) );
					root.addContent( new Element( "pal5" ).addContent( new CDATA( "" + record.getPal5() ) ) );
					root.addContent( new Element( "exit30" ).addContent( new CDATA( "" + record.getExit30() ) ) );
					root.addContent( new Element( "pal30" ).addContent( new CDATA( "" + record.getPal30() ) ) );
					root.addContent( new Element( "portfolio" ).addContent( new CDATA( "" + record.getPortfolio() ) ) );
					root.addContent( new Element( "comment" ).addContent( new CDATA( "" + record.getComment() ) ) );
					root.addContent( new Element( "watched" ).addContent( new CDATA( "" + record.getWatched() ) ) );
					root.addContent( new Element( "entry" ).addContent( new CDATA( "" + record.getEntry() ) ) );
					root.addContent( new Element( "instrument" ).addContent( new CDATA( "" + record.getInstrument() ) ) );

					root.addContent( new Element( "lastModified" ).addContent( new CDATA( "" + asset.detailedDateFormat.format( record.getLastModified() ) ) ) );
				}				
			}
    		else {
				root.addContent( new Element( "error" ).addContent( new CDATA( "Error: Data not found for " + ticker ) ) );
			}
			
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    
    private String fieldVal( String val, String rval )
    {
//    	Log.infoln("val=" + val + " rval=" + rval);
		if( val == null ) 
		{ 
			val = rval; 
    		if( val == null ) { val = ""; } 			    			
		}
//    	Log.infoln("val=" + val);
    	return val;
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

			DateFormat format = Asset.defaultDateFormat;	
			Calendar end = parseDate( endParm, format );
			Calendar start = null;
			if( startParm != null ) {
				start = parseDate( startParm, format );
			}
			else {
				start = getStart( end, yearsParm, monthsParm, daysParm );
//				Log.infoln( "start=" + format.format( start.getTime() ) );
			}

    		String json = "[";
    		
			Asset asset = Asset.instance( ticker );
			Asset.debug = false;
			if ( asset.doCrush( start, end ) )
			{
    			json += "{\"title\": { " +
							"\"ticker\": \"" + ticker + "\"," +
        					"\"useHTML\": true," +
        					"\"text\": \"<span id='populate1'>" + asset.getTicker() + "</span>\"," + 
        					"\"style\": { \"color\": \"#" + Asset.getCherryColor(asset.cherryWidth) + "\" }" +                 
        				"}," +        
        				"\"series\": [" +    				
            				"{\"name\": \"crush\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.crushSeries ) + "]}," + 	            				
            				"{\"name\": \"close\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.nClose ) + "]}," + 	            				
            				"{\"name\": \"flow\"," + 
            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.nVol ) + "]}";
//        					"{\"name\": \"plate\"," + 
//            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.plate ) + "]}," +	            				
//            				"{\"name\": \"pear\"," + 
//            				"\"data\": [" + AssetRepresentation.getSeriesValues( asset.crushGradient ) + "]}";
				json += "]}";
				
//				System.out.println( "asset.prices.size()=" + asset.prices.size() );
//				System.out.println( "asset.nVol.length=" + asset.nVol.length );
				
//    			end.add(Calendar.MONTH, 2);		// jmh 2014-05-08
    			end.setTimeInMillis(Util.getNow());
    			
				Collection matrix = History.loadStockPrices( asset.getTicker(), start, end );				
				asset.prices = (ArrayList) matrix;

//				System.out.println( "asset.prices.size()=" + asset.prices.size() );

//				double[] prices = asset.parsePrices( 4 );	//	jmh 2017-06-05 
				double[] prices = asset.parsePrices( 5 );
//				System.out.println( AssetRepresentation.getSeriesValues( prices ) );
    			json += ",{\"series\": [" + 
		        			"{\"name\": \"price\"," + 
	        				"\"data\": [" + AssetRepresentation.getSeriesValues( prices ) + "]}";
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
    
    public String obgetXml() throws RepresentationException
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
    		String targetPrice = getRequest().getParameter( "targetPrice" );
    		String strategy = getRequest().getParameter( "strategy" );
//    		Log.infoln( "strategy=" + strategy );
    		
			Element root = new Element( representation );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "ticker" ).addContent( new CDATA( getResourceId() ) ) );

			Asset asset = Asset.instance( ticker );
			
			DateFormat format = asset.defaultDateFormat;	
			Calendar end = parseDate( endParm, format );
			Calendar start = null;
			if( startParm != null )
			{
				start = parseDate( startParm, format );
			}
			else
			{
				start = getStart( end, yearsParm, monthsParm, daysParm );
			}

			root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
			root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );
			
			Asset.debug = false;
			if ( asset.doCrush( start, end ) )
			{
//				Log.outln( ticker + "=" + asset.crushes[0] );
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
				root.addContent( new Element( "crushVal" ).addContent( new CDATA( "" + asset.crushSeries[asset.crushSeries.length-1] * 100 ) ) );
				root.addContent( new Element( "plateVal" ).addContent( new CDATA( "" + asset.plate[asset.plate.length-1] * 100 ) ) );
				root.addContent( new Element( "pearVal" ).addContent( new CDATA( "" + asset.crushGradient[asset.crushGradient.length-1] * 100 ) ) );
				root.addContent( new Element( "cherryVal" ).addContent( new CDATA( "" + asset.cherryWidth * 100 ) ) );
/*				root.addContent( new Element( "volume" ).addContent( new CDATA( getVolSeriesValues( asset.volumeSeries ) ) ) );*/
												
				String fmdStart = asset.defaultDateFormat.format(start.getTime());
				String fmdEnd = asset.defaultDateFormat.format(end.getTime());
				
				String select = "from CrushRecord where ticker='" + ticker + "' and startDate='" + fmdStart + "' and endDate='" + fmdEnd + "'";
				List list = null;
				try
				{
					list = CrushProject.getDatabaseManager().selectRecords( select );
				}
				catch( Exception e) { e.printStackTrace(); } 
				if( list == null || list.size() < 1 )
				{
//		    		Log.infoln( "list=null" );

					CrushRecord record = new CrushRecord();
					record.setTicker( ticker );
					record.setStartDate( fmdStart );
					record.setEndDate( fmdEnd );
					record.setClose( asset.nClose[asset.prices.size()-1] );
					record.setFlow( asset.nVol[asset.prices.size()-1] );
					record.setPeel( asset.nsdLong[asset.prices.size()-1] );
					record.setJam( asset.nsdMedium[asset.prices.size()-1] );
					record.setPress( asset.nsdShort[asset.prices.size()-1] );
					record.setCrush( asset.crushSeries[asset.prices.size()-1] );				
					record.setPlate( asset.plate[asset.prices.size()-1] );
					record.setPear( asset.crushGradient[asset.crushGradient.length-1] );
//					Log.infoln( "pear=" + record.getPear());
					record.setCherry( asset.cherryWidth );
//					record.setDailyQuoteHtm( downloadDailyQuoteHtm( ticker ) );
					record.setTargetPrice(targetPrice);
					record.setStrategy(strategy);
					record.saveOrUpdateAndCommit();			
				}
				else
				{	
//		    		Log.infoln( "list!=null" );
					CrushRecord record = (CrushRecord) list.get(0);
//					Log.infoln("dailyQuote=" + record.getDailyQuoteHtm());
//					root.addContent( new Element( "dailyQuoteHtm" ).addContent( record.getDailyQuoteHtm() ) );
					targetPrice = "" + record.getTargetPrice();
					strategy = "" + record.getStrategy();					
				}
				root.addContent( new Element( "targetPrice" ).addContent( new CDATA( "" + targetPrice ) ) );
				root.addContent( new Element( "strategy" ).addContent( new CDATA( "" + strategy ) ) );		
			}	
			else
			{
				root.addContent( new Element( "error" ).addContent( new CDATA( "Error: Data not found for " + ticker ) ) );
			}
			
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
