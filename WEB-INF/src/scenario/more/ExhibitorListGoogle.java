package scenario.more;

import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExhibitorListGoogle extends ExhibitorList
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{	
			ExhibitorList exlist = new ExhibitorListGoogle();
			exlist.setEventName("IBC 2012");
			
			for(int i=0; i<60; i++)
			{
				exlist.setPageNum(i+1);
				exlist.populate();
			}
	
			/*
			Log.infoln("id=" + exlist.getEventId());

			Iterator links = exlist.getLinks().iterator();
			while(links.hasNext())
			{
				String link = (String) links.next();
				System.out.println("" + link);
			}
			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public String listUrl()
	{
		return "http://www.google.ca/search?hl=en&as_q=&as_epq=stand+number&as_oq=&as_eq=&as_nlo=&as_nhi=&lr=&cr=&as_qdr=all&as_sitesearch=ibc.org&as_occt=any&safe=off&tbs=&as_filetype=&as_rights=";
	}

	public File getFile()
	{
		return new File("C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\Google\\" + pageNum + "stand number  site ibc.org - Google Search.htm");
	}

	public void populate() throws Exception
	{
		String qdetails = "span[class=pplsrsl]";

//		Document doc = Jsoup.connect( listUrl() ).get();
		Document doc = Jsoup.parse(getFile(), "UTF-8");
		Iterator els = doc.select( qdetails ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();			
//			links.add(el.attr("href"));
//			Log.infoln("" + el.html().replaceAll("\n", ""));
			Log.infoln("" + el.attr("data-desc").replaceAll("\n", ""));			
		}
	}
}
