package soft.assetlist;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.hibernate.Session;

import soft.asset.CrushRecord;

import leafspider.db.DatabaseManager;
import leafspider.util.Log;
import leafspider.util.Util;

public class AlistVariableBatch extends AlistBatch
{
	public void initDatabase( DatabaseManager dbm ) throws Exception
	{
		// Delete 1-week old records
		Calendar calendar = new GregorianCalendar();
		calendar.add(GregorianCalendar.DAY_OF_MONTH, -7);
//		calendar.add(GregorianCalendar.MINUTE, -10);
		long weekAgo = calendar.getTimeInMillis();
		int num1 = dbm.deleteRecords( "delete from CrushRecord where lastModified < " + weekAgo );

		// Delete records which aren't currently in the config list
		String ticks = "'" + getTickerList().replaceAll(" ", "','") + "'";
		int num2 = dbm.deleteRecords( "delete from CrushRecord where ticker not in (" + ticks + ")");

		if ( num1 > 0 || num2 > 0) {
			Log.infoln("Deleted " + num1 + " old and " + num2 + " obsolete records from " + id);
		}
	}
}
