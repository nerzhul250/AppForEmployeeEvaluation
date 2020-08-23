package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Indicator;
import com.usoft.repo.IndicatorRepositoryInt;

@Service
public class IndicatorService implements IndicatorServiceInt {

	@Autowired
	private IndicatorRepositoryInt repository;
	
	@Transactional
	@Override
	public boolean save(Indicator indicator) {
		try {
			repository.save(indicator);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}
	
	@Transactional
	@Override
	public boolean update(Indicator indicator) {
		try {
			repository.update(indicator);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	/**
	 * This method is a service which purpose is to find all registered indicators
	 */
	@Transactional
	@Override
	public Iterable<Indicator> findAll() {
		return repository.findAll();
	}

	
	/**
	 * This method is a service which purpose is to find an specified (by its id) indicator
	 */
	@Transactional
	@Override
	public Indicator find(Integer id) throws Exception {
		try {
			Indicator indi = repository.find(id);
			if(indi==null) {
				throw new Exception("No indicator registred with id: " + id);
			}else {
				return indi;
			}
		} catch (Exception e) {
			throw new Exception("No indicator registred with id: " + id);
		}
	}
	/**
	 * This method responses the api request by deleteMapping, to remove an indicator
	 */
	@Transactional
	@Override
	public void delete(Integer id)throws Exception {
		try {
			Indicator indi = repository.find(id);
			if(indi==null) {
				throw new Exception("No indicator registred with id: " + id);
			}else {
				repository.delete(id);	
			}
		} catch (Exception e) {
			throw new Exception("No indicator registred with id: " + id);
		}
	}

}
