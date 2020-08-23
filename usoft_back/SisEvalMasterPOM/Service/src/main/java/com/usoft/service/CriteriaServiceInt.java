package com.usoft.service;

import java.util.List;

import com.usoft.model.Criteria;
public interface CriteriaServiceInt {
	public void save(Criteria criteria);	
	public void update(Criteria criteria);		
	public Iterable<Criteria> findAll();
	public Criteria find( Integer id);
	public void delete( Integer id) throws Exception;
	//public List<Criteria> findByIndicatorPosition(IndicatorpositionPK id);
	public List<Criteria> findByIndicatorPosition(long plan, long indicator, long position);
}
