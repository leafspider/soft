package soft.portfolio;

import leafspider.db.DatabaseManager;

import org.hibernate.Session;


public class StockProject extends DatabaseManager
{
	public StockProject(String project) throws Exception {
		super(project);
	}
	
	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager("portfolio"); }
		return databaseManager;
	}
}
