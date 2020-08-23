package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.dtos.PeriodDTO;
import com.usoft.model.Period;
import com.usoft.service.PeriodServiceInt;

@CrossOrigin
@RestController
@RequestMapping("/usoftapi/periods")
public class ApiPeriodController implements ApiPeriodControllerInt {

	@Autowired
	private PeriodServiceInt periodService;
	
	@Override
	@GetMapping("")
	public Iterable<PeriodDTO> findAll(){
		ArrayList<PeriodDTO> allPeriodDTOs = new ArrayList<PeriodDTO>();
		Iterable<Period> allPeriods = periodService.findAllPeriods();
		for (Period period : allPeriods) {
			PeriodDTO dto = new PeriodDTO();
			dto.setId(period.getId());
			dto.setName(period.getName());
			dto.setStartDate(period.getStartDate());
			dto.setEndDate(period.getEndDate());
			dto.setIsDeleted(period.getIsdeleted());
			allPeriodDTOs.add(dto);
		}
		return allPeriodDTOs;
	}

	@Override
	@GetMapping("/{id}")
	public PeriodDTO findPeriod(@PathVariable Integer id) {
		PeriodDTO dto = new PeriodDTO();
		Period period = periodService.findPeriod(id);
		dto.setId(period.getId());
		dto.setName(period.getName());
		dto.setStartDate(period.getStartDate());
		dto.setEndDate(period.getEndDate());
		return dto;
	}
	
	@Override
	@PostMapping("")
	public void createPeriod(@RequestBody PeriodDTO periodDTO) {
		Period newPeriod = new Period();
		newPeriod.setIsdeleted(BigDecimal.ZERO);
		newPeriod.setName(periodDTO.getName());
		newPeriod.setStartDate(periodDTO.getStartDate());
		newPeriod.setEndDate(periodDTO.getEndDate());
		periodService.createPeriod(newPeriod);		
	}
	
	@Override
	@PutMapping("/{id}")
	public void updatePeriod(@PathVariable Integer id, @RequestBody PeriodDTO periodDTO) {
		System.out.println("Received PUT request.");
		Period period = periodService.findPeriod(id);
		period.setName(periodDTO.getName());
		period.setStartDate(periodDTO.getStartDate());
		period.setEndDate(periodDTO.getEndDate());
		periodService.updatePeriod(period);
	}
	
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePeriod(@PathVariable Integer id) {
		try {
			periodService.deletePeriod(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}
	
	
}
