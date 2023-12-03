package soft.assetlist;

import soft.report.WatchlistPerformance;

public class AssetlistPerformance extends AssetlistRepresentation
{
	public static void main(String[] args)
	{
		try {

		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String representation = "performance";
    public String getRepresentation() { return representation; }

	public String getHtm() throws Exception {

		WatchlistPerformance wp = new WatchlistPerformance();
		wp.doCreateWatchlist();
		wp.doParseWatchlistToCsv();	// Loads histories
		wp.doParseCsvToPchangeXml();
		return wp.doTransform( false );
	}

    /*
    public String getXml() throws RepresentationException
    {
        if ( getResourceId() == null ) { throw new RepresentationException( "Resource not found: " + getResource() + getRequest().getPathInfo() ); }
           	
    	try
    	{
			Element root = new Element( "performance" );
	   		root.addContent( new Element( "collection" ).addContent( new CDATA( getProject() ) ) );
			root.addContent( new Element( "resourceId" ).addContent( new CDATA( getResourceId() ) ) );
			
			String csvfile = null;
			String title = null;
			
			if( getResourceId().equalsIgnoreCase("trading") )
			{
				csvfile = ServerContext.toffeeDataFolder() + "\\" + representation + "\\trading.csv";
				title = "Trading Alerts Since 2009";
			}
			else if( getResourceId().equalsIgnoreCase("assetmanager") )
			{
				csvfile = ServerContext.toffeeDataFolder() + "\\" + representation + "\\assetmanager.csv";
				title = "Asset Manager Trading";
			}
			else if( getResourceId().equalsIgnoreCase("hedge") )
			{
				csvfile = ServerContext.toffeeDataFolder() + "\\" + representation + "\\hedge.csv";
				title = "Hedge Trading";
			}
			root.addContent( new Element( "title" ).addContent( new CDATA( title ) ) );
			
			TreeSet<Trade> trades = new TreeSet<Trade>();
//			ArrayList<Trade> trades = new ArrayList<Trade>();

			Iterator list = Util.getArrayListFromFile(csvfile).iterator();
			boolean isFirst = true;
//        	Log.infoln("AssetlistPortfolio.getXml 0" );
			while(list.hasNext())
			{
				String line = (String) list.next();
//				Log.infoln(line);
				if(isFirst)	// ignore headings
				{
					isFirst = false;
					continue;
				}

				String[] vals = line.split(",", -1);	// Asset,EntryDate,EntryPrice,Duration,ExitDate,ExitPrice
//				Log.infoln("len=" + vals.length);
				
				Trade trade = new Trade();
				trade.setInstrument(vals[0]);
				try { trade.setEntryDate(Trade.format.parse(vals[1])); } catch(Exception e) {}
				try { trade.setEntryPrice(Double.parseDouble(vals[2])); } catch(Exception e) {}
				trade.setDuration(vals[3]);
				try { trade.setExitDate(Trade.format.parse(vals[4])); } catch(Exception e) {}
				try { trade.setExitPrice(Double.parseDouble(vals[5])); } catch(Exception e) {}
				try { trade.setPercentageReturn(Double.parseDouble(vals[6])); } catch(Exception e) {}
				try { trade.setShares((int)Double.parseDouble(vals[7])); } catch(Exception e) {}
				try { trade.setReturnVal(Double.parseDouble(vals[8])); } catch(Exception e) {}
				
				trades.add(trade);
				root.addContent(trade.listElement());
			}
//        	Log.infoln("AssetlistPortfolio.getXml 1" );

			return getJdomWriter().writeToString( root );
    	}
        catch( Exception e ) 
        { 
        	throw new RepresentationException( Util.getStackTrace( e ) ); 
        }    	
    }
    */

}
