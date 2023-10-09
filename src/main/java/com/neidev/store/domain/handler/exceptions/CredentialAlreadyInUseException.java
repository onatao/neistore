package com.neidev.store.domain.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class CredentialAlreadyInUseException extends RuntimeException {

    public CredentialAlreadyInUseException(String message) {
        super(message);
    }
}
