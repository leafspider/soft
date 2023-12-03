package scenario.more;

import java.io.File;
import java.net.URI;
import java.util.Iterator;
import java.util.List;

import soft.stores.Deal;
import soft.stores.HibernateManager;
import soft.stores.Page;

import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

public class MoreReport 
{
	public static void main(String[] args)
	{
		try
		{
			File urlfile = new File( "C:\\Workspace\\Ultra\\Nick\\IBC\\logs\\access morelike urls.txt" );
			Iterator urls = Util.getArrayListFromFile(urlfile.getAbsolutePath()).iterator();						
	
			int num = 1;			
			while(urls.hasNext())
			{
//				try
//				{
					String url = ((String) urls.next()).replace("htm","xml");
					/*
					String url = ((String) urls.next()).replace(" ","%20");
					if ( url.indexOf("cid=") < 0 ) { continue; }
					int pos1 = url.indexOf("/more");
					int pos2 = url.indexOf("%20HTTP");					
					url = "http://127.0.0.1:8000" + url.substring(pos1,pos2);
					*/
					
//					System.out.println("url=" + url);
					
//					URI uri = new URI(url);
//					String host = uri.getHost();
					
					//http://127.0.0.1:8000/more/kubes.htm?fid=IBC%5C8.&cid=IBC%5C8.B51c%5Cwww.atgbroadcast.co.uk,IBC%5C8.B61%5Cwww.preview-gm.com
					//http://127.0.0.1:8000/more/alerts.xml?fid=IBC%5C1.A01%5Cwww.integ-europe.com
					
					url = url.replaceAll(".+cid=", "");
					String[]vals = url.split(",");
					for(int i=0; i<vals.length; i++)
					{
						MoreAlerts page = new MoreAlerts();
						page.url = "http://127.0.0.1:8000/more/alerts.xml?fid=" + vals[i];
						page.populate();
					}
					/*
					List list = page.populate();
					Iterator pages = list.iterator();
		
					System.out.println( "" + num + " " + host + " " + ((Deal)list.get(0)).getCity() + " [" + list.size() + "]");
		
					// insert deals
					while(pages.hasNext())
					{
						Deal deal = (Deal) pages.next();
						deal.setStoreUrl( url );
						deal.setStoreHost( host );
		//					System.out.println(deal.getText());
						deal.setUnid( deal.city + "-" + deal.url );
						boolean saved = deal.saveAndCommit();
	//					System.out.println("saved=" + saved + " " + deal.text);
					}
					*/
				/*
				}
				catch(Exception e)
				{
					Log.infoln(e.getClass() + ": " + e.getMessage());
					continue;
				}
				*/
	
				num++;
	//			if(num>1) { break; }		// DEBUG
			}	
		}
		catch( Exception e)
		{
			e.printStackTrace();
		}
	}
	
}
