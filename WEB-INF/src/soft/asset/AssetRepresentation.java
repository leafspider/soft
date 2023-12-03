package soft.asset;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.*;


public abstract class AssetRepresentation extends Representation
{
	public static String resource = "asset";
    public String getResource() { return resource; }

    public static AssetRepresentation dispatch( HttpServletRequest request ) throws RepresentationException {

    	if ( debugResource( resource ) ) {
			reportParameters( request );
		}

       	AssetRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
//    	CIRILogger.infoln( "pathInfo=" + pathInfo );
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + AssetCrush.representation + ".*?" ) ) { rep = new AssetCrush(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetSnapshot.representation + ".*?" ) ) { rep = new AssetSnapshot(); }
			else if ( pathInfo.matches( "/.*?/" + AssetViper.representation + ".*?" ) ) { rep = new AssetViper(); }
	    	else if ( pathInfo.matches( "/.*?/" + ObAssetMulti.representation + ".*?" ) ) { rep = new ObAssetMulti(); }
	//    	report( getRequest(), resource );
	
	    	rep.setRequest( request );
    	}
    	catch( Exception e )
    	{
    		e.printStackTrace();
    		throw new RepresentationException( "Unrecognised representation: " + resource + pathInfo );
    	}
    	if ( rep == null )
    	{
    		throw new RepresentationException( "Null representation: " + resource + pathInfo );
    	}
//    	CIRILogger.infoln( "Found dispatch rep=" + rep.representation );
	    	
    	return rep;
    }

	private static NumberFormat seriesFormat = new DecimalFormat( "#0.000" );
    public static String getSeriesValues( double[] series )
    {
    	String vals = "";
		for ( int i = 0; i < series.length; i++ ) {
			if ( i == 0 ) { vals += seriesFormat.format( series[i] ); }
			else { vals += "," + seriesFormat.format( series[i] ); }
		}
		return vals;	
    }
	public static String getSeriesValues( String[] series )
	{
		String vals = "";
		for ( int i = 0; i < series.length; i++ ) {
			if ( i == 0 ) { vals += "\"" + series[i] + "\""; }
			else { vals += ",\"" + series[i] + "\""; }
		}
		return vals;
	}

	private static NumberFormat volSeriesFormat = new DecimalFormat( "###0.000" );
    public static String getVolSeriesValues( double[] series )
    {
    	double scaler = 1000000.d;
    	String vals = "";
		for ( int i = 0; i < series.length; i++ )
		{			
			if ( i == 0 ) { vals += volSeriesFormat.format( series[i]/scaler ); }
			else { vals += "," + volSeriesFormat.format( series[i]/scaler ); }
		}
		return vals;	
    }

    public BarchartQuote downloadDailyQuote( String ticker ) throws Exception
    {  
		BarchartQuote quote = new BarchartQuote();
//		Log.debug = true;
//		rec.setFile(new File("C:\\Temp\\barcharts\\SXI.htm"));		// DEBUG
		quote.setUrl("http://old.barchart.com/detailedquote/stocks/" + ticker);
		quote.populate();
		return quote;
    }
}
