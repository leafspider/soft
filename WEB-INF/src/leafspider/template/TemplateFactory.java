package leafspider.template;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;
import java.util.ArrayList;
import java.util.Iterator;

import leafspider.util.*;

public class TemplateFactory 
{	
	public static void main(String[] args) 
	{
		try
		{
			URL url = new URL( "http://twitter.com/SilkCharm" );
			Template template = TemplateFactory.getSpiderTemplate( url );
			if ( template != null )
			{				
				System.out.println( "Template class=" + template.getClass().getName() );
				System.out.println( "Template properties=" + template.getProperties() );
				Iterator forces = template.getForces().iterator();
				while( forces.hasNext() )
				{
					String key = "" + forces.next();
					Log.infoln( "Force " + key );
				}
				Iterator strips = template.getStrips().iterator();
				while( strips.hasNext() )
				{
					String key = "" + strips.next();
					Log.infoln( "Strip " + key );
				}
			}			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public static TemplateProperties getProperties( File file ) throws Exception
	{
		TemplateProperties props = new TemplateProperties();
		props.load( new FileInputStream( file ) );
		return props;
	}

	private static File getTemplatesFolder() { return new File( ServerContext.getProjectsFolder() + "\\templates" ); }
	public static File getSpiderTemplatesFolder() { return new File( getTemplatesFolder().getAbsolutePath() + "\\spider" ); }
	public static File getContentTemplatesFolder() { return new File( getTemplatesFolder().getAbsolutePath() + "\\content" ); }

	protected static ArrayList spiderTemplateProperties = null;
	public static ArrayList getSpiderTemplateProperties() throws Exception
	{
		spiderTemplateProperties = new ArrayList();
		File[] files = getSpiderTemplatesFolder().listFiles();
		if (files != null )
		{
			for ( int i = 0; i < files.length; i++ )
			{
				Properties props = getProperties( files[i] );
				props.setProperty( "templateName", Util.removeFileExtension( files[i].getName() ) );
				spiderTemplateProperties.add( props );
			}
		}
		return spiderTemplateProperties;
	}

	protected static ArrayList contentTemplateProperties = null;
	public static ArrayList getContentTemplateProperties() throws Exception
	{
		contentTemplateProperties = new ArrayList();
		File[] files = getContentTemplatesFolder().listFiles();
		if (files != null )
		{
			for ( int i = 0; i < files.length; i++ )
			{
				Properties props = getProperties( files[i] );
				props.setProperty( "templateName", Util.removeFileExtension( files[i].getName() ) );
				contentTemplateProperties.add( props );
			}
		}
		return contentTemplateProperties;
	}
	

	/*
	public static Template getTemplate( URL url ) throws Exception
	{
		Template template = null;
		Iterator list = getTemplateProperties().iterator();
		while ( list.hasNext() )
		{
			Properties props = (Properties) list.next();
			String regex = ".*" + props.getProperty( "path" ) + ".*";
			if ( regex != null && url.toString().matches( regex ) )
			{
				String className = props.getProperty( "template" );
				template = (Template) Class.forName( className ).newInstance();
				Log.infoln( "Template match: [" + props.getProperty( "templateName" ) + "] " + url.toString() );
				template.setProperties( props );
			}
		}
		return template;		
	}

	public static Template getTemplate( File file ) throws Exception
	{
		Template template = null;
		Iterator list = getTemplateProperties().iterator();
		while ( list.hasNext() )
		{
			Properties props = (Properties) list.next();
			String regex = ".*" + props.getProperty( "path" ) + ".*";
			if ( regex != null && file.getAbsolutePath().matches( regex ) )
			{
				String className = props.getProperty( "template" );
				template = (Template) Class.forName( className ).newInstance();
				Log.infoln( "Template match: [" + props.getProperty( "templateName" ) + "] " + file.getAbsolutePath());
				template.setProperties( props );
			}
		}
		return template;		
	}
	*/

	public static Template getSpiderTemplate( Object obj ) throws Exception
	{
		String path = null;
		if ( obj instanceof File ) { path = ((File) obj).getAbsolutePath(); }
		else if ( obj instanceof URL ) { path = ((URL) obj).toString(); }
		
		Template template = null;
		Iterator list = getSpiderTemplateProperties().iterator();
		while ( list.hasNext() )
		{
			Properties props = (Properties) list.next();
			String regex = ".*" + props.getProperty( "url" ) + ".*";
			if ( regex != null && path.matches( regex ) )
			{
				String className = props.getProperty( "template" );
				template = (Template) Class.forName( className ).newInstance();
				Log.infoln( "Template match: [" + props.getProperty( "templateName" ) + "] " + path );
				template.setProperties( props );
			}
		}
		return template;		
	}
	
	public static Template getContentTemplate( Object obj ) throws Exception
	{
		String path = null;
		if ( obj instanceof File ) { path = ((File) obj).getAbsolutePath(); }
		else if ( obj instanceof URL ) { path = ((URL) obj).toString(); }
		else { path = (String) obj; }
		
		Template template = null;
		Iterator list = getContentTemplateProperties().iterator();
		while ( list.hasNext() )
		{
			Properties props = (Properties) list.next();
			String regex = ".*" + props.getProperty( "path" ) + ".*";
			if ( regex != null && path.matches( regex ) )
			{
				String className = props.getProperty( "template" );
				template = (Template) Class.forName( className ).newInstance();
				Log.infoln( "Template match: [" + props.getProperty( "templateName" ) + "] " + path );
				template.setProperties( props );
			}
		}
		return template;		
	}

}
