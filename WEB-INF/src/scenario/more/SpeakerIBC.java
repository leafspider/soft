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

public class SpeakerIBC extends Exhibitor 
{
	protected String category = "speaker";
	
	public static void main ( String[] args )
	{
		Log.debug = false;
		int p = 0;
		int n = 0;
		for (int i=1; i<20;i++)
		{
			try
			{	
				ExhibitorList exlist = new SpeakerListIBC();
				exlist.setEventName("IBC 2013");			
				exlist.setPageNum(i);
				exlist.populate();
		
				Log.infoln("id=" + exlist.getEventId() + " page=" + exlist.getPageNum());
		
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
					String href = (String) urls.next();
					SpeakerIBC ex = new SpeakerIBC();
					ex.setExhibitorUrl("" + href);
					ex.setWebsite(href);
					ex.populate();
					ex.reportVals();
		//				ex.saveAndCommit();
					n++;
//					if ( n > 1) { break;}
				}
				p++;
//				if ( p > 1) { break;}
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}
		}

		System.out.println( "" );
		System.out.println( "pages=" + p + " exhibitors=" + n );
		
//		System.out.println( "count=" + ExhibitorProject.getDatabaseManager().countRecords("Exhibitor") );

		/*
		Iterator exhibitors = ExhibitorProject.getDatabaseManager().listRecords("Exhibitor").iterator();
		while(exhibitors.hasNext())
		{
			Exhibitor exhibitor = (Exhibitor) exhibitors.next();
			exhibitor.report();
		}
		*/
	}
	
	public void setWebsite(String website) 
	{
		this.website = website;
	}

	public void populate() throws Exception
	{		
		try
		{
			Document doc = Jsoup.parse( new URL(getExhibitorUrl()), 5000 );

			try { setName(doc.select( "div[class=vz_entryheader]" ).first().text()); } catch(Exception e1) {}			
			String desc = "";
			try { desc += doc.select( "div[ID=DIV_exhib_PageOptionCompanyProfile]" ).first().text(); } catch(Exception e1) {}
			try { desc += " " + doc.select( "td[class=libentryfieldentryTitle]" ).first().text(); } catch(Exception e1) {}
			try { desc += " " + doc.select( "td[class=libentryfieldentryDesc]" ).first().text(); } catch(Exception e1) {}			
			setDescription( desc );			
		}
		catch(Exception timeout) {}
	}	

	public void createTextFile()
	{
		File root = new File( ServerContext.getDataFolder() + "\\speaker_text" );
		root.mkdirs();
		
		File csvFile = new File( root.getAbsolutePath() + "\\" + Util.getCleanFileName( name ) + ".txt" );
		PrintStream output = null;
		try
		{ 
			output = new PrintStream( new FileOutputStream(csvFile) );
			output.println( name );
			output.println( "" );
			output.println( description );
		}
		catch( Exception e ) { e.printStackTrace(); }
		finally { output.close(); }
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

		File keynoteFile = new File( ServerContext.getDataFolder() + "\\keynote\\" + Util.getCleanFileName(getName()) + ".csv" );
		if( keynoteFile.exists() )
		{
			System.out.println( "KEYNOTE: " + row );
			return;
		}	

		if( csvFile == null )
		{
			File root = new File( ServerContext.getDataFolder() + "\\" + category );
			root.mkdirs();
			csvFile = new File( root.getAbsolutePath() + "\\" + Util.getCleanFileName(getName()) + ".csv" );
			try { output = new PrintStream( new FileOutputStream(csvFile) ); }
			catch( Exception e ) { e.printStackTrace(); }
		}
		output.println( row );
		System.out.println( row );
	}
}
