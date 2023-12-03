package soft.report;

import java.io.*;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import leafspider.util.*;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jdom2.CDATA;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.Document;

import soft.asset.CrushProject;
import soft.asset.CrushRecord;
import soft.asset.History;

public class WatchlistPerformance {

	private String path = Folders.performanceFolder() + "\\";

	private File watchXmlFile = new File(path + "watchlist.xml");
	private File pchangeXmlFile = new File(path + "pchange.xml");
	public File getPchangeXmlFile() { return pchangeXmlFile; }
	private File xslFile = new File(ServerContext.getSkinsFolder().getAbsolutePath() + "\\assetlist\\pchange.xsl");
	private File standaloneXslFile = new File(ServerContext.getSkinsFolder().getAbsolutePath() + "\\assetlist\\pchangestandalone.xsl");
	private File csvFile = new File(path + "pchange.csv");
	private File htmFile = new File(path + "pchange.htm");

	public static void main ( String[] args )
	{
   		try {
			WatchlistPerformance wp = new WatchlistPerformance();
			wp.setLimit(10);
			wp.getLastWatchedDate();
			wp.doCreateWatchlist();
			wp.doParseWatchlistToCsv();
   			wp.doParseCsvToPchangeXml();
			wp.doTransform( false );
   		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}

	private int offset = 0;
	public int getOffset() {
		return offset;
	}
	public void setOffset(int offset) {
		this.offset = offset;
	}

	private int limit = 500;
	public int getLimit() {
		return limit;
	}
	public void setLimit(int limit) {
		this.limit = limit;
	}

	public void doCreateWatchlist() throws ServletException, IOException, Exception {

		Log.infoln("doCreateWatchlist" );

		if ( watchXmlFile.length() > 0 && Duration.mins( watchXmlFile.lastModified(), Util.getNow() ) < 5 ) { return; }

		String filter = "watched";
		String filterValue = "true";
		String project = "jake";

		int offset = getOffset();
		int limit = getLimit();

		Log.infoln("doCreateWatchlist limit=" + getLimit() );

		Element root = new Element( "CrushRecordList" );
		root.addContent( new Element( "project" ).addContent( new CDATA( project ) ) );

		try {

			List records = null;
			if( filter != null ) { records = CrushProject.getDatabaseManager().listFilteredRecords("CrushRecord", filter, filterValue, "id desc", offset, limit); }
			else { records = CrushProject.getDatabaseManager().listRecords("CrushRecord", "id desc"); }

			root.addContent( new Element( "count" ).addContent( new CDATA( "" + records.size() ) ) );
			root.addContent( new Element( "filter" ).addContent( new CDATA( "" + filter ) ) );

			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				Element elem = crush.listElement();
				root.addContent( elem );
//				XmlJdomWriter writer = new XmlJdomWriter();
//				Log.infoln( writer.writeToString(elem.clone()) );
			}
		}
		catch( Exception e) { Log.infoln( e ); }

		watchXmlFile.getParentFile().mkdirs();

		PrintStream out = Util.getPrintStream(watchXmlFile.getAbsolutePath());
		out.print( getJdomWriter().writeToString( root ) );

		csvFile.delete();
	}

	private XmlJdomWriter jdomWriter = null;
	public XmlJdomWriter getJdomWriter()
	{
		if ( jdomWriter == null ) { jdomWriter = new XmlJdomWriter(); }
		return jdomWriter;
	}

	public String doTransform(boolean isStandalone) {

		Log.infoln("doTransform" );

   		try {

//			XslTransformer former = new XslTransformer();
//			former.doTransform(xml, xsl, html);
			
			StringWriter writer = new StringWriter();
			TransformerFactory xsltFactory = TransformerFactory.newInstance();

			File theXslFile = xslFile;
			if ( isStandalone ) {
				theXslFile = standaloneXslFile;
			}

			Transformer xslt = xsltFactory.newTransformer( new StreamSource( theXslFile ) );
			xslt.transform( new StreamSource( new StringReader( Util.fileToString(pchangeXmlFile, null ) ) ), new StreamResult( writer ) );

//			PrintStream out = Util.getPrintStream(htmFile.getAbsolutePath());
//			out.print( writer.toString() );

			return writer.toString();
   		}
		catch( Exception e ) {
			e.printStackTrace();
		}
   		return null;
	}
	
	public void doParseCsvToPchangeXml() {

		Log.infoln("doParseCsvToPchangeXml" );

		if ( pchangeXmlFile.length() > 0 && Duration.mins( csvFile.lastModified(), Util.getNow() ) < 5 ) { return; }

		try {

   			BufferedReader in = Util.getBufferedReader(csvFile);
   			PrintStream out = Util.getPrintStream(pchangeXmlFile.getAbsolutePath());

			Element root = new Element( "watchlist" );

   			int n = 0;
   			String line = in.readLine();
   			line = in.readLine();		// skip first line
   			while( line != null ) {
   				   				
   				Element snapshot = new Element( "snapshot" );

   				String[] vals = line.split(",");
   				snapshot.addContent( new Element("date").setText(vals[1]));
   				snapshot.addContent( new Element("ticker").setText(vals[0]));
   				snapshot.addContent( new Element("days").setText(vals[2]));
   				snapshot.addContent( new Element("pchange").setText(vals[3]));
   				
   				root.addContent(snapshot);
   				
   				line = in.readLine();
   			}

   			root.sortChildren( new Comparator<Element>() {

				@Override
				public int compare(Element elem1, Element elem2) {
					Double pchange1 = Double.valueOf( elem1.getChildText("pchange") );
					Double pchange2 = Double.valueOf( elem2.getChildText("pchange") );
					return pchange1.compareTo(pchange2);
				}
			});
   			
	   		XmlJdomWriter jdom = new XmlJdomWriter();
			out.print( jdom.writeToString( root ) );

			pchangeXmlFile.delete();
   		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void doParseWatchlistToCsv() {

		Log.infoln("doParseWatchlistToCsv" );

		if ( csvFile.length() > 0 && Duration.hours( watchXmlFile.lastModified(), Util.getNow() ) < 1 ) { return; }

		try {

   			SAXBuilder saxDoc = new SAXBuilder();
   			Document xml = saxDoc.build(watchXmlFile);
   			
   			Element root = xml.getRootElement();
   			Iterator crushes = root.getChildren("CrushRecord").iterator();

   			PrintStream out = Util.getPrintStream(csvFile.getAbsolutePath());

			out.print("ticker");
			out.print(",snapshot,days");
			out.print(",%change" );
			out.println("");

   			int n = 0;
   			while( crushes.hasNext() ) {
   				
   				Element crush = (Element) crushes.next();
   				String ticker = crush.getChildText("ticker");
   				String snapshotDate = crush.getChildText("endDate");
   				String crushVal = crush.getChildText("crush");
   				
   				DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );	
   				Calendar start = parseDate( snapshotDate, format );
//   				Calendar end = parseDate( snapshotDate, format );				
//   				end.add(Calendar.DAY_OF_MONTH, 63);
   				Date date = DateUtils.addDays(start.getTime(), 63);
   				Calendar end = Calendar.getInstance();
   				end.setTime(date);
   				   				   				
   		        String endDate = format.format(end.getTime());

   				n++;
				try {
	   				
   					ArrayList prices = (ArrayList) History.loadStockPrices( ticker, start, end );

	   				double[] close = parsePrices(prices, 4);
	   				
	   				if( close.length > 0 ) {
		   				int days = close.length;
		   				
		   				if( days > 100 ) {
		   					System.out.println("days=" + days);
		   				}
		   				
		   				double watchPrice = close[0];
	
		   				out.print(ticker);
		   				out.print("," + snapshotDate + "," + days);
	
		   		        List doubleClose = Arrays.asList(ArrayUtils.toObject(close));	
		   		        double min = (Double) Collections.min(doubleClose);
		   		        double max = (Double) Collections.max(doubleClose);
		   		        double dmax = max - watchPrice;
		   		        double dmin = min - watchPrice;
		   		        double pcmax = 100 * dmax / watchPrice;
		   		        double pcmin = 100 * dmin / watchPrice;	   		        
		   		        double pchange = pcmax;
		   		        if(pcmax + pcmin < 0) { pchange = pcmin; }
//		   		        DecimalFormat df = new DecimalFormat("#.00");
//		   		        out.print("," + df.format(pchange) );
						out.print("," + pchange );
		   		     	out.println("");
	   				}
					//System.out.println("n=" + n);
   				}
				catch ( Exception e ) {
					Log.infoln(e);
					e.printStackTrace();
				}

				if ( n > 380 ) {
					break;
				}
   			}

			//System.out.println("final n=" + n);
		}
		catch( Exception e ) {
			e.printStackTrace();
		}
	}
	
	public double[] parsePrices( ArrayList prices, int idx ) throws Exception {

		double[] vals = new double[ prices.size() ];
		for( int i=0; i<prices.size(); i++)
		{
			String[] arr = (String[])prices.get( i );
			if( arr.length < 3 ) { continue; }

			vals[i] = Double.parseDouble( arr[idx] );
		}
		return vals;
	}

	public Calendar parseDate( String st, DateFormat dateFormat ) throws Exception {

		Calendar ret = new GregorianCalendar();			
		try
		{
			dateFormat.parse( st );
			String[] parts = st.split("-");
			ret.set( Integer.parseInt(parts[0]), Integer.parseInt(parts[1])-1, Integer.parseInt(parts[2]), 0, 0, 0 );
		}
		catch( Exception e ) {}
		return ret;
  	}

	public String transform( String xml, String xsl ) throws Exception {

		StringWriter writer = new StringWriter();
		TransformerFactory xsltFactory = TransformerFactory.newInstance();
		Transformer xslt = xsltFactory.newTransformer( new StreamSource( xsl ) );
		xslt.transform( new StreamSource( new StringReader( xml ) ), new StreamResult( writer ) );
		return writer.toString();
	}

	public Calendar getLastWatchedDate() throws Exception {

		DateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
		Calendar end = null;
		try
		{
			List records = CrushProject.getDatabaseManager().listTopRecords("CrushRecord", 1, "id", "desc");

			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushRecord crush = (CrushRecord) list.next();
				Log.info("endDate=" + crush.getEndDate());
				end = parseDate( crush.getEndDate(), format );
			}
		}
		catch( Exception e) { Log.infoln( e ); }
		return end;
	}

}
