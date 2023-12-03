package scenario.more;

import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class TwitterList extends ExhibitorList
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{	
			ExhibitorList exlist = new TwitterList();
			exlist.setEventName("Bobbins");			
			exlist.setPageNum(13);
			exlist.populate();
	
//			/*
			Log.infoln("id=" + exlist.getEventId());

			Iterator links = exlist.getLinks().iterator();
			while(links.hasNext())
			{
				String link = (String) links.next();
				System.out.println("" + link);
			}
//			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public String listUrl()
	{
		return "http://twitter.com/ryoumezawa";
	}
	
	public void populate() throws Exception
	{
		String qlink = "a[class=vzone_groupcode vzone_groupcode_1]";

		Document doc = Jsoup.connect( listUrl() ).get();
		Iterator els = doc.select( qlink ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
			Log.infoln("href=" + el.attr("href"));
		}
	}
}
