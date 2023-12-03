package soft.report;

import java.io.File;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import soft.asset.Asset;

import leafspider.db.DatabaseRecord;
import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.LoginContentDownloader;
import leafspider.util.Util;

public class ObBarchartFutures extends DatabaseRecord
{
	public static void main ( String[] args )
	{
		try
		{
			ObBarchartFutures page = new ObBarchartFutures();
			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\Server\\tomcat6\\webapps\\soft\\data\\futures\\2014-6-21\\trout.htm");
			out.print(page.getHtml());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	private String url = null;
	public String getUrl() {
		if( url == null )
		{
			url = "http://old.barchart.com/commodityfutures/All";
		}
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		
	@Override
	public String getProjectName() 
	{
		return "futures";
	}

	public void populate() throws Exception 
	{	
		try
		{				
			LoginContentDownloader downloader = new LoginContentDownloader(getUrl(), getOutputFolder() );
						
			if( !downloader.getResultFile().exists() )
			{
//				downloader.setUserName("mpagah@hotmail.com");
//				downloader.setPassword("bernouilli27");
				downloader.startThread();
				downloader.join( 10000 );
			}
			
			setFile( downloader.getResultFile() );			
//			Log.infoln( getFile().getAbsolutePath() );
			
			Document doc = parse();

			html = "";
			summaryDate = doc.select( "#dtaDate" ).first().text();			
			Elements headers = doc.select( ".bar h2:eq(0) a" );
			Elements tables = doc.select( ".datatable" );
			
			/*
			String style1 = " body {background:#eeeeee;font-family:arial;}" + 
							" .qb_up { color:green; } .qb_down { color:red; }" + 
							" .qb_line { padding-right:10px; } .qb_shad { padding-right:10px; }\n";
			String style2 = " .toptail {border:1px;padding-top:20px;border-radius:10px;border-style:outset;background-color:lightgrey;}" + 
							" .datatable {align:center;background-color:white;width:100%;}" + 
							" thead {align:left;} h3 {margin:0px;padding:5px;background-color:white;}" +
							" .ds_name {border-right:dotted 1px;}\n" +
							" td[class*='ds_'] {border-top:dotted 1px;padding:5px;}" +
							" th[class*='ds_'] {border-top:dotted 1px;padding:5px;}" +
							" .qb_shad.noprint {border-top:dotted 1px;}" +
							" .qb_line.noprint {border-top:dotted 1px;}\n";
			html += "<style>" + style1 + style2 + "</style>\n";
			*/

			html += "<table>" +
					"<tr>" + 
					" <th class='spacer'></th>" + 
					" <th colspan='2' class='pagetitle'><h1>Futures Daily Summary: " + summaryDate + "</h1></th>" +  
					"</tr>" + 
//					"<td width='10%'></td><td>";
					"<tr><td></td><td>";
			
			int tableNum = 0;
			Iterator tablesit = tables.iterator();			
			while(tablesit.hasNext())
			{
				Element table = (Element) tablesit.next();				
				Elements anchors = table.select( "a[title~=(Detailed).?]" );

				int rowNum = 0;
				Elements cells = table.select( "tr td:eq(0)" );
//				Log.infoln("cells.size=" + cells.size() );
				Iterator cellsit = cells.iterator();
				while( cellsit.hasNext())
				{
					Element cell = (Element) cellsit.next();
					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
					cell.append("<br>" + quoteHtml );
				}

				/*
				cells = table.select( "tr td:eq(1)" );
				cellsit = cells.iterator();
				while( cellsit.hasNext())
				{
					Element cell = (Element) cellsit.next();
					Log.infoln("txt=" + cell.text() );
//					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
//					cell.append("<br>" + quoteHtml );
				}

				/*
				cells = table.select( "tr td:eq(8)" );
				cellsit = cells.iterator();
				while( cellsit.hasNext())
				{
					Element cell = (Element) cellsit.next();
					String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
					String linkHtml = "<a href='" + href + "'>I</a>";
					cell.append("&nbsp;" + linkHtml );
				}
//				*/

				html += "<div class='toptail'>";
				String header = "<h3>" + headers.get(tableNum++).text() + "</h3>";
				html += "<table class='datatable'>";
				html += "<thead colspan='5'><tr>" + header + "</tr></thead>";
				html += table.html();
				html += "</table>";
				html += "</div>";
				html += "<br>";
			}
			html += "</td><td width='30%></td></tr></table>";
			
			html = Util.replaceSubstring( html, "=\"/", "=\"http://old.barchart.com/");
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			html += "<tr><td>A Detailed Quote is not available for this asset</td></tr>";
		}
	}

	private String parseDetailedQuote( String href, File outputFolder ) throws Exception
	{
		LoginContentDownloader downloader = new LoginContentDownloader(href, outputFolder );
		if( !downloader.getResultFile().exists() )
		{
			downloader.debug = false;
//			downloader.setUserName("mpagah@hotmail.com");
//			downloader.setPassword("bernouilli27");
			downloader.startThread();
			downloader.join( 5000 );
		}
		
		String htm = "";
		File file = new File( outputFolder.getAbsolutePath() + "\\" + downloader.resultFileName );
		if( file.exists() )
		{ 
			Document doc = Jsoup.parse(file, null);
			Element table = doc.select(".datatable_simple").first();
			if( table == null )
			{
				return null;
//				table = doc.select(".datatable").first();
			}
			Element header = table.getElementsByTag("tr").first();
			header.remove();
			htm = "<table class='datatable_simple'>" + table.html() + "</table>";
		}
		return htm;		
	}
	
	protected String html = "";
	public String getHtml() {
		return html;
	}
	
	protected String summaryDate = "";
	public String getSummaryDate() {
		return summaryDate;
	}
	
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
}
