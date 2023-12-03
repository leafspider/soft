package leafspider.extract;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.ByteArrayOutputStream;

import javax.swing.text.html.HTML;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.w3c.tidy.Tidy;

import leafspider.util.*;

public class JTidyExtractor
{
	private static final String LINESEP = System.getProperty("line.separator", "\r\n");

	public static void main(String[] args)
	{
		try
		{			
			File input = new File( "C:\\Test\\detail.jsp%3Fkey=917330%26rc=cbb_sp%26p=3.htm" );
			File output = new File( "C:\\Test\\detail.jsp%3Fkey=917330%26rc=cbb_sp%26p=3.htm.txt" );
			
			JTidyExtractor jtt = new JTidyExtractor();
			jtt.extract(input, output);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Tidy myTidy = null;

	public JTidyExtractor()
	{
		myTidy = new Tidy();
		myTidy.setQuiet(true);
		myTidy.setBreakBeforeBR(true);
		myTidy.setShowWarnings(false);
		myTidy.setDropEmptyParas(false);
		myTidy.setDropFontTags(true);
		myTidy.setFixComments(true);
		myTidy.setHideEndTags(false);
		myTidy.setUpperCaseTags(false);
		myTidy.setUpperCaseAttrs(false);
		myTidy.setWord2000(true);
		myTidy.setRawOut(false);
		myTidy.setMakeClean(true);
		//myTidy.setEncloseBlockText(true);
		//myTidy.setEncloseText(true);
		//myTidy.setQuoteMarks(true);
		//myTidy.setQuoteNbsp(true);
		myTidy.setErrout( new PrintWriter( new ByteArrayOutputStream() ) );		// jmh 2009-02-18
	}

	public boolean extract(File inputFile, File outputFile)
	{
		InputStream theInput = null;
		try
		{
			theInput = new FileInputStream(inputFile);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return false;
		}

		boolean result = true;
		PrintWriter out = null;

		try
		{
			Document root = myTidy.parseDOM(theInput, null);
			Element rawDoc = root.getDocumentElement();

			String thisTitle = getTitle(rawDoc);
			setTitle( thisTitle );
			String meta = getMeta(rawDoc);
			String body = getBody(rawDoc);

			if (thisTitle != null || meta != null || body != null)
			{
				out = new PrintWriter(new BufferedWriter(new FileWriter(outputFile)));

//				if (thisTitle != null && !thisTitle.equals("")) out.write(thisTitle + LINESEP + LINESEP);					// jmh 2006-01-11
				if (meta != null && !meta.equals("")) out.write(meta + LINESEP + LINESEP);
				if (body != null && !body.equals("")) out.write(body + LINESEP);

				out.flush();
			}

			if (out == null) result = false;
		}
		catch (Exception e)
		{
//			Log.infoln("Exception: ", e);	// jmh 2011-02-10
			Log.infoln( "JTidyExtractor.extract: " + e.getMessage() );
			result = false;
		}
		finally
		{
			try
			{
				if (out != null) out.close();
			}
			catch (Exception e)
			{}
		}

		return result;
	}

	/**
	 * Gets the body text of the HTML document.
	 *
	 * @rawDoc the DOM Element to extract body Node from
	 * @return the body text
	 */
	private String getBody(Element rawDoc)
	{
		if (rawDoc == null)
		{
			return null;
		}
		String body = "";
		NodeList children = rawDoc.getElementsByTagName("body");
		if (children.getLength() > 0)
		{
			body = getText(children.item(0));
		}
		return body;
	}

	/**
	 * Gets the relevant meta elements of rawDoc
	 *
	 * @rawDoc the DOM Element to extract title Node from
	 * @return the title text
	 */
	protected String getMeta(Element rawDoc)
	{
		if (rawDoc == null)
		{
			return null;
		}
		String meta = "";

		NodeList children = rawDoc.getElementsByTagName("meta");
		
		if (children == null) return null;

		for (int i = 0; i < children.getLength(); i++)
		{
			Element currentElement = ((Element) children.item(i));
			if (currentElement.getNodeName().equalsIgnoreCase("meta"))
			{
				if (currentElement.getAttribute("name").equalsIgnoreCase("description") || currentElement.getAttribute("name").equalsIgnoreCase("keywords"))
				{
					meta += currentElement.getAttribute("content") + LINESEP;
				}
			}
		}
		return meta;
	}

	/**
	 * Extracts text from the DOM node.
	 *
	 * @param node a DOM node
	 * @return the text value of the node
	 */
	private String getText(Node node)
	{
		NodeList children = node.getChildNodes();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < children.getLength(); i++)
		{
			Node child = children.item(i);
			switch (child.getNodeType())
			{
				case Node.ELEMENT_NODE:
					String tagName = child.getNodeName();

					if (!tagName.equalsIgnoreCase("script") && !tagName.equalsIgnoreCase("style"))
					{
						HTML.Tag currentTag = HTML.getTag(tagName.toLowerCase());
						//System.out.println("Name: " + tagName + "\tTag: " + currentTag);

						if (currentTag != null)
						{
							if (currentTag.isBlock())
							{
								sb.append(LINESEP);
								//sb.append(LINESEP);
							}
							else if (currentTag.breaksFlow() || currentTag.isPreformatted())
							{
								sb.append(LINESEP);
							}
							else
							{
								//sb.append(" ");
							}
						}

						sb.append(getText(child));

						if (currentTag != null)
						{
							if (currentTag == HTML.Tag.LI)
							{}
							else if (currentTag == HTML.Tag.OPTION)
							{
								sb.append(" ");
							}
							else if (currentTag.isBlock())
							{
								sb.append(LINESEP);
								//sb.append(LINESEP);
							}
							else if (currentTag.breaksFlow() || currentTag.isPreformatted())
							{
								sb.append(LINESEP);
							}
						}
					}
					break;
				case Node.TEXT_NODE:
					//System.out.println(((Text) child).getData());
					sb.append(((Text) child).getData());
					break;
			}
		}
		return sb.toString().trim();
	}

	/**
	 * Gets the title text of the HTML document.
	 *
	 * @rawDoc the DOM Element to extract title Node from
	 * @return the title text
	 */
	protected String getTitle(Element rawDoc)
	{
		if (rawDoc == null)
		{
			return null;
		}
		String title = "";
		NodeList children = rawDoc.getElementsByTagName("title");
		if (children.getLength() > 0)
		{
			Element titleElement = ((Element) children.item(0));
			Text text = (Text) titleElement.getFirstChild();

			if (text != null)
			{
				title = text.getData();
			}
		}
		return title;
	}
	
	private String title = null;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getTitle( File inputFile )
	{
		try
		{
			String ext = Util.getFileExtension( inputFile );
			// jmh 2009-06-17
			if ( ext.equalsIgnoreCase("HTM") || 
				ext.equalsIgnoreCase("HTML") || 
				ext.equalsIgnoreCase("SHTML") || 
				ext.equalsIgnoreCase("PHP") || 
				ext.equalsIgnoreCase("ASP") || 
				ext.equalsIgnoreCase("CFM")  || 
				ext.equalsIgnoreCase("MSPX")  || 
				ext.equalsIgnoreCase("ASPX")  || 
				ext.equalsIgnoreCase("JHTML")  || 
				ext.equalsIgnoreCase("JSP") )
			{
				InputStream theInput = null;
				theInput = new FileInputStream( inputFile );
				Document root = myTidy.parseDOM(theInput, null);
				Element rawDoc = root.getDocumentElement();
				return getTitle( rawDoc );
			}
			else { return null; } 
		}
		catch (Exception e)
		{
			Log.warnln( "Exception: ", e );
			return null;
		}
	}
}
