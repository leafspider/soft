package scenario.more;

import leafspider.db.DatabaseManager;

public class ExhibitorProject
{
	public static String projectName = "exhibitor";
	
	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager() throws Exception
	{
		if( databaseManager == null ) { databaseManager = new DatabaseManager(ExhibitorProject.projectName); }
		return databaseManager;
	}
}
