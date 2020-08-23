package com.usoft.service;

import com.usoft.model.Indicator;

public interface IndicatorServiceInt {
	public boolean save(Indicator indicator);	
	public boolean update(Indicator indicator);		
	public Iterable<Indicator> findAll();	
	public Indicator find( Integer id) throws Exception;	
	public void delete( Integer id) throws Exception;	
}
