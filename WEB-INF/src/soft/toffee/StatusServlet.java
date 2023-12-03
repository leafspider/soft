package soft.toffee;

import leafspider.util.Log;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

public class StatusServlet extends HttpServlet
{
    private StatusAgent agent;

    public void init(ServletConfig config) {
        try {
            Log.infoln("StatusServlet.init");
            agent = new StatusAgent();
            agent.start();
        }
        catch (Exception e) {
            Log.warnln("Exception: ", e);
        }
    }

    public void destroy()
    {
        Log.infoln( "" );
        Log.infoln( "StatusServlet shutdown" );

        if (agent != null)
        {
            agent.setKeepRunning(false);
            try {
                while (agent.isAlive()) {		// Busy loop to wait until agent has stopped.
                    Thread.sleep(200);
                }
                Log.infoln( "StatusAgent shutdown complete" );
            }
            catch (Exception e)
            {
                Log.warnln("Exception: ", e);
            }
        }
        Log.infoln( "StatusServlet shutdown complete" );
    }
}
