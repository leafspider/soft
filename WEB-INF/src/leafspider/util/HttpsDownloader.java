package leafspider.util;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.ssl.SSLContexts;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class HttpsDownloader {

	public static void main(String[] args) {

//		String link = "https://httpbin.org/";
//		String link = "https://www.sdtc.ca/en/portfolio/projects/adaptive-transportation-management-efficient-traffic-routing";
//		String link = "https://query1.finance.yahoo.com/v7/finance/download/HK?period1=1493652805&period2=1496244805&interval=1d&events=history&crumb=4UoyHO28Eeu";
		String link = "https://www.barchart.com/etfs-funds/quotes/SPY/performance";
		File file = new File( "C:\\tmp\\spy-performance.htm" );		

		try {
			HttpsDownloader.download(link, file);
		}
		catch (Exception e) { e.printStackTrace(); }
    }
	
	public static File download(String link, File file) {
		
		try {
			URL theURL = new URL(link);
			
			// Create a trust manager that does not validate certificate chains
		    TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
		        public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }

		        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) { }

		        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) { }
		    } };

//			/*	jmh 2017-06-27
    		// Trust own CA and all self-signed certs
            SSLContext sslcontext = SSLContexts.custom()
            		.loadTrustMaterial(new File(ServerContext.getConfigFolder() + "\\GCTkeystore.jks"), "cleanstore".toCharArray(), new TrustSelfSignedStrategy())
                    .build();	    
//            */
//            SSLContext sslcontext = SSLContext.getInstance("TLS");
//			System.setProperty("https.protocols", "TLSv1");

            sslcontext.init(null, trustAllCerts, new java.security.SecureRandom());
		    HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());

		    Connection.Response response = Jsoup
		            .connect( link )
		            .timeout(60000) //
		            .method(Connection.Method.GET) //
		            .userAgent("Mozilla/5.0 (Windows NT 10.0; WOW64; rv:41.0) Gecko/20100101 Firefox/41.0") //
		            .execute();

		    Document document = response.parse();
//		    System.out.println(document);
		    
		    PrintStream out = Util.getPrintStream( file.getAbsolutePath() );
		    out.print( document );

//		    System.out.println(file.getAbsolutePath());

		    /*                        
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier());            
            
            CloseableHttpClient httpclient = HttpClients.custom()
                    .setSSLSocketFactory(sslsf)
                    .build();
            
	        try {
	
	            HttpGet httpget = new HttpGet(link);
	
	            System.out.println("Executing request " + httpget.getRequestLine());
	
	            CloseableHttpResponse response = httpclient.execute(httpget);
	            try {
	                HttpEntity entity = response.getEntity();
	
	                System.out.println("----------------------------------------");
	                System.out.println(response.getStatusLine());
	                EntityUtils.consume(entity);
	            } 
	            finally { response.close(); }
	        }
	        finally { httpclient.close(); }
	        */	    
		}
		catch (Exception e) { e.printStackTrace(); }
	    return file;	
    }
}
