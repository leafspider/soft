package soft.stores;


import java.io.File;
import java.lang.Runnable;
import java.net.*;
import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import leafspider.tagger.PosTagger;
import leafspider.util.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public abstract class Page
{
	public static boolean debug = false;
	
	public String url;
	public String city;
	public String[] nounStoplist = { "%","Off","Sq","metre" };

	public String qtext = null;
	public String qurl = null;
	public String qprice = null;
	public String qsaving = null;
	public String qvalue = null;
	public String qdiscount = null;
	public String qcity = null;

	public Elements texts = null;			
	public Elements urls = null;
	public Elements prices = null;
	public Elements savings = null;
	public Elements values = null;
	public Elements discounts = null;
	public Elements cities = null;

	public abstract void init();
	
	public static Page instance(String url) throws Exception
	{
		Page page = null;
		if( url.indexOf( "groupola.com" ) > -1 ) { page = new PageGroupola(); }		
		else if( url.indexOf( "citydeals.com" ) > -1 ) { page = new PageCitydeals(); }		
		else if( url.indexOf( "crowdsavings.com" ) > -1 ) { page = new PageCrowdsavings(); }		
		if( page != null ) 
		{ 
			page.url = url;
			page.init();
		}		
		return page;
	}
		
	public List<Deal> parse() throws Exception
	{
		PosTagger tagger = new PosTagger();

		List<Deal> deals = new ArrayList<Deal>();
//		try
//		{	
			/* DEBUG
			File folder = new File( ServerContext.getProjectsFolder() + "\\" + DatabaseManager.projectName + "\\pages" );
			folder.mkdirs();
			File file = Downloader.downloadFile(url, folder);
			System.out.println( "Download: " + file.getAbsolutePath());
			Document doc = Jsoup.parse(file, "UTF-8", url);
			*/

			Document doc = Jsoup.connect( url ).get();

			texts = doc.select( qtext );			
			urls = doc.select( qurl );
			prices = doc.select( qprice );
			savings = doc.select( qsaving );
			values = doc.select( qvalue );
			discounts = doc.select( qdiscount );
			cities = doc.select( qcity );
			
			/*
			System.out.println( "texts=" + texts.size() + " " + 
								"urls=" + urls.size() + " " + 
								"prices=" + prices.size() + " " + 
								"savings=" + savings.size() + " " + 
								"values=" + values.size() + " " + 
								"discounts=" + discounts.size() + " " + 
								"cities=" + cities.size() + " " );
			*/
			
			int i = 0;
			for (Element text : texts) 
			{
				Deal deal = new Deal();
				
				deal.setText( parseText(i) );
				if ( deal.url == null ) { deal.setUrl( parseUrl(i) ); }
				if ( deal.price == null ) { deal.setPrice( parsePrice(i) ); }
				if ( deal.value == null ) { deal.setValue( parseValue(i) ); }
				if ( deal.saving == null ) { deal.setSaving( parseSaving(i) ); }
				if ( deal.discount == null ) { deal.setDiscount( parseDiscount(i) ); }
				if ( deal.city == null ) { deal.setCity( parseCity(i) ); }

				tagger.tagString( deal.text.replace("£", "").replace("$", "") );
				deal.setNouns( parseNouns(tagger.nouns.toString()) );
				deal.setVerbs( parseVerbs(tagger.verbs.toString()) );
				
				deals.add( deal );
				
//			    System.out.println( deal.getCity() );
			    i++;
			}
			/*
		}
		catch( SocketException e1 )
		{
			System.out.println( e1.getClass().getSimpleName() + ": " + e1.getMessage() + " for " + url);
		}
		catch( SocketTimeoutException e2 )
		{
			System.out.println( e2.getClass().getSimpleName() + ": " + e2.getMessage() + " for " + url);
		}
		catch( Exception e )
		{
			e.printStackTrace();
			throw e;
		}
		*/
		return deals;
	}
	
	public String parseText(int i)
	{
		if ( texts.size() < 1 ) { return ""; }		
		return texts.get(i).text();
	}
	public String parseUrl(int i)
	{
		if ( urls.size() < 1 ) { return ""; }		
		return urls.get(i).text();
	}
	public String parsePrice(int i)
	{
		if ( prices.size() < 1 ) { return ""; }		
		return prices.get(i).text();
	}
	public String parseSaving(int i)
	{
		if ( savings.size() < 1 ) { return ""; }		
		return savings.get(i).text();
	}
	public String parseValue(int i)
	{
		if ( values.size() < 1 ) { return ""; }		
		return values.get(i).text();
	}
	public String parseDiscount(int i)
	{
		if ( discounts.size() < 1 ) { return ""; }		
		return discounts.get(i).text();
	}
	public String parseCity(int i)
	{
		if ( cities.size() < 1 ) { return city; }
		return cities.get(i).text();
	}	
	public String parseNouns(String st)
	{ 
		st = st.replace("[","").replace("]","").replace(",","");
		for(int i=0; i<nounStoplist.length; i++) { st = st.replace(nounStoplist[i],""); }
		st = st.replace("  "," ");
		return st.trim(); 
	}	
	public String parseVerbs(String st)
	{ 
		return st.replace("[","").replace("]","").replace(",","");
	}
	
	private DecimalFormat threeDec = new DecimalFormat("0.00");
	public String dec(double inValue)
	{
//		threeDec.setGroupingUsed(false);
		return threeDec.format(inValue);
	}
	public String pc(double lo, double hi)
	{
		return threeDec.format(100 * (lo / hi));
	}
	public String diff(double lo, double hi)
	{
		return threeDec.format(hi-lo);
	}

}
