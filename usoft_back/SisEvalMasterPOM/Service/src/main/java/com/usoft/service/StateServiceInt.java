package com.usoft.service;

import com.usoft.model.Area;
import com.usoft.model.State;

public interface StateServiceInt {
	public void createState(State state);

	public State updateState(State state);

	public void deleteState(State state);

	public State findState(long id);

	public Iterable<State> findAll();
}
