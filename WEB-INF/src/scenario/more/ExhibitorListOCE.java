package scenario.more;

import java.io.File;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import scenario.more.Exhibitor;
import scenario.more.ExhibitorList;

public class ExhibitorListOCE extends ExhibitorList
{	
	public static void main ( String[] args )
	{
		test1();
	}
	
	public static void test1()
	{
		try
		{	
			Log.debug = true;
			Log.report = Util.getPrintStream("C:\\Workspace\\Ultra\\Nick\\oce\\exhibitor2_log.csv");

			ExhibitorList exlist = new ExhibitorListOCE();
			exlist.setEventName("OCE");
			
			exlist.populate();
	
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
		return "http://www.ocediscovery.com/exhibitors";
	}
	
	public void populate() throws Exception
	{
		String qlink = "tr td:eq(1)";

//		Document doc = Jsoup.connect( listUrl() ).get();
		Connection conn = Jsoup.connect( listUrl() );
		conn.timeout(10000);
		Document doc = conn.get();		
		
//		Log.infoln( doc.text() );
		
		Iterator els = doc.select( qlink ).iterator();

//		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
//			links.add(el.attr("href"));
			
			ExhibitorOCE ex = new ExhibitorOCE();
			
			try { ex.setCategory("Exhibitor"); } catch(Exception e1) {}
			try { ex.setName(el.select("div a").text()); } catch(Exception e1) {}
//			try { ex.setBooth(doc.select( "div[class=ez_entrystand] table tr td" ).last().text()); } catch(Exception e1) {}
			try { ex.setWebsite(el.select("div a").attr("href")); } catch(Exception e1) {}
			try { ex.setDescription(el.text()); } catch(Exception e1) {}

//			Log.infoln("\"" + ex.name + "\",\"" + ex.booth + "\",,\"" + ex.website + "\",,,,\"" + ex.description + "\"" );
			Log.infoln("\"" + ex.name + "\",\"" + ex.booth + "\",\"" + ex.website + "\",\"" + ex.description + "\"" );
		}
	}
}
