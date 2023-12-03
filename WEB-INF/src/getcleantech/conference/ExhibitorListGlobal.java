package getcleantech.conference;

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

public class ExhibitorListGlobal extends ExhibitorList
{
	public static void main ( String[] args )
	{
		try
		{	
			Log.debug = true;

			String fname = ExhibitorListGlobal.class.getSimpleName();
			File exhibitorFile = new File( "C:\\Workspace\\Ultra\\Susan\\globalpowergen\\exhibitors_" + fname + ".csv" );
			
//			Log.report = Util.getPrintStream(listFile.getAbsolutePath());
			Log.report = Util.getPrintStream(exhibitorFile.getAbsolutePath());
			//				Log.infoln("name,description,,website,,,,address2,address1,phone,addressCity,addressState" );
			Log.infoln("Company Name,Description,Email,Website,Contact,Title,Position,Zip,Address,Phone Number,City,Country");
			
			int p = 0;
			int n = 0;
			int max = 5;
			
			for (int i=0; i<max;i++)
			{
				ExhibitorList exlist = new ExhibitorListGlobal();
				exlist.setEventName("Globalpowergen");			
				exlist.setPageNum(i);
				exlist.populate();

				System.out.println( "" );
				System.out.println( "i=" + i + " links=" + exlist.links.size() );

//				Log.infoln("id=" + exlist.getEventId() + " pageNum=" + exlist.getPageNum());
				/*
				Iterator links = exlist.getLinks().iterator();
				while(links.hasNext())
				{
					String link = (String) links.next();
					Log.infoln("" + link);
				}
				*/

//				Log.report = Util.getPrintStream(exhibitorFile.getAbsolutePath());
			
//				Iterator urls = Util.getArrayListFromFile(listFile.getAbsolutePath()).iterator();
				Iterator urls = exlist.links.iterator(); 
							
				while(urls.hasNext())
				{
					Exhibitor ex = new ExhibitorGlobal();
					ex.setExhibitorUrl("" + urls.next());
					ex.populate();

//					Log.infoln("" + ex.getWebsite());
					
//					ex.reportVals();
		//				ex.saveAndCommit();
					n++;
//					if ( n > 5) { break;}
				}
				p++;
//				if ( p > 1) { break;}
			}

			System.out.println( "" );
			System.out.println( "pages=" + p + " exhibitors=" + n );
			
//			System.out.println( "count=" + ExhibitorProject.getDatabaseManager().countRecords("Exhibitor") );

			/*
			Iterator exhibitors = ExhibitorProject.getDatabaseManager().listRecords("Exhibitor").iterator();
			while(exhibitors.hasNext())
			{
				Exhibitor exhibitor = (Exhibitor) exhibitors.next();
				exhibitor.report();
			}
			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void test1 ( String[] args )
	{
		try
		{	
			Log.debug = true;
			Log.report = Util.getPrintStream("C:\\Workspace\\Ultra\\Susan\\globalpowergen\\log.csv");

			ExhibitorList exlist = new ExhibitorListGlobal();
			exlist.setEventName("Globalpowergen");
			
			for(int i=0; i<6; i++ )
			{
				exlist.setPageNum(i);
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
		int rnd = (int) Math.floor(Math.random() * 99);
//		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[0]=991&fair[854]=993&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&sfr=1&viewmode=list&order=1&&offset=" + pageNum + "&limit=100";
//		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[]=854";
//		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[]=953&fair[]=954&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&sfr=1&viewmode=list&order=1&&offset=" + pageNum + "&limit=100";
//		return "http://www.globalpowergen-community.com/index.php5?Action=showSearchResults&searchtype=all&category[0]=0&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&&offset=" + pageNum + "&limit=100&order=1&sfr=1";		
//		return "http://www.globalpowergen-community.com/index.php5?Action=showSearchResults&itemtype=company&bc=12&searchtype=company&category[0]=0&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&offset=" + pageNum + "&order=1&sfr=1";
//		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[]=854&fair[]=991&fair[]=993&fair[]=953&fair[]=954&fair[]=917&fair[]=918&fair[]=983&fair[]=982&fair[]=984&fair[]=960&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&sfr=1&viewmode=list&order=1&&offset=" + (pageNum*100) + "&limit=100";
		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[]=983&fair[]=918&fair[]=993&fair[]=984&fair[]=954&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&sfr=1&viewmode=list&order=1&&offset=" + (pageNum*100) + "&limit=100";		
	}
	
	public void populate() throws Exception
	{
//		String qlink = "a[href~=(ExhibitorHome)\\.(aspx)\\?(BoothID=)(?i)]";
//		String qlink = "a[class=exhib_status exhib_status_a]";
//		String qlink = "p.result-owner a";
		String qlink = "div.topic h2 a";

//		Document doc = Jsoup.connect( listUrl() ).get();
		Connection conn = Jsoup.connect( listUrl() );
		conn.timeout(10000);
		Document doc = conn.get();
		Iterator els = doc.select( qlink ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
//			Log.infoln("" + el.attr("href"));
		}
	}
}
