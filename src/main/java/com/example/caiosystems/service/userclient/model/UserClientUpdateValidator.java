package com.example.caiosystems.service.userclient.model;

public interface UserClientUpdateValidator {

	String validateUsername(String currentUsername, String newUserName);
	String validatePassword(String currentPassword, String newPassword);
}