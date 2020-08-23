package com.usoft.repo;

import com.usoft.model.Indicator;

public interface IndicatorRepositoryInt {
	public void save(Indicator indicator);
	public void update(Indicator indicator);	
	public Iterable<Indicator> findAll();	
	public Indicator find( Integer id);	
	public void delete( Integer id);	
}
