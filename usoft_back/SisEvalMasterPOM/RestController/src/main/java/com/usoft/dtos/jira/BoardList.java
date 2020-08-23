package com.usoft.dtos.jira;

import java.util.ArrayList;

public class BoardList {
	
	private ArrayList<Board> values;
	
	public BoardList() {
		
	}

	public BoardList(ArrayList<Board> values) {
		super();
		this.values = values;
	}

	public ArrayList<Board> getValues() {
		return values;
	}

	public void setValues(ArrayList<Board> values) {
		this.values = values;
	}
	
	

}
