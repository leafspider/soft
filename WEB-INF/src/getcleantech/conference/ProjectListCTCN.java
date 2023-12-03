package getcleantech.conference;

import java.net.URL;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ProjectListCTCN	// extends Exhibitor 
{
	public static void main ( String[] args )
	{
		try
		{	
//			Log.debug = true;

			ProjectListCTCN ex = new ProjectListCTCN();
			for( int i=0; i<5; i++) {
				ex.populate("https://www.ctc-n.org/technical-assistance/technical-assistance-requests?page=" + i);
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void populate(String url) throws Exception
	{		
		try
		{
			Document doc = Jsoup.parse( new URL(url), 10000 );
			try
			{
//				Elements anchors = doc.select( ".views-field-title a" );

				Elements rows = doc.select( ".views-table tr" );
				for( Element row : rows ) {
//					Log.infoln( "rows=" + rows.size() );
					String location = row.select(".views-field-field-countries").get(0).text();
					if( location.equals("Countries")) { continue; }
					Element anchor = row.select(".views-field-title a").get(0);
					String title = anchor.text();
					String href = "https://www.ctc-n.org" + anchor.attr("href");
					Log.infoln( location + "|\"" + title + "\"|" + href );
				}
			}
			catch(Exception e) 
			{
				e.printStackTrace();			
			}
			
//			Log.infoln("\"" + name + "\",\"" + description + "\",,\"" + website + "\",,,,\"" + address2 + "\",\"" + address1 + "\",\"" + phone + "\",\"" + addressCity + "\",\"" + addressState + "\"" );
		}
		catch(Exception timeout) 
		{
			Log.infoln("Exception " + url );			
		}
	}
	
}
