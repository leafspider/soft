package soft.stores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class StoreRepresentation extends Representation
{
	public static String resource = "store";
    public String getResource() { return resource; }

    public static StoreRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {
       	StoreRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
//    	CIRILogger.infoln( "pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
//	    	else if ( pathInfo.matches( "/.*?/" + StoreSomething.representation + ".*?" ) ) { rep = new StoreSomething(); }
	//    	report( getRequest(), resource );
	
	    	rep.setRequest( request );
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace();
    		throw new RepresentationException( "Unrecognised representation: " + resource + pathInfo );
    	}
    	if ( rep == null )
    	{
    		throw new RepresentationException( "Null representation: " + resource + pathInfo );
    	}
//    	CIRILogger.infoln( "Found dispatch rep=" + rep.representation );
	    	
    	return rep;
    }

}
