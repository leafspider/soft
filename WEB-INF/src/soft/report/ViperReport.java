package soft.report;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import leafspider.db.DatabaseManager;
import leafspider.util.*;
//import org.jsoup.nodes.Element;
import org.jdom2.Element;

import soft.asset.Asset;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;

public class ViperReport extends BarchartReport
{
	public static void main ( String[] args )
	{
		try
		{
			ViperReport page = new ViperReport();
			page.setResourceId( "MMIT" );
//			Log.debug = true;
//			page.setUrl("http://old.barchart.com/commodityfutures/All");
//			page.setUrl("https://www.barchart.com/etfs-funds/etf-monitor");
//			page.setResourceId( "KWEB" );
//			page.getTickerList().add("KWEB");
			page.getTickerList().add("MMIT");
			page.populate();
//			Log.infoln(page.getHtml());
			
			PrintStream out = Util.getPrintStream("C:\\tmp\\viper.xml");

			XmlJdomWriter jdomWriter = new XmlJdomWriter();	
			out.print(jdomWriter.writeToString( page.getRoot() ));
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
		
	@Override
	public String getProjectName() 
	{
		return "viper";
	}

	private List<String> tickerList = new ArrayList<String>();
	public List<String> getTickerList() { return tickerList; }
	public void setTickerList(List<String> tickerList) { this.tickerList = tickerList; }
    
	public void populate() throws Exception {

		try {

			Long startTime = Util.getNow();
			
	   		File propsFolder = Folders.viperConfigFolder();
			File[] propsFiles = { new File(propsFolder.getAbsolutePath() + "\\" + getResourceId() + ".properties") };	// resourceId could be a ticker for custom list

			boolean isShowAlertsOnly = false;
			if ( getResourceId().equals("all") ) {
				propsFiles = propsFolder.listFiles(new FilenameFilter() {
					public boolean accept(File dir, String name) {
						return name.toLowerCase().endsWith(".properties");
					}
				});
				isShowAlertsOnly = true;
			}

			root = new Element( "reports" );
			
			Calendar end = new GregorianCalendar();
			Calendar start = new GregorianCalendar();
			start.add( Calendar.DATE, -365);	// Go back 15 days

			for( int i=0; i<propsFiles.length; i++) {

				Element report = new Element( "report" );

				File propsFile = propsFiles[i];
				OrderedProperties props = new OrderedProperties();

				List<CrushRecord> records = new ArrayList<CrushRecord>();
				if ( propsFile.exists() ) {		// We are doing a batch list viper

					props = new OrderedProperties();
					props.load(new FileInputStream(propsFile));

					Enumeration keys = props.propertyNames();
					while (keys.hasMoreElements()) {
						String key = (String) keys.nextElement();
						if (key.equals("id") || key.equals("title") || key.equals("hierarchy") || key.equals("lag")) {
							report.setAttribute(key, props.getProperty(key));
						}
					}

					DatabaseManager dbm = BatchProject.getDatabaseManager(props.getProperty("id"));

					String select = "from CrushRecord order by crush desc";
					records = dbm.selectRecords(select);

					doBatchListViper(records, dbm, start, end, props, report, isShowAlertsOnly);
				}
				else {	// We are doing a custom list viper
					DatabaseManager dbm = CrushProject.getDatabaseManager();		// To write asset failures
					doCustomListViper( dbm, start, end, props, report, isShowAlertsOnly);
				}

				root.addContent(report);
	   		}

//	   		File folder = new File( Folders.heatFolder().getAbsolutePath() + "\\" + getProjectName() );
//			File folder = new File( Folders.reportFolder().getAbsolutePath() + "\\" + getProjectName() + "\\" + getResourceId() );	// JMH 2021-02-01
			File folder = getOutputFolder();
			folder.mkdirs();
			Timestamp.writeTimestamp(folder);

			double duration = (Util.getNow() - startTime)/1000D;
			Log.infoln("ViperReport.doViper [" + getResourceId() + "] took " + duration + " seconds" );
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void doBatchListViper(List<CrushRecord> records, DatabaseManager dbm, Calendar start, Calendar end, OrderedProperties props, Element report, boolean isShowAlertsOnly) throws Exception {

		int numTicks = 0;

		Iterator<CrushRecord> iter = records.iterator();
		while(iter.hasNext()) {

			CrushRecord record = iter.next();
			String ticker = record.getTicker();

			try {

				int numFailures = dbm.selectRecords("from CrushFailure where ticker='" + ticker + "' and endDate='" + Asset.defaultDateFormat.format(end.getTime()) + "'").size();
				if (numFailures > 0) {
					//Log.infoln("numFailures=" + numFailures + " " + ticker);
					continue;
				}

				ArrayList<ArrayList> pricesArrayList = JsonConverter.jsonToArrayList( record.getPrices() );
				Collections.reverse((List<?>) pricesArrayList);
				if (pricesArrayList.size() < 1) {
					throw new Exception("VIPER no prices");
				}

				ArrayList<String[]> prices = new ArrayList<String[]>();
				for (int j = 0; j < 15; j++) {
					ArrayList arr = (ArrayList) pricesArrayList.get(j);
					prices.add( (String[]) arr.toArray(new String[arr.size()]) );
				}

				doViper(ticker, prices, report, props.getProperty(ticker), isShowAlertsOnly, record );
			}
			catch (Exception e) {
				handleFailure( dbm, ticker, start, end, e.getMessage() );
			}

			numTicks++;
			if (numTicks % 20 == 0) {
				//Log.infoln("VIPER sleeping" );
				Thread.sleep(10);
			}
		}
	}

	private void doCustomListViper(DatabaseManager dbm, Calendar start, Calendar end, OrderedProperties props, Element report, boolean isShowAlertsOnly) throws Exception {

		int numTicks = 0;

		Iterator<String> iter = getTickerList().iterator();
		while(iter.hasNext()) {

			String ticker = iter.next();

			try {

				int numFailures = dbm.selectRecords("from CrushFailure where ticker='" + ticker + "' and endDate='" + Asset.defaultDateFormat.format(end.getTime()) + "'").size();
				if (numFailures > 0) {
					//Log.infoln("numFailures=" + numFailures + " " + ticker);
					//continue;
				}

				ArrayList prices = (ArrayList) Asset.instance(ticker).loadPrices(start, end);
				Collections.reverse((List<?>) prices);

				if (prices.size() < 1) {
					throw new Exception("VIPER no prices");
				}

				CrushRecord record = new CrushRecord();

				doViper(ticker, prices, report, props.getProperty(ticker), isShowAlertsOnly, record );
			}
			catch (Exception e) {
				handleFailure( dbm, ticker, start, end, e.getMessage() );
			}

			numTicks++;
			if (numTicks % 20 == 0) {
				//Log.infoln("VIPER sleeping" );
				Thread.sleep(10);
			}
		}
	}

	private void doViper( String ticker, ArrayList<String[]> prices, Element report, String name, boolean isShowAlertsOnly, CrushRecord record ) {

		Element asset = new Element("asset");
		asset.setAttribute("ticker", ticker);
		if (name != null) { asset.setAttribute("name", name); }
		else { asset.setAttribute("name", ""); }

		boolean isRuleFired = false;

		for (int j = 0; j < 5; j++) {

			try {
				String[] cols = (String[]) prices.get(j);
				String[] prevCols = (String[]) prices.get(j + 1); // Rows are date descending

				Double close = Double.valueOf(cols[4]);
				Double prevClose = Double.valueOf(prevCols[4]);
				Double diff = close - prevClose;
				Double pcChange = 100 * (diff / prevClose);
				cols[5] = "" + diff.floatValue();

				Double vol = Double.valueOf(cols[6]);
				Double prevVol = Double.valueOf(prevCols[6]);
				Double volDiff = vol - prevVol;
				Double volPcChange = 0.0;
				if (Math.abs(volDiff) > 0.0) {
					if (prevVol == 0.0) {
						volPcChange = 100.0;
					} else {
						volPcChange = 100 * (volDiff / prevVol);
					}
				}

				// ----------- RULES --------------

				if (j == 0) {

					Element signals = new Element("signals");

					addRuleCrush(signals, record);
					addRulePorter(signals, record);

					addRuleA1(signals, prices, pcChange, volPcChange, "A1");
					addRuleA2(signals, pcChange, volPcChange, "A2");
					addRuleA3(signals, pcChange, volPcChange, "A3");
					addRuleA4(signals, prices, pcChange, volPcChange, "A4");

					addRuleD1(signals, prices, pcChange, "D1");

					asset.addContent(signals);
					if (signals.getChildren("rule").size() > 0) {
						isRuleFired = true;
					}
				}

				// -------- COLUMNS --------

				Element row = new Element("row");
				for (int k = 0; k < cols.length; k++) {

					Element col = new Element("col");
					if ( k == 0 || k == 6 ) {
						col.setText("" + cols[k]);
					}
					else {
						col.setText("" + String.format("%,.2f", Double.parseDouble(cols[k])) );
					}
					if (k == 6) {
						//col.setAttribute("vpcc", "" + volPcChange.floatValue());		// jmh 2021-03-15
						Element vpccCol = new Element("col");
						vpccCol.setText("" + volPcChange.floatValue());
						row.addContent(vpccCol);
					}
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
			}
			catch (Exception ioobe) {
				ioobe.printStackTrace();
//								handleFailure(dbm, ticker, start, end, e.getMessage());
				Log.infoln("VIPER IndexOutOfBoundsException: ticker=" + ticker);
				break;
			}
		}

		if (!isShowAlertsOnly || isRuleFired) {
			report.addContent(asset);
		}
	}

	private Element addRuleCrush(Element rules, CrushRecord record) {

		if ( record != null ) {
			Element signal = new Element("crush");
			double val = record.getCrush() * 100;
			long longVal = Math.round(val);
			String color = Asset.getCrushColor(record.getCrush());
			signal.setAttribute("val", "" + longVal);
			signal.setAttribute("color", color);
			rules.addContent(signal);
		}
		return rules;
	}

	private Element addRulePorter(Element rules, CrushRecord record) {

		if ( record != null ) {
			Element signal = new Element("porter");
			double val = record.getPear() * 100;
			long longVal = Math.round(val);
			String color = Asset.getPorterColor(record.getPear());
			signal.setAttribute("val", "" + longVal);
			signal.setAttribute("color", color);
			rules.addContent(signal);
		}
		return rules;
	}

	private Element addRuleA1(Element rules, ArrayList prices, Double pcChange, Double volPcChange, String text ) {

		// if day1(vol is red) and price change goes down < 2% with lower volume then day2 is green over 2% (eg S&P500, Advance auto parts)
		// Rule1: if day1.volPcChange < -1% then if day2.volPcChange < 0 and 0 > day2.pcChange > -2 then day3.pcChange is > 2

		Element signal = new Element("rule");
		String[] cols = (String[]) prices.get(1);
		String[] prevCols = (String[]) prices.get(2); // Rows are date descending

		Double vol1 = Double.valueOf(cols[6]);
		Double prevVol1 = Double.valueOf(prevCols[6]);
		Double volDiff1 = vol1 - prevVol1;
		Double volPcChange1 = 0.0;
		if (Math.abs(volDiff1) > 0.0) {
			if (prevVol1 == 0.0) { volPcChange1 = 100.0; }
			else { volPcChange1 = 100 * (volDiff1 / prevVol1); }
		}

		if ( volPcChange1 < -1.0 ) {
			if ( pcChange < 0 && pcChange > -2 && volPcChange < 0 ) {
				signal.setText( text );
				rules.addContent(signal);
			}
		}
		return rules;
	}

	private Element addRuleA2(Element rules, Double pcChange, Double volPcChange, String text ) {

		// if 100% increase in volume with < 1.5% drop in price means accumulation (>2% in price)
		// Rule2: if day1.volPcChange > 100 and 0 > day1.pricePcChange > -1.5 then day2.pcChange > 2

		Element signal = new Element("rule");
		if ( volPcChange > 100.0 ) {
			if ( pcChange < 0 && pcChange > -1.5 ) {
				signal.setText( text );
				rules.addContent(signal);
			}
		}
		return rules;
	}

	private Element addRuleA3(Element rules, Double pcChange, Double volPcChange, String text ) {

		// minimal >4% drop in vol with minimal increase in price means accumulation
		// Rule3: if day1.volPcChange <0 and >-4 and day1.pricePcChange >0 and <4 then acc 2%
		Element signal = new Element("rule");
		if ( volPcChange < 0.0 && volPcChange > -4.0 &&  pcChange > 0.0 && pcChange < 4.0 ) {
			signal.setText( text );
			rules.addContent(signal);
		}
		return rules;
	}

	private Double calcVolPcChange( Double vol0, Double vol1 ) {

		Double diff = vol1 - vol0;
		Double pcChange = 0.0;
		if (Math.abs(diff) > 0.0) {
			if (vol0 == 0.0) { pcChange = 100.0; }
			else { pcChange = 100 * (diff / vol0); }
		}
		return pcChange;
	}

	private Double calcPricePcChange( Double prevClose, Double close ) {

		Double diff = close - prevClose;
		Double pcChange = 100 * (diff / prevClose);
		return pcChange;
	}

	private Element addRuleA4(Element rules, ArrayList prices, Double pcChange3, Double volPcChange3, String text ) {

		// 3 days down marginal volume with marginal price decrease means accumulation
		// Rule4: if day1.volPcChange <0 >-4 and day1.pcChange >0 and <4 and
		//			day2.volPcChange <0 >-4 and day2.pcChange >0 and <4 and
		//			day3.volPcChange <0 >-4 and day3.pcChange >0 and <4 then acc > 2%
		Element signal = new Element("rule");

		String[] cols2 = (String[]) prices.get(1);
		String[] cols1 = (String[]) prices.get(2); 		// Rows are date descending
		String[] cols0 = (String[]) prices.get(3);

		Double vol2 = Double.valueOf(cols2[6]);
		Double vol1 = Double.valueOf(cols1[6]);
		Double vol0 = Double.valueOf(cols0[6]);

		Double volPcChange1 = calcVolPcChange(vol0, vol1);
		Double volPcChange2 = calcVolPcChange(vol1, vol2);

		Double price2 = Double.valueOf(cols2[4]);
		Double price1 = Double.valueOf(cols1[4]);
		Double price0 = Double.valueOf(cols0[4]);

		Double pcChange1 = calcPricePcChange(price0, price1);
		Double pcChange2 = calcPricePcChange(price1, price2);

		if ( volPcChange1 < 0 && volPcChange1 > -4 && pcChange1 > 0 && pcChange1 < 4 &&
				volPcChange2 < 0 && volPcChange2 > -4 && pcChange2 > 0 && pcChange2 < 4 &&
				volPcChange3 < 0 && volPcChange3 > -4 && pcChange3 > 0 && pcChange3 < 4 ) {
			signal.setText( text );
			rules.addContent(signal);
		}
		return rules;
	}

	private Element addRuleD1(Element rules, ArrayList prices, Double pcChange2, String text ) {

		// small vol decrease and price drop >2%, followed by bigger decrease means distribution
		// Rule5: if day1.volPcChange <0 >-4 and day1.pcChange < -2 and day2.pcChange < day1.pcChange then dist
		Element signal = new Element("rule");

		String[] cols1 = (String[]) prices.get(1); 		// Rows are date descending
		String[] cols0 = (String[]) prices.get(2);

		Double vol1 = Double.valueOf(cols1[6]);
		Double vol0 = Double.valueOf(cols0[6]);
		Double volPcChange1 = calcVolPcChange(vol0, vol1);

		Double price1 = Double.valueOf(cols1[4]);
		Double price0 = Double.valueOf(cols0[4]);
		Double pcChange1 = calcPricePcChange(price0, price1);

		if ( volPcChange1 < 0 && volPcChange1 > -4 && pcChange1 < -2 && pcChange2 < pcChange1 ) {
			signal.setText( text );
			rules.addContent(signal);
		}
		return rules;
	}

	public void handleFailure(DatabaseManager dbm, String ticker, Calendar start, Calendar end, String message ) throws Exception {

		/*
		CrushFailure failure = new CrushFailure();
		failure.setTicker(ticker);
		failure.setEndDate(Asset.defaultDateFormat.format(end.getTime()));
		failure.setStartDate(Asset.defaultDateFormat.format(start.getTime()));
		failure.setMessage(message);
		if ( dbm.selectRecords("from CrushFailure where ticker='" + ticker + "' and startDate='" + Asset.defaultDateFormat.format(start.getTime()) + "' and endDate='" + Asset.defaultDateFormat.format(end.getTime()) + "'" ).size() == 0 ) {
			dbm.saveAndCommitRecord(failure);
			Log.infoln("Saved failure [" + ticker + "] " + message );
		}
		*/
	}

	public File getOutputFolder() {

		if( outputFolder == null ) {
			outputFolder = new File( Folders.viperFolder() + "\\" + getResourceId() );
			outputFolder.mkdirs();
		}
		return outputFolder;
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
