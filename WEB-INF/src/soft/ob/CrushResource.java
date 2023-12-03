package soft.ob;

import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.ObResource;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.util.*;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.stores.HibernateManager;


public class CrushResource extends ObResource
{
	public static String resource = "crush";
	public static String dispatch = "crush";

    protected void init( HttpServletRequest request, HttpServletResponse response )
    {
    	super.init( request, response ); 
    	try
    	{
    		parsePathInfo( request.getPathInfo() );
    	}
    	catch( Exception e )
    	{
   	    	response.setStatus( 404 );
    	}
    }
    
    protected void parsePathInfo( String pathInfo )
    {
    	// /itw/BOB/interlinks.htm    	
    	// /jake/fgh/crush.htm
    	String[] vals = pathInfo.split( "/" );    	

//    	Log.outln( "AssetResource pathInfo=" + pathInfo );
//		Log.infoln( "vals.length=" + vals.length );
//    	for ( int i = 0; i < vals.length; i++ ) { Log.infoln( "vals=" + vals[i] ); }
//    	String res = null;

    	// vals[0] is blank
    	if ( vals.length > 1 )
    	{
    		setProject( Util.removeFileExtension( vals[1] ) );
    	}
    	if ( vals.length > 2 )
    	{
//    		setResourceId( Util.removeFileExtension( vals[2] ).toUpperCase() );    	
    		setResourceId( vals[2].toUpperCase() );    	
    	}
    	else 
    	{ 
    		setResourceId( null ); 
    	}
		setExtension( Util.getFileExtension( vals[vals.length-1] ) );
		
    	/*
		Log.outln( "collection=" + getCollection() );
		Log.outln( "resourceId=" + getResourceId() );
		Log.outln( "extension=" + getExtension() );
//		*/
    }
    
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
//		Log.outln( "CrushResource.doGet()" );
    	init( request, response );
    	reportRequest( getRequest(), resource );

    	try
    	{    	
   			writeResponse( request, response );
		}
		catch( RepresentationException re )
		{
			Log.infoln( re.getClass().getCanonicalName() + ": " + re.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, re.getMessage() );			// "404"
		}    	
		catch( IOException ioe )
		{
			Log.infoln( ioe.getClass().getCanonicalName() + ": " + ioe.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, ioe.getMessage() );			// "404"
		}    	
		catch( Exception e )
		{
			e.printStackTrace();
			Log.infoln( "CrushResource.doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
//	    Log.infoln( "pathInfo=" + getRequest().getPathInfo() + " project=" + getProject() + " resourceId=" + getResourceId() );
    	
		if ( getProject() != null && getResourceId() == null )
		{
    		if ( getExtension().equals( "htm" ) )
			{
				response.setContentType("text/html");	
				response.getWriter().println( getListHtm() );
			}
    		else if ( getExtension().equals( "xml" ) )
			{
				response.setContentType("application/xml");	            
				response.getWriter().println( getListXml() );
			}	
			else
			{
				throw new RepresentationException( "Unrecognised format: " + getExtension() );
			}
		}
		else
		{
			Representation rep = CrushRepresentation.dispatch( getRequest() );
			rep.setProject( getProject() );
			rep.setHostUrl( getHostUrl() );
			rep.setResourceId( getResourceId() );
			
			if ( getExtension().equals( "htm" ) )
			{
				response.setContentType("text/html");	
	//					Log.infoln( "FP=" + read.getRecord().getIndexEntry().getSourceFilePath() );
				response.getWriter().println( rep.getHtm() );
			}
			else if ( getExtension().equals( "csv" ) )
			{
				response.setContentType("text/html");	                   
				response.getWriter().println( rep.getCsv() );
			}
			else if ( getExtension().equals( "gml" ) )
			{
				response.setContentType("application/xml");	                               
				response.getWriter().println( rep.getGml() );
			}
			else if ( getExtension().equals( "xml" ) )
			{
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else
			{
				throw new RepresentationException( "Unrecognised format: " + getExtension() );
			}	    	    		
		}
    }

    public String getListHtm() throws ServletException, IOException, Exception
    {	
		return transform( getListXml(), getXslUrl( resource, dispatch + "List" ) );
    }
    
    public String getListXml() throws ServletException, IOException, Exception
    {
    	int pageLength = 500; 
		int start = 0; try { start = Integer.parseInt( getRequest().getParameter( "start" )); } catch( NumberFormatException nfe ){}
		
    	Element root = new Element( "CrushRecordList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );

   		List records = CrushProject.getDatabaseManager().listRecords("CrushRecord");
   		root.addContent( new Element( "count" ).addContent( new CDATA( "" + records.size() ) ) );

   		Iterator list = records.iterator();
		while(list.hasNext())
		{
			CrushRecord crush = (CrushRecord) list.next();
			Element elem = crush.listElement();
	   		root.addContent( elem );
//			XmlJdomWriter writer = new XmlJdomWriter();
//			Log.infoln( writer.writeToString(elem) );
		}
   		
   		return getJdomWriter().writeToString( root );
    }
}
