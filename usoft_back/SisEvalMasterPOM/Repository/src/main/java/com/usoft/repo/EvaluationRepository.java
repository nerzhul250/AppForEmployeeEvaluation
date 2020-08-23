package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Criteria;
import com.usoft.model.Evaluation;

@Repository
public class EvaluationRepository implements EvaluationRepositoryInt{

	@PersistenceContext
	private EntityManager entityManager;
	
	/**
	 * Save an Evaluation entity in the Database.
	 */
	@Override
	public Evaluation save(Evaluation evaluation) {
		entityManager.persist(evaluation);
		return evaluation;
	}

	/**
	 * Return an iterable list of all the Evaluations in the Database.
	 */
	@Override
	public Iterable<Evaluation> findAll() {
		String jpql = "SELECT e FROM Evaluation e Where isDeleted=0";
		return entityManager.createQuery(jpql).getResultList();
	}

	/**
	 * Find an Evaluation on the Database by its ID number.
	 */
	@Override
	public Evaluation find(Integer id) {
		long theId = (long) id;
		return entityManager.find(Evaluation.class, id);
	}
	
	/**
	 * Find an Evaluation on the Database by its ID number.
	 */
	@Override
	public Iterable<Evaluation> findUserPeriod(long idPeriod, long idUser) {
		
		String jpql = "SELECT e FROM Evaluation e JOIN e.usuario2 u JOIN e.period p WHERE u.id=:user_id AND p.id=:period_id";
		return entityManager.createQuery(jpql, Evaluation.class)
			.setParameter("user_id", idUser)
			.setParameter("period_id", idPeriod)
			.getResultList();			
		
//		String jpql = "SELECT c FROM Criteria c JOIN c.indicatorposition ip JOIN ip.careerplan pl JOIN ip.indicator i JOIN ip.position po WHERE pl.id=:plan AND i.id=:indicator AND po.id=:position";
//	return entityManager.createQuery(jpql, Criteria.class)
//			.setParameter("plan", plan)
//			.setParameter("indicator", indicator)
//			.setParameter("position", position)
//			.getResultList();	
//	
//	WHERE e.USER_ID=:user_id AND e.period.id=:period_id
		
		
	}

	/**
	 * Find an Evaluation on the Database by its ID number.
	 */
	@Override
	public void delete(Integer id) {
		Evaluation e = find(id);
		if(e!=null)
			entityManager.remove(e);
		
	}

	@Override
	public void update(Evaluation evaluation) {
		entityManager.merge(evaluation);
	}
	
	//TODO Make an Update method that uses entityManager.merge AND put it in the interface.

}
