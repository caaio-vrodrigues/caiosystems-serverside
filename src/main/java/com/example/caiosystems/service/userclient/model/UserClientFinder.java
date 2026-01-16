package com.example.caiosystems.service.userclient.model;

import com.example.caiosystems.infrastructure.entity.UserClient;

public interface UserClientFinder {
	UserClient findByUsername(String userName);
	UserClient findByUsernameOnCreate(String username);
}