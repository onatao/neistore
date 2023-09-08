package com.neidev.store.handler.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProdutAlreadyRegisteredException extends RuntimeException {

    public ProdutAlreadyRegisteredException(String message) {
        super(message);
    }
}
