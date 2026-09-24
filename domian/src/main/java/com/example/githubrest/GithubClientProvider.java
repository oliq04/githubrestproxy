package com.example.githubrest;

public interface GithubClientProvider {

    Repository getRepositoryInfo(String owner, String repoName);
}
