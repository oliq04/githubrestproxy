package com.example.githubrest.mapper;


import com.example.githubrest.model.RepositoryInfoCommand;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RepositoryInfoMapper {
    RepositoryInfoDto toDto(RepositoryInfo repositoryInfo);

    @Mapping(target = "id", ignore = true)
    RepositoryInfo toEntity(RepositoryInfoCommand repositoryInfoCommand);

    RepositoryInfoDto toDtoFromCommand(RepositoryInfoCommand repositoryInfoCommand);


    RepositoryInfoCommand toCommandFromEntity(RepositoryInfo repositoryInfo);
}
