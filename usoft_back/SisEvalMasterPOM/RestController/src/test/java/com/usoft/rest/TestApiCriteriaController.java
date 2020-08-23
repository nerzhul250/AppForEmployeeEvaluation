package com.usoft.rest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;

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
import com.usoft.model.Criteria;
import com.usoft.model.Indicator;
import com.usoft.service.CriteriaService;

public class TestApiCriteriaController {
	@Mock
	private CriteriaService criteriaService;

	@InjectMocks
	private ApiCriteriaController apiCriteriaController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiCriteriaController).build();
	}
	
	@Test
	public void saveCriteriaSuccess() throws JsonProcessingException, Exception {
		
	}

	@Test
	public void update() {
	}

	@Test
	public void findAllCriterias() throws Exception{
		Criteria first = new Criteria();
		Criteria second = new Criteria();
		ArrayList<Criteria> array = new ArrayList<>();
		array.add(first);
		array.add(second);
		doReturn(array).when(criteriaService).findAll();
		mockMvc.perform(get("/usoftapi/criteria")).andExpect(status().isOk());
	}

	@Test
	public void findCriteria() throws Exception{
		Criteria cri = new Criteria();
		cri.setId(1);
		doReturn(cri).when(criteriaService).find(1);
		mockMvc.perform(get("/usoftapi/criteria/1")).andExpect(status().isOk());
	}
	
	@Test
	public void deleteCriteriaSuccess() throws Exception {
		Criteria criteria = new Criteria();
		criteria.setId(200);
		criteria.setDescription("NEWINDICATOR");
		ObjectMapper objectMapper = new ObjectMapper();
		ResultActions result = mockMvc.perform(post("/usoftapi/criteria")
				.content(objectMapper.writeValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));
		result.andExpect(status().isOk());
		result = mockMvc.perform(delete("/usoftapi/criteria/200"));
		result.andExpect(status().isNoContent());
		assertTrue(result.andReturn().getResponse().getStatus()==MockHttpServletResponse.SC_NO_CONTENT);
	}

}
