package com.openclassrooms.mddapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthException extends RuntimeException{
    private String message;
}
