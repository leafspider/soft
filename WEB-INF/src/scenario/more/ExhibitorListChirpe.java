package scenario.more;

import java.util.Iterator;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExhibitorListChirpe extends ExhibitorList
{
	public static void main ( String[] args )
	{
		try
		{			
			/*
			http://chirpe.com/ExhibitorList.aspx?EVENTID=1484&SORTBY=2&DISPLAYMODE=1&DISPLAY=0&SUBPRODCATID=0&PAGE=1&SEARCH=False&KEYWORD=
            */
         
			ExhibitorList vendorList = new ExhibitorListChirpe();
			vendorList.setEventId("1484");			
			vendorList.setEventName("Fabtech 2012");			
			vendorList.setPageNum(13);
			vendorList.populate();
			
			Iterator links = vendorList.getLinks().iterator();
			while(links.hasNext())
			{
				String link = (String) links.next();
				System.out.println("" + link);
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public String listUrl()
	{
		return "http://chirpe.com/ExhibitorList.aspx?EVENTID=" + eventId + "&SORTBY=2&DISPLAYMODE=1&DISPLAY=0&SUBPRODCATID=0&PAGE=" + pageNum;
	}
	
	public void populate() throws Exception
	{

		/*
		 <a href='ExhibitorHome.aspx?BoothID=120050&EventID=1484'>
         	<span id="ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorList_rptExhibitorList_ctl02_lblMore">More...</span>
         </a>
		 */
//		String url = "http://finance.yahoo.com/q?s=" + ticker.toLowerCase();
//		String qprice = "span[id=yfs_l84_" + ticker.toLowerCase() + "]";
//		String qlink = "span.deal_cityname:has(span.deal_available)";
		String qlink = "a[href~=(ExhibitorHome)\\.(aspx)\\?(BoothID=)(?i)]";

		Document doc = Jsoup.connect( listUrl() ).get();
		Iterator els = doc.select( qlink ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
//			Log.infoln("href=" + el.attr("href"));
		}
	}
}
