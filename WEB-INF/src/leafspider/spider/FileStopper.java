package leafspider.spider;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import leafspider.util.*;

public class FileStopper
{
	public static void main(String[] args)
	{
		try
		{
//			File file = new File( "C:\\Temp\\mindjet\\test.html");
			File file = new File( "C:\\CIRILab\\kgs\\work\\read\\itw\\http\\spiderCache\\www.spafax.com\\about");
			System.out.println( file.length() );
			System.out.println( file.exists() );
			System.out.println( file.isDirectory() );
			System.out.println( file.isFile() );
			System.out.println( file.getAbsolutePath() );
//			
			System.out.println( FileStopper.getDocType( file ) );
//			System.out.println( FileStopper.isSupported( file ) );
		}
		catch( Exception e ) { e.printStackTrace(); }
	}

	private static ArrayList supportedFiles = null;
	private static ArrayList unsupportedFiles = null;

	public synchronized static boolean isSupported(File file)
	{
//		Log.infoln( "FileStopper.isSupported file=" + file.getAbsolutePath() );
		try
		{ 	
//			if ( !file.exists() ) { return false; } 	// jmh 2009-07-28
			if ( file.isDirectory() ) { return false; } // jmh 2009-07-28
			if ( file == null ) { return false; }		// jmh 2006-07-14
			
			// Load list from file
			if (supportedFiles == null || supportedFiles.size() == 0)
			{
				readSupportedFiles();
			}
	
			String filename = file.getName();
			if (filename.equals("") || filename.charAt(0) == '.')
			{
//				Log.infoln( "FileStopper.isSupported empty filename" );
//				return false;		// jmh 2010-06-21 May be HTML with no extension 
			}
			
			// jmh 20009-08-26 Handle ? from downloaded URL querystring
			int pos = filename.indexOf("?");
			if ( pos > 0 )
			{
				filename = filename.substring(0, pos);
			}
	
			// Only allow extensions from 1 to 5 characters
			String ext = Util.getFileExtension(filename);
//			Log.infoln( "FileStopper.isSupported ext=" + ext );
			if (ext.length() < 1 || ext.length() > 5)
			{	
				if ( FileStopper.getDocType( file ).equalsIgnoreCase( "html" ) )
				{
					return true;
				}
				return false;
			}

			if ( ext.length() > 3 )		// jmh 2010-09-19 Hack for querystrings where ? has been replaced by - eg php?b=blah 
			{
				if ( ext.indexOf( "-" ) == 3 )
				{
					ext = ext.substring(0, 3); 
					if (supportedFiles.contains(("." + ext).toLowerCase()))
					{
//						Log.infoln( "ext=" + ext );
						return true;
					}
				}
			}
			
			if (supportedFiles.contains(("." + ext).toLowerCase()))
			{
//				Log.infoln( "FileStopper.isSupported true" );
				return true;
			}
			
		}
		catch( Exception e )
		{
			Log.warnln( e.getMessage() );
			e.printStackTrace();
		}

		//Log.infoln( "supportedFiles.size=" + supportedFiles.size() );
//		Log.infoln( "FileStopper.isSupported false" );
		return false;
	}

	private static void readSupportedFiles()
	{
		BufferedReader inputFile = null;
		supportedFiles = new ArrayList();
		try
		{
			inputFile = Util.getBufferedReader(ServerContext.getConfigFolder() + "\\supportedFiles.txt");
			String txt;
//			supportedFiles.add( "" );		// jmh 2006-05-22
			while ((txt = inputFile.readLine()) != null)
			{
				supportedFiles.add(txt.toLowerCase().trim());
			}
			inputFile.close();
		}
		catch (IOException e)
		{
			Log.warnln("IOException: ", e);
		}
		finally
		{
			try
			{
				if (inputFile != null)
				{
					inputFile.close();
				}
			}
			catch (NullPointerException npe)
			{}
			catch (final IOException ex)
			{
				Log.infoln("IOException", ex);
			}
		}
	}

	public static String getSupportedFilesAsString(String delim)
	{
		if (supportedFiles == null)
		{
			readSupportedFiles();
		}

		String result = new String();

		Iterator iter = supportedFiles.iterator();
		while (iter.hasNext())
		{
			result += iter.next() + delim;
		}

		return result;
	}

	public static boolean isUnsupported(File file)
	{
		// Load list from file
		if (unsupportedFiles == null)
		{
			readUnsupportedFiles();
		}

		String filename = file.getName();

		if (filename.equals("") || filename.charAt(0) == '.')
		{
			return false;
		}

		// Only allow extensions from 1 to 5 characters
		String ext = Util.getFileExtension(filename);
		if (ext.length() < 1 || ext.length() > 5)
		{
			return false;
		}

		if (unsupportedFiles.contains(("." + ext).toLowerCase())) return true;

		return false;
	}

	private static void readUnsupportedFiles()
	{
		BufferedReader inputFile = null;
		unsupportedFiles = new ArrayList();
		try
		{
			inputFile = Util.getBufferedReader(ServerContext.getConfigFolder() + "\\unsupportedFiles.txt");
			String txt;
			while ((txt = inputFile.readLine()) != null)
			{
				unsupportedFiles.add(txt.toLowerCase().trim());
			}
		}
		catch (IOException e)
		{
			Log.warnln("IOException: ", e);
		}
		finally
		{
			try
			{
				if (inputFile != null)
				{
					inputFile.close();
				}
			}
			catch (NullPointerException npe)
			{}
			catch (final IOException ex)
			{
				Log.infoln("IOException", ex);
			}
		}
	}

	public static String getUnsupportedFilesAsString(String delim)
	{
		if (unsupportedFiles == null)
		{
			readUnsupportedFiles();
		}

		String result = new String();

		Iterator iter = unsupportedFiles.iterator();
		while (iter.hasNext())
		{
			result += iter.next() + delim;
		}

		return result;
	}

	/**
	 * @return Returns the supportedFiles.
	 */
	public static ArrayList getSupportedFiles()
	{
		if (supportedFiles == null)
		{
			readSupportedFiles();
		}
		return supportedFiles;
	}

	/**
	 * @param supportedFiles The supportedFiles to set.
	 */
	public static void setSupportedFiles(ArrayList supportedFiles)
	{
		FileStopper.supportedFiles = supportedFiles;
	}

	/**
	 * @return Returns the unsupportedFiles.
	 */
	public static ArrayList getUnsupportedFiles()
	{
		if (unsupportedFiles == null)
		{
			readUnsupportedFiles();
		}
		return unsupportedFiles;
	}

	/**
	 * @param unsupportedFiles The unsupportedFiles to set.
	 */
	public static void setUnsupportedFiles(ArrayList unsupportedFiles)
	{
		FileStopper.unsupportedFiles = unsupportedFiles;
	}
	
	public static String getDocType( File file )
	{
		String type = "";
		if ( file.exists() )		// jmh 2010-06-16
		{
			try
			{
				Iterator lines = Util.getArrayListFromFile( file.getAbsolutePath() ).iterator();
				String typeTag = "<!DOCTYPE";
				while( lines.hasNext() )
				{
					String line = (String) lines.next();
					int pos1 = line.indexOf( typeTag );
					if ( pos1 > -1 )
					{
						/* jmh 2010-06-21
						int pos2 = line.indexOf( " ", pos1 + typeTag.length() );
						type = line.substring( pos1 + typeTag.length(), pos2 );
						*/
						int pos2 = line.lastIndexOf( ">" );
						type = line.substring( pos1 + typeTag.length() + 1, pos2 );
						break;
					}
				}
			}
			catch( Exception e ) { e.printStackTrace(); }
		}
		return type;
	}
}
