package soft.stores;


public class Deal extends HibernateRecord
{
	public static String table = "Deal";

	public String text = null;
	public String getText() {
		return text;
	}
	public void setText(String html) {
		this.text = html;
	}

	public String url;
    public String getUrl() {
        return url;
    }
    public void setUrl(String title) {
        this.url = title;
    }
    
	public String price = null;
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}

	public String saving = null;
	public String getSaving() {
		return saving;
	}
	public void setSaving(String saving) {
		this.saving = saving;
	}

	public String value = null;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}

	public String discount = null;
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}

	public String city = null;
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}

    private String storeUrl;
    public String getStoreUrl() {
        return storeUrl;
    }
    public void setStoreUrl(String val) {
        this.storeUrl = val;
    }
        
    private String storeHost = null;
	public String getStoreHost() {
		return storeHost;
	}
	public void setStoreHost(String val) {
		this.storeHost = val;
	}
	
	private String nouns = null;
	public String getNouns() {
		return nouns;
	}
	public void setNouns(String nouns) {
		this.nouns = nouns;
	}
	
	private String verbs = null;
	public String getVerbs() {
		return verbs;
	}
	public void setVerbs(String verbs) {
		this.verbs = verbs;
	}
	
	private String unid = null;
	public String getUnid() {
		return unid;
	}
	public void setUnid(String unid) {
		this.unid = unid;
	}
}
