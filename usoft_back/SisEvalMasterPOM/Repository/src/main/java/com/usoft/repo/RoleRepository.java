package com.usoft.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.usoft.model.Role;
import com.usoft.model.Usuario;

@Repository
public class RoleRepository implements RoleRepositoryInt {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public void save(Role role) {
		entityManager.persist(role);
	}

	@Override
	public void update(Role role) {
		entityManager.merge(role);

	}

	
	/**
	 * This method purpose is to find all roles form the database
	 */
	@Override
	public Iterable<Role> findAll() {
		String jpql = "SELECT a FROM Role a where isDeleted=0";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	/**
	 * This method purpose is to find an specified role (by its id) form the database
	 */
	@Override
	public Role find(Long id) {
		return entityManager.find(Role.class,id);
	}
	/**
	 * This method remove the role by id
	 */
	@Override
	public void delete(Long id) {
		Role element= entityManager.find(Role.class,id);
		element.setIsdeleted(BigDecimal.ONE);
		entityManager.merge(element);	
	}

}
