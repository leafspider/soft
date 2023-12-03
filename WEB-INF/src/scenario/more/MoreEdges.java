package scenario.more;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;

public class MoreEdges extends MoreKubes
{
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{
			String url = "http://localhost:8000/ibc/kubes.xml";
				
			Connection conn = Jsoup.connect( url );
			conn.timeout(30000);
			Document doc = conn.get();

			Element spinIdsEl = doc.select( "spinIds" ).get(0);
			String[] spinIds = spinIdsEl.text().split(",");

			Log.infoln("<ibc>");

			boolean found = false;
			
			for (int i=0; i<spinIds.length; i++) {
//			for (int i=0; i<5; i++) {
				
//				String spinId = spinIds[i].replace("'", "").replace(": ", "%5C").replace(" ",  "%20");
				String[] vals = spinIds[i].replace("'", "").split(":");
				String spinId = vals[0];

//				if( spinId.indexOf("keynote1248") > -1 ) { found = true; continue; }				
//				if( !found ) { continue; }			

				MoreEdges page = new MoreEdges();
				page.populate( spinId );
			}

			Log.infoln("</ibc>");

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void populate( String spinId ) throws Exception
	{
		url = "http://127.0.0.1:8000/ibc/edges.xml?fid=IBC%5C" + spinId;
		
//		Log.infoln(url);

		Connection conn = Jsoup.connect( url );
		conn.timeout(30000);
		Document doc = conn.get();
		
		try 
		{ 
			Elements edges = doc.select( "Edge" );
//			Elements weightVals = doc.select( "weight" );
//			Elements sourceVals = doc.select( "source" );
//			Elements targetVals = doc.select( "target" );

			String txt = "  <silhouette>\n";	
			txt += "    <item>";
			txt += "<id>" + spinId + "</id>";
			txt += "</item>\n";
			txt += "    <cues>\n";
			
			for(int i=0; i<edges.size(); i++)
			{
				Element edge = edges.get(i);
				
//				Log.infoln(edge);
				
				String[] vals = edge.select("edgeId").text().split("\\\\");
				String id = vals[4].replace("|",  " ").trim().replace(" ", "|");
//				String[] cues = vals[4].split("\\|");
//				String source = cues[0];
//				String target = "";
//				if( cues.length > 1) { target = cues[1]; }
								
				txt += "      <cue>";
//				txt += "<id>" + vals[1] + "|" + vals[4] + "</id>\n";
				txt += "<id>" + id + "</id>";
				txt += "<weight>" + edge.select("weight").text() + "</weight>";
//				txt += "<source>" + source + "</source>\n";
//				txt += "<target>" + target  + "</target>\n";
				txt += "</cue>\n";
			}
			
			txt += "    </cues>\n";
			txt += "  </silhouette>";

			Log.infoln( txt );		
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}	

	public ArrayList edges = new ArrayList();
	public ArrayList weights = new ArrayList();
}
