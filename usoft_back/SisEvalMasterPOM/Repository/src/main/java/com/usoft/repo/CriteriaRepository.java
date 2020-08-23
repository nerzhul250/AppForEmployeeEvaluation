package com.usoft.repo;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.usoft.model.Criteria;
@Repository
@EnableTransactionManagement
@Transactional
public class CriteriaRepository implements CriteriaRepositoryInt{

	@PersistenceContext(type = PersistenceContextType.TRANSACTION) 
	private EntityManager entityManager;
	
	@Override
	public void save(Criteria criteria) {
		entityManager.persist(criteria);		
	}

	@Override
	public void update(Criteria criteria) {
		entityManager.merge(criteria);			
	}

	/**
	 * This method purpose is to find all criterias form the database
	 */
	@Override
	public Iterable<Criteria> findAll() {
		String jpql = "SELECT c FROM Criteria c where c.isDeleted=0";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	
	/**
	 * This method purpose is to find an specified criteria (by its id) form the database
	 */
	@Override
	public Criteria find(Integer id) {
		return entityManager.find(Criteria.class, (long) id);
	}
	/**
	 * This method remove and criteria by id
	 */
	@Override
	public void delete(Integer id) {
		Criteria element= entityManager.find(Criteria.class, (long) id);
		element.setIsdeleted(BigDecimal.ONE);
		entityManager.merge(element);
//		entityManager.remove(this.find(id));		
	}

	/*
	@Override
	public List<Criteria> findByIndicatorPosition(IndicatorpositionPK id) {
		String jpql = "SELECT c FROM Criteria c where c.indicatorposition =" + id + "";
		return entityManager.createQuery(jpql).getResultList();
	}*/
	
	@Override
	public List<Criteria> findByIndicatorPosition(long plan, long indicator, long position){
		String jpql = "SELECT c FROM Criteria c JOIN c.indicatorposition ip JOIN ip.careerplan pl JOIN ip.indicator i JOIN ip.position po WHERE pl.id=:plan AND i.id=:indicator AND po.id=:position";
	return entityManager.createQuery(jpql, Criteria.class)
			.setParameter("plan", plan)
			.setParameter("indicator", indicator)
			.setParameter("position", position)
			.getResultList();	
	}
	
}
