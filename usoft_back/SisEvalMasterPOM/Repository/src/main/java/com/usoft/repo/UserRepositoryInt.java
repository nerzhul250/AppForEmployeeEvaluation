package com.usoft.repo;

import com.usoft.model.Usuario;

public interface UserRepositoryInt {
	
	public void save(Usuario user);
	
	public Iterable<Usuario> findAll();
	
	public Usuario find( Integer id);
	
	public void delete( Integer id);
	
	public void update(Usuario user);
	

}
