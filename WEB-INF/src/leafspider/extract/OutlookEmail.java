package leafspider.extract;

import org.apache.poi.poifs.filesystem.*;
import java.io.*;
import java.util.*;
//import com.cirilab.kos.*;
import leafspider.util.*;

/** 
 * @author Mark Hurst 
 */
public class OutlookEmail
{
	public static void main(String[] args)
	{
		try
		{
	    	String dirName = "C:\\Catalog\\Outlook";
//	    	String fileName = "The Gurteen Knowledge-Letter (Issue 53  1 November 2004)  ##8894##.msg";
//	    	String fileName = "AttachementTXT.msg";
//	    	String fileName = "Managing Collective Intelligence.msg";
//	    	String fileName = "Large attachment.msg";
	    	String fileName = "Re  car recovery.msg";
//	    	String sourceFileName = dirName + "\\" + fileName;
	    	
	    	File folder = new File( dirName );
	    	String[] files = folder.list();
	    	
	    	for ( int i = 0; i < 1; i++ )//files.length; i++ )
	    	{
	    		String sourceFileName = folder.getAbsolutePath() + "\\" + files[i];
		    	if ( Util.getFileExtension( sourceFileName ).equalsIgnoreCase( "MSG" ) )
		    	{
		    		String textFileName = sourceFileName + ".txt";
		    		OutlookEmail.extractEmailBody( sourceFileName, textFileName );
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
		PrintStream out = null;
		try
		{
			File preExtract = new File( sourceFileName + "_extracted.txt" );
			if ( preExtract.exists() )
			{
				Util.copyFile( preExtract.getAbsolutePath(), textFileName );
				return true;
			}
			
			POIFSFileSystem fileSystem = new POIFSFileSystem( new FileInputStream( sourceFileName ) );
			DirectoryEntry root = fileSystem.getRoot();
			out = Util.getPrintStream( textFileName );
			
			for (Iterator iter = root.getEntries(); iter.hasNext(); )
			{
			    Entry entry = (Entry) iter.next();
			    if (entry instanceof DocumentEntry)
			    {
			    	if ( entry.getName().equals( "__substg1.0_1000001F" ) || 
				    		entry.getName().equals( "__substg1.0_1000001E" ) )		// jmh 2008-04-03
					{
			    		// Entry is email text
			    		DocumentEntry doc = (DocumentEntry) entry;
			    		DocumentInputStream stream = new DocumentInputStream(doc );
			    		byte[] content = new byte[ stream.available() ];
			    		stream.read(content);
			    		stream.close();
						for ( int i = 0; i < content.length; i++ )
						{
							int num = content[i];
							if ( num == 10 || num == 13 || num > 31 )
							{
								char ch = (char) content[i];
								out.print( ch );		
//								Log.info( "" + ch );		
							}
						}
//					    Log.infoln( "" );		
					}
			    }
		    	/*
			    else if (entry instanceof DirectoryEntry)
			    {
			        // .. recurse into this directory
			    	DirectoryEntry dir = (DirectoryEntry) entry;
//				    Log.infoln( " entryCount=" + dir.getEntryCount() );			    

					for (Iterator iter2 = dir.getEntries(); iter2.hasNext(); )
					{
					    Entry entry2 = (Entry)iter2.next();
					    if (entry2 instanceof DirectoryEntry)
					    {
					        // .. recurse into this directory
					    	DirectoryEntry dir2 = (DirectoryEntry) entry2;
						    Log.infoln( "EntryCount2=" + dir2.getEntryCount() );			    	
					    }
					    else if (entry2 instanceof DocumentEntry)
					    {
					    	if ( entry2.getName().equals( "__substg1.0_37010102" ) )
					    	{
							    Log.infoln( "Found entry2: " + entry2.getName() );
							    // entry is a document, which you can read
								DocumentEntry doc = (DocumentEntry) entry2;
								DocumentInputStream stream = new DocumentInputStream( doc );
								byte[] content = new byte[ stream.available() ];
								stream.read(content);
								stream.close();
								System.out.print( "\t" );
								for ( int i = 0; i < content.length; i++ )
								{
									char ch = (char) content[i];
									out.print( ch );		
									Log.info( "" + ch );		
								}
							    Log.infoln( "" );		
						    }
				    	}
					}
			    }
				*/
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
			if ( out != null )
			{
				out.close();
			}
		}
	}

	private static String[] metaData = { 
			"----- Original Message -----", 
			"From: ", 
			"<mailto:", 
			"To: ", 
			"Sent: ", 
			"Subject: ", 
			"Date: ", 
			"Content-Type:", 
			"\tcharset=", 
			"Content-Transfer-Encoding:", 
			"----- Forwarded message from", 
			"----- End forwarded message -----", 
			"\tboundary="
		};
	
	public static boolean stripMetaData( String textFileName )
	{
		PrintStream out = null;
		BufferedReader in = null;
		String copyName = textFileName + ".tmp";
		try
		{
			Util.copyFile( textFileName, copyName );
			out = Util.getPrintStream( textFileName );			
    		in = Util.getBufferedReader( copyName );
    		
    		String txt = null;
    		while ( ( txt = in.readLine() ) != null )
			{
				// Filter unwanted reply lines
				boolean isMetaData = false;
				for ( int i = 0; i < metaData.length; i++ )
				{
					if ( txt.indexOf( metaData[i] ) == 0 )
					{
						isMetaData = true;
						Log.infoln( metaData[i] + "=" + txt );
					}
				}
				if ( isMetaData )
				{
					//out.println( "---" );		// jmh 2005-03-23
//					Log.infoln( "---" );		
				}
				else
				{
					out.println( txt );		
//					Log.infoln( txt );		
				}
			}
			return true;
		}
		catch( Exception e )
		{
			Log.infoln( textFileName );
			e.printStackTrace();
			return false;
		}
		finally
		{
			out.close();
			try { in.close(); }
			catch( Exception e ) { }
    		File copy = new File( copyName );
    		boolean deleted = Util.deleteFile(copy);
		}
	}
}
