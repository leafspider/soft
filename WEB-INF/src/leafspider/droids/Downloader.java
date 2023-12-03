package leafspider.droids;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.droids.api.Link;
import org.apache.droids.delay.SimpleDelayTimer;
import org.apache.droids.handle.SysoutHandler;
import org.apache.droids.helper.factories.DroidFactory;
import org.apache.droids.helper.factories.HandlerFactory;
import org.apache.droids.helper.factories.ParserFactory;
import org.apache.droids.helper.factories.ProtocolFactory;
import org.apache.droids.helper.factories.URLFiltersFactory;
import org.apache.droids.impl.DefaultTaskExceptionHandler;
import org.apache.droids.impl.OutputStreamCrawlingDroid;
import org.apache.droids.impl.SequentialTaskMaster;
import org.apache.droids.net.RegexURLFilter;
import org.apache.droids.parse.html.HtmlParser;
import org.apache.droids.protocol.http.DroidsHttpClient;
import org.apache.droids.protocol.http.HttpProtocol;
import org.apache.http.HttpVersion;
import org.apache.http.conn.params.ConnManagerParamBean;
import org.apache.http.conn.params.ConnPerRouteBean;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParamBean;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParamBean;
import org.apache.http.protocol.HTTP;

public class Downloader
{    
  public static void main(String[] args)
  {    
	  try
	  {
		String url = "http://www.gurufocus.com/InsiderBuy.php";    
		File file = new File("C:\\Workspace\\Ultra\\Jake\\Insider\\out1.htm");
		
		Downloader der = new Downloader();
		der.download( url, file );
	  }
	  catch( Exception e)
	  {
		  e.printStackTrace();
	  }
  }
  
  public void download( String url, File file) throws Exception
  {
	FileOutputStream out = null;
	
	try
	{
		out = new FileOutputStream(file.getAbsolutePath());
	    
		// Create parser factory. Support basic HTML markup only
		ParserFactory parserFactory = new ParserFactory();
		HtmlParser htmlParser = new HtmlParser();
		htmlParser.setElements(new HashMap<String, String>());  
		/*  
		htmlParser.getElements().put("a", "href");
		htmlParser.getElements().put("link", "href");
		htmlParser.getElements().put("img", "src");
		htmlParser.getElements().put("script", "src");
		*/
		parserFactory.getMap().put("text/html", htmlParser);
		
		// Create protocol factory. Support HTTP/S only.
		ProtocolFactory protocolFactory = new ProtocolFactory();
		
		// Create and configure HTTP client 
		HttpParams params = new BasicHttpParams(); 
		HttpProtocolParamBean hppb = new HttpProtocolParamBean(params); 
		HttpConnectionParamBean hcpb = new HttpConnectionParamBean(params); 
		ConnManagerParamBean cmpb = new ConnManagerParamBean(params); 
		
		// Set protocol parametes
		hppb.setVersion(HttpVersion.HTTP_1_1);
		hppb.setContentCharset(HTTP.ISO_8859_1);
		hppb.setUseExpectContinue(true);
		// Set connection parameters
		hcpb.setStaleCheckingEnabled(false);
		// Set connection manager parameters
		ConnPerRouteBean connPerRouteBean = new ConnPerRouteBean();
		connPerRouteBean.setDefaultMaxPerRoute(2);
		cmpb.setConnectionsPerRoute(connPerRouteBean);
		
		DroidsHttpClient httpclient = new DroidsHttpClient(params);
		
		HttpProtocol httpProtocol = new HttpProtocol(httpclient);
		protocolFactory.getMap().put("http", httpProtocol);
		protocolFactory.getMap().put("https", httpProtocol);
		
		// Create URL filter factory.
		URLFiltersFactory filtersFactory = new URLFiltersFactory();
		RegexURLFilter defaultURLFilter = new RegexURLFilter();
		//    defaultURLFilter.setFile("classpath:/regex-urlfilter.txt");	// jmh 
		filtersFactory.getMap().put("default", defaultURLFilter);
		
		// Create handler factory. Provide sysout handler only.
		HandlerFactory handlerFactory = new HandlerFactory();
		SysoutHandler defaultHandler = new SysoutHandler();
		handlerFactory.getMap().put("default", defaultHandler);
		
		// Create droid factory. Leave it empty for now.
		DroidFactory<Link> droidFactory = new DroidFactory<Link>();
		
		// Create default droid 
		SimpleDelayTimer simpleDelayTimer = new SimpleDelayTimer();
		simpleDelayTimer.setDelayMillis(100);
		
		Queue<Link> simpleQueue = new LinkedList<Link>();
		
		SequentialTaskMaster<Link> taskMaster = new SequentialTaskMaster<Link>();
		taskMaster.setDelayTimer( simpleDelayTimer );
		taskMaster.setExceptionHandler( new DefaultTaskExceptionHandler() );
		
		OutputStreamCrawlingDroid helloCrawler = new OutputStreamCrawlingDroid( simpleQueue, taskMaster );
		helloCrawler.out = out;
		
		helloCrawler.setFiltersFactory(filtersFactory);
		helloCrawler.setParserFactory(parserFactory);
		helloCrawler.setProtocolFactory(protocolFactory);
		
		Collection<String> initialLocations = new ArrayList<String>();
		initialLocations.add( url );
		helloCrawler.setInitialLocations(initialLocations);
		
		// Initialize and start the crawler
		helloCrawler.init();
		helloCrawler.start();
		
		// Await termination
		helloCrawler.getTaskMaster().awaitTermination(0, TimeUnit.MILLISECONDS);

		// Shut down the HTTP connection manager
//		httpclient.getConnectionManager().shutdown();

//		System.out.println("len=" + file.length() + " mod=" + file.lastModified() );
	}
	catch(Exception e){ throw e;}
	finally { out.close(); }
  }
}