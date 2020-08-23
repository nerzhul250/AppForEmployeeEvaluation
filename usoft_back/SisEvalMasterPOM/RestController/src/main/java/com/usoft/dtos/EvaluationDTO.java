package com.usoft.dtos;

import java.io.Serializable;
import java.util.ArrayList;

public class EvaluationDTO implements Serializable {

	private static final long serialVersionUID = -6117703056065238685L;
	
	private String comment;
	
	private int id;
	private int careerPlan;
	private int period;
	private int evaluator;
	private int employee;
	
	private ArrayList<EvaluationcriteriaDTO> criteria;

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public int getCareerPlan() {
		return careerPlan;
	}

	public void setCareerPlan(int careerPlan) {
		this.careerPlan = careerPlan;
	}

	public int getPeriod() {
		return period;
	}

	public void setPeriod(int period) {
		this.period = period;
	}

	public int getEvaluator() {
		return evaluator;
	}

	public void setEvaluator(int evaluator) {
		this.evaluator = evaluator;
	}

	public int getEmployee() {
		return employee;
	}

	public void setEmployee(int employee) {
		this.employee = employee;
	}

	public ArrayList<EvaluationcriteriaDTO> getCriteria() {
		return criteria;
	}

	public void setCriteria(ArrayList<EvaluationcriteriaDTO> criteria) {
		this.criteria = criteria;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "EvaluationDTO [comment=" + comment + ", careerPlan=" + careerPlan + ", period=" + period
				+ ", evaluator=" + evaluator + ", employee=" + employee + ", criteria=" + criteria + "]";
	}
	
	

	
	
	
	
}
