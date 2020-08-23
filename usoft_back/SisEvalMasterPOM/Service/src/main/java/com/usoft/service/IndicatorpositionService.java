package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Criteria;
import com.usoft.model.Indicatorposition;
import com.usoft.repo.IndicatorpositionRepositoryInt;

@Service
public class IndicatorpositionService implements IndicatorpositionServiceInt{
	
	@Autowired
	private IndicatorpositionRepositoryInt indicatorPositionRepository;
	@Autowired
	private PositionServiceInt positionService;
	@Autowired
	private IndicatorServiceInt indicatorService;
	@Autowired
	private CriteriaServiceInt criteriaService;

	@Transactional
	@Override
	public void save(Indicatorposition indicatorposition) {
		// TODO Auto-generated method stub
		indicatorPositionRepository.save(indicatorposition);
	}

	@Transactional
	@Override
	public void update(Indicatorposition indicatorposition) {
		// TODO Auto-generated method stub
		try {
			
			Indicatorposition indposToUpdate = findById((int) indicatorposition.getId());
			
			indposToUpdate.setPercent(indicatorposition.getPercent());
			indposToUpdate.setOrden(indicatorposition.getOrden());
			indposToUpdate.setPosition(positionService.findPosition(indicatorposition.getPosition().getId()));
			indposToUpdate.setIndicator(indicatorService.find((int) indicatorposition.getIndicator().getId()));
			indicatorPositionRepository.update(indposToUpdate);
			for(Criteria criteria : indicatorposition.getCriterias()) {
				Criteria criteriaToUpdate = criteriaService.find((int) criteria.getId());
				if(criteriaToUpdate!=null) {
					criteriaService.update(criteria);
				}
				else {
					System.out.println(">>> Creating Criteria:: "+criteria.getDescription()+" :: "+criteria.getPercent());
					criteria.setIndicatorposition(indposToUpdate);
					criteriaService.save(criteria);
				}
			}
			
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Transactional(readOnly = true)
	@Override
	public Iterable<Indicatorposition> findAll() {
		// TODO Auto-generated method stub
		return indicatorPositionRepository.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Indicatorposition findById(Integer id) {
		// TODO Auto-generated method stub
		return indicatorPositionRepository.findById(id);
	}
	
	@Transactional
	@Override
	public void delete(Integer id) {
		// TODO Auto-generated method stub
		indicatorPositionRepository.delete(id);
	}

}
