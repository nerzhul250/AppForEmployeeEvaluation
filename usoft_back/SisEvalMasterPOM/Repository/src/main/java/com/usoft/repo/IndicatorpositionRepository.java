package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.usoft.model.Indicatorposition;

@Repository
@EnableTransactionManagement
@Transactional
public class IndicatorpositionRepository implements IndicatorpositionRepositoryInt{
	
	@PersistenceContext(type = PersistenceContextType.TRANSACTION) 
	private EntityManager entityManager;

	@Override
	public void save(Indicatorposition indicatorposition) {
		entityManager.persist(indicatorposition);
	}

	/**
	 * This method purpose is to update an indicatorposition from the database
	 */
	@Override
	public void update(Indicatorposition indicatorposition) {
		entityManager.merge(indicatorposition);
	}

	/**
	 * This method purpose is to find all indicatorpositions form the database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Indicatorposition> findAll() {
		String jpql = "SELECT i FROM Indicatorposition i";
		return entityManager.createQuery(jpql).getResultList();
	}

	/**
	 * This method purpose is to find an specified indicatorposition (by its id) form the database
	 */
	@Override
	public Indicatorposition findById(Integer id) {
		return entityManager.find(Indicatorposition.class, (long) id);
	}

	/**
	 * This method remove the indicatorposition by id
	 */
	@Override
	public void delete(Integer id) {
//		entityManager.remove(this.findById(id));
		
	}

}
