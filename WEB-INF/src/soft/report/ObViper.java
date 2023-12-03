package soft.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import leafspider.util.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.antlr.grammar.v3.ANTLRParser.throwsSpec_return;
import org.hibernate.Query;
import org.hibernate.Session;
import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.asset.CrushRecord;
import soft.batch.BatchAgent;
import soft.batch.BatchProject;
import leafspider.db.DatabaseManager;
import leafspider.db.DatabaseRecord;

public class ObViper extends BarchartReport
{
	public static void main ( String[] args )
	{
		try
		{
			ObViper page = new ObViper();
//			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
//			page.setUrl("https://www.barchart.com/etfs-funds/etf-monitor");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\Workspace\\Ultra\\Jake Tiley\\ViperReport\\out.xml");

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
		return "viper";
	}	

    public enum ReportSource {
        MARKETINDEX(0, "marketindex.com", ".datatable_simple"),
        MARKETVOLUME(1, "marketvolume.com", "qf"),
        COMPONENTS(2, "+Components", ".datatable_simple"),
        YAHOO(3, "yahoo.com", ".datatable_simple"),
        MININGFEEDS(4, "miningfeeds.com", ".datatable_simple"),
        COMMODITYFUTURES(5, "commodityfutures", ".datatable_simple"),
        BARCHART(6, "http", ".datatable_simple");

        int id;
        String url;
        String table;

        ReportSource(int id, String url, String table) {
            this.id = id;
            this.url = url;
            this.table = table;
        }
        
        public static ReportSource get(String url){
            for (ReportSource source : values()) {
                if(url.indexOf(source.url) > -1) {
                    return source;
                }
            }
            throw new IllegalArgumentException("Can't determine report source for url " + url);
        }
    }    
    
	public void populate() throws Exception 
	{	
		try
		{	
			Log.infoln("Starting ViperReport");
			
	   		File reportFolder = Folders.reportConfigFolder();
	   		File[] reportFiles = reportFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});

			root = new Element( "reports" );

			for( int i=0; i<reportFiles.length; i++) 
	   		{
				Properties props = new Properties();
				props.load( new FileInputStream( reportFiles[i] ) );
				
		   		File file = new File( Folders.reportFolder().getAbsolutePath() + "\\" + props.getProperty("id") + "\\output.htm" );
		   		
				if( file != null && file.exists() ) { 

					urls = new TreeSet<String>();
					try
					{
						url = props.getProperty("url");
						if( url != null ) { urls.add( url ); }
						for( int j=1; j< 31; j++) {
							String val = props.getProperty("url" + j);
							if( val != null ) { urls.add( val ); } 
						}						
					}
					catch( Exception e ) { throw e; }
					
					String id = props.getProperty("id");
					String title = props.getProperty("title");
					url = urls.first();

					Element report = new Element( "report" );
					report.setAttribute( "id",id );
					report.setAttribute( "title", title );
					report.setAttribute( "url", url );

					ArrayList<Element> picks = new ArrayList();

					Document doc = Jsoup.parse(file, null); 

					ReportSource source = ReportSource.get(urls.first());
	
//					Log.info(source.id + " " + id + " ");
					
					Elements tables = doc.select( source.table );

					int tableNum = 0;
					Iterator tablesit = tables.iterator();			
					while(tablesit.hasNext())
					{
						org.jsoup.nodes.Element table = (org.jsoup.nodes.Element) tablesit.next();	
						try 
						{
							org.jsoup.nodes.Element a = table.parent().select("a").first();
							String href = a.attr("href");
							String vals[] = href.split("/");
							String ticker = vals[vals.length-1];
							
							org.jsoup.nodes.Element row1 = table.select( "tr" ).get(0);
							org.jsoup.nodes.Element col1 = row1.select( "td" ).last();

							org.jsoup.nodes.Element row2 = row1.nextElementSibling();
							org.jsoup.nodes.Element col2 = row2.select( "td" ).last();

							if( source.id == ReportSource.COMPONENTS.id ) // eg DAX
							{
								row1 = table.select( "tr" ).get(1);
								col1 = row1.select( "td" ).get(5);
								row2 = row1.nextElementSibling();
								col2 = row2.select( "td" ).get(5);
								ticker = ticker.replaceAll("q\\?s=", "");

//								Log.infoln("ticker=" + ticker );
							}
							else if( source.id == ReportSource.BARCHART.id || source.id == ReportSource.COMMODITYFUTURES.id ) 
							{
								col1 = row1.select( "td" ).get(7);
								col2 = row2.select( "td" ).get(7);
							}
														
							if( col1 != null && col2 != null ) 
							{
//								Log.info(col1.text() + " " + col2.text());

								Double vol1 = Double.parseDouble(col1.text().replaceAll(",", ""));
								Double vol2 = Double.parseDouble(col2.text().replaceAll(",", ""));
								Double diff = vol1 - vol2;								
			        			double percent = (double) ((diff/vol2) * 100.d);

			        			double absPercent = Math.abs(percent);
			        			
			        			String color = getPercentageColor(absPercent);
								
			        			Element pick = new Element( "pick" );
			        			pick.setAttribute("ticker",ticker);
			        			pick.setAttribute("before", "" + vol2.intValue());
			        			pick.setAttribute("after", "" + vol1.intValue());
			        			pick.setAttribute("percent", "" + (int) percent);
			        			pick.setAttribute("color",color);
			        			
//								Log.infoln(percent);
		        			
			        			picks.add(pick);
							}
						}
						catch( Exception e ) { }
					}
					
					if( picks != null && picks.size() > 0) 
					{
						// Sort picks
						Collections.sort(picks,new PickComparator());
						if ( picks.size()> 30 ) {
							report.addContent( picks.subList(0, 30) );
						}
						else {
							report.addContent(picks);
						}
						root.addContent(report);
					}
				}		    	
	   		}
			
			Log.infoln("Finished ViperReport");
			
	   		File folder = new File( Folders.heatFolder().getAbsolutePath() + "\\" + getProjectName() );
	   		folder.mkdirs();
	   		Timestamp.writeTimestamp(folder);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private class PickComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			Element el0 = ((Element) arg0);
			Element el1 = ((Element) arg1);
			int pc0 = Integer.parseInt( el0.getAttributeValue("percent") );
			int pc1 = Integer.parseInt( el1.getAttributeValue("percent") );
			if( pc0 < pc1 )  { return 1; }
			if( pc0 > pc1 )  { return -1; }
			return 0;
		}
	
	}

	public static String getPercentageColor(double val) 
	{
		val *= 0.00042;
		
		if ( val > 0.42 ) { return "ff0000"; }
		else if ( val > 0.41 ) { return "ff2000"; }
		else if ( val > 0.40 ) { return "ff4000"; }
		else if ( val > 0.39 ) { return "ff6000"; }
		else if ( val > 0.38 ) { return "ff8000"; }
		else if ( val > 0.36 ) { return "ffa000"; }
		else if ( val > 0.34 ) { return "ffc000"; }
		else if ( val > 0.32 ) { return "ffe000"; }
		else if ( val > 0.30 ) { return "fff000"; }
		else if ( val > 0.28 ) { return "ffff00"; }
		else if ( val > 0.26 ) { return "ffff20"; }
		else if ( val > 0.24 ) { return "ffff40"; }
		else if ( val > 0.22 ) { return "ffff60"; }
		else if ( val > 0.20 ) { return "ffff80"; }
		else if ( val > 0.18 ) { return "ffffa0"; }
		else if ( val > 0.16 ) { return "ffffc0"; }
		else if ( val > 0.14 ) { return "ffffe0"; }
		else if ( val > 0.12 ) { return "fffff0"; }
		else if ( val > 0.10 ) { return "00ffff"; }
		else if ( val > 0.08 ) { return "00ccff"; }		
		else if ( val > 0.06 ) { return "0099ff"; }		
		else if ( val > 0.04 ) { return "0066ff"; }
		else if ( val > 0.02 ) { return "0033ff"; }
		return "0000ff";
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
