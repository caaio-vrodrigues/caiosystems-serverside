package com.example.caiosystems.service.userclient;

import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.caiosystems.customexception.ResourceNotFoundException;
import com.example.caiosystems.infrastructure.entity.UserClient;
import com.example.caiosystems.infrastructure.entity.dto.CreateUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.ResponseUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.UpdateUserClientDTO;
import com.example.caiosystems.infrastructure.entity.dto.model.ResponseUserClientDTOCreator;
import com.example.caiosystems.infrastructure.repository.UserClientRepository;
import com.example.caiosystems.service.userclient.model.UserClientFinder;
import com.example.caiosystems.service.userclient.model.UserClientSaverAndConcurrencyHandler;

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
	
	public UserClient searchUserById(Long id) {
		return repo.findById(id).orElseThrow(()->
			new ResourceNotFoundException("Unable to find user with id: "+id+
				", verify the value before sending"));
	}
	
	public List<UserClient> searchAllUsers() {
		return repo.findAll();
	}
	
	public ResponseUserClientDTO updateUser(Long id, UpdateUserClientDTO dto) {
		UserClient user = userClientFinder.findById(id);
		return null;
	}
	
	public void deleteUser(Long id) {
		if (!repo.existsById(id)) 
			throw new ResourceNotFoundException("Unable to delete user with id: "
				+id+", verify the value before sending");
		repo.deleteById(id);
	}
	
	public boolean verifyUser(String username, String rawPassword) {
        UsernamePasswordAuthenticationToken authToken =
        	new UsernamePasswordAuthenticationToken(username, rawPassword);
        Authentication auth = authenticationManager.authenticate(authToken);
        SecurityContextHolder.getContext().setAuthentication(auth);
        return auth.isAuthenticated();
    }
}
