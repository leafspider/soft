package soft.batch;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;

import leafspider.util.Log;

public class BatchServlet extends HttpServlet
{
	private BatchAgent agent;

	public void init(ServletConfig config)
	{
		try
		{
			Log.infoln("BatchServlet.init");			
			agent = new BatchAgent();
			agent.start();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
	}

	public void destroy()
	{
		Log.infoln( "" );
		Log.infoln( "BatchServlet shutdown" );
		
		if (agent != null)
		{
			agent.setKeepRunning(false);
			try
			{
				while (agent.isAlive())		// Busy loop to wait until agent has stopped.
				{
					Thread.sleep(200);
				}
				Log.infoln( "BatchAgent shutdown complete" );
			}
			catch (Exception e)
			{
				Log.warnln("Exception: ", e);
			}
		}
		Log.infoln( "BatchServlet shutdown complete" );

		/*
		try
		{
			if ( isCompact() )
			{
				getData().getDatabase().compact();
			}
			else
			{
				getData().getDatabase().disconnect();					
			}
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
		}
		*/
	}

	/*
	private boolean isCompact()
	{
		File file = new File( getData().getIndexer().getTextIndex().getDbPath() + "\\db.compact" );
		if ( file != null && file.exists() )
		{
			file.delete();
			return true;
		}
		return false;	
	}
	*/
}
