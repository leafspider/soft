package getcleantech.conference;

import java.io.File;
import java.io.PrintStream;
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

public class ProjectListCosia extends ExhibitorList
{
	public static void main ( String[] args )
	{
		try
		{	
			test1();			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static String fname = ProjectListCosia.class.getSimpleName();
	public static File outFile = new File( "C:\\Workspace\\Ultra\\Susan\\SE Import\\Projects\\import\\COSIA Initiatives.csv" );
	public static PrintStream out = null;
	
	public static void test1() throws Exception
	{
		Log.debug = true;
		out = Util.getPrintStream(outFile.getAbsolutePath());
		
		int p = 0;
		int n = 0;
		int max = 5;
		
		for (int i=0; i<max;i++)
		{
			ProjectListCosia exlist = new ProjectListCosia();
			exlist.setPageNum(i);
			String listText = exlist.parse();

			Iterator urls = exlist.links.iterator();
			while(urls.hasNext())
			{
				String url = "" + urls.next();

				Exhibitor ex = new ProjectCosia();
				ex.setExhibitorUrl(url);
				ex.populate();

				Log.infoln( "" );
				Log.info( ex.getName() + "|" );
				Log.infoln( url + "|" );
				Log.infoln( listText + " " );
				Log.infoln( ex.getDescription() );

				// Title||Category||Description||||||Web_Address||Claim_a_Page
				// Title|Page_Url|Category|Sub_Category|Description|Price|Location|Overview|Tag_String|Email_Id|Web_Address|Phone_Number|Claim_a_Page
				out.print( ex.getName().replaceAll("\\|", " ") + "|" );
				out.print("|");
				out.print("Cleantech Project|");
				out.print("|");
//				out.print( listText.replaceAll("\\|", " ") + " " );
				String desc = ex.getDescription();
				int len = Math.min(3800, desc.length());
				desc = desc.substring(0,len);
				desc = desc.replaceAll("\\|", " ");
				desc = desc.replaceAll("'", " ");
				/*
				desc = desc.replaceAll("\\x91", " ");
				desc = desc.replaceAll("\\x92", " ");
				desc = desc.replaceAll("\\x93", " ");
				desc = desc.replaceAll("\\x94", " ");
				*/
				desc = desc.replaceAll("[^\\p{Alnum}]", " ");
				out.print( desc + "|" );
				out.print("|||||");
				out.print( url.replaceAll("\\|", " ") + "|" );
				out.println("|0");

				n++;
//				if ( n > 1) { break;}
			}
			p++;
//			if ( p > 1) { break;}
		}

		System.out.println( "" );
		System.out.println( "pages=" + p + " exhibitors=" + n );
	}

	private String[] listUrls = new String[] { "tailings", "water", "land", "greenhouse_gases" };
	public String listUrl() { return "http://www.cosia.ca/initiatives/" + listUrls[getPageNum()]; }

	public void populate() throws Exception { }
	
	public String parse() throws Exception
	{
//		Document doc = Jsoup.connect( listUrl() ).get();
		Connection conn = Jsoup.connect( listUrl() );
		conn.timeout(10000);
		Document doc = conn.get();

		String text = doc.select( ".intro" ).text();
//		Log.infoln( intro );

//		text += doc.select( ".content" ).first().text();
//		Log.infoln( content );

		Iterator els = doc.select( "div.col-sm-12 ul li a" ).iterator();		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
			//Log.infoln(el.text() + ": " + el.attr("href"));
		}
		return text;
	}
}
