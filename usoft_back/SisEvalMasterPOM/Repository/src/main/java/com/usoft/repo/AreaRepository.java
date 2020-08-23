package com.usoft.repo;

import java.math.BigDecimal;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.usoft.model.Area;
import com.usoft.model.Careerplan;

@EnableTransactionManagement
@Repository
@Transactional
public class AreaRepository implements AreaRepositoryInt {
	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	/**
	 * This method makes use of the entity manager to save a Area in the
	 * database
	 * 
	 * @param Area area
	 * @pre Area != null
	 * @post The Area is added to the database
	 */
	@Override
	public void save(Area area) {
		System.out.println(area.getDescription()+" En el repositorio del area");
		entityManager.persist(area);
	}
	
	/**
	 * This method makes use of the entity manager to update a Area in the
	 * database
	 * 
	 * @param Area newArea
	 * @pre Area != null and the area with the same ID exists in the
	 *      database
	 * @post The Area with the same of ID as Area is updated in the
	 *       database
	 * @return Updated Area
	 */
	@Override
	public Area update(Area area) {
		return entityManager.merge(area);
	}

	/**
	 * This method makes use of the entity manager to delete a Area from the
	 * database
	 * 
	 * @param Area area
	 * @pre area != null and the Area with the same ID exists in
	 *      the database
	 * @post The Area with the same of ID as area doesn't exist in
	 *       the database
	 */
	@Override
	public void delete(Area area) {
//		area.setIsdeleted(BigDecimal.ONE);
//		System.out.println(area.getIsdeleted()+"Repository");
//		entityManager.merge(area);
		
		Area r=entityManager.find(Area.class, area.getId());
		r.setIsdeleted(BigDecimal.ONE);
		entityManager.merge(r);
	}
	
	/**
	 * This method makes use of the entity manager to find a Area from the
	 * database
	 * 
	 * @param long areaID
	 * @return A Area with ID AreaID
	 */
	@Override
	public Area findById(long id) {
		return entityManager.find(Area.class, id);
	}

	/**
	 * This method makes use of the entity manager to find all the Areas from
	 * the database
	 * 
	 * @return An object Iterable<Area> with the complete list of Areas
	 *         that are stored in the database
	 */
	@Override
	public Iterable<Area> findAll() {
		String jpql = "SELECT a FROM Area a WHERE isDeleted=0";
		return 	entityManager.createQuery(jpql).getResultList();
	}


}
