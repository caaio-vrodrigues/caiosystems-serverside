package com.example.caiosystems.config.security.impl;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import com.example.caiosystems.config.security.model.SecurityConfigMessageCreator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class SecurityConfigMessageCreatorImpl implements SecurityConfigMessageCreator {
	
	private final MessageSource messageSource;
	
	@Override
	public String createInvalidCredentialsMsg() {
		return messageSource.getMessage(
			"invalidCredentials", 
			new Object[] {}, 
			LocaleContextHolder.getLocale());
	}

	@Override
	public String createEnvironmentVariableNotFoundMsg() {
		return messageSource.getMessage(
			"resourceNotFound.environmentVariable", 
			new Object[] {}, 
			LocaleContextHolder.getLocale());
	}
}