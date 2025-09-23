package com.example.caiosystems.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.caiosystems.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetailsService detailsService;

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http
	) throws Exception {
		return http
			.csrf(custom -> custom.disable())
			.authorizeHttpRequests(request -> 
				request
					.requestMatchers(
						"/h2-console/**", 
						"/user/register", 
						"/user/login")
					.permitAll()
					.anyRequest()
					.authenticated())
			.httpBasic(Customizer.withDefaults())
			.headers(headers -> 
				headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
			.sessionManagement(session -> 
				session.sessionCreationPolicy(SessionCreationPolicy.ALWAYS))
			.build();
	}
	
	@Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setPasswordEncoder(passwordEncoder());
		provider.setUserDetailsService(detailsService);
		return provider;
	}
	
	@Bean
	public AuthenticationManager authManager(
		AuthenticationConfiguration config
	) throws Exception {
		return config.getAuthenticationManager();
	}
}
