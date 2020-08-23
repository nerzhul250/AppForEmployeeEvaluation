package com.usoft.dtos.jira;

public class Sprint {
	
	private int id;
	private String name;
	private String state;
	private String goal;
	private String startDate;
	private String endDate;
	private String completeDate;
	
	public Sprint() {
		
	}
	
	public Sprint(int id, String name, String state, String goal, String startDate, String endDate,
			String completeDate) {
		super();
		this.id = id;
		this.name = name;
		this.state = state;
		this.goal = goal;
		this.startDate = startDate;
		this.endDate = endDate;
		this.completeDate = completeDate;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getGoal() {
		return goal;
	}

	public void setGoal(String goal) {
		this.goal = goal;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getCompleteDate() {
		return completeDate;
	}

	public void setCompleteDate(String completeDate) {
		this.completeDate = completeDate;
	}
	

}
