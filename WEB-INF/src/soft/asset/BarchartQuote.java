package soft.asset;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;
import leafspider.util.Util;

public class BarchartQuote extends DatabaseRecord
{
	public static void main ( String[] args )
	{
		try
		{
			BarchartQuote rec = new BarchartQuote();
			Log.debug = true;
			rec.setFile(new File("C:\\Temp\\barcharts\\SXI.htm"));
			rec.setUrl("http://old.barchart.com/detailedquote/stocks/SXI");
			rec.populate();
			Log.infoln(rec.dailyHtm);
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public String getProjectName() 
	{
		return "crush";
	}

	public void populate() throws Exception 
	{	
		try
		{			
			Document doc = parse();
			
			symname = doc.select( "h1#symname" ).first().text();
			last = doc.select( "span.last" ).first().text();
			try { up = doc.select( "span.bigqb_up" ).first().text(); } catch ( Exception e) {}
			try { down = doc.select( "span.bigqb_down" ).first().text(); } catch ( Exception e) {}
			grey = doc.select( "span.smgrey" ).first().text();
			
			Iterator els = doc.select( "table.mpbox tr" ).iterator();			
			dailyHtm = "";
			boolean stop = false;
			while(els.hasNext())
			{
				Element tr = (Element) els.next();			
				if( tr.text().indexOf("Period") > -1 || 
					tr.text().indexOf("Earnings") > -1 )
				{
					stop = true;
					break;
				}					
				if( stop ) break;
				dailyHtm += "<tr>";
				/*
				Iterator subels = tr.select("td").iterator();
				while( subels.hasNext())
				{
					Element td = (Element) subels.next();
					String txt = td.text();
				}
				*/
				dailyHtm += tr.html();
				dailyHtm += "</tr>";
			}
		}
		catch(Exception e)
		{
//			e.printStackTrace();
			dailyHtm += "<tr><td>A Detailed Quote is not available for this asset</td></tr>";
		}
	}
	
	public String dailyHtm = "";
	public String symname = null;
	public String last = null;
	public String up = null;
	public String down = null;
	public String grey = null;
}
