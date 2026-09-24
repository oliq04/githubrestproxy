package com.example.githubrest.config;

import com.example.githubrest.client.GithubErrorDecoder;
import feign.Logger;
import feign.Retryer;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

public class FeignConfiguration {

    @Bean
    public Retryer retryer() {
        return new Retryer.Default();
    }

    @Bean
    public ErrorDecoder errorDecoder() {
        return new GithubErrorDecoder();
    }

    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }
}
