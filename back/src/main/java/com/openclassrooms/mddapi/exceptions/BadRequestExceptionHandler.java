package com.openclassrooms.mddapi.exceptions;

public class BadRequestExceptionHandler extends RuntimeException{

    public BadRequestExceptionHandler(String message) {
        super(message);
    }

    public BadRequestExceptionHandler(String message, Throwable cause) {
        super(message, cause);
    }
}
