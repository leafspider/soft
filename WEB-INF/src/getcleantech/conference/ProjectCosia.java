package getcleantech.conference;

import java.net.URL;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import scenario.more.Exhibitor;

public class ProjectCosia extends Exhibitor 
{
	public void populate() throws Exception
	{		
		try
		{
			Document doc = Jsoup.parse( new URL(getExhibitorUrl()), 10000 );
			
			try { setName(doc.select( ".pageTitle" ).first().text()); } catch(Exception e1) {}	
//			try { setAddress1(doc.select( "span.street-address" ).first().text()); } catch(Exception e1) {}
//			try { setAddress2(doc.select( "span.postal-code" ).first().text()); } catch(Exception e1) {}
//			try { setAddressCity(doc.select( "span.locality" ).first().text()); } catch(Exception e1) {}
//			try { setAddressState(doc.select( "span.country-name" ).first().text()); } catch(Exception e1) {}
			/*
			try
			{
				Element el = doc.select( "div.detail-address-toggle-content dl dd" ).first();
				setPhone(el.attr("title")); 
			} 
			catch(Exception e1) 
			{
				e1.printStackTrace();
			}
			*/
//			try { setWebsite(doc.select( "a.showme" ).get(1).attr("title")); } catch(Exception e1) {}
			try 
			{ 
				String desc = "";
				Iterator els = doc.select( ".col-sm-9 p" ).iterator();
				while(els.hasNext())
				{
					Element el = (Element) els.next();
					desc += el.text() + " ";
				}
				setDescription(desc);					
			} 
			catch(Exception e1) {}
			/*
			try
			{ 
				String email = doc.select( "a[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lnkExhibitorEmail]" ).first().attr("href");
				setEmail(email.replace("mailto:", ""));
			} 
			catch(Exception e1) {}
			*/			

//			Log.infoln("\"" + name + "\",\"" + description + "\",,\"" + website + "\",,,,\"" + address2 + "\",\"" + address1 + "\",\"" + phone + "\",\"" + addressCity + "\",\"" + addressState + "\"" );
		}
		catch(Exception timeout) {}
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;	
	}

}
