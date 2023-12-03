package leafspider.spider;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.Element;

import leafspider.util.Log;

public class Feed 
{
    public static void main(String[] args)
	{
    	String url = "http://www.atocharesources.com/news-feed.html";
    	if(Feed.isFeed(url))
    	{
	    	Feed feed = new Feed();
	    	feed.parse(url);
    	}
	}

	public void parse(String url)
	{
		setFeedUrl(url);
		
		Element channel = downloadXml(feedUrl).getChild("channel");		
        setFeedTitle(channel.getChildText("title"));		
        setFeedLink(channel.getChildText("link"));

        items = new ArrayList();
        Iterator itemList = channel.getChildren("item").iterator();
        while(itemList.hasNext())
        {
        	Element itemEl = (Element) itemList.next();
        	FeedItem feedItem = new FeedItem();
        	feedItem.title = itemEl.getChildText("title");
        	feedItem.link = itemEl.getChildText("link");
        	feedItem.desc = itemEl.getChildText("description");
        	items.add(feedItem);
        }
	}

    public static Element downloadXml( String url )
	{
    	Element root = null;
		try
		{
			SAXBuilder saxDoc = new SAXBuilder();
			Document doc = saxDoc.build(new URL(url));
			root = (Element) doc.getRootElement();	
		}
		catch (Exception e)
		{
			Log.warnln( "Exception: ", e );
		}
		return root;
	}

	private String feedUrl = null;
	public String getFeedUrl() {
		return feedUrl;
	}
	public void setFeedUrl(String feedUrl) {
		this.feedUrl = feedUrl;
	} 

	public List items = null;
	
	private String feedTitle = null;
	public String getFeedTitle() {
		return feedTitle;
	}
	public void setFeedTitle(String feedTitle) {
		this.feedTitle = feedTitle;
	}
	
	private String feedLink = null;
	public String getFeedLink() {
		return feedLink;
	}
	public void setFeedLink(String feedLink) {
		this.feedLink = feedLink;
	}
	
	public class FeedItem
	{
		public String title = null;
        public String link = null;
        public String desc = null;    	
	}
	
	public static boolean isFeed(String url)
	{
		try
		{
			String name = downloadXml(url).getName();
			Log.infoln("Feed.name=" + name);
			if (name != null && name.equalsIgnoreCase("rss")) { return true; }
		}
		catch(Exception e)
		{
			Log.infoln(e);
		}
		return false;
	}
}
