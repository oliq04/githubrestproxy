package com.example.githubrest.controller;

import com.example.githubrest.GithubRepositoryInfoDto;
import com.example.githubrest.GithubRepositoryMapper;
import com.example.githubrest.GithubService;
import com.example.githubrest.Repository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/repositories")
public class GithubController {

    private final GithubService githubService;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @GetMapping("/repo/{owner}/{repoName}")
    public GithubRepositoryInfoDto getRepositoryInfo(@PathVariable("owner") String owner, @PathVariable("repoName") String repoName) {
        Repository repository = githubService.getRepositoryInfo(owner, repoName);
        return githubRepositoryMapper.toDtoFromPojo(repository);
    }

    @PostMapping("/{owner}/{repository-name}")
    @ResponseStatus(HttpStatus.CREATED)
    public GithubRepositoryInfoDto createRepository(@PathVariable("owner") String owner,
                                              @PathVariable("repository-name") String repositoryName) {
        Repository repository = githubService.createRepository(owner, repositoryName);
        return githubRepositoryMapper.toDtoFromPojo(repository);
    }


    @PutMapping("/{owner}/{repository-name}")
    public GithubRepositoryInfoDto updateRepository(@PathVariable("owner") String owner,
                                              @PathVariable("repository-name") String repositoryName) {
        Repository repository = githubService.updateRepository(owner, repositoryName);
        return githubRepositoryMapper.toDtoFromPojo(repository);
    }

    @DeleteMapping("/{owner}/{repository-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepository(@PathVariable("owner") String owner,
                                 @PathVariable("repository-name") String repositoryName) {
        githubService.deleteLocalRepositoryInfo(owner, repositoryName);
    }
}
