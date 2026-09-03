package com.example.githubrest.service;

import com.example.githubrest.exception.RepositoryNotFoundException;
import com.example.githubrest.client.GithubClient;
import com.example.githubrest.mapper.RepositoryInfoMapper;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoCommand;
import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.repository.RepositoryInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GithubService {

    private final RepositoryInfoMapper repositoryInfoMapper;
    private final RepositoryInfoRepository repositoryInfoRepository;
    private final GithubClient githubClient;

    public RepositoryInfoDto getRepositoryInfo(String owner, String repoName) {
        RepositoryInfoCommand repositoryInfoCommand = githubClient.getRepositoryInfo(owner, repoName);
        log.info("Mapping repository to Dto.");
        RepositoryInfoDto repositoryInfoDto = repositoryInfoMapper.toDtoFromCommand(repositoryInfoCommand);
        log.info("Returning RepositoryDto {}", repositoryInfoDto);
        return repositoryInfoDto;
    }

    public RepositoryInfoDto createRepository(String owner, String repositoryName) {
        RepositoryInfoCommand repositoryInfoCommand = githubClient.getRepositoryInfo(owner, repositoryName);
        log.info("Saving repository in database.");
        return repositoryInfoMapper.toDto(repositoryInfoRepository.save(repositoryInfoMapper.toEntity(repositoryInfoCommand)));
    }

    public RepositoryInfoDto getLocalRepository(String owner, String repositoryName) {
        log.info("Getting local repository from database.");
        return repositoryInfoMapper.toDto(repositoryInfoRepository.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", HttpStatus.NOT_FOUND)));
    }

    public RepositoryInfoDto updateRepository(String owner, String repositoryName) {
        RepositoryInfoCommand repositoryInfoCommand = githubClient.getRepositoryInfo(owner, repositoryName);
        log.info("Finding repository in database.");
        RepositoryInfo repositoryInfo = repositoryInfoRepository.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", HttpStatus.NOT_FOUND));
        log.info("Repository found, updating details.");
        repositoryInfo.update(repositoryInfoCommand);
        log.info("Saving repository in database.");
        RepositoryInfo savedRepository = repositoryInfoRepository.save(repositoryInfo);
        log.info("Repository saved.");
        return repositoryInfoMapper.toDto(savedRepository);
    }

    public void deleteLocalRepositoryInfo(String owner, String repositoryName) {
        RepositoryInfo repositoryInfo = repositoryInfoRepository.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", HttpStatus.NOT_FOUND));
        repositoryInfoRepository.delete(repositoryInfo);
        log.info("Repository deleted successfully.");
    }
}
