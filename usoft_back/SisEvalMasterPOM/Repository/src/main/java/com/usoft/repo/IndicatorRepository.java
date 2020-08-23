package com.usoft.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.usoft.model.Indicator;

@Repository
@EnableTransactionManagement
@Transactional
public class IndicatorRepository implements IndicatorRepositoryInt {

	@PersistenceContext(type = PersistenceContextType.TRANSACTION) 
	private EntityManager entityManager;
	
	@Override
	public void save(Indicator entity) {
		entityManager.persist(entity);		
	}	

	/**
	 * This method purpose is to update an indicators from the database
	 */
	@Override
	public void update(Indicator indicator) {
		entityManager.merge(indicator);		
	}
	
	
	/**
	 * This method purpose is to find all indicators form the database
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Iterable<Indicator> findAll() {
		String jpql = "SELECT a FROM Indicator a WHERE isDeleted=0";
		return 	entityManager.createQuery(jpql).getResultList();
	}

	
	/**
	 * This method purpose is to find an specified indicator (by its id) form the database
	 */
	@Override
	public Indicator find(Integer id) {
		Indicator ind=entityManager.find(Indicator.class, (long) id);
		if(ind.getIsdeleted().equals(BigDecimal.ONE)) {
			return null;
		}else {
			return ind;
		}
	}
	/**
	 * This method remove the indicator by id
	 */
	@Override
	public void delete(Integer id) {
		Indicator element= entityManager.find(Indicator.class, (long) id);
		element.setIsdeleted(BigDecimal.ONE);
		entityManager.merge(element);	
//		entityManager.remove(this.find(id));
	}

}
