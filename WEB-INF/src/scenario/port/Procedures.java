package scenario.port;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import leafspider.extract.TextExtractor;
import leafspider.util.Log;
import leafspider.util.Util;

public class Procedures {

	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			File src = new File( "C:\\Workspace\\Ultra\\Nick Trendov\\HPA" );			
			File file = new File( src.getAbsolutePath() + "\\HPA-Port-Practices-Procedures-2014.pdf.xml" );
			File trg = new File( src.getAbsolutePath() + "\\sections" );
			trg.mkdir();
			
			Document doc = Jsoup.parse(file, "UTF-8");
			Elements sections = doc.select( "section" );
			for(int i=0;i<sections.size();i++)
			{
				Element section = sections.get(i);				
				String name = section.attr("name");
				Log.infoln( name );
				name = Util.getCleanFileName(name) + ".txt";

				PrintStream out = Util.getPrintStream(trg.getAbsolutePath() + "\\" + name);
				out.print(section.text());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public static void extract()
	{
		try
		{
			File src = new File( "C:\\Workspace\\Ultra\\Nick Trendov\\HPA" );			
			File file = new File( src.getAbsolutePath() + "\\HPA-Port-Practices-Procedures-2014.pdf" );
			File textFile = new File( file.getAbsolutePath() + ".htm" );
			if( file.exists() )
			{
				if( !textFile.exists() )
				{
					TextExtractor extractor = new TextExtractor();
					extractor.extractHtml( file.getAbsolutePath(), textFile.getAbsolutePath());
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
}
