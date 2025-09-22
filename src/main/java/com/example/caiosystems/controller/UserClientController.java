package com.example.caiosystems.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.service.UserClientService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class UserClientController {
	
	private final UserClientService service;
	
	@PostMapping
	public ResponseEntity<UserClient> newUser(
			@RequestBody UserClient body
	){
		return ResponseEntity.ok(service.createUser(body));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserClient> findUserById(@PathVariable Long id) {
		return ResponseEntity.ok(service.searchUserById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UserClient>> listUsers(){
		return ResponseEntity.ok(service.searchAllUsers());
	}
	
	@PutMapping
	public ResponseEntity<UserClient> editUser(
			Long id, 
			@RequestBody UserClient body
	){
		return ResponseEntity.ok(service.updateUser(id, body));
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(@PathVariable Long id){
		service.deleteUser(id);
		return ResponseEntity.ok("Usuário com id: "+id+" deletado com sucesso");
	}
}
