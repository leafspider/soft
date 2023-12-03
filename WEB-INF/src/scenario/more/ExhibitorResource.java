package scenario.more;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.ObResource;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.util.*;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jdom2.CDATA;
import org.jdom2.Element;

public class ExhibitorResource extends ObResource
{
	public static String resource = "exhibitor";
	public static String dispatch = "exhibitor";

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
    	/*
		int pos = pathInfo.indexOf("?");
		if( pos > -1 ) { pathInfo = pathInfo.substring(0, pos); }
		*/
		
    	String[] vals = pathInfo.split( "/" );    	

//    	Log.infoln( "ExhibitorResource.parsePathInfo.pathInfo=" + pathInfo );
//		Log.infoln( "vals.length=" + vals.length );
//    	for ( int i = 0; i < vals.length; i++ ) { Log.infoln( "vals=" + vals[i] ); }
//    	String res = null;

    	// vals[0] is blank
    	if ( vals.length > 1 )
    	{
    		setProject( Util.removeFileExtension( vals[1] ) );
    	}
    	if ( vals.length > 2 )
    	{
//    		setResourceId( Util.removeFileExtension( vals[2] ).toUpperCase() );    	
//    		setResourceId( vals[2].toUpperCase() );    	
    		setResourceId( vals[2] );    	
    	}
    	else 
    	{ 
    		setResourceId( null ); 
    	}
		setExtension( Util.getFileExtension( vals[vals.length-1] ) );
		
    	/*
		Log.outln( "collection=" + getCollection() );
		Log.outln( "resourceId=" + getResourceId() );
		Log.infoln( "extension=" + getExtension() );
//		*/
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
	    	
    		Representation rep = ExhibitorRepresentation.dispatch( getRequest() );
    		rep.setProject( getProject() );
    		rep.setHostUrl( getHostUrl() );
    		rep.setResourceId( getResourceId() );
    		
    		if ( getExtension().equals( "htm" ) )
			{
				response.setContentType("text/html");	
				response.getWriter().println( rep.getHtm() );
			}
    		else if ( getExtension().equals( "csv" ) )
			{
				response.setContentType("text/html");	                   
				response.getWriter().println( rep.getCsv() );
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

}
