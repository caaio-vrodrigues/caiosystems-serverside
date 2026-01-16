package com.example.caiosystems.service.userclient.impl;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.example.caiosystems.customexception.ConcurrentUserClientException;
import com.example.caiosystems.customexception.UserAlreadyExistsException;
import com.example.caiosystems.customexception.UserNotFoundException;
import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;
import com.example.caiosystems.service.userclient.model.UserClientFinder;
import com.example.caiosystems.service.userclient.model.UserClientMessageCreator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientFinderImpl implements UserClientFinder {
	
	private final UserClientRepository repo;
	private final UserClientMessageCreator userClientMessageCreator;

	@Override
	public UserClient findByUsername(String userName) {
		String userNotFoundMsg = userClientMessageCreator
			.createUserNotFoundMsg(userName);
		return repo.findByUsername(userName)
			.orElseThrow(() -> new UserNotFoundException(userNotFoundMsg));
	}

	@Override
	public UserClient findByUsernameOnCreate(String username) {
		Optional<UserClient> userOptional = repo.findByUsername(username);
		String userAlreadyExistsMsg = userClientMessageCreator
			.createUserAlreadyExistsMsg(username);
		if(userOptional.isEmpty()) 
			return null;
		throw new UserAlreadyExistsException(userAlreadyExistsMsg);
	}

	@Override
	public void findByUsernameOnSave(String username) {
		Optional<UserClient> userOptional = repo.findByUsername(username);
		String userAlreadyExistsMsg = userClientMessageCreator
			.createUserAlreadyExistsMsg(username);
		String concurrentUserMsg = userClientMessageCreator
			.createConcurrentUserClient(username);
		if(userOptional.isPresent()) 
			throw new UserAlreadyExistsException(userAlreadyExistsMsg);
		throw new ConcurrentUserClientException(concurrentUserMsg);
	}
}