package leafspider.extract;

import java.io.*;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.util.*;

import leafspider.util.*;

public class WordDocumentTester
{
	public static void main(String[] args)
	{
		File inputFolder = new File("D:\\TestDocs\\Word");
		BufferedWriter out;
		try
		{
			out = new BufferedWriter(new FileWriter("C:\\WordTest.txt"));

			String[] fileList = Util.getCompleteFileList(new File("D:\\"));
			System.out.println("Testing: " + fileList.length + " files.");
			out.write("Testing: " + fileList.length + " files.");
			int countWorking = 0;
			int countNonDoc = 0;
			int countWord = 0;
			for (int i = 0; i < fileList.length; i++)
			{
				File currentFile = new File(fileList[i]);
				String inFileName = currentFile.getAbsolutePath();

				if (currentFile.isDirectory() || !Util.getFileExtension(currentFile).equalsIgnoreCase("DOC"))
				{
					countNonDoc++;
					continue;
				}

				WordDocumentTester we = new WordDocumentTester(new FileInputStream(currentFile));

				boolean isSupported = we.isTextminerSupported();
				boolean isWord = we.isWordFile();

				if (isSupported)
				{
					countWorking++;
				}
				if (isWord)
				{
					countWord++;
				}

				System.out.println(System.getProperty("line.separator", "\r\n") + i + ": " + currentFile.getAbsolutePath());
				System.out.println("IsWord: " + isWord + "\t\tSupported: " + isSupported + "\t\tVersion: " + we.getVersion());
				out.write("\r\n" + i + ": " + currentFile.getAbsolutePath() + "\r\n");
				out.write("IsWord: " + isWord + "\t\tSupported: " + isSupported + "\t\tVersion: " + we.getVersion() + "\r\n");
			}
			out.write(System.getProperty("line.separator", "\r\n") + "Number of word files: " + countWord + "\r\n");
			out.write("Number of working files: " + countWorking + " out of " + (fileList.length - countNonDoc) + "\r\n");
			out.write("Number of non-DOC files: " + countNonDoc + "\r\n");
			System.out.println(System.getProperty("line.separator", "\r\n") + "Number of word files: " + countWord);
			System.out.println("Number of working files: " + countWorking + " out of " + (fileList.length - countNonDoc));
			System.out.println("Number of non-DOC files: " + countNonDoc);
			out.close();
		} catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private byte[] header;

	public WordDocumentTester(InputStream _in)
	{
		inputStream = _in;
		init();
	}

	public WordDocumentTester(String filename)
	{
		try
		{
			inputStream = new FileInputStream(filename);
		}
		catch (Exception e)
		{}
		init();
	}

	private void init()
	{
		try
		{
			POIFSFileSystem fsys = new POIFSFileSystem(inputStream);
			DocumentEntry headerProps = (DocumentEntry) fsys.getRoot().getEntry("WordDocument");
			DocumentInputStream din = new DocumentInputStream(headerProps);
			header = new byte[headerProps.getSize()];
			din.read(header);
			din.close();
		}
		catch (Exception e)
		{
			header = null;
		}
	}

	public boolean isTextminerSupported()
	{
		if (header == null)
			return false;

		int info = LittleEndian.getShort(header, 0xa);
		if ((info & 0x4) != 0)
		{
			return false;
		}
		else if ((info & 0x100) != 0)
		{
			return false;
		}
		else if (getVersion() == 104)
		{
			return false;
		}
		return true;
	}

	public boolean isWordFile()
	{
		if (header == null)
			return false;
		else
			return true;
	}

	public int getVersion()
	{
		if (header == null)
			return 0;

		// determine the version of Word this document came from.
		int nFib = LittleEndian.getShort(header, 0x2);

		switch (nFib)
		{
			default:
				return nFib;
		}
	}
	
	private InputStream inputStream;
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream instream) {
		this.inputStream = instream;
	}


}