package leafspider.spider;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;

import leafspider.util.*;

public class SpiderFolder 
{
	public URI getSpideredDocumentUri(File theFile)
	{
		URI result = null;
		try
		{
			String outputFolder = getSpiderCollectionFolder();			
			String currentURL = "http://" + theFile.getAbsolutePath().substring(theFile.getAbsolutePath().indexOf(outputFolder) + outputFolder.length());
			currentURL = currentURL.replace("\\", "/");
			if ( theFile.getName().equalsIgnoreCase( "index.html" ) )
			{
//				System.out.println( "Spidered file=" + theFile.getAbsolutePath() );		// C:\CIRILab\ios\work\ios\data\http\temp\classifyCache\www.cirilab.com\index.html
				// Replace index.html with index.htm if necessary (spider bug)
				try
				{
					URL url = new URL( currentURL );
					URLConnection connection = url.openConnection();
					connection.getInputStream();
//					System.out.println( "connected" );
				}
				catch( Exception use )
				{
					currentURL = currentURL.replace("html", "htm");
//					System.out.println( "Not connected" );
				}
			}			
			result = new URI(currentURL);
		}
		catch (URISyntaxException use)
		{
			Log.warnln("Exception: ", use);
		}
		return result;
	}
	
	public boolean isSpideredFile( File file )
	{
//		Log.infoln( "" + file.getAbsolutePath().replace( "/", "\\" ) + "=" + getSpiderCollectionFolder() );
		if ( file.getAbsolutePath().replace( "/", "\\" ).indexOf( getSpiderCollectionFolder() ) > -1 )
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	private String spiderCollectionFolder = null;
	public String getSpiderCollectionFolder()
	{
		if( spiderCollectionFolder == null )
		{
			spiderCollectionFolder = ServerContext.getDataFolder() + "\\spider\\";
		}
		return spiderCollectionFolder;
	}
	public void setSpiderCollectionFolder(String spiderCollectionFolder) {
		this.spiderCollectionFolder = spiderCollectionFolder;
	}
	
}
