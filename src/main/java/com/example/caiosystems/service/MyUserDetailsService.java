package com.example.caiosystems.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.entity.UserClientPrincipals;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserClientRepository repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		UserClient user = repo.findByUsername(username).orElseThrow(()->
			new UsernameNotFoundException("User "+username+" not found"));
		return new UserClientPrincipals(user);
	}
}
