package com.example.githubrest.config;

import com.example.githubrest.GithubClientProvider;
import com.example.githubrest.GithubService;
import com.example.githubrest.RepositoryProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GithubProxyAppConfiguration {

    @Bean
    GithubService githubService(RepositoryProvider repositoryProvider, GithubClientProvider githubClientProvider) {
        return new GithubService(repositoryProvider, githubClientProvider);
    }
}
