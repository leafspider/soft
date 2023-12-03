package scenario.more;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExhibitorChirpe extends Exhibitor 
{
	public void populate() throws Exception
	{
		/*
		 */
//		String url = "http://finance.yahoo.com/q?s=" + ticker.toLowerCase();
//		String qprice = "span[id=yfs_l84_" + ticker.toLowerCase() + "]";
//		String qlink = "span.deal_cityname:has(span.deal_available)";
//		String qlink = "a[href~=(ExhibitorHome)\\.(aspx)\\?(BoothID=)(?i)]";
		
		Document doc = Jsoup.connect( getExhibitorUrl() ).get();
		
		try { setName(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorName]" ).first().text()); } catch(Exception e1) {}
		try { setBooth(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblBoothLabel]" ).first().text()); } catch(Exception e1) {}
		try { setAddress1(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorAddress1]" ).first().text()); } catch(Exception e1) {}
		try { setAddress2(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorAddress2]" ).first().text()); } catch(Exception e1) {}
		try { setAddressCity(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorCity]" ).first().text()); } catch(Exception e1) {}
		try { setAddressState(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorState]" ).first().text()); } catch(Exception e1) {}
		try { setPhone(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorPhone]" ).first().text()); } catch(Exception e1) {}
		try
		{ 
			String email = doc.select( "a[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lnkExhibitorEmail]" ).first().attr("href");
			setEmail(email.replace("mailto:", ""));
		} 
		catch(Exception e1) {}
		try { setWebsite(doc.select( "span[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_lblExhibitorUrl]" ).first().text()); } catch(Exception e1) {}
		try { setDescription(doc.select( "div[id=ctl00_ctl00_cphRootContent_cphEventContent_ucExhibitorProfile_plhExhibitorDescription]" ).first().text()); } catch(Exception e1) {}
	}	
}
