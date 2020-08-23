package com.usoft.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Criteria;
import com.usoft.repo.CriteriaRepositoryInt;

@Service
public class CriteriaService implements CriteriaServiceInt{

	@Autowired
	private CriteriaRepositoryInt repository;

	@Transactional
	@Override
	public void save(Criteria criteria) {
		criteria.setIsdeleted(BigDecimal.ZERO);
		repository.save(criteria);			
	}

	@Transactional
	@Override
	public void update(Criteria criteria) {
		try {
			Criteria criteriaToUpdate = find((int) criteria.getId());
			criteriaToUpdate.setDescription(criteria.getDescription());
			criteriaToUpdate.setPercent(criteria.getPercent());
			
			repository.update(criteriaToUpdate);		
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is a service which purpose is to find all registered criterias
	 */
	@Transactional
	@Override
	public Iterable<Criteria> findAll() {
		return repository.findAll();
	}

	
	/**
	 * This method is a service which purpose is to find an specified (by its id) criteria
	 * @throws Exception 
	 */
	@Transactional
	@Override
	public Criteria find(Integer id){
		return repository.find(id);
	}
	/**
	 * This service call the repository to delete an a criteria by a service
	 */
	@Override
	public void delete(Integer id) throws Exception{
		try {
			Criteria criteria = repository.find(id);
			if(criteria==null) {
				throw new Exception("No criteria registred with id: " + id);
			}else {
				repository.delete(id);
			}
		} catch (Exception e) {
			throw new Exception("No criteria registred with id: " + id);
		}
	}

	@Override
    public List<Criteria> findByIndicatorPosition(long plan, long indicator, long position){
		return repository.findByIndicatorPosition(plan, indicator, position);
	}
}
