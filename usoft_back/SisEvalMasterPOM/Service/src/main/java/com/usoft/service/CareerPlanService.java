package com.usoft.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Careerplan;
import com.usoft.model.Criteria;
import com.usoft.model.Indicatorposition;
import com.usoft.repo.CareerPlanRepository;

@Service
public class CareerPlanService implements CareerPlanServiceInt{
	
	/**
	 * Dependency to the DAO for career plans
	 */
	private CareerPlanRepository planCarreraDao;
	
	@Autowired
	private AreaServiceInt areaService;
	@Autowired
	private IndicatorpositionServiceInt indicatorpositionService;
	@Autowired 
	private PositionServiceInt positionService;
	@Autowired
	private IndicatorServiceInt indicatorService;
	@Autowired
	private CriteriaServiceInt criteriaService;
	
	/**
	 *Injection of depencencies
	 * @param planCarreraDao the the dependency to be injected
	 */
	@Autowired
	public CareerPlanService(CareerPlanRepository planCarreraDao) {
		this.planCarreraDao=planCarreraDao;
	}

	/**
	 * Method to create a career plan by calling the save function of the DAO
	 * @param careerPlan is the career plan to be created
	 */
	@Transactional
	@Override
	public void createCareerPlan(Careerplan careerPlan) {
		// TODO Auto-generated method stub
		planCarreraDao.save(careerPlan);
		
	}
	/**
	 * Method to update a career plan by calling the update function of the DAO
	 * @param newCarerrPlan is the career plan to be updated
	 * @return the method returns the new career plan
	 */
	@Transactional
	@Override
	public Careerplan updateCareerPlan(Careerplan careerPlan) {
		try {
			Careerplan cpToUpdate = planCarreraDao.findById(careerPlan.getId());
			cpToUpdate.setName(careerPlan.getName());
			cpToUpdate.setDescription(careerPlan.getDescription());
			cpToUpdate.setArea(areaService.findArea(careerPlan.getArea().getId()));
			for(Indicatorposition indpos : careerPlan.getIndicatorpositions()) {
				Indicatorposition indposToUpdate = indicatorpositionService.findById((int) indpos.getId());
				if(indposToUpdate!=null) {
					indicatorpositionService.update(indpos);
				}
				else {
					System.out.println(">> Creating an Indicatorposition");
					indpos.setIndicator(indicatorService.find((int) indpos.getIndicator().getId()));
					indpos.setPosition(positionService.findPosition(indpos.getPosition().getId()));
					indpos.setCareerplan(cpToUpdate);
					indicatorpositionService.save(indpos);
					for(Criteria criteria : indpos.getCriterias()) {
						criteria.setIndicatorposition(indpos);
						criteriaService.save(criteria);
					}
				}
			}
			planCarreraDao.update(cpToUpdate);
			return cpToUpdate;
		}catch(Exception e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * Method to logically delete a career plan by calling the delete function of the DAO
	 * @param deleteCareerPlan is the career plan to be deleted
	 */
	@Transactional
	@Override
	public void deleteCareerPlan(long id) {
		Careerplan toDelete = planCarreraDao.findById(id);
		toDelete.setIsdeleted(BigDecimal.ONE);
		planCarreraDao.delete(toDelete);
		
	}
	/**
	 * Method to find a career plan calling the findbyid method of the DAO
	 * @param careerId is the id of the career plan to be searched
	 * @return is the career plan found
	 */
	@Transactional
	@Override
	public Careerplan findCareerPlan(long careerId) {
		System.out.println(planCarreraDao);
		return planCarreraDao.findById(careerId);
		
	}
	/**
	 * Method to get all the career plans present in the database by calling the findAll function of the DAO
	 * @return is a list of all the career plans found
	 */
	@Transactional
	@Override
	public Iterable<Careerplan> findAll() {
		// TODO Auto-generated method stub
		return planCarreraDao.findAll();
	}
	
	
	
	

}
