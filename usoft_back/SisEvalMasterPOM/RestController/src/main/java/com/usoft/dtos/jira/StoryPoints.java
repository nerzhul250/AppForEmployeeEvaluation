package com.usoft.dtos.jira;

public class StoryPoints {
	
	private int value;
	private String text;
	
	public StoryPoints() {
		
	}

	public StoryPoints(int value, String text) {
		super();
		this.value = value;
		this.text = text;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
