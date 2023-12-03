package soft.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import soft.asset.Asset;
import leafspider.db.DatabaseRecord;
import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.LoginContentDownloader;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class BarchartReport extends DatabaseRecord
{
	@Override
	public String getProjectName() 
	{
		return "report";
	}

	public boolean debug = false;

	public static void main ( String[] args )
	{
		try
		{
			BarchartReport page = new BarchartReport();
			page.setResourceId("asx");
			page.debug = true;
//			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
			page.populate();
//			Log.infoln(page.getHtml());
			
			File file = page.getOutputFile();			
			PrintStream out = Util.getPrintStream( file.getAbsolutePath() );
			out.print(page.getHtml());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	protected TreeSet<String> urls = null;
	public TreeSet<String> getUrls() 
	{
		if( urls == null ) { urls = new TreeSet<String>(); }
		return urls;
	}
	public void setUrls(TreeSet<String> urls) 
	{
		this.urls = urls;
	}
	
	public void populate() throws Exception 
	{	
		try
		{	
			String id = getResourceId();
			if( id == null ) { throw new Exception( "Null resourceId" ); }

			Properties prop = getProperties();
			String title = prop.getProperty("title");
			
			urls = new TreeSet<String>();
			
			try
			{
				url = prop.getProperty("url");
				if( url != null ) { urls.add( url ); }
//				urls.add( prop.getProperty("url") );
				for( int i=1; i< 31; i++)
				{
					urls.add( prop.getProperty("url" + i) );
				}
			}
			catch( Exception e ) { }

			/* DEBUG
			urls.add( "C:\\Workspace\\Ultra\\Jake Tiley\\Reports\\^NDX Components _ NASDAQ-100 Stock - Yahoo! Finance1.html" );
			urls.add( "C:\\Workspace\\Ultra\\Jake Tiley\\Reports\\^NDX Components _ NASDAQ-100 Stock - Yahoo! Finance2.html" );
			urls.add( "C:\\Workspace\\Ultra\\Jake Tiley\\Reports\\^NDX Components _ NASDAQ-100 Stock - Yahoo! Finance3.html" );			
//			*/

			String tableHtml = "";
			
			if( urls.first().indexOf("marketindex.com") > -1 ) { tableHtml = parseMarketindexComponents(); }
			else if( urls.first().indexOf("marketvolume.com") > -1 ) { tableHtml = parseMarketvolumeComponents(); }
			else if( urls.first().indexOf("yahoo.com") > -1 ) { tableHtml = parseYahooComponents(); }
			else if( urls.first().indexOf("miningfeeds.com") > -1 ) { tableHtml = parseMiningfeedsComponents(); }
			else if( urls.first().indexOf("commodityfutures") > -1 ) { tableHtml = parseCommodityfuturesComponents(); }
			else { tableHtml = parseBarchartComponents(); }
						
//			/*
			html = "<table>" +
					"<tr>" + 
					" <th class='spacer'></th>" + 
					" <th colspan='2' class='pagetitle'><h1>" + title + " (" + getReportDate() + ")</h1></th>" +  
					"</tr>" + 
					"<tr><td></td><td>";

			html += "<div class='toptail'>";
//			String header = "<h3>" + headers.get(tableNum++).text() + "</h3>";
			html += "<table class='datatable'>";
//			html += "<thead colspan='5'><tr>" + header + "</tr></thead>";
//			*/
			
			html += tableHtml;
			
//			/*
			html += "</table>";
			html += "</div>";
			html += "<br>";

			html += "</td><td width='30%></td></tr></table>";
//			*/
			
			html = Util.replaceSubstring( html, "=\"/", "=\"http://old.barchart.com/");
			
//			setReportTitle(title);
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			html += "<tr><td>A Detailed Quote is not available for this asset</td></tr>";
		}
	}

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
		
		String htm = null;
		File file = new File( outputFolder.getAbsolutePath() + "\\" + downloader.resultFileName );
		if( file.exists() )
		{ 
			if( href.indexOf("barchart.com") > -1 ) { htm = parseBarchartDetailedQuote(file); }
			else if( href.indexOf("yahoo.com") > -1 ) { htm = parseYahooDetailedQuote(file); }
		}
		return htm;		
	}
	
	protected String parseBarchartDetailedQuote(File file) throws Exception
	{
		Document doc = Jsoup.parse(file, null);
		Element table = doc.select(".datatable_simple").first();
		if( table == null ) { return null; }
		Element header = table.getElementsByTag("tr").first();
		header.remove();
		return "<table class='datatable_simple'>" + table.html() + "</table>";
	}

	protected String parseYahooDetailedQuote(File file) throws Exception
	{
		Document doc = Jsoup.parse(file, null);
		Element table = doc.select(".yfnc_datamodoutline1 table").first();
		if( table == null ) { return null; }
		Element header = table.getElementsByTag("tr").first();
//		header.remove();		
		Elements rows = table.getElementsByTag("tr");
		Iterator<Element> iter = rows.iterator();
		int count = 0;
		while(iter.hasNext())
		{
			Element row = iter.next();
			if( count > 5 || (row.getElementsByTag("td").size() < 3 && row.getElementsByTag("th").size() < 3) ) 
			{ 
//				Log.infoln( "removed " + row.getElementsByAttribute("th").first().text() );
				row.remove(); 
			}
			count++; 
		}
		return "<table class='datatable_simple'>" + table.html() + "</table>";
	}

	protected String html = "";
	public String getHtml() {
		return html;
	}
	public void setHtml(String val) {
		html = val;
	}
	
	protected String reportDate = null;
	public String getReportDate() 
	{
		if( reportDate == null )
		{
			Date now = new Date();
			String dat = reportDateFormat.format(now);
			setReportDate(dat);
		}
		return reportDate;
	}
	public void setReportDate(String summaryDate) {
		this.reportDate = summaryDate;
	}
	
	protected File outputFolder;
	public File getOutputFolder() 
	{
		if( outputFolder == null )
		{
//			Calendar now = new GregorianCalendar();	
//			outputFolder = new File( Folders.reportFolder() + "\\" + getResourceId() + "\\" + Asset.crushDate( now ) );
			outputFolder = new File( Folders.reportFolder() + "\\" + getResourceId() );
			outputFolder.mkdirs();
//			Util.deleteAllFiles(outputFolder);
		}
		return outputFolder;
	}
	public void setOutputFolder(File outputFolder) {
		this.outputFolder = outputFolder;
	}
	
	private String resourceId = null;
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String reportName) {
		this.resourceId = reportName;
	}

    protected File propertiesFile = null;
	public File getPropertiesFile() 
	{
		if( propertiesFile == null )
		{
			propertiesFile = new File( Folders.reportConfigFolder().getAbsolutePath() + "\\" + getResourceId() + ".properties" );
		}
		return propertiesFile;        
	}
	
	public Properties getProperties() throws Exception
	{
		Properties props = new Properties();
		props.load( new FileInputStream( getPropertiesFile() ) );
    	return props;
	}
	
	private String reportTitle = null;
	public String getReportTitle() {
		return reportTitle;
	}
	public void setReportTitle(String reportTitle) {
		this.reportTitle = reportTitle;
	}

	private File outputFile = null;
	public File getOutputFile() {
		if ( outputFile == null )
		{
			outputFile = new File( getOutputFolder() + "\\output.htm" );
		}
		return outputFile;
	}

	public void setOutputFile(File outputFile) {
		this.outputFile = outputFile;
	}
	
	public String parseBarchartComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
					downloader.startThread();
					downloader.join( 10000 );
				}			
				setFile( downloader.getResultFile() );
				
				Document doc = parse();
	
				if( getReportDate() == null )
				{
					String dat = doc.select( "#dtaDate" ).first().text();			
					int pos = dat.indexOf("-");
					if( pos > -1 ) { dat = dat.substring(0,pos).trim(); }
					setReportDate(dat);
				}
				
				Element titleEl = doc.select( "#dtaName" ).first();				
				String pageTitle = null;
				if( titleEl != null ) { pageTitle = titleEl.text(); }
				
	//			Elements headers = doc.select( ".bar h2" );
				Elements tables = doc.select( ".datatable" );
							
				int tableNum = 0;
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{
					Element table = (Element) tablesit.next();				
//					Elements anchors = table.select( "a[title~=(Detailed).?]" );
						
					int rowNum = 0;
					Elements rows = table.select( "tr" );
					Elements thead = table.select( "thead" );
					if( pageTitle != null )
					{
						thead.first().prepend("<tr><th class=\"section_title\" colspan=\"10\">" + pageTitle + "</th></tr>");
					}
					
	//				Log.infoln("cells.size=" + cells.size() );
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
						Element row = (Element) rowsit.next();
	
						Element cell1 = row.select( "td:eq(0)" ).first(); if( cell1 == null ) { continue; }
						Element cell2 = row.select( "td:eq(1)" ).first(); if( cell2 == null ) { continue; }
						Element cell3 = row.select( "td:eq(2)" ).first(); if( cell3 == null ) { continue; }
						Element cell4 = row.select( "td:eq(3)" ).first(); if( cell4 == null ) { continue; }
						Element cell5 = row.select( "td:eq(4)" ).first(); if( cell5 == null ) { continue; }
						
						String tick = cell1.text();
						String name = cell2.text();
						String trade = cell3.text();
						String change = cell4.text();
						String volume = cell5.text();
						Log.infoln("[" + tick + "][" + name + "][" + trade + "][" + change + "][" + volume + "]" );
						
	//					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
						String quoteHtml = parseDetailedQuote( "http://old.barchart.com/detailedquote/stocks/" + tick, outputFolder );

						if ( quoteHtml != null )
						{
							cell1.append("<br>" + quoteHtml );
						}
						
						cell2.removeAttr("nowrap");
	
						/*
						Element lastCell = row.select( "td:eq(4)" ).first();
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
//						lastCell.append("&nbsp;" + linkHtml );
//						*/
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}

	public String parseMarketindexComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{
//				Log.infoln( "url=" + url );
				
				/* DEBUG
				setFile( new File( url ) );			
				setOutputFolder( getFile().getParentFile() );
//				*/
			
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
//					downloader.startThread();
//					downloader.join( 10000 );

					downloadSPWA( url, downloader.getResultFile() );
				}			
				setFile( downloader.getResultFile() );		

				Document doc = parse();
	
				reportDate = doc.select( ".reg_data_time" ).first().text();			
//				int pos = reportDate.indexOf("-");
//				if( pos > -1 ) { reportDate = reportDate.substring(pos+1,reportDate.length()).trim(); }

				Elements tables = doc.select( "#asx_sp_table" );
							
//				int count = 0;
				
				int tableNum = 0;
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{
					Element table = (Element) tablesit.next();				
	
					int rowNum = 0;
					Elements rows = table.select( "tr" );
					
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
//						if(count++ > 20) { break; }
						
						Element row = (Element) rowsit.next();

						Element th0 = row.select( "th:eq(0)" ).first();
						Element th1 = row.select( "th:eq(1)" ).first();
						Element th2 = row.select( "th:eq(2)" ).first(); 
						Element th4 = row.select( "th:eq(4)" ).first(); 
						Element th5 = row.select( "th:eq(5)" ).first(); 
						if( th0 != null ) { th0.remove(); }
						if( th1 != null ) { th1.text("Name"); }
						if( th2 != null ) { th2.text("Sym"); }
						if( th4 != null ) { th4.text("Change"); }
						if( th5 != null ) { th5.text("Percent"); }
						
						Element[] cells = new Element[11];
						cells[0] = row.select( "td:eq(0)" ).first(); if( cells[0] == null ) { continue; }
						cells[1] = row.select( "td:eq(1)" ).first(); if( cells[1] == null ) { continue; }
						cells[2] = row.select( "td:eq(2)" ).first(); if( cells[2] == null ) { continue; }
						cells[3] = row.select( "td:eq(3)" ).first(); if( cells[3] == null ) { continue; }
						cells[4] = row.select( "td:eq(4)" ).first(); if( cells[4] == null ) { continue; }
						cells[5] = row.select( "td:eq(5)" ).first(); if( cells[5] == null ) { continue; }
						cells[6] = row.select( "td:eq(6)" ).first(); if( cells[6] == null ) { continue; }
						cells[7] = row.select( "td:eq(7)" ).first(); if( cells[7] == null ) { continue; }
						cells[8] = row.select( "td:eq(8)" ).first(); if( cells[8] == null ) { continue; }
						cells[9] = row.select( "td:eq(9)" ).first(); if( cells[9] == null ) { continue; }
						cells[10] = row.select( "td:eq(10)" ).first(); if( cells[10] == null ) { continue; }

						// |-|Code|Company|Price|Change|%Chg|High|Low|Volume|MktCap|1Year|
						
						String tick = cells[1].text();
						String name = cells[2].text();
						Log.infoln("[" + tick + "][" + name + "]" );
						
	//					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
						String quoteHtml = parseDetailedQuote( "http://old.barchart.com/detailedquote/stocks/" + tick + ".ax", outputFolder );
						
//						cells[2].prepend( cells[1].text() + " " );
						
						String link = "http://old.barchart.com/quotes/stocks/" + tick + ".ax";
						
						cells[1].empty();
						cells[2].empty();
						cells[2].append("<a href=\"" + link + "\">" + tick + "</a>");
						cells[1].append("<a href=\"" + link + "\">" + name + "</a>");

						if( quoteHtml != null ) { cells[1].append("<br>" + quoteHtml ); }
//						cells[0].empty();
//						/*
						cells[0].remove();
//						*/
	
						/*
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
						cells[10].append("&nbsp;" + linkHtml );
//						*/
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}
	
    public void downloadSPWA( String theUrl, File file ) {
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        HtmlPage page = null;
        try {
            page = webClient.getPage( theUrl );
        } catch (Exception e) {}

//        System.out.println(page.asXml());

        try {
            PrintStream out = Util.getPrintStream( file.getAbsolutePath() );
            out.print( page.asXml() );
        }
        catch( Exception e) {
        	e.printStackTrace();
        }

        webClient.close();
    }

	public String parseYahooComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{				
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
					downloader.startThread();
					downloader.join( 10000 );
				}			
				setFile( downloader.getResultFile() );
				
				Document doc = parse();
	
				if( getReportDate() == null )
				{
					String dat = reportDate = doc.select( "#yfs_market_time" ).text();	// Yahoo
					int pos = dat.indexOf("-");
					if( pos > -1 ) { dat = dat.substring(0,pos).trim(); }
					setReportDate(dat);
				}
				
	//			Elements headers = doc.select( ".bar h2" );
				Elements tables = doc.select( ".yfnc_tableout1 table" );		// Yahoo
							
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{					
					Element table = (Element) tablesit.next();				
//					Elements anchors = table.select( "a[title~=(Detailed).?]" );
	
					Elements rows = table.select( "tr" );					
	//				Log.infoln("cells.size=" + cells.size() );
					
					int count = 0;
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
						if( debug && count++ > 5 ) { break; }
						
						Element row = (Element) rowsit.next();
	
						Element cell1 = row.select( "td:eq(0)" ).first(); if( cell1 == null ) { continue; }
						Element cell2 = row.select( "td:eq(1)" ).first(); if( cell2 == null ) { continue; }
						Element cell3 = row.select( "td:eq(2)" ).first(); if( cell3 == null ) { continue; }
						Element cell4 = row.select( "td:eq(3)" ).first(); if( cell4 == null ) { continue; }
						Element cell5 = row.select( "td:eq(4)" ).first(); if( cell5 == null ) { continue; }
						
						String tick = cell1.text();
						String name = cell2.text();
						String trade = cell3.text();
						String change = cell4.text();
						String volume = cell5.text();
						Log.infoln("[" + tick + "][" + name + "][" + trade + "][" + change + "][" + volume + "]" );

						String link = "http://finance.yahoo.com/q?s=" + tick;
						String detailedLink = "http://finance.yahoo.com/q/hp?s=" + tick + "+Historical+Prices"; 	// resourceId = dax

						if( getResourceId().equals("ftse100") ) 
						{ 
							String tickSuffix = "S"; 
							link = "http://old.barchart.com/quotes/stocks/" + tick + tickSuffix;
							detailedLink = "http://old.barchart.com/detailedquote/stocks/" + tick + tickSuffix;
						}

						cell1.empty(); cell1.append("<a href=\"" + link + "\">" + tick + "</a>");

						String quoteHtml = parseDetailedQuote( detailedLink, outputFolder );						
						if ( quoteHtml != null ) { cell1.append("<br>" + quoteHtml ); }
						
						cell2.removeAttr("nowrap");
	
						/*
						Element lastCell = row.select( "td:eq(4)" ).first();
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
//						lastCell.append("&nbsp;" + linkHtml );
//						*/
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}

	protected static final SimpleDateFormat reportDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CANADA);

	public String parseMarketvolumeComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{				
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
					downloader.startThread();
					downloader.join( 10000 );
				}			
				setFile( downloader.getResultFile() );
				
				Document doc = parse();
				
				if( getReportDate() == null )
				{
					Date now = new Date();
					String dat = reportDateFormat.format(now);
					setReportDate(dat);
				}
				
				Elements tables = doc.select( ".table" );
							
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{					
					Element table = (Element) tablesit.next();				
//					Elements anchors = table.select( "a[title~=(Detailed).?]" );
	
					Elements rows = table.select( "tr" );					
	//				Log.infoln("cells.size=" + cells.size() );
					
					int count = 0;
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
						if( debug && count > 5 ) { break; }
						
						Element row = (Element) rowsit.next();

						count++;
						if( count == 1 ) { row.remove(); }						

						Element cell1 = row.select( "td:eq(0)" ).first(); if( cell1 == null ) { continue; }
						Element cell2 = row.select( "td:eq(1)" ).first(); if( cell2 == null ) { continue; }
						Element cell3 = row.select( "td:eq(2)" ).first();
						Element cell4 = row.select( "td:eq(3)" ).first();
						
						String tick = cell1.text();
						String name = cell2.text();
						Log.infoln("[" + tick + "][" + name + "]" );
						
						String tickLink = tick;
						if( getResourceId().equals("tsx") )
						{
							tickLink = tickLink.replace(".", "-");
							List vanc = Arrays.asList(new String[]{ "SGR", "NEM" } );
							if( vanc.contains(tick) ) { tickLink += ".VN"; }
							else { tickLink += ".TO"; }
						}

						String link = "http://old.barchart.com/quotes/stocks/" + tickLink;
						cell1.empty(); 
						cell1.append("<a href=\"" + link + "\">" + tick + "</a>");
						if( name != null && !name.trim().equals("") ) { cell1.append(" - <a href=\"" + link + "\">" + name + "</a>"); }
						
	//					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
						String quoteHtml = parseDetailedQuote( "http://old.barchart.com/detailedquote/stocks/" + tickLink, outputFolder );
						
						if ( quoteHtml != null )
						{
							cell1.append("<br>" + quoteHtml );
						}
						
						cell2.remove();
						cell3.remove();
						cell4.remove();
	
						/*
						Element lastCell = row.select( "td:eq(4)" ).first();
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
//						lastCell.append("&nbsp;" + linkHtml );
//						*/						
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}


	public String parseCommodityfuturesComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
					downloader.startThread();
					downloader.join( 10000 );
				}			
				setFile( downloader.getResultFile() );
				
				Document doc = parse();
	
				if( getReportDate() == null )
				{
					String dat = doc.select( "#dtaDate" ).first().text();			
					int pos = dat.indexOf("-");
					if( pos > -1 ) { dat = dat.substring(0,pos).trim(); }
					setReportDate(dat);
				}
				
				Element titleEl = doc.select( "#dtaName" ).first();				
				String pageTitle = null;
				if( titleEl != null ) { pageTitle = titleEl.text(); }
				
	//			Elements headers = doc.select( ".bar h2" );
				Elements tables = doc.select( ".datatable" );
							
				int tableNum = 0;
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{
					Element table = (Element) tablesit.next();				
//					Elements anchors = table.select( "a[title~=(Detailed).?]" );
						
					int rowNum = 0;
					Elements rows = table.select( "tr" );
					Elements thead = table.select( "thead" );
					if( pageTitle != null )
					{
						thead.first().prepend("<tr><th class=\"section_title\" colspan=\"10\">" + pageTitle + "</th></tr>");
					}
					
	//				Log.infoln("cells.size=" + cells.size() );
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
						Element row = (Element) rowsit.next();
	
						Element cell1 = row.select( "td:eq(0)" ).first(); if( cell1 == null ) { continue; }
						Element cell2 = row.select( "td:eq(1)" ).first(); if( cell2 == null ) { continue; }
						Element cell3 = row.select( "td:eq(2)" ).first(); if( cell3 == null ) { continue; }
						Element cell4 = row.select( "td:eq(3)" ).first(); if( cell4 == null ) { continue; }
						Element cell5 = row.select( "td:eq(4)" ).first(); if( cell5 == null ) { continue; }
						
						String tick = cell2.select("a").attr("href");
//						Log.infoln("............................href=" + tick );
						int pos = tick.lastIndexOf("/");
						tick = tick.substring(pos+1, tick.length() );
						String name = cell2.text();
						String trade = cell3.text();
						String change = cell4.text();
						String volume = cell5.text();
						Log.infoln("[" + tick + "][" + name + "][" + trade + "][" + change + "][" + volume + "]" );
						
	//					String quoteHtml = parseDetailedQuote( "http://old.barchart.com" + anchors.get(rowNum++).attr("href"), outputFolder );
						String quoteHtml = parseDetailedQuote( "http://old.barchart.com/detailedquote/stocks/" + tick, outputFolder );

						if ( quoteHtml != null )
						{
							cell1.append("<br>" + quoteHtml );
						}
						
						cell2.removeAttr("nowrap");
	
						/*
						Element lastCell = row.select( "td:eq(4)" ).first();
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
//						lastCell.append("&nbsp;" + linkHtml );
//						*/
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}

	public String parseMiningfeedsComponents() throws Exception 
	{	
		String tableHtml = "";
		try
		{	
			for( String url : urls )
			{
//				Log.infoln( "url=" + url );
				
				/* DEBUG
				setFile( new File( url ) );			
				setOutputFolder( getFile().getParentFile() );
//				*/
			
				LoginContentDownloader downloader = new LoginContentDownloader( url, getOutputFolder() );						
				if( !downloader.getResultFile().exists() )
				{
//					downloader.setUserName("mpagah@hotmail.com");
//					downloader.setPassword("bernouilli27");
					downloader.startThread();
					downloader.join( 10000 );
				}			
				setFile( downloader.getResultFile() );		
						
				Document doc = parse();
	
//				reportDate = doc.select( ".reg_data_time" ).first().text();			
//				int pos = reportDate.indexOf("-");
//				if( pos > -1 ) { reportDate = reportDate.substring(pos+1,reportDate.length()).trim(); }

				Elements tables = doc.select( "#sort1" );
							
//				int count = 0;
				
				int tableNum = 0;
				Iterator tablesit = tables.iterator();			
				while(tablesit.hasNext())
				{
					Element table = (Element) tablesit.next();				
	
					int rowNum = 0;
					Elements rows = table.select( "tr" );
					
					Iterator rowsit = rows.iterator();
					while( rowsit.hasNext())
					{
//						if(count++ > 20) { break; }
						
						Element row = (Element) rowsit.next();

						Element th0 = row.select( "th:eq(0)" ).first();
						Element th3 = row.select( "th:eq(3)" ).first();
						Element th4 = row.select( "th:eq(4)" ).first();
						Element th5 = row.select( "th:eq(5)" ).first();
						Element th8 = row.select( "th:eq(8)" ).first();
						Element th9 = row.select( "th:eq(9)" ).first();
						if( th0 != null ) { th0.remove(); }
						if( th3 != null ) { th3.text("Last Price"); }
						if( th4 != null ) { th4.text("Change"); }
						if( th5 != null ) { th5.text("% Change"); }
						if( th8 != null ) { th8.text("52-wk Low"); }
						if( th9 != null ) { th9.text("52-wk High"); }
						
						Element[] cells = new Element[11];
						cells[0] = row.select( "td:eq(0)" ).first(); if( cells[0] == null ) { continue; }
						cells[1] = row.select( "td:eq(1)" ).first(); if( cells[1] == null ) { continue; }
						cells[2] = row.select( "td:eq(2)" ).first(); if( cells[2] == null ) { continue; }
						cells[3] = row.select( "td:eq(3)" ).first(); if( cells[3] == null ) { continue; }
						cells[4] = row.select( "td:eq(4)" ).first(); if( cells[4] == null ) { continue; }
						cells[5] = row.select( "td:eq(5)" ).first(); if( cells[5] == null ) { continue; }
						cells[6] = row.select( "td:eq(6)" ).first(); if( cells[6] == null ) { continue; }
						cells[7] = row.select( "td:eq(7)" ).first(); if( cells[7] == null ) { continue; }
						cells[8] = row.select( "td:eq(8)" ).first(); if( cells[8] == null ) { continue; }
						cells[9] = row.select( "td:eq(9)" ).first(); if( cells[9] == null ) { continue; }
						cells[10] = row.select( "td:eq(10)" ).first(); if( cells[10] == null ) { continue; }
//						cells[11] = row.select( "td:eq(10)" ).first(); if( cells[10] == null ) { continue; }
						
						String tick = cells[2].text();
						String name = cells[1].select( "a" ).first().text();
						Log.infoln("[" + tick + "][" + name + "]" );
						
						String bctick = tick;
						if( tick.indexOf(".V") > -1 )
						{
							bctick += "N";
						}
						
						String quoteHtml = parseDetailedQuote( "http://old.barchart.com/detailedquote/stocks/" + bctick, outputFolder );
												
						String link = "http://old.barchart.com/quotes/stocks/" + bctick;
						
						cells[1].empty();
						cells[2].empty();
						cells[2].append("<a href=\"" + link + "\">" + tick + "</a>");
						cells[1].append("<a href=\"" + link + "\">" + name + "</a>");

						if( quoteHtml != null ) { cells[1].append("<br>" + quoteHtml ); }
						cells[0].remove();
	
						/*
						String href = "http://old.barchart.com/chart.php?sym=" + tick + "&style=technical&template=&p=DO&d=M&sd=&ed=&size=M&log=0&t=BAR&v=2&g=1&evnt=1&late=1&o1=&o2=&o3=&sh=100&indicators=RSI%2814%2C0%2C100%2C10079487%2C10040064%29%3BMOMENT%2820%2C13395558%29%3BCCI%2820%2C6710886%2C100%2C26367%2C16724736%29%3BADX%2814%2C26367%2C16724736%2C2186785%29&chartindicator_1_code=RSI&chartindicator_1_param_0=14&chartindicator_1_param_1=0&chartindicator_1_param_2=100&chartindicator_1_param_3=10079487&chartindicator_1_param_4=10040064&chartindicator_2_code=MOMENT&chartindicator_2_param_0=20&chartindicator_2_param_1=13395558&chartindicator_3_code=CCI&chartindicator_3_param_0=20&chartindicator_3_param_1=6710886&chartindicator_3_param_2=100&chartindicator_3_param_3=26367&chartindicator_3_param_4=16724736&chartindicator_4_code=ADX&chartindicator_4_param_0=14&chartindicator_4_param_1=26367&chartindicator_4_param_2=16724736&chartindicator_4_param_3=2186785&addindicator=&submitted=1&fpage=&txtDate=#jump";
						String linkHtml = "<a title='Technical indicators' target='_technical' style='text-decoration:none;font-weight:bold;font-style:italic;font-family:courier;' href='" + href + "'>I</a>";
						cells[10].append("&nbsp;" + linkHtml );
						*/
					}
					tableHtml += table.html();
				}
			}			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return tableHtml;
	}
}
