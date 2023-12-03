package leafspider.extract;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.apache.poi.poifs.eventfilesystem.POIFSReader;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderEvent;
import org.apache.poi.poifs.eventfilesystem.POIFSReaderListener;
import org.apache.poi.poifs.filesystem.DocumentInputStream;
import org.apache.poi.util.LittleEndian;

import leafspider.util.*;

/** 
 * @author Mark Hurst 
 */

public class PowerPointFile implements POIFSReaderListener 
{
	public static void main(String[] args)
	{
		try
		{
	    	String dirName = "D:\\KOSCatalog\\ppt4";
	    	String fileName = "ItsSmart Presentation Cover  RB Rev.ppt";
//	    	String fileName = "Strategic Advantages cover page.ppt";
//	    	String fileName = "New Kortex Interface.ppt";
//	    	String fileName = "KDS EnvCan Design v4.ppt";
//	    	String fileName = "Adaptive Workflow.ppt";
//	    	String fileName = "CLEMSHOW2.PPT";
	    	String sourceFileName = dirName + "\\" + fileName;	    	
	    	File folder = new File( dirName );
	    	String[] files = folder.list();
	    	PowerPointFile ppt = new PowerPointFile();
	    	
//	    	for ( int i = 0; i < files.length; i++ )
	    	{
//		    	sourceFileName = folder.getAbsolutePath() + "\\" + files[i];
		    	if ( Util.getFileExtension( sourceFileName ).equalsIgnoreCase( "PPT" ) )
		    	{
		    		String textFileName = sourceFileName + ".txt";
		    		ppt.extractText( sourceFileName, textFileName );
		    	}
	    	}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	private PrintStream out = null;
	public boolean extractText( String sourceFileName, String textFileName )
	{
		InputStream input = null;
		try
		{
            POIFSReader reader = new POIFSReader();
            input = new FileInputStream( sourceFileName );
            out = new PrintStream( textFileName );
            reader.registerListener( this );
            reader.read( input );		// Reads text into out
            return true;
		}
		catch( Exception e )
		{
			Log.infoln( sourceFileName );
			e.printStackTrace();
			return false;
		}
		finally
		{
			out.close();
		}
	}

    public void processPOIFSReaderEvent(POIFSReaderEvent event) 
    {
        try
		{
            if(!event.getName().equalsIgnoreCase("PowerPoint Document")) { return; }
            DocumentInputStream input = event.getStream();
            byte[] buffer = new byte[input.available()];
            input.read(buffer, 0, input.available());

            for(int i=0; i<buffer.length-20; i++) 
            {
            	long type = LittleEndian.getUShort( buffer, i+2 );
                int size = (int) LittleEndian.getUInt( buffer, i+4 );
                try
				{
	                if( type == 4008 ) 
	                {
	                    if ( i < 0 || size < 0 || buffer.length < (i + 4 + 1 + size + 3) )
	                    { 
	                    	break;
	                    }
	                	String buf = new String( buffer, i + 4 + 1, size + 3 );	                	
	                	buf = buf.replaceAll( "[\\x00]", "" );		// Convert to DOS
	                    out.print( buf );
	                    i = i + 4 + size;
	                }
				}
                catch (Exception ex) 
				{
                	System.out.println( "i=" + i + ", size=" + size + ", length=" + buffer.length ); 
                	Log.infoln( "Exception", ex ); 
                }
            }
        } 
        catch (Exception ex) { Log.infoln( "Exception", ex ); }
    }
	
	/*	
    public void processPOIFSReaderEvent(POIFSReaderEvent event) 
    {
        try
		{
            if(!event.getName().equalsIgnoreCase("PowerPoint Document")) { return; }
            DocumentInputStream input = event.getStream();
            byte[] buffer = new byte[input.available()];
            input.read(buffer, 0, input.available());

            for(int i=0; i<buffer.length-20; i++) 
            {
                long type = LittleEndian.getUShort(buffer,i+2);
                long size = LittleEndian.getUInt(buffer,i+4);
                if(type==4008) 
                {
                    writer.write(buffer, i + 4 + 1, (int) size +3);
                    i = i + 4 + 1 + (int) size - 1;
                }
                try 
				{
                    Thread.sleep(10);
                } 
                catch (Exception ex) {}
            }
        } 
        catch (Exception ex) { ex.printStackTrace(); }
    }
    */
    
}
