package soft.toffee;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.Rest;
import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.AssetRepresentation;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.util.Log;
import leafspider.util.Util;

public class ToffeeResource extends Resource
{
	public static String resource = "toffee";
	public static String dispatch = "toffee";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		ToffeeRest rest = new ToffeeRest();
		rest.init(request, response);
		rest.report(resource);

    	try {
   			writeResponse( rest );
		}
		catch( Exception re ) {
			re.printStackTrace();
			Log.infoln( re.getClass().getCanonicalName() + ": " + re.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, re.getMessage() );			// "404"
		}    	
    }

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Log.infoln("FsocialResource: Not meant to be here!" );
	}

	public synchronized void writeResponse( Rest rest ) throws Exception {

		HttpServletResponse response = rest.getResponse();
		String ext = rest.getExtension();

		if ( rest.getProject() != null && rest.getResourceId() == null ) {

			/*
			if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");
				Log.infoln("trout");
//				response.getWriter().println( getListHtm( rest ) );
			}
    		else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");
				Log.infoln("trout");
//				response.getWriter().println( getListXml( rest ) );
			}
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
			*/
			throw new RepresentationException( "Unrecognised resource: " + rest.getResourceId() );
		}
		else {

    		Representation rep = ToffeeRepresentation.dispatch( rest.getRequest() );
    		rep.setProject( rest.getProject() );
    		rep.setHostUrl( rest.getHostUrl() );
    		rep.setResourceId( rest.getResourceId() );

    		if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");
				response.getWriter().println( rep.getHtm() );
			}
    		else if ( ext.equals( "csv" ) ) {
				response.setContentType("text/html");	                   
				response.getWriter().println( rep.getCsv() );
			}
    		else if ( ext.equals( "gml" ) ) {
				response.setContentType("application/xml");	                               
				response.getWriter().println( rep.getGml() );
			}
			else if ( ext.equals( "json" ) ) {
				response.setContentType("application/json");
				response.getWriter().println( rep.getJson() );
			}
			else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
	    }
    }
    
}
