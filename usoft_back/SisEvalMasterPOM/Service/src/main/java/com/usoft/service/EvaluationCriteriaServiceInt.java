package com.usoft.service;

import com.usoft.model.Evaluation;
import com.usoft.model.Evaluationcriteria;
import com.usoft.model.EvaluationcriteriaPK;

public interface EvaluationCriteriaServiceInt {

	public boolean createEvaluationCriteria(Evaluationcriteria evaluationCriteria);

	public Iterable<Evaluationcriteria> findAllEvaluationcriteria();

	public Evaluationcriteria findEvaluationCriteria(EvaluationcriteriaPK id);

	public void deleteEvaluationCriteria(EvaluationcriteriaPK id);

}
