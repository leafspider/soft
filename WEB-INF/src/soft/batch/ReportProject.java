package soft.batch;

import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Properties;

import leafspider.util.Util;
import soft.report.Folders;

public class ReportProject extends BatchProject
{
	public static List<String> getReports()
	{
   		File folder = getConfigFolder();
   		File[] files = folder.listFiles(new FilenameFilter() {
   		    public boolean accept(File dir, String name) {
   		        return name.toLowerCase().endsWith(".properties");
   		    }
   		});
   		
		List<String> reports = new ArrayList<String>();
		for( int i=0; i<files.length; i++)
		{
			reports.add(Util.removeFileExtension(files[i].getName()));
		}
		return reports;
	}

	public static File getConfigFolder() { return Folders.reportConfigFolder(); }
}