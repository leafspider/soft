package getcleantech.data;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.extract.TextExtractor;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class CsvParserIssuers 
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			File file = new File( "C:\\Workspace\\Ultra\\Susan Sheenan\\Christine Delada\\Cleantech Companies candidates.csv" );
			processFile( file );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void processFile(File file) throws Exception
	{
		try
		{			
			PrintStream out = Util.getPrintStream(file.getAbsolutePath() + " NEW.csv");
			
			ArrayList rows = Util.getArrayListFromFile(file.getAbsolutePath());
			TreeSet indexed = new TreeSet();

			Log.infoln(file.getName());
			Log.infoln("rows=" + rows.size() );

			File indexableFile = new File( file.getParentFile().getAbsolutePath() + "\\indexable.txt" );
			ArrayList indexableList = Util.getArrayListFromFile(indexableFile.getAbsolutePath());
			TreeSet indexable = new TreeSet();
			indexable.addAll(indexableList);
			Log.infoln("indexable=" + indexable.size() );
			Iterator list = rows.iterator();	
			
			int n = 0;
			while(list.hasNext())
			{
				if( n > 0 )
				{
					String[] vals = ((String) list.next()).split("\\|");
	
					if( !indexable.contains(vals[1]) ) { continue; }
	
	//				if( true ) { continue; }
				
					String title = vals[0].replaceAll("\"", "");
					String website = ""; if ( vals.length > 1 ) { website = vals[1].replaceAll("\"", "" ); }
					
					if( website != null && !website.trim().equals("") )
					{
						if( website.indexOf("http") < 0 ) { website = "http://" + website; }
					
						if( indexed.contains(website) ) { continue; }
						
						out.println( title + "|" + website );
						indexed.add(website);
						Log.infoln( "" + indexed.size() + " " + vals[1].trim() );				
					}
				}
				n++;
			}

			Log.infoln("indexed=" + indexed.size() );
			Log.infoln("");
		}
		catch(Exception e)
		{
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
