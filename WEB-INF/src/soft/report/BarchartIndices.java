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

public class BarchartIndices extends BarchartFutures
{
	@Override
	public String getProjectName() 
	{
		return "indices";
	}
	
	public static void main ( String[] args )
	{
		try
		{
			BarchartIndices page = new BarchartIndices();
			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\tmp\\indices.htm");
			out.print(page.getHtml());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public String getUrl() 
	{
		if( url == null )
		{
			try
			{
				url = getProperties().getProperty("url");
			}
			catch( Exception e )
			{
				url = "http://old.barchart.com/stocks/indices.php";
			}
			url = url.replaceAll( "old.barchart", "old.barchart" );
		}
		return url;
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
			reportDate = doc.select( "#dtaDate" ).first().text();			
			Elements headers = doc.select( ".bar h2" );
			Elements tables = doc.select( ".datatable" );
			
			html += "<table>" +
					"<tr>" + 
					" <th class='spacer'></th>" + 
					" <th colspan='2' class='pagetitle'><h1>Indices Daily Summary: " + reportDate + "</h1></th>" +  
					"</tr>" + 
					"<tr><td></td><td>";
			
			int tableNum = 0;
			Iterator tablesit = tables.iterator();			
			while(tablesit.hasNext())
			{
				Element table = (Element) tablesit.next();				
				Elements anchors = table.select( "a[title~=(Detailed).?]" );

				int rowNum = 0;
				Elements rows = table.select( "tr" );
				
//				Log.infoln("cells.size=" + cells.size() );
				Iterator rowsit = rows.iterator();
				while( rowsit.hasNext())
				{
					Element row = (Element) rowsit.next();

					Element firstCell = row.select( "td:eq(0)" ).first();
					if( firstCell == null ) { continue; }

					Element secondCell = row.select( "td:eq(1)" ).first();
					if( secondCell == null ) { continue; }
					
					String tick = firstCell.text();
					String name = secondCell.text();
					Log.infoln("[" + tick + "][" + name + "]" );
										
//					firstCell.prepend( "&nbsp;-&nbsp;" + secondCell.html() + "&nbsp;" );

//					int pos = tick.indexOf(" ");
//					tick = tick.substring(0,pos);
					
					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
					firstCell.append("<br>" + quoteHtml );

					Element lastCell = row.select( "td:eq(9)" ).first();
//					String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
					String href = "http://old.barchart.com/charts/stocks/" + tick + "&style=technical";
					String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";

					lastCell.append("&nbsp;" + linkHtml );
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
	
	/*
	protected String parseDetailedQuote( String href, File outputFolder ) throws Exception
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
			outputFolder = new File( Folders.indicesFolder() + "\\" + Asset.crushDate( now ) );
			outputFolder.mkdirs();
//			Util.deleteAllFiles(outputFolder);
		}
		return outputFolder;
	}
	*/
}
