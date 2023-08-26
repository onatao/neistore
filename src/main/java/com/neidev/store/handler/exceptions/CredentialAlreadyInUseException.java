package com.neidev.store.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class CredentialAlreadyInUseException extends RuntimeException {

    public CredentialAlreadyInUseException(String message) {
        super(message);
    }
}
