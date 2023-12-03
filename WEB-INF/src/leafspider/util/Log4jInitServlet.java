package leafspider.util;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.PropertyConfigurator;

public class Log4jInitServlet extends HttpServlet
{
	public void init()
	{
		String prefix = getServletContext().getRealPath("/");
		String file = getInitParameter("log4j-init-file");

		if (file != null)
		{
			PropertyConfigurator.configure(prefix + file);
		}
	}

	public void doGet(HttpServletRequest req, HttpServletResponse res)
	{}
}