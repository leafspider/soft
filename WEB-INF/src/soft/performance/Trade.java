package soft.performance;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jdom2.CDATA;
import org.jdom2.Element;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;


public class Trade extends DatabaseRecord implements Comparable<Trade>
{		
	public static void main ( String[] args )
	{
		try
		{				
			Trade trade = new Trade();
			trade.setInstrument("stock12");
			trade.setEntryPrice(123);
			trade.setExitPrice(234);
			boolean saved = trade.saveOrUpdateAndCommit();
//			Log.infoln( "10000 projectedReturn=" + stock.projectedReturn(10000) );
			
			/*
			Iterator stocks = trade.getDatabaseManager().listRecords("Trade").iterator();
			while(stocks.hasNext())
			{
				Trade stock1 = (Trade) stocks.next();
				Log.infoln( "" + stock1.getTicker() );
			}
			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	private String projectName = "trading";	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName()
	{ 
		return projectName;
	}
	
	private String instrument = null;
	public String getInstrument() {
		return instrument;
	}
	public void setInstrument(String tick) {
		this.instrument = tick;
	}

	private double entryPrice = 0.d;
	public double getEntryPrice() {
		return entryPrice;
	}
	public void setEntryPrice(double price) {
		this.entryPrice = price;
	}

	private double exitPrice = 0.d;
	public double getExitPrice() {
		return exitPrice;
	}
	public void setExitPrice(double target) {
		this.exitPrice = target;
	}

	private Date entryDate = null;
	public Date getEntryDate() {
		return entryDate;
	}
	public void setEntryDate(Date entryTime) {
		this.entryDate = entryTime;
	}
	
	private Date exitDate = null;
	public Date getExitDate() {
		return exitDate;
	}
	public void setExitDate(Date exitDate) {
		this.exitDate = exitDate;
	}

	/*
	private double confidence = 0.d;
	public double getConfidence() {
		return confidence;
	}
	public void setConfidence(double confidence) {
		this.confidence = confidence;
	}
	
	private String bias = null;
	public String getBias() {
		return bias;
	}
	public void setBias(String bias) {
		this.bias = bias;
	}
	
	private String comment = null;
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	private double crush = 0.0d;
	public double getCrush() {
		return crush;
	}
	public void setCrush(double crush) {
		this.crush = crush;
	}
	
	private double cherry = 0.0d;
	public double getCherry() {
		return cherry;
	}
	public void setCherry(double cherry) {
		this.cherry = cherry;
	}
	
	private double pear = 0.0d;
	public double getPear() {
		return pear;
	}
	public void setPear(double pear) {
		this.pear = pear;
	}
	
	private int projectedMovePercent = 0;
	public int getProjectedMovePercent() {
		return projectedMovePercent;
	}
	public void setProjectedMovePercent(int projectedMovePercent) {
		this.projectedMovePercent = projectedMovePercent;
	}
	
	private int projectedDurationDays = 0;
	public int getProjectedDurationDays() {
		return projectedDurationDays;
	}
	public void setProjectedDurationDays(int projectedDurationDays) {
		this.projectedDurationDays = projectedDurationDays;
	}
	*/
	
	/*
	public double getReturnPercent() 
	{
		double profit = 0.0d;
//		if( entryPrice > 0.0d && exitPrice > 0.0d )
		if( entryPrice > 0.0d )
		{
			profit = 100 * (exitPrice-entryPrice)/entryPrice;
//			profit = 100 * (exitPrice/entryPrice); 
		}
		return profit;
	}
	*/

	private String duration = null;
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	
	public Element listElement()
	{
		Element elem = new Element( "trade" );
		elem.addContent( new Element( "instrument" ).addContent( new CDATA( "" + getInstrument() ) ) );
		elem.addContent( new Element( "entryPrice" ).addContent( new CDATA( "" + getEntryPrice() ) ) );
		if( getEntryDate() != null) { elem.addContent( new Element( "entryDate" ).addContent( new CDATA( "" + format.format(getEntryDate()) ) ) ); }
		elem.addContent( new Element( "duration" ).addContent( new CDATA( "" + getDuration() ) ) );
		elem.addContent( new Element( "exitPrice" ).addContent( new CDATA( "" + getExitPrice() ) ) );
		if( getExitDate() != null) { elem.addContent( new Element( "exitDate" ).addContent( new CDATA( "" + format.format(getExitDate()) ) ) ); }
		elem.addContent( new Element( "percentageReturn" ).addContent( new CDATA( "" + getPercentageReturn() ) ) );
		elem.addContent( new Element( "shares" ).addContent( new CDATA( "" + getShares() ) ) );
		elem.addContent( new Element( "return" ).addContent( new CDATA( "" + getReturnVal() ) ) );
		return elem;
	}

	public static DateFormat format = new SimpleDateFormat( "MM/dd/yyyy" );
	
	public int compareTo(Trade trade)
	{
		int comp = getInstrument().compareTo(trade.getInstrument()); 
//		System.out.println("" + comp + " " + getInstrument() + "=" + trade.getInstrument());		
		if( comp != 0 ) { return comp; }

//		System.out.println(getEntryDate());
//		System.out.println(trade.getEntryDate());

		if( getEntryDate() == null ) { return 1; }
		if( trade.getEntryDate() == null ) { return -1; }
		comp = getEntryDate().compareTo(trade.getEntryDate()); 
		if( comp != 0 ) { return comp; }

		if( getExitDate() == null ) { return 1; }
		if( trade.getExitDate() == null ) { return -1; }
		comp = getExitDate().compareTo(trade.getExitDate());
		
		return comp;
	}
	
	private double percentageReturn = 0.d;
	public double getPercentageReturn() {
		return percentageReturn;
	}
	public void setPercentageReturn(double parlayReturn1) {
		this.percentageReturn = parlayReturn1;
	}
	
	private int shares = 0;
	public int getShares() {
		return shares;
	}
	public void setShares(int shares) {
		this.shares = shares;
	}
	
	private double returnVal = 0.0d;
	public double getReturnVal() {
		return returnVal;
	}
	public void setReturnVal(double returnVal) {
		this.returnVal = returnVal;
	}
}
