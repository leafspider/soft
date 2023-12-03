package soft.stores;

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


public class DealResource extends ObResource
{
	public static String resource = "deal";
	public static String dispatch = "deal";

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
//		Log.infoln( "DealResource.doGet()" );
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
			Log.infoln( "DealResource.doGet status=404" );
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
			Representation rep = DealRepresentation.dispatch( getRequest() );
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
		String city = null; try { city = getRequest().getParameter( "city" );} catch( NumberFormatException nfe ) {}
		String clause = "";
		if ( city != null ) { clause = " where city = '" + city + "'"; }
		
    	Element root = new Element( "dealList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
   		root.addContent( new Element( "city" ).addContent( new CDATA( city ) ) );
//   		addContext( root );
//   		root.addContent( new Element( "pageLength" ).addContent( "" + pageLength ) );
//   		root.addContent( new Element( "editor" ).addContent( "" + getRequest().isUserInRole( "editor" ) ) );

        Session session = HibernateManager.getCurrentSession();
        session.beginTransaction();                
        
	    Object count = session.createQuery( "select count(url) from Deal" + clause ).uniqueResult();
   		root.addContent( new Element( "count" ).addContent( new CDATA( "" + count ) ) );

	    Query query = session.createQuery( "from Deal" + clause + " order by discount desc" );
	    query.setFirstResult(start);
	    query.setMaxResults(pageLength);

	    Iterator records = query.iterate();       	
    	while( records.hasNext() )
    	{
    		Deal record = (Deal) records.next();	    	
	    	Element recordElem = new Element( "deal" );
	    	recordElem.addContent( new Element( "text" ).addContent( new CDATA( "" + record.getText() ) ) );
	    	recordElem.addContent( new Element( "url" ).addContent( new CDATA( "" + record.getUrl() ) ) );
	    	recordElem.addContent( new Element( "price" ).addContent( new CDATA( "" + deZero( record.getPrice() ) ) ) );
	    	recordElem.addContent( new Element( "saving" ).addContent( new CDATA( "" + deZero( record.getSaving()) ) ) );
	    	recordElem.addContent( new Element( "value" ).addContent( new CDATA( "" + deZero( record.getValue()) ) ) );
	    	recordElem.addContent( new Element( "discount" ).addContent( new CDATA( "" + deZero( record.getDiscount()) ) ) );
	    	recordElem.addContent( new Element( "city" ).addContent( new CDATA( "" + record.getCity() ) ) );
	    	recordElem.addContent( new Element( "storeUrl" ).addContent( new CDATA( "" + record.getStoreUrl() ) ) );
	    	recordElem.addContent( new Element( "storeHost" ).addContent( new CDATA( "" + record.getStoreHost() ) ) );
	    	recordElem.addContent( new Element( "nouns" ).addContent( new CDATA( "" + record.getNouns() ) ) );
	    	recordElem.addContent( new Element( "verbs" ).addContent( new CDATA( "" + record.getVerbs() ) ) );
	   		root.addContent( recordElem );
    	}
   		
   		return getJdomWriter().writeToString( root );
    }
    
    private String deZero(String st)
    {
    	return st.replace(".00", "");
    }
}
