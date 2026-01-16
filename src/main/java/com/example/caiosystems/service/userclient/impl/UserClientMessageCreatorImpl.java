package com.example.caiosystems.service.userclient.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.example.caiosystems.service.userclient.model.UserClientMessageCreator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserClientMessageCreatorImpl implements UserClientMessageCreator {
	
	private final MessageSource messageSource;
	
	@Override
	public String createUserAlreadyExistsMsg(String username) {
		return messageSource.getMessage(
			"userAlreadyExists.username", 
			new Object[] {username},
			LocaleContextHolder.getLocale());
	}

	@Override
	public String createUserNotFoundMsg(String username) {
		return messageSource.getMessage(
			"userNotFound.username", 
			new Object[] {username},
			LocaleContextHolder.getLocale());
	}
	
	@Override
	public String createUserNotFoundMsg(Long id) {
		return messageSource.getMessage(
			"userNotFound.id", 
			new Object[] {id},
			LocaleContextHolder.getLocale());
	}

	@Override
	public String createConcurrentUserClient(String username) {
		return messageSource.getMessage(
			"concurrentUserClient.username", 
			new Object[] {username},
			LocaleContextHolder.getLocale());
	}
}