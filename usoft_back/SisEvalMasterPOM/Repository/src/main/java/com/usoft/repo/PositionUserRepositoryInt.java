package com.usoft.repo;

import com.usoft.model.Positionuser;

public interface PositionUserRepositoryInt {
	void save(Positionuser positionUser);
	
	Positionuser update(Positionuser newPositionUser);
	
	void delete(Positionuser deletePositionUser);
	
	Positionuser findBy(long positionUserId);
	
	public Iterable<Positionuser>findAll();
}
