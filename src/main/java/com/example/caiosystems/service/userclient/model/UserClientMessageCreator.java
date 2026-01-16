package com.example.caiosystems.service.userclient.model;

public interface UserClientMessageCreator {
	String createUserAlreadyExistsMsg(String username);
	String createUserNotFoundMsg(String username);
	String createConcurrentUserClient(String username);
}