package com.usoft.repo;

import java.util.List;

import com.usoft.model.Position;

public interface PositionRepositoryInt {
	
	void save(Position position);
	
	Position update(Position newPosition);
	
	void delete(Position deletePosition);
	
	Position findBy(long position);
	
	public Iterable<Position>findAll();

}
