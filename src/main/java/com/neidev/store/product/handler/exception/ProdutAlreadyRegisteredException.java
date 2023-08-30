package com.neidev.store.product.handler.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class ProdutAlreadyRegisteredException extends RuntimeException {

    public ProdutAlreadyRegisteredException(String message) {
        super(message);
    }
}
