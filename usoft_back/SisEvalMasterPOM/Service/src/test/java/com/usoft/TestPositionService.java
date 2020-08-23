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
import com.usoft.repo.PositionRepository;
import com.usoft.repo.UserRepository;
import com.usoft.service.PositionService;
import com.usoft.service.UserService;

class TestPositionService {

	@Mock
	private PositionRepository positionRepo;

	@InjectMocks
	private PositionService positionService;

	@BeforeEach
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void createPosition()throws Exception {
		Position position=new Position();
		position.setId((long)12);
		position.setDescription("Scrum Master");
		position.setName("Arango");
		positionService.createPosition(position);
		when(positionRepo.findBy((long)12)).thenReturn(position);
		assertTrue(positionService.findPosition((12)).equals(position));
		
	}
	@Test
	public void EditPosition() throws Exception {
		Position position=new Position();
		position.setId((long)12);
		position.setDescription("Scrum Master");
		position.setName("Arango");
		when(positionRepo.update(position)).thenReturn(position);
		Position newPosition=positionService.updatePosition(position);
		assertTrue(position.equals(newPosition));
	}
	
	@Test
	public void DeletePosition()throws Exception{
		Position pos=new Position();
		pos.setId((long)12);
		pos.setDescription("Scrum Master");
		pos.setName("Arango");
		positionService.createPosition(pos);
		when(positionRepo.findBy(12)).thenReturn(pos);
		try {
			Position p=positionService.findPosition(12);
			assertTrue(pos==p);
			positionService.deletePosition(pos);
			pos.setIsdeleted(BigDecimal.ONE);
			when(positionRepo.findBy(pos.getId())).thenReturn(pos);
			p=positionService.findPosition(pos.getId());
			assertTrue(p.getIsdeleted().equals(BigDecimal.ONE));
		} catch (Exception e) {
			fail();
		}
	}
	@Test
	public void findPosition()throws Exception {
		Position pos=new Position();
		pos.setId((long)12);
		pos.setDescription("Scrum Master");
		pos.setName("Arango");
		when(positionRepo.findBy((long)12)).thenReturn(pos);
		Position findPos=positionService.findPosition((long)12);
		assertTrue(findPos==pos);
	}
	
	@Test
	public void findAll() {
		Position pos=new Position();
		pos.setId((long)12);
		pos.setDescription("Scrum Master");
		pos.setName("Arango");
		ArrayList<Position> all=new ArrayList<>();
		all.add(pos);
		when(positionRepo.findAll()).thenReturn(all);
		Iterable<Position> allPositions=positionService.findAll();
		assertTrue(allPositions==all);
	}
}
