package soft.ob;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.jdom2.Element;


import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class CrushRepresentation extends Representation
{
	public static String resource = "crush";
    public String getResource() { return resource; }

    public static CrushRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {
       	CrushRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
//    	CIRILogger.infoln( "pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + CrushSnapshot.representation + ".*?" ) ) { rep = new CrushSnapshot(); }
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
