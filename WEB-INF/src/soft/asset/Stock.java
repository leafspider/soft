package soft.asset;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

import leafspider.util.Downloader;
import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.Util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

public class Stock extends Asset
{
	public Stock( String ticker ) { super( ticker ); }
	
}
