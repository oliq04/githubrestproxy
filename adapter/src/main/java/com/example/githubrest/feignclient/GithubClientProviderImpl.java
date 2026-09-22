package com.example.githubrest.feignclient;

import com.example.githubrest.GithubClientProvider;
import com.example.githubrest.GithubRepositoryInfoDto;
import com.example.githubrest.GithubRepositoryMapper;
import com.example.githubrest.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubClientProviderImpl implements GithubClientProvider {

    private final GithubClientFeign githubClientFeign;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @Override
    public Repository getRepositoryInfo(String owner, String repoName) {
        GithubRepositoryInfoDto dto = githubClientFeign.getRepositoryInfo(owner, repoName);
        return githubRepositoryMapper.toPojo(dto);
    }
}
