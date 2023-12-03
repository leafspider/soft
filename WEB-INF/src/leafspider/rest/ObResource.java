package leafspider.rest;

import leafspider.util.*;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Locale;

public abstract class ObResource extends HttpServlet {

	public static String resource = "resource";
	public static String dispatch = "resource";

	protected static final SimpleDateFormat httpDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.CANADA);
	
	/*
    public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	init( request, response );
        if ( !handleRequest() )
       	{
//   	    	report();
   	    	response.setStatus( 404 );
	    }			
    }
    */
    public void doPut( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	init( request, response );
        if ( !handleRequest() )
       	{
//   	    	report();
   	    	response.setStatus( 404 );
	    }
    }
    public void doDelete( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	init( request, response );
        if ( !handleRequest() )
       	{
//  	    	report();
   	    	response.setStatus( 404 );
	    }
    }
    public void doPost( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
    	init( request, response );
        if ( !handleRequest() )
       	{
//   	    	report();
   	    	response.setStatus( 404 );
	    }
    }

    public boolean handleRequest() throws ServletException, IOException  {

    	try {

	    	String method = getRequest().getMethod();    
	    	ObResource servlet = dispatch( getRequest().getPathInfo() );
	    	if ( servlet != null ) {

	    		if ( method.equals( "GET" ) ) { servlet.doGet( getRequest(), getResponse() ); }
	    		else if ( method.equals( "PUT" ) ) { servlet.doPut( getRequest(), getResponse() ); }
	    		else if ( method.equals( "DELETE" ) ) { servlet.doDelete( getRequest(), getResponse() ); }
	    		else if ( method.equals( "POST" ) ) { servlet.doPost( getRequest(), getResponse() ); }
	    		return true;
	    	}
    	}
    	catch( Exception e ) { Log.warnln( "WARN " + e ); }
    	return false;
    }

    public ObResource dispatch(String pathInfo ) throws ServletException, IOException
    {
    	Log.infoln( "pathInfo=" + pathInfo );
		Log.infoln( "Resource.dispatch()" );
    	return null;
    }
    
    private String project = null;
	public String getProject() {
		return project;
	}
	public void setProject(String collection) {
		this.project = collection;
	}
	
	private String extension = null;
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}
	
    public void report() throws ServletException, IOException 
    {
    	getResponse().setContentType("text/html");	            
    	getResponse().getWriter().println( "Not Implemented<br>" );
    	getResponse().getWriter().println( getClass().getName() + "<br>" );
    	getResponse().getWriter().println( "method=" + getRequest().getMethod() + "<br>" );
    	getResponse().getWriter().println( "serverName=" + getRequest().getServerName() + "<br>" );
    	getResponse().getWriter().println( "url=" + getRequest().getRequestURL() + "<br>" );
    	getResponse().getWriter().println( "contextPath=" + getRequest().getContextPath() + "<br>" );
    	getResponse().getWriter().println( "servletPath=" + getRequest().getServletPath() + "<br>" );        	
    	getResponse().getWriter().println( "pathInfo=" + getRequest().getPathInfo() + "<br>" );
    	getResponse().getWriter().println( "queryString=" + getRequest().getQueryString() + "<br>" );
    	getResponse().getWriter().println( "pathTranslated=" + getRequest().getPathTranslated() + "<br>" );
    }

    public void log() throws ServletException, IOException 
    {
    	Log.infoln( "Resource name=" + getClass().getName() );
    	Log.infoln( "method=" + getRequest().getMethod() );
    	Log.infoln( "url=" + getRequest().getRequestURL() );
    	Log.infoln( "contextPath=" + getRequest().getContextPath() );
    	Log.infoln( "pathTranslated=" + getRequest().getPathTranslated() );
    	Log.infoln( "servletPath=" + getRequest().getServletPath() );        	
    	Log.infoln( "pathInfo=" + getRequest().getPathInfo() );
    	Log.infoln( "serverName=" + getRequest().getServerName() );
    	Log.infoln( "serverPort=" + getRequest().getServerPort() );
    	Log.infoln( "queryString=" + getRequest().getQueryString() );
    }

    private HttpServletRequest request = null;
	public synchronized HttpServletRequest getRequest() {
		return request;
	}
	public synchronized void setRequest(HttpServletRequest request) {
		this.request = request;
	}
	
	private HttpServletResponse response = null;
	public HttpServletResponse getResponse() {
		return response;
	}
	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

    protected void init( HttpServletRequest request, HttpServletResponse response )
    {
		setContentLength(request.getContentLength());
		setContentType(request.getContentType());
		setRequest(request);
		setResponse(response);
    }

    public void reportRequest( HttpServletRequest request, String resource ) 
    {
    	String pathInfo = request.getPathInfo();
		if ( pathInfo == null ) { pathInfo = ""; }
		Log.infoln( request.getMethod() + " " + resource + pathInfo );	
    }

	public String transform( String xml, String xsl ) throws ServletException, IOException
	{
//		Log.infoln( "xsl=" + xsl );
//		Log.infoln( "xml=" + xml );			
		StringWriter writer = new StringWriter();
		try
		{
			TransformerFactory xsltFactory = TransformerFactory.newInstance();
			Transformer xslt = xsltFactory.newTransformer( new StreamSource( xsl ) );
			xslt.transform( new StreamSource( new StringReader( xml ) ), new StreamResult( writer ) );
		}
		catch ( TransformerConfigurationException tce )
		{
			throw new ServletException( tce ); 			
		}
		catch( TransformerException te )
		{
			throw new ServletException( te ); 
		}
		return writer.toString();
	}

	public String toJson( String xml ) throws ServletException
	{
		try {
			return JsonConverter.xmlToJson(xml);
		}
		catch ( Exception tce ) {
			throw new ServletException( tce );
		}
	}

    private static XmlJdomWriter jdomWriter = null;
	public static XmlJdomWriter getJdomWriter() 
	{
		if ( jdomWriter == null ) { jdomWriter = new XmlJdomWriter(); }
		return jdomWriter;
	}

    public String getXslUrl( String thisResource, String thisRepresentation ) throws Exception
    {
//		return "/" + KStore.context + "/skins/read/" + res + ".xsl";
//		return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + "/" + KStore.context + "/skins/read/" + res + ".xsl";
    	String url = "http://" + ServerContext.getLocalHost() + "/" + ServerContext.getApplicationName() + "/skins/" + thisResource + "/" + thisRepresentation + ".xsl";
//		Log.infoln( "xsl=" + url );
		return url;
    }
    
    private int contentLength = -1;
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	
	private String contentType = null;
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	private String resourceId = null;
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

    public String getDeletedXml( String title, String url ) throws ServletException, IOException
    {
    	Element root = new Element( "delete" );
   		root.addContent( new Element( "title" ).addContent( new CDATA( title ) ) );
   		root.addContent( new Element( "url" ).addContent( new CDATA( url ) ) );	
   		return getJdomWriter().writeToString( root );
    }    
    public String getDeletedHtm( String title, String url, String xslUrl ) throws ServletException, IOException
    {	
//    	Log.infoln( "resource=" + resource );
//    	Log.infoln( "xslUrl=" + xslUrl );
		return transform( getDeletedXml( title, url ), xslUrl );
    }

    public String getRemovedXml( String title, String url ) throws ServletException, IOException
    {
    	Element root = new Element( "remove" );
   		root.addContent( new Element( "title" ).addContent( new CDATA( title ) ) );
   		root.addContent( new Element( "url" ).addContent( new CDATA( url ) ) );	
   		return getJdomWriter().writeToString( root );
    }    
    public String getRemovedHtm( String title, String url, String xslUrl ) throws ServletException, IOException
    {	
		return transform( getRemovedXml( title, url ), xslUrl );
    }

    protected String getHostUrl()
    {
    	return "http://" + getRequest().getServerName() + ":" + getRequest().getServerPort();
    }
    
	private XslTransformer former = null;
	protected XslTransformer getFormer()
	{
		if (former == null)
		{
			former = new XslTransformer();
//			former.setDebug( true );
		}
		return former;
	}	
	private File skinRoot = null;
	public File getSkinRoot( String type ) 
	{
		if ( skinRoot == null )
		{
			skinRoot = new File( getFormer().getSkinsPath() + "\\" + type );
		}
		return skinRoot;
	}

	public static File writeXmlFile( File file, Element root ) throws Exception {		
		return writeXmlFile( file, root, null );
	}
	public static File writeXmlFile( File file, Element root, String stylesheet ) throws Exception
	{		
		file.getParentFile().mkdirs();
		XmlJdomWriter xmlWriter = new XmlJdomWriter( file.getAbsolutePath(), getXmlEncodingType() );
		if ( stylesheet != null )
		{
//			xmlWriter.setStyleSheet( "_kview/" + getFramesetXslFileName() );		
			xmlWriter.setStyleSheet( stylesheet );		
		}
//		xmlWriter.setIndent( "[t]" );
//		xmlWriter.setLineSeparator( "[n]" );
//		Format format = Format.getRawFormat();
//		format.setIndent( "\n\t" );
//		format.setTextMode( Format.TextMode.NORMALIZE );
//		format.setLineSeparator( "\n" );
//		Log.infoln( "sep=" + format.getLineSeparator() );
//		xmlWriter.getOutputter().setFormat( format );
		xmlWriter.writeOutput( new Document( root ) );
		return file;
	}

	private static String xmlEncodingType = "ISO-8859-1";
//	private String xmlEncodingType = "UTF-8";
	public static String getXmlEncodingType() {
		return xmlEncodingType;
	}
	public static void setXmlEncodingType(String _xmlDecodingType) {
		xmlEncodingType = _xmlDecodingType;
	}

    public static String getUniqueId(String thisPath)
    {
		String id = thisPath;
//		id = id.replaceAll( "[\\:\\/\\%\\.\\?\\&\\#]", "");		// jmh 2010-09-13
		id = id.replaceAll( "[\\:\\/\\%\\.\\?\\&\\#\\\\]", "");
        return id;
    }
    
	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException 
    {
		Log.infoln( getClass().getName() + ".doGet()" );
		init( request, response );
    	//report( getRequest(), resource );
		reportRequest( request, resource );

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

			e.printStackTrace();
			Log.infoln( getClass().getName() + ".doGet status=404" );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, e.getMessage() );			// "404"
		}    	
    }
	
	public abstract void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception;		// TODO Remove artifact

	public void writeResponse( Rest rest ) throws Exception {
		Log.infoln("Resource: Not meant to be here!" );
	}

    public String getListHtm() throws ServletException, IOException, Exception
    {	
		return transform( getListXml(), getXslUrl( resource, dispatch + "List" ) ); 
    }

    public String getListXml() throws ServletException, IOException, Exception
    {
    	int pageLength = 500; 
		int start = 0; try { start = Integer.parseInt( getRequest().getParameter( "start" )); } catch( NumberFormatException nfe ){}
		
		String clazz = this.getClass().getName(); 
		
    	Element root = new Element( clazz + "RecordList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );

   		try
   		{
   			/* TODO Generalize
			List records = CrushProject.getDatabaseManager().listRecords("CrushRecord", "id desc");
			root.addContent( new Element( "count" ).addContent( new CDATA( "" + records.size() ) ) );
		
			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				Element elem = crush.listElement();
		   		root.addContent( elem );
//				XmlJdomWriter writer = new XmlJdomWriter();
//				Log.infoln( writer.writeToString(elem.clone()) );
			}
			*/
   		}
   		catch( Exception e) 
   		{
   			Log.infoln( e );
   		}   		
   		return getJdomWriter().writeToString( root );
    }

}