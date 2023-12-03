package scenario.more;

import java.io.File;

import leafspider.util.Log;

public class CsvRowOCE
{
	public String category = null;
	public String name = null;
	public String place = null;
	public String start = null;
	public String end = null;
	public String pavilion = null;
	public String url = null;
	public String desc = null;
	public String cuesUrl = null;
	public String hall = "";
	public String stand = "";
	
	public String fileName = null;
	public File cachedFile = null;
	
    private char[] chars = new char[] {'"','&','\'','*',',',':','?','’','“','”','é','|'};	// \/:*?"<>|

	public CsvRowOCE( String[] vals ) 
	{
//		Log.infoln( "vals.length=" + vals.length );
		category = vals[0].trim().replaceAll("\"", "");
		name = vals[1].trim().replaceAll("\"", "");	// for(int i=0; i<chars.length; i++) { name = name.replaceAll("\\" + chars[i], " "); }		
		if( vals.length > 2) { place = vals[2].trim().replaceAll("\"", ""); } 		//if ( place == null || place.trim().equals("") ) { place = "" + (n+1); }
		if( vals.length > 3) { start = vals[3].trim().replaceAll("\"", ""); }
		if( vals.length > 4) { end = vals[4].trim().replaceAll("\"", ""); }
		if( vals.length > 5) { pavilion = vals[5].trim().replaceAll("\"", ""); }
		if( vals.length > 6) { url = vals[6].trim().replaceAll("\"", ""); }		 	//String[] urls = url.split(";"); url = urls[0].trim();
		if( vals.length > 7) { desc = vals[7].trim().replaceAll("\"", ""); }
		if( vals.length > 8) { cuesUrl = vals[8].trim().replaceAll("\"", ""); }		
		if( vals.length > 9) { hall = vals[9].trim().replaceAll("\"", ""); }		
		if( vals.length > 10) { stand = vals[10].trim().replaceAll("\"", ""); }		
	}
}
