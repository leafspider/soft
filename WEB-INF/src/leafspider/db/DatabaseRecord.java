package leafspider.db;

import leafspider.util.Log;
import leafspider.util.Util;
import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import soft.asset.Asset;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;

public abstract class DatabaseRecord 
{			
	public abstract String getProjectName();

	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

	private long lastModified = 0;
	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}
	
    private DatabaseManager databaseManager = null;
	public DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager(getProjectName()); }
		return databaseManager;
	}
	public void setDatabaseManager(DatabaseManager manager) {
		this.databaseManager = manager;
	}
	
    public boolean saveOrUpdateAndCommit() throws Exception
    {
    	try
    	{
    		setLastModified( Util.getNow() );
    		
	        Session session = getDatabaseManager().getCurrentSession();
	        session.beginTransaction(); 
	        session.saveOrUpdate(this);
	        session.getTransaction().commit();
	        /*
	        Transaction trans = session.getTransaction();
	        Log.infoln( trans.toString() );
	        trans.commit();
	        */	
    	}
        catch( ConstraintViolationException e)
        {
        	Log.infoln(e.getClass().getSimpleName() + " " + e.getMessage() + " " + this.toString());
        	try
        	{
        		getDatabaseManager().getCurrentSession().evict(this);
        	}
        	/*
            catch( IllegalStateException e1)
            {
//            	Log.infoln(e1.getClass().getSimpleName() + " " + e1.getMessage() + " " + this.toString());
            }
            */
            catch( Exception e2)
            {
            	throw e2;            	
            }
        	return false;
        }
        catch( Exception e3)
        {
        	throw e3;
        }
    	return true;
	}
        
	public void reportFields()
	{
		String st = "";
		try 
		{
			Class clazz = Class.forName(this.getClass().getCanonicalName());
			Method meths[] = clazz.getDeclaredMethods();
//			Field field[] = clazz.getDeclaredFields();
			for (int i = 0; i < meths.length; i++)
			{
				String meth = meths[i].toString();
				int pos = meth.indexOf(".get");
				if( pos > -1 )
				{
					String field = meth.substring(pos + 4).replace("()","");
					field = field.substring(0,1).toLowerCase() + field.substring(1,field.length());
					Log.info(field + "\t");
				}
//	            System.out.println( field[i].toString() + "= " + field[i].get(this));
			}
			Log.infoln("");
		}
		catch (Throwable e) 
		{
			System.err.println(e);
		}
	}

	public void reportVals()
	{
		try 
		{
			Class clazz = Class.forName(this.getClass().getCanonicalName());
			Method meths[] = clazz.getDeclaredMethods();
//			Field field[] = clazz.getDeclaredFields();
			for (int i = 0; i < meths.length; i++)
			{
				String meth = meths[i].toString();
				int pos = meth.indexOf(".get");
				if( pos > -1 )
				{
					String val = "" + meths[i].invoke(this, null);
					Log.info(val + "\t");
				}
//	            System.out.println( field[i].toString() + "= " + field[i].get(this));
			}
			Log.infoln("");
		}
		catch (Throwable e) 
		{
			System.err.println(e);
		}
	}

	public Element listElement()
	{
		Element elem = null;
		try 
		{
			Class clazz = Class.forName(this.getClass().getCanonicalName());
			elem = new Element( clazz.getSimpleName() );
			elem.addContent( new Element( "id" ).addContent( new CDATA( "" + getId() ) ) );
			
			Method meths[] = clazz.getDeclaredMethods();
//			Field field[] = clazz.getDeclaredFields();
			for (int i = 0; i < meths.length; i++)
			{
				String meth = meths[i].toString();
				int pos = meth.indexOf(".get");
				if( pos > -1 )
				{
					String field = meth.substring(pos + 4).replace("()","");
					field = field.substring(0,1).toLowerCase() + field.substring(1,field.length());					
					String val = "" + meths[i].invoke(this, null);					
				
					if( val.equals( "null" ) ) { val = ""; }
					else if( meth.indexOf(".getTradeTime()") > -1 ) {
//						Log.infoln( "val=" + val);
						val = Asset.detailedDateFormat.format( Long.parseLong(val) );						
					}
										
					elem.addContent( new Element( field ).addContent( new CDATA( "" + val.trim() ) ) );
				}
			}
		}
		catch (Throwable e) 
		{
			System.err.println(e);
		}
		return elem;
	}

	protected String url = null;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	private File file = null;
	public File getFile() {
		return file;
	}
	public void setFile(File file) {
		this.file = file;
	} 
	
	protected Document parse() throws Exception
	{
		if( file != null ) { return Jsoup.parse(file, null); }
		else if ( url != null) { return Jsoup.parse( new URL(getUrl()), 5000 ); }
		else return null;
	}

	public String dbencode( String st )
	{
		String val = st.replaceAll("'", "''").replaceAll("%", "!%");
//		Log.infoln( "val=" + val);
//		String val = "trout";
		return val.substring(0,254);
	}
	public String dbdecode( String st )
	{
		return st.replaceAll("''", "'").replaceAll("!%", "%");
	}
}
