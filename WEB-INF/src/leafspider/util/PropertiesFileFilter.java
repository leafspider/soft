package leafspider.util;

import java.io.File;
import java.io.FileFilter;

public class PropertiesFileFilter implements FileFilter
{
	public static void main(String[] args)
	{
		try
		{		
			PropertiesFileFilter filter = new PropertiesFileFilter();
			System.out.println( filter.accept( new File( "C:\\CIRILab\\kgs\\admin\\projects\\asda.properties" ) ) );
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean accept( File file )
	{
		boolean isMatch = false;
		int pos = file.getName().toLowerCase().indexOf( ".properties" );
		if ( pos == (file.getName().length() - 11) )
		{ 
			isMatch = true;
		}
		return isMatch;
	}
}
