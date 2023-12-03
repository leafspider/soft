package leafspider.util;

import leafspider.db.DatabaseManager;
import org.jdom2.Element;

import java.io.File;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Timestamp {

    public static long readTimestamp(DatabaseManager dbm) throws Exception {
        return readTimestamp( new File( dbm.getProjectFolder() ) );
    }
    public static long readTimestamp(File folder) throws Exception {
        File file = new File( folder.getAbsolutePath() + "\\timestamp.dat" );
        Long ret = 0L;
        if( file.exists() ) {
            try { ret = Long.parseLong("" + Util.getArrayListFromFile(file.getAbsolutePath()).get(0)); } catch( Exception e ) {}
        }
        return ret;
    }

    public static void writeTimestamp(DatabaseManager dbm) throws Exception {
        writeTimestamp( new File( dbm.getProjectFolder() ) );
    }
    public static void writeTimestamp(DatabaseManager dbm, long stamp) throws Exception {
        writeTimestamp( new File( dbm.getProjectFolder() ), stamp );
    }
    public static void writeTimestamp(File folder) throws Exception {
        writeTimestamp(folder,Util.getNow());
    }
    public static void writeTimestamp(File folder, long stamp) throws Exception {
        folder.mkdirs();
        PrintStream stream = Util.getPrintStream(folder.getAbsolutePath() + "\\timestamp.dat");
        stream.print(stamp);
    }

    public static Element getElement( File folder ) throws Exception {
        long stamp = Timestamp.readTimestamp( folder );
        return getElements( stamp );
    }
    public static Element getElement( DatabaseManager dbm ) throws Exception {
        long stamp = Timestamp.readTimestamp( dbm );
        return getElements( stamp );
    }

    private static Element getElements( long stamp ) {

        Element root = new Element("updated" );

        Element timeStamp = new Element( "time" );
        timeStamp.setText("" + stamp );
        root.addContent(timeStamp);

        DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd kk:mm:ss.ms" );
        Element time = new Element( "datetime" );
        time.setText( dateFormat.format( stamp ) );
        root.addContent(time );

        return root;
    }

}
