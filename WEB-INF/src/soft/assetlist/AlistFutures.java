package soft.assetlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import leafspider.util.LoginContentDownloader;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class AlistFutures extends AlistVariableBatch
{		
	public String url = "http://old.barchart.com/commodityfutures/All";
	
	public void populate() throws Exception
	{
		tickers = new ArrayList<String>();

		Document doc = Jsoup.connect( url ).get();

//			Elements headers = doc.select( ".bar h2:eq(0) a" );
		Elements tables = doc.select( ".datatable" );
		
		int tableNum = 0;
		Iterator tablesit = tables.iterator();			
		while(tablesit.hasNext())
		{
			Element table = (Element) tablesit.next();				
//				Elements anchors = table.select( "a[title~=(Detailed).?]" );

			int rowNum = 0;
			Elements rows = table.select( "tr" );
			
//					Log.infoln("cells.size=" + cells.size() );
			Iterator rowsit = rows.iterator();
			while( rowsit.hasNext())
			{
				Element row = (Element) rowsit.next();					
				Element secondCell = row.select( "td:eq(1)" ).first();
				if( secondCell == null ) { continue; }
				
				String ticker = secondCell.text();
				int pos = ticker.indexOf(" ");
				ticker = ticker.substring(0,pos);

				if( !tickers.contains(ticker)) 
				{ 
					tickers.add( ticker ); 
//						Log.infoln("ticker=" + ticker );
				}
			}
		}
	}

}
