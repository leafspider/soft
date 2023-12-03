package soft.tsocial;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.rest.Rest;
import leafspider.util.Log;
import soft.toffee.ToffeeRest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class TsocialResource extends Resource
{
	public static String resource = "tsocial";
	public static String dispatch = "tsocial";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		TsocialRest rest = new TsocialRest();
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
		Log.infoln("TsocialResource: Not meant to be here!" );
	}

	public synchronized void writeResponse( Rest rest ) throws Exception {

		HttpServletResponse response = rest.getResponse();
		String ext = rest.getExtension();

		if ( rest.getProject() != null && rest.getResourceId() == null ) {
			throw new RepresentationException( "Unrecognised resource: " + rest.getResourceId() );
		}
		else {

    		Representation rep = TsocialRepresentation.dispatch( rest.getRequest() );
    		rep.setProject( rest.getProject() );
    		rep.setHostUrl( rest.getHostUrl() );
    		rep.setResourceId( rest.getResourceId() );

    		if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");
				response.getWriter().println( rep.getHtm() );
			}
			else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
	    }
    }
    
}
