package com.example.githubrest.client;

import com.example.githubrest.model.RepositoryInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "github", url = "https://api.github.com")
public interface GithubClient {
    @RequestMapping(method = RequestMethod.GET, value = "/repos/{owner}/{repo}")
    RepositoryInfo getRepositoryInfo(@PathVariable("owner") String owner, @PathVariable("repo") String repositoryName);
}
