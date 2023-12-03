package soft.report;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.TreeSet;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import leafspider.util.Log;
import leafspider.util.LoginContentDownloader;
import leafspider.util.Util;

public class BarchartFutureOptions extends BarchartFutures
{
	@Override
	public String getProjectName() 
	{
		return "stratvol";
	}
	
	public static void main ( String[] args )
	{
		try
		{
			BarchartFutureOptions page = new BarchartFutureOptions();
			page.setResourceId("futureoptions");
			Log.debug = true;
//			page.populate("https://www.cmegroup.com/CmeWS/exp/voiTotalsViewExport.ctl?media=xls&tradeDate=20210309&reportType=F");
			page.populate("https://www.cmegroup.com/CmeWS/exp/voiProductDetailsViewExport.ctl?media=xls&tradeDate=20210310&reportType=F&productId=458");	// Silver
			page.populate("https://www.cmegroup.com/CmeWS/exp/voiProductDetailsViewExport.ctl?media=xls&tradeDate=20210310&reportType=F&productId=437");	// Gold
			/*
			page.populate("http://old.barchart.com/commodityfutures/US_Dollar_Futures/options/DX");
			page.populate("http://old.barchart.com/commodityfutures/British_Pound_Futures/options/B6");
			page.populate("http://old.barchart.com/commodityfutures/Canadian_Dollar_Futures/options/D6");
			page.populate("http://old.barchart.com/commodityfutures/Japanese_Yen_Futures/options/J6");
			page.populate("http://old.barchart.com/commodityfutures/Swiss_Franc_Futures/options/S6");
			page.populate("http://old.barchart.com/commodityfutures/Euro_Futures/options/E6");
			page.populate("http://old.barchart.com/commodityfutures/Australian_Dollar_Futures/options/A6");
			page.populate("http://old.barchart.com/commodityfutures/Crude_Oil_WTI_Futures/options/CL");
			page.populate("http://old.barchart.com/commodityfutures/Gasoline_RBOB_Futures/options/RB");
			page.populate("http://old.barchart.com/commodityfutures/Natural_Gas_Futures/options/NG");
			page.populate("http://old.barchart.com/commodityfutures/E-Mini_S%26P_500_Futures/options/ES");
			page.populate("http://old.barchart.com/commodityfutures/E-Mini_Nasdaq_100_Futures/options/NQ");
			page.populate("http://old.barchart.com/commodityfutures/DJIA_mini-sized_Futures/options/YM");
			page.populate("http://old.barchart.com/commodityfutures/Russell_2000_Mini_Futures/options/RJ");
			page.populate("http://old.barchart.com/commodityfutures/E-Mini_S%26P_Midcap_Futures/options/EW");
			page.populate("http://old.barchart.com/commodityfutures/Gold_Futures/options/GC");
			page.populate("http://old.barchart.com/commodityfutures/Silver_Futures/options/SI");
			page.populate("http://old.barchart.com/commodityfutures/High_Grade_Copper_Futures/options/HG");
			*/

			Properties prop = page.getProperties();
			String title = prop.getProperty("title");
			
			TreeSet<String> urls = new TreeSet<String>();
			
			for( int i=0; i< 30; i++) {
				String val = prop.getProperty("url" + i);
				if( val != null ) { urls.add( val ); }
			}
			
			Iterator<String> urlsit = urls.iterator();
			while( urlsit.hasNext() ) {
				page.populate( urlsit.next() );
			}			

			Log.infoln(page.getHtml());
			File file = new File( page.getOutputFolder().getAbsolutePath() + "\\output.csv");
			Log.infoln(file.getAbsolutePath());
			PrintStream out = Util.getPrintStream( file.getAbsolutePath() );
			out.print(page.getHtml());
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public File getOutputFolder() 
	{
		if( outputFolder == null )
		{
			outputFolder = new File( Folders.stratvolFolder() + "\\" + getResourceId() );
			outputFolder.mkdirs();
//			Util.deleteAllFiles(outputFolder);
		}
		return outputFolder;
	}
	
	@Override
	public File getPropertiesFile() 
	{
		if( propertiesFile == null ) {
			propertiesFile = new File( Folders.stratvolConfigFolder().getAbsolutePath() + "\\" + getResourceId() + ".properties" );
		}
		return propertiesFile;        
	}
	
	private String getDetailedQuoteUrl(String ticker ) {
		return "http://old.barchart.com/detailedquote/futures/" + ticker + "*0";					
	}

	public void populate( String url ) throws Exception  {

		try {

			setUrl( url );
			int pos = url.lastIndexOf("/");
			String ticker = url.substring(pos+1, url.length());
//			Log.infoln( "ticker=" + ticker );
			
			Element quotetable = parseDetailedQuoteTable( getDetailedQuoteUrl(ticker), getOutputFolder() );
			Elements quoterows = quotetable.select( "tr" );				
			Iterator quoteit = quoterows.iterator();
			ArrayList<String> quotes = new ArrayList<String>(); 
			while( quoteit.hasNext())
			{
				Element row = (Element) quoteit.next();
				Iterator<Element> cells = row.select( "td" ).iterator();
				String quote = "";
				while( cells.hasNext() ) {					
					Element cell = cells.next();
					quote += cell.html() + "\t";
				}
//				Log.infoln(quote);					
				quotes.add(quote);					
			}

			/* jmh 2021-03-04
			LoginContentDownloader downloader = new LoginContentDownloader(getUrl(), getOutputFolder() );
//			if( !downloader.getResultFile().exists() )
			{
//				downloader.setUserName("mpagah@hotmail.com");
//				downloader.setPassword("bernouilli27");
				downloader.startThread();
				downloader.join( 10000 );
			}
			
			setFile( downloader.getResultFile() );			
//			Log.infoln( getFile().getAbsolutePath() );
*/
			setFile( new File("C:\\Workspace\\Ultra\\Jake Tiley\\StratVol\\fo.htm") );
			
			Document doc = parse();

//			html = "";
			//String pagehead = doc.select( "#pagehead h1" ).first().ownText();
			//pagehead = pagehead.replaceAll("Futures Options - ", "");
			String pagehead = "StratVol";
			reportDate = "now";	//doc.select( "#dtaDate" ).first().text();
			Elements tables = doc.select( ".datatable_simple" );
			
			html += "\n" + pagehead + "\t" + ticker + "\t" + reportDate + "\n";
			html += "Strike\tOpen\tHigh\tLow\tCurrent\tChange\tVolume\tTime\tPrem($)\tDate\tOpen\tHigh\tLow\tLast\tChange\t%Change\tVolume\n";
			
			int tableNum = 0;
			Iterator tablesit = tables.iterator();			
			while(tablesit.hasNext())
			{
				Element table = (Element) tablesit.next();				
				Elements allrows = table.select( "tr" );

				ArrayList<Element> rows = new ArrayList<Element>();
				boolean gotBolds = false;
				
				for(int i=0; i<allrows.size(); i++)
				{
					Element row = (Element) allrows.get(i);
					/*
					String style = row.attr("style");
					if( style.indexOf("bold") > -1 ) {
						if( !gotBolds && i > 2 ) {
							rows.add(allrows.get(i-3));
							rows.add(allrows.get(i-2));
							rows.add(allrows.get(i-1));
							rows.add(row);
							gotBolds = true;
						}
						else {
							rows.add(row);
						}
					}
					else if ( gotBolds ) {
						rows.add(row);
						rows.add(allrows.get(i+1));
						rows.add(allrows.get(i+2));
						break;
					}
					*/
					rows.add(row);
				}
	
//				Log.infoln("rows.size=" + rows.size() );
					
				int rownum = 0;
				Iterator rowsit = rows.iterator();
				while( rowsit.hasNext()) {

					Element row = (Element) rowsit.next();
//					Log.info(row);

					Iterator<Element> cells = row.select( "td" ).iterator();
					while( cells.hasNext() ) {
						
						Element cell = cells.next();
						Elements spans = cell.getElementsByTag("span");
						String val = "";
						if( spans.size() > 0 ) {
							val = spans.get(0).html();												
						}
						else {
							val = cell.html();
						}
						html += val + "\t";
					}
					if( rownum < quotes.size() ) {
						html += quotes.get(rownum++);
					}
					html += "\n";					
				}
			}
			html = html.replaceAll("&nbsp;","");
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			html += "<tr><td>A Detailed Quote is not available for this asset</td></tr>";
		}
	}
	
	protected Element parseDetailedQuoteTable( String href, File outputFolder ) throws Exception
	{
		LoginContentDownloader downloader = new LoginContentDownloader(href, outputFolder );
		if( !downloader.getResultFile().exists() ) 	// jmh 2021-02-18
		{
			downloader.debug = false;
//			downloader.setUserName("mpagah@hotmail.com");
//			downloader.setPassword("bernouilli27");
			downloader.startThread();
			downloader.join( 5000 );
		}
		
		//File file = new File( outputFolder.getAbsolutePath() + "\\" + downloader.resultFileName );
		File file = new File( "C:\\Workspace\\Ultra\\Jake Tiley\\StratVol\\dq.htm");			// jmh 2021-03-04
		if( file.exists() ) {
			return parseBarchartDetailedQuoteTable(file);
		}
		return null;		
	}
	
	protected Element parseBarchartDetailedQuoteTable(File file) throws Exception
	{
		Document doc = Jsoup.parse(file, null);
		Element table = doc.select(".datatable_simple").first();
		if( table == null ) { return null; }
		Element header = table.getElementsByTag("tr").first();
		header.remove();
		return table;
	}
}
