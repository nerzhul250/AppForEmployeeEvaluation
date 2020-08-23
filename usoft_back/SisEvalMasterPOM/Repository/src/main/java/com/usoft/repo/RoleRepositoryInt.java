package com.usoft.repo;

import com.usoft.model.Role;

public interface RoleRepositoryInt {
	public void save(Role role);
	public void update(Role role);	
	public Iterable<Role> findAll();	
	public Role find(Long id);	
	public void delete(Long id);
}
