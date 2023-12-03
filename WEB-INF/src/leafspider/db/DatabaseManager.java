package leafspider.db;

import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.exception.ConstraintViolationException;

import java.io.File;
import java.io.FileInputStream;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

//import org.hsqldb.lib.Iterator;

public class DatabaseManager 
{
	public String projectName = null;
	
	public DatabaseManager(String project) throws Exception 
	{
		if( project == null || project.trim().length() == 0 )
		{
			throw new Exception("Empty project name");
		}
		projectName = project;		
	}

    public List select(String select) throws Exception {
	    return select(select, 0, 0);
    }

    public List select(String select, int offset, int limit) throws Exception {

//	    Log.infoln(select + ", " + offset + ", " + limit);

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery( select );

        if( offset < 0 ) { offset = 0; }
        if( limit < 0 ) { limit = 0; }
        if( (offset+limit) > query.list().size() ) { offset = query.list().size()-limit; }
        if( offset > 0) { query.setFirstResult(offset); }
        if( limit > 0) { query.setMaxResults(limit); }

        return query.list();
    }
	
    public List listRecords(String table) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from " + table).list();
        session.getTransaction().commit();
        return result;
    }

    public List listRecords(String table, String order) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from " + table + " order by " + order).list();
        session.getTransaction().commit();
        return result;
    }

    public List listRecords(String table, String order, int offset, int limit) throws Exception
    {
        //Log.infoln(table + " " + order + ", " + offset + ", " + limit);

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery( "from " + table + " order by " + order );
        if( offset < 0 ) { offset = 0; }
        if( limit < 0 ) { limit = 0; }
        if( (offset+limit) > query.list().size() ) { offset = query.list().size()-limit; }
        if( offset > 0) { query.setFirstResult(offset); }
        if( limit > 0) { query.setMaxResults(limit); }
        return query.list();
    }

    public List listFilteredRecords(String table, String filter) throws Exception {

        Session session = getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from " + table + " where " + filter ).list();
        session.getTransaction().commit();
        return result;
    }

    public List listFilteredRecords(String table, String filter, String value, String order) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from " + table + " where " + filter + "='" + value + "' order by " + order).list();
        session.getTransaction().commit();
        return result;
    }

    public List listFilteredRecords(String table, String filter, String value, String order, int offset, int limit) throws Exception
    {
        Log.infoln(table + " " + order + ", " + offset + ", " + limit);

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery( "from " + table + " where " + filter + "='" + value + "' order by " + order );
        if( offset < 0 ) { offset = 0; }
        if( limit < 0 ) { limit = 0; }
        if( (offset+limit) > query.list().size() ) { offset = query.list().size()-limit; }
        if( offset > 0) { query.setFirstResult(offset); }
        if( limit > 0) { query.setMaxResults(limit); }
        return query.list();
    }

    public List listTopRecords(String table, int top, String orderby, String order) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery("from " + table + " order by " + orderby + " " + order);
	    if( top > 0) { query.setMaxResults(top); }
	    List result = query.list();
        session.getTransaction().commit();
	    return result;
    }

    public Object countRecords(String table) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
	    Object count = session.createQuery(String.format("select count(id) from %s", table)).uniqueResult();
        session.getTransaction().commit();
        return count;
    }

    public Object countDistinctRecords(String table, String column) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
	    Object count = session.createQuery( "select count(distinct " + column + ") from " + table ).uniqueResult();
        session.getTransaction().commit();
        return count;
    }

    private SessionFactory sessionFactory = null;    
    public SessionFactory getSessionFactory()
    {
    	if ( sessionFactory == null )
    	{
            try { sessionFactory = getSessionFactory( projectName ); } 
            catch (Throwable ex) 
            {
                throw new ExceptionInInitializerError(ex);
            }
    	}
        return sessionFactory;
    }    
	public Session getCurrentSession()
	{
		return getSessionFactory().getCurrentSession();
	}
	public SessionFactory getSessionFactory( String project ) throws Exception
	{
		return getSessionFactory( getProperties( getPropertiesFile() ) );
	}

    public String getProjectFolder()
    {
    	return ServerContext.getProjectsFolder().getAbsolutePath() + "\\" + Util.replaceSubstring( projectName, " ", "_" );
    }
    
    public String getProjectPropertiesText()
    {
//    	String toffeeDataFolder = Util.replaceSubstring( ServerContext.toffeeDataFolder().getAbsolutePath(), "\\", "/" );
    	String txt = "hibernate.dialect=org.hibernate.dialect.HSQLDialect\r\n" + 
    				 "hibernate.connection.driver_class=org.hsqldb.jdbcDriver\r\n" +
    				 "hibernate.connection.url=jdbc:hsqldb:file:" + Util.replaceSubstring( getProjectFolder(), "\\", "/" ) + "/db/\r\n" +
    				 "hibernate.connection.databaseName=" + projectName + "\r\n" +
    				 "hibernate.connection.username=sa\r\n" +
    				 "hibernate.connection.password=\r\n";
    	return txt;
    }
    
    /** ------------ */

    private File propertiesFile = null;
	private File getPropertiesFile() 
	{
		if( propertiesFile == null )
		{
			propertiesFile = new File( ServerContext.getProjectsFolder().getAbsolutePath() + "\\" + projectName + ".properties" );
		}
		if( !propertiesFile.exists() )
		{
			Util.writeAsFile(getProjectPropertiesText(), propertiesFile.getAbsolutePath());
		}
		return propertiesFile;        
	}
	
	private Properties getProperties( File file ) throws Exception
	{
		Properties props = new Properties();
		props.load( new FileInputStream( file ) );
    	return props;
	}

    private SessionFactory getSessionFactory( File file ) throws Exception
    {    
    	return getSessionFactory( getProperties( file ) );
    }
    
    private SessionFactory getSessionFactory( Properties props ) throws Exception
    {    
//    	File file = getHibernateFile();
    	Configuration config = new Configuration().configure( getHibernateFile() );
    	config.addProperties( props );
//    	System.out.println( "dialect=" + config.getProperty( "hibernate.dialect") );
    	return config.buildSessionFactory();    	
    }
    
	private File getHibernateFile() 
	{
        return new File( ServerContext.getConfigFolder().getAbsolutePath() + "\\hibernate.cfg.xml" );
	}

    public DatabaseRecord getRecord(String table, String field, String value) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        DatabaseRecord record = (DatabaseRecord) session.createQuery( "from " + table + " where " + field + " = " + value ).uniqueResult(); 

//        List list = session.createQuery("from " + table + " where " + field + " = " + value).list();        
//        DatabaseRecord record = (DatabaseRecord) list.get(0);
        
        session.getTransaction().commit();
        return record;
    }

    /*
    public DatabaseRecord getRecord(String table, String field, Long value) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        DatabaseRecord record = (DatabaseRecord) session.createQuery( "from " + table + " where " + field + " = ?" ).setLong( 0, value ).uniqueResult(); 
        session.getTransaction().commit();
        return record;
    }
    */

    public List selectRecords(String select) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery(select).list();
        session.getTransaction().commit();
        return result;
    }
    
    public List selectRecords(String select, int offset, int limit) throws Exception
    {
        //Log.infoln(select + ", " + offset + ", " + limit);

        Session session = getCurrentSession();
        session.beginTransaction();
	    Query query = session.createQuery( select );
		if( offset < 0 ) { offset = 0; }
		if( limit < 0 ) { limit = 0; }
	    if( (offset+limit) > query.list().size() ) { offset = query.list().size()-limit; }
	    if( offset > 0) { query.setFirstResult(offset); }
	    if( limit > 0) { query.setMaxResults(limit); }
	    return query.list();
    }

    public int deleteRecords(String select) throws Exception
    {
        Session session = getCurrentSession();
        session.beginTransaction();
    	int ret = session.createQuery( select ).executeUpdate();   
        session.getTransaction().commit();
        return ret;
    }
    
    public void reportRecords(List list) throws Exception
    {		
    	Iterator records = list.iterator();
    	while(records.hasNext())
    	{
    		DatabaseRecord record = (DatabaseRecord) records.next();
    		record.reportVals();
    	}
    }

    public boolean saveAndCommitRecord(DatabaseRecord record) {

        Session session = getCurrentSession();
        try {

            Transaction trans = session.beginTransaction();
            record.setLastModified( Util.getNow() );
            if( record.getId() == null ) {
               session.save(record);
            }
            else {
                session.update(record);
            }
            trans.commit();
        }
        catch( ConstraintViolationException e)
        {
            Log.infoln(e.getClass().getSimpleName() + " " + e.getMessage() + " " + this.toString());
            try { getCurrentSession().evict(this); }
            catch( Exception e2) { throw e2; }
            return false;
        }
        catch( Exception e3) { throw e3; }
        finally { if ( session.isOpen() ) { session.close(); } }
        return true;
    }

    public boolean saveOrUpdateAndCommitBatch(List list) throws Exception
    {    	
//		Log.infoln( "DatabaseManager.saveOrUpdateAndCommitBatch list.size=" + list.size() );
        Session session = getCurrentSession();
    	try
    	{
//    		if( list == null || list.size() < 1) { Log.infoln( "DatabaseManager.saveOrUpdateAndCommitBatch list null or empty" ); return false; }

    		/*
    		StatelessSession session = getSessionFactory().openStatelessSession();
	    	Transaction tx = session.beginTransaction();
        	Iterator records = list.iterator();
        	while(records.hasNext())
        	{
        		DatabaseRecord record = (DatabaseRecord) records.next();
        		session.insert(record);
        	} 	    	   
	    	tx.commit();
	    	session.close();
        	*/

	        Transaction trans = session.beginTransaction(); 
        	Iterator records = list.iterator();
        	int n = 0;
        	while(records.hasNext())
        	{
        		DatabaseRecord record = (DatabaseRecord) records.next();

        		record.setLastModified( Util.getNow() );

        		if( record.getId() == null ) // Is just a new ticker
        		{
//        			Log.infoln( "DatabaseManager.saveOrUpdateAndCommitBatch id=" + record.getId() );
        			/*
        			record.reportFields();
        			record.reportVals();
        			throw new Exception("Null record id");
        			*/
//        			continue;
            		session.save(record);
        		}
        		else
        		{
        			session.update(record);
        		}

        		//Log.infoln( "DatabaseManager.saveOrUpdateAndCommitBatch id=" + record.getId() );
        		/*
        	    if ( n++ % 20 == 0 ) 
        	    { 
        	    	//20 same as JDBC batch size. Flush batch of inserts and release memory
        	        session.flush();
        	        session.clear();
        	    }
        	    */
        	} 
	        trans.commit();
//	        session.close();
    	}
        catch( ConstraintViolationException e)
        {
        	Log.infoln(e.getClass().getSimpleName() + " " + e.getMessage() + " " + this.toString());
        	try { getCurrentSession().evict(this); }
            catch( Exception e2) { throw e2; }
        	return false;
        }
        catch( Exception e3) { throw e3; }
    	finally { if ( session.isOpen() ) { session.close(); } }
    	return true;
	}

    public Long lastModified( ) throws Exception {

        String select = new String("select max(lastModified) from CrushRecord");

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery( select );
        query.setFirstResult(0);
        query.setMaxResults(1);
        Long ret = (Long) query.uniqueResult();
        if( ret == null ) { return (Long) 0l; }
        return ret;
    }

    public Long firstModified() {

        String select = new String("select min(lastModified) from CrushRecord");

        Session session = getCurrentSession();
        session.beginTransaction();
        Query query = session.createQuery( select );
        query.setFirstResult(0);
        query.setMaxResults(1);
        Long ret = (Long) query.uniqueResult();
        if( ret == null ) { return (Long) 0l; }
        return ret;
    }
}
