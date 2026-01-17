package com.example.caiosystems.config.security.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
class SecurityConfigMessageCreatorImplIntegrationTest {

	@Autowired private SecurityConfigMessageCreatorImpl securityConfigMessageCreatorImpl;
	@Autowired private MessageSource messageSource;
	
	@Test
	@DisplayName("Deve criar e retornar mensagem")
	void createInvalidCredentialsMsg_returnsString() {
		String expectedMsg = messageSource.getMessage(
			"invalidCredentials", 
			new Object[] {}, 
			LocaleContextHolder.getLocale());
		String msg = securityConfigMessageCreatorImpl
			.createInvalidCredentialsMsg();
		assertNotNull(msg);
		assertEquals(expectedMsg, msg);
	}
	
	@Test
	@DisplayName("Deve criar e retornar mensagem")
	void createEnvironmentVariableNotFoundMsg_returnsString() {
		String expectedMsg = messageSource.getMessage(
			"resourceNotFound.environmentVariable", 
			new Object[] {}, 
			LocaleContextHolder.getLocale());
		String msg = securityConfigMessageCreatorImpl
			.createEnvironmentVariableNotFoundMsg();
		assertNotNull(msg);
		assertEquals(expectedMsg, msg);
	}
}