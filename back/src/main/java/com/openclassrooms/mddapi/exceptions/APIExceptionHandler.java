package com.openclassrooms.mddapi.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import java.time.LocalDateTime;

@ControllerAdvice
public class APIExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {BadRequestExceptionHandler.class})
    public ResponseEntity<Object> handleAPIBadRequestException(BadRequestExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                e,
                HttpStatus.BAD_REQUEST,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.BAD_REQUEST);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {NotFoundExceptionHandler.class})
    public ResponseEntity<Object> handleAPINotFoundException(NotFoundExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                e,
                HttpStatus.NOT_FOUND,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.NOT_FOUND);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {ForbidenExceptionHandler.class})
    public ResponseEntity<Object> handleAPIForbidenException(ForbidenExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                e,
                HttpStatus.FORBIDDEN,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.FORBIDDEN);
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(value = {UnauthorizedExceptionHandler.class})
    public ResponseEntity<Object> handleAPIUnauthorizedException(UnauthorizedExceptionHandler e) {
        APIException exception = new APIException(
                e.getMessage(),
                e,
                HttpStatus.UNAUTHORIZED,
                LocalDateTime.now()
        );

        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }
}
