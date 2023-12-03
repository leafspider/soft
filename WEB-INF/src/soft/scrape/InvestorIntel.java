package soft.scrape;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;

import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import scenario.more.Exhibitor;
import scenario.more.ExhibitorList;
import scenario.more.ExhibitorListIBC;

public class InvestorIntel 
{
	public static void main ( String[] args )
	{
		try
		{	
			scrape();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void scrape() throws Exception
	{		
		File folder = new File("C:\\Workspace\\Ultra\\Jake Tiley\\InvestorIntel");
		File[] files = folder.listFiles();
		for(File file : files)
		{	
//			System.out.println(file.getName());			
			Document doc = Jsoup.parse( file, null );
			String title = doc.select( ".entry-title" ).first().text();
//			String content = doc.select( ".entry-content" ).first().text();
			Elements ps = doc.select( ".entry-content p" );
						
			File txtfile = new File( file.getParentFile().getAbsolutePath() + "\\" + Util.getCleanFileName(title) + ".txt" );
			PrintStream out = Util.getPrintStream(txtfile.getAbsolutePath());
			out.println( title );			
			for(Element p : ps)
			{
				out.println( "" );			
				out.println( p.text() );
			}
		}
	}
	
}
