package com.example.caiosystems.config;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import java.io.OutputStream;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.HttpStatusReturningLogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.example.caiosystems.customexception.CustomAuthEntryPoint;
import com.example.caiosystems.customexception.ResourceNotFoundException;
import com.example.caiosystems.service.MyUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletResponse;

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
		HttpSecurity http,
		ObjectMapper objectMapper
	) throws Exception {
		return http
			.csrf(custom -> custom.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.authorizeHttpRequests(request -> 
				request
					.requestMatchers(
						"/h2-console/**", 
						"/user/register", 
						"/user/auth")
					.permitAll()
					.anyRequest()
					.authenticated())
			.headers(headers -> 
				headers.frameOptions(frameOptions -> frameOptions.sameOrigin()))
			.formLogin(form -> 
				form
					.loginProcessingUrl("/user/login")
					.successHandler((request, response, authentication) -> {
						response.setStatus(HttpServletResponse.SC_OK);
		                response.getWriter().write("true"); 
		                response.getWriter().flush();
					})
					.failureHandler((request, response, exception) -> {
		                response
		                	.setContentType(MediaType.APPLICATION_JSON_VALUE);
		                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		                Map<String, Object> body = new LinkedHashMap<>();
		                body.put("timestamp", LocalDateTime.now());
		                body.put("status", HttpStatus.UNAUTHORIZED.value());
		                body.put("error", "Não autorizado");
		                body.put("message", "Credenciais inválidas. Verifique seu e-mail e senha antes de tentar novamente");
		                body.put("path", request.getRequestURI());
		                OutputStream out = response.getOutputStream();
		                objectMapper.writeValue(out, body);
		                out.flush();
					}))
			.sessionManagement(session -> 
				session
					.sessionCreationPolicy(SessionCreationPolicy.ALWAYS)
					.sessionFixation()
					.newSession())
			.exceptionHandling(exception -> 
				exception.authenticationEntryPoint(customAuthEntryPoint))
			.logout(logout -> 
				logout
		            .logoutUrl("/logout")
		            .invalidateHttpSession(true)
		            .deleteCookies("JSESSIONID")
		            .logoutSuccessHandler(
		            	new HttpStatusReturningLogoutSuccessHandler()))
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
