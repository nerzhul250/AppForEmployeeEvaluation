package com.usoft.repo;

import com.usoft.model.Criteria;
import com.usoft.model.Evaluationcriteria;
import com.usoft.model.EvaluationcriteriaPK;

public interface EvaluationCriteriaRepositoryInt {
	
	public void save(Evaluationcriteria evaluationCriteria);
	
	public Iterable<Evaluationcriteria> findAll();
	
	public Evaluationcriteria find(EvaluationcriteriaPK id);
	
	public void delete(EvaluationcriteriaPK id);
	
	public void update(Evaluationcriteria evaluationcriteria);	

}
