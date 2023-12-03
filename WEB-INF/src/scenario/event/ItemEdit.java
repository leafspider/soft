package scenario.event;

import leafspider.rest.RepresentationException;
import leafspider.util.Util;

import org.jdom2.CDATA;
import org.jdom2.Element;

public class ItemEdit extends ItemRepresentation
{	
	public static String representation = "edit";
    public String getRepresentation() { return representation; }
    	
    public String getXml() throws RepresentationException
    {
    	String id = getResourceId();
//        if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{				
			Element root = new Element( "edit" );			
	   		root.addContent( new Element( "id" ).addContent( new CDATA( "" + id ) ) );
	   		root.addContent( new Element( "pavilion" ).addContent( new CDATA( "" + getProject() ) ) );
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
}
