package soft.toffee;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.conf.ConfigurationBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TwitterCreds {

    /*
    public String consumerKey = "3J4GAnDZOP6bxBiOeGHTvQigj";	//"c28bt6iBCSKD8gK0PywjqKDnk";	//"TKlcOjftpx0Phtj3hTphAarkT";	//"98roGGy9M0wJ5MlCZ6WlPA";	//"lXpa4myI1eGE1EjGIAIpA";
    public String consumerSecret = "RHpgYvDElbPo3JxuTcfqUZEcx2zUDL9UIHcUEssBcP2IiQz3XB";	 //"9V2887cGgKC48HhaKEzi1g1Z8ow9u4skYfP7jzglEh4OuSyjlq";	//"4prwryNXv8L35GD2pqQ860ArAiS8qMLXKvVt8r0b6NoiX9eBL2";	//"gt3ve8YSCznEhjZDklPavAd57MFYLciRB4rMFAUM";	//"feEAx2NXqtC8NwMsCVpfv6Gr21F2Rtr4v68G4RGDs";
    public String accessToken = "15233121-DrjFBake2cMLuHtW18ulAYkVXMd8rRmph1j42MmXF";	//"15233121-JWfxHzkK2xmg4kWm0PxYoycsutaxgeHvpY56eHKQF";	//"15233121-JWfxHzkK2xmg4kWm0PxYoycsutaxgeHvpY56eHKQF";	//"15233121-JWfxHzkK2xmg4kWm0PxYoycsutaxgeHvpY56eHKQF";	//"15233121-C0ylFCBftjxgE5TK0mGnS0G7grPVBcmwFzcB2ACk3";
    public String accessTokenSecret = "sxZy2MsgEapuK5k9pBPBAKrnlPbx3Ap98tf5D9KGPRlte";	//"HAXb7BEMKUrDtR90seKFxj9OoLxkt8iVMaqoxUNH27cfs";	//"HAXb7BEMKUrDtR90seKFxj9OoLxkt8iVMaqoxUNH27cfs";	//"VKc0LWpKM8TySlc80ZPHzjCNgiEQdGln60fbL4GyqZ8Oe";
    */

    public static int agent = 0;
    public static int search = 1;

    public static Twitter twitterInstance( int type ) throws Exception {

        String consumerKey;
        String consumerSecret;
        String accessToken;
        String accessTokenSecret;

        if( type == agent ) {
            consumerKey = "c28bt6iBCSKD8gK0PywjqKDnk";
            consumerSecret = "9V2887cGgKC48HhaKEzi1g1Z8ow9u4skYfP7jzglEh4OuSyjlq";
            accessToken = "15233121-JWfxHzkK2xmg4kWm0PxYoycsutaxgeHvpY56eHKQF";
            accessTokenSecret = "HAXb7BEMKUrDtR90seKFxj9OoLxkt8iVMaqoxUNH27cfs";
        }
        else if ( type == search ) {
            consumerKey = "3J4GAnDZOP6bxBiOeGHTvQigj";
            consumerSecret = "RHpgYvDElbPo3JxuTcfqUZEcx2zUDL9UIHcUEssBcP2IiQz3XB";
            accessToken = "15233121-DrjFBake2cMLuHtW18ulAYkVXMd8rRmph1j42MmXF";
            accessTokenSecret = "sxZy2MsgEapuK5k9pBPBAKrnlPbx3Ap98tf5D9KGPRlte";
        }
        else {
            throw new TwitterException( "Unrecognised Twitter credential type" );
        }

        ConfigurationBuilder cb = new ConfigurationBuilder().setOAuthConsumerKey( consumerKey )
                .setOAuthConsumerSecret( consumerSecret )
                .setOAuthAccessToken( accessToken )
                .setOAuthAccessTokenSecret( accessTokenSecret )
                .setTweetModeExtended(true);

        TwitterFactory twitterFactory = new TwitterFactory( cb.build() );
        Twitter twitter = twitterFactory.getInstance();

        /*
        twitter.setOAuthConsumer( consumerKey, consumerSecret );
        twitter.setOAuthAccessToken(new AccessToken( accessToken, accessTokenSecret ) );
        twitter.setTweetModeExtended(true);
        */

        return twitter;
    }

}
