package soft.asset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.rest.Rest;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

public class AssetResource extends Resource
{
	public static String resource = "asset";
	public static String dispatch = "asset";

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException  {

		AssetRest rest = new AssetRest();
		rest.init(request, response);
		rest.report(resource);

    	try {
   			writeResponse( rest );
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
			Log.infoln( "AssetResource.doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }

    /*
	public void doDelete( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
//		Log.outln( "AssetResource.doGet()" );
    	init( request, response );
    	reportRequest( getRequest(), resource );

    	try {
   			writeResponse( request, response );
		}
		catch( RepresentationException re ) {
			Log.infoln( re.getClass().getCanonicalName() + ": " + re.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, re.getMessage() );			// "404"
		}    	
		catch( IOException ioe ) {
			Log.infoln( ioe.getClass().getCanonicalName() + ": " + ioe.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, ioe.getMessage() );			// "404"
		}    	
		catch( Exception e ) {
			Log.infoln( "AssetResource.doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }
    */

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Log.infoln("AssetResource: Not meant to be here!" );
	}

	public synchronized void writeResponse( Rest rest ) throws Exception {

		HttpServletResponse response = rest.getResponse();
		String ext = rest.getExtension();

		if ( rest.getProject() != null && rest.getResourceId() == null )
		{
    		if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");	
				response.getWriter().println( getListHtm( rest ) );
			}
    		else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");	            
				response.getWriter().println( getListXml( rest ) );
			}
			else if ( ext.equals( "json" ) ) {
				response.setContentType("application/json");
				response.getWriter().println( getListJson( rest ) );
			}
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
		}
		else {

    		Representation rep = AssetRepresentation.dispatch( rest.getRequest() );
    		rep.setProject( rest.getProject() );
    		rep.setHostUrl( rest.getHostUrl() );
    		rep.setResourceId( rest.getResourceId() );

    		if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");	
				response.getWriter().println( rep.getHtm() );
			}
    		else if ( ext.equals( "csv" ) ) {
				response.setContentType("text/html");	                   
				response.getWriter().println( rep.getCsv() );
			}
    		else if ( ext.equals( "gml" ) ) {
				response.setContentType("application/xml");	                               
				response.getWriter().println( rep.getGml() );
			}
    		else if ( ext.equals( "json" ) ) {
				response.setContentType("application/json");	                               
				response.getWriter().println( rep.getJson() );
			}
			else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
	    }
    }

    public String getListHtm( Rest rest ) throws ServletException, IOException, Exception {

		return transform( getListXml( rest ), getXslUrl( resource, dispatch + "List" ) );
    }

	public String getListJson( Rest rest ) throws ServletException, IOException, Exception {

		return JsonConverter.xmlToJson( getListXml( rest ) );
	}
    
    public String getListXml( Rest rest ) throws ServletException, IOException, Exception {

    	int pageLength = 500; 
		int start = 0; try { start = Integer.parseInt( rest.getRequest().getParameter( "start" )); } catch( NumberFormatException nfe ){}
		
		String filter = rest.getRequest().getParameter("f");
		String filterValue = rest.getRequest().getParameter("v");
		String offsetVal = rest.getRequest().getParameter("o");;
		String limitVal = rest.getRequest().getParameter("l");
		String distinctVal = rest.getRequest().getParameter("d");

		int offset = 0; if ( offsetVal != null ) { offset = Integer.parseInt(offsetVal); }
		int limit = 1000; if ( limitVal != null ) { limit = Integer.parseInt(limitVal); }
		boolean distinct = false; if ( distinctVal != null ) { distinct = Boolean.parseBoolean(distinctVal); }

    	Element root = new Element( "CrushRecordList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( rest.getProject() ) ) );

   		try
   		{
			List records = null;

			int effectiveLimit = limit;
			if ( distinct ) { effectiveLimit = limit * 2; }

			String filterSt = "";
			if( filter != null && filterValue != null ) { filterSt = "where " + filter + "='" + filterValue + "' " ; }

			records = CrushProject.getDatabaseManager().select("from CrushRecord " + filterSt + "order by id desc", offset, effectiveLimit);

			root.addContent( new Element( "count" ).addContent( new CDATA( "" + records.size() ) ) );
			root.addContent( new Element( "filter" ).addContent( new CDATA( "" + filter ) ) );

			List<String> distinctTickers = new ArrayList<String>();

			Iterator list = records.iterator();
			while(list.hasNext()) {

				CrushRecord crush = (CrushRecord) list.next();
				if (distinctTickers.contains(crush.getTicker())) { continue; }
				Element elem = crush.listElement();
		   		root.addContent( elem );
		   		if ( distinct ) {
		   			distinctTickers.add(crush.getTicker());
		   			if ( distinctTickers.size() >= limit ) { break; }
		   		}
//				XmlJdomWriter writer = new XmlJdomWriter();
//				Log.infoln( writer.writeToString(elem.clone()) );
			}
   		}
   		catch( Exception e)  { Log.infoln( e ); }
   		
   		return getJdomWriter().writeToString( root );
    }
}
