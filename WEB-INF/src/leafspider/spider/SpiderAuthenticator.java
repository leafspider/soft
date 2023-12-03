package leafspider.spider;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

import leafspider.util.*;

public class SpiderAuthenticator extends Authenticator 
{
	protected PasswordAuthentication getPasswordAuthentication() 
	{
		Log.infoln( "------- SpiderAuthenticator.getPasswordAuthentication() ------" );
		
//		String username = "mhurst@cirilab.com";				
//		String password = "leonardo27";	
		String username = "mpagah@hotmail.com";
		String password = "feynman27";		
		return new PasswordAuthentication ( username, password.toCharArray() );
    }
	
}
	
