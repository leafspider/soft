package scenario.more;

import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class SpeakerListIBC extends ExhibitorList
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{	
			ExhibitorList exlist = new SpeakerListIBC();
			exlist.setEventName("IBC 2013");			
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
		int rnd = (int) Math.floor(Math.random() * 999);
		return "http://www.ibc.org/page.cfm/Action=VisitorList/ListID=2/PageNum=" + pageNum + "/loadSearch=577058_" + rnd;
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
