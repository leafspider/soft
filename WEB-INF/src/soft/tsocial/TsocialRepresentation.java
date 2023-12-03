package soft.tsocial;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import soft.toffee.ToffeeGrid;

import javax.servlet.http.HttpServletRequest;

public abstract class TsocialRepresentation extends Representation
{
	public static String resource = "tsocial";
    public String getResource() { return resource; }

    public static TsocialRepresentation dispatch(HttpServletRequest request ) throws RepresentationException {

		if ( debugResource( resource ) ) { reportParameters( request ); }
    	
    	TsocialRepresentation rep = null;
    	String pathInfo = request.getPathInfo();

       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + TsocialPage.representation + ".*?" ) ) { rep = new TsocialPage(); }
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
