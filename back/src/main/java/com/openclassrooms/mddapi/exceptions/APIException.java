package com.openclassrooms.mddapi.exceptions;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public class APIException {
    private final String message;
    private final Throwable throwable;
    private final HttpStatus httpStatus;
    private final LocalDateTime timeStamp;

    public APIException(String message, Throwable throwable, HttpStatus httpStatus, LocalDateTime timeStamp) {
        this.message = message;
        this.throwable = throwable;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }
}
