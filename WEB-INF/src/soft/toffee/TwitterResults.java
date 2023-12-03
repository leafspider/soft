package soft.toffee;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.QueryResult;

import java.io.Serializable;
import java.util.List;

public class TwitterResults implements Serializable
{
	public twitter4j.Query query;
	private Twitter twitter;

	private List<twitter4j.Status> statuses;
	public List<twitter4j.Status> getStatuses() throws Exception { return statuses; }

	public TwitterResults(Twitter twitter, twitter4j.Query query ) {

		this.query = query;
		this.twitter = twitter;
	}

	public List<Status> load() throws Exception {

		//SearchResource searchResource = twitter.search();		// jmh 2022-11-02
		//QueryResult result = searchResource.search(query);	// jmh 2022-11-02
		QueryResult result = twitter.search(query);
		return statuses = result.getTweets();
	}

	public Tweet topRetweet;
}


