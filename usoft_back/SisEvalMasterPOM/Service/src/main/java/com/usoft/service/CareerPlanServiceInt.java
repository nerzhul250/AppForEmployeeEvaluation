package com.usoft.service;

import com.usoft.model.Careerplan;

public interface CareerPlanServiceInt {

	public void createCareerPlan(Careerplan careerPlan);

	public Careerplan updateCareerPlan(Careerplan newCareerPlan);

	public void deleteCareerPlan(long id);

	public Careerplan findCareerPlan(long careerId);

	public Iterable<Careerplan> findAll();

}
