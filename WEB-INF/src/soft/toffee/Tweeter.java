package soft.toffee;

import leafspider.util.Log;
import twitter4j.*;
import twitter4j.auth.AccessToken;

import java.io.IOException;
import java.io.Serializable;
import java.util.*;

public class Tweeter implements Serializable
{
	public String userName;
	private Twitter twitter;

	public Collection<Signal> signals = new TreeSet<Signal>();
	public Collection<Signal> getSignals() { return signals; }
	public void setSignals(Collection<Signal> signals) { this.signals = signals; }

	public Tweeter( Twitter twitter, String userName ) {

		this.userName = userName;
		this.twitter = twitter;
	}

	public static void main(String[] args) throws IOException, TwitterException {

		//test("HamzeiAnalytics" );
		test("dhaber" );
	}

	public static void test(String userName) {

		Log.infoln("-- User: " + userName + " --");

		try {

			Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.search );

			Tweeter tweeter = new Tweeter(twitter, userName);

			List<Status> statuses = tweeter.getStatuses();
			Collections.reverse(statuses);
			Log.infoln("------ Statuses ------");
			for (Status status : statuses) {
				Log.infoln(status.getText());
			}

			List<String> friends = tweeter.getFriends();
			//IDs friends = twitter.getFriendsIDs(1000);
			Log.infoln("------ Following ------");
			for (String friend : friends) {
				Log.infoln(friend);
			}

			List<String> followers = tweeter.getFollowers();
			Log.infoln("------ Followers ------");
			for (String follower : followers) {
				Log.infoln(follower);
			}
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	public List<Status> getStatuses() throws Exception  {
		return twitter.getUserTimeline( userName );
	}
	
	public Tweet topRetweet;

	public List getFollowers() throws Exception {

		ArrayList names = new ArrayList();
		Iterator<User> followers = twitter.getFollowersList(userName, -1L, 100).iterator();
		while ( followers.hasNext() ) {
			User follower = followers.next();
			names.add( follower.getScreenName() );
			//System.out.println(followers.getScreenName());
		}
		return names;
	}

	public List getFriends() throws Exception {

		ArrayList names = new ArrayList();
		Iterator<User> friends = twitter.getFriendsList(userName, -1L, 100).iterator();
		while ( friends.hasNext() ) {
			User friend = friends.next();
			names.add( friend.getScreenName() );
			//System.out.println(friend.getScreenName());
		}
		return names;
	}

}

/* 
	public String getUserName() { return userName; }
	public void setUserName(String screenName) { this.userName = screenName; }
	
	
	public void setStatuses(List<Status> statuses) { this.statuses = statuses; }
	

	public void report() throws Exception
	{
//		if( statuses == null ) { loadTimeline(); }
        for (Status status : statuses) { Log.infoln( status.getUser().getScreenName() + " - " + status.getText() ); }
	}

	public Tweeter obread() throws Exception
	{
		/*
		XMLDecoder dec = new XMLDecoder(new BufferedInputStream(new FileInputStream(getFile())));
		Tweeter req = (Tweeter) dec.readObject();
		dec.close();
		return req;
		*/

		/*
		FileInputStream door = new FileInputStream( obgetFile() ); 
		ObjectInputStream reader = new ObjectInputStream(door); 
		return (Tweeter) reader.readObject();
	}

	public void obwrite() throws Exception
	{
		/*
		FileOutputStream os = new FileOutputStream( getFile() );
		XMLEncoder enc = new XMLEncoder( os );
		enc.setPersistenceDelegate( Status.class, new DefaultPersistenceDelegate( new String[] { "text" } ) );
		enc.writeObject( this );
		enc.close();
		*/
	
		/*
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(obgetFile()));
		out.writeObject(this);
		out.close();
	}
	
	private File obgetFile()
	{
		return new File( ServerContext.toffeeDataFolder() + "\\pojo\\Tweeter_" + userName + ".obj" );
	}
	*/

