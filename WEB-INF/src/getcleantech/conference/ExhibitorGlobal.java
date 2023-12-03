package getcleantech.conference;

import java.io.File;
import java.net.URL;
import java.util.Iterator;

import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import scenario.more.Exhibitor;
import scenario.more.ExhibitorList;

public class ExhibitorGlobal extends Exhibitor 
{
	public static void main ( String[] args )
	{
		try
		{	
			Log.debug = true;

			String fname = "allfairs";	//Util.getCleanFileName(exlist.listUrl());			
//			File listFile = new File( "C:\\Workspace\\Ultra\\Susan\\globalpowergen\\" + fname + "_list.csv" );
			File exhibitorFile = new File( "C:\\Workspace\\Ultra\\Susan\\globalpowergen\\exhibitors_" + fname + ".csv" );
			
//			Log.report = Util.getPrintStream(listFile.getAbsolutePath());
			Log.report = Util.getPrintStream(exhibitorFile.getAbsolutePath());
			//				Log.infoln("name,description,,website,,,,address2,address1,phone,addressCity,addressState" );
			Log.infoln("Company Name,Description,Email,Website,Contact,Title,Position,Zip,Address,Phone Number,City,Country");
			
			int p = 0;
			int n = 0;
			int max = 73;
			
			for (int i=0; i<max;i++)
			{
				ExhibitorList exlist = new ExhibitorListGlobal();
				exlist.setEventName("Globalpowergen");			
				exlist.setPageNum(i);
				exlist.populate();

				System.out.println( "" );
				System.out.println( "i=" + i + " links=" + exlist.links.size() );

//				Log.infoln("id=" + exlist.getEventId() + " pageNum=" + exlist.getPageNum());
				/*
				Iterator links = exlist.getLinks().iterator();
				while(links.hasNext())
				{
					String link = (String) links.next();
					Log.infoln("" + link);
				}
				*/

//				Log.report = Util.getPrintStream(exhibitorFile.getAbsolutePath());
			
//				Iterator urls = Util.getArrayListFromFile(listFile.getAbsolutePath()).iterator();
				Iterator urls = exlist.links.iterator(); 
							
				while(urls.hasNext())
				{
					Exhibitor ex = new ExhibitorGlobal();
					ex.setExhibitorUrl("" + urls.next());
					ex.populate();

//					Log.infoln("" + ex.getWebsite());
					
//					ex.reportVals();
		//				ex.saveAndCommit();
					n++;
//					if ( n > 5) { break;}
				}
				p++;
//				if ( p > 1) { break;}
			}

			System.out.println( "" );
			System.out.println( "pages=" + p + " exhibitors=" + n );
			
//			System.out.println( "count=" + ExhibitorProject.getDatabaseManager().countRecords("Exhibitor") );

			/*
			Iterator exhibitors = ExhibitorProject.getDatabaseManager().listRecords("Exhibitor").iterator();
			while(exhibitors.hasNext())
			{
				Exhibitor exhibitor = (Exhibitor) exhibitors.next();
				exhibitor.report();
			}
			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void populate() throws Exception
	{		
		try
		{
			Document doc = Jsoup.parse( new URL(getExhibitorUrl()), 10000 );
			
			try { setName(doc.select( "h4.org" ).first().text()); } catch(Exception e1) {}	
			try { setAddress1(doc.select( "span.street-address" ).first().text()); } catch(Exception e1) {}
			try { setAddress2(doc.select( "span.postal-code" ).first().text()); } catch(Exception e1) {}
			try { setAddressCity(doc.select( "span.locality" ).first().text()); } catch(Exception e1) {}
			try { setAddressState(doc.select( "span.country-name" ).first().text()); } catch(Exception e1) {}
			try
			{
				Element el = doc.select( "div.detail-address-toggle-content dl dd" ).first();
				setPhone(el.attr("title")); 
			} 
			catch(Exception e1) 
			{
				e1.printStackTrace();
			}
			try { setWebsite(doc.select( "a.showme" ).get(1).attr("title")); } catch(Exception e1) {}
			try { setDescription(doc.select( "div.detail-overview-description" ).first().text()); } catch(Exception e1) {}
			/*
			try
			{ 
				String email = doc.select( "a[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lnkExhibitorEmail]" ).first().attr("href");
				setEmail(email.replace("mailto:", ""));
			} 
			catch(Exception e1) {}
			*/			

			Log.infoln("\"" + name + "\",\"" + description + "\",,\"" + website + "\",,,,\"" + address2 + "\",\"" + address1 + "\",\"" + phone + "\",\"" + addressCity + "\",\"" + addressState + "\"" );
		}
		catch(Exception timeout) {}
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;	
	}

}
