package leafspider.util;

import leafspider.extract.ExcelSpreadsheet;

import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Iterator;

public class Downloader 
{
	public static void main ( String[] args )
	{
		try
		{
			File folder = new File( "C:\\TEMP\\downloader" );	//C:\\tmp\\out" );
			folder.mkdirs();

			//String url = "https://ichart.finance.yahoo.com/table.csv?s=IBM&a=3&b=18&c=2016&d=3&e=18&f=2017&g=d&ignore=.csv";
			//String url = "http://www.google.ca/finance/historical?q=MRES.US&start=0&num=100&output=csv";
			//String url = "https://www.cmegroup.com/market-data/volume-open-interest/exchange-volume.html";
			//String url ="https://www.cmegroup.com/CmeWS/exp/voiTotalsViewExport.ctl?media=xls&tradeDate=20210309&reportType=F";
			//String url ="https://www.cmegroup.com/CmeWS/exp/voiProductsViewExport.ctl?media=xls&tradeDate=20210318&assetClassId=3&reportType=F&excluded=CEE,CEU,KCB";
			String url = "https://www.ted.com/talks/lisa_genova_how_your_memory_works_and_why_forgetting_is_totally_ok/transcript";
			File file = downloadFile( url, folder, false );
			//File file = new File( folder.getAbsolutePath() + "\\https___www_cmegroup_com_CmeWS_exp_voiTotalsViewExport_ctl_media_xls_tradeDate_20210309_reportType_F.html" );

			//ExcelSpreadsheet.extractText(file.getAbsolutePath(), folder.getAbsolutePath() + "\\voiProductsViewExport.csv");

			System.out.println( file.getAbsolutePath() );
			System.out.println( "Done" );				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public static void main2 ( String[] args )
	{
		try
		{
			File folder = new File( "C:\\Workspace\\Ultra\\Chad\\pages" );
			folder.mkdirs();

			File urlfile = new File( "C:\\Workspace\\Ultra\\Chad\\URLs.txt" );
			Iterator urls = Util.getArrayListFromFile(urlfile.getAbsolutePath()).iterator();
			
			while(urls.hasNext())
			{
//				String url = "http://ichart.finance.yahoo.com/table.csv?s=ZIOP&d=7&e=19&f=2011&g=d&a=7&b=24&c=2005&ignore=.csv";
				String url = (String) urls.next();
				File file = downloadFile( url, folder );
			}
			System.out.println( "Done" );				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public static File downloadFile( String url, File folder ) throws MalformedURLException {
		return downloadFile( url, folder, true );
	}

	public static File downloadFile( String url, File folder, boolean prependUrl  ) throws MalformedURLException
	{	
//		System.out.println( url );
		
		int downloadTimeout = 5000;
		LinkContentDownloader downloader = new LinkContentDownloader( "" + url, folder );
		downloader.setPrependUrl( prependUrl );		// jmh 2021-03-10
//		downloader.getResultFile().delete();		// jmh 2012-09-24

//		Log.infoln( "downloader.getResultFile().length()=" + downloader.getResultFile().length() + " =0 = " + (downloader.getResultFile().length() == 0) );

//		downloader.debug = true;
		//if ( !downloader.getResultFile().exists() ) {		// jmh 2017-09-19
		if ( !downloader.getResultFile().exists() || downloader.getResultFile().length() == 0 ) {		// jmh 2021-03-01

			downloader.startThread();
			try {
				downloader.join( downloadTimeout );
			}
			catch( Exception e ) {
				e.printStackTrace();
			}

			if ( downloader.getResultFile().exists() ) {
				//Log.infoln( "File: [" + downloader.getResultFile().length() + "] " + downloader.getResultFile().getName() );
			}
			else {
				//Log.infoln( "Downloader.downloadFile Empty file: " + downloader.getResultFile().getName() );
				downloader.success = false;
				try {
					PrintStream writer = Util.getPrintStream( downloader.getResultFile().getAbsolutePath() );
//					writer.println( "<!--" + url + "-->" );
					writer.close();
				}
				catch( Exception e ) {
					e.printStackTrace();
				}
			}

			if ( downloader.isAlive() ) {
				downloader.stopThread();
			}
		}
		return downloader.getResultFile();
	}

	private static String name = null;
	public static String getName() {
		return name;
	}
	public static void setName(String name) {
		name = name;
	}
	
	private static String password = null;
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		password = password;
	}

}
