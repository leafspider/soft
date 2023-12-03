package soft.assetlist;

import java.io.File;
import java.io.FileInputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import leafspider.db.DatabaseManager;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AlistBatch extends AlistFixed
{	
	@Override
	public String getListFolder()
	{
		return "batch";
	}

	public static Alist instance(String listId) throws Exception {

		AlistBatch alist = new AlistBatch();

    	if ( listId.indexOf( "trout" ) == 0 ) { alist = new AlistInsider(); }
    	/*
		if ( listId.indexOf( "insider" ) == 0 ) { alist = new AlistInsider(); }
		else if ( listId.equals( "finviz" ) ) { alist = new AlistFinviz(); }
    	else if ( listId.equals( "etfmonitor" ) ) { alist = new AlistEtfMonitor(); }
    	else if ( listId.equals( "allsnaps" ) ) { alist = new AlistSnapshots(); }
//    	else if ( listId.equals( "allfuts" ) ) { alist = new AlistFutures(); }
    	else if ( listId.equals( "rangeadvance" ) ) 
    	{ 
    		alist = new AlistBarcharts();
    		alist.url = "http://old.barcharts.net/stocks/rangeadvance.php";
    	}
    	else if ( listId.equals( "gapup" ) ) 
    	{ 
    		alist = new AlistBarcharts();
    		alist.url = "http://old.barcharts.net/stocks/gapup.php";
    	}
    	else if ( listId.equals( "gapdown" ) ) 
    	{ 
    		alist = new AlistBarcharts(); 
    		alist.url = "http://old.barcharts.net/stocks/gapdown.php";
    	}
    	else if ( listId.equals( "percentadvance" ) ) 
    	{ 
    		alist = new AlistPercentAdvance();	// TODO Refactor
			alist.url = "http://old.barchart.com/stocks/percentadvance.php";
    	}
    	else if ( listId.equals( "percentdecline" ) ) 
    	{ 
    		alist = new AlistPercentDecline();
    		alist.url = "http://old.barchart.com/stocks/percentdecline.php";
    	}    	
    	else if ( listId.equals( "earnings_today")) 
    	{ 
    		alist = new AlistEarnings(); 
    		alist.url = "http://biz.yahoo.com/research/earncal/today.html";
    	}	
    	else if ( listId.indexOf( "earnings_" ) == 0 )
    	{	
    		Calendar calendar = new GregorianCalendar();
			int today = calendar.get(Calendar.DAY_OF_WEEK);
			
			// Shift weekend to following Monday
			if (today == 7) { calendar.add(Calendar.DAY_OF_MONTH,2); }	// Sat				
			if (today == 1) { calendar.add(Calendar.DAY_OF_MONTH,1); }	// Sun
			today = calendar.get(Calendar.DAY_OF_WEEK);
			
//				Log.infoln("today=" + today);
//				Log.infoln("today=" + df.format(calendar.getTime()));
			
			int dow = 2;	// earnings_mon
    		if(listId.equals("earnings_tue")) { dow = 3; }
    		if(listId.equals("earnings_wed")) { dow = 4; }
    		if(listId.equals("earnings_thu")) { dow = 5; }
    		if(listId.equals("earnings_fri")) { dow = 6; }
    		
    		int diff = dow - today;
			calendar.add(Calendar.DAY_OF_MONTH, diff);
//				SimpleDateFormat df = new SimpleDateFormat();
//				Log.infoln("target=" + df.format(calendar.getTime()));

			DateFormat format = new SimpleDateFormat( "yyyyMMdd" );	
			
    		alist = new AlistEarnings(); 
			alist.url = "http://biz.yahoo.com/research/earncal/" + format.format(calendar.getTime()) + ".html";
    	}
    	*/
    	else
    	{ 
    		alist = new AlistBarcharts(); 
    		alist.setId(listId);
    		try
    		{
	    		if( alist.getProperty("url") == null ) 
	    		{ 
	    			if( alist.getProperty("tickerlist") != null ) {
	    				alist.setTickerList(alist.getProperty("tickerlist"));
	    			}
	    			else {
	    				String tickerlist = "";
						Enumeration keys = alist.getProperties().propertyNames();
						while( keys.hasMoreElements() ) {

							String key = (String) keys.nextElement();
							if (key.equals("id") || key.equals("title") || key.equals("hierarchy") || key.equals("lag")) {
								// ignore
							}
							else {
								String ticker = key;
								tickerlist += ticker + " ";
							}
						}
						alist.setTickerList( tickerlist.trim() );
					}
	    		}
	    		else { alist.url = alist.getProperty("url"); }
    		}
    		catch( Exception e ) { return null; }
//    			alist.url = "http://old.barchart.com/stocks/sp100.php?components=1&_dtp1=0";
    	}
    	
		alist.setId(listId);
		alist.init(); 

		if( alist.getPropertiesFile().exists() )
		{
//			Log.infoln("props=" + alist.getPropertiesFile().getAbsolutePath() );
			return alist; 
		}
		return null;			
	}

	public static Alist instance(String title, String hierarchy, String tickerlist) throws Exception {

		AlistBarcharts alist = new AlistBarcharts();
		alist.setTitle(title);
		alist.setTickerList( tickerlist.trim() );
		alist.setHierarchy(hierarchy);
		alist.init();
		return alist;
	}

}
