package soft.stores;

import leafspider.util.Log;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;


public abstract class HibernateRecord 
{		
	private Long id;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public boolean saveAndCommit() throws Exception
    {
    	try
    	{
	        Session session = HibernateManager.getCurrentSession();
	        session.beginTransaction();                
	        session.saveOrUpdate(this);
	        session.getTransaction().commit();	
    	}
        catch( ConstraintViolationException e)
        {
        	Log.infoln(e.getClass().getSimpleName() + " " + e.getMessage() + " " + this.toString());
        	try
        	{
        		HibernateManager.getCurrentSession().evict(this);
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
}
