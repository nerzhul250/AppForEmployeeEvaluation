package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Evaluationcriteria;
import com.usoft.model.EvaluationcriteriaPK;

@Repository
public class EvaluationCriteriaRepository implements EvaluationCriteriaRepositoryInt {

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Save an EvaluationCriteria entity in the Database.
	 */
	@Override
	public void save(Evaluationcriteria evaluationCriteria) {
		entityManager.persist(evaluationCriteria);
		
	}

	/**
	 * Return a list of all EvaluationCriteria inside the Database.
	 */
	@Override
	public Iterable<Evaluationcriteria> findAll() {
		String jpql = "SELECT e FROM Evaluationcriteria e";
		return entityManager.createQuery(jpql).getResultList();
	}

	/**
	 * Find an EvaluationCriteria from the Database using its ID
	 */
	@Override
	public Evaluationcriteria find(EvaluationcriteriaPK id) {
		return entityManager.find(Evaluationcriteria.class, id);
	}

	/**
	 * Delete an EvaluationCriteria from the Database using its ID
	 */
	@Override
	public void delete(EvaluationcriteriaPK id) {
		Evaluationcriteria e = find(id);
		if(e!=null)
			entityManager.remove(e);
		
	}

	@Override
	public void update(Evaluationcriteria evaluationcriteria) {
		entityManager.merge(evaluationcriteria);
	}

}
