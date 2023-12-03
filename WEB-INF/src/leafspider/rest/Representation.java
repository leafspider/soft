package leafspider.rest;

import java.io.*;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import leafspider.extract.*;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
//import org.json.XML;

//import soft.store.*;

public abstract class Representation
{	
	public static String resource = "resource";
	public static String representation = "representation";
    public abstract String getResource();
    public abstract String getRepresentation();

    protected NumberFormat decimalFormat = new DecimalFormat( "#0.00" );

	private String resourceId = null;
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resource) {
		this.resourceId = resource;
	} 	
	
    private static XmlJdomWriter jdomWriter = null;
	public static XmlJdomWriter getJdomWriter() 
	{
		if ( jdomWriter == null )
		{
			jdomWriter = new XmlJdomWriter();			
		}
		return jdomWriter;
	}

	private String extension = null;
	public String getExtension() {
		return extension;
	}
	public void setExtension(String extension) {
		this.extension = extension;
	}

	public static String transform( String xml, String xsl ) throws ServletException, IOException
	{
//		Log.infoln( "xsl=" + xsl );
//		Log.infoln( "xml=" + xml );			
		StringWriter writer = new StringWriter();
		try
		{
			TransformerFactory xsltFactory = TransformerFactory.newInstance();
			Transformer xslt = xsltFactory.newTransformer( new StreamSource( xsl ) );
//	    	Log.infoln("Representation.transform start");
			xslt.transform( new StreamSource( new StringReader( xml ) ), new StreamResult( writer ) );
//	    	Log.infoln("Representation.transform finish");
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

    public String getXslUrl() throws Exception
    {
//		return "/" + KStore.context + "/skins/read/" + res + ".xsl";
//		return getRequest().getScheme() + "://" + getRequest().getServerName() + ":" + getRequest().getServerPort() + "/" + KStore.context + "/skins/read/" + res + ".xsl";
    	String url = "http://" + ServerContext.getLocalHost() + "/" + ServerContext.getApplicationName() + "/skins/" + getResource() + "/" + getRepresentation() + ".xsl";
//    	Log.outln( "class=" + this.getClass().toString() + " xsl=" + url );
		return url;
    }
    
    public String getLargeXslUrl() throws Exception
    {
    	return "http://" + ServerContext.getLocalHost() + "/" + ServerContext.getApplicationName() + "/skins/" + getResource() + "/" + getRepresentation() + "Large.xsl";
    }
    
    private String hostUrl = null;
	public String getHostUrl() {
		return hostUrl;
	}
	public void setHostUrl(String hostUrl) {
		this.hostUrl = hostUrl;
	}
	
	private String project = null;
	public String getProject() {
		return project;
	}
	public void setProject(String collection) {
		this.project = collection;
	}
	
	private HttpServletRequest request = null;
	public HttpServletRequest getRequest() {
		return request;
	}
	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

    public String getHtm() throws Exception
    {	
		String transform = transform( getXml(), getXslUrl() );
    	return transform;
    }

    public String getXml() throws RepresentationException
    {
//		Log.infoln( "Representation.getXml()" );
		throw new RepresentationException( "Unsupported format: " + getResource() + getRequest().getPathInfo() );
    }

    public String getGml() throws RepresentationException
    {
		throw new RepresentationException( "Unsupported format: " + getResource() + getRequest().getPathInfo() );
    }

    public String getCsv() throws RepresentationException
    {
		throw new RepresentationException( "Unsupported format: " + getResource() + getRequest().getPathInfo() );
    }

    public String getJson() throws RepresentationException
    {
		try {
			return JsonConverter.xmlToJson( getXml() );
		}
		catch ( Exception tce ) {
			throw new RepresentationException( "Unsupported format: " + getResource() + getRequest().getPathInfo() );
		}
    }

	public String getData() throws RepresentationException {
		throw new RepresentationException( "Unsupported format: " + getResource() + getRequest().getPathInfo() );
	}

    public Element loadXml( File file )
	{
    	Element root = null;
		try
		{
			SAXBuilder saxDoc = new SAXBuilder();
//			Log.infoln("loadXml: doc loading" );
			Document doc = saxDoc.build( file );
//			Log.infoln("loadXml: doc loaded" );
			root = (Element) doc.getRootElement().clone();	
		}
		catch (Exception e)
		{
			Log.warnln( "Exception: ", e );
		}
		return root;
	}

	public static Calendar parseDate( String st, DateFormat dateFormat ) throws Exception
	{
		Calendar ret = new GregorianCalendar();			
		try {

			dateFormat.parse( st );
			String[] parts = st.split("-");
			ret.set( Integer.parseInt(parts[0]), Integer.parseInt(parts[1])-1, Integer.parseInt(parts[2]), 0, 0, 0 );
		}
		catch( Exception e ) {}
		return ret;
  	}

	public static Calendar getStart( Calendar end, String years, String months, String days ) throws Exception
	{
//		if(end == null ) { end = Calendar.getInstance(); }		
		int numYears = 0, numMonths = 0, numDays = 0;
		try { numYears = Integer.parseInt(years); } catch( Exception e ) {}		
		try { numMonths = Integer.parseInt(months); } catch( Exception e ) {}		
		try { numDays = Integer.parseInt(days); } catch( Exception e ) {}
		if ( numYears == 0 && numMonths == 0 && numDays == 0 ) { numYears = 1; }	// Default to 1 year
		Calendar ret = Calendar.getInstance();
		ret.set(end.get(Calendar.YEAR)-numYears, end.get(Calendar.MONTH)-numMonths, end.get(Calendar.DAY_OF_MONTH)-numDays );	
		return ret;
  	}

	public static void reportParameters( HttpServletRequest request ) {

//		Log.infoln( "Representation.reportParameters:");
		Map<String,String[]> map = (Map) request.getParameterMap();
		Iterator keys = map.keySet().iterator();
		while( keys.hasNext() )
		{
			String key = (String) keys.next(); 
			Log.infoln( "param: " + key + "=" + map.get(key)[0]);
		}
	}

	public String getParameter( String parm ) {
		return getRequest().getParameter(parm);
	}

	public static boolean debugResource( String resource ) {

		Properties props = new Properties();
		try {
			props.load( new FileInputStream( ServerContext.getDebugFile() ) );
//			Log.infoln("debug " + resource + "=" + props.get( resource ));
			if( Boolean.parseBoolean( (String) props.get( resource ) ) ) { return true; }
		}
		catch( Exception e ) {}
		return false;
	}
}
