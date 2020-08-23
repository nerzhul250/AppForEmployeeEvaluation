package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Period;

@Repository
public class PeriodRepository implements PeriodRepositoryInt {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public void save(Period period) {
		entityManager.persist(period);
	}

	@Override
	public Iterable<Period> findAll() {
		String jpql = "SELECT p FROM Period p Where isDeleted=0";
		return entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public Period find(Integer id) {
		long theId = (long) id;
		return entityManager.find(Period.class, theId);
	}

	@Override
	public void delete(Integer id) {
		Period p = find(id);
		if (p != null) {
			entityManager.remove(p);
		}
	}

	@Override
	public void update(Period period) {
		entityManager.merge(period);
	}

}
