package com.usoft.rest;

import static org.mockito.Mockito.doReturn;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.usoft.model.Evaluation;
import com.usoft.service.CriteriaService;
import com.usoft.service.EvaluationCriteriaService;
import com.usoft.service.EvaluationService;
import com.usoft.service.UserService;

public class TestApiEvaluationController {
	
	@Mock
	private UserService userService;
	@Mock
	private EvaluationService evaluationService;
	@Mock
	private EvaluationCriteriaService evaluationCriteriaService;
	@Mock
	private CriteriaService criteriaService;
	
	@InjectMocks
	private ApiEvaluationController apiEvaluationController;
	
	private MockMvc mockMvc;
	
	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(apiEvaluationController).build();
	}
	
//	@Test
//	public void createEvaluation() {
//		Evaluation evaluationTest = new Evaluation();
//		evaluationTest.setComentario("mi thing");
//		doReturn(true).when(evaluationService).createEvaluation(evaluationTest);
//		
//		
//		
//		
//	}

}
