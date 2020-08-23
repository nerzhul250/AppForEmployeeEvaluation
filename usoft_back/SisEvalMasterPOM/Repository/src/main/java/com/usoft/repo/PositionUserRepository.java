package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Positionuser;

@Repository
public class PositionUserRepository implements PositionUserRepositoryInt {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Positionuser positionUser) {
		entityManager.persist(positionUser);
	}

	@Override
	public Positionuser update(Positionuser newPositionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(Positionuser deletePositionUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public Positionuser findBy(long positionUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Positionuser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
