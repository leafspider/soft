package soft.assetlist;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class AlistFinviz extends AlistVariableBatch 
{	
	/*
	public void init()
	{
//		qtitle = "form.yfi_biz_form table tr td font";
		qticker = "td a.tab-link";
	}
	*/
	
	public String url = "http://finviz.com";

	public String qticker = "td a.tab-link";

	public void populate() throws Exception
	{
		tickers = new ArrayList<String>();

		Document doc = null;
		if( url != null ) { doc = Jsoup.connect( url ).get(); }
		else if( file != null && file.exists() ) { doc = Jsoup.parse(file, null); }

//		title = doc.select( qtitle ).iterator().next().text();
		Calendar calendar = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat( "yyyyMMdd_HHmmss" );	
		
		setTitle( "Finviz " + format.format(calendar.getTime()) );
		setId(getTitle());
		
//		System.out.println( "title=" + title ); 
//		System.out.println( "id=" + id ); 

		Iterator elements = doc.select( qticker ).iterator();

		String pre = "?t=";		
		while(elements.hasNext()) 
		{
			Element el = (Element) elements.next();
			String href = el.attr("href");
			if( href.indexOf(pre) > 0)
			{
				int pos = href.indexOf(pre);
				String ticker = href.substring(pos+pre.length(),href.length());
				pos = ticker.indexOf("&");
				if (pos > -1) { ticker = ticker.substring(0,pos); }
				if( !tickers.contains(ticker)) 
				{ 
					tickers.add( ticker ); 
//					System.out.println( "ticker=" + ticker);
				}
			}
		}
	}

}
