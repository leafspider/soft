package getcleantech.data;
import java.io.File;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;
import leafspider.util.Util;

public class Deduper 
{
	public static void main(String[] args)
	{
		Connection conn = null;
		try 
		{
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conn = DriverManager.getConnection("jdbc:mysql://localhost/bitnami_drupal7?user=root&password=leonardo27");
//			conn = DriverManager.getConnection("jdbc:mysql://getcleantech.com/bitnami_drupal7?user=root&password=49fkweroirgEGGWhwe");
//			Log.infoln("catalog=" + conn.getCatalog() );
			
			Statement stat = conn.createStatement();
			ResultSet rs = stat.executeQuery("select * from node where type = 4");

			TreeSet titles = new TreeSet();
			while(rs.next())
			{			
//				Log.infoln( rs.getString("title") );
				titles.add( rs.getString("title") );
			}

//			ResultSetMetaData md = rs.getMetaData();
//			writeMetaData(rs);

//			Log.infoln(titles.contains("Mark Hurst"));
//			Log.infoln(titles.contains("troutfest"));

			File root = new File("C:\\Workspace\\Ultra\\Susan\\data\\live\\root4");			
			File inFolder = new File( root.getAbsolutePath() + "\\3ready" );
			File outFolder = new File( root.getAbsolutePath() + "\\4deduped" );
			
//			File file = new File("C:\\Workspace\\Ultra\\Susan\\data\\live\\1 todo\\projects.csv");
//			File file = new File("C:\\Workspace\\Ultra\\Susan\\data\\live\\4 ready\\Experts - Deduped.csv");
//			File file = new File("C:\\Workspace\\Ultra\\Susan\\data\\live\\5 deduped\\groups.csv");
//			File file = inFolder.listFiles()[0];			
			File[] files = inFolder.listFiles();
			
			for(int i=0; i<files.length; i++)
			{
				File file = files[i];
			
	//			String path = file.getParentFile().getAbsolutePath() + "\\Deduped_" + file.getName();
				String path = outFolder.getAbsolutePath() + "\\Deduped_" + file.getName();
//				PrintStream dedup = Util.getPrintStream( path );
				PrintStream dedup = new PrintStream(path, "UTF-8");
				
				ArrayList rows = Util.getArrayListFromFile(file.getAbsolutePath());
				Log.infoln( "rows=" + rows.size() );
				Iterator rowsit = rows.iterator();
				String row = "";
				while(rowsit.hasNext())
				{
					row += (String) rowsit.next();
	//				if( row.indexOf("trout") != 0) { continue; }
					String[] vals = row.split("\\|");
					String title = vals[0];
	//				Log.infoln("len="+vals.length + " " + title);
	//				if( vals.length < 5 || titles.contains( title ) )
					if( titles.contains( title ) )
					{
						Log.infoln("Removed: " + row);
					}
					else
					{
						row = row.replaceAll("\n", " ");
						row = row.replaceAll("\r", " ");
						dedup.println(row);
					}
					row = "";
				}
				dedup.println(row);
				dedup.close();			
				
	//			file.delete();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	private static void writeMetaData(ResultSet resultSet) throws SQLException 
	{
		System.out.println("The columns in the table are: ");
		System.out.println("Table: " + resultSet.getMetaData().getTableName(1));
		for  (int i = 1; i<= resultSet.getMetaData().getColumnCount(); i++)
		{
			System.out.println("Column " +i  + " "+ resultSet.getMetaData().getColumnName(i));
		}
	}
}
