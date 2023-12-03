package leafspider.spider;

import net.matuschek.http.HttpConstants;
import net.matuschek.http.HttpDoc;
import net.matuschek.http.HttpException;
import net.matuschek.http.HttpHeader;
import net.matuschek.spider.WebRobot;
import net.matuschek.spider.RobotTask;

import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;
import java.io.File;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.net.MalformedURLException;
import java.net.URLConnection;

import org.apache.log4j.Category;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import leafspider.util.ServerContext;
import leafspider.util.Util;

public class KWebRobot extends WebRobot
{	
	public void walkTree() 
	{			
		FileAppender appender = null;
		try { appender = new FileAppender( new PatternLayout(), getLogFile().getAbsolutePath(), false );} 
		catch(Exception e) 
		{
			e.printStackTrace();
			return;
		}			
		Logger newlog = Logger.getLogger( "SpiderLogger" );
		newlog.addAppender( appender );
		log = newlog;

		while ((todo.size() > 0) && (!stopIt)) 
        {
        	RobotTask task;
            synchronized (visited) 
            {
                task = todo.removeFirst();
             
                URL url = task.getUrl();
                
                /*
                try
                {
	                URLConnection conn = url.openConnection();	
	                String username = "mpagah";
	                String password = "leonardo27";
	                String usernamePassword = username + ":" + password;
					String encoding = new sun.misc.BASE64Encoder().encode( usernamePassword.getBytes() );
	                conn.setRequestProperty("Authorization", "Basic " + encoding);
                }
                catch( Exception e )
                {
                	e.printStackTrace();
                }
                */
                
//        		File cacheFolder = new File( getData().getWorkspace().getPath() + "spiderCache" );		// jmh 2010-09-29
        		File cacheFolder = new File( getSpiderCacheFolder() );
                String filename = url2Filename( url, cacheFolder );
                
                File theFile = new File( filename );
                if ( FileStopper.isSupported( theFile ) )		// jmh 2010-06-21 Very odd behaviour with File.exists()
    			{
//                	if (filename.indexOf( "cognitive" ) > -1 ) { Log.infoln( "--- PUT " + filename + " " + url.toString() ); }
                	getUrlTable().put(filename, url.toString());
    			}
                else
                {
//                	Log.infoln( "-- NOT PUT -- " + filename + " " + url.toString() );
                }
                
                if (visited.contains(task) && (!visitMany.contains(task.getUrl().toString()))) 
                {
                	log.debug("already visited: " + task.getUrl());
                    continue;
                }
                if (activatedUrlHistory) 
                {
                    visited.add(task);
                }
            }

            boolean repeat = true;
            while (repeat) 
            {
                try 
                {
                    retrieveURL(task);
                    repeat = false;
                } 
                catch (OutOfMemoryError memoryError) 
                {
                    handleMemoryError(memoryError);
                }
            }

            // sleep, if sleep is set to true
            while (sleep) 
            {
                // callback
                if (webRobotCallback != null) 
                {
                    webRobotCallback.webRobotSleeping(true);
                }
                try 
                {
                    Thread.sleep(1000);
                } 
                catch (InterruptedException e) {}
            }

            // callback
            if (webRobotCallback != null) 
            {
                webRobotCallback.webRobotSleeping(false);
            }

            // callback
            if (webRobotCallback != null) 
            {
                webRobotCallback.webRobotUpdateQueueStatus(todo.size());
            }
            spawnThread();
        }

        // callback
        if (webRobotCallback != null) 
        {
            finishThreads();
        }
    }
  
	/*
    public HttpDoc retrieveFileURL(URL url, Date ifModifiedSince) throws HttpException 
    {
    	Log.infoln( "URL=====" + url );
        
        HttpDoc doc = new HttpDoc();
        try 
        {
            String host = url.getHost();
            String filename = url.getFile();
            if ((host == null) || (host.equals(""))) 
            {
                // local file
                // remove leading / or \
                if ((filename.startsWith("\\")) || (filename.startsWith("/"))) 
                {
                    filename = filename.substring(1);
                }
            } 
            else 
            {
                filename = "//" + host + filename;
            }
            // get the mimetype and put in the http header
            String mimetypestr = getMimeTypeForFilename(filename);
            if (mimetypestr != null) 
            {
                HttpHeader header = new HttpHeader("content-type", mimetypestr);
                doc.addHeader(header);
            }

            // get the content from the file
            File file = new File(filename);
            if (!file.exists()) 
            {
                doc.setHttpCode("httpcode " + HttpConstants.HTTP_NOTFOUND);
                return doc;
            }
            long fileLastModified = file.lastModified();
            long ifModifiedSinceTime = ifModifiedSince == null ? 0 : ifModifiedSince.getTime();
            if (fileLastModified > ifModifiedSinceTime) 
            {
                byte[] content = readFileToByteArray(file);
                doc.setContent(content);
                doc.setHttpCode("httpcode " + HttpConstants.HTTP_OK);
            } 
            else 
            {
                doc.setHttpCode("httpcode " + HttpConstants.HTTP_NOTMODIFIED);
            }
            doc.setLastModified(fileLastModified);
            doc.setDate(System.currentTimeMillis());
            doc.setURL(url);

            return doc;
        } 
        catch (Exception e) 
        {
            throw new HttpException(e.getMessage());
        }
    }
    */

	private Hashtable urlTable = null;
	public Hashtable getUrlTable() 
	{
		if ( urlTable == null )
		{
			urlTable = new Hashtable();
		}
		return urlTable;
	}
	public void setUrlTable(Hashtable urlTable) {
		this.urlTable = urlTable;
	}

	public String getSpiderCollectionFolder()
	{
		return new String( ServerContext.getDataFolder() + "\\spider\\" );
	}

	public String getSpiderCacheFolder()
	{
		return new String( ServerContext.getDataFolder() + "\\cache\\" );
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
//			Log.infoln( "  FILE1=" + filename );
        }       
		return filename;
	}

	private File logFile = null;
	public File getLogFile() {
		return logFile;
	}
	public void setLogFile(File logFile) {
		this.logFile = logFile;
	}
}
