package com.neidev.store.handler;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.neidev.store.handler.exceptions.ExceptionResponse;
import com.neidev.store.handler.exceptions.UserAlreadyRegisteredException;

@RestController
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<ExceptionResponse> handlerAllExceptions(Exception e, WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(
				new Date(), 
				e.getMessage(), 
				request.getDescription(false)
			);
		return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(UserAlreadyRegisteredException.class)
	public final ResponseEntity<ExceptionResponse> handlerBadRequestExceptions(Exception e, WebRequest request) {
		ExceptionResponse exception = new ExceptionResponse(
				new Date(), 
				e.getMessage(), 
				request.getDescription(false)
			);
		return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
	}
}
