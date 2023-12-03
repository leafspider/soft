package soft.performance;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.rest.ObResource;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.util.Log;
import leafspider.util.Util;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jdom2.CDATA;
import org.jdom2.Element;


public class TradeResource extends ObResource
{
	public static String resource = "trade";
	public static String dispatch = "trade";

    protected void init( HttpServletRequest request, HttpServletResponse response )
    {
    	super.init( request, response ); 
    	try { parsePathInfo( request.getPathInfo() ); }
    	catch( Exception e ) { response.setStatus( 404 ); }
    }
    
    protected void parsePathInfo( String pathInfo )
    {
		System.out.println("pathInfo=" + pathInfo);
    	String[] vals = pathInfo.split( "/" );    	
    	if ( vals.length > 1 )	// vals[0] is blank
    	{
    		setProject( Util.removeFileExtension( vals[1] ) );
    	}
    	if ( vals.length > 2 )
    	{
    		setResourceId( vals[2].toLowerCase() );
    		System.out.println("resourceId=" + getResourceId());
    	}
    	else 
    	{ 
    		setResourceId( null ); 
    	}
		setExtension( Util.getFileExtension( vals[vals.length-1] ) );
    }
    
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
		Log.infoln( "PicksResource.doGet()" );
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
			Log.infoln( "StoreResource.doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception
    {
	    Log.infoln( "pathInfo=" + getRequest().getPathInfo() + " project=" + getProject() + " resourceId=" + getResourceId() );
    	
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
			Representation rep = TradeRepresentation.dispatch( getRequest() );
			rep.setProject( getProject() );
			rep.setHostUrl( getHostUrl() );
			rep.setResourceId( getResourceId() );
			
    		if ( getExtension().equals( "htm" ) )
			{
				response.setContentType("text/html");	
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
		
    	Element root = new Element( "tradeList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );

        Session session = TradeManager.getDatabaseManager().getCurrentSession();
        session.beginTransaction();                
        
	    Object count = session.createQuery( "select count(distinct storeUrl) from Trade" ).uniqueResult();
   		root.addContent( new Element( "count" ).addContent( new CDATA( "" + count ) ) );

	    Query query = session.createQuery( "select distinct storeHost,city from Trade" );
	    query.setFirstResult(start);
	    query.setMaxResults(pageLength);

	    Iterator records = query.iterate();       	
    	while( records.hasNext() )
    	{
    		Object[] record = (Object[]) records.next();	    	
	    	Element recordElem = new Element( "store" );
	    	recordElem.addContent( new Element( "storeHost" ).addContent( new CDATA( "" + record[0] ) ) );
	    	recordElem.addContent( new Element( "city" ).addContent( new CDATA( "" + record[1] ) ) );
	   		root.addContent( recordElem );
    	}
   		
   		return getJdomWriter().writeToString( root );
    }
}
