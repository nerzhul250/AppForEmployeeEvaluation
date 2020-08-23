package com.usoft.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.usoft.dtos.AreaDTO;
import com.usoft.dtos.CareerplanDTO;
import com.usoft.dtos.CriteriaDTO;
import com.usoft.dtos.IndicatorDTO;
import com.usoft.dtos.IndicatorpositionDTO;
import com.usoft.dtos.PositionDTO;
import com.usoft.model.Area;
import com.usoft.model.Careerplan;
import com.usoft.model.Criteria;
import com.usoft.model.Indicator;
import com.usoft.model.Indicatorposition;
import com.usoft.model.Position;
import com.usoft.service.AreaServiceInt;
import com.usoft.service.CareerPlanServiceInt;
import com.usoft.service.CriteriaServiceInt;
import com.usoft.service.IndicatorService;
import com.usoft.service.IndicatorServiceInt;
import com.usoft.service.IndicatorpositionService;
import com.usoft.service.IndicatorpositionServiceInt;
import com.usoft.service.PositionServiceInt;
import com.usoft.service.StateServiceInt;

import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/usoftapi/careerplans")
public class ApiCareerPlanController implements ApiCareerPlanControllerInt{


	@Autowired
	private CareerPlanServiceInt careerPlanService;
	
	@Autowired
	private IndicatorServiceInt indicatorService;
	
	@Autowired
	private PositionServiceInt positionService;
	
	@Autowired
	private AreaServiceInt areaService;
	
	@Autowired
	private StateServiceInt stateService;
	
	@Autowired
	private CriteriaServiceInt criteriaPlanService;
	
	@Autowired
	private IndicatorpositionServiceInt indicatorPositionService;
	
	@Override
	@PostMapping("")
	public void createCareerPlan(@RequestBody CareerplanDTO careerplan) {
		Careerplan newCareerplan = new Careerplan();
		newCareerplan.setArea(areaService.findArea(careerplan.getArea().getId()));
		newCareerplan.setIsdeleted(BigDecimal.ZERO);
		newCareerplan.setDescription(careerplan.getDescription());
		newCareerplan.setName(careerplan.getName());
		newCareerplan.setPositionLevel(careerplan.getPositionLevel());
		
		careerPlanService.createCareerPlan(newCareerplan);
		
		for(IndicatorpositionDTO idDTO : careerplan.getIndicatorpositions()) {
			Indicatorposition idPos = new Indicatorposition();
			idPos.setPercent(idDTO.getPercent());
			idPos.setOrden(idDTO.getOrden());
			try {
				idPos.setIndicator(indicatorService.find((int) idDTO.getIndicator().getId()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			idPos.setPosition(positionService.findPosition(idDTO.getPosition().getId()));
			idPos.setCareerplan(newCareerplan);
			indicatorPositionService.save(idPos);
			
			for(CriteriaDTO c : idDTO.getCriterias()) {
				Criteria criteria = new Criteria();
				criteria.setDescription(c.getDescription());
				criteria.setPercent(c.getPercent());
				criteria.setIndicatorposition(idPos);
				criteriaPlanService.save(criteria);
			}
		}
	}

	@Override
	@GetMapping("")
	public Iterable<CareerplanDTO> findAllCareerPlans() {
		
		Iterable<Careerplan> allCareerPlans = careerPlanService.findAll();
		List<CareerplanDTO> allDTOCareerPlans = new ArrayList<CareerplanDTO>();
		
		Iterator<Careerplan> careerplanIterator = allCareerPlans.iterator();
		
		while(careerplanIterator.hasNext()) {
			
			Careerplan cp = careerplanIterator.next();
			CareerplanDTO cpDTO = new CareerplanDTO();
			
			AreaDTO areaDTO = new AreaDTO();
			areaDTO.setDescription(cp.getArea().getDescription());
			areaDTO.setId(cp.getArea().getId());
			areaDTO.setName(cp.getArea().getName());
			
			cpDTO.setArea(areaDTO);
			cpDTO.setId(cp.getId());
			cpDTO.setDescription(cp.getDescription());
			cpDTO.setName(cp.getName());
			
			List<IndicatorpositionDTO> listIndicatorPositionDTO = new ArrayList<IndicatorpositionDTO>();
			
			Iterator<Indicatorposition> indposIterator = cp.getIndicatorpositions().iterator();
			
			while(indposIterator.hasNext()) {
				
				Indicatorposition indicatorPosition = indposIterator.next();
				IndicatorpositionDTO indicatorPositionDTO = new IndicatorpositionDTO();
				indicatorPositionDTO.setId(indicatorPosition.getId());
				indicatorPositionDTO.setOrden(indicatorPosition.getOrden());
				indicatorPositionDTO.setPercent(indicatorPosition.getPercent());
				
				IndicatorDTO indicatorDTO = new IndicatorDTO();
				indicatorDTO = new IndicatorDTO();
				indicatorDTO.setId(indicatorPosition.getIndicator().getId());
				indicatorDTO.setName(indicatorPosition.getIndicator().getName());
				indicatorDTO.setDescription(indicatorPosition.getIndicator().getDescription());
				indicatorPositionDTO.setIndicator(indicatorDTO);
				
				PositionDTO positionDTO = new PositionDTO();
				positionDTO.setId(indicatorPosition.getPosition().getId());
				positionDTO.setName(indicatorPosition.getPosition().getName());
				positionDTO.setDescription(indicatorPosition.getPosition().getDescription());
				indicatorPositionDTO.setPosition(positionDTO);
				
				Iterator<Criteria> ct = criteriaPlanService.findByIndicatorPosition(indicatorPosition.getCareerplan().getId(),
						indicatorPosition.getIndicator().getId(), indicatorPosition.getPosition().getId()).iterator();
				List<CriteriaDTO> listCriteriaDTO = new ArrayList<CriteriaDTO>();
				
				while (ct.hasNext()) {
					Criteria criteria = ct.next();
					CriteriaDTO CriteriaDTO = new CriteriaDTO();
					CriteriaDTO.setId(criteria.getId());
					CriteriaDTO.setDescription(criteria.getDescription());
					CriteriaDTO.setPercent(criteria.getPercent());	
					
					listCriteriaDTO.add(CriteriaDTO);
				}
				
				indicatorPositionDTO.setCriterias(listCriteriaDTO);
				
				listIndicatorPositionDTO.add(indicatorPositionDTO);
			}
			cpDTO.setIndicatorpositions(listIndicatorPositionDTO);
			allDTOCareerPlans.add(cpDTO);
		}
		
		
		return allDTOCareerPlans;
	}

	@Override
	@GetMapping("/{id}")
	public CareerplanDTO findCareerPlan(@PathVariable(value = "id") Long id) {
		Careerplan cp = careerPlanService.findCareerPlan(id);
		CareerplanDTO careerPlanDTO = new CareerplanDTO();
		careerPlanDTO.setId(cp.getId());
		careerPlanDTO.setDescription(cp.getDescription());
		careerPlanDTO.setName(cp.getName());
		careerPlanDTO.setPositionLevel(cp.getPositionLevel());
		
		AreaDTO areaDTO = new AreaDTO();
		areaDTO.setDescription(cp.getArea().getDescription());
		areaDTO.setId(cp.getArea().getId());
		areaDTO.setName(cp.getArea().getName());
		careerPlanDTO.setArea(areaDTO);
		
		Iterator<Indicatorposition> it = cp.getIndicatorpositions().iterator();
		List<IndicatorpositionDTO> listIndicatorPositionDTO = new ArrayList<IndicatorpositionDTO>();

		while (it.hasNext()) {

			Indicatorposition indicatorPosition = it.next();

			IndicatorpositionDTO indicatorPositionDTO = new IndicatorpositionDTO();
			indicatorPositionDTO.setId(indicatorPosition.getId());
			indicatorPositionDTO.setOrden(indicatorPosition.getOrden());
			indicatorPositionDTO.setPercent(indicatorPosition.getPercent());

			IndicatorDTO indicatorDTO = new IndicatorDTO();
			indicatorDTO = new IndicatorDTO();
			indicatorDTO.setId(indicatorPosition.getIndicator().getId());
			indicatorDTO.setName(indicatorPosition.getIndicator().getName());
			indicatorDTO.setDescription(indicatorPosition.getIndicator().getDescription());
			indicatorPositionDTO.setIndicator(indicatorDTO);

			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setId(indicatorPosition.getPosition().getId());
			positionDTO.setName(indicatorPosition.getPosition().getName());
			positionDTO.setDescription(indicatorPosition.getPosition().getDescription());
			indicatorPositionDTO.setPosition(positionDTO);

			Iterator<Criteria> ct = criteriaPlanService.findByIndicatorPosition(indicatorPosition.getCareerplan().getId(),
					indicatorPosition.getIndicator().getId(), indicatorPosition.getPosition().getId()).iterator();
			List<CriteriaDTO> listCriteriaDTO = new ArrayList<CriteriaDTO>();
			
			while (ct.hasNext()) {
				Criteria criteria = ct.next();
				CriteriaDTO CriteriaDTO = new CriteriaDTO();
				CriteriaDTO.setId(criteria.getId());
				CriteriaDTO.setDescription(criteria.getDescription());
				CriteriaDTO.setPercent(criteria.getPercent());	
				
				listCriteriaDTO.add(CriteriaDTO);
			}
			
			indicatorPositionDTO.setCriterias(listCriteriaDTO);
			
			listIndicatorPositionDTO.add(indicatorPositionDTO);
		}

		careerPlanDTO.setIndicatorpositions(listIndicatorPositionDTO);
		
		return careerPlanDTO;
	}
	
	@Override
	@GetMapping("/evaluation/{id}")
	public CareerplanDTO findCareerPlanEvaluation(@PathVariable(value = "id") Long id) {

		Careerplan c = careerPlanService.findCareerPlan(id);
		CareerplanDTO careerPlanDTO = new CareerplanDTO();

		careerPlanDTO.setId(c.getId());
		careerPlanDTO.setName(c.getName());
		careerPlanDTO.setDescription(c.getDescription());
		careerPlanDTO.setPositionLevel(c.getPositionLevel());
		
		Iterator<Indicatorposition> it = c.getIndicatorpositions().iterator();
		List<IndicatorpositionDTO> listIndicatorPositionDTO = new ArrayList<IndicatorpositionDTO>();

		while (it.hasNext()) {

			Indicatorposition indicatorPosition = it.next();

			IndicatorpositionDTO indicatorPositionDTO = new IndicatorpositionDTO();
			indicatorPositionDTO.setOrden(indicatorPosition.getOrden());
			indicatorPositionDTO.setPercent(indicatorPosition.getPercent());

			IndicatorDTO indicatorDTO = new IndicatorDTO();
			indicatorDTO = new IndicatorDTO();
			indicatorDTO.setId(indicatorPosition.getIndicator().getId());
			indicatorDTO.setName(indicatorPosition.getIndicator().getName());
			indicatorDTO.setDescription(indicatorPosition.getIndicator().getDescription());
			indicatorPositionDTO.setIndicator(indicatorDTO);

			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setId(indicatorPosition.getPosition().getId());
			positionDTO.setName(indicatorPosition.getPosition().getName());
			positionDTO.setDescription(indicatorPosition.getPosition().getDescription());
			indicatorPositionDTO.setPosition(positionDTO);

			Iterator<Criteria> ct = criteriaPlanService.findByIndicatorPosition(indicatorPosition.getCareerplan().getId(),
					indicatorPosition.getIndicator().getId(), indicatorPosition.getPosition().getId()).iterator();
			List<CriteriaDTO> listCriteriaDTO = new ArrayList<CriteriaDTO>();
			
			while (ct.hasNext()) {
				Criteria criteria = ct.next();
				CriteriaDTO CriteriaDTO = new CriteriaDTO();
				CriteriaDTO.setId(criteria.getId());
				CriteriaDTO.setDescription(criteria.getDescription());
				CriteriaDTO.setPercent(criteria.getPercent());	
				
				listCriteriaDTO.add(CriteriaDTO);
			}
			
			indicatorPositionDTO.setCriterias(listCriteriaDTO);
			
			listIndicatorPositionDTO.add(indicatorPositionDTO);
		}

		careerPlanDTO.setIndicatorpositions(listIndicatorPositionDTO);

		return careerPlanDTO;
	}

	@Override
	@DeleteMapping("/{id}")
	public void deleteCareerPlan(@PathVariable Long id) {
		careerPlanService.deleteCareerPlan(id);
	}

	@Override
	@PatchMapping("")
	public void updateCareerPlan(@RequestBody Careerplan careerplan) {
		System.out.println("Editin careerplan with id:"+careerplan.getId());
		careerPlanService.updateCareerPlan(careerplan);
	}

	@Override
	@GetMapping("/search/{id}")
	public CareerplanDTO findCareerPlanWithOutCriteriasDeleted(@PathVariable(value = "id") Long id) {
		
		Careerplan cp = careerPlanService.findCareerPlan(id);
		CareerplanDTO careerPlanDTO = new CareerplanDTO();
		careerPlanDTO.setId(cp.getId());
		careerPlanDTO.setDescription(cp.getDescription());
		careerPlanDTO.setName(cp.getName());
		careerPlanDTO.setPositionLevel(cp.getPositionLevel());
		
		AreaDTO areaDTO = new AreaDTO();
		areaDTO.setDescription(cp.getArea().getDescription());
		areaDTO.setId(cp.getArea().getId());
		areaDTO.setName(cp.getArea().getName());
		careerPlanDTO.setArea(areaDTO);
		
		Iterator<Indicatorposition> it = cp.getIndicatorpositions().iterator();
		List<IndicatorpositionDTO> listIndicatorPositionDTO = new ArrayList<IndicatorpositionDTO>();

		while (it.hasNext()) {

			Indicatorposition indicatorPosition = it.next();

			IndicatorpositionDTO indicatorPositionDTO = new IndicatorpositionDTO();
			indicatorPositionDTO.setId(indicatorPosition.getId());
			indicatorPositionDTO.setOrden(indicatorPosition.getOrden());
			indicatorPositionDTO.setPercent(indicatorPosition.getPercent());

			IndicatorDTO indicatorDTO = new IndicatorDTO();
			indicatorDTO = new IndicatorDTO();
			indicatorDTO.setId(indicatorPosition.getIndicator().getId());
			indicatorDTO.setName(indicatorPosition.getIndicator().getName());
			indicatorDTO.setDescription(indicatorPosition.getIndicator().getDescription());
			indicatorPositionDTO.setIndicator(indicatorDTO);

			PositionDTO positionDTO = new PositionDTO();
			positionDTO.setId(indicatorPosition.getPosition().getId());
			positionDTO.setName(indicatorPosition.getPosition().getName());
			positionDTO.setDescription(indicatorPosition.getPosition().getDescription());
			indicatorPositionDTO.setPosition(positionDTO);

			Iterator<Criteria> ct = criteriaPlanService.findByIndicatorPosition(indicatorPosition.getCareerplan().getId(),
					indicatorPosition.getIndicator().getId(), indicatorPosition.getPosition().getId()).iterator();
			List<CriteriaDTO> listCriteriaDTO = new ArrayList<CriteriaDTO>();
			
			while (ct.hasNext()) {
				Criteria criteria = ct.next();
				if(criteria.getIsdeleted()==BigDecimal.ZERO) {
					
					CriteriaDTO CriteriaDTO = new CriteriaDTO();
					CriteriaDTO.setId(criteria.getId());
					CriteriaDTO.setDescription(criteria.getDescription());
					CriteriaDTO.setPercent(criteria.getPercent());	
					
					listCriteriaDTO.add(CriteriaDTO);
				}
			}
			
			indicatorPositionDTO.setCriterias(listCriteriaDTO);
			
			listIndicatorPositionDTO.add(indicatorPositionDTO);
		}

		careerPlanDTO.setIndicatorpositions(listIndicatorPositionDTO);
		
		return careerPlanDTO;
	}

}
