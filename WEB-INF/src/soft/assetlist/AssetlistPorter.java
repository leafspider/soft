package soft.assetlist;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.report.PorterMap;

import java.text.DateFormat;
import java.util.Calendar;

public class AssetlistPorter extends AssetlistRepresentation
{	
	public static String representation = "pearpicks";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try {

//			String endParm = getRequest().getParameter( "end" );
			String topParm = getRequest().getParameter( "top" );

			Element root = new Element( "pearpicks" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );

			PorterMap page = new PorterMap();
			page.setResourceId( getResourceId() );
			if ( topParm != null ) { page.setTop( Integer.parseInt(topParm) ); }

//			Log.debug = true;
			
			/*
			String xml = "";			
			
			File outFile = new File( page.getOutputFolder() + "\\output.xml" );
			if( outFile.exists() )
			{
				xml = Util.fileToString(outFile, "\n");
			}
			else
			{
				page.populate();
				xml = page.getXml();
				
				PrintStream out = Util.getPrintStream( outFile.getAbsolutePath() );
				out.print(xml);
			}
			*/
			
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
