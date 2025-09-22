package com.example.caiosystems.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.example.caiosystems.customexception.ResourceNotFoundException;

@RestControllerAdvice
public class ExceptionHandlerController {
	
	private static final String TIMESTAMP_KEY = "timestamp";
	private static final String STATUS = "status";
	private static final String ERROR = "error";
	private static final String MESSAGE = "message";

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Object> handleResourceNotFound(
		ResourceNotFoundException e
	){
		Map<String, Object> body = new LinkedHashMap<>();
		body.put(TIMESTAMP_KEY, LocalDateTime.now());
		body.put(STATUS, HttpStatus.NOT_FOUND.value());
		body.put(ERROR, "Not Found");
		body.put(MESSAGE, e.getMessage());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolation(
    	DataIntegrityViolationException e
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
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
	
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleMethodArgumentNotValid(
        MethodArgumentNotValidException e
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
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
