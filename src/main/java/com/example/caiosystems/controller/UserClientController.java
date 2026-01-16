package com.example.caiosystems.controller;

import java.security.Principal;
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
import com.example.caiosystems.infrastructure.entity.dto.CreateUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.ResponseUserClientDTO;
import com.example.caiosystems.service.UserClientService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserClientController {
	
	private final UserClientService service;
	
	@PostMapping("/register")
	public ResponseEntity<ResponseUserClientDTO> newUser(
		@Valid @RequestBody CreateUserClientDTO body
	){
		return ResponseEntity.ok(service.createUser(body));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<UserClient> findUserById(
		@PathVariable Long id
	){
		return ResponseEntity.ok(service.searchUserById(id));
	}
	
	@GetMapping
	public ResponseEntity<List<UserClient>> listUsers(){
		return ResponseEntity.ok(service.searchAllUsers());
	}
	
//	@PutMapping
//	public ResponseEntity<UserClient> editUser(
//		Long id, 
//		@Valid @RequestBody UserClient body
//	){
//		return ResponseEntity.ok(service.updateUser(id, body));
//	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> deleteUser(
		@PathVariable Long id
	){
		service.deleteUser(id);
		return ResponseEntity.ok("User with id: "+id+" successfully deleted");
	}
	
	@GetMapping("/auth")
	public ResponseEntity<Boolean> isAuthenticated(Principal principal){
		return ResponseEntity.ok(principal != null);
	}
}