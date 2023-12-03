package soft.fsocial;

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
import soft.assetlist.AssetlistResource;
import soft.batch.BatchProject;
import soft.report.Folders;
import twitter4j.TwitterException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

public class FsocialResource extends AssetlistResource
{
	public static String resource = "fsocial";
	public static String dispatch = "fsocial";

	public static void main(String[] args)
	{
		try {
			/*
			App ID			469498504323253
			App Secret		df4031f1ff2362c646ba8def0bf02f0d
			Client Token	5195f132b64527682df470ae3ceeecf2
			JakeTiley007=710050083
			leafspider=720369907
			*/
			//https://graph.facebook.com/JakeTiley007/accounts?access_token=5195f132b64527682df470ae3ceeecf2
//			System.out.println( fetch.getXml() );
		}
		catch( Exception e ) { e.printStackTrace(); }
	}

	public void doGet( HttpServletRequest request, HttpServletResponse response ) throws ServletException, IOException {

		FsocialRest rest = new FsocialRest();
		rest.init(request, response);
		rest.report(resource);

    	try {
   			writeResponse( rest );
		}
		catch( Exception re ) {
			re.printStackTrace();
			Log.infoln( re.getClass().getCanonicalName() + ": " + re.getMessage() );
			response.setStatus( 404 );
			response.sendError( HttpServletResponse.SC_NOT_FOUND, re.getMessage() );			// "404"
		}    	
    }

	public synchronized void writeResponse( HttpServletRequest request, HttpServletResponse response ) throws Exception {
		Log.infoln("FsocialResource: Not meant to be here!" );
	}

	public synchronized void writeResponse( Rest rest ) throws Exception {

		HttpServletResponse response = rest.getResponse();
		String ext = rest.getExtension();

		if ( rest.getProject() != null && rest.getResourceId() == null ) {
			if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");
				response.getWriter().println( getListXml( rest ) );
			}
			/*
			else if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");
				response.getWriter().println( getListHtm( rest ) );
			}
			else if ( ext.equals( "json" ) ) {
				response.setContentType("application/json");
				response.getWriter().println( getListJson( rest ) );
			}
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
			*/
			//throw new RepresentationException( "Unrecognised resource: " + rest.getResourceId() );
		}
		else {

    		Representation rep = FsocialRepresentation.dispatch( rest.getRequest() );
    		rep.setProject( rest.getProject() );
    		rep.setHostUrl( rest.getHostUrl() );
    		rep.setResourceId( rest.getResourceId() );

    		if ( ext.equals( "htm" ) ) {
				response.setContentType("text/html");
				response.getWriter().println( rep.getHtm() );
			}
			else if ( ext.equals( "xml" ) ) {
				response.setContentType("application/xml");	            
				response.getWriter().println( rep.getXml() );
			}	
			else { throw new RepresentationException( "Unrecognised format: " + ext ); }
	    }
    }

	public String getListXml( Rest rest ) throws ServletException, IOException, Exception {

		Element root = new Element( "fsocial" );
		root.addContent( new Element( "project" ).addContent( new CDATA( rest.getProject() ) ) );

		try {

			File variableFolder = Folders.variableConfigFolder();
			File[] variableFiles = variableFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".properties");
				}
			});

			Set<String> variableSet = new HashSet<String>();
			for(int i=0; i< variableFiles.length; i++ ) { variableSet.add( variableFiles[i].getName() ); }

			File batchFolder = Folders.batchConfigFolder();
			File[] batchFiles = batchFolder.listFiles(new FilenameFilter() {
				public boolean accept(File dir, String name) {
					return name.toLowerCase().endsWith(".properties");
				}
			});

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

			root.sortContent( Filters.element("assetlist"), new AssetlistResource.CrushRecordElementComparator() );

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
		}
		catch( Exception e ) { e.printStackTrace(); throw e; }

		return getJdomWriter().writeToString( root );
	}
    
}
