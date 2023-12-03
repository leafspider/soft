package leafspider.fuzzy;

import java.io.File;
import java.util.HashMap;

import net.sourceforge.jFuzzyLogic.rule.Variable;
import org.jfree.chart.JFreeChart;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.rule.Rule;

public class Fuzzy
{
	private File fclFile = null;
	private FIS fis = null;
    
    public static void main(String[] args) throws Exception 
    {   	    	        
    	File file = new File( "C:\\Server\\tomcat6\\webapps\\soft\\fcl\\crush.fcl" );
    	Fuzzy fuzzy = new Fuzzy( file );
    	fuzzy.chart();
    	fuzzy.set( "dnsd30", 0.1d );
    	fuzzy.set( "dnsd20", 0.1d );
    	fuzzy.set( "dnsd10", 0.5d );
    	fuzzy.set( "nsdsd", 0.2d );
    	fuzzy.evaluate();
//    	System.out.println( "crush=" + fuzzy.get( "crush" ) );
    	fuzzy.show( "crush" );
    	fuzzy.why( "crush" );
    }
    
    public Fuzzy( File fclFile )
    {
    	this.fclFile = fclFile;
    	load( fclFile );
    }
    
    public void chart() {
        fis.chart();
    }
    
    public void load( File file )
    {
    	fis = FIS.load( file.getAbsolutePath(),true );
    }

    public String why( String name ) {

    	String st = "\n";
    	for( Rule r : fis.getFunctionBlock( name ).getFuzzyRuleBlock("No1").getRules() ) { st += r + "\n"; }
    	st += name + "=" + get( name ) + "\n";
    	return st;        
    }

    public JFreeChart show( String name ) {

    	return fis.getVariable( name ).chartDefuzzifier(true);
    }

    public void evaluate()
    {
    	fis.evaluate();
    }

    public double get( String name ) {

    	return fis.getVariable( name ).getValue();
    }

    public void set( String name, double value )
    {
    	fis.setVariable( name, value );   
    }

    public HashMap<String, Double> getInputVariables(String functionBlockName ) {

        HashMap map = new HashMap<String, Double>();
        HashMap<String, Variable> variables = fis.getFunctionBlock( functionBlockName ).getVariables();
        for ( Variable var : variables.values() ) {
            if ( var.isOutputVarable() ) { continue; }
            String name = var.getName();
            Double value = var.getValue();
            map.put(name, value);
        }
        return map;
    }

    public HashMap<String, Double> getOutputVariables(String functionBlockName ) {

        HashMap map = new HashMap<String, Double>();
        HashMap<String, Variable> variables = fis.getFunctionBlock( functionBlockName ).getVariables();
        for ( Variable var : variables.values() ) {
            if ( var.isOutputVarable() ) {
                String name = var.getName();
                Double value = var.getValue();
                map.put(name, value);
            }
        }
        return map;
    }

    public String why( String functionBlock, String variable ) {

        String st = "";
        /*
        HashMap<String, Double> outvars = getOutputVariables(functionBlock);
        for ( String var : outvars.keySet() ) {
            if ( var.equals(variable) ) {
                st += var + "=" + String.format("%,.2f", (Double) outvars.get(var));
            }
        }
        st += " [";
        */
        HashMap<String, Double> invars = getInputVariables(functionBlock);
        for ( String var : invars.keySet() ) {
            int pos = var.lastIndexOf( "_");
            String suff = var.substring( 0, pos+1 );
            String ticker = var.substring( pos+1, var.length() ).toUpperCase();
            st += suff + ticker + " " + String.format( "%,.2f", (Double) invars.get(var) ) + " ... ";
        }
        st = st.substring(0, st.lastIndexOf(" ... ") );
//        st = st.trim();
        //st += "]";
        return st;
    }
}
