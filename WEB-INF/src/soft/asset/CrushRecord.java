package soft.asset;

import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import leafspider.util.JsonConverter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.jdom2.CDATA;
import org.jdom2.Element;

import leafspider.db.DatabaseRecord;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;
import leafspider.util.Util;
import leafspider.util.XmlJdomWriter;
import scenario.more.ExhibitorProject;

public class CrushRecord extends DatabaseRecord
{	
	public static void main ( String[] args )
	{
		Log.debug = true;

   		try {
//			List<String> strLst = Arrays.asList("1", "2", "3", "4", "5", "6", "7");
//			List<String> newLst = strLst.stream().map(str->str).collect(Collectors.toList());
//			System.out.println(newLst);

			ArrayList prices = new ArrayList();

			for ( int j = 0; j< 5; j++ ) {
				String[] tokens = new String[5];
				for (int i = 0; i < 5; i++) {
					tokens[i] = "" + i;
				}
				prices.add(tokens);
			}

			System.out.println(prices);

			String json = JsonConverter.objectToJson( prices );
			System.out.println(json);

			ArrayList list = JsonConverter.jsonToArrayList( json );
			System.out.println(list);

   			/*
			List records = CrushProject.getDatabaseManager().listRecords("CrushRecord");
			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				Element elem = crush.listElement();
				XmlJdomWriter writer = new XmlJdomWriter();
				Log.infoln( writer.writeToString(elem) );
			}
			*/
   		}
   		catch( Exception e) {}
		
		try
		{
			/*
			Iterator crushes = CrushMock.getStockValues("NNA.V").iterator();			
			while(crushes.hasNext())
			{
				CrushRecord crush = (CrushRecord) crushes.next();
				crush.saveAndCommit();
//				break;
			}
//			*/

			/*
			System.out.println( "count=" + CrushProject.getDatabaseManager().countRecords("CrushRecord") );
			
//			String select = "from CrushRecord where ticker='NNA.V' and startDate='2011-09-01' and endDate='2012-09-01'";

			boolean isFirst = true;
			Iterator list = CrushProject.getDatabaseManager().listRecords("CrushRecord").iterator();
//			Iterator list = CrushProject.getDatabaseManager().selectRecords( select ).iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				if(isFirst)
				{
					isFirst = false;
//					crush.reportFields();
				}
//				crush.reportVals();

				Element elem = crush.element();
				XmlJdomWriter writer = new XmlJdomWriter();
				Log.infoln( writer.writeToString(elem) );

//				break;
			}
//			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public String getProjectName()
	{ 
		return CrushProject.projectName;
	}
	
	private String ticker;
	private String startDate;
	private String endDate;
	private double close;
	private double flow;
	private double peel;
	private double jam;
	private double press;
	
	private double crush;
	private double pear;
	private double plate;
	private double cherry;

	private String prices;

	public String getPrices() { return prices; }
	public void setPrices(String prices) { this.prices = prices; }

	public double getPress() {
		return press;
	}
	public void setPress(double press) {
		this.press = press;
	}

	public double getCrush() { return crush; }
	public void setCrush(double crush) {
		this.crush = crush;
	}

	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}
	
	public double getClose() {
		return close;
	}
	public void setClose(double close) {
		this.close = close;
	}

	public double getPeel() {
		return peel;
	}
	public void setPeel(double high) {
		this.peel = high;
	}

	public double getJam() {
		return jam;
	}
	public void setJam(double low) {
		this.jam = low;
	}

	public double getFlow() {
		return flow;
	}
	public void setFlow(double open) {
		this.flow = open;
	}
	
	public double getCherry() {
		return cherry;
	}
	public void setCherry(double cherry) {
		this.cherry = cherry;
	}

	public double getPlate() {
		return plate;
	}
	public void setPlate(double plate) {
		this.plate = plate;
	} 

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String start) {
		this.startDate = start;
	}
	
	public String getEndDate() { return endDate; }
	public void setEndDate(String end) {
		this.endDate = end;
	}

	public double getPear() {
		return pear;
	}
	public void setPear(double pear) {
		this.pear = pear;
	}
	
	/*
	private String dailyQuoteHtm = null;
	public String getDailyQuoteHtm() 
	{
		return dbdecode( dailyQuoteHtm );
	}
	public void setDailyQuoteHtm(String dailyQuoteHtm) 
	{
		this.dailyQuoteHtm = dbencode( dailyQuoteHtm );
	}
	*/
	
	private String closeSeries;
	public String getCloseSeries() {
		return closeSeries;
	}
	public void setCloseSeries(String closeSeries) {
		this.closeSeries = closeSeries;
	}

	private String flowSeries;
	public String getFlowSeries() {
		return flowSeries;
	}
	public void setFlowSeries(String flowSeries) {
		this.flowSeries = flowSeries;
	}

	private String plateSeries;
	public String getPlateSeries() {
		return plateSeries;
	}
	public void setPlateSeries(String plateSeries) {
		this.plateSeries = plateSeries;
	}

	private String pearSeries;
	public String getPearSeries() {
		return pearSeries;
	}
	public void setPearSeries(String pearSeries) {
		this.pearSeries = pearSeries;
	}

	private String crushSeries;
	public String getCrushSeries() {
		return crushSeries;
	}
	public void setCrushSeries(String crushSeries) {
		this.crushSeries = crushSeries;
	}

	private String volumeSeries;
	public String getVolumeSeries() {
		return volumeSeries;
	}
	public void setVolumeSeries(String volumeSeries) {
		this.volumeSeries = volumeSeries;
	}

	private String dateSeries;
	public String getDateSeries() {
		return dateSeries;
	}
	public void setDateSeries(String dateSeries) {
		this.dateSeries = dateSeries;
	}

	/*
    public String getXml() throws RepresentationException
    {
		return Representation.getJdomWriter().writeToString( getElement() );
    }
    */

    public Element element() throws RepresentationException
    {
    	try
    	{
			//Element root = new Element( AssetCrush.representation );
			Element root = new Element( "crushRecord" );

			root.addContent( new Element( "ticker" ).addContent( new CDATA( ticker ) ) );			
			root.addContent( new Element( "close" ).addContent( new CDATA( closeSeries ) ) );
			root.addContent( new Element( "flow" ).addContent( new CDATA( flowSeries ) ) );
			root.addContent( new Element( "plate" ).addContent( new CDATA( plateSeries ) ) );
			root.addContent( new Element( "pear" ).addContent( new CDATA( pearSeries ) ) );
			root.addContent( new Element( "crush" ).addContent( new CDATA( crushSeries ) ) );
			root.addContent( new Element( "date" ).addContent( new CDATA( dateSeries ) ) );
			root.addContent( new Element( "cherryWidth" ).addContent( new CDATA( "" + cherry ) ) );
			root.addContent( new Element( "cherryColor" ).addContent( new CDATA( "" + Asset.getCherryColor(cherry) ) ) );

			/*
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProjectName() ) ) );
			root.addContent( new Element( "chartUrl" ).addContent( new CDATA( chartUrl() ) ) );
			root.addContent( new Element( "start" ).addContent( new CDATA( startDate ) ) );
			root.addContent( new Element( "end" ).addContent( new CDATA( endDate ) ) );
			root.addContent( new Element( "crush10" ).addContent( new CDATA( "" + crush ) ) );
			root.addContent( new Element( "crush20" ).addContent( new CDATA( "" + crush ) ) );
			root.addContent( new Element( "crush30" ).addContent( new CDATA( "" + crush ) ) );
			root.addContent( new Element( "volume" ).addContent( new CDATA( volumeSeries ) ) );

			BarchartQuote quote = downloadDailyQuote( ticker );
			root.addContent( new Element( "symname" ).addContent( quote.symname ) );
			root.addContent( new Element( "last" ).addContent( quote.last ) );
			root.addContent( new Element( "up" ).addContent( quote.up ) );
			root.addContent( new Element( "down" ).addContent( quote.down ) );
			root.addContent( new Element( "grey" ).addContent( quote.grey ) );
			root.addContent( new Element( "dailyQuoteHtm" ).addContent( quote.dailyHtm ) );
			*/
			
			//Log.infoln( "CrushRecord.getElement " + ticker );

			return root;
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    
	public String chartUrl()
	{
		return "http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + getTicker();
	}

    public String json() throws RepresentationException
    {
    	try
    	{
    		/*
    		{
    		    "title": {
    		        "useHTML": true,
    		        "text": "<span id='populate{$cid}'>ACE.MI</span>",
    		        "style": { "color": "#ffff00", "cursor": "hand" }
    		    },
    		    "series": [
    		        {
    		            "name": "crush", "data": [0,0,0.31]
    		        }
    		    ]
    		}
    		*/
    		
    		String json = "{\"title\": { " + 
    					"\"useHTML\": true," + 
    					"\"text\": \"<span id='populate{$cid}'>" + ticker + "</span>\"," + 
    					"\"style\": { \"color\": \"#" + Asset.getCherryColor(cherry) + "\", \"cursor\": \"hand\" }" +                 
    				"}," +        
    				"\"series\": [" +    				
        				"{\"name\": \"crush\"," + 
        				"\"data\": [" + crushSeries + "]}," + 
        				
        				"{\"name\": \"close\"," + 
        				"\"data\": [" + closeSeries + "]}," + 
        				
        				"{\"name\": \"flow\"," + 
        				"\"data\": [" + flowSeries + "]}," +       
        				
        				"{\"name\": \"plate\"," + 
        				"\"data\": [" + plateSeries + "]}," +
        				
        				"{\"name\": \"pear\"," + 
        				"\"data\": [" + pearSeries + "]}" +        				
        			"]}";
					
			Log.infoln( "CrushRecord.getJson " + ticker );

			return json;
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    
    private String targetPrice = null;
	public String getTargetPrice() {
		return targetPrice;
	}
	public void setTargetPrice(String targetPrice) {
		this.targetPrice = targetPrice;
	}
	
	private String strategy = null;
	public String getStrategy() {
		return strategy;
	}
	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}
	
	private String exit = null;
	public String getExit() {
		return exit;
	}
	public void setExit(String exit) {
		this.exit = exit;
	}
	
	private String pal = null;
	public String getPal() {
		return pal;
	}
	public void setPal(String pal) {
		this.pal = pal;
	}

	private String exit5 = null;
	public String getExit5() {
		return exit5;
	}
	public void setExit5(String exit5) {
		this.exit5 = exit5;
	}

	private String exit30 = null;
	public String getExit30() {
		return exit30;
	}
	public void setExit30(String exit30) {
		this.exit30 = exit30;
	}

	private String pal5 = null;
	public String getPal5() {
		return pal5;
	}
	public void setPal5(String pal5) {
		this.pal5 = pal5;
	}

	private String pal30 = null;
	public String getPal30() {
		return pal30;
	}
	public void setPal30(String pal30) {
		this.pal30 = pal30;
	}

	private String portfolio = null;
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	
	private String comment = null;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	} 
	
	private String watched = null;
	public String getWatched() {
		return watched;
	}
	public void setWatched(String watched) {
		this.watched = watched;
	}

	private String amount = null;
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	private String commission = null;
	public String getCommission() {
		return commission;
	}
	public void setCommission(String commission) {
		this.commission = commission;
	}
	
	private Long tradeTime = null;
	public Long getTradeTime() {
		if ( tradeTime == null ) {
			tradeTime = getLastModified();
		}
		return tradeTime;
	}
	public void setTradeTime(Long tradeTime) {
		this.tradeTime = tradeTime;
	}
	
	private String entry = null;
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
	
	private String instrument = null;
	public String getInstrument() {
		if ( instrument == null ) {
			instrument = getTicker();
		}
		return instrument;
	}
	public void setInstrument(String instrument) {
		this.instrument = instrument;
	}
}
