package leafspider.template;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import leafspider.util.*;

public class PhraseStripper extends LineStripper
{
	public static void main(String[] args) 
	{
		try
		{
			String txt = "Il y a environ 1 heure via Bob Retweeté par bill en réponse à sebchan";
			String fileName = "twitter.com.content.properties";
			
			TemplateProperties props = new TemplateProperties();
			props.load( new FileInputStream( new File( TemplateFactory.getContentTemplatesFolder() + "\\" + fileName ) ) );
			
			PhraseStripper stripper = new PhraseStripper();
			stripper.setProperties(props);
			
			System.out.println( txt );
			System.out.println( stripper.strippedLine( txt ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
	
	public void stripBoilerPlateFromText( File textFile ) throws Exception
	{
		char lineFeed = 10;
		char carriageReturn = 13;
		Iterator lines = Util.getArrayListFromFile( textFile.getAbsolutePath() ).iterator();
		StringBuffer buffer = new StringBuffer();
		boolean isWriting = false;
		if ( getStart().trim().length() > 0 ) { isWriting = true; }
		while ( lines.hasNext() )
		{
			String line = (String) lines.next();
			if ( getStart().trim().length() < 1 || line.matches( getStart() ) )
			{
				isWriting = true;
			}
			if ( getStop().trim().length() > 0 && line.matches( getStop() ) )
			{
				isWriting = false;
				break;
			}
			if ( isWriting )
			{
				buffer.append( strippedLine( line ) + carriageReturn );
			}
		}
		Util.writeAsFile( buffer.toString(), textFile.getAbsolutePath() );
	}

	public String strippedLine( String line )
	{
		if ( line.equals( "" ) ) { return line; } 
		Iterator list = getStrips().iterator();
		while( list.hasNext() )
		{
			String strip = (String) list.next();
			line = line.replaceAll( strip, "" );
		}
		return line;				
	}
}
