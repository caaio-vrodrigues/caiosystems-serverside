package com.example.caiosystems.infrastructure.entity.dto;

import com.example.caiosystems.service.userclient.model.UserClientMessageCreator;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserClientDTO {
	
	private final UserClientMessageCreator userClientMessageCreator;
	
    @NotBlank(
    	message="{usernameNotBlank}")
    @Email(
    	message="{username_invalidEmailFormat}")
    private String username;
    
    @NotBlank(
    	message="{passwordNotBlank}")
    @Size(
		min=8, 
		message="{passwordMinLength}")
	private String password;
}