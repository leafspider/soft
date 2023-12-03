package soft.ob;

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

import leafspider.rest.RepresentationException;
import leafspider.util.*;

import org.jdom2.CDATA;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.Attribute;
import org.jdom2.input.SAXBuilder;

import soft.asset.CrushProject;
import soft.asset.CrushRecord;


public class CrushSnapshot extends CrushRepresentation
{	
	public static String representation = "snapshot";
    public String getRepresentation() { return representation; }

    public String getXml() throws RepresentationException
    {
    	String resourceId = getResourceId();
//		Log.infoln("resourceId=" + resourceId);
        if ( resourceId == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }           	
    	try
    	{
    		CrushRecord record = (CrushRecord) CrushProject.getDatabaseManager().getRecord("CrushRecord","id", resourceId );            
			Element root = record.listElement();
			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{
			CrushRepresentation rep = new CrushSnapshot();
			rep.setResourceId("1");
			Log.infoln( rep.getXml() );			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
