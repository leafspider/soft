package soft.asset;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.StringTokenizer;

import leafspider.util.Downloader;
import leafspider.util.FileIdentifier;
import leafspider.util.HttpsDownloader;
import leafspider.util.Log;
import leafspider.util.Util;
import soft.report.WatchlistPerformance;


public class History 
{
	public static boolean debug = false;

	public static void main1 ( String[] args )
	{
		try
		{
//			File file = new File("C:\\Server\\tomcat6\\webapps\\soft\\data\\crush\\out\\2017-3-14\\http___ichart_finance_yahoo_com_table_csv_s_XLF_a_0_b_30_c_2016_d_2_e_14_f_2017_g_d_ignore__csv.htm");
			File file = new File("C:\\Server\\tomcat6\\webapps\\soft\\data\\crush\\out\\2017-5-17\\http___www_google_ca_finance_historical_q_PTGI_start_0_num_230_output_csv.htm");
			System.out.println( History.readStock(file) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void main ( String[] args )
	{
		try {

			/*
			Calendar end = new GregorianCalendar();
			end.set( 2010, 0, 1 );
			Calendar start = new GregorianCalendar();
			start.set( 2008, 0, 1 );
			start.set( end.get(Calendar.YEAR)-1, end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH) );
			*/

			/*
			Calendar start = new GregorianCalendar();
			start.set( 2010, 7, 20 );
			Calendar end = new GregorianCalendar();
			end.set( 2011, 7, 20 );	
			*/

			String ticker = "VSBN";

			Calendar end = new GregorianCalendar();
			Calendar start = new GregorianCalendar();
			start.add( Calendar.DATE, -15);

			System.out.println( History.historyCsvUrl( ticker, start, end ) );

			ArrayList prices = (ArrayList) History.loadStockPrices( ticker, start, end );
			System.out.println("size=" + prices.size());

			//double[] close = WatchlistPerformance.parsePrices(prices, 0);
			//System.out.println("" + close[0]);
		}
		catch( Exception e ) { e.printStackTrace(); }
	}
	
	private static String getGoogleUrl( String ticker ) {
		return getGoogleUrl( ticker, 230 );
	}

	private static String getGoogleUrl( String ticker, Calendar start, Calendar end ) {
		
		long days = (end.getTimeInMillis() - start.getTimeInMillis()) / (1000 * 60 * 60 * 24);
		return getGoogleUrl( ticker, days );
	}

	private static String getGoogleUrl( String ticker, long days ) {
		return "http://www.google.ca/finance/historical?q=" + ticker + "&start=0&num=" + days + "&output=csv";
	}

	private static String historyCsvUrl( String ticker, Calendar start, Calendar end )
	{
		String source = "eod";
		
//		System.out.println("ticker="+ticker);
		
//		if ( source.equals("google") ) {	//ticker.indexOf( ":" ) > -1 )	// jmh 2017-06-05
		/* jmh 2018-03-22
		if ( ticker.indexOf( ".V" ) > -1 ) {	// jmh 2017-06-27
			// Google:	Date,Open,High,Low,Close,Volume
			return getGoogleUrl( ticker, start, end );
		}
		*/
		if (source.equals("yahoo"))  {
			// Yahoo:	Date,Open,High,Low,Close,Volume,Adj Close
			//	jmh 2017-05-17
//			return "https://ichart.finance.yahoo.com/table.csv?s=" + ticker + "&a=" + start.get(Calendar.MONTH) + "&b=" + start.get(Calendar.DAY_OF_MONTH) + 
//				"&c=" + start.get(Calendar.YEAR) + "&d=" + end.get(Calendar.MONTH) + "&e=" + end.get(Calendar.DAY_OF_MONTH) + "&f=" + end.get(Calendar.YEAR) + "&g=d&ignore=.csv";

			// jmh 2017-06-05
//			return "http://download.finance.yahoo.com/d/quotes.csv?s=" + ticker + "&f=sl1d1t1c1ohgv&e=.csv";

			// jmh 2017-06-26
			int p1 = (int) (start.getTimeInMillis() / 1000);
			int p2 = (int) (end.getTimeInMillis() / 1000);			
			return "https://query1.finance.yahoo.com/v7/finance/download/" + ticker + "?period1=" + p1 + "&period2=" + p2 + "&interval=1d&events=history";
		}
		else {	// source = "eod"

			String ext = Util.getFileExtension( ticker );	// jmh 2021-02-09
			if ( ext == null || ext.length() == 0 ) {
				ticker += ".US";
			}

			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );				
//			String token = "OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX";	// AAPL test
//			return "http://eodhistoricaldata.com/api/eod/AAPL.US?from=2017-01-05&to=2017-02-10&api_token=OeAFFmMliFG5orCUuwAKQ8l4WWFQ67YX&period=d";
//			return "https://eodhistoricaldata.com/api/eod/AAPL.US?from=" + format.format( start.getTime() ) + "&to=" + format.format( end.getTime() )+ "&api_token=" + token + "&period=d";
			String token = "59529e6752f83";
			return "http://nonsecure.eodhistoricaldata.com/api/eod/" + ticker + "?from=" + format.format( start.getTime() ) + "&to=" + format.format( end.getTime() )+ "&api_token=" + token + "&period=d";
		}
	}

	public static File historyFolder( String ticker, Calendar end )
	{
		return Asset.croutFolder( end );
	}
	
//	public static String[][] loadPrices( String ticker, int days )
	public static Collection loadStockPrices( String ticker, Calendar start, Calendar end ) throws FileNotFoundException, IOException
	{
		ticker = ticker.replace("^", "%5E");
		ticker = ticker.replace("/", "%2F");

		String url = historyCsvUrl( ticker, start, end );

		/*
		String crumbleLink = "https://finance.yahoo.com/quote/" + ticker + "/history?p=" + ticker;
		String crumbleRegex = "CrumbStore\":{\"crumb\":\"(.*?)\"}";
		String cookieRegex = "Set-Cookie: (.*?); ";
		
		/*
		// get response
	    link = crumble_link.format(symbol)
	    	    response = urlopen(link)
	    	    match = re.search(cookie_regex, str(response.info()))
	    	    cookie_str = match.group(1)
	    	    text = response.read().decode("utf-8")
	    	    match = re.search(crumble_regex, text)
	    	    crumble_str = match.group(1)
	    	    return crumble_str , cookie_str
		*/
		
		// get cookie
//		url = "https://www.calcef.org/";
//		getCookieUsingCookieHandler( url );

		/*
		String crumb = "W8izr/mWWJS";
		String cookie = "B=9nhco2hacfcms&b=4&d=B7.k6KJpYEKOH9WN.HP8IZAwQJs-&s=j2&i=X32AMh_GlQmBMSXTJSMi";

		int p1 = (int) (start.getTimeInMillis() / 1000);
		int p2 = (int) (end.getTimeInMillis() / 1000);			
		String url = "https://query1.finance.yahoo.com/v7/finance/download/" + ticker + "?period1=" + p1 + "&period2=" + p2 + "&interval=1d&events=history";

		URL contenturl = new URL( url + "&crumb=" + crumb );
		URLConnection connection = contenturl.openConnection();
		connection.addRequestProperty("Cookie", cookie);				
*/

//		Log.infoln( ticker + " History.loadStockPrices url=" + url);
		File folder = historyFolder( ticker, end );
		folder.mkdirs();

		// DEBUG
//		File file = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\crush\\out\\2014-8-21\\http___ichart_finance_yahoo_com_table_csv_s_KIN_a_7_b_21_c_2013_d_7_e_21_f_2014_g_d_ignore__csv.htm" );
//		if( !file.exists() )
//		{
//			Log.infoln("url1="+url);

			File file = Downloader.downloadFile( url, folder );		// jmh 2017-06-27

			/* jmh 2021-02-11 no longer supported by Google
			if( file.length() == 0 ) {
//				url = getGoogleUrl( "TSE%3A" + ticker );	// Cannot download CSV from TSE, only 30 days in HTML format
				url = getGoogleUrl( ticker, start, end );	
				//Log.infoln("url2="+url);
				file = Downloader.downloadFile( url, folder );
			}
			*/

			/* 
			String st = url;
			int numChars = 100;
			StringBuffer sb;
			long id = 0;			
			String ext = FileIdentifier.getTypeExtensionFromUrl( st );
			if ( st.length() < numChars ) { sb = new StringBuffer( st ); }
			else {
				sb = new StringBuffer( st.substring( 0, numChars ) );
				if ( st.length() > numChars )
				{
					for ( int i = numChars + 1; i < st.length(); i++ ) { id += i * st.charAt( i ); }
					sb.append( "" + id );
				}
			}		
			st = Util.getCleanFileName( Util.replaceSubstring( Util.replaceSubstring( sb.toString(), "%20", "_", false ), ".", "_", false ) + "." + ext );
			st = st.replaceAll("\\*","_");
		
			File file = new File( folder.getAbsolutePath() + "/" + st );
			System.out.println( "file=" + file.getAbsolutePath() );
			
			HttpsDownloader.download( url, file );
			*/
//		}
		
		//if ( debug ) { Log.infoln( ticker + " History.loadStockPrices file=" + file.getAbsolutePath()); }
		return History.readStock( file );
	}
	
	/*
	public static Collection loadFuturePrices( String ticker, Calendar start, Calendar end ) throws FileNotFoundException, IOException
	{
		File file = new File( "C:\\Workspace\\Ultra\\leafspider_soft\\charts\\CrushMap\\DataOnly\\Futures\\" + ticker + ".TXT" );
		return History.readFuture( file );
	}
	*/
	
	public static Collection readStock( File file ) throws FileNotFoundException, IOException
	{
		if ( !file.exists() ) { throw new FileNotFoundException( file.getAbsolutePath() ); }

		// Date,Open,High,Low,Close,Volume,Adj Close*
		
		Collection matrix = new ArrayList();
		 
		BufferedReader bufRdr  = new BufferedReader( new FileReader( file ) );
		String line = null;
		int row = 0;
		int col = 0;
		
		while((line = bufRdr.readLine()) != null )	//&& row < rows)
		{	
			if ( line.indexOf( "<!--" ) == 0 ) { continue; }
			else if ( line.trim().equals( "" ) ) { continue; }
			else if ( line.indexOf( "History" ) > -1 ) {  continue; }
			else if ( line.indexOf( "Date" ) > -1 ) {  continue; }	// First line is: Date,Open,High,Low,Close,Volume,Adj Close*
			else if ( line.indexOf( "," ) < 0 ) { continue; }		// Last line is a datestamp (EOD)

			//System.out.println(line);

			StringTokenizer st = new StringTokenizer(line,",");
			String[] tokens = new String[st.countTokens()];
			while (st.hasMoreTokens())
			{
//				matrix[row][col] = st.nextToken();
				tokens[col] = st.nextToken();
				col++;
			}
						
			matrix.add( tokens );
			col = 0;
			row++;
		}
		return matrix;
	}
	
	public static void getCookieUsingCookieHandler( String link ) {
		
	    try {       
	        // Instantiate CookieManager and set CookiePolicy
	        CookieManager manager = new CookieManager();
	        manager.setCookiePolicy(CookiePolicy.ACCEPT_ALL);
	        CookieHandler.setDefault(manager);

	        // get content from URLConnection
	        URL url = new URL( link );
	        URLConnection connection = url.openConnection();
	        connection.getContent();
//	        InputStream is = url.openStream();

	        // get cookies from CookieStore
	        CookieStore cookieJar =  manager.getCookieStore();
	        List <HttpCookie> cookies = cookieJar.getCookies();
	        
	        System.out.println(cookieJar.toString());

	        for (HttpCookie cookie: cookies) {
	        	System.out.println("CookieHandler retrieved cookie: " + cookie);
	        }
	    } catch(Exception e) {
	        System.out.println("Unable to get cookie using CookieHandler");
	        e.printStackTrace();
	    }
	}  

	/*
	public static Collection readFuture( File file ) throws FileNotFoundException, IOException
	{
		if ( !file.exists() ) { throw new FileNotFoundException( file.getAbsolutePath() ); }

		// Date, Open, High, Low, Close, Volume (zero for forex cash markets), Open Interest (futures only), Delivery Month ( YYYYMM futures only), Unadjusted Close (zero for forex cash markets)
		
		Collection matrix = new ArrayList();
		
		BufferedReader bufRdr  = new BufferedReader( new FileReader( file ) );
		String line = null;
		int row = 0;
		int col = 0;
		int start = 4165 - 100;
		
		while((line = bufRdr.readLine()) != null )
		{	
			row++;
			if ( row < start ) { continue; }
			StringTokenizer st = new StringTokenizer(line,",");
			String[] tokens = new String[st.countTokens()];
			while (st.hasMoreTokens())
			{
//				matrix[row][col] = st.nextToken();
				tokens[col] = st.nextToken();
				col++;
			}
			matrix.add( tokens );
			col = 0;
		}
		return matrix;
	}
	*/
}
