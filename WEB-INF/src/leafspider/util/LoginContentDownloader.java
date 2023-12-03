package leafspider.util;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.http.NameValuePair;


public class LoginContentDownloader extends LinkContentDownloader
{  
    public static void main2(String[] args)
    {
//    	String postlink = "http://www.uscharts.com/login.php?sub=true";
    	String postlink = "http://www.gbemembers.com/login.php?sub=true";
//    	String link = "http://www.uscharts.com/charts/wchart.asp?sym=ZCZ12&per=D&a=D&type=";
    	String link = "http://www.uscharts.com/charts/chist.php?sym=ZCZ12&per=D&csv=yes";
//		String link = "http://www.uscharts.com/options/ohist.php?sym=SIZ16%7C8500P&csv=yes";
		String username = "DubKay";
		String password = "password";    	
		File folder = new File( "C:\\Temp\\downloader" );
		
        DefaultHttpClient client = new DefaultHttpClient();
        try 
        {
        	/*
            HttpPost post = new HttpPost( postlink );
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			
			nameValuePairs.add(new BasicNameValuePair("user",username));
			nameValuePairs.add(new BasicNameValuePair("pass",password));

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 
			HttpResponse response = client.execute(post);			
			HttpEntity entity = response.getEntity();
            
			Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }

			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
			Log.infoln( "Location=" + response.getFirstHeader("Location").getValue() );
			
            EntityUtils.consume(entity);
			
        	String location = response.getFirstHeader("Location").getValue();
            */

            String location = "http://www.uscharts.com/autologin.php?returndomain=www.gbemembers.com&redirect=/index.php&remme=&username=dubkay&password=8438bf7558a3d7bb2673ca983fcfb8ccd95a4f271fddbc3688d7f291abbdb8e0";
            HttpGet httpget = new HttpGet( location );

//            httpget.setHeader("Set-Cookie", response.getFirstHeader("Set-Cookie").getValue() );
//            httpget.setHeader("Referer", postlink );

            Log.infoln("" + httpget.getRequestLine());

			HttpResponse response = client.execute(httpget);
			HttpEntity entity = response.getEntity();

            Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }
//			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
            
//            entity.writeTo(Util.getPrintStream(filepath));
            EntityUtils.consume(entity);

            Log.infoln("");

            httpget = new HttpGet( link );

            Log.infoln("" + httpget.getRequestLine());
//			Log.infoln( "Rest:Set-Cookie=" + httpget.getFirstHeader("Set-Cookie").getValue() );

            response = client.execute(httpget);
            entity = response.getEntity();

            Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }
//			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
            
            String filepath = folder.getAbsolutePath() + "\\" + Util.getCleanFileName(link) + ".csv";
            entity.writeTo(Util.getPrintStream(filepath));
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
    
    public static void main1(String[] args)
    {
    	/*
    	String postlink = "http://www.barchart.com/login.php";
		String link = "http://www.barchart.com/historicaldata.php?sym=CLZ12&view=historicalfiles&txtDate=09%2F22%2F12";
//		String link = "http://www.barchart.com/commodityfutures/Euro_FX_Futures//options/E6?mode=d&view=merged";
		String username = "mpagah@hotmail.com";
		String password = "bernouilli27";		
//    	*/
//    	/*
//    	String postlink = "http://www.uscharts.com/login.php?sub=true";
    	String postlink = "http://www.gbemembers.com/login.php?sub=true";
//    	String link = "http://www.uscharts.com/charts/wchart.asp?sym=ZCZ12&per=D&a=D&type=";
		String link = "http://www.uscharts.com/options/ohist.php?sym=SIZ16%7C8500P&csv=yes";
		String username = "DubKay";
		String password = "password";
//		*/		
    	/*
    	String link = "http://leafspider.com/soft/";
   		String username = "jake";
   		String password = "getready99";
//   		*/
		    	
		String filepath = "C:\\Temp\\downloader\\" + Util.getCleanFileName(link) + ".htm";
		
        DefaultHttpClient client = new DefaultHttpClient();
        try 
        {
        	/*
        	client.getCredentialsProvider().setCredentials(
//                    new AuthScope("barchart.com", 80),
                    AuthScope.ANY,
                    new UsernamePasswordCredentials(username, password));
//            */
            
            HttpPost post = new HttpPost( postlink );
			List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
			
			/* barchart
			nameValuePairs.add(new BasicNameValuePair("email",username));
			nameValuePairs.add(new BasicNameValuePair("password",password));
//			*/
			
//			/* uscharts
			nameValuePairs.add(new BasicNameValuePair("user",username));
			nameValuePairs.add(new BasicNameValuePair("pass",password));
//			*/

			post.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			 
			HttpResponse response = client.execute(post);			
			HttpEntity entity = response.getEntity();
            
			Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }

//			Log.infoln( "Transfer-Encoding=" + response.getParams().getParameter("Transfer-Encoding"));
			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
			Log.infoln( "Location=" + response.getFirstHeader("Location").getValue() );
			
			// http://www.uscharts.com/autologin.php?returndomain=www.gbemembers.com&redirect=/index.php&remme=&username=dubkay&password=8438bf7558a3d7bb2673ca983fcfb8ccd95a4f271fddbc3688d7f291abbdb8e0
			
//            entity.writeTo(Util.getPrintStream(filepath));
            EntityUtils.consume(entity);
			
            /*
			BufferedReader rd = new BufferedReader(new InputStreamReader(entity.getContent()));
			String line = "";
			while ((line = rd.readLine()) != null) 
			{
				System.out.println(line);
			}
			*/           

            Log.infoln("----------------------------------------");

            HttpGet httpget = new HttpGet( response.getFirstHeader("Location").getValue() );

            httpget.setHeader("Set-Cookie", response.getFirstHeader("Set-Cookie").getValue() );
            httpget.setHeader("Referer", postlink );
            
            Log.infoln("" + httpget.getRequestLine());
			Log.infoln( "Rest:Set-Cookie=" + httpget.getFirstHeader("Set-Cookie").getValue() );

//            Log.infoln("strategy=" + client.getConnectionReuseStrategy() );

            response = client.execute(httpget);
            entity = response.getEntity();

            Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }
//			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
            
            entity.writeTo(Util.getPrintStream(filepath));
            EntityUtils.consume(entity);

            Log.infoln("----------------------------------------");

            httpget = new HttpGet( link );

//            httpget.setHeader("Set-Cookie", response.getFirstHeader("Set-Cookie").getValue() );
//            httpget.setHeader("Referer", postlink );
            
            Log.infoln("" + httpget.getRequestLine());
//			Log.infoln( "Rest:Set-Cookie=" + httpget.getFirstHeader("Set-Cookie").getValue() );

            response = client.execute(httpget);
            entity = response.getEntity();

            Log.infoln(response.getStatusLine());
            if (entity != null) 
            {
            	Log.infoln("Response content length: " + entity.getContentLength());
            }
//			Log.infoln( "Response:Set-Cookie=" + response.getFirstHeader("Set-Cookie").getValue() );	
            
            entity.writeTo(Util.getPrintStream(filepath));
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

	public static void main( String args[] )
	{	
//		String link = "http://www.uscharts.com/options/ohist.php?sym=SIZ16|8500P&csv=yes";
//		String link = "http://www.barchart.com/historicaldata.php?sym=CLZ12&view=historicalfiles&txtDate=09%2F22%2F12";
//		String link = "http://www.uscharts.com/charts/wchart.asp?sym=ZCZ12&per=D&a=D&type=";
		File folder = new File( "C:\\tmp\\downloader" );

//		String link = "https://finance.yahoo.com/portfolio/pf_1";
//		String username = "Softtradr";
//		String password = "dealmkr007";    	
		
		String link = "https://twitter.com/leafspider?page=1";
		String username = "leafspider";
		String password = "tomonaga27";    	
		
		try
		{		
			int downloadTimeout = 5000;
			LoginContentDownloader downloader = new LoginContentDownloader( "" + link, folder );
//			downloader.setUserName("mpagah@hotmail.com");
//			downloader.setPassword("bernouilli27");
//			downloader.setUserName("DubKay");
//			downloader.setPassword("password");	
			downloader.setUserName( username );
			downloader.setPassword( password );	
			downloader.getResultFile().delete();
			downloader.startThread();
			downloader.join( downloadTimeout );
			
			System.out.println( "file=" + downloader.getResultFile() );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
	}	

	public LoginContentDownloader( String thisLink, File thisResultsDir ) { super( thisLink, thisResultsDir ); }
	
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

//			Authenticator.setDefault ( new OptionsAuthenticator() );	
			
			URLConnection connection = contenturl.openConnection();
			
			// Deal with URLs which require a login
			String usernamePassword = getUserName() + ":" + getPassword();
			if( debug) { Log.infoln( "usernamePassword=" + usernamePassword ); }
			if ( !usernamePassword.equals( ":" ) && !usernamePassword.equals( "null:null" ) )
			{
//				String encoding = new sun.misc.BASE64Encoder().encode( usernamePassword.getBytes() );
				String encoding = new String(Base64.encodeBase64( usernamePassword.getBytes() ));
//				connection.setRequestProperty ( "Authorization", "Basic " + encoding );
			}
			
//	        connection.connect();		// optional
//	        setSourceLength( connection.getContentLength() );
	        
//			inurl = contenturl.openStream();
//	        inurl = new BufferedInputStream( connection.getInputStream() );
//			writer = Util.getPrintStream( getResultFile().getPath() );
			
			bis = new BufferedInputStream( connection.getInputStream() );
			if( debug) { Log.infoln( connection.getHeaderFields().toString()); }
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
		try
		{
			close();
		}
		catch( Exception e ) { success = false; if ( debug ) { Log.infoln( e.toString() + " while closing " + link ); } }
	}

	class OptionsAuthenticator extends Authenticator
	{
		protected PasswordAuthentication getPasswordAuthentication() 
		{
			String username = "DubKay";
			String password = "password";		
			return new PasswordAuthentication ( username, password.toCharArray() );
	    }
	}
	
	class FuturesAuthenticator extends Authenticator
	{
		protected PasswordAuthentication getPasswordAuthentication() 
		{
			String username = "mpagah@hotmail.com";
			String password = "bernouilli27";		
			return new PasswordAuthentication ( username, password.toCharArray() );
	    }
	}
}