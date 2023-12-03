package soft.asset;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

import leafspider.db.DatabaseManager;
import leafspider.db.DatabaseRecord;
import leafspider.fuzzy.Fuzzy;
import leafspider.stats.AlphanumComparator;
import leafspider.stats.LinearRegression;
import leafspider.stats.Statistics;
import leafspider.util.Downloader;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;
import soft.batch.BatchProject;

public abstract class Asset implements Comparable
{
	public static boolean debug = false;
	public ArrayList prices = null;
	public double[] crushes = null;
	public static boolean isUseFuzzy = true;
	public double cherryWidth = 1.0;

	protected int periodShort = 10;
	protected int periodMedium = 20;
	protected int periodLong = 30;
	protected int periodLinReg = 20;
	protected static boolean isDisplayFuzzySets = false;
	protected String clazz = this.getClass().getSimpleName();
	
	public static void main( String[] args )
	{
		try
		{				
			/*
			String[] listRangeAdvances = { "LTBR","DVR","VGZ","NTSP","ONCY","BRC","MXL","APKT","PGNX","EZCH","USAT","ACUR","MXWL","HA","AMCC","GIVN","CPWM","MNKD","TRNX","INFN","FRP","ENTR","INTG","CCRN","FSTR","VPG","EXM","ROCK","GNK","ACAT","JVA","MHO","GDOT","PRO","LCRY","DAKT","ASIA","CHDX","FXCM","FNSR","GCOM","CIGX","NEOP","ARQL","HNSN","MIPS","DIN","OPK","LDL","UWN","IDIX","KEM","VVTV","ZOLT","SKX","STSA","PLX","CCSC","LL","WG","MHGC","SWSH","EK","HHS","OMN","MTG","ECHO","DQ","SCSS","HMPR","MBI","DCTH","WBS","TTMI","MRVL","MTRX","ADSK","CBRL","INAP","ENTG","MSPD","PMCS","CASY","IMAX","SEAC","DNDN","MU","CHSI","KLIC","ALTR","TWER","VECO","LCI","STBZ","FFIV","OPTR","RUTH","BRCD","GFF","JDSU" };
			String[] listGapUps = { "ULTA","HURC","SPEX","CGA","LOV","HBNK" };
			String[] listGapDowns = { "SQM","WBK","ESLT","NVO","DEG","DB","CGV","AG","TD","ZNH","SSRI","IBN","FORTY","CFX","NICE","HHC","HDB","NIHD","MKTAY","BUD","CM","SNY","AGP","VRX","RY","SHG","WPPGY","TEVA","FTE","CEA","TDG","EOC","VHI","NVS","KB","BSI","CCJ","HBC","LFC","GG","ING","BAK","TLK","UN","VE","UL","SPNS","ALV","TI","BMO","CCU","CRH","STRC","ACH","PWRD","DAR","VOD","PTNR","AZN","PHG","MWE","TCK","DHRM","CEL","AWC","MITK","CCI","IVN","CCIX","ALGN","SHPGY","AV","PAA","CTCM","YZC","VAR","THC","PT","SPPI","ITRN","AOS","PLX","HSP","RCMT","NECB","ATPG","PHI","THER","RUK","CCK","DEO","USM","SLT","QLTY","ABC","ENL","SNA","TEF","BT","TCAP" };
			String[] listETFs = { "VXX","TZA","XRT","FAZ","TNA","SMH","QID","TLT","SSO","XOP","IWM","SPXU","ZSL","XME","USO","SPY","OIH","SDS","FAS","XLB","XLI","XLF","UCO","DZZ","IYR","XLE","SLV","XLY","DXD","EDC","QQQ","KRE","SRS","EWW","XLV","UYG","RWM","UUP","DIA","XHB","GLL","FXI","DGP","GDX","IYM","EWZ","XIV","SH","KBE","UNG","EEM","TMV","EUO","TBT","XLP","RSX","EPI","EWT","EWH","EWI","MDY","EWY","IWO","EPU","GLD","XLU","IBB","XLK","SIVR","GDXJ","ILF","EWA","SIL","EWJ","OIL","EWM","FXC","YCS","IEF","KOL","EWG","IWN","EFA","VGK","EWU","EWD","EWQ","ITB","EZU","XES","FRI","DBA","PIN","SGOL","ECH","TBF","PSLV","FBT","TAN","DBO" };
			*/
			
			Calendar end = new GregorianCalendar();			
//			end.set( 2010, 3, 29 );
			Calendar start = new GregorianCalendar();
			start.set( end.get(Calendar.YEAR)-1, end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH) );			

			/*
			Assetlist list = new Assetlist( "rangeadvance" );
			list.crush( listRangeAdvances, start, end );
			*/

			String ticker = "AEDU3.SA";
			Asset asset = Asset.instance(ticker);
			asset.doCrush( start, end );
			Log.infoln( ticker + "=" + asset.crushLongTotal );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public Asset( String ticker ) {
		this.ticker = ticker.toUpperCase().trim();
	}

	private static String[] futures = { "A6H13","A6H14","A6M13","A6U13","A6Y00","A6Z12","A6Z12","A6Z13","B6H13","B6H14","B6M13","B6U13","B6Y00","B6Z12","B6Z12","B6Z13","CCH13","CCH14","CCK13","CCK14","CCN13","CCN14","CCU13","CCY00","CCZ12","CCZ12","CCZ13","CLF13","CLF14","CLF15","CLF16","CLF17","CLG13","CLG14","CLG15","CLG16","CLG17","CLH13","CLH14","CLH15","CLH16","CLH17","CLJ13","CLJ14","CLJ15","CLJ16","CLJ17","CLK13","CLK14","CLK15","CLK16","CLK17","CLM13","CLM14","CLM15","CLM16","CLM17","CLM18","CLM19","CLM20","CLN13","CLN14","CLN15","CLN16","CLN17","CLQ13","CLQ14","CLQ15","CLQ16","CLQ17","CLU13","CLU14","CLU15","CLU16","CLU17","CLV13","CLV14","CLV15","CLV16","CLV17","CLX12","CLX12","CLX13","CLX14","CLX15","CLX16","CLX17","CLY00","CLZ12","CLZ13","CLZ14","CLZ15","CLZ16","CLZ17","CLZ18","CLZ19","CLZ20","CTH13","CTH14","CTH15","CTK13","CTK14","CTK15","CTN13","CTN14","CTN15","CTV12","CTV12","CTV13","CTV14","CTY00","CTZ12","CTZ13","CTZ14","D6H13","D6H14","D6M13","D6U13","D6Y00","D6Z12","D6Z12","D6Z13","DJH13","DJM13","DJU13","DJY00","DJZ12","DJZ12","DLF13","DLF14","DLG13","DLG14","DLH13","DLH14","DLJ13","DLJ14","DLK13","DLK14","DLM13","DLM14","DLN13","DLN14","DLQ13","DLQ14","DLU12","DLU12","DLU13","DLV12","DLV13","DLX12","DLX13","DLZ12","DLZ13","DXH13","DXM13","DXU13","DXY00","DXZ12","DXZ12","E6H13","E6H14","E6M13","E6U13","E6Y00","E6Z12","E6Z12","E6Z13","ESH13","ESM13","ESU13","ESY00","ESZ12","ESZ12","ESZ13","GCG13","GCG14","GCJ13","GCJ14","GCM13","GCM14","GCM15","GCM16","GCM17","GCM18","GCQ13","GCU12","GCU12","GCV12","GCV13","GCX12","GCY00","GCZ12","GCZ13","GCZ14","GCZ15","GCZ16","GCZ17","GEF13","GEF21","GEG13","GEH13","GEH14","GEH15","GEH16","GEH17","GEH18","GEH19","GEH20","GEH21","GEH22","GEM13","GEM14","GEM15","GEM16","GEM17","GEM18","GEM19","GEM20","GEM21","GEM22","GEU13","GEU14","GEU15","GEU16","GEU17","GEU18","GEU19","GEU20","GEU21","GEU22","GEV12","GEV12","GEX12","GEZ12","GEZ13","GEZ14","GEZ15","GEZ16","GEZ17","GEZ18","GEZ19","GEZ20","GEZ21","GFF13","GFH13","GFJ13","GFK13","GFQ13","GFU12","GFU12","GFV12","GFX12","GFY00","HEG13","HEG14","HEJ13","HEK13","HEM13","HEN13","HEQ13","HEV12","HEV12","HEV13","HEY00","HEZ12","HEZ13","HGF13","HGF14","HGG13","HGG14","HGH13","HGH14","HGH15","HGH16","HGH17","HGJ13","HGJ14","HGK13","HGK14","HGK15","HGK16","HGK17","HGM13","HGM14","HGN13","HGN14","HGN15","HGN16","HGN17","HGQ13","HGQ14","HGU12","HGU12","HGU13","HGU14","HGU15","HGU16","HGV12","HGV13","HGX12","HGX13","HGY00","HGZ12","HGZ13","HGZ14","HGZ15","HGZ16","HOF13","HOF14","HOF15","HOF16","HOG13","HOG14","HOG15","HOH13","HOH14","HOH15","HOJ13","HOJ14","HOJ15","HOK13","HOK14","HOK15","HOM13","HOM14","HOM15","HON13","HON14","HON15","HOQ13","HOQ14","HOQ15","HOU13","HOU14","HOU15","HOV12","HOV12","HOV13","HOV14","HOV15","HOX12","HOX13","HOX14","HOX15","HOY00","HOZ12","HOZ13","HOZ14","HOZ15","J6H13","J6H14","J6M13","J6U13","J6Y00","J6Z12","J6Z12","J6Z13","KCH13","KCH14","KCH15","KCK13","KCK14","KCK15","KCN13","KCN14","KCN15","KCU13","KCU14","KCY00","KCZ12","KCZ12","KCZ13","KCZ14","KEH13","KEH14","KEK13","KEK14","KEN13","KEN14","KEU13","KEY00","KEZ12","KEZ12","KEZ13","LEG13","LEG14","LEJ13","LEM13","LEQ13","LEV12","LEV12","LEV13","LEY00","LEZ12","LEZ13","LSF13","LSH13","LSK13","LSN13","LSU13","LSX12","LSX12","LSX13","M6F13","M6G13","M6H13","M6H14","M6J13","M6K13","M6M13","M6N13","M6Q13","M6U13","M6V12","M6V12","M6V13","M6X12","M6Y00","M6Z12","M6Z13","MWH13","MWH14","MWK13","MWK14","MWN13","MWN14","MWU13","MWY00","MWZ12","MWZ12","MWZ13","NDH13","NDM13","NDU13","NDY00","NDZ12","NDZ12","NDZ13","NGF13","NGF14","NGF15","NGF16","NGF17","NGF18","NGF19","NGF20","NGF21","NGF22","NGF23","NGF24","NGG13","NGG14","NGG15","NGG16","NGG17","NGG18","NGG19","NGG20","NGG21","NGG22","NGG23","NGG24","NGH13","NGH14","NGH15","NGH16","NGH17","NGH18","NGH19","NGH20","NGH21","NGH22","NGH23","NGH24","NGJ13","NGJ14","NGJ15","NGJ16","NGJ17","NGJ18","NGJ19","NGJ20","NGJ21","NGJ22","NGJ23","NGJ24","NGK13","NGK14","NGK15","NGK16","NGK17","NGK18","NGK19","NGK20","NGK21","NGK22","NGK23","NGK24","NGM13","NGM14","NGM15","NGM16","NGM17","NGM18","NGM19","NGM20","NGM21","NGM22","NGM23","NGM24","NGN13","NGN14","NGN15","NGN16","NGN17","NGN18","NGN19","NGN20","NGN21","NGN22","NGN23","NGN24","NGQ13","NGQ14","NGQ15","NGQ16","NGQ17","NGQ18","NGQ19","NGQ20","NGQ21","NGQ22","NGQ23","NGQ24","NGU13","NGU14","NGU15","NGU16","NGU17","NGU18","NGU19","NGU20","NGU21","NGU22","NGU23","NGU24","NGV12","NGV12","NGV13","NGV14","NGV15","NGV16","NGV17","NGV18","NGV19","NGV20","NGV21","NGV22","NGV23","NGV24","NGX12","NGX13","NGX14","NGX15","NGX16","NGX17","NGX18","NGX19","NGX20","NGX21","NGX22","NGX23","NGX24","NGY00","NGZ12","NGZ13","NGZ14","NGZ15","NGZ16","NGZ17","NGZ18","NGZ19","NGZ20","NGZ21","NGZ22","NGZ23","NGZ24","NQH13","NQM13","NQU13","NQY00","NQZ12","NQZ12","NQZ13","OJF13","OJF14","OJF15","OJH13","OJH14","OJH15","OJK13","OJK14","OJK15","OJN13","OJN14","OJN15","OJU13","OJU14","OJX12","OJX12","OJX13","OJX14","PAH13","PAM13","PAU12","PAU12","PAU13","PAV12","PAX12","PAY00","PAZ12","PLF13","PLJ13","PLN13","PLU12","PLU12","PLV12","PLV13","PLX12","PLY00","RBF13","RBF14","RBF15","RBG13","RBG14","RBG15","RBH13","RBH14","RBH15","RBJ13","RBJ14","RBJ15","RBK13","RBK14","RBK15","RBM13","RBM14","RBM15","RBN13","RBN14","RBN15","RBQ13","RBQ14","RBQ15","RBU13","RBU14","RBU15","RBV12","RBV12","RBV13","RBV14","RBX12","RBX13","RBX14","RBY00","RBZ12","RBZ13","RBZ14","RJH13","RJM13","RJY00","RJZ12","RJZ12","RSF13","RSF14","RSH13","RSH14","RSK13","RSK14","RSN13","RSN14","RSX12","RSX12","RSX13","RSX14","RSY00","S6H13","S6H14","S6M13","S6U13","S6Y00","S6Z12","S6Z12","S6Z13","SBH13","SBH14","SBH15","SBK13","SBK14","SBK15","SBN13","SBN14","SBN15","SBV12","SBV12","SBV13","SBV14","SBY00","SIF13","SIF14","SIH13","SIH14","SIK13","SIK14","SIN13","SIN14","SIN15","SIN16","SIN17","SIU12","SIU12","SIU13","SIV12","SIX12","SIY00","SIZ12","SIZ13","SIZ14","SIZ15","SIZ16","SPH13","SPH14","SPM13","SPM14","SPU13","SPU14","SPY00","SPZ12","SPZ12","SPZ13","SPZ14","SPZ15","SPZ16","YMH13","YMM13","YMU13","YMY00","YMZ12","YMZ12","ZBH13","ZBM13","ZBZ12","ZBZ12","ZCH13","ZCH14","ZCK13","ZCK14","ZCN13","ZCN14","ZCN15","ZCU13","ZCU14","ZCY00","ZCZ12","ZCZ12","ZCZ13","ZCZ14","ZCZ15","ZFH13","ZFM13","ZFU12","ZFU12","ZFU13","ZFZ12","ZLF13","ZLF14","ZLH13","ZLH14","ZLK13","ZLK14","ZLN13","ZLN14","ZLN15","ZLQ13","ZLQ14","ZLU13","ZLU14","ZLV12","ZLV12","ZLV13","ZLV14","ZLV15","ZLY00","ZLZ12","ZLZ13","ZLZ14","ZLZ15","ZMF13","ZMF14","ZMH13","ZMH14","ZMK13","ZMK14","ZMN13","ZMN14","ZMN15","ZMQ13","ZMQ14","ZMU13","ZMU14","ZMV12","ZMV12","ZMV13","ZMV14","ZMV15","ZMY00","ZMZ12","ZMZ13","ZMZ14","ZMZ15","ZNH13","ZNM13","ZNU13","ZNZ12","ZNZ12","ZNZ13","ZOH13","ZOH14","ZOK13","ZOK14","ZON13","ZON14","ZON15","ZOU13","ZOU14","ZOU15","ZOY00","ZOZ12","ZOZ12","ZOZ13","ZRF13","ZRH13","ZRK13","ZRN13","ZRU13","ZRX12","ZRX12","ZRX13","ZSF13","ZSF14","ZSH13","ZSH14","ZSK13","ZSK14","ZSN13","ZSN14","ZSN15","ZSQ13","ZSQ14","ZSU13","ZSU14","ZSX12","ZSX12","ZSX13","ZSX14","ZSX15","ZSY00","ZWH13","ZWH14","ZWH15","ZWK13","ZWK14","ZWK15","ZWN13","ZWN14","ZWN15","ZWU13","ZWU14","ZWY00","ZWZ12","ZWZ12","ZWZ13","ZWZ14" };
	public static Asset instance( String ticker )
	{
		if( ticker.indexOf("|") > -1 ) {
			return new FutureOption( ticker );
		}
		int pos = Arrays.binarySearch( futures, ticker );
		if ( pos > -1 ) {
			return new Future( ticker );
		}
		else {
			return new Stock( ticker );
		}
	}

	public String chartUrl()
	{
		return "http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + getTicker();
	}

	public static boolean isOption( String ticker )
	{
		return ticker.indexOf("|") > -1;
	}
	
	public static String crushDate( Calendar end ) {
		return "" + end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH)+1) + "-" + end.get(Calendar.DAY_OF_MONTH);
	}

	public double[] nVol = null;
	public double[] nClose = null;
	protected double[] sdLong = null;
	protected double[] sdMedium = null;
	protected double[] sdShort = null;
	public double[] nsdLong = null;
	public double[] nsdMedium = null;
	public double[] nsdShort = null;
	protected double[] dnsdLong = null;
	protected double[] dnsdMedium = null;
	public double[] dnsdShort = null;
	protected double[] sdsd = null;
	public double[] nsdsd = null;
//	private double[] dnClose = null;
	public double[] crushSeries = null;
	public double[] crushGradient = null;
	protected double[] xvals = null;
	protected double[] yvals = null;
	public double[] plate = null;
	protected double crushShortTotal = 0.0d;
	protected double crushMediumTotal = 0.0d;
	protected double crushLongTotal = 0.0d;

	/** Calculate CrushMap index */
	public boolean doCrush( Calendar start, Calendar end )
	{
//		Log.infoln( ticker + " Asset.doCrush");
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
				Log.debugln( "Asset.doCrush null prices=" + ticker );
				return false;
			}	

//			Log.infoln( ticker + " Asset.loadedPrices ");			
//			Log.infoln( "Asset.doCrush out=" + croutFile.getAbsolutePath() );

			getDateSeries();							// lazy loader
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
//					sdsd[i] = Statistics.sdKnuth( new double[]{ sdShort[i], sdMedium[i], sdLong[i] } ); 
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
				String datest;
//				DateFormat format = getDateFormat();				
				if ( clazz.equals("Future") || clazz.equals("FutureOption") )
				{
//					datest = "" + ((String[])prices.get( i ))[1];
					datest = "" + ((String[])prices.get( i ))[0];
				}
				else
				{
					datest = "" + ((String[])prices.get( prices.size()-i-1 ))[0];
				}
//				Date date = (Date)format.parse( datest ); 
				  
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
			    	
					// Fuzzy2
//			    	crush = fuzzy.get( "crush1" ) * nVol[i];
			    	
//			    	/* Fuzzy8
			    	crush = (fuzzy.get( "crush1" ) +
			    			fuzzy.get( "crush2" ) +
			    			fuzzy.get( "crush3" ) +
			    			fuzzy.get( "crush4" ) +
			    			fuzzy.get( "crush5" ) +
			    			fuzzy.get( "crush6" ) +
			    			fuzzy.get( "crush7" ) +  
			    			nVol[i]) / 8;
//			    	*/
//			    	crush = Math.max( crush-0.5, 0.0d ) * 2;		// Fuzzy8 Iceberg
			    			    	
			    	/* Fuzzy7
			    	crush = (fuzzy.get( "crush1" ) +
			    			fuzzy.get( "crush2" ) +
			    			fuzzy.get( "crush3" ) +
			    			fuzzy.get( "crush4" ) +
			    			fuzzy.get( "crush5" ) +
			    			fuzzy.get( "crush6" )) * (nVol[i]/6);
//			    	*/

			    	if ( i == 10598 )		// DEBUG
			    	{
			    		if ( isDisplayFuzzySets ) { fuzzy.show( "crush1" ); }
			    		String why = fuzzy.why( "crush1" );
//			    		outln( "[nsd30=" + nsd30[i] );
						int bob = 0;
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
//						double y1 = 1.0d;
//						double y2 = 1.0d;
//						int x1 = 1;
//						int x2 = 1;
						
//						/*
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
//						*/
//						int len = periodShort;
						xvals = new double[len];
						yvals = new double[len];

						for ( int j=0; j<len; j++)
						{
							xvals[j] = (double)j;
							double yval = crushSeries[i-len+j];
							yvals[j] = yval;
						}
						
						// Calculate Pear
						if( len > 1 )
						{
							LinearRegression linreg = new LinearRegression(xvals, yvals);
//							crushGradient[i] = Math.max(0, len * linreg.getSlope());		// jmh 2014-08-21 Scale by crush
							crushGradient[i] = Math.max(0, len * linreg.getSlope() * crushSeries[i]);

							//							crushGradient[i] = Math.max(0, linreg.getIntercept());
//							System.out.println("len=" + len + " slope=" + linreg.getSlope() + " grad=" + crushGradient[i]);
						}
						else
						{
							crushGradient[i] = 0.d;
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
			
//			Log.infoln( ticker + " return true ");
			
			return true;
		}
		catch( Exception e ) {
			e.printStackTrace();
		}

		return false;
	}

	private static File crushFolder()
	{
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\crush" );
	}
	
	public Collection loadPrices( Calendar start, Calendar end ) throws Exception
	{	
		prices = (ArrayList) History.loadStockPrices( this.ticker, start, end );
		return prices;
	}

	public String[] dateSeries = null;
	public String[] getDateSeries()  throws Exception {
		if ( dateSeries == null ) {
			dateSeries = parseStringsFromPrices( 0 );
		}
		return dateSeries;
	}
	public void setDateSeries(String[] dateSeries) {
		this.dateSeries = dateSeries;
	}
	
	private double[] highSeries = null;
	public double[] getHighSeries() throws Exception 
	{
		if ( highSeries == null )
		{
			highSeries = parsePrices( 2 );
		}
		return highSeries;
	}

	private double[] lowSeries = null;
	public double[] getLowSeries() throws Exception 
	{
		if ( lowSeries == null )
		{
			lowSeries = parsePrices( 3 );
		}
		return lowSeries;
	}

	protected double[] closeSeries = null;
	public double[] getCloseSeries() throws Exception 
	{
		if ( closeSeries == null )
		{
//			closeSeries = parsePrices( 5 );		// jmh 2017-06-27 Yahoo
			closeSeries = parsePrices( 4 );		// jmh 2017-06-05 Google, EOD
		}
		return closeSeries;
	}
	
	public double[] volumeSeries = null;
	public double[] getVolumeSeries() throws Exception 
	{
		if ( volumeSeries == null )
		{
			volumeSeries = parsePrices( 6 );	// jmh 2017-06-27 Yahoo
//			volumeSeries = parsePrices( 5 );	// jmh 2017-06-27 Google
		}
		return volumeSeries;
	}
	
	protected double[] parsePrices( int idx ) throws Exception 
	{
		/*	Date,Open,High,Low,Close,Volume,Adj Close */		
		/*	Date,Open,High,Low,Close,Volume */		
		/* 	Date,Open,High,Low,Close,Adj Close,Volume */	// jmh 2017-06-05
		double[] vals = new double[ prices.size() ];
		for( int i=0; i<prices.size(); i++)
		{
			String[] arr = (String[])prices.get( i );
			if( arr.length < 2 ) { continue; }
//			String[] lis = ((String[])prices.get( i ));
//			System.out.println( "lis.length=" + lis.length + " idx=" + idx );
//			outln( "" + i + "," + idx + " val=" + ((String[])prices.get(i))[idx] );
			if( arr.length < (idx-+1) ) { vals[i] = 0D; }	// jmh 2018-03-22
			else { vals[i] = Double.parseDouble( arr[idx] ); }
		}

//		String clazz = this.getClass().getSimpleName();				
//		Log.infoln( "class=" + clazz );
		/*			// jmh 2017-06-05 Don't reverse Yahoo
		if( !clazz.equals("Future") ) {
			reverse( vals );		// jmh 2017-06-27 Reverse Google
		}
		*/
		return vals;
	}

	protected String[] parseStringsFromPrices( int idx ) throws Exception {

		String[] vals = new String[ prices.size() ];
		for( int i=0; i<prices.size(); i++)
		{
			String[] arr = (String[])prices.get( i );
			if( arr.length < 2 ) { continue; }
			if( arr.length < (idx-+1) ) { vals[i] = ""; }
			else { vals[i] = arr[idx]; }
		}
		return vals;
	}

	protected static double[] reverse( double[] array) 
	{
	   int left  = 0;
	   int right = array.length-1;
	  
	   while (left < right) 
	   {
		  double temp = array[left]; 
	      array[left]  = array[right]; 
	      array[right] = temp;
	      left++;
	      right--;
	   }
	   return array;
	}

	private double[] sdShortSeries = null;
	public double[] getSdShortSeries() throws Exception 
	{
		if ( sdShortSeries == null )
		{
			sdShortSeries = runningSDSeries( getCloseSeries(), this.periodShort );
		}
		return sdShortSeries;
	}

	private double[] sdMediumSeries = null;
	public double[] getSdMediumSeries() throws Exception 
	{
		if ( sdMediumSeries == null )
		{
			sdMediumSeries = runningSDSeries( getCloseSeries(), this.periodMedium );
		}
		return sdMediumSeries;
	}

	private double[] sdLongSeries = null;
	public double[] getSdLongSeries() throws Exception 
	{
		if ( sdLongSeries == null )
		{
			sdLongSeries = runningSDSeries( getCloseSeries(), this.periodLong );
		}
		return sdLongSeries;
	}

	/*
	private double[] sdLowSeries = null;
	public double[] getSdLowSeries()
	{
		if ( sdLowSeries == null )
		{
			sdLowSeries = Statistics.runningSd( getLowSeries(), this.periodShort );
		}
		return sdLowSeries;
	}
	*/
	
	private double[] normHighSeries = null;
	public double[] getNormHighSeries() throws Exception 
	{
		if ( normHighSeries == null )
		{
//			double ratio = getMaxSdHigh() / getMaxHigh();
			double ratio = 1 / getMaxHigh();
			normHighSeries = scale( getHighSeries(), ratio );
		}
		return normHighSeries;
	}

	/*
	private double[] normVolumeSeries = null;
	public double[] getNormVolumeSeries()
	{
		if ( normVolumeSeries == null )
		{	
			normVolumeSeries = normSeries( getVolumeSeries() );
		}
		return normVolumeSeries;
	}
	*/
	
	private double max( double[] series )
	{
		double max = 0;
		for( int i=0; i<series.length; i++)
		{
			max = Math.max( max, series[i] );
		}		
		return max;
	}

	private double[] scale( double[] series, double scale )
	{
		double[] newSeries = new double[series.length];
		for( int i=0; i<series.length; i++)
		{
			newSeries[i] = series[i] * scale;
		}		
		return newSeries;
	}
	
	private double maxSdHigh = -1.0d;
	public double getMaxSdHigh() throws Exception 
	{
		if ( maxSdHigh == -1.0d )
		{
			maxSdHigh = max( getSdShortSeries() );			
		}
		return maxSdHigh;
	}

	private double maxHigh = -1.0d;
	public double getMaxHigh() throws Exception
	{
		if ( maxHigh == -1.0d )
		{
			maxHigh = max( getHighSeries() );			
		}
		return maxHigh;
	}

	private double maxVolume= -1.0d;
	public double getMaxVolume() throws Exception
	{
		if ( maxVolume == -1.0d )
		{
			maxVolume = max( getVolumeSeries() );			
		}
		return maxVolume;
	}

	/*
	private double sdHighVal = -1.0d;
	public double getSdHighVal()
	{
		if ( sdHighVal == -1.0d )
		{
			// Looking for sd < 1
			double[] series = getSdShortSeries();
			int max = 0;
			for ( int i=0; i<series.length; i++)
			{
				if ( series[i] < 1.0d )
				{
					max++;
				}
			}
			sdHighVal = max;
		}
		return sdHighVal;
	}
	*/
	
	/*
	private double[] secondSdHighSeries = null;
	public double[] getSecondSdHighSeries() 
	{
		if ( secondSdHighSeries == null )
		{
			secondSdHighSeries = Statistics.runningSd( getSdShortSeries(), this.periodShort );			
		}
		return secondSdHighSeries;
	}
	*/

	private double[] normSdShortSeries = null;
	public double[] getNormSdShortSeries() throws Exception
	{
		if ( normSdShortSeries == null )
		{	
			double ratio = 1 / getMaxSdHigh();
			normSdShortSeries = scale( getSdShortSeries(), ratio );	
		}
		return normSdShortSeries;
	}

	public double[] normSeries( double[] series ) 
	{
		double upper = max( series );
		if ( upper > 0.d )
		{ 
			double ratio = 1 / upper;
			series = scale( series, ratio );	
		}
		return series;
	}
	
	private double meanHigh = -1.0d;
	public double getMeanHigh() throws Exception
	{
		if ( meanHigh < 0 )
		{
			double[] series = getHighSeries();
			double total = 0.d;
			for ( int i=0; i<series.length; i++)
			{
				total += series[i];
			}
			meanHigh = total / series.length;
		}
		return meanHigh;
	}
	
	public double runningMean( double[] series, int day, int period ) 
	{
		double count = 0.0d;
		for( int i = day-period; i < day; i++ )
		{
			if ( i < 0 ) { break; }
			count += series[i]; 
		}
		return count / (double) period;	
	}
	
	private double[] meanNSDShortSeries = null;
	public double[] getMeanNSDShortSeries() throws Exception
	{
		if ( meanNSDShortSeries == null )
		{
			meanNSDShortSeries = meanNormSeries( getSdShortSeries(), periodShort );
		}
		return meanNSDShortSeries;
	}

	public double[] meanNormSeries( double[] series, int period )
	{
		return meanSeries( normSeries( series ), period );
	}

	public double[] meanSeries( double[] series, int period )
	{
		double[] meanSeries = new double[series.length];
		for( int i=0; i<series.length; i++)
		{
			if ( i >= 2*period ) { meanSeries[i] = runningMean( series, i, period ); } 
			else { series[i] = 0.0d; }
		}		
		return meanSeries;
	}

	private double[] meanNSDMediumSeries = null;
	public double[] getMeanNSDMediumSeries() throws Exception
	{
		if ( meanNSDMediumSeries == null )
		{
			meanNSDMediumSeries = meanNormSeries( getSdMediumSeries(), periodMedium );
		}
		return meanNSDMediumSeries;
	}

	private double[] meanNSDLongSeries = null;
	public double[] getMeanNSDLongSeries() throws Exception
	{
		if ( meanNSDLongSeries == null )
		{
			meanNSDLongSeries = meanNormSeries( getSdLongSeries(), periodLong );
		}
		return meanNSDLongSeries;
	}
	
	public double[] derivativeSeries( double[] series )
	{
		double[] derivativeSeries = new double[series.length];
		for( int i=1; i<series.length; i++)
		{
			derivativeSeries[i] = series[i] - series[i-1];
		}		
		return derivativeSeries;
	}

	public static double[] runningSDSeries( double[] series, int period )
	{
		double[] running = new double[ series.length ]; 
//		for ( int i = 0; i < period; i++ ) { running[i] = 0.0d; }
		for ( int i = period; i < series.length; i++ ) 
		{
			double[] sample = new double[ period+1 ];
			for ( int j = 0; j < period+1; j++ ) { sample[j] = series[i+j-period]; }
			running[i] = Statistics.sdKnuth( sample );
//			outln( running[i] ); 
		}
		return running;
	}
	
	public double[] getPlate( int period )
	{
		double[] running = new double[ crushSeries.length ]; 
		for ( int i = period; i < crushSeries.length; i++ ) 
		{
			if( i < periodLong ) { running[i] = 0.0d; } 
			else
			{
				double[] sample = new double[ period+1 ];
				for ( int j = 0; j < period+1; j++ ) { sample[j] = crushSeries[i+j-period]; }
//				running[i] = (crushShortTotal/periodShort) - Statistics.sdKnuth( sample );
//				running[i] = crushSeries[i] - Statistics.sdKnuth( sample );				
				/*
				double mean = 0.0d;
				for ( int j = 0; j < period+1; j++ ) { mean = crushSeries[i+j-period]; }
				mean = mean / period;
				running[i] = Math.pow(mean, 0.5) * (1- Statistics.sdKnuth( sample ));				
				*/
//				running[i] = (0.5 * Math.pow(crushSeries[i],0.5)) + (0.5 * Math.pow((1- Statistics.sdKnuth( sample )), 5));
//				running[i] = Math.pow(1 - Statistics.sdKnuth( sample ), 5);
				running[i] = Math.pow(crushGradient[i],0.3) * Math.pow(1 - Statistics.sdKnuth( sample ),5);
			}
		}
		return running;
	}
	
	public void out( String st ) throws Exception 
	{
		if ( debug ) { System.out.print( "debug=" + debug + " " + Util.replaceSubstring( st, ",", "\t" ) ); }
		getCrout().print( st );
	}
	public void outln( String st ) throws Exception 
	{
		if ( debug ) { System.out.println( "debug=" + debug + " " + Util.replaceSubstring( st, ",", "\t" ) ); }
		getCrout().println( st );
	}
	
	private PrintStream crout = null;
	private PrintStream getCrout() throws Exception {
		if ( crout == null ) { crout = Util.getPrintStream( croutFile.getAbsolutePath() ); }
		return crout;
	}

	public File getCroutFile( Calendar end ) {
		return new File( croutFolder( end ) + "\\" + ticker.replace('|', '_').replace(".","_") + " CrushMap.csv" );
	}

	public static File croutFolder( Calendar end ) {
		File folder = new File( crushFolder().getAbsolutePath() + "\\out\\" + crushDate( end ) );
		folder.mkdirs();
		return folder;
	}

	protected File croutFile = null;
	public File getCroutFile() {
		return croutFile;
	}

	public int compareTo( Object obj2 )
	{
		Asset asset2 = (Asset) obj2;
		if ( ticker.equals( asset2.ticker ) ) { return 0; }			
		else if ( crushes[0] > asset2.crushes[0] ) { return -1; }			
		else if ( crushes[0] < asset2.crushes[0] ) { return 1; }			
		else if ( crushes[1] > asset2.crushes[1] ) { return -1; }			
		else if ( crushes[1] < asset2.crushes[1] ) { return 1; }			
		else if ( crushes[2] > asset2.crushes[2] ) { return -1; }			
		else if ( crushes[2] < asset2.crushes[2] ) { return 1; }			
		return 1; 
	}
	
	private Fuzzy fuzzyCrush = null;
	public Fuzzy getFuzzyCrush() 
	{
		if ( fuzzyCrush == null )
		{
	    	File file = new File( ServerContext.getApplicationFolder() + "\\fcl\\crush.fcl" );
	    	fuzzyCrush = new Fuzzy( file );
	    	if ( isDisplayFuzzySets ) 
	    	{ 
	    		fuzzyCrush.chart(); 
	    		isDisplayFuzzySets = false;
	    	}
		}
		return fuzzyCrush;
	}

	public String getCherryColor() 
	{
		return getCherryColor(cherryWidth);
	}
	
	public static String getCherryColor(double cherryWidth) 
	{
		if ( cherryWidth < 0.01 ) { return "ff0000"; }
		else if ( cherryWidth < 0.02 ) { return "ff3300"; }
		else if ( cherryWidth < 0.03 ) { return "ff6600"; }
		else if ( cherryWidth < 0.04 ) { return "ff9900"; }
		else if ( cherryWidth < 0.05 ) { return "ffcc00"; }
		else if ( cherryWidth < 0.06 ) { return "ffff00"; }
		else if ( cherryWidth < 0.07 ) { return "ffff33"; }
		else if ( cherryWidth < 0.08 ) { return "ffff66"; }
		else if ( cherryWidth < 0.09 ) { return "ffff99"; }
		else if ( cherryWidth < 0.10 ) { return "ffffcc"; }
		else if ( cherryWidth < 0.11 ) { return "ffffff"; }
		else if ( cherryWidth < 0.12 ) { return "ccffff"; }
		else if ( cherryWidth < 0.13 ) { return "99ffff"; }
		else if ( cherryWidth < 0.14 ) { return "66ffff"; }
		else if ( cherryWidth < 0.15 ) { return "33ffff"; }
		else if ( cherryWidth < 0.16 ) { return "00ffff"; }
		else if ( cherryWidth < 0.17 ) { return "00ccff"; }
		else if ( cherryWidth < 0.18 ) { return "0099ff"; }
		else if ( cherryWidth < 0.19 ) { return "0066ff"; }
		else if ( cherryWidth < 0.20 ) { return "0033ff"; }
		return "0000ff";
	}

	public static String getPorterColor(double porter)
	{
		if ( porter > 0.42 ) { return "ff0000"; }
		else if ( porter > 0.41 ) { return "ff2000"; }
		else if ( porter > 0.40 ) { return "ff4000"; }
		else if ( porter > 0.39 ) { return "ff6000"; }
		else if ( porter > 0.38 ) { return "ff8000"; }
		else if ( porter > 0.36 ) { return "ffa000"; }
		else if ( porter > 0.34 ) { return "ffc000"; }
		else if ( porter > 0.32 ) { return "ffe000"; }
		else if ( porter > 0.30 ) { return "fff000"; }
		else if ( porter > 0.28 ) { return "ffff00"; }
		else if ( porter > 0.26 ) { return "ffff20"; }
		else if ( porter > 0.24 ) { return "ffff40"; }
		else if ( porter > 0.22 ) { return "ffff60"; }
		else if ( porter > 0.20 ) { return "ffff80"; }
		else if ( porter > 0.18 ) { return "ffffa0"; }
		else if ( porter > 0.16 ) { return "ffffc0"; }
		else if ( porter > 0.14 ) { return "ffffe0"; }
		else if ( porter > 0.12 ) { return "fffff0"; }
		else if ( porter > 0.10 ) { return "00ffff"; }
		else if ( porter > 0.08 ) { return "00ccff"; }
		else if ( porter > 0.06 ) { return "0099ff"; }
		else if ( porter > 0.04 ) { return "0066ff"; }
		else if ( porter > 0.02 ) { return "0033ff"; }
		return "0000ff";
	}

	public static String getCrushColor(double crush) {

		if ( crush > 0.94 ) { return "ff0000"; }
		else if ( crush > 0.92 ) { return "ff2000"; }
		else if ( crush > 0.90 ) { return "ff4000"; }
		else if ( crush > 0.88 ) { return "ff6000"; }
		else if ( crush > 0.86 ) { return "ff8000"; }
		else if ( crush > 0.82 ) { return "ffa000"; }
		else if ( crush > 0.78 ) { return "ffc000"; }
		else if ( crush > 0.74 ) { return "ffe000"; }
		else if ( crush > 0.70 ) { return "fff000"; }
		else if ( crush > 0.66 ) { return "ffff00"; }
		else if ( crush > 0.62 ) { return "ffff20"; }
		else if ( crush > 0.58 ) { return "ffff40"; }
		else if ( crush > 0.54 ) { return "ffff60"; }
		else if ( crush > 0.50 ) { return "ffff80"; }
		else if ( crush > 0.46 ) { return "ffffa0"; }
		else if ( crush > 0.42 ) { return "ffffc0"; }
		else if ( crush > 0.38 ) { return "ffffe0"; }
		else if ( crush > 0.34 ) { return "fffff0"; }
		else if ( crush > 0.30 ) { return "00ffff"; }
		else if ( crush > 0.26 ) { return "00ccff"; }
		else if ( crush > 0.22 ) { return "0099ff"; }
		else if ( crush > 0.18 ) { return "0066ff"; }
		else if ( crush > 0.14 ) { return "0033ff"; }
		return "0000ff";
	}

	protected String ticker = null;
	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public static DateFormat defaultDateFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	public DateFormat getDateFormat()
	{
//		return simpleDateFormat; 
		if ( clazz.equals("Future") )
		{
			return new SimpleDateFormat( "MM/dd/yyyy" );
		}
		else if ( clazz.equals("FutureOption") )
		{
			return new SimpleDateFormat( "MM/dd/yyyy" );					
		}
		else
		{
			return defaultDateFormat;
		}
	}

	public static DateFormat detailedDateFormat = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );

	/*
	public static Asset instance( CrushRecord record )
	{		
		Asset asset = Asset.instance( record.getTicker());
				
//		asset.setDatabaseManager(dbm);
		record.setTicker( ticker );
		record.setStartDate( fmdStart );
		record.setEndDate( fmdEnd );
		record.setClose( asset.nClose[asset.prices.size()-1] );
		record.setFlow( asset.nVol[asset.prices.size()-1] );
		record.setPeel( asset.nsdLong[asset.prices.size()-1] );
		record.setJam( asset.nsdMedium[asset.prices.size()-1] );
		record.setPress( asset.nsdShort[asset.prices.size()-1] );
		record.setCrush( asset.crushSeries[asset.prices.size()-1] );				
		record.setPlate( asset.plate[asset.prices.size()-1] );
		record.setPear( asset.crushGradient[asset.crushGradient.length-1] );
		record.setCherry( asset.cherryWidth );
		
		record.setCloseSeries( AssetRepresentation.getSeriesValues( asset.nClose ) );
		record.setFlowSeries( AssetRepresentation.getSeriesValues( asset.nVol ) );
		record.setPlateSeries( AssetRepresentation.getSeriesValues( asset.plate ) );
		record.setPearSeries( AssetRepresentation.getSeriesValues( asset.crushGradient ) );
		record.setCrushSeries( AssetRepresentation.getSeriesValues( asset.crushSeries ) );
		record.setVolumeSeries( AssetRepresentation.getVolSeriesValues( asset.volumeSeries ) );
		
		return asset;
	}
	*/

}
