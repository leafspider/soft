package soft.portfolio;

import leafspider.db.DatabaseRecord;

public class Portfolio extends DatabaseRecord
{
	private String projectName = "portfolio";
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getProjectName()
	{ 
		return projectName;
	}

	private String portfolio = null;
	public String getPortfolio() {
		return portfolio;
	}
	public void setPortfolio(String portfolio) {
		this.portfolio = portfolio;
	}
	
}
