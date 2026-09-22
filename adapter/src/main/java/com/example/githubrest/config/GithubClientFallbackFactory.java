package com.example.githubrest.config;

import com.example.githubrest.*;
import com.example.githubrest.feignclient.GithubClientFeign;
import com.example.githubrest.repository.GithubRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GithubClientFallbackFactory implements FallbackFactory<GithubClientFeign> {

    private final GithubRepository githubRepository;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @Override
    public GithubClientFeign create(Throwable cause) {
        log.error("Exception occured", cause);
        return new GithubClientFeign() {
            @Override
            public GithubRepositoryInfoDto getRepositoryInfo(String owner, String repositoryName) {
                log.info("[Fallback] Fallback in progress");
                return githubRepositoryMapper.toDto(githubRepository.findRepositoryInfoByFullName(owner + "/" + repositoryName)
                        .orElseThrow(() -> new RepositoryNotFoundException("Repository not found in local repository",
                                404)));
            }
        };
    }
}
