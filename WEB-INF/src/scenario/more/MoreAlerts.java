package scenario.more;

import java.net.URL;
import java.util.ArrayList;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class MoreAlerts extends MoreKubes
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{
			MoreAlerts page = new MoreAlerts();
			page.url = "http://127.0.0.1:8000/more/alerts.xml?fid=IBC%5C1.A46%5Cwww.allegrodvt.com";
			page.populate();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void populate() throws Exception
	{
//		Document doc = Jsoup.connect( url ).get();	
		Document doc = Jsoup.parse( new URL(url), 5000 );
		try 
		{ 
			Elements itemUrlVals = doc.select( "itemUrl" );
			Elements edgeVals = doc.select( "edgeid" );

//			while( edges.hasNext())
			for(int i=0; i<edgeVals.size(); i++)
			{
				String itemUrl = "" + itemUrlVals.get(i).text();
				itemUrl = itemUrl.replaceAll("\\\\text", "").trim();
				kubes.add(itemUrl);
				Log.info( "" + itemUrl );

//				String st = "" + edges.next();
				String st = "" + edgeVals.get(i);
				st = st.replaceAll("<edgeid>", "").replaceAll("</edgeid>", "");
				st = st.replaceAll(".+text\\\\", "").replaceAll("\\|", " ").trim();
				edges.add(st);
				Log.infoln( "," + st );
			}

			/*
			while( weightVals.hasNext())
			{
				String st = "" + weightVals.next();
				st = st.replaceAll("<weight>", "").replaceAll("</weight>", "").trim();
				weights.add(st);
				Log.infoln( "" + st );
			}
			*/
} 
		catch(Exception e1) {}
	}	

	public ArrayList edges = new ArrayList();
	public ArrayList kubes = new ArrayList();
}
