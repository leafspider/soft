package soft.report;

import java.io.File;
import java.util.Calendar;

import leafspider.util.ServerContext;

public class Folders 
{
	public static File heatFolder()
	{
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\heat");
	}

	public static File viperFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\viper");
	}

	public static File porterFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\porter");
	}

	public static File reportFolder()
	{
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report");
	}

	public static File futuresFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\futures" );
	}

	public static File indicesFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\indices" );
	}
	
	public static File forexFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\forex" );
	}

	public static File stratvolFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\stratvol");
	}

	public static File etfsFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\etfs");
	}

	public static File performanceFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\report\\performance");
	}

	public static File toffeeFolder() {
		return new File( ServerContext.getDataFolder().getAbsolutePath() + "\\toffee");
	}

	/* ------ LISTS --------- */
	
	public static File fixedConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\fixed");
	}

	public static File variableConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\variable");
	}

	public static File batchConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\batch");
	}

	public static File reportConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\report");
	}

	public static File toffeeConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\toffee");
	}

	public static File stratvolConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\stratvol");
	}

	public static File viperConfigFolder() {
		//return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\viper");
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\batch");
	}

	public static File rulesConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\rules");
	}

	public static File porterConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\batch");
	}

	public static File etfsConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\etfs");
	}

	public static File formulaConfigFolder() {
		return new File( ServerContext.getListsFolder().getAbsolutePath() + "\\formula");
	}

}
