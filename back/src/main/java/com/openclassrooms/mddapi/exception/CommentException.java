package com.openclassrooms.mddapi.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommentException extends RuntimeException{
    private String message;
}
