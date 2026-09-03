package com.example.githubrest.controller;

import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.service.GithubService;
import jakarta.servlet.http.HttpServletRequest;
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

    @GetMapping("/{owner}/{repoName}")
    public RepositoryInfoDto getRepositoryInfo(@PathVariable("owner") String owner,
                                               @PathVariable("repoName") String repositoryName) {

        return githubService.getRepositoryInfo(owner, repositoryName);
    }

    @PostMapping("/{owner}/{repository-name}")
    @ResponseStatus(HttpStatus.CREATED)
    public RepositoryInfoDto createRepository(@PathVariable("owner") String owner,
                                              @PathVariable("repository-name") String repositoryName) {
        return githubService.createRepository(owner, repositoryName);
    }


    @PutMapping("/{owner}/{repository-name}")
    public RepositoryInfoDto updateRepository(@PathVariable("owner") String owner,
                                              @PathVariable("repository-name") String repositoryName) {
        return githubService.updateRepository(owner, repositoryName);
    }

    @DeleteMapping("/{owner}/{repository-name}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepository(@PathVariable("owner") String owner,
                                 @PathVariable("repository-name") String repositoryName) {
        githubService.deleteLocalRepositoryInfo(owner, repositoryName);
    }
}
