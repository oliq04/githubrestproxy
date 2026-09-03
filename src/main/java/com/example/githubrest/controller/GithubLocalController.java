package com.example.githubrest.controller;

import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GithubLocalController {

    private final GithubService githubService;

    @GetMapping("/local/repositories/{owner}/{repository-name}")
    public RepositoryInfoDto getLocalRepository(@PathVariable("owner") String owner,
                                                @PathVariable("repository-name") String repositoryName) {
        return githubService.getLocalRepository(owner, repositoryName);
    }
}
