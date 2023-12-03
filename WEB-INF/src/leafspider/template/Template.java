package leafspider.template;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;

public abstract class Template 
{
	public abstract void stripBoilerPlateFromText( File textFile ) throws Exception;	

	private Properties properties = null;
	public Properties getProperties() {
		return properties;
	}
	public void setProperties(Properties properties) {
		this.properties = properties;
	} 

	public abstract ArrayList getStrips();	
	protected ArrayList strips = null;

	public abstract ArrayList getForces();	
	protected ArrayList forces = null;
}
