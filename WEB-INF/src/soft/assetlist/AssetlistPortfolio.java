package soft.assetlist;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.portfolio.Stock;

public class AssetlistPortfolio extends AssetlistRepresentation
{
	public static String representation = "portfolio";
    public String getRepresentation() { return representation; }

    public String getXml() throws RepresentationException
    {
    	String name = getResourceId();
        if ( name == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( "portfolio" );
//	   		root.addContent( new Element( "project" ).addContent( new CDATA( getCollection() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );

			// TODO Hardcoded for now
//			String[] tickers = getTickerList( name, null );

			/*
			String[] tickers = new String[] { 		"GQC.V",						"ONE.TO",				"RVX.TO",						"OLV.V",					"BGM.V",							"ROX.V",							"TIG.V",				"MDG.TO",					"SGN.V",						"ARA.V",					"EPO.V",						"ADL.V",						"WKM.V" };
        	String[] companies = new String[] { 	"GoldQuest Mining Corp",		"01 Communique Lab",	"Resverlogix",					"Olivut Resources",			"Barkerville Gold Mines",			"Canstar Resources",				"Tigray Resources",		"Medicago",					"Scorpio Gold",					"Anconia Resources",		"Encanto Potash",				"Adira Energy",					"West Kirkland Mining" };
        	double[] entryPrices = new double[] {	0.45, 							0.75, 					1.83, 							1.19, 						1.02, 								0.14, 								0.17, 					0.46, 						0.76, 							0.16, 						0.23, 							0.18, 							0.3 };
        	double[] targetPrices = new double[] {	3.0, 							3.00, 					5.00, 							3.00, 						2.00, 								2.00, 								2.00, 					1.00, 						1.50, 							2.00, 						2.00, 							3.00, 							3.00 };
        	int[] shares = new int[] { 				20000,							20000,					10000,							15000,						10000,								100000,								75000,					30000,						30000,							50000,						30000,							50000,							30000 };
        	String[] websites = new String[] { 		"http://www.goldquestcorp.com",	"http://www.01com.com",	"http://www.resverlogix.com/",	"http://www.olivut.com",	"http://www.barkervillegold.com",	"http://www.canstarresources.com",	"http://www.tigray.ca",	"http://www.medicago.com",	"http://www.scorpiogold.com",	"http://www.anconia.ca/",	"http://www.encantopotash.com",	"http://www.adiraenergy.com",	"http://www.wkmining.com" };
        	String[] entryDates = new String[] {	"2012-05-28",					"2012-06-26",			"2012-06-26",					"2012-07-23",				"2012-07-04",						"2012-07-19",						"2012-07-23",			"2012-06-26",				"2012-07-26",					"2012-07-23",				"2012-07-26",					"2012-06-26",					"2012-07-23" };
        	*/

			String[] tickers = new String[] { 		"GQC.V",						"ONE.TO",				"RVX.TO",						"OLV.V",					"ROX.V",							"TIG.V",				"MDG.TO",					"SGN.V",						"ARA.V",					"EPO.V",						"ADL.V",						"WKM.V" };
        	String[] companies = new String[] { 	"GoldQuest Mining Corp",		"01 Communique Lab",	"Resverlogix",					"Olivut Resources",			"Canstar Resources",				"Tigray Resources",		"Medicago",					"Scorpio Gold",					"Anconia Resources",		"Encanto Potash",				"Adira Energy",					"West Kirkland Mining" };
        	double[] entryPrices = new double[] {	0.45, 							0.75, 					1.79, 							0.99, 						0.2425, 							0.17, 					0.46, 						0.76, 							0.23, 						0.23, 							0.18, 							0.3 };
        	double[] targetPrices = new double[] {	3.0, 							3.00, 					5.00, 							3.00, 						2.00, 								2.00, 					1.00, 						1.50, 							2.00, 						2.00, 							3.00, 							3.00 };
        	int[] shares = new int[] { 				8000,							15000,					15286,							26582,						191296,								45000,					30000,						30000,							54666,						30000,							50000,							30000 };
        	String[] websites = new String[] { 		"http://www.goldquestcorp.com",	"http://www.01com.com",	"http://www.resverlogix.com/",	"http://www.olivut.com",	"http://www.canstarresources.com",	"http://www.tigray.ca",	"http://www.medicago.com",	"http://www.scorpiogold.com",	"http://www.anconia.ca/",	"http://www.encantopotash.com",	"http://www.adiraenergy.com",	"http://www.wkmining.com" };
        	String[] entryDates = new String[] {	"2012-05-28",					"2012-06-26",			"2012-06-26",					"2012-07-23",				"2012-07-19",						"2012-07-23",			"2012-06-26",				"2012-07-26",					"2012-07-23",				"2012-07-26",					"2012-06-26",					"2012-07-23" };

//        	Log.infoln("AssetlistPortfolio.getXml 0" );
			for (int i=0; i<tickers.length; i++)
			{
//	        	Log.infoln("tickers[" + i + "]=" + tickers[i] );
//				Stock stock = new Stock(getResourceId(), tickers[i], getTarget(tickers[i]));
				Stock stock = new Stock();
				stock.setPortfolio(getResourceId());
				stock.setCompany(companies[i]);
				stock.setTicker(tickers[i]);
				stock.setEntryPrice(entryPrices[i]);
				stock.setTargetPrice(targetPrices[i]);
				stock.setShares(shares[i]);
				stock.setTradeAmount(shares[i]*entryPrices[i]);
				stock.setWebsite(websites[i]);
				stock.setEntryDate(entryDates[i]);
				stock.populate();
				root.addContent(stock.listElement());
			}
//        	Log.infoln("AssetlistPortfolio.getXml 1" );
			
			/*
			double currentPercentage = 100.0;
			root.addContent( new Element( "currentPercentage" ).addContent( new CDATA( "" + currentPercentage ) ) );

			double projectedPercentage = 99.0;
			root.addContent( new Element( "projectedPercentage" ).addContent( new CDATA( "" + projectedPercentage ) ) );
			*/

			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    /*
    private double getTarget(String ticker)
    {
    	return 20.0;
    }
    
    private String[] getTickerList( String listName, Calendar end )
    {
    	String url = null;
    	String[] list = null;

    	if ( listName.equals( "list" ) )
    	{
    		String ticks = getRequest().getParameter("ticks");
			Log.infoln( "ticks=" + ticks );
    		list = ticks.split( "," );
    	}
    	else if ( listName.equals( "milliondollar" ) )
    	{
        	list = new String[] { "ACW","AHGP","AIMC","BNCL","CCMP","CLS.TO","CLWR","COBR","CSF.TO","CTCT","FCCY","FVE","ISSC","KDN","LPNT","MBLX" };
    	}
    	else
    	{
	    	if ( listName.equals("earnings_today"))
	    	{
	    		url = "http://biz.yahoo.com/research/earncal/today.html";
	    	}	
	    	
	    	if ( url != null )
	    	{
	    		list = parseTickers( url, end );
	    	}
    	}
    	return list;
    }
    */

    /*
    protected String[] parseTickers( String url, Calendar end )
    {
//    	Log.infoln( "url=" + url );
    	String[] list = null;
    	if ( url != null )
    	{
    		try
    		{
    	    	Alist alist = AlistVariable.instance(url);
    	    	if (alist != null)
    	    	{
    	    		alist.populate();
    	    		list = alist.tickers.toArray(new String[] {});
//        	    	Log.infoln( "alist.title=" + alist.title);
    	    	}
    	    	else
    	    	{
		    		File folder = Asset.croutFolder( end );
		    		File file = Downloader.downloadFile( url, folder );
	
					AssetlistParserBarcharts parser = new AssetlistParserBarcharts();				
					list = parser.getTickers( file );				
    	    	}
		    	Log.infoln( "alist.length=" + list.length );
    		}
    		catch( Exception e )
    		{
    			e.printStackTrace();
    		}
    	}
    	return list;
    }
    */
}
