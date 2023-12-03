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

public class CsvParserOCE extends CsvParser 
{			
	public static void main(String[] args)
	{
//		Log.debug = true;
		try
		{
			CsvParserOCE parser = new CsvParserOCE();
			
			parser.setApp( "petro" );
			
//			parser.processCsv( new File( "C:\\Workspace\\Ultra\\Nick\\OCE\\final_" + parser.getApp() + ".csv" ) );
//			parser.processCsv( new File( "C:\\Workspace\\Ultra\\Nick\\Petro\\final_petro.csv" ) );
//			parser.processCsv( new File( "C:\\Workspace\\Ultra\\Nick\\Petro\\final_noia.csv" ) );
//			parser.processCsv( new File( "C:\\Workspace\\Ultra\\Nick\\Petro\\final_expert.csv" ) );
			parser.processCsv( new File( "C:\\Workspace\\Ultra\\Nick\\Petro\\final_anon.csv" ) );
			
//			parser.createAlerts();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	private void processCsv( File csv )
	{
		setCsvFile( csv );
		spiderUrls();
		extractSpiderText();
		extractFileText();
		createEdges();		
	}

	private void createAlerts() throws Exception
	{
		File targetFolder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\feed" );
		LinkContentDownloader dloader2 = new LinkContentDownloader( "http://127.0.0.1:8000/" + getApp() + "/alerts.post", targetFolder );
		dloader2.startThread();
		dloader2.join();
	}
	
	public void spiderUrls()
	{
		try
		{
			ArrayList rows = Util.getArrayListFromFile(getCsvFile().getAbsolutePath());

			Log.infoln(""); Log.infoln("spiderUrls.rows=" + rows.size() ); Log.infoln("");

			List<Spider> spiders = new ArrayList<Spider>();
			
			int n = 0;
			Iterator list = rows.iterator();	
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
				CsvRowOCE row = new CsvRowOCE( vals );
				if( row.category.equalsIgnoreCase("Category") ) { continue; }
				
//					Log.infoln(""); 
				Log.infoln("" + row.category + " --- " + row.url + " --- " + row.name); 
//					Log.infoln("" + url );
				
				if ( row.desc != null && row.desc.trim().length() > 0 )
				{
					File file = new File( getTextRoot().getAbsolutePath() + "\\" + row.category + "\\" + Util.getCleanFileName(row.name) + "\\description.txt" );
					file.getParentFile().mkdirs();
					Util.writeAsFile( row.desc, file.getAbsolutePath() );
				}
				
				String theUrl = row.url;
				if ( row.cuesUrl != null && row.cuesUrl.trim().length() > 0 ) { theUrl = row.cuesUrl; }					

				if( theUrl != null && !theUrl.trim().equals("") && theUrl.indexOf("www.linkedin") == -1 )
				{
					Spider spider = createSpider( theUrl, new File( getSpiderRoot().getAbsolutePath() + "\\" + Util.replaceSubstring(row.category," ", "%20") + "\\" + Util.getCleanFileName(row.name) + "\\" ) );
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

			TextExtractor extractor = new TextExtractor();
			String textRoot = getTextRoot().getAbsolutePath();
			
			int mins = 5;
			while(list.hasNext())
			{
				Spider spider = (Spider) list.next();
				spider.join(mins*60*1000);
				
				SpiderFolder sf = spider.getSpiderFolder();
				File namefolder = new File( sf.getSpiderCollectionFolder() );

				File[] sitefolders = namefolder.listFiles();
				for(int i=0; i<sitefolders.length; i++)
				{
					File[] pages = sitefolders[i].listFiles();
					if( pages != null)
					{
						for(int j=0; j<pages.length; j++)
						{
				//				if( j > 0 ) { break; }
							File page = pages[j];
							Log.infoln( "" + j + " " + page.getName() );
				//				String target = page.getAbsolutePath().replaceAll("\\\\spider\\\\", "\\\\text\\\\") + page.getName() + "\\description";
							String target = textRoot + "\\" + namefolder.getParentFile().getName() + "\\" + namefolder.getName() + "\\" + page.getName() + ".txt";
							try
							{
								extractor.extractText( page.getAbsolutePath(), target );
							}
							catch(Exception ex) { Log.infoln(ex.getMessage()); }
							Log.infoln( target );
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public void extractSpiderText()
	{
		String textRoot = getTextRoot().getAbsolutePath();
		try
		{			
			TextExtractor extractor = new TextExtractor();

			File[] categoryfolders = getSpiderRoot().listFiles();
			for(int n=0; n<categoryfolders.length; n++)
			{
				File[] namefolders = categoryfolders[n].listFiles();
				for(int m=0; m<namefolders.length; m++)
				{
					File[] sitefolders = namefolders[m].listFiles();
					for(int i=0; i<sitefolders.length; i++)
					{
						File[] pages = sitefolders[i].listFiles();
						if( pages != null)
						{
							for(int j=0; j<pages.length; j++)
							{
					//				if( j > 0 ) { break; }
								File page = pages[j];
								Log.infoln( "" + j + " " + page.getName() );
					//				String target = page.getAbsolutePath().replaceAll("\\\\spider\\\\", "\\\\text\\\\") + page.getName() + "\\description";
								String target = textRoot + "\\" + categoryfolders[n].getName() + "\\" + namefolders[m].getName() + "\\" + page.getName() + ".txt";
								try
								{
									extractor.extractText( page.getAbsolutePath(), target );
								}
								catch(Exception ex) { Log.infoln(ex.getMessage()); }
								Log.infoln( target );
							}
						}
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void extractFileText()
	{
		String textRoot = getTextRoot().getAbsolutePath();
		try
		{			
			TextExtractor extractor = new TextExtractor();

			File[] categoryfolders = getFileRoot().listFiles();
			for(int n=0; n<categoryfolders.length; n++)
			{
				File[] pages = categoryfolders[n].listFiles();
				if( pages != null)
				{
					for(int j=0; j<pages.length; j++)
					{
						File page = pages[j];
						Log.infoln( "" + j + " " + page.getName() );
						String name = Util.getCleanFileName(Util.removeFileExtension(page.getName())); 
						String target = textRoot + "\\" + categoryfolders[n].getName() + "\\" + name + "\\" + name + "_file.txt";
						try
						{
							extractor.extractText( page.getAbsolutePath(), target );
						}
						catch(Exception ex) { Log.infoln(ex.getMessage()); }
						Log.infoln( target );
					}
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}
	
	public void createEdges()
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile( getCsvFile().getAbsolutePath() );

			Log.infoln("");
			Log.infoln("rows=" + vendors.size() );
			Log.infoln("");
			
			Iterator list = vendors.iterator();	
			
			SpiderFolder sfolder = new SpiderFolder();
			
			String regex = "\\p{Punct}";

			int n = 0;
			List dloaders = new ArrayList();
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
				if( vals.length < 1 ) { continue; } 

//				Log.infoln(vals[0] + "," + vals[1] + "," + vals[8]);
				CsvRowOCE row = new CsvRowOCE( vals );
				if( row.category.equalsIgnoreCase("Category") ) { continue; }

				n++;
//				if( n > 1 ) { break; }
				
//					String spinId = Util.replaceSubstring(category, " ", "%20") + "-" + booth;
				String spinId = row.category + row.place;
				spinId = Util.replaceSubstring(spinId," ", "");
				
				Log.infoln("createEdges " + n + ": " + spinId + " --- " + row.name); 

				String csv = getCsvFile().getAbsolutePath();	//"C:\\Workspace\\Ultra\\Nick\\OCE\\exhibitor_log.csv";
//				URL feedUrl = new URL("http://127.0.0.1:8000/" + getApp() + "/kube.post.xml?spinId=" + getApp().toUpperCase() + "\\" + spinId + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + Util.replaceSubstring(Util.replaceSubstring( row.name, " ", "%20"), "/", "%2F") + "/feed.xml%3Fcsv=" + csv);
				URL feedUrl = new URL("http://127.0.0.1:8000/" + getApp() + "/kube.post.xml?spinId=" + getApp().toUpperCase() + "\\" + spinId + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + Util.replaceSubstring(Util.replaceSubstring( row.name.replaceAll(regex, ""), " ", "%20"), "/", "%2F") + "/feed.xml%3Fcsv=" + csv + "&pav=" + app);
//				URL feedUrl = new URL("http://127.0.0.1:8000/" + getApp() + "/kube.post.xml?spinId=" + getApp().toUpperCase() + "\\" + spinId + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + Util.replaceSubstring(Util.replaceSubstring(Util.getCleanFileName(row.name), " ", "%20"), "/", "%2F") + "/feed.xml%3Fcsv=" + csv);
//				URL feedUrl = new URL("http://127.0.0.1:8000/" + getApp() + "/kube.post.xml?spinId=" + getApp().toUpperCase() + "\\" + spinId + "&url=http://127.0.0.1:8080/soft/exhibitor/mark/" + Util.replaceSubstring(Util.replaceSubstring(row.name, " ", "%20"), "/", "%2F") + "/feed.xml");
				
//				Log.infoln("ref=" + feedUrl ); 
				
				File targetFolder = new File( "C:\\Server\\tomcat6\\webapps\\soft\\data\\feed\\" + row.place + " " + row.name );
				LinkContentDownloader dloader = new LinkContentDownloader( feedUrl, targetFolder );
				dloaders.add(dloader);					
				dloader.startThread();
				dloader.join();
//				if(n%10 == 0) { Spider.sleep(1000); }					
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

	public File getTextRoot() { return new File( ServerContext.getDataFolder() + "\\" + getApp() + "\\text" ); }
	public File getSpiderRoot() { return new File( ServerContext.getDataFolder() + "\\" + getApp() + "\\spider" ); }
	public File getFileRoot() { return new File( ServerContext.getDataFolder() + "\\" + getApp() + "\\file" ); }

	private File csvFile = null;
	public File getCsvFile() {
		return csvFile;
	}
	public void setCsvFile(File csvFile) {
		this.csvFile = csvFile;
	}
	
	private String app = null;
	public String getApp() {
		return app;
	}
	public void setApp(String app) {
		this.app = app;
	}

	public Spider createSpider( String url, File folder ) throws Exception
	{
		folder.mkdirs();
		
		Spider spider = null;
		if( url != null && !url.trim().equals("") )
		{
			SpiderFolder sfolder = new SpiderFolder();
			sfolder.setSpiderCollectionFolder( folder.getAbsolutePath() );

			spider = new Spider();
			spider.setSpiderFolder(sfolder);
//					spider.setSpiderUrl( new URL( "http://" + url ) );
			spider.setSpiderUrl( new URL( url ) );
			spider.setStayOnSite( "ON" );
			spider.setDepth(3);
			spider.setMaxFiles(1);	
//					spider.runSpider( 3, 10 );					
//			spiders.add(spider);					
//			spider.start();
		}
		return spider;
	}

	/*
	public void obcreateDescriptions()
	{
		try
		{
			ArrayList vendors = Util.getArrayListFromFile( getCsvFile().getAbsolutePath() );

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
				if( vals.length > 3 && vals[3] != null && !vals[3].trim().equals("") )
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
						File file = new File(ServerContext.toffeeDataFolder() + "\\text\\" + url + "\\description.txt");
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
	*/	
}
