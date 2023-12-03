package soft.batch;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import leafspider.util.*;
import org.hibernate.NonUniqueObjectException;

import soft.asset.*;
import soft.assetlist.Alist;
import soft.report.*;
import leafspider.db.DatabaseManager;
import leafspider.db.DatabaseRecord;
import leafspider.rest.Representation;

public class BatchAgent extends Thread
{
	Boolean debugCrush = false;
	Boolean debugViper = false;
	public int sleepSeconds = 60;
	public int cleanupDays = 3;

	public static void main ( String[] args ) {

		try {
			//String batchId = "gotcbb_1000";
			String batchId = "camel";
			//String batchId = "finviz-1";
			testBatch(batchId);
			//testPrintFailures( batchId );

			//String ticker = "VSBN";
			//testCreateCrush( batchId, ticker );
			//testCreateFailure( batchId, ticker );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void testBatch( String batchId ) {

		try {
			DatabaseManager dbm = BatchProject.getDatabaseManager( batchId );
			List<CrushRecord> records = dbm.listRecords("CrushRecord");
			System.out.println( "\n------------- size=" + records.size() + " -------------\n" );
			BatchAgent agent = new BatchAgent();
			agent.debugCrush = true;
			//agent.debugViper = true;
			//agent.setKeepRunning(false);
			//agent.start();
			agent.doBatchCrush( batchId );
			//records = dbm.listRecords("CrushRecord");
			//System.out.println( "\n------------- size=" + records.size() + " -------------" );
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void testCreateCrush( String batchId, String ticker ) {

		try {
			DatabaseManager dbm = BatchProject.getDatabaseManager( batchId );
			CrushRecord record = new CrushRecord();
			record.setDatabaseManager(dbm);
			record.setTicker(ticker);
			record.setStartDate("test fmdStart");
			record.setEndDate("test fmdEnd");
			dbm.saveAndCommitRecord(record);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void testCreateFailure( String batchId, String ticker ) {

		try {
			DatabaseManager dbm = BatchProject.getDatabaseManager( batchId );
			CrushFailure failure = new CrushFailure();
			failure.setTicker(ticker);
			failure.setEndDate("test endDate");
			failure.setStartDate("test startDate");
			dbm.saveAndCommitRecord(failure);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public static void testPrintFailures( String batchId ) {

		try {

			DatabaseManager dbm = BatchProject.getDatabaseManager( batchId );
			List failedList = dbm.select("select ticker, count(ticker) from CrushFailure group by ticker");
			Log.infoln("size=" + failedList.size());
			Iterator results = failedList.iterator();
			while ( results.hasNext() ) {
				Object[] tuple = (Object[]) results.next();
				Log.infoln("" + tuple[0] + "=" + tuple[1]  );
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	boolean keepRunning = true;
	public boolean isKeepRunning()
	{
		return keepRunning;
	}
	public void setKeepRunning(boolean keep)
	{
		this.keepRunning = keep;
	}
	
	public void run() {

		Log.infoln("BatchAgent.run");

		while( isKeepRunning() ) {

			Calendar now = new GregorianCalendar();
			int hour = now.get(Calendar.HOUR_OF_DAY);
			int min = now.get(Calendar.MINUTE);

			// ------ crush

			Properties props = new Properties();
			try {
				props.load( new FileInputStream( ServerContext.getDebugFile() ) );
				debugCrush = Boolean.parseBoolean( (String) props.get("batch.crush") );
			}
			catch( Exception e ) {}

			if( debugCrush == true || ((hour >= 17 && hour <= 23 )) ) {        // Run between 5:00pm and midnight

				List<String> batches = BatchProject.getBatches();
				Iterator ids = batches.iterator();

				while (ids.hasNext()) {
					String id = (String) ids.next();
					try {
						doBatchCrush(id);
					}
					catch (Exception e) {
						Log.infoln(e.toString());
						e.printStackTrace();
					}
				}
				try {
					doCrushCleanup();
				}
				catch (Exception e) {
					Log.infoln(e.toString());
					e.printStackTrace();
				}
			}

			/* jmh 2021-03-23
			// ------- viper

			props = new Properties();
			try {
				props.loadTimeline( new FileInputStream( ServerContext.getDebugFile() ) );
				debugViper = Boolean.parseBoolean( (String) props.get("batch.viper") );
			}
			catch( Exception e ) {}

			if( debugViper == true || ((hour == 18 && min > 5) || (hour > 18 && hour <= 23 )) ) { 		// Run between 6:05pm and midnight

				List<String> reports = ViperProject.getVipers();

				Iterator ids = reports.iterator();
				while( ids.hasNext() )
				{
					String id = (String) ids.next();
					try {
						doViper(id);
					}
					catch( Exception e ) {
						Log.infoln(e.toString());
						e.printStackTrace();
					}
				}
			}
			*/

			/* jmh 2021-02-01
			// ------- barchart reports

			Boolean debugReports = false;
			Properties props = new Properties();
			try {
				props.loadTimeline( new FileInputStream( ServerContext.getDebugFile() ) );
				debugReports = Boolean.parseBoolean( (String) props.get("reports") );
			}
			catch( Exception e ) {}

			if( debugReports == true || ((hour == 18 && min > 5) || (hour > 18 && hour <= 23 )) ) 	// Run between 6:05pm and midnight
			{
				List<String> reports = ReportProject.getReports();
	
				ids = reports.iterator();
				while( ids.hasNext() )
				{
					String id = (String) ids.next();         	
			    	try {
			    		doReport(id);
			    	}
					catch( Exception e ) {
						Log.infoln(e.toString());
						e.printStackTrace();
					}
				}
			}
			*/

			try {
//				Log.infoln("BatchAgent sleeping");
//				Log.infoln("");
				sleep( sleepSeconds * 1000 );
			}
			catch( Exception e ) { }
		}
	}

	public void doBatchCrush(String id) throws Exception {

		String endParm = null;

		Properties props = new Properties();
		props.load( new FileInputStream( BatchProject.getConfigFile( id ) ) );
		String lag = props.getProperty("lag");
		int lagDays = 0;

		if ( lag != null ) {

			lagDays = Integer.parseInt(lag);

			Calendar now = new GregorianCalendar();
			if ( now.get( Calendar.HOUR_OF_DAY ) < 17 ) {	// not yet updated
				lagDays += 1;
			}
			Calendar end = new GregorianCalendar();
			end.add( Calendar.DATE, 0 - lagDays);
			endParm = Asset.defaultDateFormat.format( end.getTime() );
		}

		doMultithreadBatchCrush(id, endParm, "1", null, null);
	}

	public void doMultithreadBatchCrush(String id, String endParm, String yearsParm, String monthsParm, String daysParm) throws Exception
	{
		Long startTime = Util.getNow();

    	DatabaseManager dbm = BatchProject.getDatabaseManager( id );

    	if( dbm != null ) {

			long last = Timestamp.readTimestamp(dbm);
			long dif = Util.getNow() - last;
//    		Log.infoln( "BatchAgent.doBatchCrush dif=" + dif );
			if( dif < 14400000 && debugCrush == false ) { return; }	// 4 hours
			//if( dif < 500 ) { return; }	// 2 mins
    	}
    	
//    	Log.infoln( "" );
    	Log.infoln( "BatchAgent.doBatchCrush --- " + id + " ---");

		DateFormat format = Asset.defaultDateFormat;	
		Calendar end = Representation.parseDate( endParm, format );
		Calendar start = Representation.getStart( end, yearsParm, monthsParm, daysParm );
		String fmdStart = Asset.defaultDateFormat.format(start.getTime());
		String fmdEnd = Asset.defaultDateFormat.format(end.getTime());

		Alist alist = Alist.instance( id, end, null, null );
		
//		String[] tickerList = alist.getTickers().toArray(new String[] {});
		Set<String> mySet = new HashSet<String>(alist.getTickers());
		String[] tickerList = mySet.toArray(new String[mySet.size()]);
		
		if ( tickerList.length >  1 ) { Asset.debug = false; }
		
		alist.initDatabase(dbm);

		int numThreads = 50;
		for( int i = 0; i < tickerList.length; i += numThreads ) {

			try {

				if (isKeepRunning()) {

					List<CrushThread> crushThreads = new ArrayList<CrushThread>();
					for ( int j = 0; j < numThreads; j++) {
						int k = i+j;
						if ( k >= tickerList.length ) { break; }
						String ticker = tickerList[k];
						//Log.infoln( "BatchAgent.doBatchCrush[" + k + "] id=" + id + " ticker=" + ticker);
						CrushThread crushThread = new CrushThread(dbm, ticker, start, end, fmdStart, fmdEnd);
						crushThreads.add( crushThread );
					}

					Iterator<CrushThread> crushIt = crushThreads.iterator();
					while( crushIt.hasNext() ) {
						CrushThread crushThread = crushIt.next();
						crushThread.start();
					}

					ArrayList records = new ArrayList();

					crushIt = crushThreads.iterator();
					while( crushIt.hasNext() ) {
						CrushThread crushThread = crushIt.next();
						crushThread.join();
						if ( crushThread.getMessage() == null && crushThread.getRecord() != null ) {
							records.add(crushThread.getRecord());
						}
						else {
							CrushFailure fail = crushThread.getFailure();
							if ( dbm.selectRecords("from CrushFailure where ticker='" + fail.getTicker() + "' and startDate='" + fail.getStartDate() + "' and endDate='" + fail.getEndDate() + "'" ).size() == 0 ) {
								dbm.saveAndCommitRecord(fail);
								Log.infoln("BATCH saved failure " + fail.getTicker());
							}
						}
					}

					Log.infoln( "BatchAgent.doBatchCrush " + id + " [" + i + "/" + tickerList.length + "] updating " + records.size() + " records" );
					dbm.saveOrUpdateAndCommitBatch(records);
				}
			}
			catch(Exception e ) {
				Log.infoln(e);
			}

			sleep( 100 );
		}

		Timestamp.writeTimestamp(dbm);
			
		Calendar now = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );

		double duration = (Util.getNow() - startTime)/1000D;
		//Log.infoln("BatchAgent.doBatchCrush Done " + dateFormat.format(now.getTime()) );
		Log.infoln("BatchAgent.doBatchCrush " + id + " done in " + duration + " seconds with " + numThreads + " threads" );
	}

	public void doReport(String id) throws Exception {

		String html = "";	
		Calendar now = new GregorianCalendar();		

		BarchartReport page = null;			
		/*
		if( id.equals("forex") ) { page = new BarchartForex(); }
		if( id.equals("futures") ) { page = new BarchartFutures(); }
		if( id.equals("indices") ) { page = new BarchartIndices(); }
		if( page != null )
		{
			page.setResourceId( id );
			File outFile = new File( page.getOutputFolder() + "\\output.htm" );
			if( outFile.exists() ) { html = Util.fileToString(outFile, "\n"); }
			else
			{
				page.populate();
				html = page.getHtml();
				PrintStream out = Util.getPrintStream( page.getOutputFile().getAbsolutePath() );
				out.print(html);
			}
		}
		*/
		if( page == null ) {
			page = new BarchartReport();
		}
		page.setResourceId( id );

		long last = Timestamp.readTimestamp(page.getOutputFolder());
		long dif = Util.getNow() - last;
//   		Log.infoln( "BatchAgent.doReport id=" + id + " dif=" + dif ); 
		
		if( dif < 21600000 ) { return; }			// 6 hours
		
    	Log.infoln( "BatchAgent.doReport --- " + id + " ---");    	

		Util.deleteAllFiles(page.getOutputFolder());
    	
    	page.populate();
		html = page.getHtml();
		
		PrintStream out = Util.getPrintStream( page.getOutputFile().getAbsolutePath() );
		out.print(html);
		out.close();

		Timestamp.writeTimestamp(page.getOutputFolder());
		
		now = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );
		Log.infoln("BatchAgent.doReport Done " + dateFormat.format(now.getTime()) );
	}
	
	public void doCrushCleanup() throws Exception {

		File folder = new File( CrushProject.getDatabaseManager().getProjectFolder() + "\\out" );
		//Log.infoln( "Cleanup on " + folder.getAbsolutePath() );

		File[] deletableFolders = folder.listFiles(new FileFilter() {
   			public boolean accept(File file) {
   				if ( file.isDirectory() )
   				{
   					long dif = Util.getNow() - file.lastModified();
   					dif /= 86400000;	// 1000x60x60x24 = days
//  					Log.infoln( "dif=" + dif );
   					if( dif > cleanupDays ) { return true; }
   				}
   		        return false;
   		    }
   		});
   		
		for(int i=0; i<deletableFolders.length; i++) {
			Util.deleteFolder( deletableFolders[i] );
			Log.infoln( "Deleted folder " + deletableFolders[i].getName() );
		}
	}

	private class CrushThread extends Thread {

		public CrushThread(DatabaseManager dbm, String ticker, Calendar start, Calendar end, String fmdStart, String fmdEnd) {

			this.dbm = dbm;
			this.ticker = ticker;
			this.start = start;
			this.end = end;
			this.fmdStart = fmdStart;
			this.fmdEnd = fmdEnd;
		}

		private String ticker;
		private DatabaseManager dbm;
		private Calendar start;
		private Calendar end;
		private String fmdStart;
		private String fmdEnd;

		private CrushRecord record = null;
		public CrushRecord getRecord() { return record; }

		private String message = null;
		public String getMessage() { return message; }

		public void run() {

			try {

				Asset asset = Asset.instance(ticker);

				if (asset.doCrush(start, end)) {

					//Log.infoln( "BatchAgent.doBatchCrush crushed " + ticker);

					String select = "from CrushRecord where ticker='" + ticker + "'";
					List<CrushRecord> list = dbm.selectRecords(select);
					//dbm.reportRecords(list);

					if (list == null || list.size() < 1) { record = new CrushRecord(); }
					else if (list.size() > 1) { throw new Exception("Duplicate CrushRecord ticker=" + ticker); }
					else { record = list.get(0); }

					record.setDatabaseManager(dbm);
					record.setTicker(ticker);
					record.setStartDate(fmdStart);
					record.setEndDate(fmdEnd);
					record.setClose(asset.nClose[asset.prices.size() - 1]);
					record.setFlow(asset.nVol[asset.prices.size() - 1]);
					record.setPeel(asset.nsdLong[asset.prices.size() - 1]);
					record.setJam(asset.nsdMedium[asset.prices.size() - 1]);
					record.setPress(asset.nsdShort[asset.prices.size() - 1]);
					record.setCrush(asset.crushSeries[asset.prices.size() - 1]);
					record.setPlate(asset.plate[asset.prices.size() - 1]);
					record.setPear(asset.crushGradient[asset.crushGradient.length - 1]);
					record.setCherry(asset.cherryWidth);
					record.setCloseSeries(AssetRepresentation.getSeriesValues(asset.nClose));
					record.setFlowSeries(AssetRepresentation.getSeriesValues(asset.nVol));
					record.setPlateSeries(AssetRepresentation.getSeriesValues(asset.plate));
					record.setPearSeries(AssetRepresentation.getSeriesValues(asset.crushGradient));
					record.setCrushSeries(AssetRepresentation.getSeriesValues(asset.crushSeries));
					record.setVolumeSeries(AssetRepresentation.getVolSeriesValues(asset.volumeSeries));
					record.setDateSeries( AssetRepresentation.getSeriesValues( asset.dateSeries ) );
					record.setPrices( skimPrices(asset.prices) );

					//			record.setDailyQuoteHtm( downloadDailyQuoteHtm( ticker ) );
					//			record.saveOrUpdateAndCommit();
					//			Log.infoln( ticker + " --- added ---");
				}
				else {
					message = "Crush is false";
				}
			}
			catch( Exception e ) {
				message = e.getMessage();
				Log.infoln("Problem processing " + ticker);
				Log.infoln(e);
				e.printStackTrace();
			}
		}

		public CrushFailure getFailure() {

			CrushFailure failure = new CrushFailure();
			failure.setTicker(ticker);
			failure.setEndDate(fmdEnd);
			failure.setStartDate(fmdStart);
			failure.setMessage(message);
			return failure;
		}
	}

	public static String skimPrices(ArrayList<String[]> prices) throws Exception {
		//Log.infoln("prices.size=" + prices.size());
		if ( prices.size() > 15 ) {
			prices = new ArrayList<String[]>(prices.subList(prices.size() - 15, prices.size()));
		}
		return JsonConverter.objectToJson( prices );
	}

	public void doSingleThreadBatch(String id, String endParm, String yearsParm, String monthsParm, String daysParm) throws Exception
	{
		Long startTime = Util.getNow();

		DatabaseManager dbm = BatchProject.getDatabaseManager( id );

		if( dbm != null )
		{
			long last = Timestamp.readTimestamp(dbm);
			long dif = Util.getNow() - last;
//    		Log.infoln( "BatchAgent.doBatchCrush dif=" + dif );
//			if( dif < 10000 ) { return; }	// 10 mins
//			if( dif < 7200000 ) { return; }	// 2 hours
			if( dif < 14400000 ) { return; }	// 4 hours
		}

//    	Log.infoln( "" );
		Log.infoln( "BatchAgent.doBatchCrush --- " + id + " ---");

		DateFormat format = Asset.defaultDateFormat;
		Calendar end = Representation.parseDate( endParm, format );
		Calendar start = Representation.getStart( end, yearsParm, monthsParm, daysParm );
		String fmdStart = Asset.defaultDateFormat.format(start.getTime());
		String fmdEnd = Asset.defaultDateFormat.format(end.getTime());

		Alist alist = Alist.instance( id, end, null, null );

//		String[] tickerList = alist.getTickers().toArray(new String[] {});
		Set<String> mySet = new HashSet<String>(alist.getTickers());
		String[] tickerList = mySet.toArray(new String[mySet.size()]);

		if ( tickerList.length >  1 ) { Asset.debug = false; }

		alist.initDatabase(dbm);

		int chunkSize = 20;
		ArrayList records = new ArrayList();
		for( int i = 0; i < tickerList.length; i++ )
		{
//			if( i > 850 ) { continue; }
			if (i % chunkSize == 0 && i > 0) {

				Log.infoln( "BatchAgent.doBatchCrush " + id + " [" + i + "/" + tickerList.length + "] updating " + records.size() + " records" );
				try {
					dbm.saveOrUpdateAndCommitBatch(records);
				}
				catch( NonUniqueObjectException nuoe )
				{
					Iterator iter = records.iterator();
					while(iter.hasNext())
					{
						DatabaseRecord record = (DatabaseRecord) iter.next();
						try {
							record.saveOrUpdateAndCommit();
						}
						catch( NonUniqueObjectException nue ) {
							Log.infoln( "BatchAgent.doBatchCrush " + id + " NonUniqueObjectException on record id=" + record.getId() );
						}
					}
				}
				records.clear();
//				setKeepRunning(false);		// debug
			}

			if ( isKeepRunning() )
			{
				String ticker = tickerList[i];
//				Log.infoln( "BatchAgent.doBatchCrush[" + i + "] ticker=" + ticker);

				Asset asset = Asset.instance( ticker );
				try
				{
					if ( asset.doCrush( start, end ) )
					{
//						Log.infoln( "BatchAgent.doBatchCrush[" + i + "] crushed " + ticker);

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
						record.setCherry( asset.cherryWidth );
						//			record.setDailyQuoteHtm( downloadDailyQuoteHtm( ticker ) );

						record.setCloseSeries( AssetRepresentation.getSeriesValues( asset.nClose ) );
						record.setFlowSeries( AssetRepresentation.getSeriesValues( asset.nVol ) );
						record.setPlateSeries( AssetRepresentation.getSeriesValues( asset.plate ) );
						record.setPearSeries( AssetRepresentation.getSeriesValues( asset.crushGradient ) );
						record.setCrushSeries( AssetRepresentation.getSeriesValues( asset.crushSeries ) );
						record.setVolumeSeries( AssetRepresentation.getVolSeriesValues( asset.volumeSeries ) );
						record.setDateSeries( AssetRepresentation.getSeriesValues( asset.dateSeries ) );

						//					record.saveOrUpdateAndCommit();

						//					Log.infoln( ticker + " --- added ---");

						records.add(record);
					}
				}
				catch( Exception e )
				{
					Log.infoln(e);
				}
			}
		}
		Log.infoln( "BatchAgent.doBatchCrush " + id + " [" + tickerList.length + "/" + tickerList.length + "] updating " + records.size() + " records" );
		dbm.saveOrUpdateAndCommitBatch(records);

		Timestamp.writeTimestamp(dbm);

		Calendar now = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss" );

		double duration = (Util.getNow() - startTime)/1000D;
		//Log.infoln("BatchAgent.doBatchCrush Done " + dateFormat.format(now.getTime()) );
		Log.infoln("BatchAgent.doBatchCrush [" + id + "] + in " + duration + " + seconds" );
	}

	public void doViper(String id) throws Exception
	{
		ViperReport page = new ViperReport();
		page.setResourceId( id );

		long last = Timestamp.readTimestamp(page.getOutputFolder());
		long dif = Util.getNow() - last;
   		//Log.infoln( "BatchAgent.doReport id=" + id + " dif=" + dif );

		if( dif < 21600000 ) { return; }			// 6 hours
		//if( dif < 120000 ) { return; }			// 2 mins

		page.populate();
	}

}
