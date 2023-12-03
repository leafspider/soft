package leafspider.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.Hashtable;
import java.util.Enumeration;

import org.jdom2.Document;
import org.jdom2.input.SAXBuilder;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Parser;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import com.jclark.xsl.sax.Destination;
import com.jclark.xsl.sax.FileDescriptorDestination;
import com.jclark.xsl.sax.FileDestination;
import com.jclark.xsl.sax.OutputMethodHandlerImpl;
import com.jclark.xsl.sax.XMLProcessorEx;
import com.jclark.xsl.sax.XSLProcessor;
import com.jclark.xsl.sax.XSLProcessorImpl;

/**
 * @author Mark Hurst
 * @since KOS 1.00.
 */

public class XslTransformer
{
	public static void main(String[] args)
	{
		try
		{
			File xml = new File("C:\\KOSTaxonomy\\news_2\\Knowledge Organizer\\2nd Order K Functions\\Classification\\classification_Taxonomy.xml");
			File xsl = new File("dkvToc.xsl");
			String html = "C:\\KOSTaxonomy\\news_2\\Knowledge Organizer\\2nd Order K Functions\\Classification\\xslOutput.htm";
			XslTransformer former = new XslTransformer();
			former.doTransform(xml, xsl, html);
			System.out.println("Done");
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public boolean doTransform(File xmlFile, File xslFile, String htmlFileName) throws Exception
	{
		if ( !xmlFile.exists() )
		{
			return false;		// jmh 2005-08-03
		}
		
		// jmh 2005-02-10
		String inChar = "%";
		String outChar = "bib";
		String xmlFileName = xmlFile.getName();
		File htmlFile = new File ( htmlFileName );
		if ( xmlFile.getName().indexOf( inChar ) > -1 )
		{
			xmlFileName = Util.replaceSubstring( xmlFile.getName(), inChar, outChar );
			Util.copyFile( xmlFile, new File( xmlFile.getParentFile().getAbsolutePath() + "\\" + xmlFileName ) );
			xmlFile = new File( xmlFile.getParentFile().getAbsolutePath() + "\\" + xmlFileName );
		}
		if ( htmlFile.getName().indexOf( inChar ) > -1 )
		{
			htmlFileName = Util.replaceSubstring( htmlFileName, inChar, outChar );
			htmlFile = new File( htmlFileName );
		}
		
		//		Log.info( "Transform:" );
		//		Log.info( "00 " + xslFile.getPath() );
//		com.cirilab.kos.util.Timer timer = new com.cirilab.kos.util.Timer();
//		timer.start( null );			
		if (xslFile == null || !xslFile.exists())
		{
			xslFile = new File(getSkinXslRoot() + "\\" + xslFile.getName());
			//			Log.info( "10 " + xslFile.getPath() );
		}
		if (xslFile == null || !xslFile.exists())
		{
			xslFile = new File(getDefaultXslRoot() + "\\" + xslFile.getName());
			//			Log.info( "20 " + xslFile.getPath() );
		}
		if (getDebug())
		{
			Log.infoln("xmlFile=" + xmlFile.getPath());
			Log.infoln("xslFile=" + xslFile.getPath());
			Log.infoln("htmlFile=" + htmlFileName);
		}
		
//		timer.end( "doTransform00" );
		
		XSLProcessorImpl xsl = new XSLProcessorImpl();
		setParameters( xsl );
		
//		timer.end( "doTransform10" );
		setParser(xsl);
		xsl.setErrorHandler(new ErrorHandlerImpl());
		OutputMethodHandlerImpl outputMethodHandler = new OutputMethodHandlerImpl(xsl);
		xsl.setOutputMethodHandler(outputMethodHandler);
		boolean succeeded = true;
		File in = xmlFile;		
		File stylesheet = xslFile;
		File out = htmlFileName == "" ? null : new File(htmlFileName);
//		timer.end( "doTransform15" );
		if (!in.isDirectory())
		{
			succeeded = transformFile(xsl, outputMethodHandler, in, stylesheet, out);
//			timer.end( "doTransform20" );
			
			// jmh 2005-02-10
			if ( xmlFileName.indexOf( outChar ) > -1 )
			{
				xmlFileName = Util.replaceSubstring( xmlFile.getName(), outChar, inChar );
				Util.copyFile( xmlFile, new File( xmlFile.getParentFile().getAbsolutePath() + "\\" + xmlFileName ) );
//				xmlFile.delete();
				xmlFile = new File( xmlFile.getParentFile().getAbsolutePath() + "\\" + xmlFileName );
			}
			if ( htmlFileName.indexOf( outChar ) > -1 )
			{
				htmlFileName = Util.replaceSubstring( htmlFile.getAbsolutePath(), outChar, inChar );
				Util.copyFile( htmlFile, new File( htmlFileName ) );
//				htmlFile.delete();
				htmlFile = new File( htmlFileName );
			}
		}
		else
		{
			String[] inFiles = in.list();
			for (int i = 0; i < inFiles.length; i++)
			{
				java.io.File inFile = new java.io.File(in, inFiles[i]);
				if (!inFile.isDirectory())
				{
					if (!stylesheet.isDirectory())
					{
						if (!transformFile(xsl, outputMethodHandler, inFile, stylesheet, new java.io.File(out, inFiles[i])))
						{
							succeeded = false;
						}
//						timer.end( "doTransform30" );
					}
					else
					{
						int ext = inFiles[i].lastIndexOf('.');
						java.io.File stylesheetFile = new java.io.File(stylesheet, ext < 0 ? inFiles[i] : inFiles[i].substring(0, ext) + ".xsl");
						if (stylesheetFile.exists() && !transformFile(xsl, outputMethodHandler, inFile, stylesheetFile, new java.io.File(out, inFiles[i])))
						{
							succeeded = false;
						}
//						timer.end( "doTransform40" );
					}
				}
			}
		}
		if (!succeeded)
		{
			//			Log.info( "XslTransform failed" ); 
			return false;
		}
		return true;
	}

	private String defaultXslRoot = null;
	public String getDefaultXslRoot()
	{
		if (defaultXslRoot == null)
		{
//			return getPagesPath() + "\\skins\\" + getDefaultSkin() + "\\app\\views"; 		// MJM 2005-07-18
//			return getSkinsPath() + File.separator + ServerContext.getApplicationName() + File.separator + "_xsl"; 	// jmh 2008-04-24
//			return getSkinsPath() + File.separator + ServerContext.getApplicationName(); 	// jmh 2008-04-24
			return getSkinsPath() + "\\resource";
		}
		return defaultXslRoot;
	}

	private String skinXslRoot = null;
	public String getSkinXslRoot()
	{
		if (skinXslRoot == null)
		{
//			return getPagesPath() + "\\skins\\" + getSkin() + "\\app\\views";		// MJM 2005-07-18
//			return getSkinsPath() + File.separator + ServerContext.getApplicationName() + File.separator + "_xsl";  	// jmh 2008-04-24
			return getDefaultXslRoot();
		}
		return skinXslRoot;
	}

	public String getSkinsPath()
	{
		return "\\skins";
	}
	
	static class ErrorHandlerImpl implements ErrorHandler
	{
		public void warning(SAXParseException e)
		{
			printSAXParseException(e);
		}
		public void error(SAXParseException e)
		{
			printSAXParseException(e);
		}
		public void fatalError(SAXParseException e) throws SAXException
		{
			throw e;
		}
	}

	synchronized static void setParser(XSLProcessorImpl xsl)
	{
		String parserClass = System.getProperty("com.jclark.xsl.sax.parser");
		if (parserClass == null)
		{
			parserClass = System.getProperty("org.xml.sax.parser");
		}
		if (parserClass == null)
		{
			parserClass = "com.jclark.xml.sax.CommentDriver";
		}
		try
		{
			Object parserObj = Class.forName(parserClass).newInstance();
			if (parserObj instanceof XMLProcessorEx)
			{
				xsl.setParser((XMLProcessorEx) parserObj);
			}
			else
			{
				xsl.setParser((Parser) parserObj);
			}
		}
		catch (ClassNotFoundException e)
		{
			Log.infoln(e.toString());
		}
		catch (InstantiationException e)
		{
			Log.infoln(e.toString());
		}
		catch (IllegalAccessException e)
		{
			Log.infoln(e.toString());
		}
		catch (ClassCastException e)
		{
			Log.infoln(parserClass + " is not a SAX driver");
		}
	}

	synchronized static boolean transform(XSLProcessor xsl, InputSource stylesheetSource, InputSource inputSource)
	{
//		com.cirilab.kos.util.Timer timer = new com.cirilab.kos.util.Timer();
//		timer.start( null );			
//		timer.end( "transform00" );			
		try
		{
			if (debug)
			{
				Log.infoln("loadTimeline ");
			}
//			timer.end( "transform10" );		
			xsl.loadStylesheet(stylesheetSource);
//			timer.end( "transform20" );		
			if (debug)
			{
				Log.infoln("parse");
			}
//			timer.end( "transform30" );			
			xsl.parse(inputSource);
//			timer.end( "transform40" );			
			return true;
		}
		catch (SAXParseException e)
		{
			printSAXParseException(e);
		}
		catch (SAXException e)
		{
			Log.infoln(e.getMessage());
		}
		catch (java.io.IOException e)
		{
			Log.infoln(e.toString());
		}
		return false;
	}

	synchronized static boolean transformFile(XSLProcessor xsl, OutputMethodHandlerImpl outputMethodHandler, java.io.File inputFile, java.io.File stylesheetFile, java.io.File outputFile)
	{
		Destination dest;
		if (outputFile == null)
		{
			dest = new FileDescriptorDestination(java.io.FileDescriptor.out);
		}
		else
		{
			dest = new FileDestination(outputFile);
		}
		outputMethodHandler.setDestination(dest);
		if (debug)
		{
			Log.infoln("transform");
		}
		return transform(xsl, fileInputSource(stylesheetFile), fileInputSource(inputFile));
	}

	synchronized static public InputSource fileInputSource(String str)
	{
		return fileInputSource(new java.io.File(str));
	}

	synchronized static public InputSource fileInputSource(java.io.File file)
	{
		String path = file.getAbsolutePath();
		String fSep = System.getProperty("file.separator");
		if (fSep != null && fSep.length() == 1)
		{
			path = path.replace(fSep.charAt(0), '/');
		}
		if (path.length() > 0 && path.charAt(0) != '/')
		{
			path = '/' + path;
		}
		try
		{
			return new InputSource(new java.net.URL("file", "", path).toString());
		}
		catch (java.net.MalformedURLException e)
		{
			Log.infoln("unexpected MalformedURLException in KOSXMLTransformer fileInputSource");
			return null;
		}
	}

	synchronized static void printSAXParseException(SAXParseException e)
	{
		String systemId = e.getSystemId();
		int lineNumber = e.getLineNumber();
		if (systemId != null)
		{
			Log.infoln(systemId + ":");
		}
		if (lineNumber >= 0)
		{
			Log.infoln(lineNumber + ":");
		}
		if (systemId != null || lineNumber >= 0)
		{
			Log.infoln(" ");
		}
		Log.infoln(e.getMessage() + " in KOSXSLTransformer printSAXParseException");
	}

	private static boolean debug = false;
	public boolean getDebug()
	{
		return debug;
	}
	public void setDebug(boolean debug)
	{
		XslTransformer.debug = debug;
	}

	// MJM 2004-09-23: Used to ensure that when data is put into CDATA nodes, that it is never a null String
	public synchronized static String getNonNullString(String theString)
	{
		if (theString == null)
		{
			return "";
		}
		else
		{
			return theString;
		}
	}
	
	private void setParameters( XSLProcessorImpl xsl )
	{
		Enumeration keys = getParameterTable().keys();
		while( keys.hasMoreElements() )
		{
			String key = (String) keys.nextElement();
			String val = (String) getParameterTable().get( key );
			xsl.setParameter( key, val );		
		}
	}

	private Hashtable parameterTable = null;
	public Hashtable getParameterTable() 
	{
		if ( parameterTable == null )
		{
			parameterTable = new Hashtable();
		}
		return parameterTable;
	}
	public void setParameterTable(Hashtable themeContentHitTable) 
	{
		this.parameterTable = themeContentHitTable;
	}

	/*
	public boolean doTransform( String xml, File xslFile, String htmlFileName) throws Exception
	{
		if ( xml == null ) { return false; }		
		if ( xslFile == null || !xslFile.exists() ) { }

		SAXBuilder saxDoc = new SAXBuilder();
		Document jDomDoc = saxDoc.build( new StringReader( xml ) );
		
		XSLProcessorImpl xsl = new XSLProcessorImpl();
		setParameters( xsl );
		setParser( xsl );

		xsl.setErrorHandler( new ErrorHandlerImpl() );
		OutputMethodHandlerImpl outputMethodHandler = new OutputMethodHandlerImpl( xsl );
		xsl.setOutputMethodHandler( outputMethodHandler );
		
		Destination dest;
		if (outputFile == null)
		{
			dest = new FileDescriptorDestination(java.io.FileDescriptor.out);
		}
		else
		{
			dest = new FileDestination(outputFile);
		}
		outputMethodHandler.setDestination( dest );

//		boolean succeeded = transformFile(xsl, outputMethodHandler, in, stylesheet, out);
//		boolean succeeded = transformFile(xsl, outputMethodHandler, in, stylesheet, out);
		
		InputSource inputSource = new InputSource( new StringReader( xml ) );
		InputSource stylesheetSource = new InputSource( xslFile );
		
		transform( xsl, InputSource stylesheetSource, InputSource inputSource)
		
		if (!succeeded) { return false; }
		return true;
	}
	*/
}