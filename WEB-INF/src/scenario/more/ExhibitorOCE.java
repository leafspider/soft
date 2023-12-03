package scenario.more;

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
import scenario.more.ExhibitorListIBC;

public class ExhibitorOCE extends Exhibitor 
{
	public static void main ( String[] args )
	{
		try
		{	
			Log.debug = true;

			Exhibitor ex = new ExhibitorOCE();
			ex.setExhibitorUrl("http://www.dx3canada.com/page.cfm/Action=Exhib/ExhibID=27");
			ex.populate();
//			ex.reportVals();
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
			
			try { setName(doc.select( "h1[class=ez_entryheader]" ).first().text()); } catch(Exception e1) {}
			try { setBooth(doc.select( "div[class=ez_entrystand] table tr td" ).last().text()); } catch(Exception e1) {}
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
//				e1.printStackTrace();
			}
			*/
//			try { setWebsite(doc.select( "div[id=mys-exhibitorInfo] ul li a" ).get(1).attr("title")); } catch(Exception e1) {}
			try { setWebsite(doc.select( "div[ID=DIV_exhib_PageOptionCompanyProfile] p a" ).attr("href")); } catch(Exception e1) {}
			try { setDescription(doc.select( "div[ID=DIV_exhib_PageOptionCompanyProfile] p" ).first().text()); } catch(Exception e1) {}
			/*
			try
			{ 
				String email = doc.select( "a[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lnkExhibitorEmail]" ).first().attr("href");
				setEmail(email.replace("mailto:", ""));
			} 
			catch(Exception e1) {}
			*/			

//			Log.infoln("\"" + name + "\",\"" + description + "\",,\"" + website + "\",,,,\"" + address2 + "\",\"" + address1 + "\",\"" + phone + "\",\"" + addressCity + "\",\"" + addressState + "\"" );
			Log.infoln("\"" + name + "\",\"" + booth + "\",,\"" + website + "\",,,,\"" + description + "\"" );
		}
		catch(Exception timeout) {}
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;	
	}

}
