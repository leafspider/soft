package scenario.more;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class ExhibitorRepresentation extends Representation
{
	public static String resource = "exhibitor";
    public String getResource() { return resource; }

    public static ExhibitorRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {    	
       	ExhibitorRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
    	Log.infoln( "ExhibitorRepresentation.dispatch.pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + MWCExhibitorFeed.representation + ".*?" ) ) { rep = new MWCExhibitorFeed(); }
	    	else if ( pathInfo.matches( "/.*?/" + MWCSpeakerFeed.representation + ".*?" ) ) { rep = new MWCSpeakerFeed(); }
	    	else if ( pathInfo.matches( "/.*?/" + ExhibitorFeed.representation + ".*?" ) ) { rep = new ExhibitorFeed(); }
	    	Log.infoln( "ExhibitorRepresentation=" + rep.getClass().getName() );
//	    	report( request, resource );
	
	    	rep.setRequest( request );
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace();
    		throw new RepresentationException( "Unrecognised representation: " + resource + " " + pathInfo );
    	}
    	if ( rep == null )
    	{
    		throw new RepresentationException( "Null representation: " + resource + " " + pathInfo );
    	}
//    	CIRILogger.infoln( "Found dispatch rep=" + rep.representation );
	    	
    	return rep;
    }

}
