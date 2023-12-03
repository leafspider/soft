package leafspider.spider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Authenticator;
import java.net.URL;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;

import org.apache.log4j.Logger;

import net.matuschek.http.DownloadRuleSet;
import net.matuschek.spider.RegExpURLCheck;

import leafspider.util.*;
import leafspider.template.*;

public class Spider extends Thread
{

	public static void main(String[] args) 
	{
		try
		{		
			Spider spider = new Spider();
			spider.setStayOnSite( "ON" );
		//	Log.infoln( "isStayOnSite=" + spider.isStayOnSite() );
//			spider.setSpiderFolder(getSpiderFolder());
			
//			String url = "http://www.alma-tech.com";
			String url = "http://www.gurufocus.com/InsiderBuy.php";
			spider.setSpiderUrl( new URL( url ) );
			spider.setDepth(1);
			spider.setMaxFiles(1);		
			
			int mins = 5;
			Thread thread = new Thread(spider);
			thread.start();
			thread.join(mins*60*1000);

//			spider.runSpider( 3, 10 );	
						
			File spiderLogFile = spider.getSpiderLog();			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void run()
	{
		runSpider( depth, maxFiles );
	}
	
	public void runSpider( int depth, int maxFiles)
	{
		/*
		String twitterUrl = "://twitter.com";
		int pos = theURL.toString().toLowerCase().indexOf( twitterUrl );
		if ( (pos == 4 || pos == 5) ) 
		{ 
			String newUrl = Util.replaceSubstring( theURL.toString(), "/#!/", "/" );
			try
			{
				theURL = new URL( newUrl );
				Log.infoln( "theURL=" + theURL.toString() );
			}
			catch( Exception e ) 
			{
				e.printStackTrace();
			}
			
			TwitterSpider tspider = new TwitterSpider();
			tspider.runSearch( theURL, depth, maxFiles ); 
			setUrlTable( tspider.getUrlTable() );
			setSpiderLog( tspider.getSpiderLog() );
		}
		else if(Feed.isFeed(theURL.toString()))
		{
			FeedSpider feedSpider = new FeedSpider();
			feedSpider.runSearch( theURL, 1, 100 );
			setUrlTable( feedSpider.getUrlTable() );
			setSpiderLog( feedSpider.getSpiderLog() );
		}
		else
		{
		*/
			runSpider( depth, maxFiles, false );
//		}
	}
	
	public void runSpider( int depth, int maxFiles, boolean isDeleteFiles )
	{
		Authenticator.setDefault ( new SpiderAuthenticator() );	
		
//		System.out.println( "maxFiles=" + maxFiles );
		String downloadFolder = getSpiderFolder().getSpiderCollectionFolder();
		Util.createDirectory( downloadFolder );
		if ( isDeleteFiles )
		{
			Util.deleteAllFiles( new File( downloadFolder ), true );
		}
		
		KWebRobot robby = new KWebRobot();
		robby.setIgnoreRobotsTxt( true );
		robby.setStartURL(spiderUrl);
		robby.setMaxDepth(depth);
		robby.setSleepTime(0);
		robby.setAllowWholeDomain(true);
		robby.setAllowWholeHost(true);
		robby.setFlexibleHostCheck(true);
		robby.setWalkToOtherHosts( !isStayOnSite() ); 
		robby.setLogFile( getSpiderLog() );
		robby.setTimeout(5);
		
		if (spiderUrl.getHost().indexOf("twitter.com") > -1 )
		{
			// Handle TinyURLs redirections etc
			robby.setWalkToOtherHosts( true );
//				if ( robby.getMaxDepth() < 2 ) { robby.setMaxDepth(2); }		// jmh 2010-11-08 @DEBUG
		}
			
		RegExpURLCheck check = new RegExpURLCheck();		 	
		check.setDefaultResult( true );
		try
		{
//				String st = Util.escapeForRegex( theURL.toString() ) + ".?";
//				String st1 = theURL.toString();		//"http://twitter.com/judymargolis";
//				String st2 = theURL.getProtocol() + "://" + theURL.getHost();		//"http://twitter.com";
//				String st3 = theURL.getPath();		//"/judymargolis"
//				String st4 = theURL.getQuery();		//"max_id=20323035987&page=2&twttr=true"
//				if ( st4 != null ) { st4 = "\\?" + st4; } else { st4 = ""; }
//				check.addRule( "^" + st2 + st3 + st4 + "$", true );			
//				check.addRule( "^" + st2 + ".+", false );
			/*
			check.addRule( "twitter.com", false );
			check.addRule( "tweetdeck.com", false );
			check.addRule( "twimg.com", false );
			check.addRule( "amazonaws.com", false );					
			check.addRule( "twitpic.com", false );					
			check.addRule( "tweetmeme.com", false );					
			check.addRule( "yfrog.com", false );
			check.addRule( "dynamictweets.com", false );
			*/

			check.addRule( "^" + spiderUrl.toString() + "$", true );

			try
			{
				Template template = TemplateFactory.getSpiderTemplate( spiderUrl );
				if ( template != null )
				{				
//						System.out.println( "Template class=" + template.getClass().getName() );
//						System.out.println( "Template properties=" + template.getProperties() );
					Iterator forces = template.getForces().iterator();
					while( forces.hasNext() )
					{
						String key = "" + forces.next();
						if ( key.indexOf( "^" ) == 0 ) { key = key + ".*"; }
						else if ( key.indexOf( "$" ) == (key.length()-1) ) { key = ".*" + key; }
						else { key = ".*" + key + ".*"; }
						check.addRule( key, true );
//							Log.infoln( "Force " + key );
					}
					Iterator strips = template.getStrips().iterator();
					while( strips.hasNext() )
					{
						String key = "" + strips.next();
						if ( key.indexOf( "^" ) == 0 ) { key = key + ".*"; }
						else if ( key.indexOf( "$" ) == (key.length()-1) ) { key = ".*" + key; }
						else { key = ".*" + key + ".*"; }
						check.addRule( key, false );
//							Log.infoln( "Strip " + key );
					}
				}			
			}
			catch( Exception e )
			{
				e.printStackTrace();
				Log.infoln( e.getMessage() );
			}
		}
		catch( org.apache.regexp.RESyntaxException se )
		{
			Log.infoln( se.getMessage() );
		}
		robby.setURLCheck( check );

		KHttpDocToFile downloader = new KHttpDocToFile(downloadFolder);
		downloader.setReplaceAllSpecials(true);
		downloader.setStoreCGI(false);
		robby.setDocManager(downloader);

		/* Handle Max # Of Files */
		ArrayList list = FileStopper.getSupportedFiles();
		list.add( "" );		// jmh 2006-05-22

		JoboDownloadStopper stopDownload = new JoboDownloadStopper(maxFiles, list, downloadFolder, robby);
		robby.setWebRobotCallback(stopDownload);		// jmh 2007-02-08
		
		/* Exclude certain mime types */
		DownloadRuleSet rulesSet = new DownloadRuleSet();
		rulesSet.addRule("image", "*", 0, Integer.MAX_VALUE, false);
		rulesSet.addRule("audio", "*", 0, Integer.MAX_VALUE, false);
		rulesSet.addRule("video", "*", 0, Integer.MAX_VALUE, false);

		robby.setDownloadRuleSet(rulesSet);

		/* Run the Spider */
		robby.run();

		Logger log = Logger.getLogger( "SpiderLogger" );
        log.removeAllAppenders();
		
		setUrlTable( robby.getUrlTable() );
//		downloadFiles();
		rewriteFiles();	   // jmh 2010-06-21
		
//		System.out.println( "downloadFolder=" + downloadFolder );
		renameHtmlDocTypes( downloadFolder );		// jmh 2010-06-21
	}

	private SpiderFolder spiderFolder = null;
	public SpiderFolder getSpiderFolder() 
	{
		if ( spiderFolder == null )
		{
			spiderFolder = new SpiderFolder();
//			spiderFolder.setData( getData() );
		}
		return spiderFolder;
	}
	public void setSpiderFolder(SpiderFolder spiderFolder) {
		this.spiderFolder = spiderFolder;
	}

	private void renameHtmlDocTypes( String downloadFolder )
	{
		try
		{
			File folder = new File( downloadFolder );
			String[] list = Util.getCompleteFileList( folder );
			if ( list == null ) { return; }
			for ( int i = 0; i < list.length; i++ )
			{
				File file = new File( list[i] );
				if ( !file.isDirectory() )
				{
					String ext = Util.getFileExtension( file );
					if ( ext == null || ext.equals( "" ) )
					{
//						Log.infoln( "renameHtmlDocTypes=" + file.getAbsolutePath() );
						if ( FileStopper.getDocType( file ).equalsIgnoreCase( "HTML" ) )
						{
							File newFile = new File( file.getAbsolutePath() + ".htm" );
							Util.copyFile( file, newFile );
							file.delete();
						}
					}
				}				
			}
		}
		catch ( Exception e ) { e.printStackTrace(); }
	}
	
	public String[] filelist() 
	{		        	
//		Log.infoln( "getSpiderFolder().getSpiderCollectionFolder()=" + getSpiderFolder().getSpiderCollectionFolder() );
		return Util.getCompleteFileList(new File( getSpiderFolder().getSpiderCollectionFolder()), true);
	}

	public Hashtable urlTable = null;
	public Hashtable getUrlTable() { 
		return urlTable; 
	}
	public void setUrlTable(Hashtable urlTable) {
		this.urlTable = urlTable;
	}

	private void rewriteFiles()
	{
		Iterator keys = getUrlTable().keySet().iterator();
		while( keys.hasNext() )
		{
			String path = (String) keys.next();
			String url = (String) getUrlTable().get(path);
			File file = new File( path );
//			Log.infoln( "rewriteFiles file=" + file.getAbsolutePath() );
//			if ( file.exists() && FileStopper.isSupported(file))		// jmh 2010-06-21
			{
				rewriteFile( file, url );
			}
		}
	}

	private void rewriteFile( File file, String url )
	{
//		Log.infoln( "rewriteFile file=" + file.getAbsolutePath() );
		File tmpFile = new File( file.getAbsolutePath() + ".tmp" );
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;

		try
		{
			Util.copyFile(file, tmpFile);
		}
		catch( Exception fnf )
		{
			return;
		}
		
		try
		{			
			bis = new BufferedInputStream( new FileInputStream( tmpFile ));
			bos = new BufferedOutputStream( new FileOutputStream( file ) );
	
			byte[] buff = new byte[ 2048 ];
			int bytesRead;
			bos.write( ( "<!--" + url + "-->" ).getBytes() );
			bos.write( ( "\n\n" ).getBytes() );
	
			while( true )
			{
	//			if ( stopThread ) { if ( debug ) { Log.infoln( "Thread stopped: " + link ); } break; }
	//			ch = inurl.read();
				bytesRead = bis.read( buff, 0, buff.length );
				if ( bytesRead == -1 ) { break; }
	//			writer.write( ch );
				bos.write( buff, 0, bytesRead );
			}
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}	
		finally
		{	
			try
			{
				bis.close();
				bos.close();
			}		
			catch( Exception e)
			{
				e.printStackTrace();		
			}
			Util.deleteFile(tmpFile);
		}
	}

	/*
	private void downloadFiles()
	{
		File folder = new File( getSpiderFolder().getSpiderCollectionFolder() + "/download");
		folder.mkdirs();
		
		Iterator keys = getUrlTable().keySet().iterator();
		while( keys.hasNext() )
		{
			String path = (String) keys.next();
			String url = (String) getUrlTable().get(path);			
			downloadFile( url, folder );			
		}
	}
	*/

//	@Test
	public void test_spider()
	{
		try
		{
//			Spider spider = new Spider();
//			String url = "http://blogs.digitalpoint.com/entry.php?b=215";
//			String url = "http://twitter.com/MattCutts";
//			String url = "http://twitter.com/SilkCharm";
//			String url = "http://twitter.com/christina411";
			String url = "http://twitter.com/mpagah2";
			
			/* real redirects
			 http://ow.ly/2UOeP
			 http://goo.gl/Zn0S
			 */
			
			/* faux redirects
			*/
			
			setSpiderUrl( new URL( url ) );
			runSpider( 2, 10 );
			
			/*
			Template template = TemplateFactory.getTemplate( new URL( "http://twitter.com/trout" ) );
			if ( template != null )
			{				
//				System.out.println( "Template class=" + template.getClass().getName() );
				System.out.println( "Template properties=" + template.getProperties() );
				Iterator forces = template.getForces().iterator();
				while( forces.hasNext() )
				{
					String key = "" + forces.next();
//					check.addRule( key, true );
					System.out.println( "Check " + key + "=true");
				}
				Iterator strips = template.getStrips().iterator();
				while( strips.hasNext() )
				{
					String key = "" + strips.next();
//					check.addRule( key, false );
					System.out.println( "Check " + key + "=false");
				}
			}
			
			/*
		    String reg = "John (?!Smith)[A-Z]\\w+";
		    Pattern pattern = Pattern.compile(reg);

		    String candidate = "I think that John Smith ";
		    candidate += "is a fictional character. His real name ";
		    candidate += "might be John Jackson, John Westling, ";
		    candidate += "or John Holmes for all we know.";

		    Matcher matcher = pattern.matcher(candidate);

		    String tmp = null;

		    while (matcher.find()) {
		      tmp = matcher.group();
		      System.out.println("MATCH:" + tmp);
		    }
		    */	
			
			/*
			URL theURL = new URL( "http://twitter.com/judymargolis" );
			String st1 = theURL.toString();		//"http://twitter.com/judymargolis";
			String st2 = theURL.getProtocol() + "://" + theURL.getHost();		//"http://twitter.com";
			String st3 = theURL.getPath();		//"/judymargolis"
			String st4 = "http://twitter.com/login";
			String regex = st2 + "(?!" + st3 + ").+";			
			System.out.println( "st1=" + st1 );
			System.out.println( "st2=" + st2 );
			System.out.println( "st3=" + st3 );			
			System.out.println( "st4=" + st4 );
			System.out.println( "match1=" + st1.matches( regex ) );		// false
			System.out.println( "match2=" + st4.matches( regex ) );		// false
			System.out.println( RE.simplePatternToFullRegularExpression(regex) );
			
			RECompiler cmp = new RECompiler();
			cmp.compile( regex );
			
			/*
			String bst1 = "http://twitter.com/judymargolis";
			String bst2 = "http://twitter.com/login";
			String bst3 = "http://twitter.com";
			String bregex = bst3 + "(?!/judymargolis).+";
			System.out.println( "bst1=" + bst1 );
			System.out.println( "match1=" + bst1.matches( bregex ) );
			System.out.println( "bst2=" + bst2 );
			System.out.println( "match2=" + bst2.matches( bregex ) );
						
			REProgram re1 = new REProgram(bregex.toCharArray());
			RE re = new RE( re1 );
					
			System.out.println( RE.simplePatternToFullRegularExpression(bregex) );
			RECompiler cmp = new RECompiler();
//			cmp.escape();
			cmp.compile( bregex );
			
//			check.addRule( "http://twitter.com.?", false );	// jmh 2010-07-30	
//			check.addRule( st, true );	// jmh 2010-07-30
//			String hst = "twitter";
//			check.addRule( "((?!" + st + ").)*" + hst + "((?!" + st + ").)*", false );
			
//			Spider spider = new Spider();

//			String url = "http://www.linkedin.com/home";
//			String url = "http://www.facebook.com/mpagah";
//			String url = "http://twitter.com/mpagah";			
//			String url = "http://twitter.com/spafax";
//			String url = "http://www.spafax.com";
			
			/*
			File folder = new File( "C:\\Temp\\spider" );			
			folder.mkdirs();
			spider.downloadFile( url, folder );
			*/
			
//			spider.runSpider( new URL( url ), 3, 10 );
			
//			System.out.println( "done" );
		}
		catch( Exception e) { e.printStackTrace(); }
	}
	
	private String stayOnSite = null;
	public boolean isStayOnSite() 
	{
		if ( stayOnSite == null ) { stayOnSite = "OFF"; }
		if ( stayOnSite.equals( "ON" ) ) { return true; }
		else { return false; }
	}
	public void setStayOnSite(String stayOnSite) {
		this.stayOnSite = stayOnSite;
	}
	
	private File spiderLog = null;
	public File getSpiderLog()
	{		
		if ( spiderLog == null )
		{
//			String rnd = "" + (Math.random() * 1000);
			String rnd = Util.getCleanFileName( "" + getSpiderUrl() );
			spiderLog = new File( new File( getSpiderFolder().getSpiderCollectionFolder() ).getAbsolutePath() + "\\spider-" + rnd + ".log" );
		}
		return spiderLog;
	}
	public void setSpiderLog(File spiderLog) {
		this.spiderLog = spiderLog;
	}

	private URL spiderUrl = null;
	public URL getSpiderUrl() {
		return spiderUrl;
	}
	public void setSpiderUrl(URL theURL) {
		this.spiderUrl = theURL;
	}

	private int depth = 0;
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	private int maxFiles = 0;
	public int getMaxFiles() {
		return maxFiles;
	}
	public void setMaxFiles(int maxFiles) {
		this.maxFiles = maxFiles;
	}
}
