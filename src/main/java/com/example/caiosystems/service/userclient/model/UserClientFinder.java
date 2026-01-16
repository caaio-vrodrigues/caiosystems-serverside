package com.example.caiosystems.service.userclient.model;

import com.example.caiosystems.infrastructure.entity.UserClient;

public interface UserClientFinder {
	UserClient findById(Long id);
	UserClient findByUsername(String userName);
	UserClient findByUsernameOnCreate(String username);
	void findByUsernameOnSave(String username);
}