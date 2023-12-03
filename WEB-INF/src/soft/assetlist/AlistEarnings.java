package soft.assetlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import leafspider.tagger.PosTagger;
import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;


public class AlistEarnings extends AlistVariableBatch
{	
	/*
	public void init()
	{
		qtitle = "form.yfi_biz_form table tr td font";
		qticker = "a";
	}
	*/

	public void populate() throws Exception
	{
		String qtitle = "form.yfi_biz_form table tr td font";
		String qticker = "a";
		
		tickers = new ArrayList<String>();

		Document doc = null;
		if( url != null ) { doc = Jsoup.connect( url ).get(); }
		else if( file != null && file.exists() ) { doc = Jsoup.parse(file, null); }

		setTitle( doc.select( qtitle ).iterator().next().text() );
		setId(getTitle());
		
//		System.out.println( "title=" + title ); 
//		System.out.println( "id=" + id ); 

		Iterator elements = doc.select( qticker ).iterator();

		while(elements.hasNext()) 
		{
			Element el = (Element) elements.next();
			String href = el.attr("href");
			if( href.indexOf("q?s=") > 0)
			{
				int pos = href.indexOf("q?s=");
				String ticker = href.substring(pos+4,href.length());
				tickers.add( ticker );
//				System.out.println( "ticker=" + ticker);
			}
		}
	}

    public void obsetId(String title)
    {
    	id = Util.getCleanFileName(title);
		id = id.replace("_","");
		id = id.replace("US","");
		id = id.replace("Calendar","");
		id = id.replace("for","");
    }
}
