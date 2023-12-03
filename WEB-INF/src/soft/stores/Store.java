package soft.stores;

import org.hibernate.Session;


public class Store extends HibernateRecord
{
	public static String table = "Store";

    public String toString() { return "[url=" + url + "]"; }
    
    private String city = null;
	public String getCity() {
		return city;
	}
	public void setCity(String name) {
		this.city = name;
	}
	
    private String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String title) {
        this.url = title;
    }
        
    private String host = null;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
}