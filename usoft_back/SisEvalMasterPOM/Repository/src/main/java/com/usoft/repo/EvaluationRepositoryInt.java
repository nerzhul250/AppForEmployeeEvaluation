package com.usoft.repo;

import com.usoft.model.Evaluation;

public interface EvaluationRepositoryInt {
	
	public Evaluation save(Evaluation evaluation);
	
	public Iterable<Evaluation> findAll();
	
	public Evaluation find(Integer id);
	
	public Iterable<Evaluation> findUserPeriod(long idPeriod, long idUser);
	
	public void delete(Integer id);
	
	public void update(Evaluation evaluation);

}
