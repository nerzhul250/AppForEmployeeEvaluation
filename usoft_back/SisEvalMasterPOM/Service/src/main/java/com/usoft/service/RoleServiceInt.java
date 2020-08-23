package com.usoft.service;

import com.usoft.model.Role;

public interface RoleServiceInt {

	public boolean save(Role role);	
	public boolean update(Role role);		
	public Iterable<Role> findAll();	
	public Role find(Long id) throws Exception;	
	public void delete(Long id) throws Exception;	

}
