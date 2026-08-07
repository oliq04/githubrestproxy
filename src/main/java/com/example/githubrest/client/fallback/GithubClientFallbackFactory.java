package com.example.githubrest.client.fallback;

import com.example.githubrest.client.GithubClient;
import com.example.githubrest.exception.RepositoryNotFoundException;
import com.example.githubrest.mapper.RepositoryInfoMapper;
import com.example.githubrest.model.RepositoryInfoCommand;
import com.example.githubrest.repository.RepositoryInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class GithubClientFallbackFactory implements FallbackFactory<GithubClient> {

    private final RepositoryInfoRepository repositoryInfoRepository;
    private final RepositoryInfoMapper repositoryInfoMapper;

    @Override
    public GithubClient create(Throwable cause) {
        log.error("Exception occured", cause);
        return new GithubClient() {
            @Override
            public RepositoryInfoCommand getRepositoryInfo(String owner, String repositoryName) {
                log.info("[Fallback] Fallback in progress");
                return repositoryInfoMapper.toCommandFromEntity(repositoryInfoRepository.findRepositoryInfoByFullName(owner + "/" + repositoryName)
                        .orElseThrow(() -> new RepositoryNotFoundException("Repository not found in local repository",
                                HttpStatus.NOT_FOUND)));
            }
        };
    }
}
