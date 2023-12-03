package soft.assetlist;

import leafspider.rest.RepresentationException;
import leafspider.util.JsonConverter;
import leafspider.util.Util;
import soft.report.WatchlistPerformance;

public class AssetlistPerformanceStandalone extends AssetlistRepresentation
{
	public static String representation = "pchangestandalone";
    public String getRepresentation() { return representation; }

	private WatchlistPerformance wp;

	public String getXml() throws RepresentationException {

		try {
			wp = new WatchlistPerformance();
			try { wp.setLimit( Integer.parseInt( getParameter("limit") )); } catch(Exception nan) {}
			wp.doCreateWatchlist();
			wp.doParseWatchlistToCsv();	// Loads histories
			wp.doParseCsvToPchangeXml();
			return Util.fileToString( wp.getPchangeXmlFile(), null );
		}
		catch (Exception e) { return e.getMessage(); }
	}

	public String getHtm() throws Exception {

    	try { getXml(); }
		catch (Exception e) { return e.getMessage(); }
		return wp.doTransform( true );
	}

	public String getJson() throws RepresentationException {

    	try {
			//return JsonConverter.xmlToJson( Util.fileToString( wp.getPchangeXmlFile(), null ) );
			return JsonConverter.xmlToJson( getXml() );
		}
    	catch (Exception e) { return e.getMessage(); }
	}

}
