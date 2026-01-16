package com.example.caiosystems.infrastructure.entity.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateUserClientDTO {

	private static final String ERR_PASSWORD = "Invalid password lenght, minimum 8 characters required";
	
    @NotBlank(
    	message="Username cannot be blank")
    @Email(
    	message="Invalid e-mail format")
    private String username;
    
	@Size(
		min=8, 
		message=ERR_PASSWORD)
    @NotBlank(
    	message="Password cannot be blank")
	private String password;
}