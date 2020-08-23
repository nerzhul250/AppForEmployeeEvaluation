package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Role;
import com.usoft.model.Usuario;
import com.usoft.repo.RoleRepositoryInt;

@Service
public class RoleService implements RoleServiceInt {
	
	@Autowired
	private RoleRepositoryInt repository;
	
	@Transactional
	@Override
	public boolean save(Role role) {
		try{
			repository.save(role);
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	@Transactional
	@Override
	public boolean update(Role role) {
		// TODO Auto-generated method stub
		try {
			repository.update(role);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return true;
	}

	
	/**
	 * This method is a service which purpose is to find all registered roles
	 */
	@Transactional
	@Override
	public Iterable<Role> findAll() {
		return repository.findAll();
	}

	/**
	 * This method is a service which purpose is to find an specified (by its id) role
	 */
	@Transactional
	@Override
	public Role find(Long id) throws Exception {
		try {
			Role role = repository.find(id);
			if(role==null) {
				throw new Exception("No role registred with id: " + id);
			}else {
				return role;
			}
		} catch (Exception e) {
			throw new Exception("No role registred with id: " + id);
		}
	}
	/**
	 * This method responses the api request by deleteMapping, to remove an a role
	 */
	@Transactional
	@Override
	public void delete(Long id) throws Exception {
		try {
			Role role = repository.find(id);
			if(role==null) {
				throw new Exception("No role registred with id: " + id);
			}else {
				repository.delete(id);
			}
		} catch (Exception e) {
			throw new Exception("No role registred with id: " + id);
		}
	}

}
