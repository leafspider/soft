package leafspider.util;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileCopy
{
	private String toFile = "";
	public String getToFile()
	{
		return toFile;
	}
	public void setToFile(String thisFile)
	{
		toFile = thisFile;
	}
	public void setToFile(File thisFile)
	{
		toFile = thisFile.getPath();
	}

	private String fromFile = "";
	public String getFromFile()
	{
		return fromFile;
	}
	public void setFromFile(String thisFile)
	{
		fromFile = thisFile;
	}
	public void setFromFile(File thisFile)
	{
		fromFile = thisFile.getPath();
	}
	
	public void copy() throws Exception
	{
		FileChannel in = null;
		FileChannel out = null;
		try
		{
			// jmh 2004-02-12
			if (toFile.equals(fromFile))
			{
				return;
			}
			File inFile = new File(fromFile);
			if (!inFile.exists())
			{
				throw new Exception("File does not exist: " + fromFile);
			}
			if ((new File(fromFile)).isDirectory())
			{
				return;
			} // jmh 2004-02-23
			in = new FileInputStream(fromFile).getChannel();
			out = new FileOutputStream(toFile).getChannel();
			
			int size = (int) in.size();
			ByteBuffer buf = null; //sep 5 2003 dbm. Moved buffer defn up in scope
			if (size < 10000000)
			{
				buf = ByteBuffer.allocateDirect((int) in.size());
				buf.clear();
				in.read(buf);
				buf.flip();
				out.write(buf);
			}
			else
			{
				buf = ByteBuffer.allocateDirect(10000000);
				while (true)
				{
					buf.clear();
					if (in.read(buf) < 0)
					{
						break;
					}
					buf.flip();
					out.write(buf);
				}
			}
			in.close();
			out.close();
			buf = null;
		}
		catch( Exception e )
		{
			throw e;
		}
		finally
		{
			if ( in != null ) { in.close(); }
			if ( out != null ) { out.close(); }
		}		
	}

	// MJM 2005-01-18: Added function that uses cleaner NIO code
	public static void copy(String srcFilename, String dstFilename) throws Exception
	{
		if ( srcFilename.equals( dstFilename ) ) { return; }

		FileChannel srcChannel = null;
		FileChannel dstChannel = null;
		try
		{
			// Create channel on the source        
			srcChannel = new FileInputStream(srcFilename).getChannel();

			// Create channel on the destination        
			dstChannel = new FileOutputStream(dstFilename).getChannel();

			// Copy file contents from source to destination        
			dstChannel.transferFrom(srcChannel, 0, srcChannel.size());

			// Close the channels        
			srcChannel.close();
			dstChannel.close();
		}
		catch ( Exception e )
		{
			throw e;
		}
		finally
		{
			if ( srcChannel != null ) { srcChannel.close(); }
			if ( dstChannel != null ) { dstChannel.close(); }
		}		
	}

}
