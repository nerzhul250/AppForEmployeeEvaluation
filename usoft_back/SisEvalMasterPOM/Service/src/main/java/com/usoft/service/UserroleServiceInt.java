package com.usoft.service;

import com.usoft.model.State;
import com.usoft.model.Userrole;
import com.usoft.model.UserrolePK;

public interface UserroleServiceInt {
	public void createUserrole(Userrole userrole);

	public Userrole findUserrole(UserrolePK id);

	public Iterable<Userrole> findAll();

	void deleteUserrole(UserrolePK userrole);
}
