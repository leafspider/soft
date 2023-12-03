package leafspider.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.TreeSet;

import org.apache.commons.io.FileUtils;

public abstract class Util
{
	public static boolean debug = false;

	public static void main(String[] args)
	{
		try
		{
			System.out.println( getFilenameFromURL( new URL( "https://iphone.usatoday.com/" ) ) );
			System.out.println( getFilenameFromURL( new URL( "http://iphone.usatoday.com/" ) ) );
			/*
			System.out.println( getFilenameFromURL( new URL( "http://iphone.usatoday.com/detail.jsp?key=871918&rc=tr_so_cg_tr&p=2" ) ) );
			System.out.println( ServerContext.isShowHiddenFiles() );
			
			/*
			File folder = new File( "C:\\Catalog\\Shortcuts" );
			File[] files = folder.listFiles();
			PrintStream out = Util.getPrintStream( "C:\\Test\\out.txt" ); 
			for (int i = 0; i < files.length; i++ )
			{
				System.out.println( "File=" + files[i].getAbsolutePath() );
				System.out.println( Util.parseShortcutFile( files[i] ).getAbsolutePath() );
				System.out.println( "" );
			}
			out.close();
			
//			System.out.println( Util.removeNonUnicodeChars( "BONSULTATIONS050100150200250300350400450MAR" ) );
			/*
			Util.removeNonUnicodeCharsFromFile( new File( "D:\\Cher\\Jorge\\Copy of 1-design.htm" ) );
			
//			/*
			// "<H3>Ðåàëèçóþòñÿ ñëåäóþùèå îïòèìèçèðîâàííûå àäðåñà:</H3>"
			// String in = "f?Þ,Web‘f?Þ,–³—¿‘f?Þ,ƒtƒŠ?[,HP‘f?Þ,Web?§?ì,HP?§?ì,•ÇŽ†,”wŒi,ƒAƒCƒRƒ“,ƒ{ƒ^ƒ“,gifƒAƒjƒ??[ƒVƒ‡ƒ“ƒfƒUƒCƒ“,Design,apeskin,ŒfŽ¦”Â,ƒoƒi?[,ƒoƒi?[?§?ì,ƒI?[ƒ_?[‘f?Þ";

//			String filename = "D:\\KOSCatalog\\news_7\\export6.txt";
			String filename = "D:\\Cher\\Jorge\\1-design.htm";
			// "D:\\Cher\\Jorge\\Copy of 1-design.htm"
			BufferedReader reader = Util.getBufferedReader( filename );
			String txt = null;
			while ( ( txt = reader.readLine() ) != null )
			{
//				System.out.print( "\t" + txt.length() );
//				System.out.println( txt );
				txt = Util.removeNonUnicodeChars( txt );			
//				System.out.println( "\t" + txt.length() );
//				System.out.println( txt );
//				break;
			}
//			*/

			//System.out.println( "Finished" );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public synchronized static File removeNonUnicodeCharsFromFile( File file )
	{
		BufferedReader in = null;
		PrintStream out = null;
		try
		{
			File uniFile = new File( file.getAbsolutePath() + ".uni" );
			Util.copyFile( file, uniFile );
			in = Util.getBufferedReader( uniFile.getAbsolutePath() );
			out = Util.getPrintStream( file.getAbsolutePath() );
				
			String txt = null;
			while ( ( txt = in.readLine() ) != null )
			{
				out.println( Util.removeNonUnicodeChars( txt ) );
			}
			uniFile.delete();
			return file;
		}
		catch (Exception e)
		{
			Log.infoln("Exception: ", e);
			return null;
		}
		finally
		{
			try { in.close(); } catch ( Exception e ) {}
			if ( out != null ) { out.close(); }
		}
	}
	
	public synchronized static String removeNonUnicodeChars( String in )
	{
		try
		{
			in = removeHtmlTags( in );
			String regex = 	"[[^\\u0009\\u000A\\u000D]&&[^\\u0020-\\uD7FF]&&[^\\uE000-\\uFFFD]&&[^\\u10000-\\u10FFFF]]";			
			return in.replaceAll( regex, "" );
		}
		catch (Exception e)
		{
			Log.infoln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String removeHtmlTags( String in )
	{
		return in.replaceAll( "<.+?>", "" );	
	}
	
	/**
	 * Checks for a backslash at the end of the path and adds one if it doesn't exist
	 * @param path The path to check
	 * @return The string path with a backslash at the end of the path
	 */
	public synchronized static String addBackslashToPath(String path)
	{
		String result = new String(path);
		if (result.charAt(result.length() - 1) != '\\')
		{
			result = result.concat("\\");
		}
		return result;
	}

	/**
	 * Cleans directory names in 'directory' to be consistent with Util.getCleanFileName
	 * @param directory
	 */
	public synchronized static void cleanDirectoryName(File directory)
	{
		if (!directory.isDirectory())
		{
			return;
		}

		String[] files = directory.list();
		String thisDir = directory.getPath();

		// count non-directory files in this directory and recurse for each that IS a directory
		for (int i = 0; i < files.length; i++)
		{
			File f = new File(thisDir + File.separator + files[i]);
			if (f.isDirectory())
			{
				cleanDirectoryName(f);
				// Log.info("Renaming file " + f.getName() + " to " + f.getParentFile().getPath() + "\\" + Util.getCleanFileName(f.getName()));
				f.renameTo(new File(f.getParentFile().getPath() + "\\" + Util.getCleanFileName(f.getName())));
			}
		}
	}

	/**
	 * Counts the number of times 'ch' is contained in 'st'
	 * @param st The string to search
	 * @param ch The character to look for
	 * @return The number of times 'ch' is contained in 'st'
	 */
	public synchronized static int contains(String st, char ch)
	{
		int count = 0;
		for (int i = 0; i < st.length(); i++)
		{
			if (st.charAt(i) == ch)
			{
				count += 1;
			}
		}
		return count;
	}

	/**
	 * Counts the number of times 'sep' is contained in 'st'
	 * @param st The string to search
	 * @param sep The string to look for
	 * @return The number of times 'sep' is contained in 'st'
	 */
	public synchronized static int contains(String st, String sep)
	{
		if (sep == null || sep.equals("")) return 0;

		int count = 0;
		int pos = st.indexOf(sep);
		while (pos > -1)
		{
			if (pos > -1)
			{
				count += 1;
			}
			pos = st.indexOf(sep, pos + sep.length());
		}
		return count;
	}

	/**
	 * Converts the following characters in a string to their HTML equivalents: <space>, ", &, <, >
	 * @param string The string to convert
	 * @return A string with all characters that have corresponding HTML entities converted to those entities.
	 */
	public synchronized static String convertStringToHTMLString(String string)
	{
		StringBuffer sb = new StringBuffer(string.length());

		boolean lastWasBlankChar = false;
		int len = string.length();
		char c;

		for (int i = 0; i < len; i++)
		{
			c = string.charAt(i);
			if (c == ' ')
			{
				// blank gets extra work,
				// this solves the problem you get if you replace all
				// blanks with &nbsp;, if you do that you loss 
				// word breaking
				if (lastWasBlankChar)
				{
					lastWasBlankChar = false;
					sb.append("&nbsp;");
				}
				else
				{
					lastWasBlankChar = true;
					sb.append(' ');
				}
			}
			else
			{
				lastWasBlankChar = false;
				//
				// HTML Special Chars
				if (c == '"')
					sb.append("&quot;");
				else if (c == '&')
					sb.append("&amp;");
				else if (c == '<')
					sb.append("&lt;");
				else if (c == '>')
					sb.append("&gt;");
				else if (c == '\n')
					// Handle Newline
					sb.append("&lt;br/&gt;");
				else
				{
					int ci = 0xffff & c;
					if (ci < 160)
						// nothing special only 7 Bit
						sb.append(c);
					else
					{
						// Not 7 Bit use the unicode system
						sb.append("&#");
						sb.append(new Integer(ci).toString());
						sb.append(';');
					}
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Copies all files and subdirectories from fromDir to toDir
	 * @param fromDir The source directory
	 * @param toDir The desination directory
	 */
	public synchronized static void copyAllFiles(File fromDir, File toDir)
	{
		copyFiles(fromDir, toDir, true);
	}

	/**
	 * Copies a file from one destination to another
	 * @param inFile The source file
	 * @param outFile The destination file
	 * @throws Exception
	 */
	public synchronized static void copyFile(File inFile, File outFile) throws Exception
	{
		FileCopy.copy(inFile.getAbsolutePath(), outFile.getAbsolutePath());
	}

	/**
	 * Copies a file from one destination to another
	 * @param inFileName The source filename
	 * @param outFileName The destination filename
	 * @throws java.io.IOException
	 */
	public synchronized static void copyFile(String inFileName, String outFileName) throws java.io.IOException
	{
		try
		{
			if (fileExists(removeFileNameFromPath(outFileName)))
			{
				copyFile(new File(inFileName), new File(outFileName));
			}
			else
			{
				Log.infoln("ERROR: Util.copyFile: Bad CopyTo File Folder Path: " + outFileName);
			}
		}
		catch (IOException ioe)
		{
			throw ioe;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public synchronized static boolean copyFiles(File fromDir, File toDir, boolean includeFolders)
	{
		Log.infoln("Copying files from " + fromDir.getAbsolutePath() + " to " + toDir.getAbsolutePath());

		try
		{
			String[] fileListNames = fromDir.list();
			if (fileListNames == null) return true;

			File[] fileList = new File[fileListNames.length];
			File thisFile;
			File newDir;

			for (int i = 0; i < fileListNames.length; i++)
			{
				fileList[i] = new File(fromDir.getPath() + File.separator + fileListNames[i]);
			}

			if (!toDir.isDirectory())
			{
				toDir.mkdirs();
			}

			for (int i = 0; i < fileList.length; i++)
			{
				thisFile = fileList[i];
				if (thisFile.isDirectory())
				{
					if (includeFolders)
					{
						newDir = new File(toDir.getCanonicalPath() + File.separator + thisFile.getName());
						newDir.mkdir();
						copyAllFiles(thisFile, newDir);
					}
				}
				else
				{
					copyFile(thisFile, new File(toDir.getPath() + File.separator + thisFile.getName()));
				}
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
		return true;
	}

	public synchronized static boolean copyAllFilesToFolder(java.io.File inDir, java.io.File outDir)
	{
		try
		{
			if ( !inDir.isDirectory() )
			{
				Log.infoln( "Source is not a folder: " + inDir.getAbsolutePath() );
				return false;
			}
			if (!outDir.isDirectory())
			{
				outDir.mkdirs();
			}

//			Log.infoln("in=" + inDir.getPath());
//			Log.infoln("out=" + outDir.getPath());

			File[] files = inDir.listFiles();
			for ( int i = 0; i < files.length; i++ )
			{
				if ( !files[i].isDirectory() )
				{
					copyFileToFolder( files[i], outDir ); 
				}
			}
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	public synchronized static boolean copyFileToFolder(java.io.File inFile, java.io.File outDir)
	{
		try
		{
			if (!outDir.isDirectory())
			{
				outDir.mkdirs();
			}
			java.io.InputStream in = new java.io.FileInputStream(inFile);
			java.io.File outFile = new java.io.File(outDir + "\\" + inFile.getName());
//			Log.infoln("in=" + inFile.getPath() + " out=" + outFile.getPath());
			Log.infoln("out=" + outFile.getAbsolutePath());
			if (outFile.exists())
			{
//				Log.infoln("Replacing " + outFile.getName());
				outFile.delete();
			}
			java.io.FileOutputStream out = new java.io.FileOutputStream(outFile);
			int ch;
			while ((ch = in.read()) != -1)
			{
				out.write(ch);
			}
			in.close();
			out.close();
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	/**
	 * Creates a directory
	 * @param path The pathname of the directory to create
	 */
	public synchronized static void createDirectory(String path)
	{
		if (path == null || path.length() == 0) return;

		File dir = new File(path);
		try
		{
			if (dir.exists()) return;

			if (dir.mkdirs())
			{
				return;
			}
			else
			{
				Log.infoln("Warning: Unable to create directory: " + path);
			}
		}
		catch (Exception exc)
		{
			Log.warnln("Exception: " + exc);
			return;
		}
	}

	/**
	 * @deprecated Use Log instead
	 * @param e
	 * @param st
	 */
	public synchronized static void debug(Exception e, String st)
	{
		Log.infoln(e.toString() + " in " + st);
	}

	/**
	 * @deprecated Use Log instead
	 * @param pw
	 * @param e
	 * @param st
	 */
	public synchronized static void debug(java.io.PrintWriter pw, Exception e, String st)
	{
		Log.infoln(e.toString() + " in " + st);
		pw.println(e.toString() + " in " + st + "<br>");
	}

	/**
	 * @deprecated Use Log instead
	 * @param pw
	 * @param st
	 */
	public synchronized static void debug(java.io.PrintWriter pw, String st)
	{
		Log.infoln(st);
		pw.println(st + "<br>");
	}

	/**
	 * @deprecated Use Log instead
	 * @param st
	 */
	public synchronized static void debug(String st)
	{
		Log.infoln(st);
	}

	/**
	 * URL-Decodes a string
	 * @param txt The string to URL decode
	 * @return A string that has been URL-Decoded by the Java URLDecoder
	 */
	public synchronized static String decodeURL(String txt)
	{
		if (txt == null) return null;
		try
		{
			return java.net.URLDecoder.decode(txt, "UTF-8");
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static void deleteAllFiles(File fromDir)
	{
		try
		{
			if (fromDir.isDirectory())
			{
				String[] fileListNames = fromDir.list();
				File[] fileList = new File[fileListNames.length];
				for (int i = 0; i < fileListNames.length; i++)
				{
					fileList[i] = new File(fromDir.getPath() + File.separator + fileListNames[i]);
				}
				//				java.io.File[] fileList = fromDir.listFiles();	// jmh Java version too new
				for (int i = 0; i < fileList.length; i++)
				{
					/* jmh 2011-05-06
					if (!fileList[i].delete())
					{
						// Log.infoln( "Could not delete " + fileList[i].getName() ); 
					}
					else
					{
						Log.debugln("Deleted file: " + fileList[i].getName());
					}
					*/
					deleteFile( fileList[i] );		// jmh 2011-05-06
				}
			}
			else
			{
				throw new Exception("" + fromDir.toString() + " is not a directory.");
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public synchronized static void deleteAllFiles(File fromDir, boolean deleteFolders)
	{
		try
		{
			if (fromDir.isDirectory() == true)
			{
				String[] fileListNames = fromDir.list();
				if ( fileListNames != null )		// jmh 2011-01-06
				{
					File[] fileList = new File[fileListNames.length];
					for (int i = 0; i < fileListNames.length; i++)
					{
						fileList[i] = new File(fromDir.getPath() + File.separator + fileListNames[i]);
					}
					//				java.io.File[] fileList = fromDir.listFiles();	// jmh Java version too new
					for (int i = 0; i < fileList.length; i++)
					{
						//					if ( !deleteFolders && fileList[i].isDirectory() ) { continue; }
						if (fileList[i].isDirectory())
						{
							if (!deleteFolders)
							{
								continue;
							}
							else
							{
								deleteAllFiles(fileList[i], deleteFolders);
							}
						}
						/* jmh 2011-05-06
						if (!fileList[i].delete())
						{
							// Log.infoln( "Could not delete " + fileList[i].getName() ); 
						}
						else
						{
							Log.debugln("Deleted file: " + fileList[i].getName());
						}
						*/
						deleteFile( fileList[i] );		// jmh 2011-05-06
					}
				}
			}
			else
			{
				throw new Exception("" + fromDir.toString() + " is not a directory.");
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public synchronized static boolean deleteAllFilesFromFolder(File fromDir)
	{
		try
		{
			File thisFile;
			if (fromDir.isDirectory() == true)
			{
				String[] fileList = fromDir.list();
				for (int i = 0; i < fileList.length; i++)
				{
					thisFile = new File(fromDir.getPath() + "\\" + fileList[i]);
					if (!thisFile.delete())
					{
						throw new Exception(thisFile.getName() + " could not be deleted.");
					}
				}
			}
			else
			{
				throw new Exception("" + fromDir.toString() + " is not a directory.");
			}
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	/**
	 * Deletes a file.  If the file is in use, it is set to delete on exit. 
	 * @param theFile The file to delete
	 * @return True if file was immediately deleted.  False if file was not deleted, or does not exist.
	 */
	public synchronized static boolean deleteFile(File theFile)
	{
		if (theFile == null || !theFile.exists())
		{
			//Log.infoln("Cannot delete file: " + theFile.getAbsolutePath() + " [File Null/DNE]");
			return false;
		}

		boolean delRes = theFile.delete();
		if (!delRes)
		{
			/*
			 Log.infoln("\t\tDelete failed: " + theFile.getAbsolutePath());
			 Report.reportFileHandles("", "D:\\Handles_" + theFile.getName() + ".log");
			 try
			 {
			 Report.reportThreads(Util.getPrintStream("D:\\Threads_" + theFile.getName() + ".log"));
			 }
			 catch (Exception e)
			 {}
			 */
			theFile.deleteOnExit();
		}

		return delRes;
	}

	/**
	 * Completely deletes directory 'fromDir' from the filesystem. 
	 * @param fromDir The directory to remove
	 * @return
	 */
	public synchronized static boolean deleteFolder(File fromDir)
	{
		try
		{
			if (fromDir.isDirectory())
			{
				//deleteAllFilesFromFolder(fromDir);
				deleteAllFiles(fromDir, true);
				if (!fromDir.delete())
				{
					throw new Exception(fromDir.getName() + " could not be deleted.");
				}
			}
			else
			{
				throw new Exception("" + fromDir.toString() + " is not a directory.");
			}
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	public synchronized static boolean deleteFolderOnExit(File fromDir)
	{
		try
		{
			if (fromDir.isDirectory())
			{
				deleteAllFiles(fromDir, true);
				if (!fromDir.delete())
				{
					fromDir.deleteOnExit();
				}
			}
			/* jmh 2011-05-06
			else
			{
				throw new Exception("" + fromDir.toString() + " is not a directory.");
			}
			*/
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	/**
	 * Deletes temporary files created by Cimmitry from the %TEMP% directory.
	 * @param numMinutes - The number of minutes old that the file can be (at most) in order to be deleted
	 */
	public synchronized static void deleteImagesFromTempFolder( File temp, int numMinutes)
	{
		/*jmh 2008-02-31
		String tempFolder = null;
		try
		{
			tempFolder = System.getenv("temp");
		}
		catch (Exception e)
		{}
		*/

		if ( temp == null || !temp.isDirectory() ) return;

//		File temp = new File(tempFolder);
		ArrayList fileTypes = new ArrayList();
		fileTypes.add("bmp");
		fileTypes.add("emf");
		fileTypes.add("jpg");
		fileTypes.add("pic");
		fileTypes.add("png");
		fileTypes.add("tmp");
		fileTypes.add("wmf");

		File[] tempFiles = temp.listFiles();
		long currentTime = new Date().getTime();
		long cutoffInterval = numMinutes * 60 * 1000;

		for (int i = 0; i < tempFiles.length; i++)
		{
			File currentFile = tempFiles[i];
			String currentFilename = currentFile.getName();
			if (currentFilename.indexOf("pn") == 0)
			{
				String currentExt = Util.getFileExtension(currentFile);
				if (fileTypes.contains(currentExt.toLowerCase()))
				{
					long lastMod = currentFile.lastModified();
					if ((currentTime - lastMod) < cutoffInterval) currentFile.delete();
				}
			}
		}
	}

	/**
	 * URL-Encodes a string
	 * @param txt The string to URL encode
	 * @return A string that has been URL-Encoded by the Java URLEncoder
	 */
	public synchronized static String encodeURL(String txt)
	{
		if (txt == null) return null;
		try
		{
			return java.net.URLEncoder.encode(txt, "UTF-8");
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	/** 
	 * Replace Accented characters with their HTML escape sequence for Browser usability
	 * @param thisText The text to escape
	 */
	public synchronized static String escapeAccents(String thisText)
	{
		String[] character =
		{ "á", "Á", "â", "Â", "à", "À", "å", "Å", "ã", "Ã", "ä", "Ä", "æ", "Æ", "ç", "Ç", "ð", "Ð", "é", "É", "ê", "Ê", "è", "È", "ë", "Ë", "í", "Í", "î", "Î", "ì", "Ì", "ï", "Ï", "ñ", "Ñ", "ó", "Ó", "ô", "Ô", "ò", "Ò", "ø", "Ø", "õ", "Õ", "ö", "Ö", "ß", "þ", "Þ", "ú", "Ú", "û", "Û", "ù", "Ù", "ü", "Ü", "ý", "Ý", "ÿ" };
		String[] escape =
		{ "&aacute;", "&Aacute;", "&acirc;", "&Acirc;", "&agrave;", "&Agrave;", "&aring;", "&Aring;", "&atilde;", "&Atilde;", "&auml;", "&Auml;", "&aelig;", "&AElig;", "&ccedil;", "&Ccedil;", "&eth;", "&ETH;", "&eacute;", "&Eacute;", "&ecirc;", "&Ecirc;", "&egrave;", "&Egrave;", "&euml;", "&Euml;", "&iacute;", "&Iacute;", "&icirc;", "&Icirc;", "&igrave;", "&Igrave;", "&iuml;", "&Iuml;", "&ntilde;", "&Ntilde;", "&oacute;", "&Oacute;", "&ocirc;", "&Ocirc;", "&ograve;", "&Ograve;", "&oslash;", "&Oslash;", "&otilde;", "&Otilde;", "&ouml;", "&Ouml;", "&szlig;", "&thorn;", "&THORN;", "&uacute;", "&Uacute;", "&ucirc;", "&Ucirc;", "&ugrave;", "&Ugrave;", "&uuml;", "&Uuml;", "&yacute;", "&Yacute;", "&yuml;" };
		String[] escapHelp =
		{ "small a, acute accent", "capital A, acute accent", "small a, circumflex accent", "capital A, circumflex accent", "small a, grave accent", "capital A, grave accent", "small a, ring", "capital A, ring", "small a, tilde", "capital A, tilde", "small a, dieresis or umlaut mark", "capital A, dieresis or umlaut mark", "small ae diphthong (ligature)", "capital AE diphthong (ligature)", "small c, cedilla", "capital C, cedilla", "small eth, Icelandic", "capital Eth, Icelandic", "small e, acute accent", "capital E, acute accent", "small e, circumflex accent", "capital E, circumflex accent", "small e, grave accent", "capital E, grave accent", "small e, dieresis or umlaut mark", "capital E, dieresis or umlaut mark", "small i, acute accent", "capital I, acute accent", "small i, circumflex accent", "capital I, circumflex accent", "small i, grave accent", "capital I, grave accent", "small i, dieresis or umlaut mark", "capital I, dieresis or umlaut mark", "small n, tilde",
				"capital N, tilde", "small o, acute accent", "capital O, acute accent", "small o, circumflex accent", "capital O, circumflex accent", "small o, grave accent", "capital O, grave accent", "small o, slash", "capital O, slash", "small o, tilde", "capital O, tilde", "small o, dieresis or umlaut mark", "capital O, dieresis or umlaut mark", "small sharp s, German (sz ligature)", "small thorn, Icelandic", "capital THORN, Icelandic", "small u, acute accent", "capital U, acute accent", "small u, circumflex accent", "capital U, circumflex accent", "small u, grave accent", "capital U, grave accent", "small u, dieresis or umlaut mark", "capital U, dieresis or umlaut mark", "small y, acute accent", "capital Y, acute accent", "small y, dieresis or umlaut mark" };

		for (int j = 0; j < 61; j++)
		{
			thisText = replaceSubstring(thisText, character[j], escape[j]);
			//		 Log.info("Replace [" + character[j]+"] with ["+escape[j]+"]");
		}
		return thisText;
	}

	/**
	 * Inserts escape characters in a text segment where non-printable HTML/Java/XML characters appear - for HTTP publishing
	 * @param txt
	 * @param tC
	 * @return A string in which all non-printable characters have been escaped 
	 */
	public synchronized static String escapeCharacter(String txt, String tC)
	{
		String rC;
		int lCount;
		String txta;
		boolean iChar;
		boolean lchar;
		int aQ;
		int aQ_start;
		int sC;
		rC = "\\";
		lCount = 0;
		int ReplaceTheCharacter = 1;
		boolean tCheck = false;
		sC = 1;
		boolean rChar = false;
		int Sc_start = 1;
		String EscapeCharacter = txt;

		//' Escape in-line Double Quotes (interfers with JavaScript)
		aQ = 1;
		iChar = false;
		aQ_start = 1;
		EscapeCharacter = txt;
		boolean tCcommaCheck = false;
		int tCcomma = 0;

		if (!tCcommaCheck)
		{
			tCcomma = EscapeCharacter.indexOf(",");
		}

		while (aQ > 0)
		{
			tCheck = true;
			aQ = EscapeCharacter.indexOf(tC);
			if (aQ > 0 && aQ != (tCcomma + 1))
			{
				//		txta=txta + EscapeCharacter.substring(aQ_start,aQ-1) + "\\" + EscapeCharacter.substring(aQ,1);
				txta = txt + EscapeCharacter.substring(aQ_start, aQ - 1) + "\\" + EscapeCharacter.substring(aQ, 1);
				EscapeCharacter = EscapeCharacter.substring(aQ + 1, EscapeCharacter.length() - aQ);
				iChar = true;
			}
			//ALREADYESCAPED:		
			lCount++;
			;
			if (lCount > 100)
			{
				break;
			}
		}
		//	EscapeCharacter=txta + EscapeCharacter;
		EscapeCharacter = txt + EscapeCharacter;

		return txt;
	}

	/**
	 * Tokenizes an array of strings
	 * @param inst The string to tokenize
	 * @param sep The delimiter that separates each string within 'inst'
	 * @return An array of strings
	 */
	public synchronized static String[] explodeString(String inst, char sep)
	{
		// Log.infoln( "Util.explodeString: sep=" + sep + " inst=" + inst );
		if (inst == null || inst.trim().equals(""))
		{
			return null;
		}
		String[] arr;
		int len, pos1, pos2, i;
		len = contains(inst, sep) + 1;
		if (inst.indexOf(sep) == 0)
		{
			len -= 1;
		}
		if (inst.lastIndexOf(sep) == inst.length() - 1)
		{
			len -= 1;
		}
		arr = new String[len];
		if (inst.indexOf(sep) == 0)
		{
			pos1 = 1;
		}
		else
		{
			pos1 = 0;
		}
		i = 0;
		while (pos1 > -1 && pos1 < inst.length() + 1)
		{
			pos2 = inst.indexOf(sep, pos1 + 1);
			if (pos2 < 0)
			{
				pos2 = inst.length();
			}
			if (pos1 == pos2)
			{
				break;
			}
			arr[i] = new String(inst.substring(pos1, pos2));
			pos1 = pos2 + 1;
			i += 1;
		}
		return arr;
	}

	public synchronized static String[] explodeString(String inst, String sep)
	{
		if (inst == null || inst.trim().equals("")) return null;

		/* MJM 2005-01-27 Testing out StringTokenizer
		 StringTokenizer st = new StringTokenizer(inst, sep);
		 String[] exploded = new String[st.countTokens()];
		 int i = 0;
		 while (st.hasMoreTokens())
		 {
		 exploded[i] = st.nextToken();
		 i++;
		 }
		 return exploded;
		 */

		inst = replaceSubstring(inst, sep + sep, sep + "&dblsep;" + sep);
		String[] arr;
		int len, pos1, pos2, i;
		len = contains(inst, sep) + 1;
		if (inst.indexOf(sep) == 0)
		{
			len -= 1;
		}
		if (inst.lastIndexOf(sep) == inst.length() - sep.length())
		{
			len -= 1;
		}
		arr = new String[len];
		if (inst.indexOf(sep) == 0)
		{
			pos1 = sep.length();
		}
		else
		{
			pos1 = 0;
		}
		i = 0;
		while (pos1 > -1 && pos1 < inst.length() + 1)
		{
			pos2 = inst.indexOf(sep, pos1 + 1);
			if (pos2 < 0)
			{
				pos2 = inst.length();
			}
			if (pos1 == pos2)
			{
				break;
			}
			String val = inst.substring(pos1, pos2);
			if (val.equals("&dblsep;"))
			{
				val = "";
			}
			arr[i] = val;
			pos1 = pos2 + sep.length();
			i += 1;
		}
		//		Log.info("explodeString() Array Size i = " + i);
		return arr;
	}

	/**
	 * Determines if a file exists
	 * @param fileName The filename to check
	 * @return true if file 'fileName' exists
	 */
	public synchronized static boolean fileExists(String fileName)
	{
		File thisFile = new File(fileName);
		if (thisFile.exists())
			return true;
		else
			return false;
	}

	public synchronized static boolean fileExists(String fileName, File directory)
	{
		if (!directory.exists()) return false;

		String[] fileList = directory.list();
		boolean exists = false;
		for (int i = 0; i < fileList.length; i++)
		{
			if (fileList[i].equals(fileName))
			{
				exists = true;
				break;
			}
		}
		return exists;
	}

	/* Should be Deprecated */
	public synchronized static Vector getAllFilesInFolder(File thisDir)
	{
		try
		{
			Vector allFiles = new Vector();

			File thisFile;
			if (thisDir.isDirectory())
			{
				String[] fileList = thisDir.list();
				for (int i = 0; i < fileList.length; i++)
				{
					thisFile = new File(thisDir.getPath() + fileList[i]);
					if (!thisFile.isDirectory())
					{
						// Log.info( "Adding " + thisFile.getName() );
						allFiles.addElement(thisFile);
					}
				}
			}
			else
			{
				throw new Exception(thisDir.toString() + " is not a directory.");
			}
			return allFiles;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static ArrayList getArrayListFromFile(String fileName) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			File file = new File(fileName);
//			Log.infoln("file.isDirectory()=" + file.isDirectory() );
//			Log.infoln("fileName=" + fileName );
			if (!file.exists())
			{
				throw new IOException("File not found: " + file.getPath());
			}
			reader = Util.getBufferedReader(file.getPath());
			ArrayList array = new ArrayList();
			String txt;
			while ((txt = reader.readLine()) != null)
			{
				array.add(txt);
			}
			reader.close();
			return array;
		}
		finally
		{
			try
			{
				reader.close();
			}
			catch (NullPointerException npe)
			{}
			catch (final IOException ex)
			{
				Log.infoln("Exception", ex);
			}
		}
	}

	public synchronized static BufferedReader getBufferedReader(File thisFile)
	{
		if (!thisFile.exists()) return null;

		try
		{
			return new java.io.BufferedReader(new java.io.FileReader(thisFile));
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static java.io.BufferedReader getBufferedReader(String thisFilePath)
	{
		try
		{
			return getBufferedReader(new java.io.File(thisFilePath));
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String getCleanFileName(String filename)
	{
		//		char specialChars[] = { ':', '/', '<', '>', '\\', '@', '|', ' ', '?', '#', ',', '\"', '\'', ';', '=','&','+','?'};									// apr 19 2003 dbm

		//jan 21 2004 dbm. added % as no valid  so convert
		char specialChars[] =
		{ '%', ':', '/', '<', '>', '\\', '@', '|', ' ', '?', '#', ',', '\"', '\'', ';', '=', '&', '+', '?', '(', ')', '[', ']', '{', '}' }; // jmh 2003-06-30
		//		char replacementChars[] = { '_', '_', '(', ')', '_', '_', '_', '_' , '_' , '_' , '_', '_', '_', '_', '_' , '_', '_', '_' };									// apr 19 2003 dbm 
		char replacementChars[] =
		{ '_', '_', '_', '(', ')', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_' }; // jmh 2003-06-30

		//		Log.info("GCFN: Getfilename"); 
		StringBuffer sb;
		int pos;
		/* jun 29 2003 dbm
		 pos = filename.lastIndexOf( "http" ); 
		 if ( pos > -1 ) { sb = new StringBuffer( filename.substring( pos ) ); }
		 else { sb = new StringBuffer( filename ); }
		 */
		sb = new StringBuffer(filename);
		//aug 9 2003 dbm. Leave in but nullify (=true) until we can work through the implications of removing it
		boolean replaceSpacesInFileNames = true; // aug 06 2003 dbm		// jmh 2003-08-08 Removed
		try
		{
			for (int i = 0; i < specialChars.length; i++)
			{
				//	Log.info("specialChars[" + i + "] = " + specialChars[ i ]);
				if (i == 7 && !replaceSpacesInFileNames)
				{
					continue;
				} // aug 06 2003 dbm		// jmh 2003-08-08 Removed
				pos = sb.toString().indexOf(specialChars[i], 0);
				while (pos > -1)
				{
					sb.setCharAt(pos, replacementChars[i]);
					pos = sb.toString().indexOf(specialChars[i], 0);
				}
			}
			if (sb.length() > 200)
			{
				return sb.toString().substring(0, 200);
			}
			else
			{ // Apr 19 2003 dbm
				//			Log.info("GCFN: final filename = " + sb.toString());  // Apr 19 2003 dbm
				return sb.toString(); // Apr 19 2003 dbm
			}
			//	 Apr 19 2003 dbm			else { return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	//jan 21 2004 dbm
	public synchronized static String getCleanFolderage(String filename)
	{
		//jan 21 2004 dbm. added % as no valid  so convert
		char specialChars[] =
		{ '%', ':', '<', '>', '@', '|', ' ', '?', '#', ',', '\"', '\'', ';', '=', '&', '+', '?', '(', ')', '[', ']', '{', '}' }; // jmh 2003-06-30
		char replacementChars[] =
		{ '_', '_', '_', '(', ')', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_' }; // jmh 2003-06-30
		StringBuffer sb;
		int pos;
		sb = new StringBuffer(filename);
		//aug 9 2003 dbm. Leave in but nullify (=true) until we can work through the implications of removing it
		boolean replaceSpacesInFileNames = true; // aug 06 2003 dbm		// jmh 2003-08-08 Removed
		try
		{
			for (int i = 0; i < specialChars.length; i++)
			{
				if (i == 7 && !replaceSpacesInFileNames)
				{
					continue;
				} // aug 06 2003 dbm		// jmh 2003-08-08 Removed
				pos = sb.toString().indexOf(specialChars[i], 0);
				while (pos > -1)
				{
					sb.setCharAt(pos, replacementChars[i]);
					pos = sb.toString().indexOf(specialChars[i], 0);
				}
			}
			if (sb.length() > 200)
			{
				return sb.toString().substring(0, 200);
			}
			else
			{ // Apr 19 2003 dbm
				return sb.toString(); // Apr 19 2003 dbm
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * Gets a list of all files located in 'thisFolder' and all subdirectories within
	 * @param thisFolder The folder the get a file listing for
	 * @return A string array of files in thisFolder.  Null if no files exist.
	 */
	public synchronized static String[] getCompleteFileList(File thisFolder)
	{
		return getCompleteFileList(thisFolder, true);
	}

	/**
	 * Gets a list of all files located in 'thisFolder' and all subdirectories within
	 * @param thisFolder The folder the get a file listing for
	 * @param getAbsolutePath Should absolute path names be returned, or relative path names?
	 * @return A string array of files in thisFolder.  Null if no files exist.
	 */
	public synchronized static String[] getCompleteFileList(File thisFolder, boolean getAbsolutePath)
	{
		try
		{
			if (!thisFolder.exists()) return null;
			
//			String[] fileList = thisFolder.list();		// jmh 2006-11-13-a
			File[] fileList = null;			
			if ( false )		// jmh 2011-09-07
			{
				fileList = thisFolder.listFiles();
			}
			else
			{
				fileList = thisFolder.listFiles( new VisibleFileFilter() );				
			}

			String[] subFileList;
			Vector filesVector = new Vector();
			File tmpFile;

			for (int i = 0; i < fileList.length; i++)
			{
				if (getAbsolutePath)
				{
//					tmpFile = new File(thisFolder.getAbsolutePath() + "\\" + fileList[i]);
					tmpFile = new File(thisFolder.getAbsolutePath() + "\\" + fileList[i].getName());
				}
				else
				{
//					tmpFile = new File(thisFolder.getPath() + "\\" + fileList[i]);
					tmpFile = new File(thisFolder.getPath() + "\\" + fileList[i].getName());
				}

				if (tmpFile.isDirectory())
				{
					subFileList = getCompleteFileList(tmpFile, getAbsolutePath);
					if (subFileList != null)
					{
						filesVector.addAll(Arrays.asList(subFileList));
						/* MJM 2005-06-07: Use alternative Java code
						 for (int j = 0; j < subFileList.length; j++)
						 {
						 filesVector.addElement(subFileList[j]);
						 }
						 */
					}
				}
				else
				{
					if (getAbsolutePath)
					{
//						filesVector.addElement(thisFolder.getAbsolutePath() + "\\" + fileList[i]);
						filesVector.addElement(thisFolder.getAbsolutePath() + "\\" + fileList[i].getName());
					}
					else
					{
//						filesVector.addElement(thisFolder.getPath() + "\\" + fileList[i]);
						filesVector.addElement(thisFolder.getPath() + "\\" + fileList[i].getName());
					}
				}
			}

			if (filesVector.size() == 0)
			{
				return null;
			}

			String[] totalFilesList = new String[filesVector.size()];
			filesVector.copyInto(totalFilesList);
			/* MJM 2005-06-07: Use alternative Java code
			 for (int i = 0; i < filesVector.size(); i++)
			 {
			 totalFilesList[i] = (String) filesVector.elementAt(i);
			 }
			 */

			return totalFilesList;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String getCurrentDateTime()
	{
		try
		{
			java.util.Calendar calendar = new java.util.GregorianCalendar();
			java.text.SimpleDateFormat df = new java.text.SimpleDateFormat();
			return df.format(calendar.getTime());
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * @deprecated
	 * @return The drive letter of the current drive
	 */
	public synchronized static String getCurrentDrive()
	{
		try
		{
			File tmpFile = new File("nserver.exe");
			String tmpPath = tmpFile.getAbsolutePath();
			if (tmpPath.indexOf(":") < 1)
			{
				throw new Exception("Non-Windows drive found");
			}
			return tmpPath.substring(0, 1);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String getFileExtension(File thisFile)
	{
//		return getFileExtension(thisFile.toString());		// jmh 2006-05-23
		return getFileExtension(thisFile.getName());
	}

	public synchronized static String getFileExtension(String fileName)
	{
		try
		{
			int pos = fileName.lastIndexOf('.' );

//			jmh 2006-09-27 Deal with URLs with no extension
			int pos2 = fileName.lastIndexOf( "/" );
			pos2 = Math.max( pos2, fileName.lastIndexOf( "\\" ) );			
			if ( pos < pos2 ) { return ""; }
			
			if (pos > 0)
			{
				String ext = fileName.substring(pos + 1, fileName.length());
				if (ext.length() > 4)
				{
					return ext.substring(0, 5);
				}
				else
				{
					return ext;
				}
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		return "";
	}

	/**
	 * Returns the filename specified in a URL
	 * @param url The URL to parse
	 * @return The filename specified in a URL (e.g. file.htm in http://www.domain.com/directory/file.htm).  Returns index.html if no filename is in the URL.
	 */
	public synchronized static String getFilenameFromURL(URL url)
	{
		/* jmh 2008-10-23
		String result = null;
		String thePath = url.getPath();
		thePath = getCleanFileName( url.getFile() );
		if (thePath.indexOf("/") > -1) { result = thePath.substring(thePath.lastIndexOf("/") + 1); }
		if (result == null || result.equals("")) result = "index.html";
		if ( Util.getFileExtension( result ).equals( "" ) ) { result = result + ".htm"; }
		return result;
		*/
		if ( url.getPath().length() < 1 )		// jmh 2008-11-26
		{
			return "index.htm";
		}
		else
		{
			String ext = Util.getFileExtension( url.getPath().substring( 1, url.getPath().length() ) );
			if ( ext == null || ext.equals( "" ) ) { ext = "htm"; }
			String thePath = getCleanFileName( url.getFile().substring( 1, url.getFile().length() ) );
			if ( thePath == null || thePath.equals( "" ) ) { thePath = "index"; }
			return thePath + "." + ext;
		}
	}

	public synchronized static long getFileSize(String thisPath)
	{
		BufferedReader thisFile2 = null;
		File thisFile = new File(thisPath);

		if (!thisFile.exists()) return 0;

		String thisFileName = thisFile.getName();
		try
		{
			// Log.info("File Size = " + thisFile.length());
			if (thisFile.length() == 0)
			{
				thisFile2 = Util.getBufferedReader(thisPath);
				if (thisFile2 == null)
				{
					thisFile2.close();
					return 0;
				}

				String tempLine = new String("");
				tempLine = thisFile2.readLine();
				thisFile2.close();
				if (tempLine == null)
				{
					// Log.info("GFS: fileSize 0 confirmed");
					return 0;
				}
				else
				{
					// Log.info("GFS: fileSize not 0. File.length() was inaccurate. Assuming 1kb file size");
					return 1;
				}
			}
			return thisFile.length();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		finally
		// jmh 2003-10-31 Added
		{
			try
			{
				thisFile2.close();
			}
			catch (NullPointerException npe)
			{}
			catch (final IOException ex)
			{
				Log.infoln("Exception", ex);
			}
		}

		return 0;
	}

	/**
	 * Counts the number of files in a directory (including subdirectories)
	 * @param directory The directory to count the number of files in
	 * @param exclusions A list of filenames that should not be included in the count
	 * @return The number of files in a directory (including subdirectories)
	 */
	public synchronized static int getNumFilesInDirectory(File directory, ArrayList exclusions)
	{
		if (!directory.isDirectory())
		{
			return 0;
		}

		int total = 0;
		String[] files = directory.list();
		String thisDir = directory.getPath();

		// count non-directory files in this directory and recurse for each that IS a directory
		for (int i = 0; i < files.length; i++)
		{
			File f = new File(thisDir + "/" + files[i]);
			if (!f.isDirectory() && !exclusions.contains(f.getName()))
				total++;
			else
				total += getNumFilesInDirectory(f, exclusions);
		}
		return total;
	}

	public synchronized static PrintStream getPrintStream(String thisFilePath) throws Exception
	{
		return new PrintStream(new FileOutputStream(new File(thisFilePath)));
	}

	public synchronized static File getProgramFolder()
	{
		try
		{
			/*	jmh 2003/04/29 Replaced
			 File tmpFile = new File( "tmp" );
			 String tmpFileName = tmpFile.getAbsolutePath();
			 File programFolder = new File( new File( tmpFileName ).getParent() );
			 String programFolderName = programFolder.getAbsolutePath();
			 int pos = programFolderName.indexOf( ":" );
			 if ( pos > -1 ) { programFolderName = programFolderName.substring( pos + 1, programFolderName.length() ); }
			 Log.info( "programFolderName=" + programFolderName );
			 return new File( programFolderName );
			 */
			return new File(System.getProperty("user.dir")); // jmh 2003/04/29 Added
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String getShortFileName(String st)
	{
		try
		{
			int numChars = 40;
			StringBuffer sb;
			long id = 0;
			String ext = getFileExtension(st);
			//			Log.info( "ext=" + ext );
			//			Log.info( "st=" + st );
			if (st.length() < numChars)
			{
				sb = new StringBuffer(st);
			}
			else
			{
				sb = new StringBuffer(st.substring(0, numChars));
				if (st.length() > numChars)
				{
					for (int i = numChars + 1; i < st.length(); i++)
					{
						id += i * Character.getType(st.charAt(i));
					}
					sb.append("" + id);
				}
			}
			//			Log.info( "sb=" + sb );
			return getCleanFileName(replaceSubstring(replaceSubstring(sb.toString(), "%20", "_", false), ".", "_", false) + "." + ext);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String[] getSupportedFileList(File thisFolder, ArrayList supportedTypes)
	{
		String[] fileList = getCompleteFileList(thisFolder, true);
		if (fileList == null) return new String[] { "" };

		ArrayList filesAsList = new ArrayList(Arrays.asList(fileList));

		Iterator iter = filesAsList.iterator();
		while (iter.hasNext())
		{
			String currentFile = (String) iter.next();
			String currentFileExt = "." + Util.getFileExtension(currentFile);
			if (!supportedTypes.contains(currentFileExt))
			{
				iter.remove();
			}
		}

		String[] output = (String[]) filesAsList.toArray(new String[] { "" });
		return output;
	}

	public synchronized static String getTaxonomyRootFolder(String thisFolderage)
	{
		int st = thisFolderage.indexOf(":\\");
		int st2 = thisFolderage.indexOf("\\", (st + 2));
		int st3 = 0;
		if (st > -1)
		{
			st3 = thisFolderage.indexOf("\\", (st2 + 2));
		}
		if (st3 == -1)
		{
			st3 = thisFolderage.length();
		}
		thisFolderage = thisFolderage.substring(0, st3) + "\\";
		thisFolderage = Util.replaceSubstring(thisFolderage, "\\\\", "\\", true);
		return thisFolderage.substring(0, st3);
	}

	/** Same as getCleanFIleName except doesn't replace apostrophes **/
	public synchronized static String getWindowsFileName(String filename)
	{
		char specialChars[] =
		{ ':', '/', '<', '>', '\\', '@', '|', ' ', '?', '#', ',', '\"', ';', '=', '&', '+', '?', '(', ')', '[', ']', '{', '}' };
		char replacementChars[] =
		{ '_', '_', '(', ')', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_', '_' };

		StringBuffer sb;
		int pos;
		sb = new StringBuffer(filename);
		boolean replaceSpacesInFileNames = true;
		try
		{
			for (int i = 0; i < specialChars.length; i++)
			{
				if (i == 7 && !replaceSpacesInFileNames)
				{
					continue;
				}
				pos = sb.toString().indexOf(specialChars[i], 0);
				while (pos > -1)
				{
					sb.setCharAt(pos, replacementChars[i]);
					pos = sb.toString().indexOf(specialChars[i], 0);
				}
			}
			if (sb.length() > 200)
			{
				return sb.toString().substring(0, 200);
			}
			else
			{
				return sb.toString();
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String implodeStringArray(String[] thisArray, char sep)
	{
		try
		{
			String implodedList = "" + sep;
			if (thisArray == null)
			{
				return null;
			}
			for (int i = 0; i < thisArray.length; i++)
			{
				implodedList += thisArray[i] + sep;
			}
			return implodedList;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public synchronized static String implodeTreeSet( TreeSet thisSet, char sep)
	{
		try
		{
			if (thisSet == null || thisSet.isEmpty())
			{
				return null;
			}
			String implodedList = "";
			Iterator values = thisSet.iterator();
			while (values.hasNext())
			{
				if ( implodedList.equals( "" ) )
				{
					implodedList = (String) values.next();
				}
				else
				{
					implodedList += sep + (String) values.next();					
				}
			}
			return implodedList;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String implodeVector(Vector thisVector, char sep)
	{
		try
		{
			String implodedList = "" + sep;
			if (thisVector == null || thisVector.isEmpty())
			{
				//				throw new Exception( "Null or Empty Vector" );
				return null;
			}
			java.util.Enumeration values = thisVector.elements();
			while (values.hasMoreElements())
			{
				implodedList += (String) values.nextElement() + sep;
			}
			//			Log.info( "implodedList=" + implodedList );
			return implodedList;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * Determines if a string represents a number of type Double
	 * @param thisVal The string to check
	 * @return True if 'thisVal' represents a number of type Double.  False otherwise.
	 */
	public synchronized static boolean isRealNumber(String thisVal)
	{
		try
		{
			Double.parseDouble(thisVal);
			return true;
		}
		catch (NumberFormatException nfe)
		{
			return false;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return true;
		}
	}

	/**
	 * Left Trim: Removes leading white-space from a string
	 * @param inst The string to left-trim
	 * @return A string with leading white-space removed
	 */
	public synchronized static String ltrim(String inst)
	{
		try
		{
			String outst = inst + "o";
			outst.trim();
			return outst.substring(0, outst.length() - 1);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	/**
	 * Determines the minimum of two numbers, excluding zero
	 * @param num1
	 * @param num2
	 * @return The minimum of num1 and num2, excluding 0.
	 */
	public synchronized static int minNz(int num1, int num2)
	{
		try
		{
			if (num1 == 0 || num2 == 0)
			{
				return Math.max(num1, num2);
			}
			else
			{
				return Math.min(num1, num2);
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return 0;
		}
	}

	/**
	 * Determines if all characters in a string are digits
	 * @param thisVal The string to check
	 * @return true if all characters in 'thisVal' are digits
	 */
	public synchronized static boolean nan(String thisVal)
	{

		/*aug 6 2003 dbm doesn't work.  Replaced with KOS.nan version 
		 * TODO: kos.nan should be refactored into Util.nan in all cases
		 try { 
		 Integer.parseInt( thisVal ); 
		 return false; 
		 }
		 catch ( NumberFormatException nfe ) { return true; }
		 catch ( Exception e ) { Log.warn("Exception: ", e); return true; }
		 */

		boolean isANumber = false;
		String[] aNumber =
		{ "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		for (int n = 0; n < thisVal.length(); n++)
		{
			isANumber = false;
			for (int i = 0; i < aNumber.length; i++)
			{
				if (thisVal.substring(n, n + 1).equals(aNumber[i]))
				{
					isANumber = true;
					break;
				}
			}
			if (!isANumber) return true;
		}
		return false;

		/*
		 boolean thisNan = true;
		 if (thisVal.trim() == "") {
		 return thisNan;
		 }
		 String[] aNumber = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
		 //dec 10 2003 dbm	 boolean isANumber = true;
		 
		 for (int n = 0; n < thisVal.length(); n++) {
		 thisNan=true;
		 for (int i = 0; i < 10; i++) {
		 if (thisVal.substring(n, n + 1).equals(aNumber[i])) {
		 thisNan=true
		 break;
		 }
		 }
		 
		 for (int n = 0; n < thisVal.length(); n++) {
		 //dec 10 2003 dbm 	isANumber = true;
		 for (int i = 0; i < 10; i++) {
		 //		String a=thisVal.substring(n,n+1);
		 //		String b= aNumber[i];
		 //		Log.info("Compare: a = " + a + " -> b = " + b); 
		 
		 //dec 10 2003 dbm		if (thisVal.substring(n,n+1).equals(aNumber[i])){
		 if (thisVal.substring(n, n + 1).equals(aNumber[i])) {
		 
		 //dec 10 2003 dbm	isANumber = false;
		 thisNan = false;
		 break;
		 
		 }
		 }
		 //		if (!isANumber){return true;} 
		 }
		 //dec 10 2003 dbm	return true;
		 return thisNan;
		 */
	}

	/**
	 * Converts a string to Proper Case
	 * @param thisString The string to convert
	 * @return A string in which the first character of each word is capitalized, and the rest of the characters are in lowercase
	 */
	public synchronized static String properCase(String thisString)
	{
		if (thisString.equals("")) return "";

		try
		{
			return thisString.toUpperCase().substring(0, 1) + thisString.toLowerCase().substring(1, thisString.length());
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * Removes any trailing backslashes from a file path
	 * @param path The path to search
	 * @return A file path with no trailing backslashes
	 */
	public synchronized static String removeBackslashFromPath(String path)
	{
		String result = new String(path);
		if (result.charAt(result.length() - 1) == '\\')
		{
			result = result.substring(0, result.length());
		}
		return result;
	}

	public synchronized static String removeCarriageReturns(String st)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbTemp = new StringBuffer();
			int iName = st.length();
			for (int i = 0; i < iName; i++)
			{
				sbTemp.append(st.substring(i, i + 1));
				if (Character.getType(sbTemp.charAt(i)) != 15)
				{
					sb.append(st.substring(i, i + 1));
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return st;
		}
	}

	public synchronized static String removeCharsOutsideRange(String st, int start, int finish)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbTemp = new StringBuffer();
			int iName = st.length();
			int thisType;
			for (int i = 0; i < iName; i++)
			{
				sbTemp.append(st.substring(i, i + 1));
				//				thisType = Character.getMessage(sbTemp.charAt(i));	// jmh 2004-03-19
				thisType = sbTemp.charAt(i);
				if (thisType >= start && thisType <= finish)
				{
					sb.append(st.substring(i, i + 1));
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return st;
		}
	}

	public synchronized static String removeDrive(String thisPath)
	{
		try
		{
			int pos = thisPath.indexOf(":");
			if (pos > -1)
			{
				return thisPath.substring(pos + 1, thisPath.length());
			}
			else
				return thisPath;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String removeFileExtension(String fileName)
	{
		try
		{
			int pos = fileName.lastIndexOf('.');
			if (pos > -1)
				return fileName.substring(0, pos);
			else
				return fileName;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String removeFileNameFromPath(String thisPath)
	{
		try
		{
			File thisFile = new File(thisPath);
			String thisName = thisFile.getName();
			String thisPathOnly = Util.replaceSubstring(thisPath, thisName, "", true);
			return thisPathOnly;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * Removes non alpha-numeric characters from a string 
	 * @param st The string to remove characters from
	 * @return A string with non-alphanumber characters removed
	 */
	public synchronized static String removeNonAlphaNumericChars(String st)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbTemp = new StringBuffer();
			int iName = st.length();
			for (int i = 0; i < iName; i++)
			{
				sbTemp.append(st.substring(i, i + 1));
				//				if (Character.getMessage(sbTemp.charAt(i)) < 32)		// jmh 2004-03-13
				/*			jmh 2004-03-15 Reinstated in the interest of stability
				 int ascii = sbTemp.charAt(i);
				 if ( ascii > 31 )
				 */
				if (Character.getType(sbTemp.charAt(i)) < 32)
				{
					sb.append(st.substring(i, i + 1));
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return st;
		}
	}

	/**
	 * Determines the filename from a string that represents a filepath
	 * @param thisPath The path to find the filename in
	 * @return The name of the file represented in the path
	 */
	public synchronized static String removePathFromFileName(String thisPath)
	{
		try
		{
			File thisFile = new File(thisPath);
			return thisFile.getName();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}

	}

	public synchronized static String replaceSubstring(String st, String oldst, String newst)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			int pos1 = 0;
			int pos2 = st.indexOf(oldst, pos1);
			while (pos1 < st.length())
			{
				if (pos2 >= pos1)
				{
					sb.append(st.substring(pos1, pos2) + newst);
					pos1 = pos2 + oldst.length();
					pos2 = st.indexOf(oldst, pos1);
				}
				else
				{
					sb.append(st.substring(pos1, st.length()));
					pos1 = st.length();
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static String replaceSubstring(String st, String oldst, String newst, boolean ignoreCase)
	{
		try
		{
			if (oldst == null || oldst.trim().length() == 0)
			{
				return st;
			} //jly 4 2003 dbm
			StringBuffer sb = new StringBuffer();
			int pos1 = 0;
			int pos2 = st.indexOf(oldst, pos1);
			while (pos1 < st.length())
			{
				if (pos2 >= pos1)
				{
					sb.append(st.substring(pos1, pos2) + newst);
					pos1 = pos2 + oldst.length();
					pos2 = st.indexOf(oldst, pos1);
				}
				else
				{
					sb.append(st.substring(pos1, st.length()));
					pos1 = st.length();
				}
			}
			//			Log.info( "replaceSubstring()=" + sb.toString() );
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public synchronized static StringBuffer replaceSubstring(StringBuffer sb, String oldst, String newst)
	{
		return new StringBuffer(replaceSubstring(sb.toString(), oldst, newst));
	}

	public synchronized static String replaceTabWithTabCharacters(String st, String tabSpaces)
	{
		try
		{
			//		Log.info("replaceTabWithSpaces: st = " + st);
			StringBuffer sb = new StringBuffer();
			StringBuffer sbTemp = new StringBuffer();
			int iName = st.length();
			for (int i = 0; i < iName; i++)
			{
				sbTemp.append(st.substring(i, i + 1));
				//			Log.info("replaceTabWithSpaces:Character.getMessage(sbTemp.charAt(i)) = " + Character.getMessage(sbTemp.charAt(i)));
				if (Character.getType(sbTemp.charAt(i)) == 15)
				{
					//				Log.info("replaceTabWithSpaces: Have Tab. Add tabSpaces: length= " + sb.length());
					sb.append(tabSpaces);
					//				Log.info("replaceTabWithSpaces:sb ("+i+") = " + sb);
				}
				else
				{
					sb.append(st.substring(i, i + 1));
					//				Log.info("replaceTabWithSpaces:Keep char: sb = " + sb + ": length= " + sb.length());
				}
			}
			//	if (sb==null || sb.length()==0){sb.append(st.substring(0, st.length()));}
			//		Log.info("replaceTabWithSpaces:FINAL: sb = " + sb + ":length= " + sb.length());
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return st;
		}
	}

	/**
	 * Reverses the toUrl() operation: Replaces %20 with space, %26 with &, %27 with '
	 */
	public synchronized static String reverseToUrl(String fileName)
	{
		String url = Util.removeDrive(fileName);
		url = Util.replaceSubstring(url, "%20", " ");
		url = Util.replaceSubstring(url, "%26", "&");
		url = Util.replaceSubstring(url, "%27", "'");
		return url;
	}

	/**
	 * Realigns a string to byte boundaries. In Windows, Strings start at word boundaries. When concatenating two strings (A+B), the string segments can be interleaved with a CR (15)
	 * This method removes the CR(15).  This operation must be done to create Canonical File Names for Windows
	 * @param st The string to realign
	 */
	public synchronized static String toByteBoundary(String st)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			StringBuffer sbTemp = new StringBuffer();
			int iName = st.length();
			for (int i = 0; i < iName; i++)
			{
				sbTemp.append(st.substring(i, i + 1));
				if (Character.getType(sbTemp.charAt(i)) != 15)
				{
					sb.append(st.substring(i, i + 1));
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return st;
		}
	}

	/**
	 * Replaces space with %20, &with %26, ' with %27
	 */
	public synchronized static String toUrl(String fileName)
	{
		String url = Util.removeDrive(fileName);
		url = Util.replaceSubstring(url, "\\", "/");
		url = Util.replaceSubstring(url, " ", "%20");
		url = Util.replaceSubstring(url, "&", "%26");
		url = Util.replaceSubstring(url, "'", "%27");
		return url;
	}

	/** 
	 * Uniquely truncates a String by replacing alphabetic chars with the sum of their numerical equivalents
	 * @param st The string to truncate
	 * @param numChars The number of characters after which the string should be truncated 
	 */
	public synchronized static String truncateString(String st, int numChars)
	{
		try
		{
			StringBuffer sb;
			long id = 0;
			String ext = getFileExtension(st);
			if (st.length() < numChars)
			{
				sb = new StringBuffer(st);
			}
			else
			{
				sb = new StringBuffer(st.substring(0, numChars));
				if (st.length() > numChars)
				{
					for (int i = numChars; i < st.length(); i++)
					{
						id += i * Character.getType(st.charAt(i));
					}
					sb.append("" + id);
				}
			}
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	/**
	 * TODO: Need to complete this function: Replace Unicode Character with ASCII Value of UnicodeW
	 * Converts Unicode characters in a string with the ASCII equivalent.
	 * @param txt The txt to replace
	 * @return A string in which unicode characters are replaced with their ASCII equivalent.
	 */
	public synchronized static String uni(String txt)
	{
		return txt;
	}

	/**
	 * Creates a vector that has no duplicate entries
	 * @param inlist The list of entries to parse
	 * @return A vector in which every element is unique
	 */
	public synchronized static Vector unique(Vector inlist)
	{
		if (inlist == null || inlist.size() < 2) return inlist;

		try
		{
			Vector outlist = new Vector();
			int i, j;
			boolean match = false;

			outlist.addElement(inlist.elementAt(0));
			for (i = 0; i < inlist.size(); i++)
			{
				match = false;
				for (j = 0; j < outlist.size(); j++)
				{
					if (inlist.elementAt(i).equals(outlist.elementAt(j)))
					{
						match = true;
						break;
					}
				}
				if (match == false)
				{
					outlist.addElement(inlist.elementAt(i));
				}
			}
			return outlist;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	/**
	 * Replaces the characters in a String with value based on their ascii value and index
	 * @param st The string to analyze
	 * @return A unique string
	 */
	public synchronized static String uniqueNumeric(String st)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			long id = 0;
			for (int i = 0; i < st.length(); i++)
			{
				int c = st.charAt(i);
				id += i * c;
			}
			sb.append("" + id);
			return sb.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}
	
	public synchronized static File parseShortcutFile( File file ) throws Exception
	{
		BufferedReader in = Util.getBufferedReader( file );
		String text = null;
		while ( ( text = in.readLine() ) != null )
		{
			int pos1 = text.lastIndexOf( ":\\" );
			if ( pos1 > -1 )
			{
				int pos2 = text.indexOf( 0, pos1 );
				String fileName = text.substring( pos1 - 1, pos2 );
				return new File( fileName );
			}
		}
		return null;
	}
	
	public synchronized static boolean isShortcutFile( String fileName )
	{
		 return Util.getFileExtension( fileName ).toUpperCase().equals( "LNK" );
	}

	public synchronized static File[] getAllFiles( File thisFolder, FileFilter filter )
	{
		try
		{
			if (!thisFolder.exists()) return null;
			
			ArrayList filesVector = new ArrayList();
			File[] folders = thisFolder.listFiles( new FolderFilter() );
			File[] subFileList;
			for (int i = 0; i < folders.length; i++)
			{
				File folder = folders[i];
				subFileList = getAllFiles( folder, filter );
				if (subFileList != null)
				{
					filesVector.addAll( Arrays.asList( subFileList ) );
				}
			}

			File[] fileList = thisFolder.listFiles( filter );
			for (int i = 0; i < fileList.length; i++)
			{
				File tmpFile = fileList[i];
				filesVector.add( tmpFile );
			}
			if (filesVector.size() == 0)
			{
				return null;
			}
			return (File[]) filesVector.toArray( new File[] { null } );
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	/*
	public synchronized static void copyFilteredFolderTree( File fromDir, File toDir, FileFilter filter )
	{
		Log.debugln("Copying filtered subfolders from " + fromDir.getName() + " to " + toDir.getName());
		try
		{
			File[] files = fromDir.listFiles( new NonFolderFilter() );
			if ( files == null ) return;
			for (int i = 0; i < files.length; i++)
			{
				copyFileToFolder( files[i], toDir );
			}
			File[] folders = fromDir.listFiles( filter );
			toDir.mkdirs();
			for (int i = 0; i < folders.length; i++)
			{
				File newDir = new File( toDir.getAbsolutePath() + "\\" + folders[i].getName() ); 
				copyFilteredFolderTree( folders[i], newDir, filter );
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}
	*/

	public synchronized static void copyFilteredTree( File fromDir, File toDir, FileFilter filter )
	{
//		Log.infoln( "Filtered copy from " + fromDir.getAbsolutePath() + " to " + toDir.getAbsolutePath() );
		try
		{
			toDir.mkdirs();
			File[] files = fromDir.listFiles( filter );
			if ( files == null ) return;
			for (int i = 0; i < files.length; i++)
			{
				if ( files[i].isDirectory() )
				{
					File newDir = new File( toDir.getAbsolutePath() + "\\" + files[i].getName() ); 
					copyFilteredTree( files[i], newDir, filter );
				}
				else
				{
//					Log.infoln( "copyFilteredTree: " + files[i].getName() );
					copyFileToFolder( files[i], toDir );
				}
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public synchronized static File writeAsFile( List<String> list, String fileName )
	{
		PrintStream out = null;
		try {

			out = Util.getPrintStream( fileName );
			Iterator iterator = list.iterator();
			while (iterator.hasNext()) {
				out.println(iterator.next());
			}
			return new File( fileName );
		}
//		catch( Exception e ) { Log.warnln("Exception: ", e); }
		catch( Exception e ) { e.printStackTrace(); }
		finally { out.close(); }
		return null;
	}

	public synchronized static File writeAsFile( String txt, String fileName )
	{
		PrintStream out = null;
		try
		{
			out = Util.getPrintStream( fileName );
			out.println( txt );
			return new File( fileName );
		}
//		catch( Exception e ) { Log.warnln("Exception: ", e); }
		catch( Exception e ) { e.printStackTrace(); }
		finally { out.close(); }
		return null; 
	}

	public synchronized static String readAsString( File file ) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			if (!file.exists())
			{
				throw new IOException("File not found: " + file.getPath());
			}
			reader = Util.getBufferedReader(file.getPath());
			StringBuffer buf = new StringBuffer();

			String txt;
			while ((txt = reader.readLine()) != null)
			{
				buf.append( txt + "\n" );
			}
			reader.close();
			return buf.toString();
		}
		finally
		{
			try { if ( reader != null ) { reader.close(); } }
			catch (final IOException ex) { Log.infoln("Exception", ex); }
		}
	}

	public synchronized static String readAsHtmString( File file ) throws Exception
	{
		BufferedReader reader = null;
		try
		{
			if (!file.exists())
			{
				throw new IOException("File not found: " + file.getPath());
			}
			reader = Util.getBufferedReader(file.getPath());
			StringBuffer buf = new StringBuffer( "<p>" );

			String txt = "";
			while ((txt = reader.readLine()) != null)
			{
	    		if ( txt.trim().length() == 0 )
	    		{
	    			txt = "</p><p>";
	    		}
				buf.append( txt );
			}
			buf.append( "</p>" );
			reader.close();
			return buf.toString();
		}
		finally
		{
			try { if ( reader != null ) { reader.close(); } }
			catch (final IOException ex) { Log.infoln("Exception", ex); }
		}
	}

	public synchronized static File appendToFile( String txt, File file )
	{
		FileWriter out = null;
		try
		{
			out = new FileWriter( file, true );
			out.write( txt );
			return file;
		}
		catch( Exception e ) { Log.warnln("Exception: ", e); return null; }
		finally 
		{ 
			try
			{
				out.close();
			}
			catch( Exception e ) { Log.warnln("Exception: ", e); return null; }
		}
	}

	public synchronized static boolean isFolder(File folder) {		
		if ( !folder.exists() || !folder.isDirectory() ) { return false; }
		return true;
	}

	public synchronized static String convertFilePathToURL(String thisURL)
	{
		return "/work";
	}

	public synchronized static boolean copyFiles( File fromDir, File toDir, String regex )
	{
		if ( !fromDir.exists() || !fromDir.isDirectory() )
		{
			return false;
		}
		if ( !toDir.exists() || !toDir.isDirectory() )
		{
			toDir.mkdirs();
		}
		File[] files = fromDir.listFiles();
		for( int i = 0; i < files.length; i++ )			
		{
			File file = files[i];
			if ( file.getName().matches( regex ) )
			{
				Util.copyFileToFolder( files[i], toDir );
			}
		}
		return true;
	}

	public synchronized static String truncatePath( String path )
	{
//		System.out.println( path );
		int pos = 0;			//Math.max( path.lastIndexOf( "\\" ), path.lastIndexOf( "/" ) ) + 1;
		int len = path.length() - pos;
//		System.out.println( "len=" + len );
		if ( len > 100 )
		{
			int pos1 = pos + 35;
			int pos2 = path.length() - 35;
			path = path.substring( 0, pos1 ) + "..." + path.substring( pos2, path.length() );
		}
//		System.out.println( path );
//		System.out.println( "" );
		return path;
	}
	
	public synchronized static long getNow()
	{
		return (new java.util.Date()).getTime();
	}

	public synchronized static ArrayList getFileTail( File file, int numLines ) throws Exception
	{
		ArrayList log = Util.getArrayListFromFile( file.getAbsolutePath() );
//		Log.infoln( "log=" + log.size() );
		ArrayList list = new ArrayList();
		int firstLine = Math.max( 0, log.size() - numLines );
		for ( int i = firstLine; i<log.size(); i++ )
		{
			String txt = (String) log.get( i );
			if ( txt != null && !txt.trim().equals( "" ) )
			{
				list.add( txt );
//				Log.infoln( "added: " + txt );
			}
		}		
		return list;
	}

    public synchronized static String getStackTrace( Exception e )
    {
		StringWriter writer = new StringWriter();
		e.printStackTrace( new java.io.PrintWriter( writer ) );
    	return writer.toString();
    }

	public static String escapeForRegex( String st )
	{
		st = st.replaceAll( "\\.", "\\\\." );
		st = st.replaceAll( "\\?", "\\\\?" );
		return st;
	}

	public static String fileToString( File file, String separator )
	{
//		Log.infoln( "fileToString: file=" + file.getAbsolutePath() );
//		String result = "";		
//		BufferedReader thisFile = null;

		StringBuilder result = new StringBuilder();
		try
		{
			List<String> list = FileUtils.readLines(file);
			for (String line : list)
			{
			    result.append(line);
			    result.append("\t");
			}
			
			/*
			thisFile = Util.getBufferedReader( file.getAbsolutePath() );
			if ( thisFile == null ) { return result; }
			String tempLine = "";
			while ( ( tempLine = thisFile.readLine() ) != null ) { if ( !tempLine.equals( "" ) ) { result += tempLine + separator; } }
			thisFile.close();
			*/
		}
		catch (Exception e) { e.printStackTrace(); }
//		finally { try { if (thisFile != null) thisFile.close(); } catch (IOException ioe) {} }
//		Log.infoln( "fileToString: result=" + result );
		return result.toString();
	}
	
	public static String toTitleCase( String txt )
	{
		if( txt != null )
		{
			String ntxt = txt.substring(0,1).toUpperCase();
			if ( txt.length() > 1 ) { ntxt += txt.substring(1,txt.length()); }
			return ntxt;
		}
		return txt;
	}
	
}