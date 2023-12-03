package soft.toffee;

import leafspider.util.Log;

import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;

public class FriendsServlet  extends HttpServlet
{
    private FriendsAgent agent;

    public void init(ServletConfig config) {
        try {
            Log.infoln("FriendsServlet.init");
            agent = new FriendsAgent();
            agent.start();
        }
        catch (Exception e) {
            Log.warnln("Exception: ", e);
        }
    }

    public void destroy()
    {
        Log.infoln( "" );
        Log.infoln( "FriendsServlet shutdown" );

        if (agent != null)
        {
            agent.setKeepRunning(false);
            try {
                while (agent.isAlive()) {		// Busy loop to wait until agent has stopped.
                    Thread.sleep(200);
                }
                Log.infoln( "FriendsAgent shutdown complete" );
            }
            catch (Exception e)
            {
                Log.warnln("Exception: ", e);
            }
        }
        Log.infoln( "FriendsServlet shutdown complete" );
    }
}
