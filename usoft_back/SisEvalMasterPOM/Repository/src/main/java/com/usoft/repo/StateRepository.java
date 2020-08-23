package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Area;
import com.usoft.model.State;


@Repository
public class StateRepository implements StateRepositoryInt {
	
	@PersistenceContext
	private EntityManager entityManager;

	
	@Override
	public void save(State state) {
		entityManager.persist(state);
	}

	@Override
	public State update(State state) {
		return entityManager.merge(state);
	}

	@Override
	public void delete(State state) {
		entityManager.remove(state);
	}

	@Override
	public State findById(long id) {
		return entityManager.find(State.class, id);
	}

	@Override
	public Iterable<State> findAll() {
		String jpql = "SELECT a FROM State a";
		return 	entityManager.createQuery(jpql).getResultList();
	}

}
