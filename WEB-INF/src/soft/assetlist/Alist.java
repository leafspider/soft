package soft.assetlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.TreeSet;

import soft.asset.Asset;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;

import leafspider.db.DatabaseManager;
import leafspider.util.Downloader;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public abstract class Alist 
{
    public static void main(String[] args)
	{
    	try
    	{
//    		Alist alist = new AlistFixed();
//    		alist.setId("List_number_1");
    		
//    		/*
//	    	Alist alist = AlistVariable.instance("http://biz.yahoo.com/research/earncal/today.html");
//	    	Alist alist = AlistVariable.instance("http://old.barchart.com/stocks/percentdecline.php");
//	    	Alist alist = AlistVariable.instance("insider_0");
//	    	Alist alist = AlistVariable.instance("finviz");
//    		alist.url = "http://old.barchart.com/etf/monitor.php";
//		    */
	    	Alist alist = Alist.instance("earnings_fri", null, null, null);
    		
    		alist.populate();
		    
	    	System.out.println( "title=" + alist.title);
	    	System.out.println( "id=" + alist.id);
	    	
	    	Iterator list = alist.tickers.iterator();
	    	while( list.hasNext())
	    	{
	    		System.out.println( "ticker=" + list.next());
	    	}
    	}
    	catch( Exception e)
    	{
    		e.printStackTrace();
    	}
	}
    	
	public abstract void populate() throws Exception;
	
	public void init() {}

	public void initDatabase( DatabaseManager dbm ) throws Exception {}
	
	public String id = null;
    public String getId() 
    {
		if(id == null) { setId(Util.getCleanFileName(title.toLowerCase())); }
		return id;
	}
	public void setId(String id)
    {
    	this.id = id;
    }
	
	private String title = null;
	public String getTitle() 
	{
		if(title == null)
		{
			setTitle(id.toUpperCase()); 
		}
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	private String hierarchy = null;
	public String getHierarchy() {
		return hierarchy;
	}
	public void setHierarchy(String hierarchy) {
		this.hierarchy = hierarchy;
	}

	public List<String> tickers = null;
	public List<String> getTickers() {
		return tickers;
	}
	public void setTickers(List<String> tickers) {
		this.tickers = tickers;
	}

	public String tickerList = null;
	public String getTickerList() {
		return tickerList;
	}
	public void setTickerList(String tickerList) {
		this.tickerList = tickerList;
	}

	public String url = null;
	public String getUrl() {	
		if( url == null ) 
		{
			try
			{
				url = getProperty("url"); 
			}
			catch( Exception e ) {}
		}
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public abstract String getListFolder();
	
    protected File propertiesFile = null;
	public File getPropertiesFile() 
	{
		if( propertiesFile == null )
		{
			propertiesFile = new File( ServerContext.getListsFolder().getAbsolutePath() + "\\" + getListFolder() + "\\" + getId() + ".properties" );
		}
		/*
		if( !propertiesFile.exists() )
		{
			Util.writeAsFile(getPropertiesText(), propertiesFile.getAbsolutePath());
		}
		*/
		return propertiesFile;        
	}

	public File writePropertiesFile() 
	{
		Util.writeAsFile(getPropertiesText(), getPropertiesFile().getAbsolutePath());
		return propertiesFile;        
	}
	
	public Properties getProperties() throws Exception
	{
		Properties props = new Properties();
		props.load( new FileInputStream( getPropertiesFile() ) );
    	return props;
	}

	public String getProperty( String name ) throws Exception
	{
		return getProperties().getProperty( name );
	}
	
    public String getPropertiesText()
    {
    	String txt = "id=" + getId() + "\r\n" + 
    				"title=" + getTitle() + "\r\n" +
					"hierarchy=" + getHierarchy() + "\r\n" +
					"tickerlist=" + getTickerList() + "\r\n";
    	return txt;
    }

    public static Alist instance( String listId, Calendar end, String title, String ticks ) throws Exception
    {
		Alist alist = null;
		if ( listId.equals( "new" ) ) {

			//Log.infoln( "ticks=" + ticks );
    		//alist = new AlistFixed();
			//alist.setTitle(title);
			//alist.setTickerList(ticks);

			//alist = AlistBatch.instance(title, ticks);
    		//alist.writePropertiesFile();
			Log.infoln( "New Alist id=" + alist.getId() );
    	}		
    	if ( alist == null ) 
    	{ 
    		alist = AlistBatch.instance(listId); 
    	}		  	
		if ( alist == null )
		{ 
			alist = AlistVariable.instance(listId);
		}  
    	if ( alist == null ) 
    	{ 
    		alist = AlistFixed.instance(listId); 
    	}

//		Log.infoln("alist.url=" + alist.url);
		if( alist != null )
		{
			Properties prop = alist.getProperties();
			alist.setTitle( prop.getProperty("title") );
			if(alist.getTitle() == null) { alist.setTitle( alist.getId()); }				
			if(alist.url != null ) 
			{ 
				alist.file = Downloader.downloadFile( alist.url, Asset.croutFolder( end ));		// for AlistBarcharts
			}	
			/*
			else		// if ( alist.getProperties().getProperty("tickerlist") != null )
			{
				alist = null;		//.setTickerList(alist.getProperties().getProperty("tickerlist"));
			}
			*/
		}
		
    	if( alist != null ) { alist.populate(); }	
    	return alist;
    }
    
	public File doCrush( Calendar start, Calendar end ) throws Exception
	{
		String[] tickerList = getTickers().toArray(new String[] {});
		if ( tickerList.length >  1 )
		{
			Asset.debug = false;
		}
	
	//	PrintStream stream = Util.getPrintStream( Asset.crushFolder().getAbsolutePath() + "\\CrushMap " + crushDate( end ) + ".htm" );
		File mapFile = croutMenu( end );
		PrintStream stream = Util.getPrintStream( mapFile.getAbsolutePath() );
		
		stream.println( "<html>" );
		
		stream.println( "<head>" );		
		stream.println( "<title>CrushMap</title>" );
		stream.println( "<link rel='icon' href='/soft/skins/resource/leafspider.png'>" );
		stream.println( "<link href='/soft/skins/resource/crush.css' rel='stylesheet' type='text/css'>" );		
		stream.println( "</head>" );
		
		stream.println( "<body>" );
		stream.println( "<table style='font-family:arial;border-spacing:20px 1px;'>" );
		stream.println( "<tr>" );
		stream.println( "	<td colspan='5' align='center' style='font-size:1.2em'>" + 
				"<b>CrushMap on " +
				getTitle() + " (" + 
				start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + start.get(Calendar.DAY_OF_MONTH) + 
				" to " + 
				end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH)+1) + "-" + end.get(Calendar.DAY_OF_MONTH) + 
				")</b></td>" );
		stream.println( "</tr>" );
		
		TreeSet assets = new TreeSet();
		for( int i = 0; i < tickerList.length; i++ ) 
		{ 
			String ticker = tickerList[i];			
			Asset asset = Asset.instance( ticker );	
			try
			{
				if ( asset.doCrush( start, end ) )
				{
					assets.add( asset );
				}
			}
			catch( Exception e )
			{
	
			}
		}
		
		stream.println( "<tr>" );
	
		NumberFormat decimalFormat = new DecimalFormat( "#0.00" );
	
		double rows = 35;
		int rowCount = 0;
		Iterator iter = assets.iterator();		
		while( iter.hasNext() ) 
		{
			Asset asset = (Asset) iter.next();
			if ( asset.getTicker() != null )
			{
				if ( asset.getTicker().equals( "BAC" ) )
				{
					String bob = "bob";		// DEBUG
				}
						
	//			stream.println( "	<td align='right'>" + formatter.format( key.doubleValue() ) + "</td><td><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td>" );
				double rint = Math.IEEEremainder( rowCount, rows );
	//			System.out.println( "" + rint );
				boolean isNewCol = ( rint == 0.0 );
				boolean isEndofCol = ( rint == -1.0 );
//				Log.infoln( "asset.channelWidth=" + asset.channelWidth );
				if ( isNewCol )
				{ 
					stream.println( "	<td align='right' valign='top' style='padding:10px;'>" ); 
					stream.println( "		<table style='border-spacing:5px 1px;'>" ); 
					stream.println( "			<tr style='text-align:center;'>" );
					stream.println( "				<td></td><td><i>10</i></td><td><i>20</i></td><td><i>30</i></td><td></td>" );
					stream.println( "			</tr>" );
				}
	//			stream.println( decimalFormat.format( key.doubleValue() ) + " <a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></br>" );
	//			stream.println( "<tr><td align='right' valign='top'>" + decimalFormat.format( key.doubleValue() ) + "</td><td align='left' valign='top'><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td></tr>" );
				stream.println( "<tr>" +
									"<td>" +
										"<a style='background:" + asset.getCherryColor() + "' title='" + asset.cherryWidth + "' href='/soft/asset/jake/" + asset.getTicker() + "/crush.htm' target='_blank'><b>" + asset.getTicker() + "</b></a> " +
									"</td>" +
									"<td>" + decimalFormat.format( asset.crushes[0] ) + "</td>" +
									"<td>" + decimalFormat.format( asset.crushes[1] ) + "</td>" +
									"<td>" + decimalFormat.format( asset.crushes[2] ) + "</td>" +
									"<td>" +
										"<nobr>" + 
										"<a href='" + asset.getCroutFile().getName() + "' target='_blank'>csv</a> " +
										"<a href='" + asset.chartUrl() + "' target='_blank'>chart</a>" +
										"</nobr>" +
									"</td>" +
								"</tr>" );
				if ( isEndofCol ) 
				{ 
					stream.println( "		</table>" ); 
					stream.println( "	</td>" ); 
				}
				rowCount++;
			}
		}
	
		stream.println( "</tr>" );		
		stream.println( "</table>" );		
		stream.println( "</body></html>" );
	
		if ( tickerList.length >  1 )
		{
			System.out.println( "Done" );
		}
		
		return mapFile;
	}
    
	public File obdoCrush( Calendar start, Calendar end ) throws Exception
	{
		String[] tickerList = getTickers().toArray(new String[] {});
		if ( tickerList.length >  1 )
		{
			Asset.debug = false;
		}
	
	//	PrintStream stream = Util.getPrintStream( Asset.crushFolder().getAbsolutePath() + "\\CrushMap " + crushDate( end ) + ".htm" );
		File mapFile = croutMenu( end );
		PrintStream stream = Util.getPrintStream( mapFile.getAbsolutePath() );
		
		stream.println( "<html><body>" );
		stream.println( "<table style='font-family:arial;border-spacing:20px 1px;'>" );
		stream.println( "<tr>" );
		stream.println( "	<td colspan='5' align='center' style='font-size:1.2em'>" + 
				"<b>CrushMap on " +
				getTitle() + " (" + 
				start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + start.get(Calendar.DAY_OF_MONTH) + 
				" to " + 
				end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH)+1) + "-" + end.get(Calendar.DAY_OF_MONTH) + 
				")</b></td>" );
		stream.println( "</tr>" );
		
		TreeSet assets = new TreeSet();
		for( int i = 0; i < tickerList.length; i++ ) 
		{ 
			String ticker = tickerList[i];			
			Asset asset = Asset.instance( ticker );	
			try
			{
				if ( asset.doCrush( start, end ) )
				{
					assets.add( asset );
				}
			}
			catch( Exception e )
			{
	
			}
		}
		
		stream.println( "<tr>" );
	
		NumberFormat decimalFormat = new DecimalFormat( "#0.00" );
	
		double rows = 35;
		int rowCount = 0;
		Iterator iter = assets.iterator();		
		while( iter.hasNext() ) 
		{
			Asset asset = (Asset) iter.next();
			if ( asset.getTicker() != null )
			{
				if ( asset.getTicker().equals( "BAC" ) )
				{
					String bob = "bob";		// DEBUG
				}
						
	//			stream.println( "	<td align='right'>" + formatter.format( key.doubleValue() ) + "</td><td><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td>" );
				double rint = Math.IEEEremainder( rowCount, rows );
	//			System.out.println( "" + rint );
				boolean isNewCol = ( rint == 0.0 );
				boolean isEndofCol = ( rint == -1.0 );
//				Log.infoln( "asset.channelWidth=" + asset.channelWidth );
				if ( isNewCol )
				{ 
					stream.println( "	<td align='right' valign='top'>" ); 
					stream.println( "		<table style='border-spacing:5px 1px;'>" ); 
					stream.println( "			<tr style='text-align:center;'>" );
					stream.println( "				<td></td><td><i>10</i></td><td><i>20</i></td><td><i>30</i></td><td></td>" );
					stream.println( "			</tr>" );
				}
	//			stream.println( decimalFormat.format( key.doubleValue() ) + " <a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></br>" );
	//			stream.println( "<tr><td align='right' valign='top'>" + decimalFormat.format( key.doubleValue() ) + "</td><td align='left' valign='top'><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td></tr>" );
				stream.println( "<tr>" +
									"<td align='left' valign='top'>" +
										"<a style='border-radius:5px;background:" + asset.getCherryColor() + "' title='" + asset.cherryWidth + "' href='/soft/asset/jake/" + asset.getTicker() + "/crush.htm' target='_blank'><b>" + asset.getTicker() + "</b></a> " +
									"</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[0] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[1] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[2] ) + "</td>" +
									"<td align='left' valign='top'>" +
										"<nobr>" + 
										"<a href='" + asset.getCroutFile().getName() + "' target='_blank'>csv</a> " +
										"<a href='" + asset.chartUrl() + "' target='_blank'>chart</a>" +
										"</nobr>" +
									"</td>" +
								"</tr>" );
				if ( isEndofCol ) 
				{ 
					stream.println( "		</table>" ); 
					stream.println( "	</td>" ); 
				}
				rowCount++;
			}
		}
	
		stream.println( "</tr>" );		
		stream.println( "</table>" );		
		stream.println( "</body></html>" );
	
		if ( tickerList.length >  1 )
		{
			System.out.println( "Done" );
		}
		
		return mapFile;
	}

	public File croutMenu( Calendar end )
	{
		return new File( Asset.croutFolder( end ).getAbsolutePath() + "\\" + getId() + ".htm" );
	}

	public File file = null;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	}

	/*
	public File doBatchCrush( String id, Calendar start, Calendar end ) throws Exception
	{
		DatabaseManager dbm = CrusherProject.getDatabaseManager( id );
		
		String[] tickerList = getTickers().toArray(new String[] {});
		if ( tickerList.length >  1 ) { Asset.debug = false; }
	
	//	PrintStream stream = Util.getPrintStream( Asset.crushFolder().getAbsolutePath() + "\\CrushMap " + crushDate( end ) + ".htm" );
		File mapFile = croutMenu( end );
		Log.infoln( "mapFile=" + mapFile.getAbsolutePath() );
		PrintStream stream = Util.getPrintStream( mapFile.getAbsolutePath() );
		
		stream.println( "<html><body>" );
		stream.println( "<table style='font-family:arial;border-spacing:20px 1px;'>" );
		stream.println( "<tr>" );
		stream.println( "	<td colspan='5' align='center' style='font-size:1.2em'>" + 
				"<b>CrushMap on " +
				getTitle() + " (" + 
				start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + start.get(Calendar.DAY_OF_MONTH) + 
				" to " + 
				end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH)+1) + "-" + end.get(Calendar.DAY_OF_MONTH) + 
				")</b></td>" );
		stream.println( "</tr>" );

		String fmdStart = Asset.defaultDateFormat.format(start.getTime());
		String fmdEnd = Asset.defaultDateFormat.format(end.getTime());
		
		TreeSet assets = new TreeSet();
		ArrayList records = new ArrayList();
		for( int i = 0; i < tickerList.length; i++ ) 
		{ 
			String ticker = tickerList[i];		
			Log.infoln( "ticker=" + ticker);
			
			Asset asset = Asset.instance( ticker );	
			try
			{
				if ( asset.doCrush( start, end ) ) 
				{ 
					assets.add( asset ); 
				
	//				String select = "from CrushRecord where ticker='" + ticker + "' and startDate='" + fmdStart + "' and endDate='" + fmdEnd + "'";				
					String select = "from CrushRecord where ticker='" + ticker + "'";
					
					List<CrushRecord> list = dbm.selectRecords( select );
//					dbm.reportRecords(list);
	
					CrushRecord record = null;
					if( list == null || list.size() < 1 ) { record = new CrushRecord(); }
					else if( list.size() > 1 ) { throw new Exception("Duplicate CrushRecord ticker=" + ticker); }
					else { record = list.get(0); }
					
					record.setDatabaseManager(dbm);
					record.setTicker( ticker );
					record.setStartDate( fmdStart );
					record.setEndDate( fmdEnd );
					record.setClose( asset.nClose[asset.prices.size()-1] );
					record.setFlow( asset.nVol[asset.prices.size()-1] );
					record.setPeel( asset.nsdLong[asset.prices.size()-1] );
					record.setJam( asset.nsdMedium[asset.prices.size()-1] );
					record.setPress( asset.nsdShort[asset.prices.size()-1] );
					record.setCrush( asset.crushSeries[asset.prices.size()-1] );				
					record.setPlate( asset.plate[asset.prices.size()-1] );
					record.setPear( asset.crushGradient[asset.crushGradient.length-1] );
		//			Log.infoln( "pear=" + record.getPear());
					record.setCherry( asset.cherryWidth );
		//			record.setDailyQuoteHtm( downloadDailyQuoteHtm( ticker ) );
										
//					record.saveOrUpdateAndCommit();			
//					Log.infoln( "saved " + record.getTicker() + "=" + saved);

					records.add(record);
				}
			}
			catch( Exception e ) 
			{
				Log.infoln(e);
			}
		}
		
		dbm.saveOrUpdateAndCommitBatch(records);
		
		stream.println( "<tr>" );
	
		NumberFormat decimalFormat = new DecimalFormat( "#0.00" );
	
		double rows = 35;
		int rowCount = 0;
		Iterator iter = assets.iterator();		
		while( iter.hasNext() ) 
		{
			Asset asset = (Asset) iter.next();
			if ( asset.getTicker() != null )
			{
				if ( asset.getTicker().equals( "BAC" ) )
				{
					String bob = "bob";		// DEBUG
				}
						
	//			stream.println( "	<td align='right'>" + formatter.format( key.doubleValue() ) + "</td><td><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td>" );
				double rint = Math.IEEEremainder( rowCount, rows );
	//			System.out.println( "" + rint );
				boolean isNewCol = ( rint == 0.0 );
				boolean isEndofCol = ( rint == -1.0 );
//				Log.infoln( "asset.channelWidth=" + asset.channelWidth );
				if ( isNewCol )
				{ 
					stream.println( "	<td align='right' valign='top'>" ); 
					stream.println( "		<table style='border-spacing:5px 1px;'>" ); 
					stream.println( "			<tr style='text-align:center;'>" );
					stream.println( "				<td></td><td><i>10</i></td><td><i>20</i></td><td><i>30</i></td><td></td>" );
					stream.println( "			</tr>" );
				}
	//			stream.println( decimalFormat.format( key.doubleValue() ) + " <a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></br>" );
	//			stream.println( "<tr><td align='right' valign='top'>" + decimalFormat.format( key.doubleValue() ) + "</td><td align='left' valign='top'><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td></tr>" );
				stream.println( "<tr>" +
									"<td align='left' valign='top'>" +
										"<a style='border-radius:5px;background:" + asset.getCherryColor() + "' title='" + asset.cherryWidth + "' href='/soft/asset/jake/" + asset.getTicker() + "/crush.htm' target='_blank'><b>" + asset.getTicker() + "</b></a> " +
									"</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[0] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[1] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[2] ) + "</td>" +
									"<td align='left' valign='top'>" +
										"<nobr>" + 
										"<a href='" + asset.getCroutFile().getName() + "' target='_blank'>csv</a> " +
										"<a href='" + asset.chartUrl() + "' target='_blank'>chart</a>" +
										"</nobr>" +
									"</td>" +
								"</tr>" );
				if ( isEndofCol ) 
				{ 
					stream.println( "		</table>" ); 
					stream.println( "	</td>" ); 
				}
				rowCount++;
			}
		}
	
		stream.println( "</tr>" );		
		stream.println( "</table>" );		
		stream.println( "</body></html>" );
	
		if ( tickerList.length >  1 )
		{
			System.out.println( "Done" );
		}
		
		return mapFile;
	}
	*/

}
