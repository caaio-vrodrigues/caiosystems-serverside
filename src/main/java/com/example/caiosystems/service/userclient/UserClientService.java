package com.example.caiosystems.service.userclient;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.entity.dto.CreateUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.ResponseUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.UpdateUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.model.ResponseUserClientDTOCreator;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;
import com.example.caiosystems.service.userclient.model.UserClientFinder;
import com.example.caiosystems.service.userclient.model.UserClientSaverAndConcurrencyHandler;
import com.example.caiosystems.service.userclient.model.UserClientUpdateValidator;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserClientService {
	
	private final BCryptPasswordEncoder cryptedPassword;
	private final UserClientRepository repo;
	private final AuthenticationManager authenticationManager; 
	private final UserClientFinder userClientFinder;
	private final UserClientSaverAndConcurrencyHandler userClientSaverAndConcurrencyHandler;
	private final ResponseUserClientDTOCreator responseUserClientDTOCreator;
	private final UserClientUpdateValidator userClientUpdateValidator;
	
	@Transactional
	public ResponseUserClientDTO createUser(CreateUserClientDTO body) {
		userClientFinder.findByUsernameOnCreate(body.getUsername());
		UserClient user = UserClient.builder()
			.password(cryptedPassword.encode(body.getPassword()))
			.username(body.getUsername())
			.build();
		userClientSaverAndConcurrencyHandler.save(user);
		return responseUserClientDTOCreator
			.createResponseUserClientDTO(user.getUsername());
	}
	
	@Transactional(readOnly=true)
	public ResponseUserClientDTO searchUserById(Long id) {
		UserClient user = userClientFinder.findById(id);
		return responseUserClientDTOCreator
			.createResponseUserClientDTO(user.getUsername());
	}
	
	@Transactional(readOnly=true)
	public List<ResponseUserClientDTO> searchAllUsers() {
		return repo.findAll().stream()
			.map(user -> responseUserClientDTOCreator
				.createResponseUserClientDTO(user.getUsername()))
			.toList();
	}
	
	@Transactional
	public ResponseUserClientDTO updateUser(Long id, UpdateUserClientDTO dto) {
		UserClient user = userClientFinder.findById(id);
		String newUsername = userClientUpdateValidator.validateUsername(
			user.getUsername(), 
			dto.getUsername());
		String newPassword = userClientUpdateValidator.validatePassword(
			user.getPassword(), 
			dto.getPassword());
		user = UserClient.builder()
			.id(user.getId())
			.username(newUsername)
			.password(newPassword)
			.build();
		userClientSaverAndConcurrencyHandler.save(user);
		return responseUserClientDTOCreator
			.createResponseUserClientDTO(user.getUsername());
	}
	
	@Transactional
	public boolean deleteUser(Long id) {
		userClientFinder.findById(id);
		repo.deleteById(id);
		return true;
	}
	
	@Transactional(readOnly=true)
	public boolean verifyUser(String username, String rawPassword) {
        UsernamePasswordAuthenticationToken authToken =
        	new UsernamePasswordAuthenticationToken(username, rawPassword);
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth.isAuthenticated();
    }
}