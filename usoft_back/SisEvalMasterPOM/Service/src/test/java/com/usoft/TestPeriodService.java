package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Period;
import com.usoft.repo.PeriodRepository;
import com.usoft.service.PeriodService;

public class TestPeriodService {
	
	@Mock
	private PeriodRepository periodRepository;
	
	@InjectMocks
	private PeriodService periodService;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void createPeriod() {
//		LocalDate start = LocalDate.of(2020, 1, 1);
//		LocalDate end = LocalDate.of(2020, 6, 30);
		
//		Date.from(start.atStartOfDay().toInstant();
		
		Period period = new Period();
		period.setName("2020-1");
		period.setStartDate(new Date(2020,0,1));
		period.setEndDate(new Date(2019, 5, 30));
		assertFalse(periodService.createPeriod(period));
		
		period.setEndDate(new Date(2020, 5, 30));
		assertTrue(periodService.createPeriod(period));
	}
	
	@Test
	public void findAllPeriods() {
		Period period = new Period();
		period.setId(666);
		ArrayList<Period> periods = new ArrayList<Period>();
		periods.add(period);
		when(periodRepository.findAll()).thenReturn(periods);
		Iterable<Period> allPeriods = periodService.findAllPeriods();
		assertEquals(periods, allPeriods);
	}
	
	@Test
	public void findPeriod() {
		Period period = new Period();
		period.setId(666);
		when(periodRepository.find(666)).thenReturn(period);
		try {
			assertEquals(period,periodService.findPeriod(666));
		} catch (Exception e) {
			fail();
		}
		
		Period p2 = new Period();
		period.setId(667);
		when(periodRepository.find(667)).thenThrow(new RuntimeException("No period registered with that Id."));
		try {
			periodService.findPeriod(667);
			fail();
		} catch (Exception e) {
		}
	}
	
	public void updatePeriod() {
		
	}
	
	public void deletePeriod() {
		
	}

}
