package soft.stores;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.hibernate.*;
import org.hibernate.cfg.*;


public class HibernateUtil 
{
	/*
    private static final SessionFactory sessionFactory;

    static 
    {
        try 
        {
        	// Create the SessionFactory from hibernate.cfg.xml
        	sessionFactory = new Configuration().configure().buildSessionFactory();
        } 
        catch (Throwable ex) 
        {
            // Make sure you log the exception, as it might be swallowed
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
    */

	public static SessionFactory getSessionFactory( String project ) throws Exception
	{
		return getSessionFactory( getProperties( getProjectPropertiesFile( project ) ) );
	}
	
    public static String getProjectPropertiesText( String project )
    {
    	String dataFolder = Util.replaceSubstring( ServerContext.getDataFolder().getAbsolutePath(), "\\", "/" );    	
    	String txt = "hibernate.dialect=org.hibernate.dialect.HSQLDialect\r\n" + 
    				 "hibernate.connection.driver_class=org.hsqldb.jdbcDriver\r\n" +
    				 "hibernate.connection.url=jdbc:hsqldb:file:" + dataFolder + "/" + Util.replaceSubstring( project, " ", "_" ) + "/db/\r\n" +
    				 "hibernate.connection.databaseName=" + project + "\r\n" +
    				 "hibernate.connection.username=sa\r\n" +
    				 "hibernate.connection.password=\r\n";
    	return txt;
    }

	private static File getProjectPropertiesFile( String project ) 
	{
        return new File( ServerContext.getProjectsFolder().getAbsolutePath() + "\\" + project + ".properties" );
	}
	
	private static Properties getProperties( File file ) throws Exception
	{
		Properties props = new Properties();
		props.load( new FileInputStream( file ) );
    	return props;
	}

    private static SessionFactory getSessionFactory( File file ) throws Exception
    {    
    	return getSessionFactory( getProperties( file ) );
    }
    
    private static SessionFactory getSessionFactory( Properties props ) throws Exception
    {    
    	Configuration config = new Configuration().configure( HibernateUtil.getHibernateFile() );
    	config.addProperties( props );
//    	System.out.println( "dialect=" + config.getProperty( "hibernate.dialect") );
    	return config.buildSessionFactory();    	
    }
    
	private static File getHibernateFile() 
	{
        return new File( ServerContext.getConfigFolder().getAbsolutePath() + "\\hibernate.cfg.xml" );
	}
}