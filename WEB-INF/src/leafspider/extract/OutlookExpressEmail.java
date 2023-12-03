package leafspider.extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.PrintStream;

import leafspider.util.*;

/** 
 * @author Mark Hurst 
 */
public class OutlookExpressEmail extends OutlookEmail
{
	public static void main(String[] args)
	{
		try
		{
	    	String dirName = "D:\\Cher\\Carl\\msg";
//	    	String fileName = "The Gurteen Knowledge-Letter (Issue 53  1 November 2004)  ##8894##.msg";
//	    	String fileName = "AttachementTXT.msg";
//	    	String fileName = "Managing Collective Intelligence.msg";
	    	String fileName = "Re_ GOF between Word and PDF version of the same doc.eml";	    	
	    	String sourceFileName = dirName + "\\" + fileName;
	    	
	    	File folder = new File( dirName );
	    	String[] files = folder.list();
	    	
	    	for ( int i = 0; i < files.length; i++ )
	    	{
		    	sourceFileName = folder.getAbsolutePath() + "\\" + files[i];
		    	if ( Util.getFileExtension( sourceFileName ).equalsIgnoreCase( "EML" ) )
		    	{
		    		String textFileName = sourceFileName + ".txt";
		    		OutlookExpressEmail.extractEmailBody( sourceFileName, textFileName );
		    	}
	    	}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static boolean extractEmailBody( String sourceFileName, String textFileName )
	{
		BufferedReader in = null;
		PrintStream out = null;
		try
		{
			in = Util.getBufferedReader( sourceFileName );
			out = Util.getPrintStream( textFileName );
			
			String txt = null;
			String partName = "^%*^&@#%$(@&#$^#$@#";
			boolean isMultipart = false;
			boolean writing = false;
			while( ( txt = in.readLine() ) != null )
			{
				if ( txt.indexOf( "Content-Type: multipart/mixed;" ) == 0 )
				{
					isMultipart = true;
				}
				if ( !isMultipart )
				{
					if ( txt.indexOf( "Content-Type: text/plain;" ) == 0 )
					{
						writing = true;
						continue;
					}
				}
			    if ( isMultipart && txt.indexOf( "------=_NextPart_" ) > -1 )
			    {
					if ( txt.equals( partName ) )
					{
						writing = false;
					}
					else
					{
						partName = txt;
						writing = true;
					}
			    	continue;
			    }
			    if ( writing )
			    {
					out.println( txt );
			    }
			}
			stripMetaData( textFileName );
			return true;
		}
		catch( Exception e )
		{
			Log.infoln( sourceFileName );
			e.printStackTrace();
			return false;
		}
		finally
		{
			out.close();
		}
	}
	
}
