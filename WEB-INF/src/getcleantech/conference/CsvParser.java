package getcleantech.conference;

import java.io.File;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import leafspider.extract.TextExtractor;
import leafspider.util.Log;
import leafspider.util.LoginContentDownloader;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class CsvParser
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			File folder = new File( "C:\\Workspace\\Ultra\\Susan\\data\\live\\3 csv" );			
			File[] files = folder.listFiles();
			
//			for (int i=0;i<files.length;i++ )
//			{
//				File file = files[i];
			File file = new File( "C:\\Workspace\\Ultra\\Susan Sheenan\\data\\live\\root6\\2csv\\VC and Angel Investor's Lists.csv" );

//				if( !file.isDirectory() )
				{
					processFile( file );
				}
//			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void processFile(File file)
	{
		try
		{
			PrintStream out = Util.getPrintStream(file.getAbsolutePath() + " NEW.csv");
			
			ArrayList rows = Util.getArrayListFromFile(file.getAbsolutePath());

			Log.infoln(file.getName());
			Log.infoln("rows=" + rows.size() );
			
			Iterator list = rows.iterator();	
			
			File outputFolder = new File( file.getParent() + "\\download" );
//			outputFolder.mkdirs();

			int n = 0;
			int downloads = 0;
			while(list.hasNext())
			{
//				String[] vals = ((String) list.next()).split("\\|");
				String[] vals = ((String) list.next()).split("\\t");
				Log.infoln( "" + (n++) + " " + vals.length + " " + vals[0].trim() );				
				if( true ) { continue; }
				
//				Log.infoln( vals[0] + " | " + vals[1] );				
				String title = vals[0].replaceAll("\"", "");
				String website = ""; if ( vals.length > 1 ) { website = vals[1].replaceAll("\"", "" ); }
				String body = ""; if ( vals.length > 2 ) { body = vals[2].replaceAll("\"", "" ); }
				String location = ""; if ( vals.length > 3 ) { location = vals[3].replaceAll("\"", "" ); }
				
				if( n > 0 && website != null && !website.trim().equals("") )
				{
					if( website.indexOf("http") < 0 )
					{
						website = "http://" + website;
					}
					
					LoginContentDownloader dload = new LoginContentDownloader(website, outputFolder );
					if( !dload.getResultFile().exists() )
					{
						dload.startThread();
						dload.join( 5000 );
					}

					File textFile = new File( dload.getResultFile().getAbsolutePath() + ".txt" );
					if( dload.getResultFile().exists() )
					{
						if( !textFile.exists() )
						{
							TextExtractor extractor = new TextExtractor();
							extractor.extractText( dload.getResultFile().getAbsolutePath(), textFile.getAbsolutePath());
						}
					}
					
					if( textFile.exists() )
					{
						ArrayList textList = Util.getArrayListFromFile(textFile.getAbsolutePath());					
						Iterator textit = textList.iterator();
						while(textit.hasNext())
						{
							body += (String) textit.next() + " ";
						}
						downloads++;
					}
				}
//				if(n++ > 12) { break; }
				n++;
//				Log.infoln( title + "|" + website + "|" + body + "|" + location );

				out.println( title + "|" + website + "|" + body + "|" + location );
			}

			Log.infoln("downloads=" + downloads );
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
