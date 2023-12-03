package getcleantech.conference;

import java.net.URL;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import scenario.more.Exhibitor;

public class ExhibitorBio13 extends Exhibitor 
{
	public static void main ( String[] args )
	{
		try
		{	
			Log.debug = true;

			Exhibitor ex = new ExhibitorBio13();
			ex.setExhibitorUrl("http://bio13.mapyourshow.com/5_0/exhibitor_details.cfm?exhid=00224372");
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
			
			try { setName(doc.select( "div[id=mys-exhibitorInfo] h2" ).first().text()); } catch(Exception e1) {}	
			/*
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
//				e1.printStackTrace();
			}
//			try { setWebsite(doc.select( "div[id=mys-exhibitorInfo] ul li a" ).get(1).attr("title")); } catch(Exception e1) {}
			try { setWebsite(doc.select( "div[id=mys-exhibitorInfo] ul" ).get(2).children().last().child(0).text()); } catch(Exception e1) {}
			*/
			try
			{
				Elements lis = doc.select( "div[id=mys-exhibitorInfo] ul" ).get(2).children();
//				Log.infoln( "size=" + lis.size() );
				for( int i=lis.size()-1; i>-1; i--)
				{
					Element li = lis.get(i);
					String val = li.text();
					
					if(li.children().size() > 0)
					{
						Element child = li.child(0);
						if( child != null && child.hasAttr("href") )
						{
							setWebsite(val);				
						}
						else if( child != null && child.tagName().equals("strong") )
						{
							if( child != null && child.text().equals("P:"))
							{
								setPhone(val.replace("P: ", ""));
							}
						}
					}
					else
					{
						setAddressState(val);
						break;
					}		
				}
			}
			catch(Exception e1) {}
			
//			try { setDescription(doc.select( "div.detail-overview-description" ).first().text()); } catch(Exception e1) {}
			/*
			try
			{ 
				String email = doc.select( "a[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lnkExhibitorEmail]" ).first().attr("href");
				setEmail(email.replace("mailto:", ""));
			} 
			catch(Exception e1) {}
			*/			

			Log.infoln("\"" + name + "\",\"" + description + "\",,\"" + website + "\",,,,\"" + address2 + "\",\"" + address1 + "\",\"" + phone + "\",\"" + addressCity + "\",\"" + addressState + "\"" );
			int b = 9;
		}
		catch(Exception timeout) 
		{
			Log.infoln("Exception " + getExhibitorUrl() );			
		}
	}
	
	public void setPhone(String phone) 
	{
		this.phone = phone;	
	}

}
