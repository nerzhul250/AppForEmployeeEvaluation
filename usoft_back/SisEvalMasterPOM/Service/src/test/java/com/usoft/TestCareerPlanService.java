package com.usoft;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.event.annotation.BeforeTestClass;

import com.usoft.model.Careerplan;
import com.usoft.repo.CareerPlanRepository;
import com.usoft.service.CareerPlanService;



public class TestCareerPlanService {
	/*
	@Mock
	private CareerPlanRepository careerPlanRepo;
	@InjectMocks
	private CareerPlanService careerPlanService;

	@BeforeEach
	 public void setUp() {
		MockitoAnnotations.initMocks(this);
	 }
	
	@Test
	public void testCaseAdd() {
		Careerplan plan = new Careerplan();
		plan.setId((long)12);
		plan.setDescription("Test Plan");
		careerPlanService.createCareerPlan(plan);
		when(careerPlanRepo.findById((long)12)).thenReturn(plan);
		assertTrue(careerPlanService.findCareerPlan((long)12).equals(plan));
	}
	
	@Test
	public void testCaseEdit() {
		Careerplan plan = new Careerplan();
		plan.setDescription("Test Plan");
		when(careerPlanRepo.update(plan)).thenReturn(plan);
		Careerplan newPlan=careerPlanService.updateCareerPlan(plan);
		assertTrue(plan.equals(newPlan));
	}
	
	@Test
	public void testCaseDelete() {
		Careerplan plan = new Careerplan();
		plan.setId((long)12);
		plan.setDescription("Test Plan");
		careerPlanService.createCareerPlan(plan);
		careerPlanService.deleteCareerPlan(plan);
		assertTrue(careerPlanService.findCareerPlan((long)12)==null);
	}
	
	@Test
	public void testCaseFind() {
		Careerplan plan = new Careerplan();
		plan.setDescription("Test Plan");
		plan.setId(12);
		when(careerPlanRepo.findById((long)12)).thenReturn(plan);
		Careerplan newPlan=careerPlanService.findCareerPlan((long)12);
		assertTrue(newPlan==plan);
	}
	
	@Test
	public void testCaseFindAll() {
		Careerplan plan = new Careerplan();
		plan.setDescription("Test Plan");
		ArrayList<Careerplan> test= new ArrayList<>();
		test.add(plan);
		when(careerPlanRepo.findAll()).thenReturn(test);
		Iterable<Careerplan> allPlans=careerPlanService.findAll();
		assertTrue(allPlans==test);
	}
	
	*/
	

}
