package soft.assetlist;

import leafspider.db.DatabaseManager;

public class AssetlistProject
{
	public static String projectName = "assetlist";
	
	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager(AssetlistProject.projectName); }
		return databaseManager;
	}
}
