package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Evaluation;
import com.usoft.model.Evaluationcriteria;
import com.usoft.model.EvaluationcriteriaPK;
import com.usoft.repo.EvaluationCriteriaRepositoryInt;

@Service
public class EvaluationCriteriaService implements EvaluationCriteriaServiceInt {

	@Autowired
	private EvaluationCriteriaRepositoryInt evaluationCriteriaRepo;
	
	@Override
	public boolean createEvaluationCriteria(Evaluationcriteria evaluationCriteria) {
		try {
			evaluationCriteriaRepo.save(evaluationCriteria);
			System.out.println("works");
			return true;
		} catch (Exception e) {
			System.out.println("doesnt work");
		}
		return false;
	}

	@Override
	public Iterable<Evaluationcriteria> findAllEvaluationcriteria() {
		return evaluationCriteriaRepo.findAll();
	}

	@Override
	public Evaluationcriteria findEvaluationCriteria(EvaluationcriteriaPK id) {
		return evaluationCriteriaRepo.find(id);
	}

	@Override
	public void deleteEvaluationCriteria(EvaluationcriteriaPK id) {
		evaluationCriteriaRepo.delete(id);
		
	}

}
