package com.example.caiosystems.security;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.caiosystems.customexception.CustomAuthEntryPoint;
import com.example.caiosystems.customexception.ResourceNotFoundException;
import com.example.caiosystems.service.MyUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private MyUserDetailsService detailsService;
	private final CustomAuthEntryPoint customAuthEntryPoint;
	private static final String CLIENT_URL = System.getenv("CLIENT_URL");
	
	public SecurityConfig(CustomAuthEntryPoint customAuthEntryPoint) {
        this.customAuthEntryPoint = customAuthEntryPoint;
    }

	@Bean
	public SecurityFilterChain securityFilterChain(
		HttpSecurity http
	) throws Exception {
		return http
			.csrf(custom -> custom.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
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
			.exceptionHandling(exception -> exception
				.authenticationEntryPoint(customAuthEntryPoint))
			.build();
	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		List<String> allowedOrigins = Optional.ofNullable(CLIENT_URL)
	        .filter(url -> !url.isEmpty())
	        .map(url -> Arrays.asList(url.split(",")))
	        .orElseThrow(() -> new ResourceNotFoundException(
	        	"Environment variable not found"));
		configuration.setAllowedOrigins(allowedOrigins);
		configuration.setAllowedMethods(
			Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
		configuration.setAllowedHeaders(Arrays.asList("*"));
		configuration.setAllowCredentials(true);
		configuration.setMaxAge(3600L);
		UrlBasedCorsConfigurationSource source = new 
			UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
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
