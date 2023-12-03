package soft.assetlist;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import soft.asset.Asset;
import leafspider.util.Downloader;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public abstract class AlistVariable extends Alist
{			
	public String qtitle = null;
	public String qticker = null;

	@Override
	public String getListFolder()
	{
		return "variable";
	}

	public static Alist instance(String listId) throws Exception
	{
		AlistVariable alist = null;
		
		/*
		if( url.indexOf( "finviz.com" ) > -1 ) { list = new AlistFinviz(); }
		else if( url.indexOf( "biz.yahoo.com/research/earncal" ) > -1 ) { list = new AlistEarnings(); }		
		else if( url.indexOf( "old.barchart.com/stocks/percentdecline" ) > -1 ) { list = new AlistPercentDecline(); }
		else if( url.indexOf( "old.barchart.com/stocks/percentadvance" ) > -1 ) { list = new AlistPercentAdvance(); }
		*/
		
		/*
    	if ( listId.equals( "rangeadvance" ) )
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
    		alist = new AlistPercentAdvance(); 
    		alist.url = "http://old.barchart.com/stocks/percentadvance.php";
    	}
    	else if ( listId.equals( "percentdecline" ) )
    	{
    		alist = new AlistPercentDecline(); 
    		alist.url = "http://old.barchart.com/stocks/percentdecline.php";
    	}
    	else if ( listId.equals( "finviz" ) )
    	{
    		alist = new AlistFinviz(); 
    		alist.url = "http://finviz.com";
    	}
    	else if ( listId.equals("earnings_today"))
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
			
//				Log.infoln("url=" + url);
//	    		url = null;	//"http://biz.yahoo.com/research/earncal/today.html";
    	}
    	*/
    	/*
    	else if ( listId.equals("etfmonitor"))
    	{
    		alist = new AlistEtfMonitor(); 
    		alist.url = "http://old.barchart.com/etf/monitor.php";
    	}
    	*/
    	
    	/*
    	else if ( listId.indexOf( "insider_" ) == 0 )
    	{
    		alist = new AlistInsider(); 
    		int page = 0;
    		if(listId.equals("insider_1")) { page = 1; }
    		if(listId.equals("insider_2")) { page = 2; }
//    		alist.url = "http://www.gurufocus.com/modules/insiders/InsiderBuy_ajax.php?p=" + page;
//    		alist.url = "http://www.gurufocus.com/InsiderBuy.php";
    		alist.url = "http://www.gurufocus.com/rssInsider.php";
    	} 
    	*/   	
    	
    	if(alist != null) 
    	{ 
    		alist.init();
    		alist.setId(listId);
    	}
    	
		return alist;
	}

}
