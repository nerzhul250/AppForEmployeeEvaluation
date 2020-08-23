package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.sun.net.httpserver.Authenticator.Success;
import com.usoft.model.Indicator;
import com.usoft.repo.IndicatorRepository;
import com.usoft.service.IndicatorService;

public class TestIndicatorService {

	@Mock
	private IndicatorRepository repo;

	@InjectMocks
	private IndicatorService indicatorService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void saveIndicatorSuccess() {
		Indicator indicator = new Indicator();
		indicator.setId(14);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		assertTrue(indicatorService.save(indicator));
	}

	@Test
	public void update() {
		Indicator indicator = new Indicator();
		indicator.setId(11);
		indicator.setDescription("INDICATORBYKR");
		indicator.setName("INDICATORBYKR");
		assertTrue(indicatorService.update(indicator));
	}

	@Test
	public void findAllIndicators() {
		Indicator indicator = new Indicator();
		indicator.setId(14);
		ArrayList<Indicator> test = new ArrayList<>();
		test.add(indicator);
		when(repo.findAll()).thenReturn(test);
		Iterable<Indicator> allIndicators = indicatorService.findAll();
		assertTrue(allIndicators == test);

	}

	@Test
	public void findIndicatorSuccess() {
		Indicator indi= new Indicator();
		indi.setId(12);
		when(repo.find(12)).thenReturn(indi);
		try {
			Indicator ind = indicatorService.find(12);
			assertTrue(indi==ind);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void findIndicatorFailure() {
		Indicator indicator = new Indicator();
		indicator.setId(12);
		String original = "No indicator registred with id: 12";
		when(repo.find(12)).thenThrow(new RuntimeException("No indicator registred with id: 12"));
		String message = "";
		try {
			Indicator indi = indicatorService.find(12);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
	}

	@Test
	public void deleteSuccess() {
		Indicator indicator = new Indicator();
		indicator.setId(14);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		assertTrue(indicatorService.save(indicator));
		when(repo.find(14)).thenReturn(indicator);
		try {
			Indicator ind = indicatorService.find(14);
			assertTrue(indicator==ind);
			indicatorService.delete(14);
			indicator.setIsdeleted(BigDecimal.ONE);
			when(repo.find(14)).thenReturn(indicator);
			ind = indicatorService.find(14);
			assertTrue(ind.getIsdeleted().equals(BigDecimal.ONE));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void deleteFailure() {
		Indicator indicator = new Indicator();
		indicator.setId(14);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		assertTrue(indicatorService.save(indicator));
		when(repo.find(14)).thenReturn(indicator);
		try {
			Indicator ind = indicatorService.find(14);
			assertTrue(indicator==ind);
			indicatorService.delete(4);
			fail();
		} catch (Exception e) {
			
		}
	}

}
