package soft.fsocial;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;

import javax.servlet.http.HttpServletRequest;

public abstract class FsocialRepresentation extends Representation
{
	public static String resource = "fsocial";
    public String getResource() { return resource; }

    public static FsocialRepresentation dispatch(HttpServletRequest request ) throws RepresentationException {

		if ( debugResource( resource ) ) { reportParameters( request ); }
    	
    	FsocialRepresentation rep = null;
    	String pathInfo = request.getPathInfo();

       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + FsocialPage.representation + ".*?" ) ) { rep = new FsocialPage(); }
	    	rep.setRequest( request );
    	}
    	catch( Exception e ) {
    		e.printStackTrace();
    		throw new RepresentationException( "Unrecognised representation: " + resource + pathInfo );
    	}
    	if ( rep == null ) {
    		throw new RepresentationException( "Null representation: " + resource + pathInfo );
    	}
	    	
    	return rep;
    }
}
