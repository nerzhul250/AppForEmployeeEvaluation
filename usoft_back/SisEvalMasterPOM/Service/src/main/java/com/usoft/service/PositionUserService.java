package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Positionuser;
import com.usoft.repo.PositionUserRepositoryInt;

@Service
public class PositionUserService implements PositionUserServiceInt {
	
	@Autowired
	private PositionUserRepositoryInt positionUserRepository;
	
	@Override
	@Transactional
	public void createPositionUser(Positionuser positionUser) {
		positionUserRepository.save(positionUser);
	}

	@Override
	public Positionuser updatePositionUser(Positionuser positionUser) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePositionUser(Positionuser positionUser) {
		// TODO Auto-generated method stub

	}

	@Override
	public Positionuser findPositionUser(long positionUserId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Iterable<Positionuser> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
