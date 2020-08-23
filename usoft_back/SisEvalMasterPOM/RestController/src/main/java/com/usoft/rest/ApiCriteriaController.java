package com.usoft.rest;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.dtos.CriteriaDTO;
import com.usoft.dtos.CriteriaDTO;
import com.usoft.model.Criteria;
import com.usoft.model.Criteria;
import com.usoft.service.CriteriaServiceInt;

@CrossOrigin(origins = { "*" }, methods = { RequestMethod.OPTIONS, RequestMethod.GET, RequestMethod.POST,
		RequestMethod.PUT, RequestMethod.PATCH, RequestMethod.DELETE })
@RestController
@RequestMapping("/usoftapi/criteria")
public class ApiCriteriaController implements ApiCriteriaControllerInt {

	@Autowired
	private CriteriaServiceInt service;

	@Override
	@PostMapping("")
	public void save(@RequestBody Criteria criteria) {
		service.save(criteria);
	}

	@Override
	@PutMapping("")
	public void update(@RequestBody Criteria criteria) {
		service.update(criteria);
	}

	/**
	 * This method exposes a GET rest service which purpose is to return all the
	 * registered criterias
	 */
	@Override
	@GetMapping("")
	public Iterable<CriteriaDTO> findAll() {
		Iterable<Criteria> allCriterias = service.findAll();
		List<CriteriaDTO> allCriteriasDTO = new ArrayList<CriteriaDTO>();
		Iterator<Criteria> criteriaIterator = allCriterias.iterator();

		while (criteriaIterator.hasNext()) {
			Criteria criter = criteriaIterator.next();
			CriteriaDTO criDTO = new CriteriaDTO();
			criDTO.setId(criter.getId());
			criDTO.setDescription(criter.getDescription());
			criDTO.setPercent(criter.getPercent());
			allCriteriasDTO.add(criDTO);
		}

		return allCriteriasDTO;
	}

	/**
	 * This method exposes a GET rest service which purpose is to return a specified
	 * (by its id) criteria
	 */
	@Override
	@GetMapping("/{id}")
	public CriteriaDTO find(@PathVariable(value = "id") Integer id) {
		try {
			Criteria criteria = service.find(id);
			CriteriaDTO dto = new CriteriaDTO();
			dto.setId(criteria.getId());
			dto.setDescription(criteria.getDescription());
			dto.setPercent(criteria.getPercent());
			return dto;
		} catch (Exception e) {
			return null;
		}

	}

	/**
	 * This method responses the api request by deleteMapping, to remove an
	 * indicator
	 */
	@Override
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable(value = "id") Integer id) {
		try {
			System.out.println(">> Borrando criteria: "+id+" isDeleted=1");
			service.delete(id);
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
		return ResponseEntity.noContent().build();
	}

}
