package com.usoft.repo;

import com.usoft.model.Userrole;
import com.usoft.model.UserrolePK;

public interface UserroleRepositoryInt {
	
	public void save(Userrole userRole);
	
	public Iterable<Userrole> findAll();
	
	public Userrole find(UserrolePK id);
	
	public void delete(UserrolePK id);
}
