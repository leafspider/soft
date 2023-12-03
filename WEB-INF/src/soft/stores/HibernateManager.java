package soft.stores;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


import java.util.List;

import leafspider.util.Log;

public class HibernateManager 
{
	public static String projectName = "stores";
	
    public static void main(String[] args) 
    {   
        try
        {
            Store store = new Store();
            store.setCity("London");
            store.setUrl("http://www.bbc4.co.uk/London"); 
           	store.saveAndCommit();
           	
            HibernateManager mgr = new HibernateManager();	        
//          mgr.createAndSaveStore("Shop", "www.shop.com");

            List stores = mgr.listRecords(Store.table);
            for (int i = 0; i < stores.size(); i++) 
            {
                Store store1 = (Store) stores.get(i);
                System.out.println("" + store1.getCity() + "=" + store1.getUrl() + "");
            }
        }
        catch( Exception e)
        {
        	e.printStackTrace();
        }

        try
        {
        	HibernateUtil.getSessionFactory( HibernateManager.projectName ).getCurrentSession().close();
        }
        catch( Exception e)
        {
        	e.printStackTrace();
        }
    }

    public List listRecords(String table) throws Exception
    {
        Session session = HibernateManager.getCurrentSession();
        session.beginTransaction();
        List result = session.createQuery("from " + table).list();
        session.getTransaction().commit();
        return result;
    }

    private static SessionFactory sessionFactory = null;    
    private static SessionFactory getSessionFactory()
    {
    	if ( sessionFactory == null )
    	{
            try { sessionFactory = HibernateUtil.getSessionFactory( HibernateManager.projectName ); } 
            catch (Throwable ex) 
            {
                throw new ExceptionInInitializerError(ex);
            }
    	}
        return sessionFactory;
    }    
	public static Session getCurrentSession()
	{
		return getSessionFactory().getCurrentSession();
	}
}
