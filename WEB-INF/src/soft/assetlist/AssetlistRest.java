package soft.assetlist;

import leafspider.rest.Rest;
import leafspider.util.Util;

public class AssetlistRest extends Rest {

    public void parsePathInfo( String pathInfo ) {

        String[] vals = pathInfo.split( "/" );
        if ( vals.length > 1 ) {
            setProject( Util.removeFileExtension( vals[1] ) );
        }
        if ( vals.length > 2 ) {
            setResourceId( vals[2].toLowerCase() );
        }
        else {
            setResourceId( null );
        }
        setExtension( Util.getFileExtension( vals[vals.length-1] ) );
    }
}
