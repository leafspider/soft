package soft.asset;

import leafspider.db.DatabaseRecord;
import leafspider.rest.RepresentationException;
import leafspider.util.Log;
import leafspider.util.Util;
import leafspider.util.XmlJdomWriter;
import org.jdom2.CDATA;
import org.jdom2.Element;

import java.util.Iterator;
import java.util.List;

public class CrushFailure extends DatabaseRecord
{	
	public static void main ( String[] args )
	{
		Log.debug = true;

   		try
   		{
			List records = CrushProject.getDatabaseManager().listRecords("CrushFailure");
			Log.infoln( "size=" + records.size() );

			Iterator list = records.iterator();
			while(list.hasNext())
			{
				CrushFailure fail = (CrushFailure) list.next();
				Element elem = fail.listElement();
				XmlJdomWriter writer = new XmlJdomWriter();
				Log.infoln( writer.writeToString(elem) );
			}
   		}
		catch( Exception e ) { e.printStackTrace(); }
	}
	
	public String getProjectName()
	{ 
		return CrushProject.projectName;
	}

	private String ticker;
	private String startDate;
	private String endDate;
	private String message;

	public String getTicker() {
		return ticker;
	}
	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

    public Element element() throws RepresentationException
    {
    	try {

			Element root = new Element( "crushFailure" );
			root.addContent( new Element( "ticker" ).addContent( new CDATA( ticker ) ) );
			root.addContent( new Element( "endDate" ).addContent( new CDATA( endDate ) ) );
			root.addContent( new Element( "startDate" ).addContent( new CDATA( startDate ) ) );
			root.addContent( new Element( "message" ).addContent( new CDATA(message) ) );
			return root;
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }

}
