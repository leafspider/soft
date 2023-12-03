package soft.performance;

import leafspider.db.DatabaseManager;

import org.hibernate.Session;


public class TradeManager extends DatabaseManager 
{
	public TradeManager(String project) throws Exception {
		super(project);
	}
	
	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager("picks"); }
		return databaseManager;
	}
}
