package soft.stores;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import leafspider.util.*;

import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;


public class PageCrowdsavings extends Page
{	
	public void init()
	{
		qtext = "div.content div a span";		
		qurl = "div.content div a:has(span)";	
		qprice = "na";
		qsaving = "na";
		qvalue = "na";
		qdiscount = "na";
		qcity = "na";
	}

	@Override
	public String parseUrl(int i)
	{
		if ( urls.size() < 1 ) { return ""; }		
		String st = urls.get(i).attr("href");
//		int pos = st.lastIndexOf("http");
//		if (pos > -1) { return st.substring(pos); }
		return st.trim();
	}
	
	@Override
	public String parseCity(int i)
	{
		String[] vals = url.split("/");
		return vals[vals.length-1] + " NA";
	}	

	@Override
	public String parseText(int i)
	{
		if ( texts.size() < 1 ) { return ""; }		
		String st = texts.get(i).text();
		String[] vals = st.split("[$]");
		double hi = 0d;
		double lo = 10000d;
		for(int v=0; v<vals.length; v++)
		{
			int pos = vals[v].indexOf(" ");
			if (pos > -1)
			{
				double dub = Double.parseDouble(vals[v].replace(",","").substring(0,pos));
				if (dub > hi) { hi = dub; }
				if (dub < lo) { lo = dub; }
			}			
		}
		
		Element el = texts.get(i).clone();		
		el.text("$" + dec(hi));		
		values.add(el);
		
		el = texts.get(i).clone();
		el.text("$" + dec(lo));
		prices.add(el);
				
		el = texts.get(i).clone();
		el.text("" + pc(lo,hi) + "%");
		discounts.add(el);
		
		el = texts.get(i).clone();
		el.text("$" + diff(lo,hi) );
		savings.add(el);
				
		return st;
	}
}
