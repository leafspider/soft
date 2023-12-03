package scenario.more;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;

import leafspider.db.DatabaseRecord;
import leafspider.util.Log;
import leafspider.util.ServerContext;
import leafspider.util.Util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


public abstract class Exhibitor extends DatabaseRecord
{
	public abstract void populate() throws Exception;
	
	public String getProjectName()
	{ 
		return ExhibitorProject.projectName;
	}

	private String exhibitorUrl = null;
	public String getExhibitorUrl() {
		return exhibitorUrl;
	}
	public void setExhibitorUrl(String url) {
		this.exhibitorUrl = url;
	}
	
	public static void main ( String[] args )
	{
		Log.debug = true;
		try
		{
			Exhibitor ex = new ExhibitorIBC();
//			ex.setWebsite("http://chirpe.com/Exhibitor");
			ex.setEmail("barfchirpe.c@.om");
			ex.setWebsite("http://www.chirpe.com/");
			ex.setPhone("+44(345)-456.BUMF");
			ex.reportVals();
			ex.saveOrUpdateAndCommit();

			Log.infoln("email=" + ex.getEmail());
			Log.infoln("website=" + ex.getWebsite());
			Log.infoln("phone=" + ex.getPhone());
			
			/*
			Iterator urls = Util.getArrayListFromFile("C:\\Workspace\\Ultra\\Nick\\IBC\\Exhibitor\\ChirpeVendorLinks.txt").iterator();
			
			int n = 0;
			while(urls.hasNext())
			{
				Exhibitor ex = new Exhibitor();
//				ex.setExhibitorUrl("http://chirpe.com/ExhibitorHome.aspx?BoothID=106542&EventID=1484");
				ex.setExhibitorUrl("http://chirpe.com/" + urls.next());
				ex.populate();
				ex.report();
				n++;
//				ex.saveAndCommit();
//				if ( n > 10) { break;}
			}
			System.out.println( "" );
			System.out.println( "n=" + n );
//			*/
			
//			System.out.println( "count=" + ExhibitorProject.getDatabaseManager().countRecords("Exhibitor") );

			/*
			Iterator exhibitors = ExhibitorProject.getDatabaseManager().listRecords("Exhibitor").iterator();
			while(exhibitors.hasNext())
			{
				Exhibitor exhibitor = (Exhibitor) exhibitors.next();
				exhibitor.report();
			}
			*/
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public void reportColumns()
	{
		report("name");
		report("booth");
		report("address1");
		report("address2");
		report("addressCity");
		report("addressState");
		report("phone");
		report("email");
		report("website");
		reportLast("description");
	}	
	
	public void reportVals()
	{
		report(getName());
		report(getBooth());
		report(getAddress1());
		report(getAddress2());
		report(getAddressCity());
		report(getAddressState());
		report(getPhone());
		report(getEmail());
		report(getWebsite());
		reportLast(getDescription());
	}	
	
	private void report(String st)
	{		
		if(st == null)
		{
			System.out.print( "," );		
		}
		else
		{
			System.out.print( "\"" + st.replaceAll(",", ";") + "\"," );
		}
	}
	private void reportLast(String st)
	{
		if(st == null)
		{
			System.out.println( "" );		
		}
		else
		{
			System.out.println( "\"" + st.replaceAll(",", ";") + "\"" );
		}
	}
	
	protected String name = "";
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	protected String booth = "";
	public String getBooth() {
		return booth;
	}
	public void setBooth(String booth) {
		this.booth = booth;
	}
	
	protected String address1 = "";
	public String getAddress1() {
		return address1;
	}
	public void setAddress1(String address) {
		this.address1 = address;
	}
	
	protected String address2 = "";
	public String getAddress2() {
		return address2;
	}
	public void setAddress2(String address2) {
		this.address2 = address2;
	}
	
	protected String addressCity = "";
	public String getAddressCity() {
		return addressCity;
	}
	public void setAddressCity(String addressCity) {
		this.addressCity = addressCity;
	}
	
	protected String addressState = "";
	public String getAddressState() {
		return addressState;
	}
	public void setAddressState(String addressState) {
		this.addressState = addressState;
	}
	
	protected String phone = "";
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) 
	{
		if ( phone.matches("[a-zA-Z0-9\\+\\(\\)\\-\\.]+") ) { this.phone = phone; }	
	}
	
	private String email = "";
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) 
	{
		if ( email.matches("[^@]+@{1}[a-z0-9-]+(\\.[a-z0-9-]+)+") ) { this.email = email; }		
	}
	
	protected String website = "";
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) 
	{
		if ( website.matches(".+[a-z0-9-]+(\\.[a-z0-9-]+)+/?") ) { this.website = website; }
	}
	
	protected String description = "";
	public String getDescription() {
		return description;
	}
	public void setDescription(String text) {
		this.description = text;
	}

	protected String category = "exhibitor";
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
}
