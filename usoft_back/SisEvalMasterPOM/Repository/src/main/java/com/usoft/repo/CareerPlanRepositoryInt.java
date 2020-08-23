package com.usoft.repo;

import com.usoft.model.Careerplan;

public interface CareerPlanRepositoryInt {

	void save(Careerplan careerPlan);

	Careerplan update(Careerplan newCareerPlan);

	void delete(Careerplan deleteCareerPlan);

	Careerplan findById(long careerId);
	
	public Iterable<Careerplan> findAll();

}
