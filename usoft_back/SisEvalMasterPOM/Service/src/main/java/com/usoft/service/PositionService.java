package com.usoft.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Position;
import com.usoft.repo.PositionRepository;
import com.usoft.repo.PositionRepositoryInt;

@Service
public class PositionService implements PositionServiceInt{
	/**
	 * Dependency to the DAO for positions
	 */
	private PositionRepository repositoryDao;

	/**
	 *Injection of depencencies
	 * @param positionDao the the dependency to be injected
	 */
	@Autowired
	public PositionService(PositionRepository positionDao) {
		this.repositoryDao=positionDao;
	}
	/**
	 * Method to create a position by calling the save function of the DAO
	 * @param position is the position to be created
	 */
	@Transactional
	@Override
	public void createPosition(Position position) {
		repositoryDao.save(position);
	}
	/**
	 * Method to update a position by calling the update function of the DAO
	 * @param newPosition is the position to be updated
	 * @return the method returns the new position
	 */
	@Transactional
	@Override
	public Position updatePosition(Position newPosition) {
		repositoryDao.update(newPosition);
		return newPosition;
	}
	/**
	 * Method to logically delete a position by calling the delete function of the DAO
	 * @param deletePosition is the position to be deleted
	 */
	@Transactional
	@Override
	public void deletePosition(Position deletePosition) {
		repositoryDao.delete(deletePosition);
	}
	/**
	 * Method to find a position calling the findbyid method of the DAO
	 * @param positionID is the id of the position to be searched
	 * @return is the position found
	 */
	@Transactional
	@Override
	public Position findPosition(long positionID)  {
		System.out.println(repositoryDao);
		return repositoryDao.findBy(positionID);
	}
	/**
	 * Method to get all the positions present in the database by calling the findAll function of the DAO
	 * @return is a list of all the positions found
	 */
	@Transactional
	@Override
	public Iterable<Position> findAll() {
		return repositoryDao.findAll();
	}

	
}
	