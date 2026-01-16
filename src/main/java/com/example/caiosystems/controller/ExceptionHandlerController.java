package com.example.caiosystems.controller;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.caiosystems.customexception.ConcurrentUserClientException;
import com.example.caiosystems.customexception.ResourceNotFoundException;
import com.example.caiosystems.customexception.UserAlreadyExistsException;
import com.example.caiosystems.customexception.UserNotFoundException;

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
		body.put(ERROR, "Recurso não encontrado");
		body.put(MESSAGE, e.getMessage());
		body.put(PATH, request.getRequestURI());
		return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
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
    
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Object> handleHttpRequestMethodNotSupportedException(
    	HttpRequestMethodNotSupportedException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.METHOD_NOT_ALLOWED.value());
        body.put(ERROR, "Método não permitido");
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
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
        body.put(ERROR, "Recurso indisponível"); 
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<Object> handleUserAlreadyExistsException(
    	UserAlreadyExistsException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.CONFLICT.value());
        body.put(ERROR, "Duplicidade de usuário"); 
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Object> handleUserNotFoundException(
    	UserNotFoundException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.NOT_FOUND.value());
        body.put(ERROR, "Usuário não encontrado"); 
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(ConcurrentUserClientException.class)
    public ResponseEntity<Object> handleConcurrentUserClientException(
    	ConcurrentUserClientException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.CONFLICT.value());
        body.put(ERROR, "Falha de concorrência"); 
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.CONFLICT);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Object> handleHttpMessageNotReadableException(
    	HttpMessageNotReadableException e,
        HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Request inválida");
        body.put(MESSAGE, e.getMessage()); 
        body.put(PATH, request.getRequestURI()); 
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
        body.put(ERROR, "Argumento inválido"); 
        body.put(MESSAGE, e.getMessage());
        body.put(PATH, request.getRequestURI()); 
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
    
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(
    	ConstraintViolationException e,
    	HttpServletRequest request
    ){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put(TIMESTAMP_KEY, LocalDateTime.now());
        body.put(STATUS, HttpStatus.BAD_REQUEST.value());
        body.put(ERROR, "Erro de validação de dados");
        body.put(MESSAGE, e.getMessage());
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
        body.put(ERROR, "Tipo de argumento inválido");
        body.put(MESSAGE, e.getMessage());
        body.put(PATH, request.getRequestURI());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
    }
}
