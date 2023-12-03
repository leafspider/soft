package soft.formula;

import leafspider.rest.RepresentationException;
import leafspider.util.Duration;
import leafspider.util.Log;
import leafspider.util.Util;
import soft.report.Folders;
import soft.toffee.*;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class FormulaGrid extends FormulaRepresentation
{
    private boolean debug = false;

    public static String representation = "grid";
    public String getRepresentation() { return representation; }

    private static DateFormat dateFormat = new SimpleDateFormat( "yyyy-MM-dd (HH:mm)" );

    public static void main(String[] args) throws IOException, TwitterException
    {
    	try {
    		FormulaGrid fetch = new FormulaGrid();
			fetch.setResourceId("nutraceuticals");
    		System.out.println( fetch.getXml() );
    	}
		catch( Exception e ) { e.printStackTrace(); }
    }

    public String getXml() throws RepresentationException {

    	try {

			String formula = getResourceId();

			File toffeeXmlFile = new File(Folders.toffeeFolder() + "\\" + formula.toUpperCase() + ".xml");
			toffeeXmlFile.getParentFile().mkdirs();

			boolean isStoredToffee = true;
			int frequencyHours = 1;

			if ( isStoredToffee && toffeeXmlFile.exists() && Duration.hours( toffeeXmlFile.lastModified(), Util.getNow() ) < frequencyHours ) {
				return Util.fileToString( toffeeXmlFile, "\n" );
			}

			//Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.search );		// jmh 2023-06-21
			Twitter twitter = TwitterCreds.twitterInstance( TwitterCreds.agent );

			FormulaQuery formulaQuery = new FormulaQuery( twitter );

			TreeSet<Signal> signals = new TreeSet();
			TreeSet<String> userNames = new TreeSet();
			TreeSet<String> tickers = new TreeSet();
			HashMap<String, Tweeter> tweeters = new HashMap();

			List<String> xlist = Util.getArrayListFromFile( formulaFile( formula ).getAbsolutePath() );
			List<String> ylist = Util.getArrayListFromFile( formulaFile( formula ).getAbsolutePath() );
			Collections.reverse( xlist );

			xlist = toProperCase( xlist );
			ylist = toProperCase( ylist );

			List<String> queries = new ArrayList();

			for ( int y=0; y < ylist.size()-1; y++ ) {

				//String query = "";
				String yitem = ylist.get(y);
				for (int x = 0; x < xlist.size() - 1; x++) {

					if (x > ylist.size() - y - 2) {
						continue;
					}
					String xitem = xlist.get(x);
					//query = "\"moss bladderwrack\" AND \"burdock root\"";
					//query += " OR (\"" + xitem + "\" AND \"" + yitem + "\")";
					queries.add("(\"" + xitem + "\" AND \"" + yitem + "\")");
				}
				//query = query.replaceFirst( " OR ", "");
			}

			String query = "";
			int n = 0;
			for( String clause : queries ) {

				query += " OR " + clause;

				if ( ++n > 9 || queries.indexOf( query ) == queries.size()-1 ) {

					query = query.replaceFirst( " OR ", "");

					formulaQuery.doQuery(query, ylist);

					userNames.addAll(formulaQuery.userNames);
					tweeters.putAll(formulaQuery.tweeters);
					tickers.addAll(formulaQuery.tickers);
					signals.addAll(formulaQuery.signals);

					//Log.infoln( query );
					//Log.infoln( "" );

					query = "";
					n = 0;
				}
			}


			/* all combos in separate queries
			for ( int y=0; y < ylist.size()-1; y++ ) {

				String yitem = ylist.get(y);
				for ( int x=0; x < xlist.size()-1; x++ ) {

					if ( x > ylist.size() - y - 2 ) { continue; }
					String xitem = xlist.get(x);

					String query = "\"" + xitem + "\" AND \"" + yitem + "\"";

					toffeeQuery.doQuery( query );

					tickers.addAll( toffeeQuery.tickers );
					userNames.addAll( toffeeQuery.userNames );
					signals.addAll( toffeeQuery.signals );
					tweeters.putAll( toffeeQuery.tweeters );
				}
			}
			*/

			/* all items in one query
			for ( int y=0; y < ylist.size(); y++ ) {

				String yitem = ylist.get(y);
				query += " OR \"" + yitem + "\"";
			}
			query = query.replaceFirst( " OR ", "");

			toffeeQuery.doQuery( query );

			tickers.addAll( toffeeQuery.tickers );
			userNames.addAll( toffeeQuery.userNames );
			signals.addAll( toffeeQuery.signals );
			tweeters.putAll( toffeeQuery.tweeters );
			*/

			ToffeeGrid grid = new ToffeeGrid();
			grid.setResourceId( formula );
			grid.setQuery( formula );

			String xml = grid.buildXml(tickers, userNames, signals, tweeters);

			if ( isStoredToffee ) {
				PrintStream out = Util.getPrintStream(toffeeXmlFile.getAbsolutePath());
				out.print(xml);
				out.close();
			}

			return xml;
    	}
	    catch( Exception e )  {
	    	throw new RepresentationException( Util.getStackTrace( e ) ); 
	    }
    }

    private List<String> toProperCase( List<String> list ) {

    	List<String> newlist = new ArrayList();
		for ( String item : list ) {
			newlist.add( toProperCase( item ) );
		}
		return newlist;
	}

    private String toProperCase( String s ) {
    	return s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase();
	}

	public static File formulaFile( String formula ) {
		return new File(Folders.formulaConfigFolder() + "\\" + formula + ".txt");
	}


		/*
		Element root = new Element( "formula" );
		root.addContent( new Element( "project" ).addContent( new CDATA( getProject() ) ) );
		root.addContent( new Element( "handle" ).addContent( new CDATA( getResourceId() ) ) );

		//String[] list = {"Liposomal NAD+", "Chaga Mushroom", "Kiwi", "Bladderwrack", "Okra", "L-Glutamine", "Collagen", "Saccharomyces Boulardii", "Multi Bacillus Spore Probiotics", "Turpene to Gene" };

		Element ingredients = new Element( "ingredients" );
		String txt = "";
		for ( String value : list ) { txt += "'" +  value + "',"; }
		ingredients.addContent( txt.substring(0,txt.length()-1) );
		root.addContent( ingredients );

		return getJdomWriter().writeToString( root );
		*/

	}



