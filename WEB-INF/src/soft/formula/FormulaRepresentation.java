package soft.formula;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;

import javax.servlet.http.HttpServletRequest;

public abstract class FormulaRepresentation extends Representation
{
	public static String resource = "formula";
    public String getResource() { return resource; }

    public static FormulaRepresentation dispatch(HttpServletRequest request ) throws RepresentationException {

		if ( debugResource( resource ) ) { reportParameters( request ); }
    	
    	FormulaRepresentation rep = null;
    	String pathInfo = request.getPathInfo();

       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + FormulaGrid.representation + ".*?" ) ) { rep = new FormulaGrid(); }
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
