package com.usoft.rest;

import org.springframework.http.ResponseEntity;

import com.usoft.dtos.PeriodDTO;

public interface ApiPeriodControllerInt {

	Iterable<PeriodDTO> findAll();

	PeriodDTO findPeriod(Integer id);

	void createPeriod(PeriodDTO periodDTO);

	void updatePeriod(Integer id, PeriodDTO periodDTO);

	ResponseEntity<?> deletePeriod(Integer id);

}
