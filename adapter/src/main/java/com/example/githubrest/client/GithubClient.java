package com.example.githubrest.client;


import com.example.githubrest.GithubRepositoryInfoDto;
import com.example.githubrest.config.FeignConfiguration;
import com.example.githubrest.config.GithubClientFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "github", configuration = FeignConfiguration.class, fallbackFactory = GithubClientFallbackFactory.class)
public interface GithubClient {
    @RequestMapping(method = RequestMethod.GET, value = "/repos/{owner}/{repo}")
    GithubRepositoryInfoDto getRepositoryInfo(@PathVariable("owner") String owner, @PathVariable("repo") String repositoryName);
}

