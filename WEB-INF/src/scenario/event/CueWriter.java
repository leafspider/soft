package scenario.event;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import leafspider.extract.TextExtractor;
import leafspider.spider.LinkContentDownloader;
import leafspider.spider.Spider;
import leafspider.spider.SpiderFolder;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.jsoup.Jsoup;

import scenario.more.CsvRowOCE;
import scenario.more.Exhibitor;
import scenario.more.ExhibitorIBC;
import scenario.more.ExhibitorList;
import scenario.more.ExhibitorListIBC;

public class CueWriter 
{	
	public static void main( String[] args )
	{
		try
		{	
			System.out.println( "" );
			
			CueWriter cwr = new CueWriter();

			File root = new File( ServerContext.getDataFolder() + "\\speaker" );
			File[] files = root.listFiles();
			for ( int i=0; i<files.length; i++)
//			for ( int i=0; i<3; i++)
			{
				File file = files[i];
				cwr.createCues( file );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void createCues( File csvFile )
	{
		try
		{
			Log.infoln("");
			Log.infoln("createCues: csvFile=" + csvFile.getAbsolutePath());
			ArrayList vendors = Util.getArrayListFromFile( csvFile.getAbsolutePath() );
			Log.infoln("rows=" + vendors.size() );
			
			Iterator list = vendors.iterator();				
			SpiderFolder sfolder = new SpiderFolder();			
			String regex = "\\p{Punct}";

			int n = 0;
//			List dloaders = new ArrayList();
			while(list.hasNext())
			{
				String[] vals = ((String) list.next()).split(",");
//				String[] vals = ((String) list.next()).split("\t");
				if( vals.length < 1 ) { continue; } 

				CsvRowOCE row = new CsvRowOCE( vals );
				if( row.category.equalsIgnoreCase("Category") ) { continue; }

				n++;
//				if( n > 1 ) { break; }

				createFiles( row );
				
//				String spinId = row.category + row.place;
				String spinId = row.category;
				spinId = Util.replaceSubstring(spinId," ", "");
				
				Log.infoln("createCues: " + n + " " + spinId + " --- " + row.name); 
				Log.infoln("createCues: pavilion=" + row.pavilion); 

				String csv = csvFile.getAbsolutePath();	//"C:\\Workspace\\Ultra\\Nick\\OCE\\exhibitor_log.csv";
				URL feedUrl = new URL("http://127.0.0.1:8000/" + row.pavilion + "/kube.post.xml?spinId=" + row.pavilion.toUpperCase() + "\\" + spinId + "&url=http://127.0.0.1:8080/" + ServerContext.getApplicationName() + "/exhibitor/mark/" + Util.replaceSubstring(Util.replaceSubstring( row.name.replaceAll(regex, ""), " ", "%20"), "/", "%2F") + "/feed.xml%3Fcsv=" + csv + "&pav=" + row.pavilion);
				
				Log.infoln("createCues: feedUrl=" + feedUrl ); 
				
//				File targetFolder = new File( ServerContext.toffeeDataFolder() + "\\feed\\" + row.place + row.name );
				File targetFolder = new File( ServerContext.getDataFolder() + "\\feed\\" + row.name );
				LinkContentDownloader dloader = new LinkContentDownloader( feedUrl, targetFolder );
				dloader.setPrependUrl(false);
//				dloaders.add(dloader);					
				dloader.startThread();
				dloader.join();
				Log.infoln("createCues: resultFile=" + dloader.getResultFile().getAbsolutePath() );
				
				org.jsoup.nodes.Document jsdoc = Jsoup.parse(dloader.getResultFile(), null);
				String iconId = jsdoc.select( "spinId" ).first().text();

//				String iconId = root.getChildText("spinId");
				Log.infoln("iconId=" + iconId );
				File iconFile = new File( ServerContext.getContextProperty( row.pavilion + "Icons", "C:\\Workspace\\Visual\\Projects\\PServer\\WCFCues\\WCF REST Service Host\\bin\\Debug\\skin\\image\\icons" ) + "\\" + iconId + ".ico" ); 
				Log.infoln("createCues: iconFile=" + iconFile.getAbsolutePath() );
				
				if( false )//!row.category.equalsIgnoreCase("speaker") && !row.category.equalsIgnoreCase("keynote") )
				{
					// Download icon <link rel="icon" href="//bits.wikimedia.org/favicon/wikipedia.ico" />
					org.jsoup.nodes.Document doc = Jsoup.connect( row.url ).get();
					org.jsoup.nodes.Element el = doc.select( "link[rel=icon]" ).first();
					if( el != null )
					{
						String iconHref = el.attr("href");
						Log.infoln("createCues: iconHref=" + iconHref );
						
						URL iconurl = new URL(iconHref);
						InputStream in = new BufferedInputStream(iconurl.openStream());
						ByteArrayOutputStream out = new ByteArrayOutputStream();
						byte[] buf = new byte[1024];
						int d = 0;
						while (-1!=(d=in.read(buf))) { out.write(buf, 0, d); }
						out.close();
						in.close();
						byte[] response = out.toByteArray();				
						FileOutputStream fos = new FileOutputStream( iconFile.getAbsolutePath() );
						fos.write(response);
						fos.close();				
						Log.infoln("createCues: iconFile copied" );
					}
				}
//				if(n%10 == 0) { Spider.sleep(1000); }					
			}

			/*
			Log.infoln("");
			Log.infoln("createCues: dloaders=" + dloaders.size() );
			Log.infoln("");			

			list = dloaders.iterator(); 
			
			int mins = 5;
			while(list.hasNext())
			{
				LinkContentDownloader dloader = (LinkContentDownloader) list.next();
				dloader.join(mins*60*1000);
			}
			*/
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}		
	}

	public void createFiles( CsvRowOCE row ) throws Exception
	{
		TextExtractor extractor = new TextExtractor();		
		String textRoot = getTextRoot( row.pavilion ).getAbsolutePath();	
		
		// Handle spider
		if ( row.cuesUrl == null || row.cuesUrl.trim().length() == 0 ) { row.cuesUrl = row.url; }		 		
		if ( row.cuesUrl != null && row.cuesUrl.trim().length() > 0 ) 		
		{
			int mins = 5;
			Spider spider = createSpider( row.cuesUrl, new File( getSpiderRoot( row.pavilion ).getAbsolutePath() + "\\" + Util.replaceSubstring(row.category," ", "%20") + "\\" + Util.getCleanFileName(row.name) + "\\" ) );
			spider.start();		

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
						File page = pages[j];
						Log.infoln( "" + j + " " + page.getName() );
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
	  
		// Handle description
		if ( row.desc != null && row.desc.trim().length() > 0 ) 	
		{
			File file = new File( textRoot + "\\" + row.category + "\\" + Util.getCleanFileName(row.name) + "\\description.txt" );
			file.getParentFile().mkdirs();
			Util.writeAsFile( row.desc, file.getAbsolutePath() );
		}
	  
		// Handle file
		if ( row.fileName != null && row.fileName.trim().length() > 0 )		
		{
			String textName = Util.getCleanFileName(Util.removeFileExtension(row.cachedFile.getName())); 
			String target = textRoot + "\\" + row.category + "\\" + Util.getCleanFileName(row.name) + "\\" + textName + "_file.txt";
			try { extractor.extractText( row.cachedFile.getAbsolutePath(), target ); }
			catch(Exception ex) { Log.infoln(ex.getMessage()); }
		}
		
	}
	
	public File getTextRoot( String pavilion ) { return new File( ServerContext.getDataFolder() + "\\" + pavilion + "\\text" ); }
	public File getSpiderRoot( String pavilion ) { return new File( ServerContext.getDataFolder() + "\\" + pavilion + "\\spider" ); }
	public File getFileRoot( String pavilion ) { return new File( ServerContext.getDataFolder() + "\\" + pavilion + "\\file" ); }

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
}
