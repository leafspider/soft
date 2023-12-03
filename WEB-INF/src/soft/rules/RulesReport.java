package soft.rules;

import leafspider.db.DatabaseManager;
import leafspider.fuzzy.Fuzzy;
import leafspider.util.*;
import org.jdom2.Element;
import soft.asset.Asset;
import soft.asset.CrushRecord;
import soft.batch.BatchAgent;
import soft.report.Folders;
import soft.report.ViperReport;

import java.io.File;
import java.io.FileFilter;
import java.io.PrintStream;
import java.util.*;

public class RulesReport extends ViperReport {

    private static boolean isDisplayFuzzySets = false;

    public static void main ( String[] args )
    {
        try {

            RulesReport report = new RulesReport();
            report.setResourceId( "rules" );
            report.setDatabaseManager( new DatabaseManager( report.getResourceId() ) );
            report.populate();

            PrintStream out = Util.getPrintStream("C:\\tmp\\rules.xml");

            XmlJdomWriter jdomWriter = new XmlJdomWriter();
            out.print(jdomWriter.writeToString( report.getRoot() ));
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public void populate() throws Exception {

        Long startTime = Util.getNow();

        try {

            root = new Element( "reports" );

            for ( String sector : getSectors() ) {

                //Log.infoln("Sector " + sector);

                Element report = new Element("report");
                report.setAttribute("title", sector);

                for ( String assetName : getAssets( sector ) ) {

                    //Log.infoln("--- Asset " + assetName);

                    Element asset = new Element("asset");
                    asset.setAttribute("name", assetName);

                    Fuzzy fuzzy = getFuzzy(sector, assetName);

                    Element signals = new Element("signals");

                    HashMap<String, Double> finvars = fuzzy.getInputVariables(assetName);

                    for (String finvar : finvars.keySet()) {
                        if (finvar.indexOf("dvol_") == -1) {
                            String ticker = finvar.substring(finvar.lastIndexOf("_") + 1, finvar.length());
                            AssetDelta delta = new AssetDelta(prices(ticker));
                            fuzzy.set("d_" + ticker, delta.getPricePC());
                            fuzzy.set("dvol_" + ticker, delta.getVolumePC());
                        }
                    }

                    fuzzy.evaluate();

                    HashMap<String, Double> outvars = fuzzy.getOutputVariables(assetName);
                    for (String var : outvars.keySet()) {
                        double val = fuzzy.get(var);
                        Log.infoln(assetName + " " + var + "=" + val );
                        if ( isDisplayFuzzySets ) { fuzzy.show( var ); }

                        Element signal = new Element("signal");
                        String color = Asset.getCrushColor(val);
                        signal.setAttribute("action", var);
                        signal.setAttribute("val", "" + val);
                        signal.setAttribute("color", color);

                        Element why = new Element("why");
                        String whyst = fuzzy.why(assetName, var);
                        if ( isDisplayFuzzySets ) { Log.infoln(whyst); }
                        why.addContent(whyst);
                        signal.addContent(why);

                        signals.addContent(signal);
                    }
                    asset.addContent(signals);
                    report.addContent(asset);
                }

                root.addContent(report);
            }

            /*
            File folder = getOutputFolder();
            folder.mkdirs();
            Timestamp.writeTimestamp(folder);
            */
        }
        catch(Exception e) {
            e.printStackTrace();
        }

        double duration = (Util.getNow() - startTime)/1000D;
        Log.infoln("RulesReport took " + duration + " seconds" );
    }

    public ArrayList<String[]> prices(String ticker ) throws Exception {

        //Log.infoln("----- Prices for " + ticker);

        Calendar end = new GregorianCalendar();
        Calendar start = new GregorianCalendar();
        start.add( Calendar.DATE, -5);	// Go back 5 days

        ArrayList<String[]> prices = null;

        CrushRecord record = new CrushRecord();

        String fmdStart = Asset.defaultDateFormat.format(start.getTime());
        String fmdEnd = Asset.defaultDateFormat.format(end.getTime());
        String select = "from CrushRecord where ticker='" + ticker + "' and endDate='" + fmdEnd + "'";
        List records = getDatabaseManager().selectRecords(select);

        if ( records.size() > 0 ) {
            ArrayList<ArrayList> pricesArrayList = JsonConverter.jsonToArrayList( ((CrushRecord)records.iterator().next()).getPrices() );
            prices = new ArrayList();
            for (int j = 0; j < pricesArrayList.size(); j++) {
                ArrayList arr = pricesArrayList.get(j);
                prices.add( (String[]) arr.toArray(new String[arr.size()]) );
            }
        }
        else {

            prices = (ArrayList<String[]>) Asset.instance(ticker).loadPrices(start, end);
            Collections.reverse(prices);
            record.setTicker(ticker);
            record.setStartDate(fmdStart);
            record.setEndDate(fmdEnd);
            record.setPrices(BatchAgent.skimPrices(prices));
            getDatabaseManager().saveAndCommitRecord(record);
        }

        if (prices.size() < 1) {
            throw new Exception("RulesReport: " + ticker + " has no prices");
        }

        return prices;
    }

    public Fuzzy getFuzzy( String sector, String asset ) {

        File file = new File( Folders.rulesConfigFolder() + "\\" + sector + "\\" + asset + ".fcl" );
        Fuzzy fuzzy = new Fuzzy( file );
        if ( isDisplayFuzzySets ) {
            fuzzy.chart();
        }
        return fuzzy;
    }

    public ArrayList<String> getSectors() {

        File rulesFolder = Folders.rulesConfigFolder();
        File[] folders = rulesFolder.listFiles( new FolderFilter() );
        ArrayList<String> sectors = new ArrayList<String>();
        for ( File folder : folders ) {
            sectors.add( Util.removeFileExtension( folder.getName() ) );
        }
        return sectors;
    }

    public ArrayList<String> getAssets( String sector ) {

        File sectorFolder = new File( Folders.rulesConfigFolder() + "\\" + sector );
        File[] files = sectorFolder.listFiles();
        ArrayList<String> assets = new ArrayList<String>();
        for ( File file : files ) {
            if ( !file.isDirectory() ) {
                assets.add( Util.removeFileExtension( file.getName() ) );
            }
        }
        return assets;
    }

}
