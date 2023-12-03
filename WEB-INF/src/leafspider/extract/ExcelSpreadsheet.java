package leafspider.extract;

import org.apache.poi.poifs.filesystem.*;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.eventusermodel.*;
import java.io.*;

import leafspider.util.*;

public class ExcelSpreadsheet implements HSSFListener
{
    private SSTRecord sstrec;

    public static void main ( String[] args )
    {
        try
        {
            File folder = new File( "C:\\TEMP\\downloader" );
            folder.mkdirs();

            File file = new File( folder.getAbsolutePath() + "\\https___www_cmegroup_com_CmeWS_exp_voiProductsViewExport_ctl_media_xls_tradeDate_20210318_assetClass369745.htm" );

            ExcelSpreadsheet.extractText(file.getAbsolutePath(), folder.getAbsolutePath() + "\\voiProductsViewExport.csv");

            System.out.println( file.getAbsolutePath() );
            System.out.println( "Done" );
        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    /**
     * This method listens for incoming records and handles them as required.
     * @param record    The record that was found while reading.
     */
    public void processRecord(Record record)
    {
        switch (record.getSid())
        {
            // the BOFRecord can represent either the beginning of a sheet or the workbook
            case BOFRecord.sid:
                BOFRecord bof = (BOFRecord) record;
                if (bof.getType() == bof.TYPE_WORKBOOK)
                {
//                	System.out.println("Encountered workbook");
//                	getOut().println("Encountered workbook");
                    // assigned to the class level member
                } 
                else if (bof.getType() == bof.TYPE_WORKSHEET)
                {
//                	System.out.println("Encountered sheet reference");
                	getOut().println("");
                }
                break;
            case BoundSheetRecord.sid:
                BoundSheetRecord bsr = (BoundSheetRecord) record;
//                System.out.println("New sheet named: " + bsr.getSheetname());
//                getOut().println("New sheet named: " + bsr.getSheetname());
                break;
            case RowRecord.sid:
                RowRecord rowrec = (RowRecord) record;
                System.out.println("Row found, first column at " + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());
//                getOut().println( "Row found, first column at " + rowrec.getFirstCol() + " last column at " + rowrec.getLastCol());
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;
                System.out.println("Cell found with value " + numrec.getValue() + " at row " + numrec.getRow() + " and column " + numrec.getColumn());
                getOut().println( numrec.getValue() );
//                getOut().print( numrec.getValue() + "," );
                break;
                // SSTRecords store a array of unique strings used in Excel.
            case SSTRecord.sid:
                sstrec = (SSTRecord) record;
                for (int k = 0; k < sstrec.getNumUniqueStrings(); k++)
                {
                	System.out.println("String table value " + k + " = " + sstrec.getString(k));
                    getOut().println( sstrec.getString(k) );
//                    getOut().print( sstrec.getString(k) + "," );
                }
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lrec = (LabelSSTRecord) record;
                System.out.println("String cell found with value " + sstrec.getString(lrec.getSSTIndex()));
                getOut().println( sstrec.getString(lrec.getSSTIndex()));
//                getOut().print( sstrec.getString(lrec.getSSTIndex()) + ",");
                break;
        }
    }

    /**
     * Read an excel file and spit out what we find.
     *
     * @param args      Expect one argument that is the file to read.
     * @throws IOException  When there is an error processing the file.
     */
    public static void test(String[] args) throws Exception
    {
    	String filename = "C:\\Cher\\Carl\\2005-09-15-a\\secret\\YEARCOMP_02_PROGRID_REV_REP9.XLS"; 
//    	String filename = "C:\\Catalog\\xls\\SAMPLES.XLS";
    	String outname = filename + ".txt";
    	
        // create a new file input stream with the input file specified at the command line
        FileInputStream fin = new FileInputStream( filename );
        
        // create a new org.apache.poi.poifs.filesystem.Filesystem
        POIFSFileSystem poifs = new POIFSFileSystem(fin);
        
        // get the Workbook (excel part) stream in a InputStream
        InputStream din = poifs.createDocumentInputStream("Workbook");
        
        // construct out HSSFRequest object
        HSSFRequest req = new HSSFRequest();
        
        ExcelSpreadsheet event = new ExcelSpreadsheet();
        event.setOut( Util.getPrintStream( outname ) );
        
        // lazy listen for ALL records with the listener shown above
        req.addListenerForAllRecords(event);
        
        // create our event factory
        HSSFEventFactory factory = new HSSFEventFactory();
        
        // process our events based on the document input stream
        factory.processEvents(req, din);
        
        // once all the events are processed close our file input stream
        fin.close();
        
        // and our document input stream (don't want to leak these!)
        din.close();
        
        System.out.println("done.");
    }
    
    /*
    public static test()
    {
    	fs = new POIFSFileSystem(new FileInputStream("workbook.xls"));
	    HSSFWorkbook wb = new HSSFWorkbook(fs);
	    HSSFSheet sheet = wb.getSheetAt(0);
	    HSSFRow row = sheet.getRow(2);
	    HSSFCell cell = row.getCell((short)3);
	    if (cell == null)
	        cell = row.createCell((short)3);
	    cell.setCellType(HSSFCell.CELL_TYPE_STRING);
	    cell.setCellValue("a test");
	
	    // Write the output to a file
	    FileOutputStream fileOut = new FileOutputStream("workbook.xls");
	    wb.write(fileOut);
	    fileOut.close();
    }
    */
    
    private PrintStream out = null;
	public PrintStream getOut() {
		return out;
	}
	public void setOut(PrintStream printStream) {
		this.out = printStream;
	} 

    public static boolean extractText( String sourceFileName, String textFileName ) {

    	PrintStream newOut = null;
		try {
	        // create a new file input stream with the input file specified at the command line
	        FileInputStream fin = new FileInputStream( sourceFileName );
	        newOut = Util.getPrintStream( textFileName );
	        
	        // create a new org.apache.poi.poifs.filesystem.Filesystem
	        POIFSFileSystem poifs = new POIFSFileSystem(fin);
	        
	        // get the Workbook (excel part) stream in a InputStream
	        InputStream din = poifs.createDocumentInputStream("Workbook");
	        
	        // construct out HSSFRequest object
	        HSSFRequest req = new HSSFRequest();
	        
	        ExcelSpreadsheet event = new ExcelSpreadsheet();
	        event.setOut( newOut );
	        
	        // lazy listen for ALL records with the listener shown above
	        req.addListenerForAllRecords(event);
	        
	        // create our event factory
	        HSSFEventFactory factory = new HSSFEventFactory();
	        
	        // process our events based on the document input stream
	        factory.processEvents(req, din);
	        
	        // once all the events are processed close our file input stream
	        fin.close();
	        
	        // and our document input stream (don't want to leak these!)
	        din.close();

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
			newOut.close();
		}
    }
    
}

