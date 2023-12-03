package leafspider.spider;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

public class SpiderLogAppender extends FileAppender 
{
	public SpiderLogAppender() {}

	public SpiderLogAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize) throws IOException 
	{
		super(layout, filename, append, bufferedIO, bufferSize);
	}
	
	public SpiderLogAppender(Layout layout, String filename, boolean append) throws IOException 
	{
		super(layout, filename, append);
	}
	
	public SpiderLogAppender(Layout layout, String filename) throws IOException 
	{
		super(layout, filename);
	}
	
	public void activateOptions() 
	{
		if (fileName != null) 
		{
			try 
			{
				fileName = newFileName();
				setFile(fileName, fileAppend, bufferedIO, bufferSize);
			} 
			catch (Exception e) 
			{
//				errorHandler.error("Error while activating log options", e, ErrorCode.FILE_OPEN_FAILURE);
				e.printStackTrace();
			}
		}
	}
	
	private String newFileName() 
	{
		if (fileName != null) 
		{
			final String DOT = ".";
			final String HYPHEN = "-";
			final File logFile = new File(fileName);
			final String fileName = logFile.getName();
			String newFileName = "";
		
			final int dotIndex = fileName.indexOf(DOT);
			if (dotIndex != -1) 
			{
				// the file name has an extension. so, insert the time stamp
				// between the file name and the extension
				newFileName = fileName.substring(0, dotIndex) + HYPHEN
						+ +System.currentTimeMillis() + DOT
						+ fileName.substring(dotIndex + 1);
			} 
			else 
			{
				// the file name has no extension. So, just append the timestamp
				// at the end.
				newFileName = fileName + HYPHEN + System.currentTimeMillis();
			}
			setNewLogFile( new File( logFile.getParent() + File.separator + newFileName ) );
			return getNewLogFile().getAbsolutePath();
		}
		return null;
	}
	
	private File newLogFile = null;
	public File getNewLogFile() {
		return newLogFile;
	}
	public void setNewLogFile(File logFile) {
		this.newLogFile = logFile;
	}
}