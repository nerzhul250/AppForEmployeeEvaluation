package com.usoft.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.State;
import com.usoft.model.Userrole;
import com.usoft.model.UserrolePK;
import com.usoft.repo.UserroleRepositoryInt;

@Service
public class UserroleService implements UserroleServiceInt {
	
	@Autowired
	private UserroleRepositoryInt userroleRepository;
	
	@Override
	@Transactional
	public void createUserrole(Userrole userrole) {
		userroleRepository.save(userrole);
	}


	@Override
	@Transactional
	public void deleteUserrole(UserrolePK userrole) {
		userroleRepository.delete(userrole);
	}


	@Override
	public Iterable<Userrole> findAll() {
		return userroleRepository.findAll();
	}


	@Override
	public Userrole findUserrole(UserrolePK id) {
		return userroleRepository.find(id);
	}

}
