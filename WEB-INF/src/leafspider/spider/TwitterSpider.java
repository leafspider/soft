package leafspider.spider;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.Vector;

import net.matuschek.http.DownloadRuleSet;
import net.matuschek.spider.RegExpURLCheck;

import org.apache.log4j.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import leafspider.template.*;
import leafspider.util.*;

public class TwitterSpider extends Spider 
{
	private static final String twitterUrl = "http://twitter.com";
	private static boolean debug = false;

	public static void main( String[] args )
	{
		test_search();
		System.out.println( "Done" );
	}
	
	public static void test_search()
	{
		try
		{
//			debug = true;
			String url = "http://twitter.com/ryoumezawa";
			
			TwitterSpider spider = new TwitterSpider();
			spider.runSearch( new URL( url ), 1, 100 );
			
			Iterator links = spider.getUrlTable().values().iterator();
			while( links.hasNext() )
			{
				String link = (String) links.next();
				System.out.println( link );
			}			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void runSearch(URL theURL, int depth, int maxFiles)
	{
		try
		{
//			String[] vals = theURL.getPath().split( "/" );
//			String tweeter = vals[ vals.length-1 ];
			
//			setData( new DataContext( "Project1" ) );
			
			File downloadFolder = new File( getSpiderFolder().getSpiderCollectionFolder() );
			downloadFolder.mkdirs();
			Util.deleteAllFiles( downloadFolder, true );

			Template template = TemplateFactory.getSpiderTemplate( new URL( twitterUrl ) );
			
			StringBuffer log = new StringBuffer();
			
			TreeSet triedLinks = new TreeSet();
			Hashtable filteredLinks = new Hashtable();
			setUrlTable( new Hashtable() );
			
			TwitterParser parser = new TwitterParser();
			
			// For each page
			for( int i=1; i<100;i++ )
			{
				String pageUrl = theURL.toString() + "?page=" + i;

				File readerFile = downloadFile( pageUrl, downloadFolder );
				if ( !readerFile.exists() ) 
				{ 
					throw new Exception( "Could not download " + pageUrl ); 
				}
				parser.parseFile( readerFile, theURL, downloadFolder );
//				readerFile.delete();		// jmh 2011-11-13	
				
				System.out.println( "readerFile=" + readerFile.length());
				
				if ( parser.getTweets().size() < 1 )
				{
					// Have gone beyond first tweet
					Log.infoln( "No more tweets found" );
					break;
				}
				
				File contentFile = new File( url2Filename( new URL( pageUrl ), downloadFolder ) );
				contentFile.getParentFile().mkdirs();
				
				StringBuffer html = new StringBuffer();
				html.append( "<!--" + pageUrl + "-->\n" );
				html.append( "<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">\n" );
				html.append( "<html>\n" );
				html.append( "<head></head>\n" );
				html.append( "<body>\n" );

				Iterator tweets = parser.getTweets().iterator();
				while( tweets.hasNext() )
				{
					String tweet = (String) tweets.next();
					html.append( "<p>" + tweet + "</p>\n" );
				}
				
				html.append( "</body>\n" );
				html.append( "</html>\n" );
				Util.writeAsFile( html.toString(), contentFile.getAbsolutePath() );
				
				filteredLinks.put( contentFile.getAbsolutePath(), pageUrl );
				
				Iterator links = parser.getLinks().iterator();
				while( links.hasNext() )
				{
					String link = (String) links.next();
					if ( !triedLinks.contains( link ) && ( isForced( template, link ) || !isStripped( template, link ) ) )
					{						
						if ( !link.equals( pageUrl ) )		// pageUrl handled by contentFile
						{					
							triedLinks.add( link );
							
							if ( debug )
							{
								System.out.println( "Spidering " + link );
								continue;
							}
							
							if ( !debug )
							{
								setSpiderUrl( new URL( link ) );
								runSpider( depth+2, 1, false );	// Handle at least 2 redirects
							}
	
		            		Iterator keys = getUrlTable().keySet().iterator();
		            		while( keys.hasNext() )
		            		{
		            			String key = (String) keys.next();
		            			String url = (String) getUrlTable().get( key );
								File file = new File( key );
								if ( file.exists() ) { filteredLinks.put( key, url ); }
		            		}
		            		
		            		log.append( Util.fileToString( getSpiderLog(), "\n" ) );

//	    					Log.infoln( "filteredLinks.size()=" + filteredLinks.size() );
		    				if ( filteredLinks.size() >= maxFiles ) 
		    				{ 
		    					Log.infoln( "Max downloads reached for spider" );
		    					i = 100; 
		    					break;
		    				}		    				
						}
					}
				}		
				if ( debug )
				{
					break;
				}
			}

			setUrlTable( filteredLinks );
			
			Util.writeAsFile( log.toString(), getSpiderLog().getAbsolutePath() );
			
			if ( debug )
			{
				Log.infoln( "links=" + filteredLinks.size() );
			}
			/*
			Log.infoln( "contentFile=" + contentFile.getAbsolutePath() );	    
    		Iterator keys = getUrlTable().keySet().iterator();
    		while( keys.hasNext() )
    		{
				System.out.println( "url=" + (String) getUrlTable().get( (String) keys.next() ) );	    
    		}
//    		*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
		
	private ArrayList parseLinks( String content )
	{
		ArrayList links = new ArrayList();
		String[] vals = content.split( "<a href=\"" );
		for (String val : vals) 
		{
			int pos = val.indexOf( "\"" );
			if ( pos > -1 )
			{
				val = val.substring( 0, pos );
				links.add( val );
			}
		}
		return links;
	}
	
	private boolean isForced( Template template, String link )
	{
		if ( template == null ) { return false; }
		Iterator forces = template.getForces().iterator();
		while( forces.hasNext() )
		{
			String key = "" + forces.next();
			if ( key.indexOf( "^" ) == 0 ) { key = key + ".*"; }
			else if ( key.indexOf( "$" ) == (key.length()-1) ) { key = ".*" + key; }
			else { key = ".*" + key + ".*"; }
//			Log.infoln( "Force " + key );
			if ( link.matches( key ) ) { return true; }
		}
		return false;
	}

	private boolean isStripped( Template template, String link )
	{
		if ( template == null ) { return false; }
		Iterator strips = template.getStrips().iterator();
		while( strips.hasNext() )
		{
			String key = "" + strips.next();
			if ( key.indexOf( "^" ) == 0 ) { key = key + ".*"; }
			else if ( key.indexOf( "$" ) == (key.length()-1) ) { key = ".*" + key; }
			else { key = ".*" + key + ".*"; }
//			Log.infoln( "Strip " + key );
			if ( link.matches( key ) ) { return true; }
		}
		return false;
	}

	public static String url2Filename( URL url, File cacheFolder )
	{
		String host = url.getHost();
		String filename = url.getFile();
        if ((host == null) || (host.equals(""))) 
        {
            // local file - remove leading / or \
            if ((filename.startsWith("\\")) || (filename.startsWith("/"))) { filename = filename.substring(1); }
        } 
        else 
        {
        	/* jmh 2010-11-15
            filename = "/" + host + filename;
            filename = Util.replaceSubstring(filename, "/", "\\");                    
            filename = cacheFolder.getAbsolutePath() + filename;
			filename = filename.replaceAll("[?&=]", "-");
//			Log.infoln( "PATH=" + url.getPath() );
            if ( filename.endsWith("\\") ) { filename += "index.htm"; }
            else if ( Util.getFileExtension( filename ).equalsIgnoreCase("") ) { filename += "\\index.htm"; }
            else if ( url.getPath() == null || url.getPath().equals("") || url.getPath().equals("/") ) { filename += "\\index.htm"; }		// jmh 2010-08-05
            */

        	// jmh 2010-11-15 Matches KStore.downloadUrl()
			File folder = new File( cacheFolder.getAbsolutePath() + "\\" + url.getHost() );
			folder.mkdirs();
			filename = folder.getAbsolutePath() + "\\" + Util.getFilenameFromURL( url );
//			Log.infoln( "  FILE2=" + filename );
        }       
		return filename;
	}

	protected File downloadFile( String url, File folder ) throws MalformedURLException
	{
//		Authenticator.setDefault ( new SpiderAuthenticator() );	
		
		int downloadTimeout = 3000;
		LinkContentDownloader downloader = new LinkContentDownloader( "" + url, folder );

//		File contentFile = new File( url2Filename( new URL( url ), folder ) );	// jmh 2010-11-26
//		downloader.setResultFileName( contentFile.getName() );					// jmh 2010-11-26
//		downloader.setResultFile( contentFile );								// jmh 2010-11-26
		
//		downloader.setUserName( "mhurst@cirilab.com" );
		downloader.getResultFile().delete();
		
		if ( !downloader.getResultFile().exists() )
		{
			downloader.startThread();
			try
			{
				downloader.join( downloadTimeout );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}

			if ( downloader.getResultFile().exists() )
			{
//				Log.infoln( "Created file: length=" + downloader.getResultFile().length() + " " + downloader.getResultFile().getName() );					
			}
			else
			{
				Log.infoln( "TwitterSpider.downloadFile Empty file: " + downloader.getResultFile().getName() );
				downloader.success = false;
				try
				{
					PrintStream writer = Util.getPrintStream( downloader.getResultFile().getAbsolutePath() );
					writer.println( "<!--" + url + "-->" );
					writer.close();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}

			if ( downloader.isAlive() )
			{
				downloader.stopThread();
			}
		}
		return downloader.getResultFile();
	}

}
