package leafspider.spider;

import java.io.File;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Vector;

import javax.swing.text.html.parser.ParserDelegator;

import leafspider.util.*;

public class TwitterParser 
{
	private static final String twitterUrl = "http://twitter.com";
	
	public boolean parseFile( File readerFile, URL theUrl, File downloadFolder )
	{
		try
		{
			/* jmh 2012-08-22 TODO Replace with JSoup
			String pageUrl = theUrl.toString();
			TwitterQueryResults results = new TwitterQueryResults( pageUrl, "", downloadFolder );
			results.getResultsLinks( readerFile, downloadFolder, theUrl );
			
			setTweets( new LinkedHashSet() );
			setLinks( new LinkedHashSet() );
						
			Iterator tweetsit = results.links.iterator();		// For TwitterQueryResults resultsLinks are actually Tweets
			while( tweetsit.hasNext() )
			{
				String tweet = (String) tweetsit.next();
//				System.out.println( "tweet=" + tweet );

				String tweetToAdd = swingConvertHTMLToText( tweet );
				tweetToAdd = Util.replaceSubstring( tweetToAdd, "http://", "" );		// jmh 2011-11-13
				getTweets().add( tweetToAdd );
				
				Iterator linksit = parseLinks( tweet ).iterator();
				while( linksit.hasNext() )
				{
					String link = (String) linksit.next();
					getLinks().add( link );
//					System.out.println( "" + links.size() + " link=" + link );
				}
			}		
			*/
			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace();
			return false;
		}
	}

	public synchronized static String swingConvertHTMLToText(String in)
	{
		try
		{
			StringWriter out = new StringWriter();
			TagStripper callback = new TagStripper(out);
			callback.setLineSeparator(" ");
			new ParserDelegator().parse(new StringReader(in), callback, true);
			return out.toString();
		}
		catch (Exception e)
		{
			Log.warnln("Exception: ", e);
			return null;
		}
	}
	
	private ArrayList parseLinks( String content )
	{
		if ( content.indexOf( "German Village Produces 321" ) > -1 )
		{
			int bob = 0;
		}
		ArrayList links = new ArrayList();
		String[] vals = content.split( "<a " );
		String start = "href=\"";
//		String end = "\" class=\"tweet-url web\"";		// jmh 2011-08-26
		String end = "\" target=\"_blank";
		for (String val : vals) 
		{
			if ( val.indexOf( "class=\"tweet-url web\"" ) > -1 )
			{
				int pos1 = val.indexOf( start );
				if ( pos1 > -1 )
				{
					int pos2 = val.indexOf( end );
					if ( pos2 > pos1 )		// jmh 2011-11-13 
					{
						val = val.substring( pos1+start.length(), pos2 );
						links.add( val );
					}
				}
			}
		}
		return links;
	}

	private LinkedHashSet tweets = null;
	public LinkedHashSet getTweets() {
		return tweets;
	}
	public void setTweets(LinkedHashSet tweets) {
		this.tweets = tweets;
	}
	
	private LinkedHashSet links = null;
	public LinkedHashSet getLinks() {
		return links;
	}
	public void setLinks(LinkedHashSet links) {
		this.links = links;
	}
}
