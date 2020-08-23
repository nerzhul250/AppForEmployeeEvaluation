package com.usoft.service;

import com.usoft.model.Usuario;

public interface UserServiceInt {
	
	public boolean createUser(Usuario user);
	
	public Iterable<Usuario> findAllUsers();
	
	public Usuario findUser( Integer id) throws Exception;
	
	public void deleteUser( Integer id)throws Exception;

	public void sendEmail(String from,String to, String user, String password)throws Exception;
	
	public boolean updateUser(Usuario user);

}
