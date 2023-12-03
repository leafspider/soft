package soft.stores;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class CityRepresentation extends Representation
{
	public static String resource = "city";
    public String getResource() { return resource; }

    public static CityRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {
       	CityRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
//	    	else if ( pathInfo.matches( "/.*?/" + CitySomething.representation + ".*?" ) ) { rep = new CitySomething(); }
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
