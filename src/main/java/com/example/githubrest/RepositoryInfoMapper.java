package com.example.githubrest;


import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RepositoryInfoMapper {
    RepositoryInfoDto toDto(RepositoryInfo repositoryInfo);
}
