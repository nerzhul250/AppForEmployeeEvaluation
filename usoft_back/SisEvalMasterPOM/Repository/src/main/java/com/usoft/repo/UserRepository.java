package com.usoft.repo;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.usoft.model.Usuario;

@Repository
public class UserRepository implements UserRepositoryInt{
	
	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void save(Usuario user) {
		entityManager.persist(user);
	}

	/**
	 * This method purpose is to find all users form the database
	 */
	@Override
	public Iterable<Usuario> findAll() {
		String jpql = "SELECT a FROM Usuario a where isDeleted=0";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	/**
	 * This method purpose is to find an specified user (by its id) form the database
	 */
	@Override
	public Usuario find(Integer id) {
		long theID = (long) id;
		return entityManager.find(Usuario.class,theID);
	}
	/**
	 * This method remove the user from the list of users 
	 */
	@Override
	public void delete(Integer id) {
		entityManager.remove(this.find(id));
	}
	
	@Override
	public void update(Usuario user) {
		entityManager.merge(user);
	}

	public Usuario findByEmail(String email) {
		TypedQuery<Usuario> query = entityManager.createQuery(
		        "SELECT u FROM Usuario u WHERE u.email = :email", Usuario.class);
		    return query.setParameter("email",email).getSingleResult();
	}

}

