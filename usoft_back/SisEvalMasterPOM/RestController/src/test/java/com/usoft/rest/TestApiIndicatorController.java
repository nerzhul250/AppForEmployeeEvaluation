package com.usoft.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Iterator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.usoft.model.Indicator;
import com.usoft.model.Indicatorposition;
import com.usoft.service.IndicatorService;
import com.usoft.service.IndicatorpositionServiceInt;

public class TestApiIndicatorController {
	
	@Mock
	private IndicatorService indicatorService;

	@InjectMocks
	private ApiIndicatorController apiIndicatorController;
	
	@Mock
	private IndicatorpositionServiceInt indicatorPositionService;
	
	private MockMvc mockMvc;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiIndicatorController).build();
	}
	
	@Test
	public void saveIndicatorSuccess() throws JsonProcessingException, Exception {
		Indicator indicator = new Indicator();
		indicator.setId(14);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		doReturn(true).when(indicatorService).save(indicator);
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/indicators")
				.content(objectMapper.writeValueAsString(indicator))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
	}

	@Test
	public void update() {
	}

	@Test
	public void findAllIndicators() throws Exception{
		Indicator first = new Indicator();
		Indicator second = new Indicator();
		ArrayList<Indicator> array = new ArrayList<>();
		array.add(first);
		array.add(second);
		doReturn(array).when(indicatorService).findAll();
		mockMvc.perform(get("/usoftapi/indicators")).andExpect(status().isOk());
	}

	@Test
	public void findIndicator() throws Exception{
		Indicator indi = new Indicator();
		indi.setId(1);
		doReturn(indi).when(indicatorService).find(1);
		mockMvc.perform(get("/usoftapi/indicators/1")).andExpect(status().isOk());
	}
	
	@Test
	public void deleteIndicatorSuccess() throws Exception {
		Indicator indicator = new Indicator();
		indicator.setId(200);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		doReturn(true).when(indicatorService).save(indicator);
		doReturn(null).when(indicatorPositionService).findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/indicators")
				.content(objectMapper.writeValueAsString(indicator))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result = mockMvc.perform(delete("/usoftapi/indicators/200"));
		result.andExpect(status().isNoContent());
		assertTrue(result.andReturn().getResponse().getStatus()==MockHttpServletResponse.SC_NO_CONTENT);
	}
	@Test
	public void deleteIndicatorFailure() throws Exception {
		Indicator indicator = new Indicator();
		indicator.setId(200);
		indicator.setDescription("NEWINDICATOR");
		indicator.setName("MYINDICATOR");
		doReturn(true).when(indicatorService).save(indicator);
		Iterable<Indicatorposition> retorno= new Iterable<Indicatorposition>() {
			@Override
			public Iterator<Indicatorposition> iterator() {
				Iterator<Indicatorposition> indicatorsPositions= new Iterator<Indicatorposition>() {
					@Override
					public Indicatorposition next() {
						Indicatorposition prueba= new Indicatorposition();
						prueba.setId(1);
						prueba.setIndicator(indicator);
						return prueba;
					}
					
					@Override
					public boolean hasNext() {
						return true;
					}
				};
				return indicatorsPositions;
			}
		};
		doReturn(retorno).when(indicatorPositionService).findAll();
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/indicators")
				.content(objectMapper.writeValueAsString(indicator))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result = mockMvc.perform(delete("/usoftapi/indicators/200"));
		result.andExpect(status().isBadRequest());
		assertTrue(result.andReturn().getResponse().getStatus()==MockHttpServletResponse.SC_BAD_REQUEST);
	}
}
