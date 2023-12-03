package soft.toffee;

import java.util.Date;

public class Signal implements Comparable
{
	public String userName;
	public String tickerName;
	public String url;
	public String text;
	public Date created;

	public int mention = 0;
	public int longEquity = 0;
	public int longCall = 0;
	public int shortPut = 0;
	public int shortEquity = 0;
	public int shortCall = 0;
	public int longPut = 0;
	
	public int compareTo( Object o ) {

		Signal item = (Signal) o;
		String comp1 = tickerName + userName + getNature(); 
		String comp2 = item.tickerName + item.userName + item.getNature(); 
		return comp1.compareTo( comp2 );
	}
	
	public String getNature() {

		return "" + mention + ":" + longEquity + ":" + longCall + ":" + shortPut + ":" + shortEquity + ":" + shortCall + ":" + longPut;
	}
}
