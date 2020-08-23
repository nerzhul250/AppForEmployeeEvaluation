package com.usoft.dtos.jira;

public class StatFieldValue {
	
	private int value;
	
	public StatFieldValue() {
		
	}

	public StatFieldValue(int value) {
		super();
		this.value = value;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
	
}
