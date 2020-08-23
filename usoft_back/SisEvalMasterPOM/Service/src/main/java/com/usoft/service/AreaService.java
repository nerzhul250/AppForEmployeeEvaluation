package com.usoft.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Area;
import com.usoft.repo.AreaRepository;
import com.usoft.repo.AreaRepositoryInt;

@Service
public class AreaService implements AreaServiceInt{
	
	private AreaRepository areaRepository;
	
	@Autowired
	public AreaService(AreaRepository area) {
		this.areaRepository=area;
	}
	@Transactional
	@Override
	public void createArea(Area area) {
		System.out.println("Entra al service area");
		areaRepository.save(area);
	}

	@Transactional
	@Override
	public Area updateArea(Area area) {
		return areaRepository.update(area);
	}

	@Transactional
	@Override
	public void deleteArea(Area area) {
		
		System.out.println("Entra al Service");
		areaRepository.delete(area);
	}

	@Transactional
	@Override
	public Area findArea(long id) {
		return areaRepository.findById(id);
	}

	@Transactional
	@Override
	public Iterable<Area> findAll() {
		return areaRepository.findAll();
	}

}
