package scenario.more;

import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.nodes.Element;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;

public class MoreKubes extends DatabaseRecord
{
	@Override
	public String getProjectName() 
	{
		return "morereport";
	}

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
				
				String spinId = spinIds[i].replace("'", "").replace(": ", "%5C").replace(" ",  "%20");

//				if( spinId.indexOf("keynote1248") > -1 ) { found = true; continue; }				
//				if( !found ) { continue; }			

				MoreKubes page = new MoreKubes();
				page.url = "http://127.0.0.1:8000/ibc/kubes.xml?fid=IBC%5C&cid=IBC%5C" + spinId;
				page.populate();
			}

			Log.infoln("</ibc>");

		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void populate() throws Exception
	{
		Connection conn = Jsoup.connect( url );
		conn.timeout(30000);
		Document doc = conn.get();
		
		try 
		{ 
			Element cue = doc.select( "cue" ).get(0);

			String[] vals = cue.select("id").text().split("\\\\");
			
			String txt = "  <morelike>\n";	
			txt += "    <query>\n";
			txt += "      <item>\n";
			txt += "        <id>" + vals[1] + "</id>\n";
			txt += "        <name>" + cue.select("kubeName").text() + "</name>\n";
			txt += "        <hall>" + cue.select("hall").text() + "</hall>\n";
			txt += "        <stand>" + cue.select("stand").text() + "</stand>\n";
			txt += "      </item>\n";
			txt += "    </query>\n";

			txt += "    <results>";

			Iterator kubes = doc.select( "kube" ).iterator();
			
			while( kubes.hasNext())
			{
				Element kube = (Element) kubes.next();				

				vals = kube.select("kubeId").text().split("\\\\");

				txt += "      <hit>\n";
				txt += "        <id>" + vals[1] + "</id>\n";
				txt += "        <weight>" + kube.select("weight").text() + "</weight>\n";
				txt += "      </hit>\n";

//				if( num++ > 5) { break; }
			}

			txt += "    </results>\n";
			txt += "  </morelike>\n";
			
			Log.infoln( txt );			
		} 
		catch(Exception e) 
		{
			e.printStackTrace();
		}
	}	

	public String url = null;
	public ArrayList kubeIds = new ArrayList();
	public ArrayList cueIds = new ArrayList();
}
