package soft.toffee;

import javax.servlet.http.HttpServletRequest;

import leafspider.util.Log;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;

public abstract class ToffeeRepresentation extends Representation
{
	public static String resource = "toffee";
    public String getResource() { return resource; }

    public static ToffeeRepresentation dispatch( HttpServletRequest request ) throws RepresentationException {

		if ( debugResource( resource ) ) {
			reportParameters( request );
		}
    	
    	ToffeeRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
    	Log.infoln( "pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches(
	    			"/.*?/" + ToffeeGrid.representation + ".*?" ) ) { rep = new ToffeeGrid();
				String query = request.getParameter("query");
				((ToffeeGrid) rep).setQuery(query);
	    	}
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
	    	
    	return rep;
    }
}
