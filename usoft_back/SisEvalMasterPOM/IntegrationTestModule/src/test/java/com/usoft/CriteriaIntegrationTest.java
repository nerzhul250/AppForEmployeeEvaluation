package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.usoft.application.Application;
import com.usoft.model.Criteria;
import com.usoft.service.CriteriaService;

@SpringBootTest(classes = Application.class)
public class CriteriaIntegrationTest {
	
	@Autowired
	private CriteriaService criteriaService;
	
	@Test
	public void saveCriteriaSuccess() throws Exception {
		
	}
	
	@Test
	public void findAllCriterias() {
		Iterable<Criteria> allcriteria=criteriaService.findAll();
        ArrayList<Criteria> list = new ArrayList<>(); 
        allcriteria.forEach(list::add);
        assertTrue(list.size()>0);
	}

	@Test
	public void findCriteriaFailure() {
		Criteria cri = criteriaService.find(1028);
		assertNull(cri);
	}
	
	
	/*@Test
	public void findCriteriaSuccess() {
		Criteria cri = criteriaService.find(226);
		assertNotNull(cri);
	}*/
	@Transactional
	@Test
	public void deleteCriteriaSuccess() {
		
	}
	@Transactional
	@Test
	public void deleteCriteriaFailure() {
		
	}

}
