package com.usoft.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Careerplan;

@Repository
public class CareerPlanRepository implements CareerPlanRepositoryInt {

	@PersistenceContext
	private EntityManager entityManager;

	/**
	 * This method makes use of the entity manager to save a career plan in the
	 * database
	 * 
	 * @param Careerplan careerPlan
	 * @pre careerPlan != null
	 * @post The careerPlan is added to the database
	 */
	@Override
	public void save(Careerplan careerPlan) {
		entityManager.persist(careerPlan);
	}

	/**
	 * This method makes use of the entity manager to update a career plan in the
	 * database
	 * 
	 * @param Careerplan newCareerPlan
	 * @pre newCareerPlan != null and the career plan with the same ID exists in the
	 *      database
	 * @post The careerPlan with the same of ID as newCareerPlan is updated in the
	 *       database
	 * @return Updated career plan
	 */
	@Override
	public Careerplan update(Careerplan newCareerPlan) {
		return entityManager.merge(newCareerPlan);
	}

	/**
	 * This method makes use of the entity manager to delete a career plan from the
	 * database
	 * 
	 * @param Careerplan deleteCareerPlan
	 * @pre deleteCareerPlan != null and the career plan with the same ID exists in
	 *      the database
	 * @post The careerPlan with the same of ID as deleteCareerPlan doesn't exist in
	 *       the database
	 */
	@Override
	public void delete(Careerplan deleteCareerPlan) {
		//entityManager.remove(deleteCareerPlan);
		System.out.println("Deleting careerplan with id: "+deleteCareerPlan.getId());
		entityManager.merge(deleteCareerPlan);
	}

	/**
	 * This method makes use of the entity manager to find a career plan from the
	 * database
	 * 
	 * @param long careerId
	 * @return A CareerPlan with ID careerID
	 */
	@Override
	public Careerplan findById(long careerId) {
		return entityManager.find(Careerplan.class, careerId);
	}

	/**
	 * This method makes use of the entity manager to find all the career plans from
	 * the database
	 * 
	 * @return An object Iterable<Careerplan> with the complete list of career plans
	 *         that are stored in the database
	 */
	@Override
	public Iterable<Careerplan> findAll() {
		String jpql = "SELECT a FROM Careerplan a WHERE a.isdeleted= 0";
		return 	entityManager.createQuery(jpql).getResultList();
	}

}
