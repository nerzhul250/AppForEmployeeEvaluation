package com.usoft.rest;

import org.springframework.http.ResponseEntity;

import com.usoft.dtos.IndicatorDTO;
import com.usoft.model.Indicator;

public interface ApiIndicatorControllerInt {
	public String save(Indicator indicator);	
	public String update(IndicatorDTO indicator);		
	public Iterable<IndicatorDTO> findAll();	
	public IndicatorDTO find( Integer id);	
	public ResponseEntity<?> delete( Integer id);
}
