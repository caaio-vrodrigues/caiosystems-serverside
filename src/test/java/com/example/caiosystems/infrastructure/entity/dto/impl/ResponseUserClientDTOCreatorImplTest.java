package com.example.caiosystems.infrastructure.entity.dto.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.example.caiosystems.infrastructure.entity.dto.ResponseUserClientDTO;

@SpringBootTest
@DirtiesContext(classMode=ClassMode.BEFORE_EACH_TEST_METHOD)
class ResponseUserClientDTOCreatorImplTest {
	
	@Autowired private ResponseUserClientDTOCreatorImpl responseUserClientDTOCreatorImpl;
	
	private static final String USERNAME = "caio@teste.com";
	
	@Test
	@DisplayName("Deve criar DTO ao receber 'username'")
	void createResponseUserClientDTO_returnsResponseUserClientDTO() {
		ResponseUserClientDTO dto = responseUserClientDTOCreatorImpl
			.createResponseUserClientDTO(USERNAME);
		assertNotNull(dto);
		assertEquals(USERNAME, dto.getUsername());
	}
}