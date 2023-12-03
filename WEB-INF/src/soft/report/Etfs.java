package soft.report;

import leafspider.util.*;
import org.jdom2.Element;
import soft.asset.Asset;
import soft.batch.BatchAgent;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

//import org.jsoup.nodes.Element;

public class Etfs extends BarchartReport
{
	public static void main ( String[] args )
	{
		try
		{
			Etfs page = new Etfs();
//			Log.debug = true;
//			page.setUrl("https://www.nasdaq.com/api/v1/historical/SPY/etf/2019-11-09/2019-12-09");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\tmp\\etfs.xml");

			XmlJdomWriter jdomWriter = new XmlJdomWriter();	
			out.print(jdomWriter.writeToString( page.getRoot() ));
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
		
	@Override
	public String getProjectName() 
	{
		return "viper";
	}	
    
	public void populate() throws Exception 
	{	
		try
		{	
			Log.infoln("Starting ViperReport");
			
	   		File propsFolder = Folders.etfsConfigFolder();
	   		File[] propsFiles = propsFolder.listFiles(new FilenameFilter() {
	   			public boolean accept(File dir, String name) {
	   		        return name.toLowerCase().endsWith(".properties");
	   		    }
	   		});

			root = new Element( "reports" );
			
			DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );				
			Calendar end = new GregorianCalendar();			
			Calendar start = new GregorianCalendar();
			start.add( Calendar.DATE, -10);		

			for( int i=0; i<propsFiles.length; i++) {
				OrderedProperties props = new OrderedProperties();
				props.load( new FileInputStream( propsFiles[i] ) );

				Element report = new Element( "report" );

				Enumeration keys = props.propertyNames();
				while( keys.hasMoreElements() ) {

					String key = (String) keys.nextElement();
					
					if( key.equals( "id" ) || key.equals( "title" )) {
						report.setAttribute( key, props.getProperty( key ) );
					}
					else if( key.equals( "tickerlist" ) ) { }
					else {					
						
						String ticker = key;

						Element asset = new Element( "asset" );

						asset.setAttribute("ticker", ticker);
						asset.setAttribute("name", props.getProperty( key ) );
						
//						System.out.println( "" );
//						System.out.println( "" + props.getProperty( key ) + " (" + key + ")" );
//						System.out.println( "Date Open High Low Close Adjusted_close Volume" );	
//						System.out.println( "Date Open High Low Close Change %Chg Volume");
//						Log.infoln("start="+start + " end=" + end);

						url = "https://www.nasdaq.com/api/v1/historical/" + ticker + "/etf/2019-11-09/2019-12-09";
						File folder = Folders.etfsFolder();
						File file = Downloader.downloadFile( url, folder );		// jmh 2017-06-27

						/*
//						ArrayList prices = (ArrayList) Asset.instance( ticker ).loadPrices(start, end);
						Collections.reverse((List<?>) prices);

                        for (int j=0; j<5; j++ ) {

                        	try {
								String[] cols = (String[]) prices.get(j);
								String[] prevCols = (String[]) prices.get(j + 1); // Rows are date descending

								Double close = Double.valueOf(cols[4]);
								Double prevClose = Double.valueOf(prevCols[4]);
								Double diff = close - prevClose;
								Double pcChange = 100 * (diff / prevClose);

								cols[5] = "" + diff.floatValue();

								Element row = new Element("row");
								for (int k = 0; k < cols.length; k++) {

									Element col = new Element("col");
									col.setText("" + cols[k]);
									row.addContent(col);
									//								System.out.print( "" + cols[k] + " " );
									if (k == 5) {
										col = new Element("col");
										col.setText("" + pcChange.floatValue());
										row.addContent(col);
										//									System.out.print( pcChange.floatValue() + " " );
									}
								}
								asset.addContent(row);
								//							System.out.println( "" );
							}
                        	catch ( Exception ioobe ) {
                        		Log.infoln( "VIPER IndexOutOfBoundsException: ticker=" + ticker);
							}
						}
						*/
						
						report.addContent(asset);
					}
				}		
				
				root.addContent(report);
	   		}
			
			Log.infoln("Finished Etfs");
			
	   		File folder = new File( Folders.heatFolder().getAbsolutePath() + "\\" + getProjectName() );
	   		folder.mkdirs();
	   		Timestamp.writeTimestamp(folder);
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private class PickComparator implements Comparator {

		@Override
		public int compare(Object arg0, Object arg1) {
			Element el0 = ((Element) arg0);
			Element el1 = ((Element) arg1);
			int pc0 = Integer.parseInt( el0.getAttributeValue("percent") );
			int pc1 = Integer.parseInt( el1.getAttributeValue("percent") );
			if( pc0 < pc1 )  { return 1; }
			if( pc0 > pc1 )  { return -1; }
			return 0;
		}
	
	}

	public static String getPercentageColor(double val) 
	{
		val *= 0.00042;
		
		if ( val > 0.42 ) { return "ff0000"; }
		else if ( val > 0.41 ) { return "ff2000"; }
		else if ( val > 0.40 ) { return "ff4000"; }
		else if ( val > 0.39 ) { return "ff6000"; }
		else if ( val > 0.38 ) { return "ff8000"; }
		else if ( val > 0.36 ) { return "ffa000"; }
		else if ( val > 0.34 ) { return "ffc000"; }
		else if ( val > 0.32 ) { return "ffe000"; }
		else if ( val > 0.30 ) { return "fff000"; }
		else if ( val > 0.28 ) { return "ffff00"; }
		else if ( val > 0.26 ) { return "ffff20"; }
		else if ( val > 0.24 ) { return "ffff40"; }
		else if ( val > 0.22 ) { return "ffff60"; }
		else if ( val > 0.20 ) { return "ffff80"; }
		else if ( val > 0.18 ) { return "ffffa0"; }
		else if ( val > 0.16 ) { return "ffffc0"; }
		else if ( val > 0.14 ) { return "ffffe0"; }
		else if ( val > 0.12 ) { return "fffff0"; }
		else if ( val > 0.10 ) { return "00ffff"; }
		else if ( val > 0.08 ) { return "00ccff"; }		
		else if ( val > 0.06 ) { return "0099ff"; }		
		else if ( val > 0.04 ) { return "0066ff"; }
		else if ( val > 0.02 ) { return "0033ff"; }
		return "0000ff";
	}
	
	protected Element root = null;
	public Element getRoot() {
		return root;
	}
	
	class OrderedProperties extends Properties {

	    public OrderedProperties() {
	        super ();

	        _names = new Vector();
	    }

	    public Enumeration propertyNames() {
	        return _names.elements();
	    }

	    public Object put(Object key, Object value) {
	        if (_names.contains(key)) {
	            _names.remove(key);
	        }

	        _names.add(key);

	        return super .put(key, value);
	    }

	    public Object remove(Object key) {
	        _names.remove(key);

	        return super .remove(key);
	    }

	    private Vector _names;

	}
}
