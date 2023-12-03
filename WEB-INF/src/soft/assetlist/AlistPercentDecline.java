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


public class AlistPercentDecline extends AlistVariableBatch
{
	public String url = "http://old.barchart.com/stocks/percentdecline.php";

	public void populate() throws Exception
	{
//		String qtitle = "form.yfi_biz_form table tr td font";
		String qticker = "table.datatable tr td:eq(0) a";

		tickers = new ArrayList<String>();

		Document doc = null;
		if( url != null ) { doc = Jsoup.connect( url ).get(); }
		else if( file != null && file.exists() ) { doc = Jsoup.parse(file, null); }

		Calendar calendar = new GregorianCalendar();
		DateFormat format = new SimpleDateFormat( "yyyyMMdd_HHmmss" );	

		setTitle( "Percent Decline " + format.format(calendar.getTime()) );
		setId(getTitle());
		
		Iterator elements = doc.select( qticker ).iterator();

		while(elements.hasNext()) 
		{
			Element el = (Element) elements.next();
			String val = el.text();
//			System.out.println( "val=" + val);
			String ticker = val;
			if( !tickers.contains(ticker)) 
			{ 
				tickers.add( ticker ); 
//					System.out.println( "ticker=" + ticker);
			}
		}
	}
}
