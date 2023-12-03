package soft.assetlist;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.TreeSet;

import leafspider.db.DatabaseManager;
import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.hibernate.mapping.Set;
import org.jdom2.CDATA;
import org.jdom2.Element;

import leafspider.util.DateUtils;
import soft.asset.Asset;
import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.batch.BatchProject;
import soft.performance.Trade;
import soft.portfolio.Stock;
import soft.report.Folders;

public class AssetlistMulti extends AssetlistRepresentation
{	
	public static String representation = "multi";
    public String getRepresentation() { return representation; }

    public String getXml() throws RepresentationException
    {
    	String id = getResourceId();
    	if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			int offset = 0; try { offset = Integer.parseInt( getRequest().getParameter( "offset" ) ); } catch(Exception e){}
//			int offset = 0; try { offset = Integer.parseInt( getResourceId() ); } catch(Exception e){}
			int limit = 6;  try { limit = Integer.parseInt( getRequest().getParameter( "limit" ) ); } catch(Exception e){}

//			Log.infoln("offset=" + offset );
//			Log.infoln("limit=" + limit );
	   			   		
			Properties props = new Properties();
			props.load( new FileInputStream( BatchProject.getConfigFile( getResourceId() ) ) );
			String title = props.getProperty("title");

			Element root = new Element( representation );
	   		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "hierarchy" ).addContent( new CDATA("" + BatchProject.getProperty(id, "hierarchy")  ) ) );
			root.addContent( new Element( "id" ).addContent( new CDATA( getResourceId() ) ) );
			root.addContent( new Element( "title" ).addContent( new CDATA( title ) ) );
			root.addContent( new Element( "offset" ).addContent( new CDATA( "" + offset ) ) );
			root.addContent( new Element( "limit" ).addContent( new CDATA( "" + limit ) ) );
			
			DateFormat format = Asset.defaultDateFormat;
			Calendar end = Calendar.getInstance();
			Calendar start = getStart( end, "1", null, null );
//			Log.infoln( "start=" + format.format( start.getTime() ) );

			String lag = props.getProperty("lag");
			if ( lag != null ) {
				int lagDays = Integer.parseInt(lag);
				end.add( Calendar.DATE, 0-lagDays);
			}
			String endParm = format.format( end.getTime() );

			root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
			root.addContent( new Element( "end" ).addContent( new CDATA( endParm ) ) );

			DatabaseManager dbm = BatchProject.getDatabaseManager( id );

			root.addContent( Timestamp.getElement( dbm ) );
			
			Long max = (Long) dbm.countRecords( "CrushRecord" );
			root.addContent( new Element( "max" ).addContent( new CDATA( "" + max) ) );
			
			//String select = "from CrushRecord order by ticker";		// where ticker='" + ticker + "'";			// jmh 2021-02-16
			String select = "from CrushRecord order by crush desc";

			List<CrushRecord> list = dbm.selectRecords( select, offset, limit );
			/*
			if ( lagDays > 0 ) {

				Log.infoln("endParm=" + endParm + " end=" + list.get(0).getEndDate() );
				if( list.get(0).getEndDate().equals(endParm) ) {
					list.remove(0);
				}
			}
			*/

//			dbm.reportRecords(list);
//			if( list == null || list.size() > 1 ) { throw new Exception("Duplicate CrushRecord ticker=" + ticker); }

			Iterator records = list.iterator();
			while(records.hasNext()) {

				CrushRecord record = (CrushRecord) records.next();	//list.get(0);    		
	    		if( record != null) {

	    			Element recordEl = record.element().clone();
					recordEl.removeChild("plate");	// jmh 2021-02-02
					recordEl.removeChild("pear");	// jmh 2021-02-02

	    			// Add plotLine for most recent Snapshot
					String selectSnapshot = "from CrushRecord where ticker='" + record.getTicker() + "' order by endDate desc";
//					Log.infoln("selectSnapshot=" + selectSnapshot);
					List snaplist = CrushProject.getDatabaseManager().selectRecords( selectSnapshot );
//					Log.infoln("snaplist.size()=" + snaplist.size());
					if( snaplist.size() > 0 )
					{
						CrushRecord snap = (CrushRecord) snaplist.get(0);	
//						Log.infoln("snap.getEndDate()=" + snap.getEndDate());
						Element snapEl = new Element("snapshot");
						snapEl.addContent(snap.getEndDate());
						recordEl.addContent(snapEl);
					}

					root.addContent( recordEl );
	    		}
//	    		break;
			}
			
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

    public String getData() throws RepresentationException
    {
    	String id = getResourceId();
    	if ( id == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			int offset = 0; try { offset = Integer.parseInt( getRequest().getParameter( "offset" ) ); } catch(Exception e){}
			int limit = 6;	try { limit = Integer.parseInt( getRequest().getParameter( "limit" ) ); } catch(Exception e){}
			boolean fill = false;	try { fill = Boolean.parseBoolean( getRequest().getParameter( "fill" ) ); } catch(Exception e){}

//			Log.infoln("offset=" + offset );
//			Log.infoln("limit=" + limit );

			DatabaseManager dbm = BatchProject.getDatabaseManager( id );

//			Long max = (Long) dbm.countRecords( "CrushRecord" );

			String select = "from CrushRecord order by crush desc";
			List<CrushRecord> list = dbm.selectRecords( select, offset, limit );
//			dbm.reportRecords(list);

//			String json = "{\"size\": {" + max + "},";
			String json = "[";

			int n = 1;
			Iterator records = list.iterator();
			while(records.hasNext())
			{
				CrushRecord record = (CrushRecord) records.next();	//list.get(0);
				/*
	    		if( record != null)
	    		{
	    			json += "";
	    			if( n > 1 ) { json += ","; } 
	    			
	        		json += "{\"title\": { " + 
	        					"\"useHTML\": true," +
	        					"\"text\": \"<span id='populate" + n + "'>" + record.getTicker() + "</span>\"," + 
	        					"\"style\": { \"color\": \"#" + Asset.getCherryColor(record.getCherry()) + "\", \"cursor\": \"hand\" }" +                 
	        				"}," +        
	        				"\"series\": [" +    				
	            				"{\"name\": \"crush\"," + 
	            				"\"data\": [" + record.getCrushSeries() + "]}," + 	            				
	            				"{\"name\": \"close\"," + 
	            				"\"data\": [" + record.getCloseSeries() + "]}," + 	            				
	            				"{\"name\": \"flow\"," + 
	            				"\"data\": [" + record.getFlowSeries() + "]}";
	        		if( fill )
	        		{
	        			json += "," + 
	        					"{\"name\": \"plate\"," + 
	            				"\"data\": [" + record.getPlateSeries() + "]}," +	            				
	            				"{\"name\": \"pear\"," + 
	            				"\"data\": [" + record.getPearSeries() + "]}";
	        		}
       				json += "]}";
	    					
//	    			json += record.json();
	    			json += "";
	    			
	    			n++;
	    		}
	    		*/
				
	    		if( record != null)
	    		{
	    			String uptick = record.getTicker().toUpperCase();
	    			
	    			// Get plotLine for least recent Snapshot
					String selectSnapshot = "from CrushRecord where ticker='" + uptick + "' order by endDate asc";
//					Log.infoln("selectSnapshot=" + selectSnapshot);
					List snaplist = CrushProject.getDatabaseManager().selectRecords( selectSnapshot );
//					Log.infoln("snaplist.size()=" + snaplist.size());
					String snapshotStart = null;
					String snapshotEnd = null;
					int snapshotValue = -1;
					if( snaplist.size() > 0 )
					{						
						CrushRecord snapshot = null;
						Iterator snaps = snaplist.iterator();
						while(snaps.hasNext())
						{
							snapshot = (CrushRecord) snaps.next();	
//							Log.infoln("snap.getEndDate()=" + snap.getEndDate());
							snapshotStart = snapshot.getStartDate().toString();
							snapshotEnd = snapshot.getEndDate().toString();
						
							SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
							try 
							{					 
								Date recordDate = formatter.parse(record.getEndDate());
								Date snapshotDate = formatter.parse(snapshot.getEndDate());
								int diff = bizDaysBetweenDates(snapshotDate, recordDate );
//								Log.infoln( "diff=" + diff );
								
								if( diff > 252 ) { continue; }		// Can only use if in the last year 
								else
								{
									snapshotValue = 252 - diff;
									break;
								}
							}
							catch (Exception e) 
							{
								e.printStackTrace();
							}
						}						
					}	    	
					//record.getTicker()
	    			
	    			json += "";
	    			if( n > 1 ) { json += ","; }

	        		json += "{";

	        		json += "\"title\": { "+
							    "\"ticker\": \"" + uptick + "\","+
	        					"\"useHTML\": true,"+
	        					"\"text\": \"<span id='populate" + n + "'>" + uptick + "</span>\","+
	        					"\"style\": { \"color\": \"#" + Asset.getCherryColor(record.getCherry()) + "\", \"cursor\": \"hand\" }"+
	        				"},";
	        		
	        		if(snapshotValue > -1)
	        		{
	        			json += "\"xAxis\": {"+
	            					"\"plotLines\": [{"+
	            						"\"color\": \"green\","+
	            						"\"value\": " + snapshotValue + ","+
	            						"\"width\": 2,"+
            							"\"zIndex\": 100,"+
	            						"\"label\": {"+
	    	        						"\"text\": \"" + uptick + " " + snapshotEnd + "\","+
	            							"\"verticalAlign\": \"bottom\","+
	            							"\"x\": -60,"+
	            							"\"y\": 27,"+										                				                
	            							"\"rotation\": 0,"+
	            							"\"style\": {"+
	            								"\"color\": \"green\","+
	            								"\"fontWeight\": \"bold\","+
	            								"\"cursor\": \"hand\""+
	            							"}"+
	            						"}"+
	            						"}]"+
	            					"},";
	        		}
	        		
	        		json += "\"series\": [" +    				
	            				"{\"name\": \"crush\"," + 
	            				"\"data\": [" + record.getCrushSeries() + "]}," + 	            				
	            				"{\"name\": \"close\"," + 
	            				"\"data\": [" + record.getCloseSeries() + "]}," + 	            				
	            				"{\"name\": \"flow\"," + 
	            				"\"data\": [" + record.getFlowSeries() + "]}";
	        		if( fill ) {
	        			json += ","+ 
	            				"{\"name\": \"porter\"," +
	            				"\"data\": [" + record.getPearSeries() + "]}," +
								"{\"name\": \"plate\"," +
								"\"data\": [" + record.getPlateSeries() + "]}";
	        		}
       				json += "]}";
       				
//	    			json += record.json();
//	    			json += "";
	    			
	    			n++;
	    		}
//	    		break;
			}
			
			json += "]";

//			Log.infoln(json);
			
			return json;
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    
    public int bizDaysBetweenDates(Date startDate, Date endDate) 
    {  
    	Calendar startCal;  
        Calendar endCal;  
        startCal = Calendar.getInstance();  
        startCal.setTime(startDate);  
        endCal = Calendar.getInstance();  
        endCal.setTime(endDate);  
        int workDays = 0;  
      
        //Return 0 if start and end are the same  
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {  
            return 0;  
        }  
      
        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {  
            startCal.setTime(endDate);  
            endCal.setTime(startDate);  
        }  
      
        do 
        {  
            startCal.add(Calendar.DAY_OF_MONTH, 1);  
            if ( startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY 
            	&& startCal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) 
            {  
            	++workDays;  
            }  
        } 
        while (startCal.getTimeInMillis() < endCal.getTimeInMillis());        
        return workDays;  
    }  
    

}
