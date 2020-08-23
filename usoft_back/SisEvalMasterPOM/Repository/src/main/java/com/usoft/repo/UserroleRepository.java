package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Userrole;
import com.usoft.model.UserrolePK;
import com.usoft.model.Usuario;

@Repository
public class UserroleRepository implements UserroleRepositoryInt{
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Userrole userRole) {
		entityManager.persist(userRole);
	}

	@Override
	public Iterable<Userrole> findAll() {
		String jpql = "SELECT a FROM Userrole a";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	@Override
	public Userrole find(UserrolePK id) {
		return entityManager.find(Userrole.class,id);
	}

	@Override
	public void delete(UserrolePK id) {
		entityManager.remove(this.find(id));
	}

}
