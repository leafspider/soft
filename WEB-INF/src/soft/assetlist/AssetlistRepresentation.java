package soft.assetlist;

import leafspider.rest.Representation;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;

import javax.servlet.http.HttpServletRequest;


public abstract class AssetlistRepresentation extends Representation
{
	public static String resource = "assetlist";
    public String getResource() { return resource; }

    public static AssetlistRepresentation dispatch( HttpServletRequest request ) throws RepresentationException {

		if ( debugResource( resource ) ) {
			reportParameters( request );
		}

       	AssetlistRepresentation rep = null;           	
    	String pathInfo = request.getPathInfo(); 
       	try
    	{
	    	if ( pathInfo == null ) { rep = null; }				
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistCrush.representation + ".*?" ) ) { rep = new AssetlistCrush(); }
			else if ( pathInfo.matches( "/.*?/" + AssetlistTickers.representation + ".*?" ) ) { rep = new AssetlistTickers(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistPortfolio.representation + ".*?" ) ) { rep = new AssetlistPortfolio(); }
			else if ( pathInfo.matches( "/.*?/" + AssetlistPerformanceStandalone.representation + ".*?" ) ) { rep = new AssetlistPerformanceStandalone(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistPerformance.representation + ".*?" ) ) { rep = new AssetlistPerformance(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistFutures.representation + ".*?" ) ) { rep = new AssetlistFutures(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistIndices.representation + ".*?" ) ) { rep = new AssetlistIndices(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistForex.representation + ".*?" ) ) { rep = new AssetlistForex(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistMulti.representation + ".*?" ) ) { rep = new AssetlistMulti(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistCherrypicks.representation + ".*?" ) ) { rep = new AssetlistCherrypicks(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistPorter.representation + ".*?" ) ) { rep = new AssetlistPorter(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistViper.representation + ".*?" ) ) { rep = new AssetlistViper(); }
			else if ( pathInfo.matches( "/.*?/" + AssetlistRules.representation + ".*?" ) ) { rep = new AssetlistRules(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistReport.representation + ".*?" ) ) { rep = new AssetlistReport(); }
	    	else if ( pathInfo.matches( "/.*?/" + AssetlistStratvol.representation + ".*?" ) ) { rep = new AssetlistStratvol(); }	
//	    	report( request, resource );

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
//    	Log.infoln( "Found dispatch rep=" + rep.representation );
	    	
    	return rep;
    }

}
