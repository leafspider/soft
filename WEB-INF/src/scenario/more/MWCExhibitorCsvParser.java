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

public class MWCExhibitorCsvParser extends CsvParser 
{
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			File csvFile = new File( "C:\\Workspace\\Ultra\\Nick Trendov\\MWC\\Spanish.Pavilion.Companies.MWC.2015.csv" );
			File docFolder = new File( "C:\\Workspace\\Ultra\\Nick Trendov\\MWC\\exhibitor_htm");			
			File textFolder = new File( docFolder.getParent() + "\\exhibitor_text" );
			String projectName = "MWC";
			
//			spiderUrls( csvFile, docFolder );
//			extractText( docFolder, textFolder );
//			createDescriptions( csvFile.getAbsolutePath() );
			createEdges( projectName, csvFile, textFolder );
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public static void extractText(File docFolder, File textFolder)
	{
		try
		{			
			TextExtractor extractor = new TextExtractor();
			File[] folders = docFolder.listFiles();
			for(int i=0; i<folders.length; i++)
			{
				File folder = folders[i];
				File[] pages = folder.listFiles();
				for(int j=0; j<pages.length; j++)
				{
		//				if( j > 0 ) { break; }
					File page = pages[j];
					Log.infoln( "" + j + " " + page.getName() );
		//				String target = page.getAbsolutePath().replaceAll("\\\\spider\\\\", "\\\\text\\\\") + page.getName() + "\\description";
					String target = textFolder + "\\" + folder.getName() + ".txt";
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
	
	/*
	public static void extractText(File docFolder, File textFolder)
	{
		try
		{			
			TextExtractor extractor = new TextExtractor();
			File[] pages = docFolder.listFiles();
			for(int j=0; j<pages.length; j++)
			{
				File page = pages[j];
				String fname = Util.removeFileExtension( page.getName() );
				String target = textFolder + "\\" + fname + "\\" + fname + ".txt";
				try
				{
					extractor.extractText( page.getAbsolutePath(), target );
				}
				catch(Exception ex) { Log.infoln(ex.getMessage()); }
				Log.infoln( "" + j + " " + page.getName() + " " + target );
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	*/
	
	public static void createEdges(String projectName, File csvFile, File textFolder )
	{
		try
		{
			ArrayList rows = Util.getArrayListFromFile(csvFile.getAbsolutePath());

			Log.infoln("");
			Log.infoln("rows=" + rows.size() );
			Log.infoln("");
			
			Iterator list = rows.iterator();	
			
			int n = 0;
			List dloaders = new ArrayList();
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
				if( vals.length < 1 ) { continue; } 

				n++;
				if ( n > 4 )
				{ 
//					String name = vals[0] + " " + vals[1];
					String name = vals[3];
//					String fileName = vals[2] + ".text";
//					String company = vals[3];
					String hall = "exhibitor";
//					String url = "http://linkedin.com";
					
//					if( url.indexOf("i-pal") < 0) { continue; }
	
					/*
					String name = "Ultimatte Corporation";
					String booth = "7.B25";
					String url = "www.ultimatte.com";
					*/
					
//					if(url != null && !url.trim().equals("")) { url = url.replaceAll("\"", ""); }
					{
	//					http://127.0.0.1:8000/more/kube.post?spinId=IBC\Exhibitors&url=http://127.0.0.1:8080/soft/stand/mark/1.A52/feed.xml
	
//						URL feedUrl = new URL("http://127.0.0.1:8000/mwc/kube.post.xml?spinId=MWC\\Speakers&url=http://127.0.0.1:8080/soft/stand/mark/" + booth + "/feed.xml");
//						URL feedUrl = new URL("http://127.0.0.1:8000/mwc/kube.post.xml?spinId=MWC\\" + booth + "&url=http://127.0.0.1:8080/soft/stand/mark/" + Util.replaceSubstring(name, " ", "%20") + "/feed.xml");
						URL feedUrl = new URL("http://127.0.0.1:8000/mwc/kube.post.xml?spinId=MWC\\" + hall + "&url=http://127.0.0.1:8080/soft/" + hall + "/mark/" + Util.replaceSubstring(name, " ", "%20") + "/" + hall + "feed.xml");
						
						File targetFolder = new File( textFolder.getParentFile().getAbsolutePath() + "\\" + hall + "_feed\\" );
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
	
	public static void spiderUrls(File file, File docFolder )
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile(file.getAbsolutePath());

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
				if( n > 3 )// && vals.length > 8 && vals[8] != null && !vals[8].trim().equals("") )
				{ 
					String name = vals[3];
					String booth = vals[4];
					String url = "http://www." + vals[2];
					String[] urls = url.split(";");
					url = urls[0].trim();
					
//					Log.infoln(""); 
					Log.infoln("" + booth + " --- " + url + " --- " + name); 
//					Log.infoln("" + url ); 
					
					File targetFolder = new File( docFolder.getAbsolutePath() + "\\"+ Util.getCleanFileName(name) );
					targetFolder.mkdirs();
					
					LinkContentDownloader downloader = new LinkContentDownloader( url, targetFolder );
					downloader.setPrependUrl( false );
					File resultFile = downloader.getResultFile();
					downloader.start();
										
					/*
					sfolder = new SpiderFolder();
//					sfolder.setSpiderCollectionFolder( ServerContext.toffeeDataFolder() + "\\spider\\" + Util.getCleanFileName(name) + "\\" + booth + "\\" );
//					sfolder.setSpiderCollectionFolder( ServerContext.toffeeDataFolder() + "\\spider\\" + booth + "\\"+ Util.getCleanFileName(name) + "\\" );
					sfolder.setSpiderCollectionFolder( targetFolder.getAbsolutePath() + "\\" );
					
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
					*/
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


}
