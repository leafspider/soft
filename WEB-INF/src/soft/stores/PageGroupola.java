package soft.stores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import leafspider.util.*;

import org.jsoup.select.Elements;


public class PageGroupola extends Page
{	
	public void init()
	{
		qtext = "div.deal_main div.detail";		
		qurl = "div.deal_right div.deal_status span.deal_price a.get_deal";	
		qprice = "span.deal_price";
		qsaving = "span.deal_saving";
		qvalue = "span.deal_value";
		qdiscount = "span.deal_discount";
		qcity = "span.deal_cityname:has(span.deal_available)";
	}

	@Override
	public String parseUrl(int i)
	{
		if ( urls.size() < 1 ) { return ""; }		
		String st = urls.get(i).attr("href");
		int pos = st.lastIndexOf("http");
		if (pos > -1) { return st.substring(pos); }
		return st.trim();
	}
	
	@Override
	public String parseSaving(int i)
	{
		if ( savings.size() < 1 ) { return ""; }		
		return savings.get(i).text().replace("Saving: ", "").trim();
	}

	@Override
	public String parseValue(int i)
	{
		if ( values.size() < 1 ) { return ""; }		
		return values.get(i).text().replace("Value: ", "").trim();
	}

	@Override
	public String parseDiscount(int i)
	{
		if ( discounts.size() < 1 ) { return ""; }		
		return discounts.get(i).text().replace("Discount: ", "").trim();
	}

	@Override
	public String parseCity(int i)
	{
		if ( cities.size() < 1 ) 
		{ 
			String[] vals = url.split("/");
			return vals[vals.length-1] + " UK";	
		}		
		return cities.get(i).text().replace("Available in: ", "").trim() + " UK";
	}
}
