package com.usoft;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.usoft.model.Area;
import com.usoft.model.Position;
import com.usoft.model.State;
import com.usoft.model.Usuario;
import com.usoft.repo.AreaRepository;
import com.usoft.repo.PositionRepository;
import com.usoft.repo.UserRepository;
import com.usoft.service.AreaService;
import com.usoft.service.PositionService;
import com.usoft.service.UserService;

class TestAreaService {

	@Mock
	private AreaRepository areaRepository;

	@InjectMocks
	private AreaService areaService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createPosition()throws Exception {
		Area area=new Area();
		area.setId((long)12);
		area.setDescription("Area de Ingenieros");
		area.setName("IngZone");
		area.setIsdeleted(new BigDecimal("0"));
		areaService.createArea(area);
		when(areaRepository.findById((long)12)).thenReturn(area);
		assertTrue(areaService.findArea((12)).equals(area));
//		positionService.createPosition(position);
//		when(positionRepo.findBy((long)12)).thenReturn(position);
//		assertTrue(positionService.findPosition((12)).equals(position));
		
	}
	@Test
	public void EditPosition() throws Exception {
		Area area=new Area();
		area.setId((long)12);
		area.setDescription("Area de Ingenieros");
		area.setName("IngZone");
		area.setIsdeleted(BigDecimal.ZERO);
		when(areaRepository.update(area)).thenReturn(area);
		Area newArea=areaService.updateArea(area);
		assertTrue(area.equals(newArea));
//		when(positionRepo.update(position)).thenReturn(position);
//		Position newPosition=positionService.updatePosition(position);
//		assertTrue(position.equals(newPosition));
	}
	
	@Test
	public void DeletePosition()throws Exception{
		Area area=new Area();
		area.setId((long)12);
		area.setDescription("Area de Ingenieros");
		area.setName("IngZone");
		area.setIsdeleted(BigDecimal.ZERO);
		when(areaRepository.findById(12)).thenReturn(area);
		try {
			Area ar=areaService.findArea(12);
			assertTrue(area==ar);
			areaService.deleteArea(area);
			area.setIsdeleted(BigDecimal.ONE);
			when(areaRepository.findById(12)).thenReturn(area);
			ar=areaService.findArea(12);
			assertTrue(ar.getIsdeleted().compareTo(BigDecimal.ONE)==0);
		} catch (Exception e) {
			// TODO: handle exception
		}
//		positionService.createPosition(pos);
//		positionService.deletePosition(pos);
//		assertTrue(positionService.findPosition((long)12)==null);
	}
	@Test
	public void findPosition()throws Exception {
		Area pos=new Area();
		pos.setId((long)12);
		pos.setDescription("Area de Ingenieros");
		pos.setName("IngZone");
		pos.setIsdeleted(BigDecimal.ZERO);
		when(areaRepository.findById(12)).thenReturn(pos);
		try {
			Area p=areaRepository.findById(12);
			assertTrue(pos==p);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//		when(positionRepo.findBy((long)12)).thenReturn(pos);
//		Position findPos=positionService.findPosition((long)12);
//		assertTrue(findPos==pos);
	}
	
	@Test
	public void findAll() {
		Area pos=new Area();
		pos.setId((long)12);
		pos.setDescription("Area de Ingenieros");
		pos.setName("IngZone");
		pos.setIsdeleted(BigDecimal.ZERO);
		Area probar=new Area();
		pos.setId((long)22);
		pos.setDescription("Area de Diseño");
		pos.setName("Diseño");
		pos.setIsdeleted(BigDecimal.ONE);
		ArrayList<Area> all=new ArrayList<>();
		all.add(pos);
		all.add(probar);
		when(areaRepository.findAll()).thenReturn(all);
		Iterable<Area>allAreas=areaService.findAll();
		assertTrue(allAreas==all);
//		when(positionRepo.findAll()).thenReturn(all);
//		Iterable<Position> allPositions=positionService.findAll();
//		assertTrue(allPositions==all);
	}
}
