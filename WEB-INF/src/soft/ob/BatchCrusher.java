package soft.ob;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import org.hibernate.Query;
import org.hibernate.Session;
import org.jdom2.CDATA;
import org.jdom2.Element;

import leafspider.db.DatabaseManager;
import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;
import soft.asset.Asset;
import soft.asset.AssetRepresentation;
import soft.asset.CrushRecord;
import soft.assetlist.Alist;
import soft.assetlist.AlistBatch;
import soft.batch.BatchProject;

public class BatchCrusher
{
	public static void main ( String[] args )
	{
    	String id = "missing";           	
    	try
    	{
    		BatchCrusher crusher = new BatchCrusher(id);
    		crusher.doBatch();
    	}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

    protected String id = null;
	protected DatabaseManager dbm = null;

	public BatchCrusher( String id ) throws Exception
	{
		this.id = id;
		dbm = BatchProject.getDatabaseManager( id );
	}
	
	public void doBatch() throws Exception
	{
		doBatch(null, "1", null, null);
	}

	public void doBatch(String endParm, String yearsParm, String monthsParm, String daysParm) throws Exception
	{
    	Log.infoln( "" );
    	Log.infoln( "Crusher.doBatchCrush ------ " + id + " ------");

		DateFormat format = Asset.defaultDateFormat;	
		Calendar end = Representation.parseDate( endParm, format );
		Calendar start = Representation.getStart( end, yearsParm, monthsParm, daysParm );
		String fmdStart = Asset.defaultDateFormat.format(start.getTime());
		String fmdEnd = Asset.defaultDateFormat.format(end.getTime());

		Alist alist = Alist.instance( id, end, null, null );
		String[] tickerList = alist.getTickers().toArray(new String[] {});
		if ( tickerList.length >  1 ) { Asset.debug = false; }
				
		int chunkSize = 20;
		ArrayList records = new ArrayList(); 
		for( int i = 0; i < tickerList.length; i++ ) 
		{     	    
			if (i % chunkSize == 0 && i > 0) 
			{ 
				dbm.saveOrUpdateAndCommitBatch(records);
				records.clear(); 
			}
			
			String ticker = tickerList[i];		
			Log.infoln( "Crusher.doBatchCrush[" + i + "] ticker=" + ticker);
			
			Asset asset = Asset.instance( ticker );	
			try
			{
				if ( asset.doCrush( start, end ) ) 
				{ 
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
		dbm.saveOrUpdateAndCommitBatch(records);

		System.out.println( "Done" );
	}

    public Long lastModified() throws Exception
    {		
    	String select = "select max(lastModified) from CrushRecord";

        Session session = dbm.getCurrentSession();
        session.beginTransaction();
	    Query query = session.createQuery( select );
	    query.setFirstResult(0);
	    query.setMaxResults(1);
	    return (Long) query.uniqueResult();
    }
    
}
