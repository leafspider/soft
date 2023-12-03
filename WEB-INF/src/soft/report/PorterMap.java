package soft.report;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import leafspider.util.Timestamp;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;

import leafspider.db.DatabaseManager;
import leafspider.util.Log;
import leafspider.util.Util;
import leafspider.util.XmlJdomWriter;

public class PorterMap extends BarchartReport	//DatabaseRecord
{
	public static void main ( String[] args )
	{
		try
		{
			PorterMap page = new PorterMap();
//			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
			page.setResourceId("etf_gaps");
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

	private String resourceId = null;
	public String getResourceId() {return resourceId;}
	public void setResourceId(String resourceId) {this.resourceId = resourceId;}

	private int top = 30;
	public int getTop() { return top; }
	public void setTop(int top) { this.top = top; }

	@Override
	public String getProjectName() 
	{
		return "pearpicks";
	}
    
	public void populate() throws Exception 
	{	
		try {

			List<String> batches = null;	//BatchProject.getBatches();
			if ( getResourceId() != null && !getResourceId().equals("summary") && !getResourceId().equals("all") ) {
				batches = new ArrayList<String>();
				batches.add( getResourceId() );
			}
			else {
				batches = BatchProject.getBatches();
			}

			Iterator ids = batches.iterator();
			root = new Element( "batches" );

			while( ids.hasNext() ) {

				String id = (String) ids.next();         	
		    	try {

					Element batch = new Element( "batch" );
					batch.setAttribute( "hierarchy", "" + BatchProject.getProperty(id, "hierarchy") );
					batch.setAttribute("id",id);
					batch.setAttribute("title", BatchProject.getProperty(id, "title") );
//		    		Log.infoln( "id=" + id );
		    		
		        	DatabaseManager dbm = BatchProject.getDatabaseManager( id );		        	
		        	if( dbm != null ) {

//						Long max = (Long) dbm.countRecords( "CrushRecord" );
//						batch.addContent( new Element( "size" ).addContent( new CDATA( "" + batches.size() ) ) );

		        		Iterator picks = dbm.listTopRecords("CrushRecord", getTop(), "pear", "desc").iterator();
						addPicksToBatch(picks, batch);
		        	}
		        	root.addContent(batch);

		        	long stamp = dbm.lastModified();
					File folder = getOutputFolder();
					folder.mkdirs();
					Timestamp.writeTimestamp(folder, stamp);
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

	private void addPicksToBatch( Iterator picks, Element batch) {

//		Log.infoln( "pears id=" + id );
		while (picks.hasNext()) {

			CrushRecord record = (CrushRecord) picks.next();
			record.getTicker();
			int crushPercent = (int) (record.getCrush() * 100);
			String crushColor = Asset.getCrushColor(record.getCrush());
			int pearPercent = (int) (record.getPear() * 100);
			String pearColor = Asset.getPorterColor(record.getPear());
			Element pick = new Element("pick");
			pick.setAttribute("ticker", record.getTicker());
			pick.setAttribute("crush", "" + crushPercent);
			pick.setAttribute("crushColor", crushColor);
			pick.setAttribute("pear", "" + pearPercent);
			pick.setAttribute("pearColor", pearColor);
			batch.addContent(pick);
//		    Log.infoln( record.getTicker() + ": " + percent + " " + color );
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

	public File getOutputFolder() {

		if( outputFolder == null ) {

			outputFolder = new File( Folders.porterFolder() + "\\" + getResourceId() );
			outputFolder.mkdirs();
		}
		return outputFolder;
	}
}
