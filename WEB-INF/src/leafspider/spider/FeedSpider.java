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

import leafspider.util.*;

public class FeedSpider extends Spider 
{
	private static boolean debug = false;
	
	public void runSearch(URL theURL, int depth, int maxFiles)
	{
		try
		{
			Feed feed = new Feed();
	    	feed.parse(theURL.toString());
	    	
			File downloadFolder = new File( getSpiderFolder().getSpiderCollectionFolder() );
			downloadFolder.mkdirs();
			Util.deleteAllFiles( downloadFolder, true );

			StringBuffer log = new StringBuffer();
			
			Hashtable filteredLinks = new Hashtable();
			setUrlTable( new Hashtable() );
			
			Iterator itemList = feed.items.iterator();			
			while(itemList.hasNext())
			{
				Feed.FeedItem item = (Feed.FeedItem) itemList.next();
				String pageUrl = item.link;
				
				File itemFile = downloadFile( item.link, downloadFolder );
				if ( !itemFile.exists() ) 
				{ 
					log.append("Failed to download " + item.link);
					throw new Exception( "Failed to download " + item.link ); 
				}
				else
				{
					filteredLinks.put( itemFile.getAbsolutePath(), pageUrl );
					log.append("Downloaded " + item.link);
				}
			}	
			setUrlTable( filteredLinks );
			
			Util.writeAsFile( log.toString(), getSpiderLog().getAbsolutePath() );			
			if ( debug )
			{
				Log.infoln( "links=" + filteredLinks.size() );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
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
				Log.infoln( "FeedSpider.downloadFile Empty file: " + downloader.getResultFile().getName() );
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
