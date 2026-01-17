package com.example.caiosystems.config.security.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.Locale;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

@ExtendWith(MockitoExtension.class)
class SecurityConfigMessageCreatorImplTest {

	@InjectMocks private SecurityConfigMessageCreatorImpl securityConfigMessageCreatorImpl;
	@Mock private MessageSource messageSource;
	
	private static final String INVALID_CREDENTIALS_MSG = "Credenciais inválidas. Verifique seu e-mail e senha antes de tentar novamente";
	private static final String ENVIRONMENT_VARIABLE_NOT_FOUND_MSG = "Variável de ambiente não encontrada";
	
	@Test
	@DisplayName("Deve criar e retornar mensagem")
	void createInvalidCredentialsMsg_returnsString() {
		when(messageSource.getMessage(
				anyString(), 
				any(Object[].class), 
				any(Locale.class)))
			.thenReturn(INVALID_CREDENTIALS_MSG);
		String msg = securityConfigMessageCreatorImpl
			.createInvalidCredentialsMsg();
		assertNotNull(msg);
		assertEquals(INVALID_CREDENTIALS_MSG, msg);
		verify(messageSource, times(1)).getMessage(
			anyString(), 
			any(Object[].class), 
			any(Locale.class));
	}
	
	@Test
	@DisplayName("Deve criar e retornar mensagem")
	void createEnvironmentVariableNotFoundMsg_returnsString() {
		when(messageSource.getMessage(
				anyString(), 
				any(Object[].class), 
				any(Locale.class)))
			.thenReturn(ENVIRONMENT_VARIABLE_NOT_FOUND_MSG);
		String msg = securityConfigMessageCreatorImpl
			.createEnvironmentVariableNotFoundMsg();
		assertNotNull(msg);
		assertEquals(ENVIRONMENT_VARIABLE_NOT_FOUND_MSG, msg);
		verify(messageSource, times(1)).getMessage(
			anyString(), 
			any(Object[].class), 
			any(Locale.class));
	}
}