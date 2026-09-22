package com.example.githubrest;

import com.example.githubrest.repository.RepositoryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GithubRepositoryMapper {
    Repository toPojo(GithubRepositoryInfoDto githubRepositoryInfoDto);
    GithubRepositoryInfoDto toDto(RepositoryEntity repository);
    Repository toPojo(RepositoryEntity repositoryEntity);
}
