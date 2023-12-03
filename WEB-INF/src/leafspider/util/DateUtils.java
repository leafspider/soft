package leafspider.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public abstract class DateUtils
{

	/**
	 * Converts a string to a date, using the same convetions as date_getString
	 * @param theDate The date represented as a string
	 * @return A date object that theDate represents
	 */
	public static synchronized Date getDateFromString(String theDate)
	{
		try
		{
			return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL).parse(theDate);
		}
		catch (ParseException pe)
		{
			return null;
		}
	}

	/**
	 * Gets a locale-dependent string representation of the current date
	 * @return A locale-dependent represetation of the current date
	 */
	public static synchronized String getString()
	{
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL).format(new Date());
	}

	public static synchronized long getLong()
	{
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL).LONG;
	}

	/**
	 * Gets a locale-dependent string representation of a date
	 * @param theDate The date to get a string representation of
	 * @return A locale-dependent represetation of a date
	 */
	public static synchronized String getString(Date theDate)
	{
		return DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL).format(theDate);
	}

}