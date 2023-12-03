package leafspider.util;
import java.io.File;
import java.io.BufferedReader;
import java.net.URL;
import java.net.URLConnection;

public class FileIdentifier
{
	private File file;
	private String fileName;
	private String extension;
	private String typeExtension;
	private String contentEncoding;
	private String headerField;	
	private static String unidentifiedExtension = "htm";
	
	public FileIdentifier( File thisFile ) { setFile( thisFile ); }
	public static void main ( String[] args )
	{
	    try
	    {
			String thisFileName = "C:\\TestDocs\\http_trout1.bsjdhfbsf";
			FileIdentifier fid = new FileIdentifier( new File( thisFileName ) );
			
			System.out.println( "Extension=" + fid.getExtension() );		
			System.out.println( "TypeExtension=" + fid.getTypeExtension() );		
		}
		catch( Exception e ) { e.printStackTrace(); }
	}
	
	public File getFile() { return file; }
	private void setFile( File thisFile ) { file = thisFile; fileName = file.getPath(); setExtension(); }
	
	public String getExtension()
	{
		if ( extension == null ) { setExtension(); }
		return extension;
	}
	private void setExtension()
	{
		try { extension = Util.getFileExtension( fileName ); }
		catch( Exception e ) { Log.warnln("Exception: ", e); }
	}	
	
	public String getTypeExtension()
	{
		if ( typeExtension == null ) { setTypeExtension(); }
		return typeExtension;
	}
	private void setTypeExtension()
	{
		try
		{
			// jmh Note Util method truncates extension at 4 chars
			String ext = Util.getFileExtension( fileName );			
			if ( ext.equalsIgnoreCase( "HTM" ) || ext.equalsIgnoreCase( "PDF" ) || ext.equalsIgnoreCase( "DOC" ) || ext.equalsIgnoreCase( "TXT" ) ) { typeExtension = ext; }
			else if ( ext.equalsIgnoreCase( "HTML" ) || ext.equalsIgnoreCase( "ASP" ) || ext.equalsIgnoreCase( "PHP" ) ) { typeExtension = "htm"; }
			else { typeExtension = getTypeExtensionFromContents(); }
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); }
	}
	
	public synchronized static String getTypeExtensionFromUrl( String thisUrl )
	{
		int pos = thisUrl.indexOf("?");
		if( pos > -1 ) { thisUrl = thisUrl.substring(0,pos); }
//		Log.infoln("thisUrl=" + thisUrl );
		// jmh In use until we can solve the problem of interrupting Threads properly to allow Content interrogation
		String ext = Util.getFileExtension( thisUrl );
		if ( ext.equalsIgnoreCase( "HTM" ) || ext.equalsIgnoreCase( "PDF" ) || ext.equalsIgnoreCase( "DOC" ) || ext.equalsIgnoreCase( "TXT" ) || ext.equalsIgnoreCase( "XML" ) ) { return ext; }
		else if ( ext.equalsIgnoreCase( "HTML" ) || ext.equalsIgnoreCase( "SHTML" ) || ext.equalsIgnoreCase( "ASP" ) || ext.equalsIgnoreCase( "PHP" ) ) { return "htm"; }
		else { return unidentifiedExtension; }
	}
	
	public synchronized static String getTypeExtensionFromFile( File thisFile )
	{
		BufferedReader reader = null;
		try
		{
			reader = Util.getBufferedReader( thisFile );
			String ext = "";
			String line;
			for( int i = 0; i < 10; i++ )			// Only read first 10 lines
			{
				line = reader.readLine();
//				Log.info( line );
				ext = identifyExtensionFromLine( line );
				if ( !ext.equals( "" ) ) { return ext; }
			}
			return unidentifiedExtension;
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); }
		finally
		{
			try
			{
				if (reader != null) 
					reader.close();
			}
			catch(java.io.IOException ioe)
			{}
		}
		return "";
	}
	
	private String getEncoding( URL thisUrl )
	{
		try
		{
			URL url = thisUrl;
			URLConnection connection = url.openConnection();
			String encoding = connection.getContentEncoding();
			return encoding;
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); return null; }
	}
	
	private String getTypeExtensionFromContents()
	{
		BufferedReader reader = null;
		try
		{
			reader = Util.getBufferedReader( getFile() );
			String ext = "";
			String line;
			for( int i = 0; i < 10; i++ )				// Only read first 10 lines
			{
				line = reader.readLine();
//				Log.info( line );
				ext = identifyExtensionFromLine( line );
				if ( !ext.equals( "" ) ) { return ext; }
			}
			return "txt";
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); }
		finally
		{
			try
			{
				if (reader != null) 
					reader.close();
			}
			catch(java.io.IOException ioe)
			{}
		}
		return null;
	}
	
	private synchronized static String identifyExtensionFromLine( String thisLine )
	{
		String line = thisLine.toUpperCase();
		if ( line == null || line.equals( "" ) ) { return ""; }
		if ( line.indexOf( "<HTML>" ) > -1 ) { return "htm"; }
		if ( line.indexOf( "<HEAD>" ) > -1 ) { return "htm"; }
		if ( line.indexOf( "<BODY>" ) > -1 ) { return "htm"; }
		return unidentifiedExtension;
	} 
	
	/*
	public String getHeaderField() { }
	*/
	
}

