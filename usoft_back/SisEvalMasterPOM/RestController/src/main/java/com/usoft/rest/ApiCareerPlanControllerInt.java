package com.usoft.rest;

import com.usoft.dtos.CareerplanDTO;
import com.usoft.model.Careerplan;

public interface ApiCareerPlanControllerInt {
	
	public void createCareerPlan(CareerplanDTO careerplan);
	
	public Iterable<CareerplanDTO> findAllCareerPlans();
	
	public CareerplanDTO findCareerPlan(Long id);
	
	public CareerplanDTO findCareerPlanWithOutCriteriasDeleted(Long id);
	
	public CareerplanDTO findCareerPlanEvaluation(Long id);

	public void deleteCareerPlan(Long id);

	public void updateCareerPlan(Careerplan careerplan);

}
