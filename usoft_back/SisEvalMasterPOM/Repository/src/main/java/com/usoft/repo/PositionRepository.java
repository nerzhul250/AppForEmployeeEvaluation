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
import com.usoft.model.Position;
import com.usoft.model.Usuario;
@EnableTransactionManagement
@Repository
@Transactional
public class PositionRepository implements PositionRepositoryInt {

	@PersistenceContext(type = PersistenceContextType.TRANSACTION)
	private EntityManager entityManager;

	/**
	 * This method makes use of the entity manager to save the position in the data base
	 *@param Position position
	 *@pre position!=null
	 *@post the position is added to the data base
	 */
	@Override
	public void save(Position position) {
		entityManager.persist(position);
	}

	/**
	 * This method makes use of the entity manager to update a position in the database
	 * 
	 * @param Position newPosition
	 * @pre newPosition != null and the position with the same ID exists in the database
	 * @post The position with the same of ID as newPosition is updated in the database
	 * @return Updated position
	 */
	@Override
	public Position update(Position newPosition) {
		return entityManager.merge(newPosition);
	}
	/**
	 * This method makes use of the entity manager to delete a position from the database
	 * 
	 * @param position deletePosition
	 * @pre deletePosition != null and the position with the same ID exists in the database
	 * @post The position with the same of ID as deletePosition doesn't exist in the database
	 */
		
	@Override
	public void delete(Position deletePosition) {
		deletePosition.setIsdeleted(new BigDecimal(1));
		entityManager.merge(deletePosition);
	}
	/**
	 * This method makes use of the entity manager to find a position from the database
	 * 
	 * @param long positionID
	 * @return A position with ID positionID
	 */
	@Override
	public Position findBy(long positionID) {
		return entityManager.find(Position.class, positionID);
	}
	/**
	 * This method makes use of the entity manager to find all the positions from the database
	 * 
	 * @return An object Iterable<Position> with the complete list of positions that are stored in the database
	 */
	@Override
	public Iterable<Position> findAll() {
		String jpql = "SELECT a FROM Position a WHERE isDeleted= 0";
		return 	entityManager.createQuery(jpql).getResultList();
	}


}
