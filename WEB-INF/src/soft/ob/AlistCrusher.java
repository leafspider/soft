package soft.ob;

import java.io.File;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.TreeSet;

import soft.asset.Asset;

import leafspider.util.Log;
import leafspider.util.Util;

public class AlistCrusher
{	
	protected String name = null;
	
	public static void main ( String[] args )
	{
		try
		{					
			String[] list1 = { "path " };// bcsi" };//"NASDAQ:CSCO" };// "WTC.V" };//"hmg.v" };//"NCU.TO" };//"LIM.TO" }; CSCO
			String[] list2 = { "CEL", "BMO", "PHI", "QLTY", "TEN.V" };//,"ewz","ewi","ewq","gcu.v","lps","chmto","lfd.v","acg.v" };
			String[] list3 = { "ABAT","ACHN","ACLS","ACOM","ACPW","ACTG","ACXM","ADTN","AEZS","AFFX","AGEN","AINV","AKRX","ALKS","ALSK","ALXA","AMCC","AMKR","AMLN","AMRN" };
			String[] list4 = { "GLL", "GLD", "UGL", "DZZ", "CYTK", "SNTA", "SCMP", "BONE", "DVAX", "WDO.TO", "WDG.V", "ZIOP", "LGND" };
			String[] list5 = { "GLD", "SLV", "GDX", "GDXJ", "NGD" };
			String[] list50 = { "KERX", "LGND", "BAC", "HPQ", "CSCO", "F", "GE", "INTC", "MSFT", "PFE", "C", "WFC", "NWSA", "JPM", "NOK", "MU", "S", "DELL", "ORCL", "T", "AMD", "MS", "XOM", "GM", "RIMM", "NLY", "RF", "MRVL", "MGM", "ALU", "AA", "EK", "AAPL", "CLWR", "HK", "EMC", "YHOO", "HAL", "SCHW", "QCOM", "VZ", "WMT", "GLW", "MMI", "MRK", "FCX", "CMCSA", "ITUB" };
			String[] list100 = { "BDSI", "KERX", "LGND", "BAC", "HPQ", "CSCO", "F", "GE", "INTC", "MSFT", "PFE", "C", "WFC", "NWSA", "JPM", "NOK", "MU", "S", "DELL", "ORCL", "T", "AMD", "MS", "XOM", "GM", "RIMM", "NLY", "RF", "MRVL", "MGM", "ALU", "AA", "EK", "AAPL", "CLWR", "HK", "EMC", "YHOO", "HAL", "SCHW", "QCOM", "VZ", "WMT", "GLW", "MMI", "MRK", "FCX", "CMCSA", "ITUB", "ADSK", "JNJ", "BK", "PBR", "NVDA", "DIS", "HBAN", "LVS", "BRCD", "MO", "FITB", "USB", "BSX", "XRX", "LOW", "WFT", "EBAY", "BMY", "PG", "AMAT", "KEY", "KFT", "HD", "GPS", "KO", "STX", "FTR", "TXN", "BIDU", "NTAP", "IBM", "M", "STI", "CAT", "COP", "CVX", "WMB", "DOW", "SLB", "CSX", "DAL", "VOD", "PEP", "AXP", "SBUX", "VLO", "LUV", "LDK", "MET", "SYMC", "EP", "ABX", "TSM", "GGB", "PM", "BHI", "DUK", "CIM", "MCD", "SPLS", "MYL", "TWX", "PHM", "GILD", "HON", "SD", "HST", "JDSU", "CHK", "GNW", "AUY", "JCP", "INTU", "IPG", "BP", "GEN", "CVS", "ERTS", "BBD", "POT", "DRYS", "GT", "SLW", "MRO", "ATVI", "CRM", "DD", "AMGN", "GG", "SU", "ABT", "ACN", "ANN", "BA", "BRK.B", "CBS", "IP", "PNC", "AMR", "NEM", "PRU", "UNH", "CCL", "KGC", "UTX", "UAL", "PBR.A", "ARO", "ATML", "SINA", "TGT", "SWN", "JNPR", "BRCM", "WU", "MDT", "WAG", "SGEN", "FL", "SNDK", "VIA.B", "AES", "ESRX", "LNC", "X", "ADI", "NWS", "GME", "PPL", "LSI", "AMX", "XL", "PCS", "HL", "CTSH", "NVE", "YGE", "AKS", "CX", "V", "NSM", "CBG", "ITW", "UBS", "TEVA", "DTV", "ADM", "LCC", "PGR", "HIG", "LLY", "THC", "DE", "IVZ" };
			String[] listRangeAdvances = { "LTBR","DVR","VGZ","NTSP","ONCY","BRC","MXL","APKT","PGNX","EZCH","USAT","ACUR","MXWL","HA","AMCC","GIVN","CPWM","MNKD","TRNX","INFN","FRP","ENTR","INTG","CCRN","FSTR","VPG","EXM","ROCK","GNK","ACAT","JVA","MHO","GDOT","PRO","LCRY","DAKT","ASIA","CHDX","FXCM","FNSR","GCOM","CIGX","NEOP","ARQL","HNSN","MIPS","DIN","OPK","LDL","UWN","IDIX","KEM","VVTV","ZOLT","SKX","STSA","PLX","CCSC","LL","WG","MHGC","SWSH","EK","HHS","OMN","MTG","ECHO","DQ","SCSS","HMPR","MBI","DCTH","WBS","TTMI","MRVL","MTRX","ADSK","CBRL","INAP","ENTG","MSPD","PMCS","CASY","IMAX","SEAC","DNDN","MU","CHSI","KLIC","ALTR","TWER","VECO","LCI","STBZ","FFIV","OPTR","RUTH","BRCD","GFF","JDSU" };
			String[] listGapUps = { "ULTA","HURC","SPEX","CGA","LOV","HBNK" };
			String[] listGapDowns = { "SQM","WBK","ESLT","NVO","DEG","DB","CGV","AG","TD","ZNH","SSRI","IBN","FORTY","CFX","NICE","HHC","HDB","NIHD","MKTAY","BUD","CM","SNY","AGP","VRX","RY","SHG","WPPGY","TEVA","FTE","CEA","TDG","EOC","VHI","NVS","KB","BSI","CCJ","HBC","LFC","GG","ING","BAK","TLK","UN","VE","UL","SPNS","ALV","TI","BMO","CCU","CRH","STRC","ACH","PWRD","DAR","VOD","PTNR","AZN","PHG","MWE","TCK","DHRM","CEL","AWC","MITK","CCI","IVN","CCIX","ALGN","SHPGY","AV","PAA","CTCM","YZC","VAR","THC","PT","SPPI","ITRN","AOS","PLX","HSP","RCMT","NECB","ATPG","PHI","THER","RUK","CCK","DEO","USM","SLT","QLTY","ABC","ENL","SNA","TEF","BT","TCAP" };
			String[] listETFs = { "VXX","TZA","XRT","FAZ","TNA","SMH","QID","TLT","SSO","XOP","IWM","SPXU","ZSL","XME","USO","SPY","OIH","SDS","FAS","XLB","XLI","XLF","UCO","DZZ","IYR","XLE","SLV","XLY","DXD","EDC","QQQ","KRE","SRS","EWW","XLV","UYG","RWM","UUP","DIA","XHB","GLL","FXI","DGP","GDX","IYM","EWZ","XIV","SH","KBE","UNG","EEM","TMV","EUO","TBT","XLP","RSX","EPI","EWT","EWH","EWI","MDY","EWY","IWO","EPU","GLD","XLU","IBB","XLK","SIVR","GDXJ","ILF","EWA","SIL","EWJ","OIL","EWM","FXC","YCS","IEF","KOL","EWG","IWN","EFA","VGK","EWU","EWD","EWQ","ITB","EZU","XES","FRI","DBA","PIN","SGOL","ECH","TBF","PSLV","FBT","TAN","DBO" };
			String[] listAll = { "mur","hrbn","sprd","olv.v","clgx","MCOX","VIFL","CEDC","REGN","QTWW","CVV","DYN","GSS","ADLR","IPHI","ASIA","PNSN","DCTH","WBC","JAG","SSRI","SODA","ARRY","MSSR","COGO","REX","BONT","EK","NG","CT","CHOP","HNSN","LSTZA","PHX","OPTR","SQNS","MITK","HFWA","DROOY","TRGP","MAKO","GHM","HOLI","KIRK","EGHT","TAM","ZSTN","GKK","MYRX","FLOW","CLSN","KID","CCSC","CYTX","ESSX","SVA","USNA","COMV","HRBN","AUY","BMTI","STMP","MY","GEF","OCLR","VSI","LAVA","SGMO","PANL","MDVN","ONTY","ECTE","MOTR","ORCC","XG","XRA","SVU","CIE","MELA","SGI","NKA","MCP","MBI","GBG","FLDM","CJES","ANV","PIP","MIC","LODE","GNC","POT","BAH","BPAX","HEES","TNH","AVD","QDEL","MPG","GPL","JVA","GLBL","SWSH","ROYL","NHC","XPO","GOLD","RGLD","FNSR","GG","ABX","GKK","GPL","NEM","AZK","MFN","AUY","EXK","IAG","SLW","NFLX","ESL","CEO","WPO","GOOG","PTR","AAPL","GS","WYNN","CMI","MA","BIDU","MTD","APA","SHG","ATI","JLL","SSL","CAT","BTU","SINA","SOHU","CVX","FDX","VMW","BHP","MSTR","ROK","OXY","IX","PII","DRQ","VFC","TIF","CNS","SM","COH","FSLR","PH","CE","APC","WBK","WAT","RKT","COF","IBM","GWR","ROC","CYT","NFX","PXD","DB","EOG","ROP","UTX","PRU","BLK","SLB","LLL","OKE","ASMI","OIS","HUB.B","SPW","JPM","EQT","BRY","XEC","UNP","MTX","EMN","BBL","CAM","WFM","C","GDI","LYB","MMM","APH","CRR","MUR","FMC","PPG","CRM","AMZN","SNP","AZN","PKX","RIG","X","BCR","PVH","WLK","CLF","UNT","GLF","TYC","BP","TOT","EGN","VXX","TZA","XRT","FAZ","TNA","SMH","QID","TLT","SSO","XOP","IWM","SPXU","ZSL","XME","USO","SPY","OIH","SDS","FAS","XLB","XLI","XLF","UCO","DZZ","IYR","XLE","SLV","XLY","DXD","EDC","QQQ","KRE","SRS","EWW","XLV","UYG","RWM","UUP","DIA","XHB","GLL","FXI","DGP","GDX","IYM","EWZ","XIV","SH","KBE","UNG","EEM","TMV","EUO","TBT","XLP","RSX","EPI","EWT","EWH","EWI","MDY","EWY","IWO","EPU","GLD","XLU","IBB","XLK","SIVR","GDXJ","ILF","EWA","SIL","EWJ","OIL","EWM","FXC","YCS","IEF","KOL","EWG","IWN","EFA","VGK","EWU","EWD","EWQ","ITB","EZU","XES","FRI","DBA","PIN","SGOL","ECH","TBF","PSLV","FBT","TAN","DBO" };
	
			Calendar end = new GregorianCalendar();			
	//		end.set( 2010, 3, 29 );
			Calendar start = new GregorianCalendar();
			start.set( end.get(Calendar.YEAR)-1, end.get(Calendar.MONTH), end.get(Calendar.DAY_OF_MONTH) );			
			
			AlistCrusher list = new AlistCrusher( "gapup" );
			list.crush( list2, start, end );			// CrushMap analysis on ticker list
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public AlistCrusher( String name )
	{
		this.name = name;
	}
	
	public File crush( String[] tickerList, Calendar start, Calendar end ) throws Exception
	{
		if ( tickerList.length >  1 )
		{
			Asset.debug = false;
		}
	
	//	PrintStream stream = Util.getPrintStream( Asset.crushFolder().getAbsolutePath() + "\\CrushMap " + crushDate( end ) + ".htm" );
		File mapFile = croutMenu( end );
		PrintStream stream = Util.getPrintStream( mapFile.getAbsolutePath() );
		
		stream.println( "<html><body>" );
		stream.println( "<table style='font-family:arial;border-spacing:20px 1px;'>" );
		stream.println( "<tr>" );
		stream.println( "	<td colspan='2' align='center' style='font-size:1.2em'>" + 
				"<b>CrushMap " +
				name.toUpperCase() + " (" + 
				start.get(Calendar.YEAR) + "-" + (start.get(Calendar.MONTH)+1) + "-" + start.get(Calendar.DAY_OF_MONTH) + 
				" to " + 
				end.get(Calendar.YEAR) + "-" + (end.get(Calendar.MONTH)+1) + "-" + end.get(Calendar.DAY_OF_MONTH) + 
				")</b></td>" );
		stream.println( "</tr>" );
		
		TreeSet assets = new TreeSet();
		for( int i = 0; i < tickerList.length; i++ ) 
		{ 
			String ticker = tickerList[i];			
			Asset asset = Asset.instance( ticker );	
			try
			{
				if ( asset.doCrush( start, end ) )
				{
					assets.add( asset );
				}
			}
			catch( Exception e )
			{
	
			}
		}
		
		stream.println( "<tr>" );
	
		NumberFormat decimalFormat = new DecimalFormat( "#0.00" );
	
		double rows = 35;
		int rowCount = 0;
		Iterator iter = assets.iterator();		
		while( iter.hasNext() ) 
		{
			Asset asset = (Asset) iter.next();
			if ( asset.getTicker() != null )
			{
				if ( asset.getTicker().equals( "BAC" ) )
				{
					String bob = "bob";		// DEBUG
				}
						
	//			stream.println( "	<td align='right'>" + formatter.format( key.doubleValue() ) + "</td><td><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td>" );
				double rint = Math.IEEEremainder( rowCount, rows );
	//			System.out.println( "" + rint );
				boolean isNewCol = ( rint == 0.0 );
				boolean isEndofCol = ( rint == -1.0 );
//				Log.infoln( "asset.channelWidth=" + asset.channelWidth );
				if ( isNewCol )
				{ 
					stream.println( "	<td align='right' valign='top'>" ); 
					stream.println( "		<table style='border-spacing:5px 1px;'>" ); 
					stream.println( "			<tr style='text-align:center;'>" );
					stream.println( "				<td></td><td><i>10</i></td><td><i>20</i></td><td><i>30</i></td><td></td>" );
					stream.println( "			</tr>" );
				}
	//			stream.println( decimalFormat.format( key.doubleValue() ) + " <a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></br>" );
	//			stream.println( "<tr><td align='right' valign='top'>" + decimalFormat.format( key.doubleValue() ) + "</td><td align='left' valign='top'><a href='http://finance.yahoo.com/q/bc?t=1y&l=on&z=l&q=l&p=&a=&c=&s=" + val + "' target='_blank'>" + val + "</a></td></tr>" );
				stream.println( "<tr>" +
									"<td align='left' valign='top'>" +
										"<a style='border-radius:5px;background:" + asset.getCherryColor() + "' title='" + asset.cherryWidth + "' href='/soft/asset/jake/" + asset.getTicker() + "/crush.htm' target='_blank'><b>" + asset.getTicker() + "</b></a> " +
									"</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[0] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[1] ) + "</td>" +
									"<td align='right' valign='top'>" + decimalFormat.format( asset.crushes[2] ) + "</td>" +
									"<td align='left' valign='top'>" +
										"<nobr>" + 
										"<a href='" + asset.getCroutFile().getName() + "' target='_blank'>csv</a> " +
										"<a href='" + asset.chartUrl() + "' target='_blank'>chart</a>" +
										"</nobr>" +
									"</td>" +
								"</tr>" );
				if ( isEndofCol ) 
				{ 
					stream.println( "		</table>" ); 
					stream.println( "	</td>" ); 
				}
				rowCount++;
			}
		}
	
		stream.println( "</tr>" );		
		stream.println( "</table>" );		
		stream.println( "</body></html>" );
	
		if ( tickerList.length >  1 )
		{
			System.out.println( "Done" );
		}
		
		return mapFile;
	}

	private File croutMenu( Calendar end )
	{
		return new File( Asset.croutFolder( end ).getAbsolutePath() + "\\" + name + ".htm" );
	}
}