package com.example.githubrest.client;

import com.example.githubrest.GithubClientProvider;
import com.example.githubrest.GithubRepositoryInfoDto;
import com.example.githubrest.GithubRepositoryMapper;
import com.example.githubrest.Repository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubClientProviderImpl implements GithubClientProvider {

    private final GithubClient githubClient;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @Override
    public Repository getRepositoryInfo(String owner, String repoName) {
        GithubRepositoryInfoDto dto = githubClient.getRepositoryInfo(owner, repoName);
        return githubRepositoryMapper.toPojo(dto);
    }
}
