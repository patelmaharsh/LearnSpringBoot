package com.example.demo.advice;

import java.util.NoSuchElementException;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
public class MyControllerAdvice {

	@ExceptionHandler({EmptyResultDataAccessException.class,NoSuchElementException.class})
	public ResponseEntity<String> handleEmptyResultDataAccessException(Exception emptyResultException) {
		return new ResponseEntity<String>("No value is present in DB. Please change your request", HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	public ResponseEntity<String> handleHttpRequestMethodNotSupportedException(Exception methodNotSupported){
		return new ResponseEntity<String>("Invalid url. Please change your url", HttpStatus.METHOD_NOT_ALLOWED);
	}
	
	@ExceptionHandler({MethodArgumentTypeMismatchException.class})
	public ResponseEntity<String> handleMethodArgumentTypeMismatchException(Exception exception){
		return new ResponseEntity<String>("Invalid url. Please change your url", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler({HttpMessageNotReadableException.class})
	public ResponseEntity<String> handleHttpMessageNotReadableException(Exception exception){
		return new ResponseEntity<String>("Invalid Data. Update request body", HttpStatus.BAD_REQUEST);
	}
	

}
