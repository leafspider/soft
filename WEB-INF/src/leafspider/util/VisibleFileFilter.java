package leafspider.util;

import java.io.File;
import java.io.FileFilter;

public class VisibleFileFilter implements FileFilter
{
	public boolean accept( File file )
	{		
		return !file.isHidden();
	}

}