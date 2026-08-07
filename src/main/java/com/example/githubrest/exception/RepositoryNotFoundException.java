package com.example.githubrest.exception;

import org.springframework.http.HttpStatus;

public class RepositoryNotFoundException extends GithubRestException {
    public RepositoryNotFoundException(String message, HttpStatus status) {
        super(message, status);
    }
}
