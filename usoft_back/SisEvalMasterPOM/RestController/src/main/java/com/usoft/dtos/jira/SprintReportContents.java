package com.usoft.dtos.jira;

import java.util.ArrayList;

public class SprintReportContents {
	
	private ArrayList<Issue> completedIssues;
	private ArrayList<Issue> issuesNotCompletedInCurrentSprint;
	private StoryPoints completedIssuesEstimateSum;
	private StoryPoints issuesNotCompletedEstimateSum;
	private StoryPoints allIssuesEstimateSum;
	
	public SprintReportContents() {
		
	}

	public SprintReportContents(ArrayList<Issue> completedIssues, ArrayList<Issue> issuesNotCompletedInCurrentSprint,
			StoryPoints completedIssuesEstimateSum, StoryPoints issuesNotCompletedEstimateSum,
			StoryPoints allIssuesEstimateSum) {
		super();
		this.completedIssues = completedIssues;
		this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
		this.completedIssuesEstimateSum = completedIssuesEstimateSum;
		this.issuesNotCompletedEstimateSum = issuesNotCompletedEstimateSum;
		this.allIssuesEstimateSum = allIssuesEstimateSum;
	}

	public ArrayList<Issue> getCompletedIssues() {
		return completedIssues;
	}

	public void setCompletedIssues(ArrayList<Issue> completedIssues) {
		this.completedIssues = completedIssues;
	}

	public ArrayList<Issue> getIssuesNotCompletedInCurrentSprint() {
		return issuesNotCompletedInCurrentSprint;
	}

	public void setIssuesNotCompletedInCurrentSprint(ArrayList<Issue> issuesNotCompletedInCurrentSprint) {
		this.issuesNotCompletedInCurrentSprint = issuesNotCompletedInCurrentSprint;
	}

	public StoryPoints getCompletedIssuesEstimateSum() {
		return completedIssuesEstimateSum;
	}

	public void setCompletedIssuesEstimateSum(StoryPoints completedIssuesEstimateSum) {
		this.completedIssuesEstimateSum = completedIssuesEstimateSum;
	}

	public StoryPoints getIssuesNotCompletedEstimateSum() {
		return issuesNotCompletedEstimateSum;
	}

	public void setIssuesNotCompletedEstimateSum(StoryPoints issuesNotCompletedEstimateSum) {
		this.issuesNotCompletedEstimateSum = issuesNotCompletedEstimateSum;
	}

	public StoryPoints getAllIssuesEstimateSum() {
		return allIssuesEstimateSum;
	}

	public void setAllIssuesEstimateSum(StoryPoints allIssuesEstimateSum) {
		this.allIssuesEstimateSum = allIssuesEstimateSum;
	}
	
	

}
