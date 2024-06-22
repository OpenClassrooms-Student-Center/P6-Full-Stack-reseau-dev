package com.openclassrooms.mddapi.handler;

import com.openclassrooms.mddapi.dto.ResponseDTO;
import javax.persistence.EntityExistsException;
import javax.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    /*
        * Handle all exceptions
        * EntityExistsException: Throwed when a user already exists
        * BadCredentialsException: Throwed when a user try to authenticate with bad credentials
        * ConstraintViolationException: Throwed when a constraint is violated (@NotNull, @NotEmpty, etc.)
        * Exception: Other exceptions
     */
    @ExceptionHandler(value = { EntityExistsException.class, BadCredentialsException.class, ConstraintViolationException.class, Exception.class })
    protected ResponseEntity<?> handleException(Exception ex) {

        // Most of the time, the error is a bad request
        HttpStatus status = HttpStatus.BAD_REQUEST;

        if (ex instanceof EntityExistsException || ex instanceof ConstraintViolationException) {
            return ResponseEntity.status(status).body(new HashMap<>());
        }
        else{
            if(ex instanceof BadCredentialsException){
                status = HttpStatus.UNAUTHORIZED;
            }
            return ResponseEntity.status(status).body(new ResponseDTO("error"));
        }

    }

}
