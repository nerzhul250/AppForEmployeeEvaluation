package com.usoft.rest;

import com.usoft.dtos.PositionDTO;
import com.usoft.model.Position;

public interface ApiPositionControllerInt {

	public String createPosition(Position position);
	
	public Iterable<Position> findAllPositions();
	
	public Position findPosition(Long id);
	
	public String deletePosition(Long id);
	
	public String updatePosition(Position position);
}
