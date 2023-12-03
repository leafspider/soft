package leafspider.util;

import java.io.File;
import java.io.FileFilter;

public class FolderFilter implements FileFilter
{
	public boolean accept( File folder )
	{		
		return folder.isDirectory();
	}

}