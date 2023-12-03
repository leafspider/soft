package leafspider.extract;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.List;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

import leafspider.util.*;
//import com.cirilab.kos.engine.DataContext;
//import com.cirilab.kos.transaction.Default;

public class Word12Extractor 
{
	public static void main(String[] args) 
	{
		try
		{
	//		File sourceFile = new File( "C:\\Temp\\Walk Through V5 DOCX.docx" );
//			File sourceFile = new File( "C:\\Temp\\Arnold Villeneuve CV DOCX11111.docx" );
			File sourceFile = new File( "C:\\Cher\\Michael Scherotter\\DOCX\\Office Open XML Part 5 - Markup Compatibility and Extensibility_final.docx" );
			File textFile = new File( sourceFile.getAbsolutePath() + ".txt" );
					
			Word12Extractor ext = new Word12Extractor();
			ext.extractText( sourceFile.getAbsolutePath(), textFile.getAbsolutePath() );
	//		/*
//			File xmlFile = new File( "C:\\Temp\\Walk Through V5 DOCX.docx.document.xml" );
//			Word12Extractor ext = new Word12Extractor();
			
//			System.out.println( ext.removeBadChars( "=preserve> ™</w:t></w:r></w:") );
			
			/*
			ext.setSourceFile( sourceFile );
			ext.setTextFile( textFile );
			ext.setXmlFile( xmlFile );
			ext.writeXmlFile();			
//			if ( !ext.isCorrectlyEncoded( xmlFile.getAbsolutePath() )  ) { 
//				ext.encodeFileForExtract( xmlFile );// }			
			ext.writeTextFile();
	// 		*/		
		}
		catch ( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public synchronized boolean extractText( String sourceFileName, String textFileName )
	{
		try
		{
			if ( sourceFileName == null )
			{
				throw new Exception( "Null parameter: sourceFile"  );
			}
			if ( textFileName == null )
			{
				throw new Exception( "Null parameter: textFile"  );
			}
			setSourceFile( new File( sourceFileName ) );
			setTextFile( new File( textFileName ) );
	
			if ( !getSourceFile().exists() )
			{
				throw new Exception( "File does not exist: " + sourceFileName  );			
			}
//			writeXmlFile();					
			writeTextFile();
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	/*
	private File xmlFile = null;
	public File getXmlFile() 
	{
		if ( xmlFile == null )
		{
			xmlFile = new File( getData().getWorkspace().getTemp() + "\\" + Util.uniqueNumeric( getSourceFile().getAbsolutePath() ) + "." + getSourceFile().getName() + ".document.xml" );
		}
		return xmlFile;
	}
	public void setXmlFile(File xmlFile) {
		this.xmlFile = xmlFile;
	}
	*/
	
	private File sourceFile = null;
	public File getSourceFile() {
		return sourceFile;
	}
	public void setSourceFile(File sourceFile) {
		this.sourceFile = sourceFile;
	} 
	
	private File textFile = null;
	public File getTextFile() {
		return textFile;
	}
	public void setTextFile(File textFile) {
		this.textFile = textFile;
	}
	
	private void writeTextFile()
	{
		PrintStream out = null;
		try
		{
			ZipInputStream zip = new ZipInputStream( new FileInputStream( getSourceFile().getAbsolutePath() ) );						
			ZipEntry entry = zip.getNextEntry();
		    while( entry != null ) 
		    {
		    	if ( entry.getName().equalsIgnoreCase( "word/document.xml" ) )
		    	{
//			    	byte[] bytes = readBytes( zip );
			    	out = Util.getPrintStream( getTextFile().getAbsolutePath() );

					Namespace wns = Namespace.getNamespace( "w", "http://schemas.openxmlformats.org/wordprocessingml/2006/3/main" );
					
					SAXBuilder saxDoc = new SAXBuilder();
					Document jDomDoc = saxDoc.build( zip );	
					
					Element root = jDomDoc.getRootElement();					
					Element body = root.getChild( "body", wns );					
					if ( body == null )	// jmh 2007-03-06
					{
						wns = Namespace.getNamespace( "w", "http://schemas.openxmlformats.org/wordprocessingml/2006/main" );
						body = root.getChild( "body", wns );
					}
					
					List paras = body.getChildren( "p", wns );					
					for ( int i = 0; i < paras.size(); i++ )
					{
						Element para = (Element) paras.get( i );	
						
						List breaks = para.getChildren( "pPr", wns );
						for ( int j = 0; j < breaks.size(); j++ )
						{
							out.println( "" ); 
						}
						
						List reads = para.getChildren( "r", wns );
						for ( int j = 0; j < reads.size(); j++ )
						{
							Element read = (Element) reads.get( j );
							
							boolean isHidden = false;
							List props = read.getChildren( "rPr", wns );
							for ( int k = 0; k < props.size(); k++ )		// jmh 2007-03-06
							{
								Element prop = (Element) props.get( k );								
								List hiddens = prop.getChildren( "webHidden", wns );
								if ( hiddens.size() > 0 )
								{
									isHidden = true;
									break;
								}
							}
							if ( isHidden )
							{
								continue;
							}
							
							String text = read.getChildText( "t", wns );
							if ( text != null )
							{
								out.print( text );
							}
						}
						out.println( "" ); 		// jmh 2007-03-06
						out.println( "" ); 		// jmh 2007-03-06
					}		    	    	
					break;
			    }
		    	entry = zip.getNextEntry();
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		finally
		{
			if ( out != null )
			{
				out.close();
			}
		}
	}

	/* DEPRECATED Problemattic due to MS encoding issues in converted documents*/
	/*
	private void writeXmlFile()
	{
		PrintStream out = null;
		try
		{
//			Util.copyFile( getSourceFile().getAbsolutePath(), getZipFile().getAbsolutePath() );
//			ZipInputStream zip = new ZipInputStream( new FileInputStream( getZipFile().getAbsolutePath()) );						
			ZipInputStream zip = new ZipInputStream( new FileInputStream( getSourceFile().getAbsolutePath() ) );						
			ZipEntry entry = zip.getNextEntry();
		    while( entry != null ) 
		    {
		    	if ( entry.getName().equalsIgnoreCase( "word/document.xml" ) )
		    	{
//			    	String entryName = entry.getName();
			    	byte[] bytes = readBytes( zip );
//			    	if (bytes!=null) 
//			    	{
//			    		entries.put( entryName, bytes );
//			    	}
//			    	String st = new String( bytes, "ISO-8859-1" );
//			    	String st = new String( bytes, "UTF-8" );
//			    	String st = new String( bytes, "ISO-10646-1" );		// jmh 2007-02-13
			    	String st = new String( bytes );
			    	out = Util.getPrintStream( getXmlFile().getAbsolutePath() );
//			    	out.print( st );		// jmh 2007-02-12
			    	out.print( removeBadChars( st ) );			    	
					break;
			    }
		    	entry = zip.getNextEntry();
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		finally
		{
			if ( out != null )
			{
				out.close();
			}
		}
	}
	*/

	/*
	public String removeBadChars( String in )
	{
		try
		{
//			String regex = 	"[[^\\u0009\\u000A\\u000D]&&[^\\u0020-\\uD7FF]&&[^\\uE000-\\uFFFD]&&[^\\u10000-\\u10FFFF]]";			
//			String regex = 	"[\\u0000\\u00FF\\u00FE]";
//			String regex = 	"[\\u007F-\\u009F]";
//			in = in.replaceAll( regex, "" );
			String regex = 	"\\u00FF";			
//			in = in.replaceAll( regex, "" );
//			regex = 	"\\u00FE";			
//			in = in.replaceAll( regex, "" );
//			regex = 	"\\u0000";
			regex = "[\\u2122]";
			return in.replaceAll( regex, "" );
		}
		catch (Exception e)
		{
			Log.infoln("Exception: ", e);
			return null;
		}
	}
	*/

	/*
	private String encodeForExtract( String txt ) throws Exception
	{
		return new String( txt.getBytes(), "UTF-8" );		// jmh ISO-8859-1 and UTF-16 fail to work
	}
	
	private void encodeFileForExtract( File file )
	{
		PrintStream out = null;
		try
		{
			ArrayList list = Util.getArrayListFromFile( file.getAbsolutePath() );
			out = Util.getPrintStream( file.getAbsolutePath() );
			Iterator lines = list.iterator();
			while ( lines.hasNext() )
			{
				String line = (String) lines.next();
				line = encodeForExtract( line );
	            out.println( line );
			}
		}
		catch( Exception e ) { e.printStackTrace(); }
		finally { try { if ( out != null ) { out.close(); } } catch( Exception e ) { e.printStackTrace(); } }
	}
	
	private boolean isCorrectlyEncoded( String fileName ) throws Exception
	{
		FileInputStream stream = null;
        BufferedReader reader = null;
        FileReader freader = null;
		try 
		{
			freader = new FileReader( new File( fileName ) );
			stream = new FileInputStream( fileName );
	        Charset charset = Charset.forName( freader.getEncoding() );
	        CharsetDecoder csd = charset.newDecoder();
	        csd.onMalformedInput( CodingErrorAction.REPORT );
	        reader = new BufferedReader( new InputStreamReader( stream, csd ) );
	        while( ( reader.readLine() ) != null ) {}
			return true;
		}
		catch (Exception e) 
		{
//			e.printStackTrace();
			Log.infoln( "Rencoding file " + (new File( fileName )).getName() );
			return false;
		}
		finally
		{
			if ( stream != null ) { stream.close(); }
			if ( reader != null ) { reader.close(); }
			if ( freader != null ) { freader.close(); }
		}
	}
	*/
	
	/*
	static final int BUFFERSIZE = 4096;
	static byte[] readBytes(InputStream inputStream) throws IOException 
	{
		byte[] bytes = null;
		if (inputStream==null) 
		{
			throw new NullPointerException("inputStream is null in Word12Extractor.readBytes()");
		}
		byte[] buffer = new byte[BUFFERSIZE];
		int bytesRead = 0;
		while ( (bytesRead = inputStream.read(buffer)) != -1) 
		{
			if (bytes!=null) 
			{
				byte[] oldBytes = bytes;
				bytes = new byte[oldBytes.length+bytesRead];
				System.arraycopy(oldBytes, 0, bytes, 0, oldBytes.length);
				System.arraycopy(buffer, 0, bytes, oldBytes.length, bytesRead);
			} 
			else 
			{
				bytes = new byte[bytesRead];
				System.arraycopy(buffer, 0, bytes, 0, bytesRead);
			}
		}
		return bytes;
	}
	*/
	
	/* DEPRECATED Replaced by direct extract from Zip XML file*/
	/*
	public void obwriteTextFile()
	{
		PrintStream out = null;
		try
		{
			out = Util.getPrintStream( getTextFile().getAbsolutePath() );
			
			Namespace wns = Namespace.getNamespace( "w", "http://schemas.openxmlformats.org/wordprocessingml/2006/3/main" );
			
			SAXBuilder saxDoc = new SAXBuilder();
			Document jDomDoc = saxDoc.build( getXmlFile() );
			
			Element root = jDomDoc.getRootElement();			
			Element body = root.getChild( "body", wns );
			
			List paras = body.getChildren( "p", wns );
			for ( int i = 0; i < paras.size(); i++ )
			{
				Element para = (Element) paras.get( i );				
				
				List breaks = para.getChildren( "pPr", wns );
				for ( int j = 0; j < breaks.size(); j++ )
				{
					out.println( "" ); 
				}
				
				List reads = para.getChildren( "r", wns );
				for ( int j = 0; j < reads.size(); j++ )
				{
					Element read = (Element) reads.get( j );
					String text = read.getChildText( "t", wns );
					if ( text != null )
					{
						out.print( text );
					}
				}
			}			
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		finally
		{
			if ( out != null )
			{
				out.close();
			}
		}
	}
	*/	
		
	/*
	private File zipFile = null;
	public File getZipFile() 
	{
		if ( zipFile == null )
		{
			zipFile = new File( getData().getWorkspace().getTemp() + "\\" + Util.uniqueNumeric( getSourceFile().getAbsolutePath() ) + "." + getSourceFile().getName() + ".zip" );
		}
		return zipFile;
	}
	public void setZipFile(File zipFile) {
		this.zipFile = zipFile;
	}
	*/

}
