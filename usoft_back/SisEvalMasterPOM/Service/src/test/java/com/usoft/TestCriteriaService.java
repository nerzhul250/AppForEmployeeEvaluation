package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Criteria;
import com.usoft.model.Criteria;
import com.usoft.repo.CriteriaRepository;
import com.usoft.service.CriteriaService;

public class TestCriteriaService {

	@Mock
	private CriteriaRepository repo;

	@InjectMocks
	private CriteriaService criteriaService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void saveCriteriaSuccess() {
		
	}

	@Test
	public void update() {
	}

	@Test
	public void findAllCriterias() {
		Criteria criteria = new Criteria();
		criteria.setId(14);
		ArrayList<Criteria> test = new ArrayList<>();
		test.add(criteria);
		when(repo.findAll()).thenReturn(test);
		Iterable<Criteria> allIndicators = criteriaService.findAll();
		assertTrue(allIndicators == test);

	}

	@Test
	public void findCriteriaSuccess() {
		Criteria criteria= new Criteria();
		criteria.setId(12);
		when(repo.find(12)).thenReturn(criteria);
		try {
			Criteria cr = criteriaService.find(12);
			assertTrue(criteria==cr);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findCriteriaFailure() {
		Criteria criteria = new Criteria();
		criteria.setId(1245);
		String original = "No criteria registred with id: 1245";
//		when(repo.find(1245)).thenThrow(new RuntimeException("No indicator registred with id: 1245"));
		when(repo.find(1245)).thenReturn(null);
		Criteria crite = criteriaService.find(1245);
		assertTrue(crite==null);
	}
	

	@Test
	public void deleteSuccess() {
		Criteria criteria= new Criteria();
		criteria.setId(12);
		when(repo.find(12)).thenReturn(criteria);
		try {
			Criteria cr = criteriaService.find(12);
			assertTrue(criteria==cr);
			criteriaService.delete(12);
			criteria.setIsdeleted(BigDecimal.ONE);
			when(repo.find(12)).thenReturn(criteria);
			cr = criteriaService.find(12);
			assertTrue(cr.getIsdeleted().compareTo(BigDecimal.ONE)==0);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	public void deleteFailure() {
		Criteria criteria= new Criteria();
		criteria.setId(12);
		when(repo.find(12)).thenReturn(criteria);
		String message="";
		String respuesta= "No criteria registred with id: 1245";
		try {
			Criteria cr = criteriaService.find(12);
			assertTrue(criteria==cr);
			criteriaService.delete(1245);
			fail();
			criteria.setIsdeleted(BigDecimal.ONE);
			when(repo.find(12)).thenReturn(criteria);
			cr = criteriaService.find(12);
			assertTrue(cr.getIsdeleted().compareTo(BigDecimal.ONE)==0);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertTrue(message.equals(respuesta));
	}
	
	
}
