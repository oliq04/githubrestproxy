package com.example.githubrest.controller;

import com.example.githubrest.client.GithubClient;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GithubController {

    private final GithubClient githubClient;
    private final GithubService githubService;

    @GetMapping("/repo/{owner}/{repoName}")
    public RepositoryInfoDto getRepositoryInfo(@PathVariable("owner") String owner, @PathVariable("repoName") String repoName) {
        RepositoryInfo repositoryInfo = githubClient.getRepositoryInfo(owner, repoName);
        return githubService.getRepositoryInfo(repositoryInfo);
    }
}
