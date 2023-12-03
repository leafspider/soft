package soft.assetlist;

import leafspider.rest.RepresentationException;
import leafspider.util.Timestamp;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import soft.report.ViperReport;
import soft.rules.RulesReport;

public class AssetlistRules extends AssetlistRepresentation
{	
	public static String representation = "rules";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( "rules" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );

			RulesReport page = new RulesReport();
			page.setResourceId( getResourceId() );

			page.populate();

			Element xml = page.getRoot();
			root.addContent( Timestamp.getElement( page.getOutputFolder() ) );
			root.addContent( xml );

			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
