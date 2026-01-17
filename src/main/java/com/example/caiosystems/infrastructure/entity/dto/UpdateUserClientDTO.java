package com.example.caiosystems.infrastructure.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserClientDTO {
	
	@Email(message="{username_invalidEmailFormat}")
	private String username;
	
	@Size(min=8, message="{passwordMinLength}")
	private String password;
}