package leafspider.template;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.ArrayList;

import leafspider.util.*;

public class LinkStripper extends Template
{
	public static void main(String[] args) 
	{
		try
		{
			TemplateProperties props = new TemplateProperties();
			props.load( new FileInputStream( new File( "C:\\CIRILab\\kgs\\admin\\templates\\export.properties" ) ) );

			PhraseStripper stripper = new PhraseStripper();
			stripper.setProperties(props);
			
			String txt = "FEMA Leads Effort; Federal Emergency Borders Tightened";
			System.out.println( txt );
			System.out.println( stripper.strippedLine( txt ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void stripBoilerPlateFromText( File textFile ) throws Exception { }
	
	public ArrayList getStrips() 
	{
		if ( strips == null )
		{
			strips = new ArrayList();
			Iterator keys = getProperties().keySet().iterator();
			while ( keys.hasNext() )
			{
				String key = (String) keys.next();
				if ( key.matches( "templateName" ) 
					|| key.matches( "template" )  
					|| key.matches( "url" ) )
				{ continue; }
				else
				{
					String value = getProperties().getProperty( key );
					if ( value.trim().length() == 0 ) 
					{
						value = "false";
						getProperties().setProperty( key, value );
					}
					if ( value.equals( "false" ) ) 
					{
//						System.out.println( "Strip " + key + "=" + value );
						strips.add( key );
					}
				}
			}
		}
		return strips;
	}
	
	public ArrayList getForces() 
	{
		if ( forces == null )
		{
			forces = new ArrayList();
			Iterator keys = getProperties().keySet().iterator();
			while ( keys.hasNext() )
			{
				String key = (String) keys.next();
				if ( key.matches( "template" ) || key.matches( "url" ) ) { continue; }
				else
				{
					String value = getProperties().getProperty( key );
					if ( value.equals( "true" ) ) 
					{
//						System.out.println( "Force " + key + "=" + value );
						forces.add( key );
					}
				}
			}
		}
		return forces;
	}
}
