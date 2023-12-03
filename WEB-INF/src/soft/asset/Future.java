package soft.asset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.StringTokenizer;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;

import soft.ob.AlistCrusher;

import leafspider.fuzzy.Fuzzy;
import leafspider.rest.Representation;
import leafspider.stats.AlphanumComparator;
import leafspider.stats.Statistics;
import leafspider.util.Downloader;
import leafspider.util.Log;
import leafspider.util.Util;

public class Future extends Asset
{
	public Future( String ticker ) { super( ticker ); }
	
    protected String location = "http://www.uscharts.com/autologin.php?returndomain=www.gbemembers.com&redirect=/index.php&remme=&username=dubkay&password=8438bf7558a3d7bb2673ca983fcfb8ccd95a4f271fddbc3688d7f291abbdb8e0";

    protected String getLink()
	{
		return "http://www.uscharts.com/charts/chist.php?sym=" + ticker + "&per=D&csv=yes";
	}
    
	public String chartUrl()
	{
		return "http://www.uscharts.com/charts/wchart.php?sym=" + ticker;
	}
	
	public Collection loadPrices( Calendar start, Calendar end ) throws Exception
	{		
		File folder = History.historyFolder( ticker, end );
		folder.mkdirs();
        File file = new File( folder.getAbsolutePath() + "\\" + Util.getCleanFileName( getLink() ) + ".csv" );
		
//        Log.infoln( "file=" + file.getAbsolutePath() );
        
        if( !file.exists() )
        {
	        DefaultHttpClient client = new DefaultHttpClient();
	        try 
	        {
	            HttpGet httpget = new HttpGet( location );
	            Log.infoln("" + httpget.getRequestLine());
	
				HttpResponse response = client.execute(httpget);
				HttpEntity entity = response.getEntity();
	
	//            Log.infoln(response.getStatusLine());
	//	          if (entity != null) { Log.infoln("Response content length: " + entity.getContentLength());}
	            
	            EntityUtils.consume(entity);
	            
	            httpget = new HttpGet( getLink() );
	            Log.infoln("" + httpget.getRequestLine());
	
	            response = client.execute(httpget);
	            entity = response.getEntity();
	
	//            Log.infoln(response.getStatusLine());
	//            if (entity != null) { Log.infoln("Response content length: " + entity.getContentLength());}
	            
	            entity.writeTo(Util.getPrintStream( file.getAbsolutePath() ));
	            EntityUtils.consume(entity);
	        }
	        catch(Exception e)
	        {
	        	e.printStackTrace();
	        }
	        finally 
	        {
	            client.getConnectionManager().shutdown();
	        }
        }
	        
		prices = (ArrayList) readCsv( file, start, end );			
		
		return prices;
	}

	public double[] getVolumeSeries() throws Exception
	{
		if ( volumeSeries == null )
		{
//			volumeSeries = parsePrices( 5 );	// jmh 2017-6-05
			volumeSeries = parsePrices( 6 );
		}
		return volumeSeries;
	}

	public double[] getCloseSeries() throws Exception
	{
		if ( closeSeries == null )
		{
//			closeSeries = parsePrices( 4 );	// jmh 2017-6-05
			closeSeries = parsePrices( 5 );
		}
		return closeSeries;
	}
	
	public Collection readCsv( File file, Calendar start, Calendar end ) throws Exception
	{
		if ( !file.exists() ) { throw new FileNotFoundException( file.getAbsolutePath() ); }

		// Date	Open	High	Low	Close	Volume	Open Int
		
		DateFormat format = getDateFormat();
		
		Collection matrix = new ArrayList();
		 
		BufferedReader bufRdr  = new BufferedReader( new FileReader( file ) );
		String line = null;
//		int row = 0;
		int col = 0;
		
		while((line = bufRdr.readLine()) != null )	//&& row < rows)
		{	
			if ( line.trim().equals( "" ) ) { continue; }
			else if ( line.indexOf( "History" ) > -1 ) {  continue; }
			else if ( line.indexOf( "Date" ) > -1 ) {  continue; }

			StringTokenizer st = new StringTokenizer(line,",");
			String[] tokens = new String[st.countTokens()];
			while (st.hasMoreTokens())
			{
//				matrix[row][col] = st.nextToken();
				tokens[col] = st.nextToken();
				col++;
			}
			col = 0;
//			row++;
						
			Date dat = format.parse(tokens[0]);

//			Log.infoln( "" + format.format(start.getTime()) + " | " + format.format(dat) + " | " + format.format(end.getTime()) );

			if( dat.getTime() < start.getTime().getTime() || dat.getTime() > end.getTime().getTime() ) { continue; }
//			Log.infoln( tokens[1] );
			
			matrix.add( tokens );
		}
		return matrix;
	}

	
	
	

	public void populate() throws Exception
	{
//		String url = "http://finance.yahoo.com/q?s=" + ticker.toLowerCase();
//		String qprice = "span[id=yfs_l84_" + ticker.toLowerCase() + "]";
//		String qlink = "span.deal_cityname:has(span.deal_available)";
//		String qlink = "a[href~=(ExhibitorHome)\\.(aspx)\\?(BoothID=)(?i)]";
		
		Document doc = Jsoup.connect( getUrl() ).get();	

		prices = new ArrayList();
		
		Elements pels = doc.select( "td.ds_last" );
		Elements vels = doc.select( "td.ds_volume" );
		Elements dels = doc.select( "td.ds_displaytime" );		
		for(int i=0; i<pels.size(); i++)
		{
			Element pel = pels.get(i);
			Log.infoln("pel=" + pel.text() );
			String close = pel.text();
			close = close.replaceAll("s", "").replaceAll("p","").replaceAll("c", "");

			Element vel = vels.get(i);
			String volume = vel.text();
			volume = volume.replaceAll(",", "");

			Element del = dels.get(i);
			String dat = del.text();
//			dat = dat.replaceAll(",", "");
			
			// close=6 vol=5
			String[] matrix = { dat,"0","0","0","0",close,volume };
			prices.add(matrix);
		}
	}	
	
	public String getUrl()
	{
		return "http://old.barchart.com/historicaldata.php?sym=" + ticker + "&view=historicalfiles&txtDate=09%2F22%2F12";
	}

}
