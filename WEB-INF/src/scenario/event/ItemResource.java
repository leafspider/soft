package scenario.event;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.ObResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.jdom2.Element;
import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import scenario.more.CsvRowOCE;

import leafspider.extract.TextExtractor;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.spider.LinkContentDownloader;
import leafspider.spider.Spider;
import leafspider.spider.SpiderFolder;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class ItemResource extends ObResource
{
	public static String resource = "item";
	public static String dispatch = "item";

    protected void init( HttpServletRequest request, HttpServletResponse response )
    {
    	super.init( request, response ); 
    	try
    	{
    		parsePathInfo( request.getPathInfo() );
    	}
    	catch( Exception e )
    	{
   	    	response.setStatus( 404 );
    	}
    }
    
    protected void parsePathInfo( String pathInfo )
    {
    	String[] vals = pathInfo.split( "/" );    	

    	// vals[0] is blank
    	if ( vals.length > 1 )
    	{
    		setProject( Util.removeFileExtension( vals[1] ) );
    	}
    	if ( vals.length > 2 )
    	{   	
    		setResourceId( vals[2] );    	
    	}
    	else 
    	{ 
    		setResourceId( null ); 
    	}
		setExtension( Util.getFileExtension( vals[vals.length-1] ) );		
    }
    
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
//		Log.outln( getClass().getName() + ".doGet()" );
    	init( request, response );
    	reportRequest( getRequest(), resource );

    	try
    	{    
   			writeResponse( request, response );
		}
		catch( RepresentationException re )
		{
			Log.infoln( re.getClass().getCanonicalName() + ": " + re.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, re.getMessage() );			// "404"
		}    	
		catch( IOException ioe )
		{
			Log.infoln( ioe.getClass().getCanonicalName() + ": " + ioe.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, ioe.getMessage() );			// "404"
		}    	
		catch( Exception e )
		{
			e.printStackTrace();
			Log.infoln( getClass().getName() + ".doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
//	    	Log.infoln( "writeResponse.pathInfo=" + getRequest().getPathInfo() );
    	
		Representation rep = ItemRepresentation.dispatch( getRequest() );
		rep.setProject( getProject() );
		rep.setHostUrl( getHostUrl() );
		rep.setResourceId( getResourceId() );
		
		if ( getExtension().equals( "htm" ) )
		{
			response.setContentType("text/html");	
			response.getWriter().println( rep.getHtm() );
		}
		else if ( getExtension().equals( "xml" ) )
		{
			response.setContentType("application/xml");	            
			response.getWriter().println( rep.getXml() );
		}	
		else
		{
			throw new RepresentationException( "Unrecognised format: " + getExtension() );
		}	    	    		
    }
	
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {    			
    	init( request, response );
    	reportRequest( getRequest(), resource );

		try
		{    
			String name = null;
			String url = null;
			String pavilion = null;
			String category = null;
			String place = "";
			String cuesurl = null;
			String desc = null;
			String hall = "";
			String stand = "";
			String fileName = null;
			File cachedFile = null;

			Log.infoln( "ItemResource: contentType=" + getContentType() );
			
			if ( getContentType().indexOf( "multipart/form-data" ) == 0 )
			{
				Log.infoln( "ItemResource: pathInfo=" + getRequest().getPathInfo() );

				CueWriter cwr = new CueWriter();
								
				ServletFileUpload servletFileUpload = new ServletFileUpload(new DiskFileItemFactory());
					            
				Iterator files = servletFileUpload.parseRequest(request).iterator();
				while (files.hasNext())
				{
					FileItem fileItem = (FileItem)files.next();
					if (fileItem.isFormField())
					{
						String fieldName = fileItem.getFieldName();
						if ( fieldName.equals( "name" ) )			{ name = fileItem.getString();}
						if ( fieldName.equals( "url" ) )			{ url = fileItem.getString();}
						if ( fieldName.equals( "pavilion" ) ) 	{ pavilion = fileItem.getString();}					  
						if ( fieldName.equals( "category" ) ) 	{ category = fileItem.getString();}
						if ( fieldName.equals( "place" ) ) 		{ place = fileItem.getString();}
//						if ( fieldName.equals( "pavlink" ) ) 		{ pavlink = fileItem.getString();}					  
						if ( fieldName.equals( "cuesurl" ) ) 		{ cuesurl = fileItem.getString();}
						if ( fieldName.equals( "description" ) )	{ desc = fileItem.getString();}						
						if ( fieldName.equals( "hall" ) )	{ hall = fileItem.getString();}
						if ( fieldName.equals( "stand" ) )	{ stand = fileItem.getString();}
					}
					else
					{
						String itemPath = fileItem.getName();		// Usually just filename but could be entire path
						File itemFile = new File( itemPath );
						fileName = itemFile.getName();
						if( fileName == null || fileName.trim().length() == 0 ) 
						{ 
							Log.infoln( "ItemResource: no file" );
							break; 
						}			// No file present
						
						int rand = (int) (Math.random() * 1000000);					
						File uploadFolder = new File( cwr.getFileRoot( pavilion ).getAbsolutePath() + "\\" + rand );
						uploadFolder.mkdirs();

						cachedFile = new File( uploadFolder.getAbsolutePath() + "\\" + fileName );
						Log.infoln( "ItemResource: file=" + cachedFile.getAbsolutePath() );
						fileItem.write( cachedFile );
					}
				}
				Log.infoln( "ItemResource: name=" + name );
				Log.infoln( "ItemResource: pavilion=" + pavilion );

				File fileRoot = cwr.getFileRoot( pavilion );
				fileRoot.mkdirs();
				
				category = category.replaceAll("[,]", " ");
				name = name.replaceAll("[,]", " ");
				place = place.replaceAll("[,]", " ");
				url = url.replaceAll("[,]", " ");
				desc = desc.replaceAll("[,\\n\\r]", " ");

				String row = category + "," + name + "," + place + ",,," + pavilion + "," + url + "," + desc + "," + cuesurl + "," + hall + "," + stand;
				File csvFile = new File( fileRoot.getAbsolutePath() + "\\" + Util.getCleanFileName(name) + ".csv" );
				PrintStream output = new PrintStream(new FileOutputStream(csvFile));
				output.println( row );
				output.close();
								
				cwr.createCues( csvFile );

				/*
				String fid = pavilion.toUpperCase() + "%5C";
				String cid = fid + category + place + "%5C" + Util.replaceSubstring(name, " ", "%20");
				
				createAlerts( pavilion, fid, cid );
				*/
				
//		    	String xslUrl = "http://127.0.0.1:7958/" + KStore.appName + "/skins/admin/uploadFile.xsl";				// jmh 2011-09-16		
//		    	File xsl = new File( ServerContext.getServerWebappAppSkinsFolder() + "\\admin\\uploadFile.xsl" );
//		    	response.getWriter().println( Representation.transform( getJdomWriter().writeToString( root ), xsl ) );
				
	   			writeResponse( request, response );
			}
			/*
			else if ( getContentType() == null )
			{
				Log.infoln( "Error 415: Unrecognised Content-Type=" + getContentType() );				
				response.setStatus( 415 );
			}
			else if ( getContentType().equals( "application/xml" ) )
			{
				processXml( request );
			}
			*/
			else
			{
				Log.infoln( "Error 415: Unrecognised Content-Type=" + getContentType() );				
				response.setStatus( 415 );
			}
		}
		catch( Exception e )
		{
			response.setStatus( 404 );
			throw new ServletException( e );
		}
    }

	private void createAlerts( String pavilion, String fid, String cid ) throws Exception
	{
		File targetFolder = new File( ServerContext.getDataFolder() + "\\feed" );
		LinkContentDownloader dloader2 = new LinkContentDownloader( "http://127.0.0.1:8000/" + pavilion + "/alerts.post?f=&fid=" + fid + "&cid=" + cid, targetFolder );
//		LinkContentDownloader dloader2 = new LinkContentDownloader( "http://127.0.0.1:8000/" + pavilion + "/alerts.post?f=kubeId&fid=" + fid, targetFolder );
		dloader2.startThread();
		dloader2.join();
	}
		
}
