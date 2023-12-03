package plants;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Wrangler {

    public static void main(String[] args) throws IOException, TwitterException
    {
        try {
            Wrangler.wrangle( new File("C:\\Workspace\\Ultra\\Mark\\Garden\\plants.htm") );
        }
        catch( Exception e ) { e.printStackTrace(); }
    }


    public static void wrangle( File file) throws Exception {

        Document doc = Jsoup.parse(file, null);

        /*
        symname = doc.select( "h1#symname" ).first().text();
        last = doc.select( "span.last" ).first().text();
        try { up = doc.select( "span.bigqb_up" ).first().text(); } catch ( Exception e) {}
        try { down = doc.select( "span.bigqb_down" ).first().text(); } catch ( Exception e) {}
        grey = doc.select( "span.smgrey" ).first().text();
        */

        ArrayList nodes = new ArrayList();
        ArrayList links = new ArrayList();

        Iterator rows = doc.select("div.row").iterator();
        while (rows.hasNext()) {
            Element row = (Element) rows.next();
            Iterator groups = row.select(".col-md-6").iterator();
            String id = "";
            while( groups.hasNext() ) {

                Element group = (Element) groups.next();
                Element header = (Element) group.select("h4").first();

                String name = header.text();
                String relation = "companion";
                double strength = 1D;
                if( name.indexOf(" Companions") > -1 ) {
                    name = name.replace(" Companions","");
                    //System.out.println( "\ng.put('" + name + "','type','Plant')" );
                    id = name.replace(" ", "_");
                    nodes.add("{id: '" + id + "', name: '" + name + "', weight: 1}");
                }
                else {
                    name = name.replace(" Antagonists","");
                    relation = "antagonist";
                    strength = 0.1;
                }

                Iterator list = group.select("div ul li" ).iterator();
                while( list.hasNext() ) {
                    Element entry = (Element) list.next();
                    //System.out.println( entry.text() );
                    String target = entry.text();
                    String targetId = entry.text().replace(" ", "_");
                    //System.out.println( "g.put('" + name + "','" + relation + "','" + target + "')" );
                    links.add("{source: '" + id + "', target: '" + targetId + "', strength: " + strength + "}");
                }
            }
        }

        Iterator nit = nodes.iterator();
        Iterator lit = links.iterator();

        System.out.println( "\"nodes\": [" );
        while (nit.hasNext()) {
            System.out.println( nit.next() + "," );
        }
        System.out.println( "]" );
        System.out.println( "\"links\": [" );
        while (lit.hasNext()) {
            System.out.println( lit.next() + "," );
        }
        System.out.println( "]" );
    }
}
