package soft.report;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;

import leafspider.db.DatabaseManager;
import leafspider.db.DatabaseRecord;
import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.LoginContentDownloader;
import leafspider.util.Util;
import leafspider.util.XmlJdomWriter;

public class Cherrypicks extends DatabaseRecord
{
	public static void main ( String[] args )
	{
		try
		{
			Cherrypicks page = new Cherrypicks();
//			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\Server\\tomcat6\\webapps\\soft\\data\\cherrypicks\\2014-8-10\\trout.htm");

			XmlJdomWriter jdomWriter = new XmlJdomWriter();	
			out.print(jdomWriter.writeToString( page.getRoot() ));
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
		
	@Override
	public String getProjectName() 
	{
		return "cherrypicks";
	}
    
	public void populate() throws Exception 
	{	
		try
		{	
			List<String> batches = BatchProject.getBatches();
			Iterator ids = batches.iterator();
			root = new Element( "batches" );
			while( ids.hasNext() )
			{
				String id = (String) ids.next();         	
		    	try
		    	{
					Element batch = new Element( "batch" );
					batch.setAttribute("id",id);
					batch.setAttribute("title", BatchProject.getProperty(id, "title") );
//		    		Log.infoln( "id=" + id );
		    		
		        	DatabaseManager dbm = BatchProject.getDatabaseManager( id );		        	
		        	if( dbm != null )
		        	{	
//		    			Long max = (Long) dbm.countRecords( "CrushRecord" );
//		    			Log.infoln( "max=" + max );
		    			
		        		Iterator cherries = dbm.listTopRecords("CrushRecord", 15, "cherry", "asc").iterator();
//		        		Log.infoln( "id=" + id );
		        		while( cherries.hasNext() )
		        		{
		        			CrushRecord record = (CrushRecord) cherries.next();
		        			record.getTicker();
		        			int percent = (int) (record.getCherry() * 100); 
		        			String color = Asset.getCherryColor(record.getCherry());
		        			Element pick = new Element( "pick" );
		        			pick.setAttribute("ticker",record.getTicker());
		        			pick.setAttribute("cherry","" + percent);
		        			pick.setAttribute("cherryColor",color);
		        			batch.addContent(pick);
//		        			Log.infoln( record.getTicker() + ": " + percent + " " + color );
		        		}
		        	}
		        	root.addContent(batch);
		    	}
				catch( Exception e )
				{
					Log.infoln(e.toString());
					e.printStackTrace();
				}
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			html += "<tr><td>A Detailed Quote is not available for this asset</td></tr>";
		}
	}
	
	protected Element root = null;
	public Element getRoot() {
		return root;
	}
	
	protected String summaryDate = "";
	public String getSummaryDate() {
		return summaryDate;
	}
	
	/*
	protected File outputFolder;
	public File getOutputFolder() {
		if( outputFolder == null )
		{
			Calendar now = new GregorianCalendar();	
			outputFolder = new File( Folders.futuresFolder() + "\\" + Asset.crushDate( now ) );
			outputFolder.mkdirs();
//			Util.deleteAllFiles(outputFolder);
		}
		return outputFolder;
	}
	*/
}
