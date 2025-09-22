package com.example.caiosystems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClientService {
	
	private final UserClientRepository repo;
	
	public UserClient createUser(UserClient body) {
		return repo.saveAndFlush(body);
	}
	
	public UserClient searchUserById(Long id) {
		return repo.findById(id).orElseThrow(()->
			new RuntimeException("User not found"));
	}
	
	public List<UserClient> searchAllUsers() {
		return repo.findAll();
	}
	
	public UserClient updateUser(Long id, UserClient body) {
		UserClient existsUser = searchUserById(id);
		body.setId(existsUser.getId());
		return repo.saveAndFlush(body);
	}
	
	public void deleteUser(Long id) {
		repo.deleteById(id);
	}
}
