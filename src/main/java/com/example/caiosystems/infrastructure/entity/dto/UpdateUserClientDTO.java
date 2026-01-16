package com.example.caiosystems.infrastructure.entity.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateUserClientDTO {
	
	private String username;
	private String password;
}