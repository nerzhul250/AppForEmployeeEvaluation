package com.usoft.repo;

import com.usoft.model.Area;
import com.usoft.model.State;

public interface StateRepositoryInt {
	void save(State state);

	State update(State state);

	void delete(State state);

	State findById(long id);
	
	public Iterable<State> findAll();
}
