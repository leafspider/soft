package soft.portfolio;

import java.net.URL;
import java.util.Iterator;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;

import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import soft.assetlist.Alist;

public class Stock extends DatabaseRecord
{	
	private String projectName = "portfolio";
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName()
	{ 
		return projectName;
	}

	public Stock() { }

	public Stock( String port, String tick, double tar)
	{
		setPortfolio(port);
		setTicker(tick);
		setTargetPrice(tar);
	}
	
	public static void main ( String[] args )
	{
		try
		{				
			Stock stock = new Stock("Portfolio1", "CLS.TO", 67);
			stock.populate();

			System.out.println( "ticker=" + stock.getTicker() );
			System.out.println( "price=" + stock.getEntryPrice() );
			
//			/*
			(new Stock("Portfolio1", "AHGP", 67)).saveOrUpdateAndCommit();
			(new Stock("Portfolio1", "BNCL", 37)).saveOrUpdateAndCommit();
			(new Stock("Portfolio1", "CCMP", 47)).saveOrUpdateAndCommit();
			(new Stock("Portfolio1", "ACW", 27)).saveOrUpdateAndCommit();
			
			Iterator stocks = StockProject.getDatabaseManager().listRecords("Stock").iterator();
			while(stocks.hasNext())
			{
				Stock stock1 = (Stock) stocks.next();
				Log.infoln( "" + stock1.getTicker() );
			}
//			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
			
	private String ticker = null;
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String tick) {
		this.ticker = tick;
	}
	
	private double entryPrice = 0.d;
	public double getEntryPrice() {
		return entryPrice;
	}
	public void setEntryPrice(double price) {
		this.entryPrice = price;
	}
	
	private double targetPrice = 0.d;
	public double getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(double target) {
		this.targetPrice = target;
	}

	private String portfolio = null;
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}

	public double stake() 
	{
		return shares * entryPrice;
	}
	private double projectedValue()
	{
		return targetPrice * shares;
	}
	public double projectedReturn()
	{
		return projectedValue() - stake();
	}
	public double projectedReturnPercent()
	{	
		return 100 * projectedReturn()/stake();
	}	
	private double currentValue()
	{
		return currentPrice * shares;
	}
	public double currentReturn()
	{
		return currentValue() - stake();
	}
	public double currentReturnPercent()
	{	
		return 100 * currentReturn()/stake();
	}	

	public Element listElement()
	{
		Element elem = new Element( "stock" );
		elem.addContent( new Element( "portfolio" ).addContent( new CDATA( "" + getPortfolio() ) ) );
		elem.addContent( new Element( "company" ).addContent( new CDATA( "" + getCompany() ) ) );
		elem.addContent( new Element( "ticker" ).addContent( new CDATA( "" + getTicker() ) ) );
		elem.addContent( new Element( "entryPrice" ).addContent( new CDATA( "" + getEntryPrice() ) ) );
		elem.addContent( new Element( "targetPrice" ).addContent( new CDATA( "" + getTargetPrice() ) ) );
		elem.addContent( new Element( "shares" ).addContent( new CDATA( "" + getShares() ) ) );
		elem.addContent( new Element( "tradeAmount" ).addContent( new CDATA( "" + getTradeAmount() ) ) );
		elem.addContent( new Element( "projectedReturn" ).addContent( new CDATA( "" + projectedReturn() ) ) );
		elem.addContent( new Element( "projectedReturnPercent" ).addContent( new CDATA( "" + projectedReturnPercent() ) ) );
		elem.addContent( new Element( "currentPrice" ).addContent( new CDATA( "" + getCurrentPrice() ) ) );
		elem.addContent( new Element( "currentReturn" ).addContent( new CDATA( "" + currentReturn() ) ) );
		elem.addContent( new Element( "currentReturnPercent" ).addContent( new CDATA( "" + currentReturnPercent() ) ) );
		elem.addContent( new Element( "website" ).addContent( new CDATA( "" + getWebsite() ) ) );
		elem.addContent( new Element( "entryDate" ).addContent( new CDATA( "" + getEntryDate() ) ) );
		return elem;
	}
	
	public void populate() throws Exception
	{
		String url = "http://finance.yahoo.com/q?s=" + ticker.toLowerCase();
		String qprice = "span[id=yfs_l84_" + ticker.toLowerCase() + "]";

//		/* DEBUG OFF
//		Document doc = Jsoup.connect( url ).get();
		Document doc = Jsoup.parse( new URL(url), 5000 );
//		Elements els = doc.select( qprice );
		double pri = Double.parseDouble(doc.select( qprice ).iterator().next().text());
//		*/

		/* DEBUG ON
		double rand = Math.random();
		double pri = getEntryPrice();
		if (rand>0.2) { pri = 1.49; } 
		if (rand<0.1) { pri = -0.16; }
//		*/ 

//		Log.infoln("Stock.update ticker=" + ticker + " currentPrice=" + pri);

		setCurrentPrice(pri);
	}
	
	private double currentPrice = 0.0d;
	public double getCurrentPrice() {
		return currentPrice;
	}
	public void setCurrentPrice(double currentPrice) {
		this.currentPrice = currentPrice;
	}
	
	private int shares = 0;
	public int getShares() {
		return shares;
	}
	public void setShares(int shares) {
		this.shares = shares;
	}
	
	private String company = null;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	
	private double tradeAmount = 0.0d;
	public double getTradeAmount() {
		return tradeAmount;
	}
	public void setTradeAmount(double tradeAmount) {
		this.tradeAmount = tradeAmount;
	} 
	
	private String website = null;
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	
	private String entryDate = "";
	public String getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(String entryDate) {
		this.entryDate = entryDate;
	}
}
