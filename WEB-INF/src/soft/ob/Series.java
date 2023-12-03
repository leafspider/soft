package soft.ob;

public class Series 
{
	public String ticker = null;
	public String name = null;
	public double[] vals = null;

	public static void main ( String[] args )
	{
		try
		{		
			
//			Series series = new Series( "BXB", "ndgf", vals );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	public Series( String ticker, String name, double[] vals )
	{
		this.ticker = ticker;
		this.name = name;
		this.vals = vals;
	}
	
	
}
