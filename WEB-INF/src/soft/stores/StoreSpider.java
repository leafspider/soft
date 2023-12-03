package soft.stores;

import java.io.File;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import leafspider.util.Downloader;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public class StoreSpider
{		
	public static void main ( String[] args )
	{
		try
		{
			StoreSpider.runSpider();			
			/*
            DatabaseManager mgr = new DatabaseManager();
            List stores = mgr.listRecords(Store.table);
            for (int i = 0; i < stores.size(); i++) 
            {
                Store store1 = (Store) stores.get(i);
                System.out.println("" + store1.getCity() + "=" + store1.getUrl() + "");
            }
            */
			System.out.println( "Done" );				
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static void runSpider() throws Exception
	{
		// get list of pages
		File urlfile = new File( ServerContext.getProjectsFolder() + "\\" + HibernateManager.projectName + "\\URLs.txt" );
		Iterator urls = Util.getArrayListFromFile(urlfile.getAbsolutePath()).iterator();						

		int num = 1;
		
		while(urls.hasNext())
		{
			try
			{
				String url = ((String) urls.next()).replace(" ","%20");
				if ( url.indexOf("groupola.com") > -1 ) { continue; }		// DEBUG
	//			if ( url.indexOf("citydeals.com") > -1 ) { continue; }		// DEBUG
				if ( url.indexOf("crowdsavings.com") > -1 ) { continue; }		// DEBUG
				
				URI uri = new URI(url);
				String host = uri.getHost();
	
				/*
				String[] vals = url.split("/");
				String city = vals[vals.length-1];

				Store store = new Store();
		        store.setCity(city);
		        store.setUrl(url); 
		       	store.saveAndCommit();
		       	*/
	
				// parse page
				Page page = Page.instance(url);
				List list = page.parse();
				Iterator deals = list.iterator();
	
				System.out.println( "" + num + " " + host + " " + ((Deal)list.get(0)).getCity() + " [" + list.size() + "]");
	
				// insert deals
				while(deals.hasNext())
				{
					Deal deal = (Deal) deals.next();
					deal.setStoreUrl( url );
					deal.setStoreHost( host );
	//					System.out.println(deal.getText());
					deal.setUnid( deal.city + "-" + deal.url );
					boolean saved = deal.saveAndCommit();
//					System.out.println("saved=" + saved + " " + deal.text);
				}				
			}
			catch(Exception e)
			{
				Log.infoln(e.getClass() + ": " + e.getMessage());
				continue;
			}

			num++;
//			if(num>1) { break; }		// DEBUG
		}	

//        DatabaseManager.getCurrentSession().close();
	}
	
}
