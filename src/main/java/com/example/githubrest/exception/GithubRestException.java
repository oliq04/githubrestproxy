package com.example.githubrest.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
public class GithubRestException extends RuntimeException {
    private HttpStatus status;

    public GithubRestException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }
}
