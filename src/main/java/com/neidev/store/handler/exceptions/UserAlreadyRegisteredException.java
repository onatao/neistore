package com.neidev.store.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserAlreadyRegisteredException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UserAlreadyRegisteredException(String message) {
		super(message);
	}
}