package scenario.event;

import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;

public abstract class ItemRepresentation extends Representation
{
	public static String resource = "item";
    public String getResource() { return resource; }

    public static ItemRepresentation dispatch( HttpServletRequest request ) throws RepresentationException
    {    	
    	ItemRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
    	Log.infoln( "ItemRepresentation.dispatch.pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + ItemEdit.representation + ".*?" ) ) { rep = new ItemEdit(); }
	    	Log.infoln( "ItemRepresentation=" + rep.getClass().getName() );
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
