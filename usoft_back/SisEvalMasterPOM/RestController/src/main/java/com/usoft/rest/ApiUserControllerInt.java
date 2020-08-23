package com.usoft.rest;

import org.springframework.http.ResponseEntity;

import com.usoft.dtos.UserDTO;
import com.usoft.model.Usuario;

public interface ApiUserControllerInt {
	
	
	
	public Iterable<UserDTO> findAllUsers();
	
	public UserDTO findUser( Integer id);
	
	public ResponseEntity<?> deleteUser( Integer id);
	
	public String updateUser(UserDTO user);

	public String createUser(UserDTO user);

	String updateProfile(UserDTO user);
	
	
	
	

}

