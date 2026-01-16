package com.example.caiosystems.service.userclient.model;

import com.example.caiosystems.infrastructure.entity.UserClient;

public interface UserClientSaverAndConcurrencyHandler {
	UserClient save(UserClient user);
}