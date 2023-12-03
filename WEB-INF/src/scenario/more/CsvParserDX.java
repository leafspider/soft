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

public class CsvParserDX extends CsvParser 
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
//			File folder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\spider" );
//			File folder = new File( "C:\\Workspace\\Ultra\\Nick\\DX\\DX Event\\DX Event\\DX3 topics" );
			File file = new File( "C:\\Workspace\\Ultra\\Nick\\DX\\Exhibitors\\DX Exhibitors_1.csv" );
//			File file = new File( "C:\\Workspace\\Ultra\\Nick\\DX\\Topics\\DX Topics_1.csv" );
//			File file = new File( "C:\\Workspace\\Ultra\\Nick\\DX\\Speakers\\DX Speakers_1.csv" );
			
//			spiderUrls( file.getAbsolutePath() );
//			extractText( new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\spider" ) );
			extractText( new File( "C:\\Workspace\\Visual\\Projects\\Cleantech\\WattsScan\\bin\\x86\\Debug\\web") );
//			extractText( folder );
//			createDescriptions( file.getAbsolutePath() );
//			createEdges( file.getAbsolutePath() );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void extractText(File root)
	{
		String textFolder = root.getParent() + "\\text";
		try
		{			
			TextExtractor extractor = new TextExtractor();

			File[] folders = root.listFiles();
			for(int i=0; i<folders.length; i++)
			{
				File[] pages = folders[i].listFiles();
				for(int j=0; j<pages.length; j++)
				{
		//				if( j > 0 ) { break; }
					File page = pages[j];
					Log.infoln( "" + j + " " + page.getName() );
		//				String target = page.getAbsolutePath().replaceAll("\\\\spider\\\\", "\\\\text\\\\") + page.getName() + "\\description";
					String target = textFolder + "\\" + folders[i].getName() + "\\" + page.getName() + ".txt";
					try
					{
						extractor.extractText( page.getAbsolutePath(), target );
					}
					catch(Exception ex) { Log.infoln(ex.getMessage()); }
					Log.infoln( target );
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
//				if( vals.length > 8 && vals[8] != null && !vals[8].trim().equals("") )
				if( vals.length > 8 )
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
//					spider.setSpiderUrl( new URL( "http://" + url ) );
					spider.setSpiderUrl( new URL( url ) );
					spider.setStayOnSite( "ON" );
					spider.setDepth(3);
					spider.setMaxFiles(1);	
//					spider.runSpider( 3, 10 );					
					spiders.add(spider);					
					spider.start();
				}
//				if(n++ > 1) { break; }
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
				if( vals.length < 1 ) { continue; } 

				n++;
//				if( n < 10 || n > 12 ) { continue; }					
//				if( vals[8].indexOf("sdi.co.uk") < 0 ) { continue; }

//				Log.infoln(vals[0] + "," + vals[1] + "," + vals[8]);
//				if( vals.length > 8 )//&& vals[8] != null && !vals[8].trim().equals("") )
				{ 
//					/*
					String name = vals[0].replaceAll("\"", "");
					String booth = vals[1].replaceAll("\"", "");
					String url = vals[8];
//					*/
					
//					if( url.indexOf("i-pal") < 0) { continue; }
	
					/*
					String name = "Ultimatte Corporation";
					String booth = "7.B25";
					String url = "www.ultimatte.com";
					*/
					
//					if(url != null && !url.trim().equals("")) { url = url.replaceAll("\"", ""); }

					{
	//					Log.infoln(""); 
						Log.infoln("" + n + ": " + booth + " --- " + url + " --- " + name); 
	//					Log.infoln("" + url ); 
	
	//					http://127.0.0.1:8000/more/kube.post?spinId=IBC\Exhibitors&url=http://127.0.0.1:8080/soft/stand/mark/1.A52/feed.xml
	
	//					URL feedUrl = new URL("http://127.0.0.1:8000/more/kube.post?spinId=IBC\\Exhibitors&url=http://127.0.0.1:8080/soft/stand/mark/" + booth + "/feed.xml");
						URL feedUrl = new URL("http://127.0.0.1:8000/dx/kube.post.xml?spinId=DX\\" + booth + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + Util.replaceSubstring(name, " ", "%20") + "/feed.xml");
						
						File targetFolder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\feed\\" + booth + " " + name );
						LinkContentDownloader dloader = new LinkContentDownloader( feedUrl, targetFolder );
						dloaders.add(dloader);					
						dloader.startThread();
						dloader.join();
					}
				}
//				if(n > 1) { break; }
//				if(n%10 == 0) { Spider.sleep(1000); }					
			}

			Log.infoln("");
			Log.infoln("dloaders=" + dloaders.size() );
			Log.infoln("");			

			/*
			File targetFolder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\feed" );
			LinkContentDownloader dloader2 = new LinkContentDownloader( "http://127.0.0.1:8000/dx/alerts.post?f=scenarioId&fid=DX", targetFolder );
			dloader2.startThread();
			dloader2.join();
			*/			
			
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
