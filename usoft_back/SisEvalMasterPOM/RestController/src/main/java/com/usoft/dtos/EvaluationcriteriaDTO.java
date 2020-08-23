package com.usoft.dtos;

import java.io.Serializable;

public class EvaluationcriteriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String comment;
	private int criteria_id;
	private int value;
	
	@Override
	public String toString() {
		return "EvaluationcriteriaDTO [comment=" + comment + ", criteria_id=" + criteria_id + ", value=" + value + "]";
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public int getCriteria_id() {
		return criteria_id;
	}
	public void setCriteria_id(int criteria_id) {
		this.criteria_id = criteria_id;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	
	
	

}
