package com.example.caiosystems.service;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.caiosystems.customexception.ResourceNotFoundException;
import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClientService {
	
	private final UserClientRepository repo;
	private final BCryptPasswordEncoder cryptedPassword;
	private final AuthenticationManager authenticationManager; 
	
	public UserClient createUser(UserClient body) {
		body.setPassword(cryptedPassword.encode(body.getPassword()));
		return repo.saveAndFlush(body);
	}
	
	public UserClient searchUserById(Long id) {
		return repo.findById(id).orElseThrow(()->
			new ResourceNotFoundException("Unable to find user with id: "+id+
				", verify the value before sending"));
	}
	
	public List<UserClient> searchAllUsers() {
		return repo.findAll();
	}
	
	public UserClient updateUser(Long id, UserClient body) {
		UserClient existsUser = searchUserById(id);
		body.setId(existsUser.getId());
		body.setPassword(cryptedPassword.encode(body.getPassword()));
		return repo.saveAndFlush(body);
	}
	
	public void deleteUser(Long id) {
		if (!repo.existsById(id)) 
			throw new ResourceNotFoundException("Unable to delete user with id: "
				+id+", verify the value before sending");
		repo.deleteById(id);
	}
	
	public boolean verifyUser(String username, String rawPassword) {
        UsernamePasswordAuthenticationToken authToken =
        	new UsernamePasswordAuthenticationToken(username, rawPassword);
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth.isAuthenticated();
    }
}
