package com.example.caiosystems.config.security.model;

public interface SecurityConfigMessageCreator {
	String createInvalidCredentialsMsg();
	String createEnvironmentVariableNotFoundMsg();
}