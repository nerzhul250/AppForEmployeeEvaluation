package com.usoft.rest;

import org.springframework.http.ResponseEntity;

import com.usoft.dtos.RoleDTO;


public interface ApiRoleControllerInt {
	public String save(RoleDTO role);	
	public String update(RoleDTO role);		
	public Iterable<RoleDTO> findAll();	
	public RoleDTO find(Long id);	
	public ResponseEntity<?> delete(Long id);
}
