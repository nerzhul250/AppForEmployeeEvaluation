package com.usoft.dtos.jira;

public class VelocityChartElement {
	
	private int id;
	private Sprint sprint;
	private int estimated;
	private int completed;
	
	public VelocityChartElement() {
		
	}

	public VelocityChartElement(int id,Sprint sprint, int estimated, int completed) {
		super();
		this.id = id;
		this.sprint = sprint;
		this.estimated = estimated;
		this.completed = completed;
	}
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Sprint getSprint() {
		return sprint;
	}

	public void setSprint(Sprint sprint) {
		this.sprint = sprint;
	}

	public int getEstimated() {
		return estimated;
	}

	public void setEstimated(int estimated) {
		this.estimated = estimated;
	}

	public int getCompleted() {
		return completed;
	}

	public void setCompleted(int completed) {
		this.completed = completed;
	}

}
