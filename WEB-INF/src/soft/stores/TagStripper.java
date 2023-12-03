package soft.stores;

import java.io.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.text.html.parser.*;

import leafspider.util.Log;


/** Uses Swing classes to strip everything but the text from HTML */
public class TagStripper extends HTMLEditorKit.ParserCallback
{
	private Writer out;
	private boolean inBlock = false;
	private String lineSeparator = System.getProperty("line.separator", "\r\n");

	public String getLineSeparator()
	{
		return lineSeparator;
	}

	public void setLineSeparator(String thisVal)
	{
		lineSeparator = thisVal;
	}

	public static void main(String[] args)
	{
		try
		{
			for (int i = 1; i <= 5; i++)
			{
				String inputFile = "C:\\KOSCatalog\\HTMLTest\\HTMLTest" + i + ".html";
				String outFile = "C:\\KOSCatalog\\HTMLTest\\HTMLTest" + i + ".html.txt";

				System.out.println(System.getProperty("line.separator", "\r\n") + inputFile);

				InputStream in = new FileInputStream(new File(inputFile));
				TagStripper callback = new TagStripper(new File(outFile));

				new ParserDelegator().parse(new InputStreamReader(in), callback, true);
			}

			/* Performance Testing 
			KOSIntrinsics kos = new KOSIntrinsics();	
			File root = new File( "C:\\XMLTest" );
			File[] files = root.listFiles();
			for ( int i = 0; i < files.length; i++ )
			{
				String extract = callback.getTextFromFile( files[i].getPath() );
				TextExtractor extractor = new TextExtractor();
				String convert = extractor.swingConvertHTMLToText( extract );
				
				System.out.println( 
					kos.getTaxonomy().filterOutNotAlphaCharacters(
						Util.replaceSubstring(
							Util.replaceSubstring(
								Util.replaceSubstring( convert, ">", "", true
								), "<", "", true
							), "&quote", "'", true)
						)
					);
				System.out.println( "----------------------------------- Done " + i );
			}
			*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	/* Inserted for Performance Testing only
	public String getTextFromFile(String thisFilePath)
	{
		try
		{
			java.io.BufferedReader thisFile = Util.getBufferedReader(thisFilePath);
			String tempLine = new String("");
			String thisSummary = "";
			while (tempLine != null)
			{
				tempLine = thisFile.readLine();
				if (tempLine == null)
				{
					continue;
				}
				thisSummary = (thisSummary + " " + tempLine).trim();
			}
			return thisSummary;
		}
		catch (Exception e)
		{
			Log.warn("Exception: ", e);
			return "";
		}
	}
	*/

	public TagStripper(File out)
	{
		try
		{
			this.out = new OutputStreamWriter(new FileOutputStream(out));
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public TagStripper(Writer out)
	{
		this.out = out;
	}

	public void handleSimpleTag(HTML.Tag tag, MutableAttributeSet attributes, int position)
	{
		// Log.infoln("Simple Tag: " + tag);
		try
		{
			if (tag == HTML.Tag.SCRIPT || tag.toString().equals("style") || tag == HTML.Tag.STYLE)
			{
				inBlock = true;
			}
			else if (tag.isBlock() || tag.breaksFlow())
			{
				out.write(lineSeparator);
			}
			else
			{
				// Log.infoln("Simple Tag: " + tag);
			}
		}
		catch (IOException e)
		{
			Log.warnln("Exception", e);
		}
		return;
	}

	public void handleStartTag(HTML.Tag tag, MutableAttributeSet attributes, int position)
	{
		// Log.infoln("Start Tag: " + tag);
		try
		{
			if (tag == HTML.Tag.SCRIPT || tag.toString().equals("style") || tag == HTML.Tag.STYLE)
			{
				inBlock = true;
			}
			else if (tag.isBlock() || tag.breaksFlow())
			{
				out.write(lineSeparator);
			}
			else
			{
				// Log.infoln("Start Tag: " + tag);
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception", e);
		}
		return;
	}

	public void handleEndTag(HTML.Tag tag, int position)
	{
		// Log.infoln("End Tag: " + tag);
		try
		{
			if (tag == HTML.Tag.SCRIPT || tag.toString().equals("style") || tag == HTML.Tag.STYLE)
			{
				inBlock = false;
			}
			else if (tag == HTML.Tag.HTML)
			{
				this.flush(); // Work around bug in the parser that fails to call flush
			}
			else if ((tag.isBlock() || tag.breaksFlow()) && tag != HTML.Tag.LI)
			{
				out.write(lineSeparator);
			}
			else
			{
				// Log.infoln("End Tag: " + tag);
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception", e);
		}

	}

	public void handleText(char[] text, int position)
	{
		// Log.infoln("Text: " + new String(text));
		try
		{
			if (!inBlock)
			{
				out.write(text);
				out.flush();
			}
		}
		catch (IOException e)
		{
			Log.infoln("Exception", e);
		}
	}

	public void flush()
	{
		try
		{
			out.flush();
		}
		catch (IOException e)
		{
			Log.warnln("Exception", e);
		}
	}

	public void handleEndOfLineString(String eol)
	{}

	public void handleComment(char[] data, int pos)
	{
		/*
		Log.info("Comment: ");
		Log.info(data);
		*/
	}

	public void handleError(String errorMsg, int pos)
	{
		// Log.infoln("Parsing error: " + errorMsg + " at " + pos);
	}

}
