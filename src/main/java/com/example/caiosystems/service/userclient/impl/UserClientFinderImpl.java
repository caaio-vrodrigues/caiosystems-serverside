package com.example.caiosystems.service.userclient.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.caiosystems.customexception.ConcurrentUserClientException;
import com.example.caiosystems.customexception.UserAlreadyExistsException;
import com.example.caiosystems.customexception.UserNotFoundException;
import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;
import com.example.caiosystems.service.userclient.model.UserClientFinder;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientFinderImpl implements UserClientFinder {
	
	private final UserClientRepository repo;

	@Override
	public UserClient findByUsername(String userName) {
		return repo.findByUsername(userName)
			.orElseThrow(() -> new UserNotFoundException("Não foi possível encontrar usuário para o e-mail: `"+userName+"`"));
	}

	@Override
	public UserClient findByUsernameOnCreate(String username) {
		Optional<UserClient> userOptional = repo.findByUsername(username);
		if(userOptional.isEmpty()) return null;
			throw new UserAlreadyExistsException("O e-mail: `"+username+"` já está em uso");
	}

	@Override
	public void findByUsernameOnSave(String username) {
		Optional<UserClient> userOptional = repo.findByUsername(username);
		if(userOptional.isPresent()) 
			throw new UserAlreadyExistsException("Falha ao tentar criar usuário. O e-mail: `"+username+"` já está em uso");
		throw new ConcurrentUserClientException("Falha ao tentar salvar novo usuário: `"+username+"`");
	}
}