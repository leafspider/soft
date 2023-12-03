package soft.tsocial;

import leafspider.rest.Rest;
import leafspider.util.Util;
import leafspider.util.Log;

public class TsocialRest extends Rest {

    public void parsePathInfo( String pathInfo )
    {
        // /tsocial/jake/<handle>/grid.htm
        String[] vals = pathInfo.split( "/" );

//    	Log.infoln( "TsocialRest pathInfo=" + pathInfo );
//		Log.infoln( "vals.length=" + vals.length );
//    	for ( int i = 0; i < vals.length; i++ ) { Log.infoln( "vals=" + vals[i] ); }

        // vals[0] is blank
        if ( vals.length > 1 ) {
            setProject( Util.removeFileExtension( vals[1] ) );
        }
        if ( vals.length > 2 ) {
//    		setResourceId( Util.removeFileExtension( vals[2] ).toUpperCase() );
            setResourceId( vals[2].toUpperCase() );
        }
        else {
            setResourceId( null );
        }
        setExtension( Util.getFileExtension( vals[vals.length-1] ) );
    }
}
