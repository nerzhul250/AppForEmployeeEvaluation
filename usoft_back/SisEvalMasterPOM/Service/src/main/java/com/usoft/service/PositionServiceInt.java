package com.usoft.service;

import com.usoft.model.Position;

public interface PositionServiceInt {

	public void createPosition(Position position);

	public Position updatePosition(Position position);

	public void deletePosition(Position position);

	public Position findPosition(long position);

	public Iterable<Position> findAll();
	
}
