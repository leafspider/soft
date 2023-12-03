package scenario.more;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.URL;
import java.util.Iterator;

import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExhibitorIBC extends Exhibitor 
{
	public static void main ( String[] args )
	{
		Log.debug = false;
		try
		{	
			int p = 0;
			int n = 0;
			for (int i=1; i<2000;i++)
			{
				ExhibitorList exlist = new ExhibitorListIBC();
				exlist.setEventName("IBC 2013");			
				exlist.setPageNum(i);
				exlist.populate();
		
				Log.infoln("id=" + exlist.getEventId() + " pageNum=" + exlist.getPageNum());
		
				Iterator links = exlist.getLinks().iterator();
				while(links.hasNext())
				{
					String link = (String) links.next();
					Log.infoln("" + link);
				}
			
		//			Iterator urls = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitor\\ChirpeVendorLinks.txt").iterator();
				Iterator urls = exlist.links.iterator(); 
							
				while(urls.hasNext())
				{
					Exhibitor ex = new ExhibitorIBC();
					ex.setExhibitorUrl("" + urls.next());
					ex.populate();
					ex.reportVals();
		//				ex.saveAndCommit();
					n++;
//					if ( n > 1) { break;}
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
			Document doc = Jsoup.parse( new URL(getExhibitorUrl()), 5000 );
			
			/*
			try
			{ 
				String details = doc.select( "div[ID=DIV_exhib_PageOptionCatEntry]" ).first().html();
				String[] vals = Util.explodeString(details, "<br />");
	
				String nam = vals[0].replaceAll("\\n", "");
				int pos = nam.indexOf("-->")+3;
				setName(nam.substring(pos,nam.length()));
				
				setAddress1(vals[1].replaceAll("\\n", ""));
				setAddressCity(vals[2].replaceAll("\\n", ""));
				setAddressState( vals[3].replaceAll("\\n", ""));
				setPhone(vals[vals.length-3].replaceAll("\\n", ""));
				setEmail(vals[vals.length-2].replaceAll("\\n", ""));
				setWebsite(vals[vals.length-1].replaceAll("\\n", ""));
			} 
			catch(Exception e1) { e1.printStackTrace(); }
			if( getName() == null)
			{
				try { setName(doc.select( "div[class=ez_entryheader]" ).first().text()); } catch(Exception e1) {}
			}
			*/
	
			/*
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
			*/

			try { setName(doc.select( "div[class=ez_entryheader]" ).first().text()); } catch(Exception e1) {}
			try { setBooth(doc.select( "div[class=ez_entrystand]" ).first().text()); } catch(Exception e1) {}
			try { setDescription(doc.select( "div[ID=DIV_exhib_PageOptionCompanyProfile]" ).first().text()); } catch(Exception e1) {}
			try
			{ 
				String details = doc.select( "div[class=ez_contact]" ).first().html();
				String[] vals = Util.explodeString(details, "<br />");
				
				setWebsite("");
				String val = vals[vals.length-1];
				if( val.indexOf("Website") > -1 )
				{
					int pos = val.lastIndexOf(">");
					if( pos > -1 )
					{
						String href = val.substring(pos+1, val.length()).trim();
						if( href.indexOf("http") < 0 ) { href = "http://" + href; }
						setWebsite(href);
					}
				}
			} 
			catch(Exception e1) { e1.printStackTrace(); }
		}
		catch(Exception timeout) {}
	}	

	private File csvFile = null;
	private PrintStream output = null;
	public void reportVals()
	{		
		String pavilion = "ibc";
		String hall = "";
		String stand = "";
		int pos = booth.indexOf(".");
		if( pos > -1)
		{
			hall = booth.substring(0,pos);
			if( pos < booth.length() )
			{
				stand = booth.substring(pos+1,booth.length());
			}
		}

		category = category.replaceAll("[,]", " ");
		name = name.replaceAll("[,]", " ");
		booth = booth.replaceAll("[,]", " ");
		website = website.replaceAll("[,]", " ");
		description = description.replaceAll("[,\\n\\r]", " ");

		String row = category + "," + name + "," + booth + ",,," + pavilion + "," + website + "," + description + ",," + hall + "," + stand;
		if( csvFile == null )
		{
			File root = new File( ServerContext.getDataFolder() + "\\" + getProjectName() );
			root.mkdirs();
			csvFile = new File( root.getAbsolutePath() + "\\" + Util.getCleanFileName(getName()) + ".csv" );
			try { output = new PrintStream( new FileOutputStream(csvFile) ); }
			catch( Exception e ) { e.printStackTrace(); }
		}
		output.println( row );
	}	
	
}
