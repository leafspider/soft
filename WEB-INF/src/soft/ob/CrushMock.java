package soft.ob;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import soft.asset.Asset;
import soft.asset.CrushRecord;

public class CrushMock 
{
	public static List<CrushRecord> getStockValues( String ticker ) 
	{
		List<CrushRecord> list = new ArrayList<CrushRecord>();
		double begin = 2.5;
		for (int i = 1; i <= 31; i++) 
		{
			Calendar day = Calendar.getInstance();
			day.set(Calendar.HOUR, 0);
			day.set(Calendar.MINUTE, 0);
			day.set(Calendar.SECOND, 0);
			day.set(Calendar.MILLISECOND, 0);
			day.set(Calendar.YEAR, 2012);
			day.set(Calendar.MONTH, 8);
			day.set(Calendar.DAY_OF_MONTH, i);
			
			CrushRecord data = new CrushRecord();
			data.setTicker( ticker );
			data.setEndDate( Asset.defaultDateFormat.format(day.getTime()));
			day.add(Calendar.YEAR, -1);
			data.setStartDate( Asset.defaultDateFormat.format(day.getTime()));
			double close = Math.round(begin + Math.random() * begin * 0.1);
			data.setClose(close);
			data.setFlow( begin );
			data.setJam(Math.round(Math.min(begin, begin - Math.random() * begin * 0.1)));
			data.setPeel(Math.round(Math.max(begin, close) + Math.random() * 2));
			data.setPress(Math.round(Math.max(begin, close) + Math.random() * 2));
			data.setCrush(Math.round(Math.max(begin, close) + Math.random() * 2));
			data.setCherry(Math.round(Math.max(begin, close) + Math.random() * 2));
			data.setPlate(Math.round(Math.max(begin, close) + Math.random() * 2));

			list.add(data);
		}
		return list;
	}
 
}
