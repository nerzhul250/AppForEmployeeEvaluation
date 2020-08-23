package com.usoft.dtos.jira;

public class SprintReport {
	
	private SprintReportContents contents;
	private Sprint sprint;
	private String userWhoStarted;
	
	public SprintReport() {
		
	}

	public SprintReport(SprintReportContents contents, Sprint sprint, String userWhoStarted) {
		super();
		this.contents = contents;
		this.sprint = sprint;
		this.userWhoStarted = userWhoStarted;
	}

	public SprintReportContents getContents() {
		return contents;
	}

	public void setContents(SprintReportContents contents) {
		this.contents = contents;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public String getUserWhoStarted() {
		return userWhoStarted;
	}

	public void setUserWhoStarted(String userWhoStarted) {
		this.userWhoStarted = userWhoStarted;
	}
	

}
