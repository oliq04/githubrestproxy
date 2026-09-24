package com.example.githubrest.controller;

import com.example.githubrest.GithubRepositoryInfoDto;
import com.example.githubrest.GithubRepositoryMapper;
import com.example.githubrest.GithubService;
import com.example.githubrest.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class GithubLocalController {

    private final GithubService githubService;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @GetMapping("/local/repositories/{owner}/{repository-name}")
    public GithubRepositoryInfoDto getLocalRepository(@PathVariable("owner") String owner,
                                                      @PathVariable("repository-name") String repositoryName) {
        Repository repository = githubService.getLocalRepository(owner, repositoryName);
        return githubRepositoryMapper.toDtoFromPojo(repository);
    }
}
