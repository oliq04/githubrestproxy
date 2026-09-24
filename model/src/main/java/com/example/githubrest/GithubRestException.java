package com.example.githubrest;

public class GithubRestException extends RuntimeException {
    private final Integer status;

    public GithubRestException(String message, Integer status) {
        super(message);
        this.status = status;
    }
}
