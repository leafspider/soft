package sagegate;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

public class FrankEmail extends FrankRepresentation
{	
	public static String representation = "email";
    public String getRepresentation() { return representation; }
    	
    public String getXml() throws RepresentationException
    {
    	String id = getResourceId();
        if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{				
			Element root = new Element( "email" );
	   		root.addContent( new Element( "id" ).addContent( new CDATA( "" + id ) ) );
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    public String getHtm() throws Exception
    {	
    	String id = getResourceId();
    	
		Log.infoln( "FrankEmail.id=" + id );
		Log.infoln( "FrankEmail.project=" + getProject() );
		
		if( !getProject().equals("server") ) { return transform( getXml(), getXslUrl() ); }
		else
		{
			Frank frank = new Frank( "2739608259600C98E6030BB47580A84D" );

			String html = "";	//frank.openEmailForm();

			return html;			
		}		
    }
}
