package com.usoft.dtos.jira;

public class Issue {
	
	private int id;
	private String key;
	private int typeId;
	private String summary;
	private int priorityId;
	private String assignee;
	private CurrentEstimateStatistic currentEstimateStatistic;
	private int statusId;
	
	public Issue() {
		
	}

	public Issue(int id, String key, int typeId, String summary, int priorityId, String assignee,
			CurrentEstimateStatistic currentEstimateStatistic, int statusId) {
		super();
		this.id = id;
		this.key = key;
		this.typeId = typeId;
		this.summary = summary;
		this.priorityId = priorityId;
		this.assignee = assignee;
		this.currentEstimateStatistic = currentEstimateStatistic;
		this.statusId = statusId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getPriorityId() {
		return priorityId;
	}

	public void setPriorityId(int priorityId) {
		this.priorityId = priorityId;
	}

	public String getAssignee() {
		return assignee;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public CurrentEstimateStatistic getCurrentEstimateStatistic() {
		return currentEstimateStatistic;
	}

	public void setCurrentEstimateStatistic(CurrentEstimateStatistic currentEstimateStatistic) {
		this.currentEstimateStatistic = currentEstimateStatistic;
	}

	public int getStatusId() {
		return statusId;
	}

	public void setStatusId(int statusId) {
		this.statusId = statusId;
	}

	
}
