package com.usoft.rest;

import org.springframework.http.ResponseEntity;

import com.usoft.dtos.CriteriaDTO;
import com.usoft.model.Criteria;

public interface ApiCriteriaControllerInt {
	public void save(Criteria criteria);	
	public void update(Criteria criteria);		
	public Iterable<CriteriaDTO> findAll();	
	public CriteriaDTO find( Integer id);	
	public ResponseEntity<?> delete( Integer id);
}
