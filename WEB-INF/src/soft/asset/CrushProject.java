package soft.asset;

import leafspider.db.DatabaseManager;

public class CrushProject 
{
	public static String projectName = "crush";
	
	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager(CrushProject.projectName); }
		return databaseManager;
	}	
}
