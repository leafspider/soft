package soft.assetlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import leafspider.util.Log;
import leafspider.util.Util;

import org.jdom2.CDATA;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import soft.asset.Asset;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;

public class AlistSnapshots extends AlistVariableBatch
{			
	public void populate() throws Exception
	{
		tickers = new ArrayList<String>();
   		try
   		{
			Calendar end = new GregorianCalendar();			
//			end.set( 2010, 3, 29 );
			Calendar start = new GregorianCalendar();
			start.set( end.get(Calendar.YEAR)-1, end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH) );		
//			String fmdStart = Asset.defaultDateFormat.format(start.getTime());	
			
			List records = CrushProject.getDatabaseManager().listRecords("CrushRecord", "id desc");
//			List records = CrushProject.getDatabaseManager().listFilteredRecords("CrushRecord", "watched", "true", "id desc");	// Get watched only to avoid null id exception
			/*
			String select = "from CrushRecord where startDate > '" + fmdStart + "' order by id desc";
			List records = null;
			try
			{
				records = CrushProject.getDatabaseManager().selectRecords( select );
			}
			catch( Exception e) { e.printStackTrace(); } 
			*/
		
			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				String ticker = crush.getTicker();
				
				DateFormat format = Asset.defaultDateFormat;
				Date date = format.parse( crush.getEndDate() );
				GregorianCalendar snapshotDate = new GregorianCalendar();
				snapshotDate.setTime( date );
				
				/* Original
				if( snapshotDate.after( start ) )
				{
				*/
					if( !tickers.contains(ticker)) 
					{ 
						tickers.add( ticker ); 
	//					Log.infoln("ticker=" + ticker);
					}				
				/*
				}
				*/
				
			}
   		}
   		catch( Exception e) 
   		{
   			Log.infoln( e );
   		}
	}
}
