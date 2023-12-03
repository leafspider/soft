package soft.stores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class DealRepresentation extends Representation
{
	public static String resource = "deal";
    public String getResource() { return resource; }

    public static DealRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {
       	DealRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
//	    	else if ( pathInfo.matches( "/.*?/" + DealSomething.representation + ".*?" ) ) { rep = new DealSomething(); }
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
