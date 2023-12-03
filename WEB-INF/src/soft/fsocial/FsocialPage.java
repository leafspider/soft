package soft.fsocial;

import leafspider.rest.RepresentationException;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import twitter4j.TwitterException;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class FsocialPage extends FsocialRepresentation
{
    private boolean debug = false;

    public static String representation = "page";
    public String getRepresentation() { return representation; }

	private String query = null;

    private static DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd (HH:mm)" );

    public static void main(String[] args) throws IOException, TwitterException
    {
    	try {
    		FsocialPage fetch = new FsocialPage();
			fetch.setResourceId("justtradin");
    		System.out.println( fetch.getXml() );
    	}
		catch( Exception e ) { e.printStackTrace(); }
    }

    public String getXml() throws RepresentationException {

    	try {

	        Element root = new Element( "fsocial" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "handle" ).addContent( new CDATA( getResourceId() ) ) );

			return getJdomWriter().writeToString( root );
		}
	    catch( Exception e )  {
	    	throw new RepresentationException( Util.getStackTrace( e ) ); 
	    }    	
    }

}



