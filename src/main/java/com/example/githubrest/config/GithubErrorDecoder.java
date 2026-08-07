package com.example.githubrest.config;

import feign.FeignException;
import feign.Response;
import feign.RetryableException;
import feign.codec.ErrorDecoder;

public class GithubErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {

        int status = response.status();
        FeignException exception = feign.FeignException.errorStatus(methodKey, response);
        if(status == 503) {
            return new RetryableException(
                    response.status(),
                    "Retrying",
                    response.request().httpMethod(),
                    exception,
                    50L,
                    response.request()
            );
        }
        return exception;
    }
}
