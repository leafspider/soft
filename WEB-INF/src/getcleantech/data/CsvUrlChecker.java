package getcleantech.data;

import leafspider.extract.TextExtractor;
import leafspider.util.HttpsDownloader;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class CsvUrlChecker
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			File folder = new File( "C:\\Workspace\\Ultra\\Susan\\gct data" );
			File outFolder = new File( folder.getAbsolutePath() + "\\out" );
			File file = new File( folder.getAbsolutePath() + "\\live organizations.csv" );

			processFile( file, outFolder );
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}

	public static void processFile(File file, File outFolder) throws Exception
	{
		try
		{			
			PrintStream out = Util.getPrintStream(file.getAbsolutePath() + "_out.csv");

			List<String> rows = Util.getArrayListFromFile(file.getAbsolutePath());

			for( String row : rows )
			{
				String[] vals = row.split(",");
				if( vals.length < 2) { continue; }
				String title = vals[0];
				String url = vals[1];

//				File tmpFile = LinkChecker.downloadFile(url, tmp);
				File tmpFile = HttpsDownloader.download(url, new File( outFolder.getAbsolutePath() + "\\" + Util.getCleanFileName(url) ));
				if ( tmpFile == null ){
					System.out.println( "null," + url );
				}
				else {
					System.out.println(tmpFile.length() + "," + url);
				}
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void obextractText(File root)
	{
		try
		{			
			TextExtractor extractor = new TextExtractor();

			File[] files = root.listFiles();
			for(int i=0; i<files.length; i++)
			{
				File folder = files[i];
				if( folder.isDirectory() );
				{
					File[] pages = folder.listFiles();
					if( pages != null )
					{
						Log.infoln( "" + pages.length + "      " + folder.getName() );
						for(int j=0; j<pages.length; j++)
						{
							File page = pages[j];
							String target = page.getAbsolutePath().replaceAll("\\\\spider\\\\", "\\\\text\\\\");
							try
							{
								extractor.extractText( page.getAbsolutePath(), target + ".txt");
							}
							catch(Exception ex) { Log.infoln(ex.getMessage()); }
						}
					}
				}
//				if(i>3){break;}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void countFiles(File root)
	{
		try
		{			
			int n = 0;
			File[] files = root.listFiles();
			for(int i=0; i<files.length; i++)
			{
				File folder = files[i];
				if( folder.isDirectory() );
				{
					File[] pages = folder.listFiles();
					if( pages != null )
					{
						Log.infoln( "" + pages.length + "      " + folder.getName() );
						n += pages.length;
					}
				}
			}
			Log.infoln( "files=" + n );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void countUrls(File root)
	{
		try
		{
			ArrayList folders = new ArrayList(); 
			
			File[] files = root.listFiles();
			for(int i=0; i<files.length; i++)
			{
				File folder = files[i];
				if( folder.isDirectory() );
				{
					File[] pages = folder.listFiles();
					if( pages != null )
					{
//						Log.infoln( "" + pages.length + "      " + folder.getName() );
						folders.add("" + folder.getName());
					}
				}
			}
			
			String fileName = "C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\IBC Exhibitors_2_sorted_asc.csv";			
			ArrayList vendors = Util.getArrayListFromFile(fileName);
			
			int n = 0;
			Iterator list = vendors.iterator();
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
				if( vals.length > 8)
				{
					String url = vals[8].replaceAll("\"", "").trim();
//					String[] urls = url.split(";");
//					url = urls[0].trim();
					if( folders.contains(url) )
					{
						Log.infoln( url + " " + vals[1] );
						n++;
					}
				}
			}
			Log.infoln( "vendors=" + vendors.size() );
			Log.infoln( "urls=" + n );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public static void countCompanies(File root)
	{
		try
		{			
			File[] files = root.listFiles();
			for(int i=0; i<files.length; i++)
			{
				File folder = files[i];
				if( folder.isDirectory() );
				{
					File[] pages = folder.listFiles();
					if( pages != null )
					{
						Log.infoln( "" + pages.length + "      " + folder.getName() );
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public static void obcreateDescriptions(String fileName)
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile(fileName);

			Log.infoln("");
			Log.infoln("vendors=" + vendors.size() );
			Log.infoln("");
			
			Iterator list = vendors.iterator();	
			
			int n = 0;
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
//				Log.infoln(vals[0] + "," + vals[1] + "," + vals[8]);
				if( vals.length > 8 && vals[8] != null && !vals[8].trim().equals("") )
				{ 
					String name = vals[0].replaceAll("\"", "");
					String booth = vals[1].replaceAll("\"", "");
					String url = vals[8].replaceAll("\"", "");
					String[] urls = url.split(";");
					url = urls[0].trim();
					
//					Log.infoln(""); 
					Log.infoln("" + booth + " --- " + url + " --- " + name); 
//					Log.infoln("" + url ); 

					if( vals.length > 9 )
					{
						File file = new File(ServerContext.getDataFolder() + "\\text\\" + url + "\\description.txt");
						file.getParentFile().mkdirs();
						Util.writeAsFile(vals[9], file.getAbsolutePath() );
					}
				}
				n++;
//				if(n > 1) { break; }
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	

}
