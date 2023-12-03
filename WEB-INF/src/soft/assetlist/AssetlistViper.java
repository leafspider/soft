package soft.assetlist;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.batch.BatchProject;
import soft.report.ViperReport;

public class AssetlistViper extends AssetlistRepresentation
{	
	public static String representation = "viper";
    public String getRepresentation() { return representation; }
    
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( "viper" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );

//			Log.debug = true;
			
//			/*
			ViperReport page = new ViperReport();
			page.setResourceId( getResourceId() );
			
			/*
	   		File folder = new File( Folders.heatFolder().getAbsolutePath() + "\\" + viper.getProjectName() );
	   		File outFile = new File( Folders.heatFolder().getAbsolutePath() + "\\" + viper.getProjectName() + "\\output.xml" );

	   		long last = BatchAgent.readTimestamp(folder);
			long dif = Util.getNow() - last;
			if( dif < 21600000 ) {			// 6 hours

				if( outFile != null && outFile.exists() && outFile.length() > 0 ) {
					
					root = new ElementUtil.fileToString(file);
					return; 
				}
			}  							
			*/
			
			page.populate();

			Element xml = page.getRoot();
			root.addContent( Timestamp.getElement( page.getOutputFolder() ) );
			root.addContent( xml );

//			/*
			return getJdomWriter().writeToString( root );
//			*/

			/*	DEBUG
			File file = new File("C:\\Workspace\\Ultra\\Jake Tiley\\ViperReport\\out.xml");
			return Util.fileToString(file, null);
			*/
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
