package com.example.caiosystems.infrastructure.entity.dto;

import com.example.caiosystems.serialization.TrimmedStringDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
public class UpdateUserClientDTO {
	
	@Email(message="{username_invalidEmailFormat}")
	@JsonDeserialize(using=TrimmedStringDeserializer.class)
	private String username;
	
	@Size(min=8, message="{passwordMinLength}")
	@JsonDeserialize(using=TrimmedStringDeserializer.class)
	private String password;
}