package soft.asset;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import leafspider.db.DatabaseManager;
import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.hibernate.Query;
import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.input.SAXBuilder;

import soft.batch.BatchProject;


public class ObAssetMulti extends AssetRepresentation
{	
	public static String representation = "multi";
    public String getRepresentation() { return representation; }

	public static void main ( String[] args )
	{
		Log.debug = true;
		test();
	}

	public static void test()
	{
		try
		{				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
    public String getXml() throws RepresentationException
    {
//    	String batch = getResourceId();
//    	if ( batch == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
//			int offset = 0; try { offset = Integer.parseInt( getRequest().getParameter( "offset" ) ); } catch(Exception e){}
			int offset = 0; try { offset = Integer.parseInt( getResourceId() ); } catch(Exception e){}
			int limit = 4;  try { limit = Integer.parseInt( getRequest().getParameter( "limit" ) ); } catch(Exception e){}

			Log.infoln("offset=" + offset );
			Log.infoln("limit=" + limit );

			Element root = new Element( representation );
	   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );

			DateFormat format = Asset.defaultDateFormat;
			Calendar end = Calendar.getInstance();
			Calendar start = getStart( end, "1", null, null );
//			Log.infoln( "start=" + format.format( start.getTime() ) );

			root.addContent( new Element( "start" ).addContent( new CDATA( format.format( start.getTime() ) ) ) );
			root.addContent( new Element( "end" ).addContent( new CDATA( format.format( end.getTime() ) ) ) );

			DatabaseManager dbm = BatchProject.getDatabaseManager( "crusher" );
			
			Long max = (Long) dbm.countRecords( "CrushRecord" );
//			Log.infoln( "max=" + max);
			root.addContent( new Element( "max" ).addContent( new CDATA( "" + max ) ) );
			
			String select = "from CrushRecord order by ticker";		// where ticker='" + ticker + "'";
			
			List<CrushRecord> list = dbm.selectRecords( select, offset, limit );
//			dbm.reportRecords(list);
//			if( list == null || list.size() > 1 ) { throw new Exception("Duplicate CrushRecord ticker=" + ticker); }

			Iterator records = list.iterator();
			while(records.hasNext())
			{
				CrushRecord record = (CrushRecord) records.next();	//list.get(0);    		
	    		if( record != null)
	    		{
	    			root.addContent( record.element() );
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
    
}
