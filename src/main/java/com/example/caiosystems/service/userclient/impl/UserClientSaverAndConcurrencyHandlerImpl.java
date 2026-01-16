package com.example.caiosystems.service.userclient.impl;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;
import com.example.caiosystems.service.userclient.model.UserClientFinder;
import com.example.caiosystems.service.userclient.model.UserClientSaverAndConcurrencyHandler;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientSaverAndConcurrencyHandlerImpl implements UserClientSaverAndConcurrencyHandler {
	
	private final UserClientRepository repo;
	private final UserClientFinder userClientFinder;

	@Override
	public UserClient save(UserClient user) {
		try {
			return repo.saveAndFlush(user);
		}
		catch(DataIntegrityViolationException e) {
			userClientFinder.findByUsernameOnSave(user.getUsername());
			return null;
		}
	}
}