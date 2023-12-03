package soft.assetlist;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import leafspider.droids.Downloader;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AlistInsider extends AlistVariableBatch
{		
	public void populate() throws Exception
	{
		tickers = new ArrayList<String>();

		Calendar calendar = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat( "yyyyMMdd_HHmmss" );	

		setTitle( "Insider Trade for " + format.format(calendar.getTime()) );
		setId(getTitle());

		// Page 1	
		url = "http://www.gurufocus.com/InsiderBuy.php";
//		File file = new File("C:\\Workspace\\Ultra\\Jake\\Insider\\out1.htm");
		File file = new File( ServerContext.getDataFolder().getAbsolutePath() + "\\insider\\buy.htm" );
		file.getParentFile().mkdir();
		String qticker = "textarea#quick_symbollist";
		
		Downloader der = new Downloader();
		der.download( url, file );		
		Document doc = Jsoup.parse(file, null);
		
		Iterator elements = doc.select( qticker ).iterator();
		while(elements.hasNext()) 
		{
			Element el = (Element) elements.next();
			String val = el.text();
//			System.out.println( "val=" + val);
			String[] vals = val.split(", ");			
			for( int i=0; i<vals.length; i++)
			{
				String ticker = vals[i];
				if( !tickers.contains(ticker)) 
				{ 
					tickers.add( ticker ); 
				}
			}
		}

		/*
		// Page 2
		url = "http://www.gurufocus.com/rssInsider.php";
		File file2 = new File("C:\\Workspace\\Ultra\\Jake\\Insider\\out2.htm");
		qticker = "link";	
		
		der.download( url, file2 );			
		doc = Jsoup.parse(file2, null);
		
		elements = doc.select( qticker ).iterator();
		while(elements.hasNext()) 
		{
			Element el = (Element) elements.next();
			String val = el.text();
			System.out.println( "val=" + val);
		}
		*/
	}
}
