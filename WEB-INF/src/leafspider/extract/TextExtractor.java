package leafspider.extract;

import java.io.*;
import java.util.*;

import javax.swing.text.html.parser.*;
import javax.swing.text.rtf.RTFEditorKit;

import org.jdom.*;
import org.jdom.input.*;
import org.textmining.text.extraction.*;

import leafspider.template.Template;
import leafspider.template.TemplateFactory;
import leafspider.util.*;

/*
 import org.pdfbox.pdmodel.*;
 import org.pdfbox.util.*;
 import org.pdfbox.pdfparser.*;
 import org.apache.log4j.*;
 */
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.*;
import java.nio.*;

public class TextExtractor
{
	private String extractionEngine = "CIMMITRY";
	private Date startDate;
	private Date endDate;

	public static void main(String[] args)
	{
		try
		{			
			//			String sourceFileName = "D:\\KOSCatalog\\pdf7\bam11_a1.pdf";
			//			String sourceFileName = "D:\\Cher\\Carl\\Chinese\\FC1.pdf";
			//			String sourceFileName = "D:\\KOSCatalog\\megover2\\Autonomy New Product Development Q3 2000.PDF";
			/*
			 String sourceFileName = "D:\\KOSCatalog\\megover2\\KOM Networks User Documentation.pdf";
			 String pdfFontsExe = ServerContext.getApplicationBinFolder() + "\\xpdf\\pdffonts.exe";
			 String[] cmdList = { pdfFontsExe, sourceFileName };
			 File outputFile = null; //new File( "D:\\Cher\\Carl\\Chinese\\fonts.txt" );
			 boolean ret = Execute.execPrint(cmdList, outputFile);
			 */
			//			System.out.println( TextExtractor.swingConvertHTMLToText( "Mister Trout and the<br>big famous <b>salmon</b>" ) );
			TextExtractor te = new TextExtractor();
			te.setExtractionEngine("CIMMITRY");
			te.setStartDate(new Date());

			// te.extractText("D:\\Downloads\\Health WPD problem files\\Facility Paper Part 1.wpd", "D:\\Downloads\\Health WPD problem files\\Facility Paper Part 1.wpd.txt");

			/*
			 // Script tag
			 //			te.extractText( "C:\\Cher\\Bain\\HTML extraction\\change_of_address.htm", "C:\\Cher\\Bain\\HTML extraction\\change_of_address.htm.txt" );
			 // Abort
			 //			te.extractText( "C:\\10740875799531074087579468764233.http___www_congress_gov_cgi-bin_cpquery_14168.htm", "C:\\10740875799531074087579468764233.http___www_congress_gov_cgi-bin_cpquery_14168.htm.txt" );
			 // Hang
			 //			te.extractText( "C:\\Cher\\Shelly\\XPDF hang\\bam11_c6.pdf", "C:\\Cher\\Shelly\\XPDF hang\\bam11_c6.pdf.txt" );
			 //			System.out.println( "Duration=" + te.getDuration() + " milliseconds" );		
			 //			te.extractText( "C:\\Cher\\Shelly\\XPDF hang\\6-LSPR.pdf", "C:\\Cher\\Shelly\\XPDF hang\\6-LSPR.pdf.txt" );
			 //			te.setPdfTimeout( 1 );
			 //			te.extractText( "C:\\Cher\\Bain\\ShellyHangs\\3general-2.pdf", "C:\\Cher\\Bain\\ShellyHangs\\3general-2.pdf.txt" );
			 //			te.extractText( "C:\\Cher\\Arnold\\ZeroTopo\\0025 ALASKA REPORT4.DOC", "C:\\Cher\\Arnold\\ZeroTopo\\0025 ALASKA REPORT4.DOC.txt" );
			 //			System.out.println( "Duration=" + te.getDuration() + " milliseconds" );	

			 //			C:\Documents and Settings\Mark\Local Settings\Temp			
			 //			te.extractText( "C:\\Cher\\Rick\\PPT\\brugge03_apr29.ppt", "C:\\Cher\\Rick\\PPT\\brugge03_apr29.ppt.txt" );

			 //			te.extractText( "C:\\KOSCatalog\\gartner_1\\bam11_c6.pdf", "C:\\KOSCatalog\\gartner_1\\bam11_c6.pdf.txt" );
			 //			te.extractText( "C:\\KOSCatalog\\2pdf\\bam11_a5.pdf", "C:\\KOSCatalog\\2pdf\\bam11_a5.txt" );

			 // Timeout
			 //			te.extractText( "C:\\Cher\\Shelly\\XPDF hang\\6-LSPR.pdf", "C:\\Cher\\Shelly\\XPDF hang\\6-LSPR.pdf.txt" );
			 //			te.extractText( "C:\\Cher\\Alejandro\\QA\\2004-07-22-a\\PDF Knowledge Map truncation\\The KOS Tiles API.pdf", "C:\\Cher\\Alejandro\\QA\\2004-07-22-a\\PDF Knowledge Map truncation\\The KOS Tiles API.pdf.txt" );
			 //			te.extractText( "C:\\Cher\\Alejandro\\Technical Specs KOS.doc", "C:\\Cher\\Alejandro\\Technical Specs KOS.doc.txt" );
			 //			te.extractText( "C:\\Cher\\Alejandro\\API.pdf", "C:\\Cher\\Alejandro\\API.pdf.txt" );
			 //			te.extractText( "C:\\Cher\\Alejandro\\QA\\2004-08-19-a\\Benchmarking in Hong Kong_ Mass Transit Railway Excels in Worldwide Industry Study.pdf", "C:\\Cher\\Alejandro\\QA\\2004-08-19-a\\Benchmarking in Hong Kong_ Mass Transit Railway Excels in Worldwide Industry Study.pdf.txt" );
			 //			te.extractText( "C:\\Cher\\Alejandro\\QA\\2004-08-19-a\\MJ.doc", "C:\\Cher\\Alejandro\\QA\\2004-08-19-a\\MJ.doc.txt" );
			 //			te.extractText("C:\\Cher\\Alejandro\\QA\\CIRILabBrochure.pdf", "C:\\Cher\\Alejandro\\QA\\CIRILabBrochure.pdf.txt");
			 */
			
			te.extractText("C:\\Cher\\Adam Gravitis\\STF-Cirilab_March9_Proposal.pdf", "C:\\Cher\\Adam Gravitis\\STF-Cirilab_March9_Proposal.pdf.txt");
					
			/*
			 String inFolder = "C:\\Cher\\Matt\\JVM crash";
			 String fileName = "10.doc";

			 String out = inFolder;
			 File outFolder = new File(out);
			 outFolder.mkdirs();

			 te.extractText(inFolder + "\\" + fileName, out + "\\" + fileName + ".txt");
			 */

			/*
			 File inputFolder = new File("D:\\TestDocs\\HTML2");
			 //File outputFolder = new File("D:\\TestDocs\\HTML2\\Old");
			 File outputFolder = new File("D:\\TestDocs\\HTML2\\New4");
			 File[] fileList = inputFolder.listFiles();
			 System.out.println("Extracting from: " + fileList.length + " files.");
			 for (int i = 0; i < fileList.length; i++)
			 {
			 File currentFile = fileList[i];
			 if (currentFile.isDirectory()) continue;

			 String inFileName = currentFile.getAbsolutePath();
			 String outFileName = outputFolder.getAbsolutePath() + "\\" + currentFile.getName() + ".txt";

			 System.out.println(System.getProperty("line.separator", "\r\n") + (i + 1) + ": " + currentFile.getName());

			 te.extractText(inFileName, outFileName);
			 System.out.println("Duration=" + te.getDurationSeconds() + " sec");
			 }
			 System.out.println("Done");
			 */
			Report.reportFileHandles( "javaw.exe", "", ServerContext.getApplicationFolder() + "\\log\\handles.log");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean jTidyConvertHTMLToText(File inFile, File outFile)
	{
		JTidyExtractor jte = new JTidyExtractor();
		boolean ret = jte.extract(inFile, outFile);
		setTitle( jte.getTitle() );
		return ret;
	}
	
	private String title = null;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public boolean swingConvertHTMLToText(File inFile, File outFile) throws Exception
	{
		FileReader reader = null;
		try
		{
			reader = new FileReader( inFile );
			String inFileString = this.removeStyleTags( reader );
			TagStripper callback = new TagStripper(outFile);
			new ParserDelegator().parse(new StringReader(inFileString), callback, true);
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
		finally
		{
			if ( reader != null )
			{
				reader.close();
			}
		}
	}

	public synchronized boolean rtfToText(File inFile, File outFile) throws Exception
	{
		FileInputStream stream = null;
		PrintStream out = null;
		try
		{
			stream = new FileInputStream( inFile );

			RTFEditorKit kit = new RTFEditorKit();
			javax.swing.text.Document doc = kit.createDefaultDocument();
			kit.read(stream, doc, 0);
			String txt = doc.getText(0, doc.getLength());
			
			out = Util.getPrintStream( outFile.getAbsolutePath() );
			out.println( txt );
			
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
		finally
		{
			if ( stream != null ) { stream.close(); }
			if ( out != null ) { out.close(); }
		}
	}

	/** Accepts a URL instead of a file, for use with HTML page only */
	public boolean swingConvertHTMLToText(java.net.URL inUrl, java.io.File outFile) throws Exception
	{
		InputStreamReader reader = new InputStreamReader( inUrl.openStream() );
		try
		{
			String inUrlString = this.removeStyleTags( reader );
			TagStripper callback = new TagStripper(outFile);
			new ParserDelegator().parse(new StringReader(inUrlString), callback, true);
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
		finally
		{
			if ( reader != null )
			{
				reader.close();
			}
		}
	}

	/** Strips tags from a String **/
	public synchronized static String swingConvertHTMLToText(String in)
	{
		try
		{
			StringWriter out = new StringWriter();
			TagStripper callback = new TagStripper(out);
			callback.setLineSeparator(" ");
			new ParserDelegator().parse(new StringReader(in), callback, true);
			return out.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}

	public TextExtractor()
	{}

	public TextExtractor(String thisExtractionEngine)
	{
		try
		{
			setExtractionEngine(thisExtractionEngine);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public boolean setExtractionEngine(String thisExtractionEngine)
	{
		try
		{
			extractionEngine = thisExtractionEngine;
			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	public String getExtractionEngine()
	{
		try
		{
			return extractionEngine;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return "";
		}
	}

	public boolean extractText(String sourceFileName, String textFileName) throws Exception
	{
//		Log.infoln("Extracting Text: " + sourceFileName);
//		try
//		{
			//  setStartDate(new Date()); // MJM 2004-08-26 Removed - resets the duration
			boolean ret = false;
			String ext = Util.getFileExtension(sourceFileName);

			File textFile = new File(textFileName);			
			/*
			if (textFile.exists())
			{
				Log.infoln("Text file already exists " + textFile.getName());
				textFile.delete();
			}
			*/
			File extractionFolder = textFile.getParentFile();
			extractionFolder.mkdirs();

			if (ext.equalsIgnoreCase("UNK"))
			{
				ext = FileIdentifier.getTypeExtensionFromFile(new File(sourceFileName));
			}

			if (ext.equalsIgnoreCase("TXT"))
			{
				Util.copyFile(sourceFileName, textFileName);
				if (textFile.exists())
				{
					ret = true;
				}
				else
				{
					ret = false;
				}
			}
			else if (ext.equalsIgnoreCase("HTM") || 
					ext.equalsIgnoreCase("HTML") || 
					ext.equalsIgnoreCase("SHTML") || 
					ext.equalsIgnoreCase("PHP") || 
					ext.equalsIgnoreCase("PHP3") || 
					ext.equalsIgnoreCase("ASP") || 
					ext.equalsIgnoreCase("CFM")  || 
					ext.equalsIgnoreCase("MSPX")  || 
					ext.equalsIgnoreCase("ASPX")  || 
					ext.equalsIgnoreCase("JHTML")  || 
					ext.equalsIgnoreCase("JSP")  || 
					ext.equalsIgnoreCase("STM")  || 
					ext.equalsIgnoreCase("HTMX")  || 
					ext.equalsIgnoreCase("XPD")  || 
					ext.equals("") )
			{
				// ret = convertHTMLToText(sourceFileName, textFileName);
				ret = jTidyConvertHTMLToText(new File(sourceFileName), new File(textFileName));
				if ( ret == false )
				{
					ret = convertHTMLToText(sourceFileName, textFileName);		// jmh 2011-02-10
				}
				if ( ret && !isCorrectlyEncoded( sourceFileName )  ) { encodeFileForExtract( new File( textFileName ) ); }		// jmh 2006-07-13
			}
			else if (ext.equalsIgnoreCase("MSG"))
			{
				ret = OutlookEmail.extractEmailBody(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("MLM"))
			{
				ret = mlmExtractText( sourceFileName, textFileName );
			}
			else if (ext.equalsIgnoreCase("EML"))
			{
				ret = OutlookExpressEmail.extractEmailBody(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("XLS"))
			{
				ret = ExcelSpreadsheet.extractText(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("PPT"))
			{
				PowerPointFile ppt = new PowerPointFile();
				ret = ppt.extractText(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("XML") || 
					 ext.equalsIgnoreCase("XMMAP"))
			{
				ret = xmlExtractText(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("PDF") || 
					 ext.equalsIgnoreCase("PS"))
			{
//				ret = pdfBoxExtractText(sourceFileName, textFileName);
				ret = xpdfExtractText(sourceFileName, textFileName);
			}
			else if (ext.equalsIgnoreCase("WPD") || 
					ext.equalsIgnoreCase("WP") || 
					ext.equalsIgnoreCase("WP5") )
			{
				ret = wpdlibExtractText(sourceFileName, textFileName);
				if ( ret && !isCorrectlyEncoded( sourceFileName )  ) { encodeFileForExtract( new File( textFileName ) ); }		// jmh 2006-07-13
			}
			else if (ext.equalsIgnoreCase("DOC") )
			{
				WordDocumentTester wdt = new WordDocumentTester(sourceFileName);
				try
				{
					if (wdt.isTextminerSupported())
					{
						ret = textminerDocExtractText(sourceFileName, textFileName);
					}
					if (wdt.isWordFile() && (!ret || !wdt.isTextminerSupported()))
					{
						 ret = prepareAndExecuteCimmitry(sourceFileName, textFileName);		// jmh 2006-12-15 Remove Cimmitry	// jmh 2006-12-22 Reinstate Cimmitry
					}
					else
					{
						// Log.infoln("Text Extractor: Skipping non-Word .doc file.");
					}
				}
				catch( Exception e ) {  }
				finally
				{
					if ( wdt != null ) { wdt.getInputStream().close(); }
				}
			}
			else if (ext.equalsIgnoreCase("DOCX") )	// jmh 2006-09-27
			{
				Word12Extractor wext = new Word12Extractor();
				ret = wext.extractText( sourceFileName, textFileName );
			}
			else if ( ext.equalsIgnoreCase( "RTF" ) )
			{
				ret = rtfToText(new File(sourceFileName), new File(textFileName));
			}
			else if (getExtractionEngine().equalsIgnoreCase("VERITY"))
			{
				ret = extractHtml(sourceFileName, sourceFileName + ".htm");
				if (ret)
				{
					ret = convertHTMLToText(sourceFileName + ".htm", textFileName);
					(new File(sourceFileName + ".htm")).delete();
				}
				// Log.info( "extractText( VERITY )=" + ret );
			}
//			/* jmh 2006-12-15 Remove Cimmitry
			else if (getExtractionEngine().equalsIgnoreCase("CIMMITRY"))
			{
				ret = prepareAndExecuteCimmitry(sourceFileName, textFileName);
			}
//			*/
			/*	 jmh 2006-12-22 Reinstate Cimmitry
			else
			{
				// Default to HTML
				ret = jTidyConvertHTMLToText(new File(sourceFileName), new File(textFileName));
				if ( ret && !isCorrectlyEncoded( sourceFileName )  ) { encodeFileForExtract( new File( textFileName ) ); }
			}
			*/
			
//			if ( ret && !isEncoded( sourceFileName, "Cp1252" )  ) { encodeFileForExtract( new File( textFileName ) ); }		// jmh 2006-07-03

			if ( ret )
			{			
				Template template = TemplateFactory.getContentTemplate( getPath() );
				if ( template != null )
				{				
//					System.out.println( "Template class=" + template.getClass().getName() );
					template.stripBoilerPlateFromText( textFile );
				}
			}
						
			return ret;
//		}
//		catch (Exception e)
//		{
//			Log.warnln("Exception: ", e);
//			return false;
//		}
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

	private boolean isEncoded( String fileName, String encoding ) throws Exception
	{
		FileInputStream stream = null;
        BufferedReader reader = null;
		try 
		{
//			FileReader freader = new FileReader( new File( fileName ) );
//	        System.out.println( "---------- encoding=" + freader.getEncoding() );		// jmh Inaccurate so have to check file line by line
			
			stream = new FileInputStream( fileName );
	        Charset charset = Charset.forName( encoding );
	        CharsetDecoder csd = charset.newDecoder();
	        csd.onMalformedInput( CodingErrorAction.REPORT );
	        reader = new BufferedReader( new InputStreamReader( stream, csd ) );
//	        reader = new BufferedReader( new InputStreamReader( stream ) );
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
		}
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

	private String encodeForExtract( String txt ) throws Exception
	{
		return new String( txt.getBytes(), "UTF-8" );		// jmh ISO-8859-1 and UTF-16 fail to work
	}
	
	public boolean prepareAndExecuteCimmitry(String sourceFileName, String textFileName)
	{
		boolean ret;
		try
		{
			if (sourceFileName.indexOf(" ") > -1)
			{
				// Workaround for Cimmitry bug when filepath contains a space
				/* jmh 2008-02-27
				String workspace = Default.toffeeDataFolder();
				File sourceFile = new File(sourceFileName);
				File fileToExtract = new File(workspace + Util.getCleanFileName(sourceFile.getName()));
				fileToExtract.getParentFile().mkdirs();
				Util.copyFile(sourceFileName, fileToExtract.getAbsolutePath());
				ret = cimmitryExtractText(fileToExtract.getAbsolutePath(), textFileName);
				fileToExtract.delete();
				*/
				File textFile = new File( textFileName );
				File fileToExtract = new File( textFile.getParentFile().getAbsolutePath() + "\\" + Util.getCleanFileName( textFile.getName() ) );
				ret = cimmitryExtractText( sourceFileName, fileToExtract.getAbsolutePath() );
				Util.copyFile( fileToExtract, textFile );
			}
			else
			{
				ret = cimmitryExtractText(sourceFileName, textFileName);
			}
			return ret;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	private boolean cimmitryExtractText(String sourceFileName, String textFileName)
	{
		/*
		try
		{
			KOSNative kosNative = new KOSNative();

			// jmh 2003-10-28 Support accented filenames
			String escapedFileName = Util.escapeAccents(sourceFileName);
			String ext = Util.getFileExtension(sourceFileName);
			boolean ret = false;
			if (!escapedFileName.equals(sourceFileName))
			{
				// File to be extracted contains accents, so must workaround				
				Util.copyFile(sourceFileName, escapedFileName);
				String extractedFileName = escapedFileName + ".txt";
				ret = KOSNative.extractText(escapedFileName + "," + extractedFileName);
				Log.infoln("ret=" + ret);
				try
				{
					Util.copyFile(extractedFileName, textFileName);
					(new File(escapedFileName)).delete();
					(new File(extractedFileName)).delete();
				}
				catch (FileNotFoundException fnfe)
				{
					throw fnfe;
				}
			}
			else
			{
				ret = KOSNative.extractText(sourceFileName + "," + textFileName);
			}

			// MJM 2004-08-26: Check for a 0-length file size
			if (new File(textFileName).length() <= 0) ret = false;

			return ret;

			/*
			 // Alternative call to EXE - unfortunately locks files
			 String[] cmdList = { "C:\\CIRILab\\bin\\themes\\call_textExtractor_101.exe", sourceFileName + "," + textFileName };
			 return Execute.execWait( cmdList );
			 */
		/*
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		*/
		return false;
	}

	public boolean extractHtml(String sourceFileName, String htmlFileName)
	{
		try
		{
			String ext = Util.getFileExtension(sourceFileName);
			if (ext.equalsIgnoreCase("PDF") )
			{
				return xpdfExtractHtml(sourceFileName, htmlFileName);
			}
			else if (getExtractionEngine().equalsIgnoreCase("VERITY"))
			{
				return verityExtractHTML(sourceFileName, htmlFileName);
			}
			else
			{
				return false;
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	private boolean verityExtractHTML(String sourceFileName, String htmlFileName)
	{
		try
		{
			// MJM 2004-12-03 String[] cmdList = { "C:\\KOS\\KeyViewBin\\htmlini.exe", "C:\\KOS\\KeyViewBin\\nofrills.ini", sourceFileName, htmlFileName };
			String[] cmdList = { ServerContext.getApplicationFolder() + "\\KeyViewBin\\htmlini.exe", ServerContext.getApplicationFolder() + "\\KeyViewBin\\nofrills.ini", sourceFileName, htmlFileName };
			return Execute.execWait(cmdList);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	public boolean convertHTMLToText(String inFileName, String outFileName) throws Exception
	{
		return convertHTMLToText(new java.io.File(inFileName), new java.io.File(outFileName));
	}

	public boolean convertHTMLToText(java.io.File inFile, java.io.File outFile) throws Exception
	{
		return swingConvertHTMLToText(inFile, outFile); // jmh 2004-01-13 Added
	}

	private boolean xpdfExtractText(String sourceFileName, String textFileName) throws Exception
	{
//		try
//		{
			//	Log.info( "xpdfExtractText: pdfTimeout=" + getPdfTimeout() + " sourceFileName=" + sourceFileName );
			//	Log.info("xpdfExtractText: textFileName = " + textFileName);
			String ext = Util.getFileExtension(sourceFileName);
			//String extractedFileName = Util.replaceSubstring(sourceFileName, "." + ext, ".txt");
			String pdfExeFileName = ServerContext.getApplicationFolder() + "\\bin\\pdftotext.exe";

			// String[] cmdList = { pdfExeFileName, "-raw", sourceFileName };	// jmh 2004-08-18 Using raw inserts line breaks which cause truncated paras
			String[] cmdList = { pdfExeFileName, sourceFileName, textFileName };
			boolean ret = false;
			try
			{
				//				ret = Execute.execWait( cmdList );			// jmh 2004-03-02
				ret = Execute.execTimeout(cmdList, getPdfTimeout() * 1000);
//				Log.infoln( "ret=" + ret );
				//				boolean ret = Execute.execPrint( cmdList );
				/* jmh 2005-01-19
				 if (Util.fileExists(extractedFileName))
				 {
				 Util.copyFile(extractedFileName, textFileName);
				 (new File(extractedFileName)).delete();
				 }
				 */
				if (!Util.fileExists(textFileName))
				{
					Log.infoln("\tWarning: xpdfExtractText: Document Skipped. No Text extracted: " + textFileName.substring(0, (textFileName.length() - 4)));
					return false;
				}
			}
			catch (UnyieldingProcessException upe)
			{
				Log.infoln("\tWarning: XPDF extraction process did not yield");
			}
			/*
			 catch (FileNotFoundException fnfe)
			 {
			 throw fnfe;
			 }
			 */
			//			Util.printStackTrace();		// Use for debug
			return ret;
//		}
//		catch (Exception e)
//		{
//			Log.warnln("Exception: ", e);
//			return false;
//		}
	}

	private boolean xpdfExtractHtml(String sourceFileName, String textFileName) throws Exception
	{
		String ext = Util.getFileExtension(sourceFileName);
		String pdfExeFileName = ServerContext.getApplicationFolder() + "\\bin\\pdftohtml.exe";

		// String[] cmdList = { pdfExeFileName, "-raw", sourceFileName };	// jmh 2004-08-18 Using raw inserts line breaks which cause truncated paras
		String[] cmdList = { pdfExeFileName, "-c", sourceFileName, textFileName };
		boolean ret = false;
		try
		{
			ret = Execute.execTimeout(cmdList, 60 * 1000);
			if (!Util.fileExists(textFileName))
			{
				Log.infoln("\tWarning: xpdfExtractText: Document Skipped. No Text extracted: " + textFileName.substring(0, (textFileName.length() - 4)));
				return false;
			}
		}
		catch (UnyieldingProcessException upe)
		{
			Log.infoln("\tWarning: XPDF extraction process did not yield");
		}
		return ret;
	}

	private boolean wpdlibExtractText(String sourceFileName, String textFileName) throws ExtractFailureException
	{
		try
		{
			// Log.infoln("wpdlibExtractText: source=" + sourceFileName + ", textFileName=" + textFileName);
			String wpdlibExeFileName = ServerContext.getApplicationFolder() + "\\bin\\wpd2text.exe";		// jmh 2006-12-11
//			String wpdlibExeFileName = ServerContext.getApplicationBinFolder() + "\\libwpd-tools\\wpd2text_0.8.4.exe";
			
			String[] cmdList = { wpdlibExeFileName, "\"" + sourceFileName + "\"" };
			boolean ret = false;
			try
			{
				ret = Execute.execPrint(cmdList, new File(textFileName));

				if (!Util.fileExists(textFileName))
				{
					Log.infoln("\tWarning: wpdlibExtractText: Document Skipped. No Text extracted: " + textFileName.substring(0, (textFileName.length() - 4)));
					return false;
				}
			}
			catch (UnyieldingProcessException upe)
			{
				Log.infoln("\tWarning: wpdlib extraction process did not yield");
			}
			
//			removeCp1252Encoding( new File( textFileName ) );		// jmh 2003-07-03 Applied generally 

			return ret;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	private boolean textminerDocExtractText(String sourceFileName, String textFileName)// throws IOException
	{
		FileInputStream fileInput = null;
		FileOutputStream fileOutput = null;
//		Word97TextExtractor extractor = null;
//		org.textmining.extraction.TextExtractor extractor = null;
		WordExtractor extractor = new WordExtractor();
		String str = null;
//		try
//		{
			//			String ext = Util.getFileExtension(sourceFileName);
			//			String extractedFileName = Util.replaceSubstring(sourceFileName, "." + ext, ".txt");
			boolean ret = false;
			try 
			{
				fileInput = new FileInputStream(sourceFileName);
//				WordExtractor extractor = new WordExtractor();
//				String str = extractor.extractText(fileInput);
					
				Log.infoln( "sourceFileName=" + sourceFileName );
			
//				extractor = new Word97TextExtractor( fileInput );
//				WordTextExtractorFactory factory = new WordTextExtractorFactory();
//				extractor = factory.textExtractor( fileInput );
				
				str = extractor.extractText( fileInput );
			
//				Log.infoln( "97" );
//				extractor = new Word97TextExtractor( fileInput );
			}
//		    catch (PasswordProtectedException pe) {}
		    catch (IOException ioe) {}
		    catch (NoSuchMethodError nme) {}
		    catch (Exception e)
		    {
		    	e.printStackTrace();
		    	/*
				Log.infoln( "6" );
		    	try { extractor = new Word6TextExtractor( fileInput ); }
			    catch (Exception ex)
			    {
					Log.infoln( "2" );
			    	try { extractor = new Word2TextExtractor( fileInput ); }
				    catch (Exception exc)
				    {
				    	Log.infoln( "Word version not supported" );				    	
//				    	throw new Exception("Word version not supported");
				    }
			    }
			    */
		    }
			finally
			{
				if ( fileInput != null ) 
				{ 
					try { fileInput.close(); }
					catch( Exception e) { e.printStackTrace(); }
				}
			}

			try
			{
//				Log.infoln( "toString=" + extractor.toString() );

				str = removeControlCharactersFromExtractedText(str);
				if (str.length() > 0) { ret = true; }
				else { ret = false; }

				// Write the file
				/* jmh 2005-01-19
				 FileOutputStream out = new FileOutputStream(extractedFileName);
				 out.write(str.getBytes());
				 out.close();

				 Util.copyFile(extractedFileName, textFileName);

				 if (!extractedFileName.equals(textFileName))
				 {
				 (new File(extractedFileName)).delete();
				 }
				 */
				fileOutput = new FileOutputStream(textFileName);
				fileOutput.write(str.getBytes());
			}
			catch (Exception e)
			{
				Log.warnln( "Exception: ", e );
				ret = false;
			}
			finally
			{
				if ( fileOutput != null ) 
				{
					try { fileOutput.close(); }
					catch( Exception e) { e.printStackTrace(); }
				}
			}
			//			Util.printStackTrace();		// Use for debug
			return ret;
	}

	public void setStartDate(Date thisDate)
	{
		startDate = thisDate;
	}

	public void setEndDate(Date thisDate)
	{
		endDate = thisDate;
	}

	public long getDuration()
	{
		if (endDate == null)
		{
			return (new Date()).getTime() - startDate.getTime();
		}
		else
		{
			return endDate.getTime() - startDate.getTime();
		}
	}

	public float getDurationSeconds()
	{
		float secs = (float) getDuration() / 1000;
		return secs;
	}

	private String removeStyleTags(Reader inFile)
	{
		BufferedReader input = null;
		StringBuffer theFile = new StringBuffer();

		try
		{
			input = new BufferedReader(inFile);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return new String();
		}

		// First, read the file into a string
		try
		{
			String currentLine = new String();
			while ((currentLine = input.readLine()) != null)
			{
				theFile.append(currentLine);
				theFile.append(System.getProperty("line.separator", "\r\n"));
			}
			input.close();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		finally
		{
			try
			{
				if (input != null) input.close();
			}
			catch (IOException ioe)
			{}
		}

		// Now, go through and remove all text of the form <style>...</style>
		String theFileLowercase = theFile.toString().toLowerCase();
		int indexOfStartStyle = theFileLowercase.indexOf("<style");
		while (indexOfStartStyle >= 0)
		{
			int indexOfEndStyle = theFileLowercase.indexOf("</style>", indexOfStartStyle + 7);
			if (indexOfEndStyle >= 0)
			{
				theFile = theFile.replace(indexOfStartStyle, indexOfEndStyle + 8, "");
				theFileLowercase = theFile.toString().toLowerCase();
				indexOfStartStyle = theFileLowercase.indexOf("<style");
			}
			else
			{
				indexOfStartStyle = -1;
			}
		}
		return theFile.toString();
	}

	/** PDF Timeout in seconds */
	private long pdfTimeout = 30;

	public long getPdfTimeout()
	{
		return pdfTimeout;
	}

	public void setPdfTimeout(long timeout)
	{
		this.pdfTimeout = timeout;
	}

	/*
	 private boolean pdfBoxExtractText(String sourceFileName, String textFileName) throws ExtractFailureException, Exception
	 {
	 PDFTextStripper stripper = new PDFTextStripper();
	 stripper.setLineSeparator("\n");
	 OutputStream os = null;
	 Writer writer = null;
	 PDDocument document = null;
	 try
	 {
	 document = PDDocument.loadTimeline(new File(sourceFileName));
	 
	 File outFile = new File(textFileName);
	 os = new FileOutputStream(outFile);
	 writer = new OutputStreamWriter(os);
	 
	 stripper.writeText(document, writer);
	 }
	 catch (Exception e)
	 {
	 Log.warn("Exception: ", e);
	 return false;
	 }
	 finally
	 {
	 if (os != null)
	 {
	 os.close();
	 }
	 if (writer != null)
	 {
	 writer.close();
	 }
	 if (document != null)
	 {
	 document.close();
	 }
	 }
	 
	 return true;
	 
	 //		int currentArgumentIndex = 0;
	 //		String password = "";
	 //		String encoding = "ISO-8859-1"; //DEFAULT_ENCODING;
	 //		PDFTextStripper stripper = new PDFTextStripper();
	 //		String pdfFile = null;
	 //		String textFile = null;
	 //		int startPage = 1;
	 //		int endPage = Integer.MAX_VALUE;
	 //		Logger log = Logger.getLogger(TextExtractor.class);
	 //		
	 //		pdfFile = sourceFileName;
	 //		textFile = textFileName;
	 //		InputStream input = null;
	 //		Writer output = null;
	 //		PDDocument document = null;
	 //		
	 //		try
	 //		{
	 //			input = new FileInputStream(pdfFile);
	 //			long start = System.currentTimeMillis();
	 //			//			document = parseDocument( input );
	 //		
	 //			PDFParser parser = new PDFParser(input);
	 //			parser.parse();
	 //			document = parser.getPDDocument();
	 //		
	 //			long stop = System.currentTimeMillis();
	 //			log.info("Time to parse time=" + (stop - start));
	 //		
	 //			//document.print();
	 //			if (encoding != null)
	 //			{
	 //				output = new OutputStreamWriter(new FileOutputStream(textFile), encoding);
	 //			}
	 //			else
	 //			{
	 //				//use default encoding
	 //				output = new OutputStreamWriter(new FileOutputStream(textFile));
	 //			}
	 //		
	 //			start = System.currentTimeMillis();
	 //			stripper.setStartPage(startPage);
	 //			stripper.setEndPage(endPage);
	 //			stripper.writeText(document, output);
	 //			stop = System.currentTimeMillis();
	 //			log.info("Time to extract text time=" + (stop - start));
	 //		}
	 //		catch (Exception e)
	 //		{
	 //			Log.warn("Exception: ", e);
	 //			return false;
	 //		}
	 //		finally
	 //		{
	 //			if (input != null)
	 //			{
	 //				input.close();
	 //			}
	 //			if (output != null)
	 //			{
	 //				output.close();
	 //			}
	 //		}
	 //		//			Util.printStackTrace();		// Use for debug
	 //		return true;
	 }
	 */

	private boolean xmlExtractText(String sourceFileName, String textFileName)
	{
		try
		{
			File in = new File(sourceFileName);
			if (in == null)
			{
				return false;
			}
			PrintStream out = Util.getPrintStream(textFileName);

			SAXBuilder saxDoc = new SAXBuilder();
			Document jDomDoc = saxDoc.build(in);
						
			File extract = new File( sourceFileName + "_extracted.txt" );
			if ( extract.exists() )
			{
       			Util.copyFile( extract, new File( textFileName ) );
       			return true;
			}

			org.jdom.Element root = jDomDoc.getRootElement();
			printElementText(root, out);
			out.close();

			return true;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}
	}

	private void printElementText(org.jdom.Element root, PrintStream out)
	{
		List attributes = root.getAttributes();
		for (int i = 0; i < attributes.size(); i++)
		{
			Attribute att = (Attribute) attributes.get(i);
			out.println(att.getValue().trim());
		}
		out.println(root.getText().trim());
		List kids = root.getChildren();
		for (int i = 0; i < kids.size(); i++)
		{
			printElementText((org.jdom.Element) kids.get(i), out);
		}
	}

	public static String removeControlCharactersFromExtractedText(String string)
	{
		StringBuffer sb = new StringBuffer(string.length());
		int len = string.length();
		char c;
		for (int i = 0; i < len; i++)
		{
			c = string.charAt(i);
			int ci = 0xffffffff & c;
			if (ci == 9)
			{
				sb.append('\t');
			}
			else if (ci == 7 || ci == 11 || ci == 12)
			{
				sb.append(System.getProperty("line.separator", "\r\n"));
			}
			else if (ci >= 32 || ci == 10 || ci == 13)
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public boolean mlmExtractText( String sourceFileName, String textFileName )
	{
		/*
		MLMMessage item = new MLMMessage();
		item.setFile( new File( sourceFileName ) );
		boolean ret = item.extractEmailBody( textFileName );
		item.getExtractedFile().delete();
		return ret;
		*/
		return false;
	}
	
	private String path = null;
	public String getPath() {
		return path;
	}
	public void setPath( String path ) {
		this.path = path;
	}
}
