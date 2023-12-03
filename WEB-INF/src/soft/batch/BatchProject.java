package soft.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import soft.report.Folders;
import leafspider.db.DatabaseManager;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class BatchProject 
{
	private static Hashtable hash = new Hashtable();
	
//	private static DatabaseManager databaseManager = null;
	public static DatabaseManager getDatabaseManager( String id ) throws Exception
	{
//		if( databaseManager == null ) { databaseManager = new DatabaseManager(projectName); }
//		return databaseManager;
//		return new DatabaseManager(projectName);
	
		if(hash.containsKey(id))
		{
			return (DatabaseManager) hash.get(id);
		}
		else
		{
			DatabaseManager dbm = new DatabaseManager(id);
			hash.put(id, dbm);
			return dbm;
		}
	}	
		
	public static List<String> getBatches() {

   		File folder = getConfigFolder();
   		File[] files = folder.listFiles(new FilenameFilter() {
   		    public boolean accept(File dir, String name) {
   		        return name.toLowerCase().endsWith(".properties");
   		    }
   		});
   		
		List<String> batches = new ArrayList<String>();
//		batches.add("mlse");
//		batches.add("nasdaq");
		for( int i=0; i<files.length; i++)
		{
			batches.add(Util.removeFileExtension(files[i].getName()));
		}
		return batches;
	}
	
	public static File getConfigFolder()
	{
		return Folders.batchConfigFolder();
	}

	public static File[] getConfigFiles() throws Exception
	{
		File[] files = getConfigFolder().listFiles(new FilenameFilter() {
			public boolean accept(File dir, String name) {
   		        return name.toLowerCase().endsWith(".properties");
		    }
		});
		return files;
	}

	public static File getConfigFile(final String id) throws Exception
	{
		File[] files = Folders.batchConfigFolder().listFiles(new FilenameFilter() {
		    public boolean accept(File dir, String name) {
		        return name.toLowerCase().equals( id + ".properties" );
		    }
		});
		return files[0];
	}
	
	public static String getProperty(final String id, String propName ) throws Exception
	{
		Properties props = new Properties();
		props.load( new FileInputStream( getConfigFile( id ) ) );
		return props.getProperty( propName );
	}
}
