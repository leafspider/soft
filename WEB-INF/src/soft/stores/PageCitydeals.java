package soft.stores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import leafspider.util.*;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class PageCitydeals extends Page
{	
	public void init()
	{
//		qtext = "div#search-deal-restrictions a";		
		qtext = "div#search-deal-logo a";		
		qurl = "div#search-deal-restrictions a";	
		qprice = "div.search-deal-price-price span.price";
		qsaving = "na";
		qvalue = "div#search-deal-title span.price";
		qdiscount = "div.search-deal-savings";
		qcity = "na";
	}

	@Override
	public String parseText(int i)
	{
		if ( texts.size() < 1 ) { return ""; }		
		String st = texts.get(i).attr("alt");
		return st;
	}
	
	@Override
	public String parseUrl(int i)
	{
		if ( urls.size() < 1 ) { return ""; }		
		String st = urls.get(i).attr("href");
		return "http://www.citydeals.com" + st.trim();
	}

	@Override
	public String parseValue(int i)
	{
		if ( values.size() < 1 ) { return ""; }	
		double value = Double.parseDouble(values.get(i).text().replace("$","").replace(",","").trim());
		double price = Double.parseDouble(prices.get(i).text().replace("$","").replace(",","").trim());
		double saving = value - price;
		
		Element el = texts.get(i).clone();		
		el.text("$" + dec(saving));		
		savings.add(el);	
		
		return "$" + value;
	}
	
	@Override
	public String parseCity(int i)
	{
		if ( cities.size() < 1 ) 
		{ 
			// index?q=All&customer_location=Richmond&category=%25&zip_miles=25&filter=popularity US 
			String[] vals = url.split("/");
			String city = vals[vals.length-1].replace("%20", " ");	
			int pos1 = city.indexOf("&customer_location=");
			int pos2 = city.indexOf("&category=");
			return city.substring(pos1+19,pos2) + " NA";
		}			
		return cities.get(i).text() + " NA";
	}
}
