package soft.assetlist;

import java.io.File;
import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.*;


public class AssetlistParserVulture 
{
	private String anchorIdentifier = "<a href=\"/quotes/stocks/";
	boolean debug = true;

	public static void main ( String[] args )
	{
		try
		{		
			AssetlistParserVulture parser = new AssetlistParserVulture();
			parser.getTickers( new File( "C:\\Temp\\vulture\\vulturebargaincandidates.html" ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}	
	
	public String[] getTickers( File file )
	{
		TreeSet set = new TreeSet();		
		try
		{			
			if ( !file.exists() )
			{
				Log.infoln( "File not found: " + file.getAbsolutePath() );
				return null;
			}
			
			String[] lines = Util.explodeString( Util.readAsString( file ), "\">" );

			if ( debug ) { Log.infoln( "Reading " + file.getAbsolutePath() ); }
			
			for ( int j = 0; j < lines.length; j++ )
			{
				String templine = lines[j];
				if ( debug ) { Log.infoln( "tempLine=" + templine ); }						
	
				int pos = templine.toLowerCase().indexOf( anchorIdentifier );
				if ( pos > -1 )
				{
					int pos2 = templine.lastIndexOf( "/" );
					String ticker = templine.substring( pos2+1, templine.length() );
					if ( !ticker.equals( "" ) )
					{
						set.add( ticker ); 
						if ( debug ) { Log.infoln( "ticker=" + ticker ); }						
					}
				}
			}
			if ( debug ) { Log.infoln( toString() + " found " + set.size() + " hits" ); }
		}
		catch ( Exception e ) { Log.warnln("Exception: ", e); }
		String[] tickers = new String[set.size()];
		Iterator iter = set.iterator();
		int count = 0;
		while( iter.hasNext() )
		{
			tickers[count++] = (String) iter.next();
		}
		return tickers;
	}
}
