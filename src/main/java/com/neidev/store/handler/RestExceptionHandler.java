package com.neidev.store.handler;

import com.neidev.store.handler.exceptions.BadRequestException;
import com.neidev.store.handler.exceptions.CredentialAlreadyInUseException;
import com.neidev.store.handler.exceptions.ResourceNotFoundException;
import com.neidev.store.handler.exceptions.UserAlreadyRegisteredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

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

    @ExceptionHandler(BadRequestException.class)
    public final ResponseEntity<ExceptionResponse> handlerBadRequestException(Exception e, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CredentialAlreadyInUseException.class)
    public final ResponseEntity<ExceptionResponse> handlerConflictException(Exception e, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public final ResponseEntity<ExceptionResponse> handlerResourceNotFoundException(Exception e, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse(
          new Date(),
          e.getMessage(),
          request.getDescription(false)
        );
        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserAlreadyRegisteredException.class)
    public final ResponseEntity<ExceptionResponse> handlerUserAlreadyRegisteredException(Exception e, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }

    public final ResponseEntity<ExceptionResponse> handlerProductAlreadyRegisteredException(Exception e, WebRequest request) {
        ExceptionResponse exception = new ExceptionResponse(
                new Date(),
                e.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(exception, HttpStatus.CONFLICT);
    }
}
