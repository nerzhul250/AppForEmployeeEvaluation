package com.usoft.service;

import java.util.ArrayList;

import com.usoft.model.Careerplan;
import com.usoft.model.Evaluation;
import com.usoft.model.Period;

//import com.fasterxml.jackson.databind.JsonNode;

public interface EvaluationServiceInt {
	
	public Evaluation createEvaluation(Evaluation evaluation, int careerPlan, int period, int evaluator, int employee, ArrayList<String[]> criteriaData);
	
	public Iterable<Evaluation> findAllEvaluations();
	
	public Evaluation findEvaluation(int id);
	
	public Iterable<Evaluation> findUserPeriod(long idPeriod, long idUser);
	
	public void deleteEvaluation(int id) throws Exception;
	
	public Iterable<Evaluation> getEvaluationsOfUser(int id);
	
	public Iterable<Evaluation> getEvaluationsByPeriodAndCareerPlan(int period, int careerPlan);

	public Iterable<Evaluation> getEvaluationsByPeriod(int period);

	public Iterable<Evaluation> getEvaluationsByCareerPlan(int careerPlan);
}
