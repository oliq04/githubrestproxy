package com.example.githubrest;

public class RepositoryNotFoundException extends GithubRestException {
    public RepositoryNotFoundException(String message, Integer status) {
        super(message, status);
    }
}
