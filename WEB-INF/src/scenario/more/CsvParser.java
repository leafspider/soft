package scenario.more;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import leafspider.spider.LinkContentDownloader;

import leafspider.extract.TextExtractor;
import leafspider.spider.Spider;
import leafspider.spider.SpiderFolder;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class CsvParser 
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
//			Spider spider = new Spider();
//			File root = new File( spider.getSpiderFolder().getSpiderCollectionFolder());
//			File root = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\text" );

			spiderUrls( "C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\IBC Scottish Exhibitors_sorted_asc.csv" );
//			countUrls( new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\spider" ) );
//			countFiles( new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\spider" ) );
//			extractText( new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\spider_scottish" ) );			
//			createDescriptions( "C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\IBC Exhibitors_3.csv" );
//			createEdges( "C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitors\\IBC Exhibitors_3.csv" );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public static void extractText(File root)
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
	
	public static void createDescriptions(String fileName)
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
	
	public static void spiderUrls(String fileName)
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile(fileName);

			Log.infoln("");
			Log.infoln("vendors=" + vendors.size() );
			Log.infoln("");
			
			Iterator list = vendors.iterator();	
			
			SpiderFolder sfolder = new SpiderFolder();

			int n = 0;
			List spiders = new ArrayList();
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

					sfolder = new SpiderFolder();
//					sfolder.setSpiderCollectionFolder( ServerContext.toffeeDataFolder() + "\\spider\\" + Util.getCleanFileName(name) + "\\" + booth + "\\" );
					sfolder.setSpiderCollectionFolder( ServerContext.getDataFolder() + "\\spider\\" + booth + "\\"+ Util.getCleanFileName(name) + "\\" );
					
					Spider spider = new Spider();
//					spider.setSpiderFolder(sfolder);
					spider.setSpiderUrl( new URL( "http://" + url ) );
					spider.setStayOnSite( "ON" );
					spider.setDepth(3);
					spider.setMaxFiles(10);	
//					spider.runSpider( 3, 10 );					
					spiders.add(spider);					
					spider.start();
				}
//				if(n++ > 12) { break; }
				n++;
				if(n%10 == 0) { Spider.sleep(2000); }					
			}

			Log.infoln("");
			Log.infoln("spiders=" + spiders.size() );
			Log.infoln("");
			
			list = spiders.iterator(); 
			
			int mins = 5;
			while(list.hasNext())
			{
				Spider spider = (Spider) list.next();
				spider.join(mins*60*1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void createEdges(String fileName)
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile(fileName);

			Log.infoln("");
			Log.infoln("vendors=" + vendors.size() );
			Log.infoln("");
			
			Iterator list = vendors.iterator();	
			
			SpiderFolder sfolder = new SpiderFolder();

			int n = 0;
			List dloaders = new ArrayList();
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");

				n++;
//				if( n < 10 || n > 12 ) { continue; }					
//				if( vals[8].indexOf("sdi.co.uk") < 0 ) { continue; }

//				Log.infoln(vals[0] + "," + vals[1] + "," + vals[8]);
				if( vals.length > 8 && vals[8] != null && !vals[8].trim().equals("") )
				{ 
//					/*
					String name = vals[0].replaceAll("\"", "");
					String booth = vals[1].replaceAll("\"", "");
					String url = vals[8].replaceAll("\"", "");
//					*/
					
//					if( url.indexOf("i-pal") < 0) { continue; }
	
					/*
					String name = "Ultimatte Corporation";
					String booth = "7.B25";
					String url = "www.ultimatte.com";
					*/
					
					if(url != null && !url.trim().equals(""))
					{
	//					Log.infoln(""); 
						Log.infoln("" + n + ": " + booth + " --- " + url + " --- " + name); 
	//					Log.infoln("" + url ); 
	
	//					http://127.0.0.1:8000/more/kube.post?spinId=IBC\Exhibitors&url=http://127.0.0.1:8080/soft/stand/mark/1.A52/feed.xml
	
	//					URL feedUrl = new URL("http://127.0.0.1:8000/more/kube.post?spinId=IBC\\Exhibitors&url=http://127.0.0.1:8080/soft/stand/mark/" + booth + "/feed.xml");
						URL feedUrl = new URL("http://127.0.0.1:8000/more/kube.post.xml?spinId=IBC\\" + booth + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + url + "/feed.xml");
						
						File targetFolder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\feed\\" + booth + " " + url );
						LinkContentDownloader dloader = new LinkContentDownloader( feedUrl, targetFolder );
						dloaders.add(dloader);					
						dloader.startThread();
						dloader.join();
					}
				}
//				if(n > 5) { break; }
				if(n%10 == 0) { Spider.sleep(1000); }					
			}

			Log.infoln("");
			Log.infoln("dloaders=" + dloaders.size() );
			Log.infoln("");
			
			list = dloaders.iterator(); 
			
			int mins = 5;
			while(list.hasNext())
			{
				LinkContentDownloader dloader = (LinkContentDownloader) list.next();
//				dloader.join(mins*60*1000);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

}
