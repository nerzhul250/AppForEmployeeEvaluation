package com.usoft.rest;

import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;

import com.usoft.dtos.EvaluationDTO;
import com.usoft.model.Evaluation;

public interface ApiEvaluationControllerInt {

	public Iterable<Evaluation> findAllEvaluations();
	
	public Iterable<Evaluation> getEvaluationsByPeriodAndCareerPlan(long period, long careerplan);

	public HashMap<String,EvaluationDTO> getEvaluationsByPeriod(long period);

//	public HashMap<String,ArrayList<EvaluationDTO>> getEvaluationsByCareerPlan(long careerplan);
	
	public HashMap<String,HashMap<String,EvaluationDTO>> getEvaluationsByCareerPlan(long careerplan);
	
	//	public Iterable<Evaluation> getEvaluationsOfUser(Integer id);

	public HashMap<String,EvaluationDTO> getEvaluationsOfUser(Integer id);
	
	public String createEvaluation(EvaluationDTO evaluation);
	
	public ResponseEntity<?> deleteEvaluation(@PathVariable Integer id);
}
