package com.example.caiosystems.infrastructure.entity.dto.impl;

import org.springframework.stereotype.Component;

import com.example.caiosystems.infrastructure.entity.dto.ResponseUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.model.ResponseUserClientDTOCreator;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ResponseUserClientDTOCreatorImpl implements ResponseUserClientDTOCreator {

	@Override
	public ResponseUserClientDTO createResponseUserClientDTO(String username) {
		return ResponseUserClientDTO.builder()
			.username(username)
			.build();
	}
}