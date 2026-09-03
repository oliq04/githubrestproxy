package com.example.githubrest.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GithubRepositoryExceptionHandler {
    @ExceptionHandler(GithubRestException.class)
    ResponseEntity<ErrorMessage> handleGithubRepositoryException(GithubRestException exception) {
        return ResponseEntity.status(exception.getStatus())
                .body(new ErrorMessage(LocalDateTime.now(), exception.getMessage(), exception.getStatus()));
    }
}
