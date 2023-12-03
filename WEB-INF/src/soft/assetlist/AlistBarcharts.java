package soft.assetlist;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

import leafspider.util.*;

public class AlistBarcharts extends AlistVariableBatch
{	
	private String anchorIdentifier = "<a href=\"/quotes/stocks/";
	public boolean debug = false;
	
//	public String url = "http://old.barcharts.net/stocks/rangeadvance.php";

	public static void main ( String[] args )
	{
		try
		{		
			AlistBarcharts parser = new AlistBarcharts();
//			parser.debug = true;
			parser.setFile( new File( "C:\\Temp\\barcharts\\gapup.htm" ));
//			parser.setFile( new File( "C:\\Temp\\barcharts\\SXI.htm" ));
			parser.populate();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}	

	public void populate() throws Exception 
	{
		TreeSet set = new TreeSet();
		try
		{			
			if( tickerList != null )
			{
				String[] ticks = tickerList.split(" ");
				set.addAll(Arrays.asList(ticks));
			}
			if ( file != null && file.exists() )
			{
				String[] lines = Util.explodeString( Util.readAsString( file ), "\">" );
	
				if ( debug ) { Log.infoln( "Reading " + file.getAbsolutePath() ); }
				
				for ( int j = 0; j < lines.length; j++ )
				{
					String templine = lines[j];
	//				if ( debug ) { Log.infoln( "tempLine=" + templine ); }						
		
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
		}
		catch ( Exception e ) { Log.warnln("Exception: ", e); }
		
		tickers = new ArrayList();
		tickers.addAll(set);
		
		/*
//		String[] tickers = new String[set.size()];
		Iterator iter = set.iterator();
		int count = 0;
		while( iter.hasNext() )
		{
//			tickers[count++] = (String) iter.next();
			tickers.add((String) iter.next());
		}
		*/
	}

}
