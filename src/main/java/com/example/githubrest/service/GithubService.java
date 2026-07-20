package com.example.githubrest.service;

import com.example.githubrest.RepositoryInfoMapper;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GithubService {

    private final RepositoryInfoMapper repositoryInfoMapper;

    public RepositoryInfoDto getRepositoryInfo(RepositoryInfo repositoryInfo) {
        return repositoryInfoMapper.toDto(repositoryInfo);
    }
}
