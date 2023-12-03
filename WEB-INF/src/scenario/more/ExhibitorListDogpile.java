package scenario.more;

import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class ExhibitorListDogpile extends ExhibitorList
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{	
			ExhibitorList exlist = new ExhibitorListDogpile();
			exlist.setEventName("IBC 2012");
			
			for(int i=0; i<1500; i++)
			{
				if(i==0 || i%10 == 0)
				{
					exlist.setPageNum(i+1);
					exlist.populate();
					Log.infoln("i=" + i);
				}
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
//		return "http://www.dogpile.com/search/web?fcoid=485&fcop=&fpid=27&qall=&qexact=stand+number&qany=&qnone=&qimgc=All&qimgs=All&qlang=en&qdomainf=Include&qdomain=ibc.org";
//		return "http://www.dogpile.com/search/web?qsi=11&qexact=stand%20number&qlang=en&qdomainf=Include&qdomain=ibc.org&fcoid=4&fcop=results-bottom&fpid=" + pageNum;
		return "http://www.dogpile.com/search/web?qsi=" + pageNum + "&qexact=stand%20number&qlang=en&qdomainf=Include&qdomain=ibc.org&fcoid=4&fcop=results-bottom&fpid=2";
	}
	
	public void populate() throws Exception
	{
		String qdetails = "div[class=resultDescription]";

		Document doc = Jsoup.connect( listUrl() ).get();
		Iterator els = doc.select( qdetails ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();			
//			links.add(el.attr("href"));
			Log.infoln("" + el.html().replaceAll("\n", ""));
		}
	}
}
