package com.usoft.dtos.jira;

public class CurrentEstimateStatistic {
	
	private String statFieldId;
	private StatFieldValue statFieldValue;
	
	public CurrentEstimateStatistic() {
		
	}

	public CurrentEstimateStatistic(String statFieldId, StatFieldValue statFieldValue) {
		super();
		this.statFieldId = statFieldId;
		this.statFieldValue = statFieldValue;
	}

	public String getStatFieldId() {
		return statFieldId;
	}

	public void setStatFieldId(String statFieldId) {
		this.statFieldId = statFieldId;
	}

	public StatFieldValue getStatFieldValue() {
		return statFieldValue;
	}

	public void setStatFieldValue(StatFieldValue statFieldValue) {
		this.statFieldValue = statFieldValue;
	}
	
}
