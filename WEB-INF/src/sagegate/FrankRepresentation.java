package sagegate;

import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;

public abstract class FrankRepresentation extends Representation
{
	public static String resource = "frank";
    public String getResource() { return resource; }

    public static FrankRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {    	
       	FrankRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
    	Log.infoln( "FrankRepresentation.dispatch.pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + FrankEmail.representation + ".*?" ) ) { rep = new FrankEmail(); }
	    	Log.infoln( "FrankRepresentation=" + rep.getClass().getName() );
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
