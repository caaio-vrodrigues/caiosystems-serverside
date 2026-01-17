package com.example.caiosystems.infrastructure.entity.dto;

import com.example.caiosystems.serialization.TrimmedStringDeserializer;
import com.example.caiosystems.service.userclient.model.UserClientMessageCreator;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class CreateUserClientDTO {
	
	private final UserClientMessageCreator userClientMessageCreator;
    
    @Email(message="{username_invalidEmailFormat}")
    @NotBlank(message="{usernameNotBlank}")
    @JsonDeserialize(using=TrimmedStringDeserializer.class)
    private String username;
    
    @Size(min=8, message="{passwordMinLength}")
    @NotBlank(message="{passwordNotBlank}")
    @JsonDeserialize(using=TrimmedStringDeserializer.class)
	private String password;
}