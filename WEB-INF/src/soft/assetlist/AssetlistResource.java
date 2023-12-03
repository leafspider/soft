package soft.assetlist;

import leafspider.db.DatabaseManager;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.rest.Resource;
import leafspider.rest.Rest;
import leafspider.util.Log;
import org.apache.commons.io.comparator.NameFileComparator;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.filter.Filters;
import soft.batch.BatchProject;
import soft.report.Folders;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.*;

public class AssetlistResource extends Resource {

	public static String resource = "assetlist";
	public static String dispatch = "assetlist";

	public static void main(String[] args) {

		try {
			Rest rest = new AssetlistRest();
			rest.setProject("jake");

			AssetlistResource res = new AssetlistResource();
			res.getListXml( rest );
		}
		catch (Exception e) { e.printStackTrace(); }
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		AssetlistRest rest = new AssetlistRest();
		rest.init(request, response);
		rest.report(resource);

		try {
			writeResponse(rest);
		}
		catch (RepresentationException re) {

			Log.infoln(re.getClass().getCanonicalName() + ": " + re.getMessage());
			response.setStatus(404);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, re.getMessage());            // "404"
		}
		catch (IOException ioe) {

			Log.infoln(ioe.getClass().getCanonicalName() + ": " + ioe.getMessage());
			response.setStatus(404);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, ioe.getMessage());            // "404"
		}
		catch (Exception e) {

			e.printStackTrace();
			Log.infoln("AssetlistResource.doGet status=404");
			response.setStatus(404);
			response.sendError(HttpServletResponse.SC_NOT_FOUND, e.getMessage());            // "404"
		}
	}

	public void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Log.infoln("AssetlistResource: Not meant to be here!" );
	}

	public void writeResponse( Rest rest ) throws Exception {

		HttpServletResponse response = rest.getResponse();
		String ext = rest.getExtension();

		if ( rest.getProject() != null && rest.getResourceId() == null ) {

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

			Representation rep = AssetlistRepresentation.dispatch( rest.getRequest() );
			rep.setProject( rest.getProject() );
			rep.setHostUrl( rest.getHostUrl() );
			rep.setResourceId( rest.getResourceId() );

			if ( ext.equals( "htm" ) ) {

				response.setContentType("text/html");	
				response.getWriter().println( rep.getHtm() );
			}
			else if ( ext.equals( "csv" ) )
			{
				response.setContentType("text/html");	                   
				response.getWriter().println( rep.getCsv() );
			}
			else if ( ext.equals( "gml" ) )
			{
				response.setContentType("application/xml");	                               
				response.getWriter().println( rep.getGml() );
			}
			else if ( ext.equals( "json" ) ) {

				response.setContentType("application/json");
				response.getWriter().println( rep.getJson() );
			}
			else if ( ext.equals( "data" ) )
			{
				response.setContentType("application/json");
				response.getWriter().println( rep.getData() );
			}
			else if ( ext.equals( "xml" ) )
			{
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
		}
    }

    public String getListHtm( Rest rest ) throws ServletException, IOException, Exception {
		return transform( getListXml( rest ), getXslUrl( resource, dispatch + "List" ) );
    }

    public String getListXml( Rest rest ) throws ServletException, IOException, Exception {

    	int pageLength = 500; 
		int start = 0; //try { start = Integer.parseInt( getRequest().getParameter( "start" )); } catch( NumberFormatException nfe ){}
		
    	Element root = new Element( "assetlistList" );
   		root.addContent( new Element( "project" ).addContent( new CDATA( rest.getProject() ) ) );

   		try {

   			/*
//	   		File reportFolder = Folders.reportConfigFolder();
			File reportFolder = Folders.viperConfigFolder();
	   		File[] reportFiles = reportFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});
	   		for( int i=0; i<reportFiles.length; i++)
	   		{
				Properties props = new Properties();
				props.loadTimeline( new FileInputStream( reportFiles[i] ) );
				
				Element recordElem = new Element( "report" );
		    	recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + props.getProperty("id") ) ) );
		    	recordElem.addContent( new Element( "title" ).addContent( new CDATA( "" + props.getProperty("title") ) ) );
		   		root.addContent( recordElem );
	   		}
	   		*/

   			/*
			File porterFolder = Folders.porterConfigFolder();
			File[] porterFiles = porterFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".properties");
				}
			});
			for( int i=0; i<porterFiles.length; i++)
			{
				Properties props = new Properties();
				props.loadTimeline( new FileInputStream( porterFiles[i] ) );

				Element recordElem = new Element( "porter" );
				recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + props.getProperty("id") ) ) );
				recordElem.addContent( new Element( "title" ).addContent( new CDATA( "" + props.getProperty("title") ) ) );
				root.addContent( recordElem );
			}
			*/

	   		/* jmh 2021-01-21
	   		File fixedFolder = Folders.fixedConfigFolder();
	   		File[] files = fixedFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});
	//   		root.addContent( new Element( "count" ).addContent( new CDATA( "" + files.length ) ) );
	   		for( int i=0; i<files.length; i++)
	   		{
				Properties props = new Properties();
				props.loadTimeline( new FileInputStream( files[i] ) );
				
				Element recordElem = new Element( "fixedAssetlist" );
		    	recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + props.getProperty("id") ) ) );
		    	recordElem.addContent( new Element( "title" ).addContent( new CDATA( "" + props.getProperty("title") ) ) );
		   		root.addContent( recordElem );
	   		}
	   		*/
	
	   		File variableFolder = Folders.variableConfigFolder();
	   		File[] variableFiles = variableFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});
	
	   		Set<String> variableSet = new HashSet<String>();
	   		if (variableFiles != null){
				for (int i = 0; i < variableFiles.length; i++) {
					variableSet.add(variableFiles[i].getName());
				}
			}
	
	   		File batchFolder = Folders.batchConfigFolder();
	   		File[] batchFiles = batchFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});

	   		Log.infoln("batchFolder=" + batchFolder);

	   		Arrays.sort(batchFiles, NameFileComparator.NAME_COMPARATOR);
	   		
	//   		root.addContent( new Element( "count" ).addContent( new CDATA( "" + files.length ) ) );
	   		for( int i=0; i<batchFiles.length; i++)
	   		{
	   			File batchFile = batchFiles[i];
	   			
	   			if( variableSet.contains(batchFile.getName()) ) { continue; }
	   				
				Properties props = new Properties();
				props.load( new FileInputStream( batchFile ) );

				Element recordElem = new Element( "assetlist" );
				recordElem.addContent( new Element( "hierarchy" ).addContent( new CDATA( "" + props.getProperty("hierarchy") ) ) );

				String ticker = props.getProperty("id");
		    	recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + ticker ) ) );
		    	recordElem.addContent( new Element( "title" ).addContent( new CDATA( "" + props.getProperty("title") ) ) );

				DatabaseManager dbm = BatchProject.getDatabaseManager( ticker );
				Long count = (Long) dbm.countRecords( "CrushRecord" );
				recordElem.addContent( new Element( "count" ).addContent( new CDATA( "" + count ) ) );

				root.addContent( recordElem );
	   		}

	   		root.sortContent( Filters.element("assetlist"), new CrushRecordElementComparator() );

			File toffeeFolder = Folders.toffeeConfigFolder();
			File[] toffeeFiles = toffeeFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".txt");
				}
			});
			for( int i=0; i<toffeeFiles.length; i++) {

				File file = toffeeFiles[i];
				String id = file.getName().replace(".txt", "");

				Element recordElem = new Element( "toffeelist" );
				recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + id ) ) );
				root.addContent( recordElem );
			}

			File formulaFolder = Folders.formulaConfigFolder();
			File[] formulaFiles = formulaFolder.listFiles( new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".txt");
				}
			});
			for( int i=0; i<formulaFiles.length; i++) {

				File file = formulaFiles[i];
				String id = file.getName().replace(".txt", "");

				Element recordElem = new Element( "formulalist" );
				recordElem.addContent( new Element( "id" ).addContent( new CDATA( "" + id ) ) );
				root.addContent( recordElem );
			}
   		}
   		catch( Exception e ) { e.printStackTrace(); throw e; }
   		
   		return getJdomWriter().writeToString( root );
    }

	public String getListJson( Rest rest ) throws ServletException, IOException, Exception {
		return toJson( getListXml( rest ) );
	}

	public class CrushRecordElementComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			Element el0 = ((Element) arg0);
			Element el1 = ((Element) arg1);
			int comp = el0.getChild("hierarchy").getText().compareTo( el1.getChild("hierarchy").getText() );
			if ( comp == 0 ) {
				comp = el0.getChild("title").getText().compareTo( el1.getChild("title").getText() );
			}
			return comp;
		}

	}
}
