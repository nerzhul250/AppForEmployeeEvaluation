package com.usoft.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.usoft.model.Usuario;
import com.usoft.repo.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserRepository userRepository;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Usuario user=null;
		try {
			user = userRepository.findByEmail(username);
		}catch(Exception e) {
			throw new UsernameNotFoundException("User Not Found with username: " + username);
		}
		return UserDetailsImpl.build(user);
	}

}