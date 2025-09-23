package com.example.caiosystems.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.caiosystems.customexception.ResourceNotFoundException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	private static final String TIMESTAMP_KEY = "timestamp";
	private static final String STATUS = "status";
	private static final String ERROR = "error";
	private static final String MESSAGE = "message";
	private static final String PATH = "path";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFound(
		ResourceNotFoundException e,
		HttpServletRequest request
	){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP_KEY, LocalDateTime.now());
		body.put(STATUS, HttpStatus.NOT_FOUND.value());
		body.put(ERROR, "Not Found");
		body.put(MESSAGE, e.getMessage());
		body.put(PATH, request.getRequestURI());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(
    	DataIntegrityViolationException e,
    	HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        String errorMessage = 
        	"Data integrity violation, verify the values before sending";
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.CONFLICT.value());
        body.put(ERROR, "Data Conflict");
        Throwable rootCause = e.getRootCause();
        if (rootCause != null && rootCause.getMessage() != null) {
            String rootCauseMessage = rootCause.getMessage().toLowerCase();
            if (rootCauseMessage.contains("unique") && 
            		rootCauseMessage.contains("username")
            ){
                errorMessage = "This username (e-mail) is not available";
                body.put(ERROR, "Duplicated E-mail");
            }
        }
        body.put(MESSAGE, errorMessage);
        body.put(PATH, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        String errorMessage = "Validation failed";
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Bad Request");
        if (e.getBindingResult().hasErrors()) {
            errorMessage = e.getBindingResult()
            	.getAllErrors()
            	.get(0)
            	.getDefaultMessage();
        }
        body.put(MESSAGE, errorMessage);
        body.put(PATH, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(
        AuthenticationException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.UNAUTHORIZED.value());
        body.put(ERROR, "Unauthorized Action"); 
        body.put(MESSAGE, e.getMessage()); 
        body.put("path", request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.UNAUTHORIZED);
    }
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
    	HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put(ERROR, "Method Not Allowed");
        body.put(MESSAGE, e.getMessage()+", verify the url before sending"); 
        body.put("path", request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Object> handleNoResourceFoundException(
    	NoResourceFoundException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.NOT_FOUND.value());
        body.put(ERROR, "No Resource Available"); 
        body.put(MESSAGE, "Invalid URL path"); 
        body.put("path", request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
    	HttpMessageNotReadableException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Bad Request Format");
        body.put(MESSAGE, e.getMessage()); 
        body.put("path", request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Object> handleIllegalArgumentException(
    	IllegalArgumentException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value()); 
        body.put(ERROR, "Invalid Argument"); 
        body.put(MESSAGE, e.getMessage());
        body.put("path", request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
    	ConstraintViolationException e,
    	HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        String errorMsg = "Invalid format of data";
        if (e.getMessage().contains("Invalid e-mail format")) 
        	errorMsg = "Invalid e-mail format";
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Invalid Format");
        body.put(MESSAGE, errorMsg);
        body.put(PATH, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Object> handleMethodArgumentTypeMismatchException(
    	MethodArgumentTypeMismatchException e,
    	HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Method Type Mismatch");
        body.put(MESSAGE, "Method and request mismatch");
        body.put(PATH, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
