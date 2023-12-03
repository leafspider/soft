package leafspider.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Properties;

import leafspider.util.*;

import org.jdom2.Element;


public class ServerContext// implements Serializable
{
	private static String applicationName = "soft";
	public static String getApplicationName()
	{
		return applicationName;
	}

	private static ServerContext context = null;
	private static String implementationVersion = null;
	private static String specificationVersion = null;

	static
	{
		context = new ServerContext();
		Log.infoln( ServerContext.getTomcatFolder() );
	}

	/**
	 * @return The application drive with a colon (e.g. C:)
	 */
	public synchronized static String getRootDrive()
	{
		return getTomcatFolder().substring(0, 2);
	}

	/** Instantiates a Singleton */
	public static ServerContext getContext()
	{
		return context;
	}

	public static String getContextProperty(String paramName) throws Exception
	{
		return getContext().getContextProperties().getProperty( paramName );
	}
	
	public static String getContextProperty(String paramName, String defaultValue ) throws Exception
	{
		String val = getContextProperty( paramName );
		if ( val != null && !val.trim().equals( "" ) ) { return val; } 
		return defaultValue;
	}

	public static String getImplementationVersion()
	{
		if (implementationVersion == null || implementationVersion.equalsIgnoreCase("null")) readManifest();
		return implementationVersion;
	}
	
	private static String tomcatFolder = null;
	public synchronized static String getTomcatFolder()
	{
		if ( tomcatFolder == null )
		{
			File tmp = new File( "tmp.txt" );
			File binfolder = new File(tmp.getAbsolutePath()).getParentFile();
			System.out.println( "tmpbin=" + binfolder.getAbsolutePath() );
			tomcatFolder = binfolder.getParentFile().getAbsolutePath();
			tomcatFolder = tomcatFolder.replace("\\webapps", "");		// Handle running in Eclipse
		}
		return tomcatFolder;
	}
	
	public synchronized static String getApplicationFolder()
	{
		return getTomcatFolder() + "\\webapps\\" + getApplicationName();
	}

	public synchronized static String getApplicationWebInfFolder()
	{
		return getApplicationFolder() + "\\WEB-INF";
	}

	public synchronized static String getApplicationMetaInfFolder()
	{
		return getApplicationFolder() + "\\META-INF";
	}

	public static String getSpecificationVersion()
	{
		if (specificationVersion == null || specificationVersion.equalsIgnoreCase("null")) readManifest();

		return specificationVersion;
	}

	public static void main(String[] args)
	{
		try
		{
			//		String fileName = "C:\\KOS\\admin\\kos-web.xml";
			//		ServerContext serverContext = new ServerContext( fileName );
			//		serverContext.writeXml( Util.getPrintStream( fileName ) );
			//		System.out.println("ldsDtdPath=" + ServerContext.getContextParameter("ldsDtdPath"));
			//		System.out.println("catalogRootFolder=" + ServerContext.getCatalogRootFolder());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private static void readManifest()
	{
		File theFile = new File(ServerContext.getApplicationFolder() + "\\META-INF\\MANIFEST.MF");
		BufferedReader in = null;

		if (theFile.exists())
		{
			try
			{
				in = Util.getBufferedReader(theFile);

				String temp = null;
				while ((temp = in.readLine()) != null)
				{
					if (temp.indexOf("Specification-Version:") > -1)
					{
						specificationVersion = temp.substring(new String("Specification-Version: ").length());
					}
					else if (temp.indexOf("Implemention-Version: ") > -1)
					{
						implementationVersion = temp.substring(new String("Implemention-Version: ").length());
					}

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
					if (in != null)
					{
						in.close();
					}
				}
				catch (Exception e) {}
			}
		}
	}

	private Element getContextParamElement(String name, String value, String description)
	{
		try
		{
			Element paramElement = new Element("context-param");

			Element paramNameElement = new Element("param-name");
			paramNameElement.addContent(name);
			paramElement.addContent(paramNameElement);

			Element paramValueElement = new Element("param-value");
			paramValueElement.addContent(value);
			paramElement.addContent(paramValueElement);

			Element descriptionElement = new Element("description");
			descriptionElement.addContent(description);
			paramElement.addContent(descriptionElement);

			return paramElement;
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}
	
	private String contextFilePath = null;
	public String getContextFilePath()
	{
		return contextFilePath;
	}
	public void setContextFilePath(String thisVal)
	{
		contextFilePath = thisVal;
	}

	private long lastLoaded = 0;
	private Properties contextProperties = null;
	public Properties getContextProperties() throws Exception
	{
		if ( contextProperties == null || getContextFile().lastModified() > lastLoaded )
		{
			contextProperties = new Properties();
			contextProperties.load( new FileInputStream( getContextFile() ) );
			lastLoaded = DateUtils.getLong();
		}
		return contextProperties;
	}

	public static File getContextFile()
	{
		return new File( getConfigFolder().getAbsolutePath() + "\\context.properties" );
	}

	public static File getConfigFolder()
	{
		return new File( getApplicationFolder() + "\\conf" );
	}

	public static File getProjectsFolder()
	{
		return new File( getApplicationFolder() + "\\data" );
	}

	public static File getDataFolder()
	{
		return new File( getApplicationFolder() + "\\data" );
	}

	public static File getSkinsFolder()
	{
		return new File( getApplicationFolder() + "\\skins" );
	}

	public static File getListsFolder() {
		return new File( getApplicationFolder() + "\\conf\\lists" );
	}

	public static String getLocalHost() throws Exception
	{
		return getContextProperty( "localhost" );
	}

	public static File getDebugFile()
	{
		return new File( getConfigFolder().getAbsolutePath() + "\\debug.properties" );
	}
}
