package leafspider.util;

import java.io.*;

public class Execute
{
	public static void main(String[] args)
	{
		try
		{
			//			String flags = "command.com /E:1900 /C ";			// jmh Might be useful for non-exe commands
			//			String[] cmdList = { "C:\\KOS\\KeyViewBin\\htmlini.exe", "C:\\KOS\\KeyViewBin\\nofrills.ini", "C:\\TestDocs\\ExtractorTest.doc", "C:\\TestDocs\\Extractor Test29.doc.htm" };
			//			String[] cmdList = { "command.com", "/E:1900", "/C", "C:\\Handle\\handle", "-p", "nlnotes.exe", "Notes", ">", "C:\\Handle\\output.txt" };
			//			String[] cmdList = { "command.com", "/C", "C:\\Handle\\handle", "-p", "nlnotes.exe", ">", "C:\\Handle\\output.txt" };
			//			Execute.execWait( cmdList );

//			Report.reportFileHandles("javaw.exe", "validation", "C:\\Handle\\output.txt");
			
//			String url = "http://www.bobbins.com";
			String url = "http://127.0.0.1:7958/kgs/read/asda/upload/701428export9txt/rview.htm";
			
			String[] cmdList = { "C:\\Program Files (x86)\\MiniCap\\MiniCap.exe", "-save", "\"C:\\Temp\\MiniCap\\screen_appname$$uniquenum$_$date$.jpg\"", "-closeapp", "-exit", "-sleep", "4", "-capturerunapp", "-run", "\"C:\\Program Files (x86)\\Internet Explorer\\iexplore.exe\"", url };
			Execute.exec( cmdList );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static boolean debug = false;

	public synchronized static boolean exec(String[] cmdList) throws Exception
	{
		return (exec(cmdList, false, false, 0, null));
	}

	public synchronized static boolean execWait(String[] cmdList) throws Exception
	{
		return (exec(cmdList, false, true, 0, null));
	}

	public synchronized static boolean execTimeout(String[] cmdList, long timeout) throws Exception
	{
		return (exec(cmdList, false, true, timeout, null));
	}

	public synchronized static boolean execPrint(String[] cmdList) throws Exception
	{
		return (exec(cmdList, true, false, 0, null));
	}

	public synchronized static boolean execPrint(String[] cmdList, File outputFile) throws Exception
	{
		return (exec(cmdList, true, false, 0, outputFile));
	}

	private synchronized static boolean exec(String[] cmdList, boolean printResults, boolean wait, long timeout, File outputFile) throws Exception
	{
		String command = new String();
		for (int i = 0; i < cmdList.length; i++)
		{
			command = command + cmdList[i] + " ";
		}
		command = command.trim();
//		Log.debugln( "Executing \"" + command + "\".");
//		Log.infoln( "Executing " + command );

		// Start running command, returning immediately
		Process proc = null;
		try
		{
			proc = Runtime.getRuntime().exec(cmdList);
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			proc.destroy();
			return false;
		}

		// Print the output. Since we read until there is no more input, this causes us to wait until the process is completed
		if (printResults)
		{
			BufferedReader commandResult = new BufferedReader(new InputStreamReader(proc.getInputStream()));
			PrintStream out = null;
			String st = null;
			try
			{
				if (outputFile == null)
					out = System.out;
				else
					out = Util.getPrintStream(outputFile.getAbsolutePath());

				while ((st = commandResult.readLine()) != null)
				{
					out.println(st);
					// Ignore read errors; they mean process is done
				}
				commandResult.close();
				if (out != System.out)
				{
					out.close();
				}

				if (proc.exitValue() != 0)
				{
					Log.warnln("Exception: ", new Exception("Process ExitValue != 0 (" + proc.exitValue() + ")" ));
					return false;
				}
			}
			catch (Exception e)
			{
				Log.warnln("Exception: ", e);
			}
			finally
			{
				try
				{
					if (commandResult != null)
					{
						commandResult.close();
					}
					if (out != null && out != System.out)
					{
						out.close();
					}
				}
				catch (IOException e)
				{}
			}
		}
		else if (wait)
		{
			// If you don't print the results, then you need to call waitFor to stop until the process is completed
			try
			{
				ProcessRunner runner = new ProcessRunner(proc);
				Thread thread = new Thread(runner);
				thread.setDaemon(true);
				thread.start();
				thread.join(timeout);
				int returnVal = proc.exitValue();
				
				// jmh 2007-02-14 Hack for XPDF support
				if ( cmdList[0].indexOf( "xpdf" ) > -1 )
				{
					// Successful XPDF should returns 0
					if ( returnVal == 1 )
					{
						throw new Exception("Password protected file: " + cmdList[1] );
					}
					else if ( returnVal == 3 )
					{
						throw new Exception("Empty text extract: " + cmdList[1] );						
					}
				}
				else
				{
//					Log.infoln("\tExecute returnVal=" + returnVal);
					// Successful Cimmitry returns 1, Parser returns 11 
	//				if (returnVal != 0 && returnVal != 1 && returnVal != 11)		// jmh 2005-06-10 Parser returns 11... 16 for English, French etc
					if (returnVal != 0 && returnVal != 1 && returnVal < 11 && returnVal > 16)
					{
						Log.debugln("\tExecute returnVal=" + returnVal);
						throw new Exception("Process did not yield");
					}
				}
			}
			catch (IllegalThreadStateException itse)
			{
//				Log.infoln("Process timed out");
//				return false;
				throw itse;
			}
			catch (Exception e)
			{
				Log.warnln("Exception: ", e);				
				return false;
			}
			finally
			{
				proc.destroy();
			}
		}
		return true;
	}

	static class ProcessRunner implements Runnable
	{
		public ProcessRunner(Process proc)
		{
			this.proc = proc;
		}

		public void run()
		{
			try
			{
				returnVal = proc.waitFor();
				Log.debugln("\tExecute returnVal=" + returnVal);

				// XPDF returns 0, Cimmitry returns 1, Parser returns 11 
				if (returnVal != 0 && returnVal != 1 && returnVal != 11)
				{
					Log.infoln("\tExecute returnVal=" + returnVal);
					throw new Exception("Process did not yield");
				}
			}
			catch (Exception e)
			{
				if (debug)
				{
					Log.warnln("Exception: ", e);
				}
			}
			finally
			{
				proc.destroy();
			}
		}

		Process proc = null;

		public Process getProc()
		{
			return proc;
		}

		public void setProc(Process proc)
		{
			this.proc = proc;
		}

		int returnVal = 0;

		public int getReturnVal()
		{
			return returnVal;
		}

		public void setReturnVal(int returnVal)
		{
			this.returnVal = returnVal;
		}

	}

}
