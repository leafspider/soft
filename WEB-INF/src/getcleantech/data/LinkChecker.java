package getcleantech.data;

import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.util.Iterator;

import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.Util;

import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;

public class LinkChecker {

	public static void main(String[] args)
	{
		Connection conn = null;
		try 
		{
			File file = new File( "C:\\Workspace\\Ultra\\Susan Sheenan\\Broken Links\\GCT.xml" );
			File folder = new File( "C:\\Workspace\\Ultra\\Susan Sheenan\\Broken Links\\downloads" );
			folder.mkdir();

			SAXBuilder build = new SAXBuilder();
			Element root = build.build(file).getRootElement();

			Iterator<Element> reads = root.getChildren("read").iterator();
			
			while ( reads.hasNext() ) {
				Element read = reads.next();
				String url = read.getChildText("path");
				url = url.replace("getcleantech.com/", "getcleantech.com/platform/" );
				
				downloadFile( url, folder );
				/*
				File target - Downloader.getResultFile() );
				if ( !target.exists() ) {
					if( target.)
					System.out.println( url );
				}

				File dlfile = Downloader.downloadFile( url, folder );
				if( !dlfile.exists() ) {
					System.out.println( url );						
				}
				*/
			}

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static File downloadFile( String url, File folder ) throws MalformedURLException
	{		
		int downloadTimeout = 5000;
		LinkContentDownloader downloader = new LinkContentDownloader( "" + url, folder );
	
		if ( downloader.getResultFile().exists() )
		{
			if( downloader.getResultFile().length() > 0 ) {
				return downloader.getResultFile(); 
			}
			else {
//				Log.infoln( "Empty file: " + url );
				return null;
			}
		}
	
		downloader.startThread();
		try
		{
			downloader.join( downloadTimeout );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}

		if ( !downloader.getResultFile().exists() ) {
//			Log.infoln( "Empty url: " + url );
			downloader.success = false;
			try
			{
				PrintStream writer = Util.getPrintStream( downloader.getResultFile().getAbsolutePath() );
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

		return downloader.getResultFile();
	}
}
