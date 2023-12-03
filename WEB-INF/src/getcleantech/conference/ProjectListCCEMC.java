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

public class ProjectListCCEMC extends ExhibitorList
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
	
	public static String projectSource = "CCEMC";
	public static File outFile = new File( "C:\\Workspace\\Ultra\\Susan\\data\\import\\Projects\\" + projectSource + "\\" + projectSource + " Projects.csv" );
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
			ProjectListCCEMC exlist = new ProjectListCCEMC();
			exlist.setPageNum(i);
			exlist.parse();

			Log.infoln( "----- " + exlist.listUrls[exlist.getPageNum()] + " -----" );
			
			Iterator urls = exlist.links.iterator();
			while(urls.hasNext())
			{
				String url = "" + urls.next();

				Exhibitor ex = new ProjectCCEMC();
				ex.setExhibitorUrl(url);
				ex.populate();

				// Title|Page_Url|Category|Sub_Category|Description|Price|Location|Overview|Tag_String|Email_Id|Web_Address|Phone_Number|Claim_a_Page
				// Title||Category||Description||||||Web_Address||Claim_a_Page
				
				String name = sanitize(ex.getName());
				String desc = projectSource + ": " + sanitize(ex.getDescription());
				String href = url.replaceAll("\\|", "%7C");
				
				out.print( name + "|" );
				out.print("|");
				out.print("Cleantech Project|");
				out.print("|");
				out.print( desc + "|" );
				out.print("|||||");
				out.print( href + "|" );
				out.println("|0");

				Log.infoln( href );
				Log.infoln( name );
				Log.infoln( desc );
				Log.infoln( "" );
				
				n++;
//				if ( n > 0) { break;}
			}

			p++;
//			if ( p > 0) { break;}
		}

		System.out.println( "" );
		System.out.println( "pages=" + p + " exhibitors=" + n );
	}
	
	protected static String sanitize( String st )
	{
		int len = Math.min(3800, st.length());
		st = st.substring(0,len);
		st = st.replaceAll("\\|", " ");
		st = st.replaceAll("�", "'");
		st = st.replaceAll("[^\\p{Alnum}']", " ");
//		desc = desc.replaceAll("'", " ");
//		desc = desc.replaceAll("\\x91", " ");
//		desc = desc.replaceAll("\\x92", " ");
//		desc = desc.replaceAll("\\x93", " ");
//		desc = desc.replaceAll("\\x94", " ");
		return st;
	}

	private String[] listUrls = new String[] { "ccs", "renewable-energy", "cleaner-energy-production", "energy-efficiency", "adaptation", "carbon-use", "biological" };
	public String listUrl() { return "http://ccemc.ca/projects/" + listUrls[getPageNum()]; }

	public void populate() throws Exception { }
	
	public String parse() throws Exception
	{		
//		Document doc = Jsoup.connect( listUrl() ).get();
		Connection conn = Jsoup.connect( listUrl() );
		conn.timeout(10000);
		Document doc = conn.get();

		String text = null;		//doc.select( ".intro" ).text();
//		Log.infoln( intro );

//		text += doc.select( ".content" ).first().text();
//		Log.infoln( content );

		Iterator els = doc.select( "div.project a" ).iterator();		
		links = new TreeSet();
		while(els.hasNext())
		{
			Element el = (Element) els.next();
			links.add(el.attr("href"));
//			String ptitle = el.getElementsByAttributeValue( "class", "project_title" ).text();
//			Log.infoln(ptitle + ": " + el.attr("href"));
//			Log.infoln(el.attr("href"));
		}
		return text;
	}
}
