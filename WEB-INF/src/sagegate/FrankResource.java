package sagegate;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.ObResource;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;

public class FrankResource extends ObResource
{
	public static String resource = "frank";
	public static String dispatch = "frank";

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
	    	
    		Representation rep = FrankRepresentation.dispatch( getRequest() );
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
