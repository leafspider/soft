package leafspider.spider;

import net.matuschek.http.HttpDoc;
import net.matuschek.http.HttpDocToFile;
import net.matuschek.http.DocManagerException;

import java.io.BufferedOutputStream;  
import java.io.File;
import java.io.FileOutputStream;   
import java.io.IOException;   
import java.net.URL;

import leafspider.util.*;

public class KHttpDocToFile extends HttpDocToFile
{
	public KHttpDocToFile( String st )
	{
		super( st );		
	}
	
	public void storeDocument(HttpDoc doc) throws DocManagerException   
	{
//  	  	Log.infoln( "  url=" + doc.getURL() );
  	  	
		if ((doc == null) || (doc.getContent() == null)) 
		{   
	  	  	Log.warnln( "URL has no content: " + doc.getURL() );
			return;   
	    }   

		// write file only, if this was NOT a cached document (in this case we have it already on harddisk)
	    if (doc.isCached()) 
	    {   
	  	  	Log.warnln( "URL is cached: " + doc.getURL() );
	    	return;   
	    }

	    // do not store dynamic pages, because storeCGI is false and the URL contains a "?"
	    if ((! getStoreCGI()) && (doc.getURL().toString().indexOf('?') >= 0)) 
	    {   
//	  	  	Log.warnln( "URL is dynamic: " + doc.getURL() );
//	  	  	return;   			// jmh 2009-08-26
	    }
	   
	    String filename = url2Filename(doc.getURL());
	    /* jmh 2010-06-22
	    if ( Util.getFileExtension( filename ).equals("") )		// jmh 2010-06-21
	    {
	    	filename += ".htm";
	    }
	    */
//  	  	Log.infoln( "KHttpDocToFile.storeDocument filename=" + filename );
	    if (doc.getContent().length >= getMinFileSize()) 
	    {
	      try 
	      {   
	    	  createDirs(filename);   
	    	  BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(filename));   
	    	  os.write(doc.getContent());   
	    	  os.flush();   
	    	  os.close();   
	      } 
	      catch (IOException e) 
	      {   
	        throw new DocManagerException(e.getMessage());   
	      }   
	    }   
	}   
	
	public String url2Filename( URL url )
	{
		File cacheFolder = new File( getBaseDir() );
		return KWebRobot.url2Filename( url, cacheFolder );
	}

	public static void main( String[] args )
	{
		try
		{
			KHttpDocToFile doc = new KHttpDocToFile( "C:\\CIRILab\\kgs\\work\\read\\itw\\http\\spiderCache" );
			doc.url2Filename( new URL("http://twitter.com/acxiom?max_id=16798884795&page=2&twttr=true" ) );
			
			System.out.println( "done" );
		}
		catch( Exception e) { e.printStackTrace(); }
	}
}
