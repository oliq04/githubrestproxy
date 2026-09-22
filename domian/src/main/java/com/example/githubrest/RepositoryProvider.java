package com.example.githubrest;

import java.util.Optional;

public interface RepositoryProvider {
    Repository save(Repository repository);

    Optional<Repository> findRepositoryInfoByFullName(String fullName);

    void delete(String fullName);
}
