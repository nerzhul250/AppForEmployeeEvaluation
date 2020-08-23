package com.usoft;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.transaction.annotation.Transactional;
import com.usoft.model.Indicator;
import com.usoft.repo.IndicatorRepository;

public class TestIndicatorRepository {

	private IndicatorRepository indicatorRepository;
	
	@BeforeEach
	public void init() {		
		indicatorRepository = new IndicatorRepository();		
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
	public Iterable<Indicator> findAll() {
		return null;
	}

	@Test
	@Transactional
	public Indicator find() {
		return null;
	}

	@Test
	@Transactional
	public void delete() {		
	}
}

