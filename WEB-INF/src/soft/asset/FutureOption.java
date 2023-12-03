package soft.asset;

import java.io.File;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import leafspider.fuzzy.Fuzzy;
import leafspider.stats.LinearRegression;
import leafspider.stats.Statistics;
import leafspider.util.Downloader;
import leafspider.util.LinkContentDownloader;
import leafspider.util.Log;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FutureOption extends Future
{
	public FutureOption( String ticker ) { super( ticker ); }

    protected String getLink()
	{
		return "http://www.uscharts.com/options/ohist.php?sym=" + ticker.replace("|", "%7C") + "&csv=yes";
	}

	public String chartUrl()
	{
		return "http://www.uscharts.com/options/ohist.php?sym=" + ticker.replace("|", "%7C");
	}
    
    
    
    
    
    
    
    
    
    
    
    
	public Collection obloadPrices( Calendar start, Calendar end ) throws Exception
	{		
		/*
//		String url = "http://www.uscharts.com/options/ohist.php?sym=SIZ16|8500P&csv=yes";
//		String url = "http://www.uscharts.com/options/ohist.php?sym=" + ticker + "&csv=yes";		// can't login
		String url = "http://old.barchart.com/commodityfutures/Crude_Oil_WTI_Futures/options/CLX12";
		String url = "http://old.barchart.com/commodityfutures/Crude_Oil_WTI_Futures/CLX12";
		File folder = History.historyFolder( ticker, end );
		folder.mkdirs();
//		*/
		
//		File file = new File( "C:\\Workspace\\Ultra\\Jake\\Options\\Silver Dec 16 8600P History.csv" );	//Downloader.downloadFile( url, folder );
		File file = new File( "C:\\Workspace\\Ultra\\Jake\\Options\\Crude Oil WTI Nov 12 93.00P History.csv" );
		prices = (ArrayList) History.readStock( file );			
		return prices;
	}
	
	public File obdownloadFile( String url, File folder ) throws MalformedURLException
	{		
		int downloadTimeout = 3000;
		LinkContentDownloader downloader = new LinkContentDownloader( "" + url, folder );
		downloader.setUserName("DubKay");
		downloader.setPassword("password");
		downloader.getResultFile().delete();
		
		if ( !downloader.getResultFile().exists() )
		{
			downloader.startThread();
			try
			{
				downloader.join( downloadTimeout );
			}
			catch( Exception e )
			{
				e.printStackTrace();
			}

			if ( downloader.getResultFile().exists() )
			{
//				Log.infoln( "Created file: length=" + downloader.getResultFile().length() + " " + downloader.getResultFile().getName() );					
			}
			else
			{
				Log.infoln( "Downloader.downloadFile Empty file: " + downloader.getResultFile().getName() );
				downloader.success = false;
				try
				{
					PrintStream writer = Util.getPrintStream( downloader.getResultFile().getAbsolutePath() );
//					writer.println( "<!--" + url + "-->" );
					writer.close();
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}

			if ( downloader.isAlive() )
			{
				downloader.stopThread();
			}
		}
		return downloader.getResultFile();
	}
	
	public static boolean obisFuture( String ticker )
	{
		String[] futures = { "AD_0_I0B","BO20_I0B","BP_0_I0B","C2_0_I0B","CC_0_I0B","CD_0_I0B","CL20_I0B","CT_0_I0B","CU_0_I0B","DX_0_I0B","ED_0_I0B","EM_0_I0B","ES_0_I0B","FC_0_I0B","GC20_I0B","HG20_I0B","HO20_I0B","JY_0_I0B","KC_0_I0B","LB_0_I0B","LC_0_I0B","LH_0_I0B","MP_0_I0B","ND_0_I0B","NG20_I0B","O2_0_I0B","OJ_0_I0B","PA20_I0B","PB_0_I0B","PL20_I0B","RB20_I0B","S2_0_I0B","SB20_I0B","SF_0_I0B","SI20_I0B","SM20_I0B","SP20_I0B","TY_0_I0B","US_0_I0B","W2_0_I0B" };
		int pos = Arrays.binarySearch( futures, ticker );
		if ( pos > -1 ) { return true; }
		return false;
	}
	
	public boolean obdoCrush( Calendar start, Calendar end )
	{
		try
		{
			croutFile = getCroutFile( end );			
			if ( !croutFile.exists() )
			{
				croutFile.getParentFile().mkdirs();
			}
			if( prices == null )
			{
				loadPrices( start, end );
			}
			if ( prices == null || prices.size() < 1 )
			{
				return false;
			}

			// Date	Open	High	Low	Close	Volume	Open Int	Premium($)
			volumeSeries = parsePrices( 5 );
			closeSeries = parsePrices( 4 );
			
			nVol = normSeries( getVolumeSeries() );		// normalised volume
			nClose = normSeries( getCloseSeries() );	// normalised close price
			sdLong = getSdLongSeries();					// long time-frame close standard deviation 
			sdMedium = getSdMediumSeries();				// medium time-frame close standard deviation
			sdShort = getSdShortSeries();				// short time-frame close standard deviation
			nsdLong = normSeries( sdLong );				// normalised long period close standard deviation
			nsdMedium = normSeries( sdMedium );			// normalised medium period close standard deviation
			nsdShort = normSeries( sdShort );			// normalised short period close standard deviation
			dnsdLong = derivativeSeries( nsdLong );		// 
			dnsdMedium = derivativeSeries( nsdMedium );	// 
			dnsdShort = derivativeSeries( nsdShort );	// 
			sdsd = new double[prices.size()];			// 
			
			double min = 1.0;
			double max = 0.0;
			for( int i=0; i<prices.size(); i++) 
			{ 
				if ( i >= 2*periodLong+1 ) 
				{ 
					sdsd[i] = Statistics.sdKnuth( new double[]{ sdMedium[i], sdLong[i] } ); 
				} 
				if ( i > prices.size() - periodLong )
				{
					min = Math.min(min, nClose[i]);
					max = Math.max(max, nClose[i]);
				}
			}
			cherryWidth = max - min;
//			Log.infoln( "channelWidth=" + channelWidth );
			nsdsd = normSeries( sdsd );
//			dnClose = derivativeSeries( nClose );
			crushSeries = new double[prices.size()];
			crushGradient = new double[prices.size()];

			out( ticker );
			out( "," + ticker + " close" );		// nclose
			out( "," + ticker + " flow" );		// nvol
			out( "," + ticker + " peel" );		// nsdLong
			out( "," + ticker + " jam" );		// nsdMedium
			out( "," + ticker + " press" );		// nsdShort
			out( "," + ticker + " crush" );
			
			// Extras
			out( "," + ticker + " nsdsd" );
//			out( "," + ticker + " dnsdLong" );
//			out( "," + ticker + " dnsdMedium" );
//			out( "," + ticker + " dnsdShort" );
			outln( "" );
						
			for( int i=0; i<prices.size(); i++)
			{
				if ( i == 222 )		// DEBUG
				{
					int bob = 0;
				}

				String datest;
				DateFormat format;
				if ( obisFuture( ticker ) )
				{
					datest = "" + ((String[])prices.get( i ))[0];
					format = new SimpleDateFormat( "yyyyMMdd" );					
				}
				else if ( isOption( ticker ) )
				{
					datest = "" + ((String[])prices.get( prices.size()-i-1 ))[0];
					format = new SimpleDateFormat( "dd/MM/yyyy" );					
				}
				else
				{
					datest = "" + ((String[])prices.get( prices.size()-i-1 ))[0];
					format = new SimpleDateFormat( "yyyy-MM-dd" );
				}
				Date date = (Date)format.parse( datest ); 
				  
				out( datest );				
				out( "," + nClose[i] );
				out( "," + nVol[i] );
				if ( i > periodLong ) { out( "," + nsdLong[i] ); } else { out( "," ); }
				if ( i > periodMedium ) { out( "," + nsdMedium[i] ); } else { out( "," ); }
				if ( i > periodShort ) { out( "," + nsdShort[i] ); } else { out( "," ); }
				
				// Calculate crush				
				double crush = 0.0d;
				
				if ( isUseFuzzy )
				{
					// Fuzzy CrushMap
					Fuzzy fuzzy = getFuzzyCrush();					
			    	fuzzy.set( "nvol", nVol[i] );
			    	fuzzy.set( "nsdLong", nsdLong[i] );
			    	fuzzy.set( "nsdMedium", nsdMedium[i] );
			    	fuzzy.set( "nsdShort", nsdShort[i] );
			    	fuzzy.set( "dnsdLong", dnsdLong[i] );
			    	fuzzy.set( "dnsdMedium", dnsdMedium[i] );
			    	fuzzy.set( "dnsdShort", dnsdShort[i] );
			    	fuzzy.set( "nsdsd", nsdsd[i] );
			    	fuzzy.evaluate();
			    	
			    	crush = (fuzzy.get( "crush1" ) +
			    			fuzzy.get( "crush2" ) +
			    			fuzzy.get( "crush3" ) +
			    			fuzzy.get( "crush4" ) +
			    			fuzzy.get( "crush5" ) +
			    			fuzzy.get( "crush6" ) +
			    			fuzzy.get( "crush7" ) +  
			    			nVol[i]) / 8;

			    	if ( i == 598 )		// DEBUG
			    	{
			    		if ( isDisplayFuzzySets ) { fuzzy.show( "crush1" ); }
			    		String why = fuzzy.why( "crush1" );
//			    		outln( "[nsd30=" + nsd30[i] );
			    	}
				}
				else
				{
					// Linear CrushMap
					double nsdMax = 0.4;
					double nsdsdMax = 0.2;	
					double dnsdLongMax = 0.1d;
					double dnsdMediumMax = 0.2d;	
					double dnsdShortMin = 0.0d;
					
					if (   dnsdShort[i] > dnsdShortMin 
						&& dnsdMedium[i] < dnsdMediumMax 
						&& dnsdLong[i] < dnsdLongMax 
						&& nsdsd[i] < nsdsdMax )
					{
						crush = Math.max( (3-(nsdShort[i]+nsdMedium[i]+nsdLong[i])/nsdMax)/3, 0) * nVol[i];
					}
				}

				if ( i >= periodLong ) 
				{ 
					crushSeries[i] = crush;
					out( "," + crush );
					
					if ( i >= (periodLong+periodLinReg) ) 
					{
						double lowest = 1.d;
						int xstart = i-periodLinReg;						
//						for ( int j=0; j<periodLinReg; j++)		// jmh 2012-08-13 Attempt to remove spikes 
						for ( int j=0; j<periodLinReg-5; j++)
						{
							int k = i-periodLinReg+j;
							double val = crushSeries[k];
							if ( val < lowest )
							{
								lowest = val;
								xstart = k;		
//								System.out.println("xstart=" + xstart );						
							}
						}
						int len = i-xstart;

						xvals = new double[len];
						yvals = new double[len];

						for ( int j=0; j<len; j++)
						{
							xvals[j] = (double)j;
							double yval = crushSeries[i-len+j];
							yvals[j] = yval;
						}
						
						if( len > 1 )
						{
							LinearRegression linreg = new LinearRegression(xvals, yvals);
							crushGradient[i] = Math.max(0, len * linreg.getSlope());
//							crushGradient[i] = Math.max(0, linreg.getIntercept());
//							System.out.println("len=" + len + " slope=" + linreg.getSlope() + " grad=" + crushGradient[i]);
						}
						else
						{
							crushGradient[i] = 0.0;
						}
						
//						double lowest = (y1 + y2)/2;
//						int width = Math.abs(x2-x1);						
//						crushGradient[i] = Math.max((crush - lowest)/(Math.sqrt(width)),0);
//						System.out.println(" l=" + lowest + " w=" + width + " g=" + crushGradient[i] );
					}
					else 
					{ 
						crushGradient[i] = 0.d;
					}	
				} 
				else 
				{ 
					crushSeries[i] = 0.d;
					out( "," ); 
					crushGradient[i] = 0.d;
				}				

				if ( i > prices.size() - periodShort ) { crushShortTotal += crush; }	// Only if in last n days
				if ( i > prices.size() - periodMedium) { crushMediumTotal += crush; }	// Only if in last n days
				if ( i > prices.size() - periodLong ) 
				{ 
					crushLongTotal += crush; 
				}		// Only if in last n days
								
				// Extras
				if ( i > periodLong ) { out( "," + nsdsd[i] ); } else { out( "," ); }
//				if ( i > periodLong ) { out( "," + dnsdLong[i] ); } else { out( "," ); }
//				if ( i > periodMedium ) { out( "," + dnsdMedium[i] ); } else { out( "," ); }
//				if ( i > periodShort ) { out( "," + dnsdShort[i] ); } else { out( "," ); }
				outln( "" );
			}
			
			plate = getPlate(periodShort);
			
			crushes = new double[] { (100*crushShortTotal)/periodShort, (100*crushMediumTotal)/periodMedium, (100*crushLongTotal)/periodLong };
			
			return true;
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		return false;
	}
}
