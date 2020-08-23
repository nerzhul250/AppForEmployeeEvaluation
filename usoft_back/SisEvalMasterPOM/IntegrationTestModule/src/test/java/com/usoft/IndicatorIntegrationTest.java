package com.usoft;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.math.BigDecimal;
import java.util.ArrayList;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.usoft.application.Application;
import com.usoft.model.Indicator;
import com.usoft.model.Usuario;
import com.usoft.service.IndicatorService;
import com.usoft.service.UserService;

@SpringBootTest(classes = Application.class)
public class IndicatorIntegrationTest {
	
	@Autowired
	private IndicatorService indicatorService;
	
	@Test
	public void saveIndicatorSuccess() throws Exception {
		Indicator indicator = new Indicator();
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		indicator.setIsdeleted(BigDecimal.ZERO);
		indicatorService.save(indicator);
		assertTrue(indicator.getDescription()
				.equals(indicatorService.find((int) indicator.getId()).getDescription()));
	}
	
	@Test
	public void findAllIndicators() {
		Iterable<Indicator> allindicator=indicatorService.findAll();
        ArrayList<Indicator> list = new ArrayList<>(); 
        allindicator.forEach(list::add);
        assertTrue(list.size()>0);
	}

	@Transactional
	@Test
	public void findIndicatorFailure() {
		String original = "No indicator registred with id: 1028";
		String message = "";
		try {
			Indicator indi = indicatorService.find(1028);
		} catch (Exception e) {
			message = e.getMessage();
		}
		assertEquals(message, original);
		
	}
	
	
	@Test
	public void findIndicatorSuccess() {
		
		try {
			Indicator indi = indicatorService.find((102));
			assertNotNull(indi);
			assertEquals("NO BORREN", indi.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	@Transactional
	@Test
	public void deleteIndicatorSuccess() {
		Indicator indicator = new Indicator();
		try {
			indicator.setDescription("NEWINDICATOR");
			indicator.setName("MYINDICATOR");
			indicatorService.save(indicator);
			assertTrue(indicator.getDescription()
					.equals(indicatorService.find((int) indicator.getId()).getDescription()));
			indicatorService.delete((int)indicator.getId());
			assertTrue(indicator.getDescription()
					.equals(indicatorService.find((int) indicator.getId()).getDescription()));
			fail();
		} catch (Exception e) {
			String original = "No indicator registred with id: "+indicator.getId();
			assertEquals(e.getMessage(), original);
		}
	}
	@Transactional
	@Test
	public void deleteIndicatorFailure() {
		Indicator indicator = new Indicator();
		try {
			indicator.setDescription("NEWINDICATOR");
			indicator.setName("MYINDICATOR");
			indicatorService.save(indicator);
			assertTrue(indicator.getDescription()
					.equals(indicatorService.find((int) indicator.getId()).getDescription()));
			indicatorService.delete(10);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
}
