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

public class ExhibitorListMS extends ExhibitorList
{	
	public static void main ( String[] args )
	{
		test1();
//		test2();
	}
	
	public static int[] exhibids = {27,29,35,36,37,38,44,48,49,51,52,57,62,71,72,80,83,84,86,103,104,107,115,118,120,129,130,136,139,140,141,142,143,144,145,146,147,149,150,152,154,155,156,158,159,160,161,162,163,164,165,166,167,168,169,171,172,173,175,177,179,180,181,182,184,186,187,188,190,191,192,193,194,195,196,197,198,199,200,201,203,204,205,206,208,209,210,211,212,213,214,215,216,217,218,219,220,221,222,223,225,226,227,229,230,231,232,233,234};
	
	public static void test2()
	{
		try
		{	
			Log.debug = true;

			String fname = ExhibitorListMS.class.getSimpleName();
			File exhibitorFile = new File( "C:\\Workspace\\Ultra\\Nick\\Microsoft\\exhibitors_" + fname + ".csv" );
			
//			Log.report = Util.getPrintStream(listFile.getAbsolutePath());
			Log.report = Util.getPrintStream(exhibitorFile.getAbsolutePath());
			Log.infoln("name,booth,website,description");
			
			int p = 0;
			int n = 0;
			int max = exhibids.length;
			
			for (int i=0; i<max;i++)
			{
				int id = exhibids[i];

				int rnd = (int) Math.floor(Math.random() * 99);
				
				Exhibitor ex = new ExhibitorDX();
				ex.setExhibitorUrl("http://www.dx3canada.com/page.cfm/Action=Exhib/ExhibID=" + id + "/loadSearch=577998_18" + rnd);
				ex.populate();
				n++;
			}

			System.out.println( "" );
			System.out.println( "exhibitors=" + n );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void test1()
	{
		try
		{	
			Log.debug = true;
			Log.report = Util.getPrintStream("C:\\Workspace\\Ultra\\Nick\\Microsoft\\log.csv");

			ExhibitorList exlist = new ExhibitorListMS();
			exlist.setEventName("Microsoft");
			
			for(int i=0; i<126; i++ )
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
//		return "http://www.globalpowergen-community.com/index.php5?path=Visitor&Action=showCompanies&fair[]=983&fair[]=918&fair[]=993&fair[]=984&fair[]=954&fid=" + rnd + "1c03c02766ecc6592b9f3be0521e09&sfr=1&viewmode=list&order=1&&offset=" + (pageNum*100) + "&limit=100";
//		return "http://www.dx3canada.com/page.cfm/action=ExhibList/ListID=8/t=m";
//		return "http://advertising.microsoft.com/casestudies?Search=1&Tag_VerticalID=0&Tag_TopicID=0&Tag_PropertySubpropertyID=&DocType=casestudy&pindex=" + pageNum;
		return "http://advertising.microsoft.com/research-reports?Search=1&Tag_VerticalID=0&Tag_TopicID=0&Tag_PropertySubpropertyID=&DocType=researchreport&pindex=" + pageNum;
	}
	
	public void populate() throws Exception
	{
//		String qlink = "a[href~=(ExhibitorHome)\\.(aspx)\\?(BoothID=)(?i)]";
//		String qlink = "a[class=exhib_status exhib_status_a]";
//		String qlink = "p.result-owner a";
		String qlink = "th[class=IMAGERIGHT] a";

//		Document doc = Jsoup.connect( listUrl() ).get();
		Connection conn = Jsoup.connect( listUrl() );
		conn.timeout(10000);
		Document doc = conn.get();
		
//		Log.infoln( doc.text() );
		
		String booth = "Research Reports";//"Case Studies";
		
		Iterator els = doc.select( qlink ).iterator();
		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
			String name = el.child(0).attr("title");				
			Log.infoln("" + el.attr("href") + "^t" + booth + "^t" + name);
		}
	}
}
