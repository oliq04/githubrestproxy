package com.example.githubrest.repository;

import com.example.githubrest.GithubRepositoryMapper;
import com.example.githubrest.Repository;
import com.example.githubrest.RepositoryProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RepositoryImpl implements RepositoryProvider {

    private final GithubRepository githubRepository;
    private final GithubRepositoryMapper githubRepositoryMapper;

    @Override
    public Repository save(Repository repository) {
        RepositoryEntity repositoryEntity = RepositoryEntity.from(repository);
        RepositoryEntity savedEntity = githubRepository.save(repositoryEntity);
        return githubRepositoryMapper.toPojo(savedEntity);
    }

    @Override
    public Optional<Repository> findRepositoryInfoByFullName(String fullName) {
        return githubRepository.findRepositoryInfoByFullName(fullName)
                .map(githubRepositoryMapper::toPojo);
    }

    @Override
    public void delete(String fullName) {
        githubRepository.deleteByFullName(fullName);
    }
}
