package soft.tsocial;

import leafspider.rest.RepresentationException;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import soft.report.Folders;
import soft.toffee.*;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TsocialPage extends TsocialRepresentation
{
    private boolean debug = false;

    public static String representation = "page";
    public String getRepresentation() { return representation; }

	private String query = null;

    private static DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd (HH:mm)" );

    public static void main(String[] args) throws IOException, TwitterException
    {
    	try {
    		TsocialPage fetch = new TsocialPage();
			fetch.setResourceId("justtradin");
    		System.out.println( fetch.getXml() );
    	}
		catch( Exception e ) { e.printStackTrace(); }
    }

    public String getXml() throws RepresentationException {

    	try {

	        Element root = new Element( "tsocial" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "handle" ).addContent( new CDATA( getResourceId() ) ) );

			return getJdomWriter().writeToString( root );
		}
	    catch( Exception e )  {
	    	throw new RepresentationException( Util.getStackTrace( e ) ); 
	    }    	
    }

}



