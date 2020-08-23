package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.usoft.model.Area;
import com.usoft.model.State;
import com.usoft.repo.StateRepositoryInt;

@Service
public class StateService implements StateServiceInt{
	
	@Autowired
	private StateRepositoryInt stateRepository;
	
	@Override
	public void createState(State state) {
		stateRepository.save(state);
	}

	@Override
	public State updateState(State state) {
		return stateRepository.update(state);
	}

	@Override
	public void deleteState(State state) {
		stateRepository.delete(state);
	}

	@Override
	public State findState(long id) {
		return stateRepository.findById(id);
	}

	@Override
	public Iterable<State> findAll() {
		return stateRepository.findAll();
	}

}
