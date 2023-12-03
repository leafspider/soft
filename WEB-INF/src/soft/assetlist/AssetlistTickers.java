package soft.assetlist;

import leafspider.db.DatabaseManager;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;
import leafspider.util.Util;
import org.jdom2.CDATA;
import org.jdom2.Element;
import soft.asset.Asset;
import soft.asset.CrushFailure;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;

import javax.servlet.ServletException;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class AssetlistTickers extends AssetlistRepresentation
{
	public static String representation = "tickers";
    public String getRepresentation() { return representation; }

    public String getXml() throws RepresentationException {

    	String id = getResourceId();
    	if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try {

			Properties props = new Properties();
			props.load( new FileInputStream( BatchProject.getConfigFile( getResourceId() ) ) );
			String title = props.getProperty("title");

			Element root = new Element( "assetlist" );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "hierarchy" ).addContent( new CDATA("" + BatchProject.getProperty(id, "hierarchy")  ) ) );
			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );
			root.addContent( new Element( "title" ).addContent( new CDATA( title ) ) );

			//Alist alist = AlistBatch.instance(id);

			DatabaseManager dbm = BatchProject.getDatabaseManager( id );
			List<CrushRecord> recordsList = dbm.listRecords("CrushRecord", "ticker");
			root.addContent( new Element( "max" ).addContent( new CDATA( "" + recordsList.size() ) ) );

			Element tickerlist = new Element( "tickerlist" );

			Iterator<CrushRecord> records = recordsList.iterator();
			String tickers = "";
			while ( records.hasNext() ) {
				CrushRecord record = records.next();
				tickers += " " + record.getTicker();

				Element asset = new Element( "ticker" );
				asset.setText( record.getTicker() );
				tickerlist.addContent(asset);
			}
			tickers = tickers.trim();

			//root.addContent( new Element( "tickers" ).addContent( new CDATA( alist.getTickerList() ) ) );
			root.addContent( new Element( "tickers" ).addContent( new CDATA( tickers ) ) );
			root.addContent(tickerlist);

			Element failuresList = new Element( "failures" );
			List failedList = dbm.select("select ticker, count(ticker) from CrushFailure group by ticker order by ticker");

			Iterator failures = failedList.iterator();
			while ( failures.hasNext() ) {
				Object[] tuple = (Object[]) failures.next();
				Element failure = new Element( "failure" );
				failure.setAttribute("ticker", "" + tuple[0] );
				failure.setAttribute("count", "" + tuple[1] );
				failuresList.addContent(failure);
			}
			root.addContent( failuresList );

			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }


}
