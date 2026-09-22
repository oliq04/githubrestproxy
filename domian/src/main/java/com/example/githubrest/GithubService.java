package com.example.githubrest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Slf4j
public class GithubService {

    private final RepositoryProvider repositoryProvider;
    private final GithubClientProvider githubClient;

    public Repository getRepositoryInfo(String owner, String repoName) {
        Repository repository = githubClient.getRepositoryInfo(owner, repoName);
        log.info("Returning repository '{}'", repository);
        return repository;
    }

    public Repository createRepository(String owner, String repositoryName) {
        Repository repository = githubClient.getRepositoryInfo(owner, repositoryName);
        log.info("Saving repository in database.");
        return repositoryProvider.save(repository);
    }

    public Repository getLocalRepository(String owner, String repositoryName) {
        log.info("Getting local repository from database.");
        return repositoryProvider.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", 404));
    }

    public Repository updateRepository(String owner, String repositoryName) {
        Repository repositoryInfoCommand = githubClient.getRepositoryInfo(owner, repositoryName);
        log.info("Finding repository in database.");
        Repository repositoryInfo = repositoryProvider.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", 404));
        log.info("Repository found, updating details.");
        repositoryInfo.update(repositoryInfoCommand);
        log.info("Saving repository '{}' in database.", repositoryInfoCommand.getFullName());
        Repository savedRepository = repositoryProvider.save(repositoryInfo);
        log.info("Repository saved.");
        return savedRepository;
    }

    public void deleteLocalRepositoryInfo(String owner, String repositoryName) {
        Repository repository = repositoryProvider.findRepositoryInfoByFullName(owner + "/" +
                repositoryName).orElseThrow(() -> new RepositoryNotFoundException("Repository not fund", 404));
        repositoryProvider.delete(repositoryName);
        log.info("Repository deleted successfully.");
    }
}
