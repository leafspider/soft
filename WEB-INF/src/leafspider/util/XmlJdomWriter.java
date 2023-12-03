package leafspider.util;

import java.io.*;

import org.jdom2.output.*;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.ProcessingInstruction;
import java.util.HashMap;

public class XmlJdomWriter
{
	public XmlJdomWriter(String outputFilename, String encoding)
	{
		/*
		// Set up the outputter
//		Format format = Format.getCompactFormat();
//		Format format = Format.getPrettyFormat();
		Format format = Format.getRawFormat();
		format.setEncoding( encoding );
		format.setExpandEmptyElements( false );
		format.setIndent("\t");		// Semantica can't cope with this
		format.setLineSeparator( "\n" );
		this.outputter = new XMLOutputter( format ); 
		*/
		setOutputFilename( outputFilename );
		setXmlEncodingType( encoding );
	}

	public XmlJdomWriter() { }
	
	public XmlJdomWriter(String encoding)
	{
		setXmlEncodingType( encoding );
	}
	
	public boolean writeOutput(org.jdom2.Document theDocument, StringWriter writer )
	{
		boolean result = true;
		if ( getStyleSheet() != null )
		{			
			HashMap piMap = new HashMap( 2 );
			piMap.put( "type", "text/xsl" );
			piMap.put( "href", getStyleSheet() );
			ProcessingInstruction pi = new ProcessingInstruction( "xml-stylesheet", piMap );
			theDocument.getContent().add( 0, pi );
		}
		try
		{
			getOutputter().output( theDocument, writer );
		}
		catch (Exception e)
		{
			result = false;
			Log.warnln("Exception: ", e);
		}
		return result;
	}

	public boolean writeOutput(org.jdom2.Document theDocument)
	{		
		boolean result = true;
		FileOutputStream out = null;
		
		if ( getStyleSheet() != null )
		{
			HashMap piMap = new HashMap( 2 );
			piMap.put( "type", "text/xsl" );
			piMap.put( "href", getStyleSheet() );
			ProcessingInstruction pi = new ProcessingInstruction( "xml-stylesheet", piMap );
			theDocument.getContent().add( 0, pi );
		}

		try
		{
			out = new FileOutputStream( getOutputFilename() );
			getOutputter().output( theDocument, out );
		}
		catch (Exception e)
		{
			result = false;
//			Log.warnln("Exception: ", e);
			e.printStackTrace();
		}
		finally
		{
			try
			{
				if (out != null) { out.close(); }
			}
			catch (java.io.IOException ioe) 
			{
				ioe.printStackTrace();
			}
		}
		return result;
	}
	
	private String styleSheet = null;
	public String getStyleSheet()
	{
		return styleSheet;
	}
	public void setStyleSheet(String styleSheet)
	{
		this.styleSheet = styleSheet;
	}
	
	private String indent = "    ";
	public String getIndent() {
		return indent;
	}
	public void setIndent(String indent) {
		this.indent = indent;
	}
	
	private String xmlEncodingType = null;
	public String getXmlEncodingType()
	{
		if ( xmlEncodingType == null )
		{
//			xmlEncodingType = "UTF-8";		// jmh 2008-04-06
			xmlEncodingType = "ISO-8859-1";
		}
		return xmlEncodingType;
	}
	public void setXmlEncodingType(String xmlEncodingType) {
		this.xmlEncodingType = xmlEncodingType;
	}
	
	private String lineSeparator = "\n";
	public String getLineSeparator() {
		return lineSeparator;
	}
	public void setLineSeparator(String lineSeparator) {
		this.lineSeparator = lineSeparator;
	}
	
	private boolean expandEmptyElements = false;
	public boolean isExpandEmptyElements() {
		return expandEmptyElements;
	}
	public void setExpandEmptyElements(boolean expandEmptyElements) {
		this.expandEmptyElements = expandEmptyElements;
	}

	private String outputFilename = null;
	public String getOutputFilename() {
		return outputFilename;
	}
	public void setOutputFilename(String outputFilename) {
		this.outputFilename = outputFilename;
	}

	private XMLOutputter outputter = null;
	public XMLOutputter getOutputter() 
	{
		if ( outputter == null )
		{
			Format format = Format.getRawFormat();
			format.setEncoding( getXmlEncodingType() );
			format.setExpandEmptyElements( false );
//			format.setIndent( getIndent() );		// jmh 2008-03-07
//			format.setLineSeparator( getLineSeparator() );		// jmh 2008-03-03
			outputter = new XMLOutputter( format ); 
		}
		return outputter;
	}
	public void setOutputter(XMLOutputter outputter) {
		this.outputter = outputter;
	}
	
	public String writeToString( Element rootElem )
	{
		StringWriter writer = new StringWriter();
		Document xdoc = new Document( rootElem );
		writeOutput( xdoc, writer );		
		return writer.toString(); 
	}

}
