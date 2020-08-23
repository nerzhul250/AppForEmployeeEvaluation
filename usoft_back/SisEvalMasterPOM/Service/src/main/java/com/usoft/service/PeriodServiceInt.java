package com.usoft.service;

import com.usoft.model.Period;

public interface PeriodServiceInt {
	
	public boolean createPeriod(Period period);
	
	public Iterable<Period> findAllPeriods();
	
	public Period findPeriod(Integer id);

	String updatePeriod(Period period);

	void deletePeriod(Integer id) throws Exception;
	
	

}
