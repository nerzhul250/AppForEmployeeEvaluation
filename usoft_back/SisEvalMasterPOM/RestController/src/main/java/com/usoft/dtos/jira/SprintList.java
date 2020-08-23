package com.usoft.dtos.jira;

import java.util.ArrayList;

public class SprintList {
	
	private ArrayList<Sprint> values;
	
	public SprintList() {
		
	}

	public SprintList(ArrayList<Sprint> values) {
		super();
		this.values = values;
	}

	public ArrayList<Sprint> getValues() {
		return values;
	}

	public void setValues(ArrayList<Sprint> values) {
		this.values = values;
	}
	

}
