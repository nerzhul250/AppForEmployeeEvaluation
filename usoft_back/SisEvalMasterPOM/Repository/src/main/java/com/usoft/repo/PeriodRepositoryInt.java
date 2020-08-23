package com.usoft.repo;

import com.usoft.model.Period;

public interface PeriodRepositoryInt {

	
	
	public void save(Period period);

	public Iterable<Period> findAll();

	public Period find(Integer id);

	public void delete(Integer id);
	
	public void update(Period period);

}
