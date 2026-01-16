package com.example.caiosystems.service.userclient.impl;

import org.springframework.stereotype.Component;

import com.example.caiosystems.service.userclient.model.UserClientUpdateValidator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientUpdateValidatorImpl implements UserClientUpdateValidator {

	@Override
	public String validateUsername(String currentUsername, String newUserName) {
		boolean usernameNotNullAndIsDifferent = newUserName != null && 
			!currentUsername.equals(newUserName);
		if(usernameNotNullAndIsDifferent) return newUserName;
		return currentUsername;
	}

	@Override
	public String validatePassword(String currentPassword, String newPassword) {
		boolean usernameNotNullAndIsDifferent = newPassword != null && 
			!currentPassword.equals(newPassword);
		if(usernameNotNullAndIsDifferent) return newPassword;
		return currentPassword;
	}
}