package com.usoft.service;

import com.usoft.model.Positionuser;

public interface PositionUserServiceInt {
	public void createPositionUser(Positionuser positionUser);

	public Positionuser updatePositionUser(Positionuser positionUser);

	public void deletePositionUser(Positionuser positionUser);

	public Positionuser findPositionUser(long positionUserId);

	public Iterable<Positionuser> findAll();
}
