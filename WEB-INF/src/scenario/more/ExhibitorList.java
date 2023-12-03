package scenario.more;

import java.util.Collection;
import java.util.Iterator;
import java.util.TreeSet;

import leafspider.util.Log;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import soft.portfolio.Stock;

public abstract class ExhibitorList 
{	
	public abstract void populate() throws Exception;
	
	public abstract String listUrl();
	
	protected int pageNum = 1;
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	
	protected String eventId = null;
	public String getEventId()
	{
		if( eventId == null )
		{
			eventId = eventName.replaceAll("[, ]", "");
		}
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public Collection links = null;
	public Collection getLinks() {
		return links;
	}
	public void setLinks(Collection links) {
		this.links = links;
	}
	
	private String eventName = null;
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
}
