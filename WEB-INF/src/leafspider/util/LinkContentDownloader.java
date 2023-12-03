package leafspider.util;

import java.io.*;
import java.net.*;

import javax.servlet.http.Cookie;

/** @modelguid {FAC7DEBA-0FA2-4070-B30F-F6E7E7442089} */
public class LinkContentDownloader extends Thread
{
	public boolean debug = false;
	public java.io.PrintStream writer;
	public java.net.URL contenturl;
	public File resultsDir;
	public String link;
	public boolean success;
	protected boolean stopThread = false;
	private long connectionTimeout;
	private long sourceLength;
	private boolean threadStarted = false;	
	protected BufferedInputStream bis;
	protected BufferedOutputStream bos;
	private String password = "";
	private String userName = "";
	private boolean prependUrl = true;
	
	public static void main( String args[] )
	{	
    	try
    	{
//			String url = "http://www.neustar.us/policies/docs/ustld_nexus_requirements.pdf";
//			String link = "http://www.complexityclan.com/";
//			String link = "http://72.138.243.62:7777/search/query/search?search_nextview=Submit&search_startnum=&search_endnum=&search_dupid=&search.p_attrows=&search_p_tab=&nodeid=&fid=&browse_mode=Alldocs&search.p_mode=Basic&search.p_action=Search&q=people";
//    		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=109&f_url=c%3A%2Ftest%2FWe%2520the%2520People%2520of%2520the%2520United%2520States.txt";
//   		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=476&f_url=c%3A%2Ftest%2FDocuments%2FTerrorism%2FPublic%2FGeneral_Reference%2FResource_Guide_Transportation_Mass_Transit.pdf";
//    		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=372&f_url=c%3A%2Ftest%2FDocuments%2FUS_Gov%2FConstitution%2Fhow_the_US_is_governed.pdf";
//    		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=476&f_url=c%3A%2Ftest%2FDocuments%2FTerrorism%2FPublic%2FGeneral_Reference%2FResource_Guide_Transportation_Mass_Transit.pdf";
//    		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=452&f_url=c%3A%2Ftest%2FDocuments%2FTerrorism%2FPublic%2FRecent%2Fnsct2006.pdf";
//    		String link = "http://72.138.243.62:7777/search/query/display.jsp?type=file&docid=642&f_url=c%3A%2Ftest%2FDocuments%2FTerrorism%2FPublic%2FUS_Gov%2FCommissions%2520and%2520Studies%2FRL33166.pdf";
//    		String link = "http://iphone.usatoday.com/detail.jsp?key=871918&rc=tr_so_cg_tr&p=2";
//    		String link = "http://ow.ly/2brT94";
//    		String link = "http://ow.ly/2brT94";
//    		String link = "http://localhost:8000/adspace/sms";
//    		String link = "http://www.linkedin.com/in/abrahamheifets";
//    		String link = "http://www.gurufocus.com/modules/insiders/InsiderBuy_ajax.php?p=0";
//    		String link = "http://www.linkedin.com/profile/view?id=18667194&authType=name&authToken=hB-Y&trk=api*a301188*s308725*";
    		//String link = "https://www.cmegroup.com/market-data/volume-open-interest/exchange-volume.html";
			//String link ="https://www.cmegroup.com/CmeWS/exp/voiTotalsViewExport.ctl?media=xls&tradeDate=20210309&reportType=F";
    		String link = "https://www.ted.com/talks/lisa_genova_how_your_memory_works_and_why_forgetting_is_totally_ok/transcript";

			URL url = new URL( link );
			long timeout = 120000;
			File outputFolder = new File( "C:\\Temp\\downloader" );
			outputFolder.mkdirs();
			Util.deleteAllFiles( outputFolder );
			String userName = "qpadmin";
			String password = "password";
			
			LinkContentDownloader downloader = new LinkContentDownloader( url, outputFolder );
			downloader.setUserName( userName );
			downloader.setPassword( password );
			
			downloader.setPrependUrl( false );
			
			File resultFile = downloader.getResultFile();
			System.out.println( "resultFile.getPath()=" + resultFile.getPath() );
			
			if ( !resultFile.exists() )
			{
				System.out.println( "- Starting thread" );
				downloader.start();
				if ( downloader.isAlive() )
				{
					System.out.println( "- Joining thread" );
					
					Timer timer = new Timer();
					timer.start( null );	
					downloader.join( timeout );
					timer.end( "bobbins" );
				}
				System.out.println( "- Testing file" );
				if ( resultFile.exists() )
				{
					System.out.println( "timeout=" + timeout );
		    	    System.out.println( "Source file: length=" + downloader.getSourceLength() );
					System.out.println( "Created file: length=" + resultFile.length() + " " + resultFile.getName() );
				}
				else
				{
					Log.infoln( "Empty file: " + resultFile.getName() );
					downloader.success = false;
					PrintStream writer = Util.getPrintStream( resultFile.getAbsolutePath() );
					writer.println( "<!--" + url + "-->" );
					writer.close();
				}
			}
			else { System.out.println( "File already exists" ); }
			System.out.println( "- Stopping thread" );
			downloader.stopThread();
			
			/*
			System.out.println( "- Writing to downloaded file" );	
			PrintStream writer = Util.getPrintStream( resultFile.getAbsolutePath() );
			writer.println( "1" );
			writer.println( "2" );
			writer.println( "3" );
			writer.println( "4" );
			writer.println( "5" );		
			writer.close();
			*/
				
			System.out.println( "- Reading downloaded file" );	
			BufferedReader reader = Util.getBufferedReader( resultFile.getPath() );
			for ( int i = 0; i < 10; i++ )
			{
				System.out.println( reader.readLine() );
			}
			System.out.println( "- Finished" );
			Report.reportThreads();
		}
		catch ( Exception e ) { e.printStackTrace(); }
	}	
	
	/** Contructors */
	public LinkContentDownloader( URL thisUrl, File thisResultsDir ) { init( thisUrl.toExternalForm(), thisResultsDir ); }	
    /** @modelguid {A2B1DF33-819A-417F-9244-05A230BE2932} */
	public LinkContentDownloader( String thisLink, File thisResultsDir ) { init( thisLink, thisResultsDir ); }
	
	private void init( String thisLink, File thisResultsDir )
	{
//		Log.infoln( "LinkContentDownloader.thisLink=" + thisLink );
		
		link = thisLink;
		resultsDir = thisResultsDir;
		if ( resultsDir.isDirectory() == false ) { resultsDir.mkdir(); }		
		
		String st = link;
//		int numChars = 40;		// jmh 2011-08-31
//		int numChars = 60;		// jmh 2014-05-01
		int numChars = 100;
		StringBuffer sb;
		long id = 0;			
		String ext = FileIdentifier.getTypeExtensionFromUrl( st );		// jmh 2007-10-25
//		String ext = Util.getFileExtension( st );
		if ( st.length() < numChars ) { sb = new StringBuffer( st ); }
		else
		{
			sb = new StringBuffer( st.substring( 0, numChars ) );
			if ( st.length() > numChars )
			{
//				for ( int i = numChars + 1; i < st.length(); i++ ) { id += i * Character.getMessage( st.charAt( i ) ); }
				for ( int i = numChars + 1; i < st.length(); i++ ) { id += i * st.charAt( i ); }
				sb.append( "" + id );
			}
		}		
		st = Util.getCleanFileName( Util.replaceSubstring( Util.replaceSubstring( sb.toString(), "%20", "_", false ), ".", "_", false ) + "." + ext );
		st = st.replaceAll("\\*","_");
		
		setResultFileName( st );
		setResultFile( new File( resultsDir + File.separator + getResultFileName() ) );
		success = false;
		try { contenturl = new URL( link ); }
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while initializing " + link ); } }
	}
			
	public void run()
	{
		if ( debug ) { Log.infoln( "run()" ); }
		long startTime = System.currentTimeMillis();
		
		int ch = -1;
		try
		{
//			contenturl = new URL( (URL) null, link, new HttpTimeoutHandler( (int) getConnectionTimeout() ) );			// jmh Can't use in QP due to old Java version 
//			contenturl.setURLStreamHandlerFactory( new HttpTimeoutFactory( (int) getConnectionTimeout() ) );		// optional				

//			sun.net.www.protocol.http.HttpURLConnection connection = new sun.net.www.protocol.http.HttpURLConnection( contenturl, contenturl.getHost(), contenturl.getPort() );
//			sun.net.www.protocol.http.HttpURLConnection connection = (sun.net.www.protocol.http.HttpURLConnection) contenturl.openConnection();
//			connection.setFollowRedirects( false );	// optional

			// jmh 2017-06-05 Yahoo cookie & crumb combo
			// https://query1.finance.yahoo.com/v7/finance/download/VZ?period1=1495807326&period2=1498485726&interval=1d&events=history&crumb=4UoyHO28Eeu
			// B=9nhco2hacfcms&b=4&d=B7.k6KJpYEKOH9WN.HP8IZAwQJs-&s=j2&i=X32AMh_GlQmBMSXTJSMi

			String cookie = null;
			String crumb = null;
			if( contenturl.toString().indexOf("finance.yahoo.com") > -1 ) {
				
//				crumb = "AF.UgV.H.10";			// jmh 2017-06-05
//				cookie = "B=c1voaa1cjb351&b=3&s=3n";

//				crumb = "4UoyHO28Eeu";			// jmh 2017-06-26
///				cookie = "B=9nhco2hacfcms&b=4&d=B7.k6KJpYEKOH9WN.HP8IZAwQJs-&s=j2&i=X32AMh_GlQmBMSXTJSMi";				
				
				crumb = "W8izr/mWWJS";
				cookie = "B=9nhco2hacfcms&b=4&d=B7.k6KJpYEKOH9WN.HP8IZAwQJs-&s=j2&i=X32AMh_GlQmBMSXTJSMi";
				
				contenturl = new URL( contenturl.toString() + "&crumb=" + crumb );
			}

//			URLConnection connection = contenturl.openConnection();
			HttpURLConnection connection = (HttpURLConnection) contenturl.openConnection();
			connection.setInstanceFollowRedirects(true);
			connection.addRequestProperty("Cookie", cookie);

			// Follow redirects
			URL base, next;
			String location;			
			switch (connection.getResponseCode()) {
				case HttpURLConnection.HTTP_MOVED_PERM:
					location = connection.getHeaderField("Location");
					location = URLDecoder.decode(location, "UTF-8");
					base     = contenturl;               
					next     = new URL(base, location);  // Deal with relative URLs
					contenturl      = new URL( next.toExternalForm() );
			}
			
//			Log.infoln("contenturl=" + contenturl);
		     
			// Deal with URLs which require a login
			/*
			String usernamePassword = getUserName() + ":" + getPassword();
			if ( !usernamePassword.equals( ":" ) && !usernamePassword.equals( "null:null" ) )
			{
				String encoding = new sun.misc.BASE64Encoder().encode( usernamePassword.getBytes() );
				connection.setRequestProperty ( "Authorization", "Basic " + encoding );
			}
			*/
//			Log.info( "usernamePassword=" + usernamePassword );
			
//	        connection.connect();		// optional
	        setSourceLength( connection.getContentLength() );
	        
//			inurl = contenturl.openStream();
//	        inurl = new BufferedInputStream( connection.getInputStream() );
//			writer = Util.getPrintStream( getResultFile().getPath() );
			
			bis = new BufferedInputStream( connection.getInputStream() );
			bos = new BufferedOutputStream( new FileOutputStream( getResultFile().getPath() ) );
		}
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while connecting " + link ); } }
//		catch( Exception e ) { success = false; if ( debug ) { Log.warn("Exception: ", e); } }
		if( bis != null && bos != null )
		{
			success = true;
//			Log.infoln( "LCD: link=" + link );
//			bos.println( "<!--" + link + "-->" );
//			bos.println( "" );
			try
			{
				byte[] buff = new byte[ 2048 ];
				int bytesRead;
				if ( getPrependUrl() )
				{
					bos.write( ( "<!--" + link + "-->" ).getBytes() );
					bos.write( ( "\n\n" ).getBytes() );
				}
				while( true )
				{
					if ( stopThread ) { if ( debug ) { Log.infoln( "Thread stopped: " + link ); } break; }
//					ch = inurl.read();
					bytesRead = bis.read( buff, 0, buff.length );
					if ( bytesRead == -1 ) { if ( debug ) { Log.infoln( "Reached EOF: " + link ); } break; }
//					writer.write( ch );
					bos.write( buff, 0, bytesRead );
				}
			}
			catch( Exception e ) { if ( debug ) { Log.infoln ( e.toString() + " while reading " + link ); } }
		}
/*
		if( inurl != null && writer != null )
		{
			success = true;
			writer.println( "<!--" + link + "-->" );
			writer.println( "" );
			try
			{
				while( true )
				{
					if ( stopThread ) { if ( debug ) { Log.info( "Thread stopped: " + link ); } break; }
					ch = inurl.read();
					if ( ch == -1 | ch == 0 ) { if ( debug ) { Log.info( "Reached EOF: " + link ); } break; }
					writer.write( ch );
				}
			}
			catch( Exception e ) { if ( debug ) { Log.info ( e.toString() + " while reading " + link ); } }
		}
*/
		try
		{
			close();
//			Log.info( "resultFile=" + resultFile.getName() + " length=" + resultFile.length() );
//			if ( resultFile.length() > 0 ) { success = true; }
//			else { success = false; resultFile.delete(); }
		}
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while closing " + link ); } }
	}
	
	public void finalize()
	{
		try
		{
			if ( debug ) { Log.infoln( "Finalizing LinkContentDownloader " + toString() ); }
			if ( writer != null ) { writer.close(); }
//			if ( inurl != null ) { inurl.close(); }		// jmh InputStream.close() does nothing!
		}
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while finalizing LinkContentDownloader." ); } }
	}
		
	public void close() throws Exception
	{		
		if ( debug ) { Log.infoln( "Closing LinkContentDownloader " );	}	//+ contenturl.getRef() );		
//		if( writer != null ) { writer.close(); if ( debug ) { Log.info( "Closed writer" ); } }
		if( bos != null ) { bos.close(); if ( debug ) { Log.infoln( "Closed OutputStream" ); } }
//		if( inurl != null ) { inurl.close(); Log.info( "Closed inurl" ); }		// jmh InputStream.close() does nothing!
		if ( debug ) { Log.infoln( "Closed LinkContentDownloader" ); }
	}	
	
	public void stopThread() { setStopThread( true ); }
	private void setStopThread( boolean thisStopThread ) { stopThread = true; }
	
	public String resultFileName;
	private String getResultFileName() { return resultFileName; }
	public void setResultFileName( String st ) { resultFileName = st; }	
	
	private File resultFile;
	public File getResultFile() { return resultFile; }
	public void setResultFile( File thisFile ) { resultFile = thisFile; }	
	
	/** Timeout for making the initial URL connection */
	public long getConnectionTimeout() { if ( connectionTimeout == 0 ) { setConnectionTimeout( 100 ); } return connectionTimeout; }
	public void setConnectionTimeout( long thisTimeout ) { connectionTimeout = thisTimeout; }	
	
	public long getSourceLength() { return sourceLength; }
	private void setSourceLength( long thisLength ) { sourceLength = thisLength; }	
	
	public boolean getThreadStarted() { return threadStarted; }
	public void setThreadStarted( boolean thisThreadStarted ) { threadStarted = thisThreadStarted; }
	
	public void startThread()
	{
		try
		{
			setThreadStarted( true );
			start();
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); }
	}
	
	public String getPassword() { return password; }
	public String getUserName() { return userName; }

	public void setPassword( String thisVal ) { password = thisVal; }
	public void setUserName( String thisVal ) { userName = thisVal; }
	
	public boolean getPrependUrl() { return prependUrl; }
	public void setPrependUrl( boolean thisVal ) { prependUrl = thisVal; }
	
	public void run2()
	{
		if ( debug ) { Log.infoln( "run()" ); }
		Log.infoln( "run2()" );
		try
		{
			URLConnection connection = contenturl.openConnection();
			
			// Deal with URLs which require a login
			/*
			String usernamePassword = getUserName() + ":" + getPassword();
			if ( !usernamePassword.equals( ":" ) && !usernamePassword.equals( "null:null" ) )
			{
				String encoding = new sun.misc.BASE64Encoder().encode( usernamePassword.getBytes() );
				connection.setRequestProperty ( "Authorization", "Basic " + encoding );
			}
			*/
			setSourceLength( connection.getContentLength() );
	        
			bis = new BufferedInputStream( connection.getInputStream() );
			bos = new BufferedOutputStream( new FileOutputStream( getResultFile().getPath() ) );
		}
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while connecting " + link ); } }
		if( bis != null && bos != null )
		{
			success = true;
			try
			{
				byte[] buff = new byte[ 2048 ];
				int bytesRead;
				if ( getPrependUrl() )
				{
					bos.write( ( "<!--" + link + "-->" ).getBytes() );
					bos.write( ( "\n\n" ).getBytes() );
				}
				while( true )
				{
					if ( stopThread ) { if ( debug ) { Log.infoln( "Thread stopped: " + link ); } break; }
					bytesRead = bis.read( buff, 0, buff.length );
					if ( bytesRead == -1 ) { if ( debug ) { Log.infoln( "Reached EOF: " + link ); } break; }
					bos.write( buff, 0, bytesRead );
				}
			}
			catch( Exception e ) { if ( debug ) { Log.infoln ( e.toString() + " while reading " + link ); } }
		}
		try { close(); }
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while closing " + link ); } }
	}

}