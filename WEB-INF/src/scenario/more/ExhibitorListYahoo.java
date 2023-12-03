package scenario.more;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExhibitorListYahoo extends ExhibitorList
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{	
			ExhibitorListYahoo exlist = new ExhibitorListYahoo();
			exlist.setEventName("IBC 2012");
			
			Iterator list = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\Query_booths_3.txt").iterator();
//			for(int i=0; i<20; i++)
			int n = 0;
			while(list.hasNext())
			{			
				n++;
				exlist.setPageText("" + list.next());
//				if( n < 200) { continue; }
				try { exlist.populate(); }
				catch(Exception e) {}
//				if( n > 5 ) { break; }
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
//		return "http://ca.search.yahoo.com/search?n=" + pageNum + "0&ei=UTF-8&va_vt=any&vo_vt=any&ve_vt=any&vp_vt=any&vf=all&vm=p&fl=0&p=%22stand+number%22&vs=ibc.org";
//		return "http://ca.search.yahoo.com/search;_ylt=A0geu8gKxypQPz4AjnHrFAx.?p=%22stand+number%22&pvid=VQda1EoG71QKE2wCTjYx5QTHzOF6BFAqxvkAAsmu&n=100&vs=ibc.org&vf=all&vm=p&rd=r1&fr2=sb-top&xargs=0&pstart=1&b=101";
		return "http://ca.search.yahoo.com/search;_ylt=A0geu8gKMytQOTYA49vrFAx.?p=" + pageText + "+site%3Aibc.org&fr2=sb-bot&fr=yfp-t-715";
	}
	
	public void populate() throws Exception
	{
		String qdetails = "div[class=abstr]";

		Document doc = Jsoup.parse( new URL(listUrl()), 5000 );
		Iterator els = doc.select( qdetails ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();			
//			links.add(el.attr("href"));
			if(el.html().indexOf("Exhibitor:") < 0 ) { break; }
			Log.infoln("" + el.html().replaceAll("\n", ""));
		}
	}
	
	private String pageText = null;
	public String getPageText() {
		return pageText;
	}
	public void setPageText(String pageTxt) {
		this.pageText = pageTxt;
	}
}
