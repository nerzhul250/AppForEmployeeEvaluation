package com.usoft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Criteria;
import com.usoft.repo.CriteriaRepository;

public class TestCriteriaRepository {

	private CriteriaRepository criteriaRepository;
	
	@BeforeEach
	public void init() {		
		criteriaRepository = new CriteriaRepository();		
	}	
	
	@Test
	@Transactional
	public void save() {
	}

	@Test
	@Transactional
	public void update() {
	}

	@Test
	@Transactional
	public Iterable<Criteria> findAll() {
		return null;
	}

	@Test
	@Transactional
	public Criteria find() {
		return null;
	}

	@Test
	@Transactional
	public void delete() {		
	}
}
