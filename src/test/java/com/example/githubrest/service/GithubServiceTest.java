package com.example.githubrest.service;


import com.example.githubrest.client.GithubClient;
import com.example.githubrest.mapper.RepositoryInfoMapper;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoCommand;
import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.repository.RepositoryInfoRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubServiceTest {

    private RepositoryInfoMapper repositoryInfoMapper;
    private RepositoryInfoRepository repositoryInfoRepository;
    private GithubService service;
    private GithubClient githubClient;

    @BeforeEach
    void setup() {
        repositoryInfoMapper = Mappers.getMapper(RepositoryInfoMapper.class);
        repositoryInfoRepository = Mockito.mock(RepositoryInfoRepository.class);
        githubClient = Mockito.mock(GithubClient.class);
        service = new GithubService(repositoryInfoMapper, repositoryInfoRepository, githubClient);
    }

    @Test
    void getLocalRepositoryInfo_CorrectData_RepositoryInfoDtoReturned() {

        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .fullName("oliq04/UserManagment")
                .cloneUrl("url")
                .description("desc")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();

        RepositoryInfoDto repositoryInfoDto = RepositoryInfoDto.builder()
                .fullName("oliq04/UserManagment")
                .cloneUrl("url")
                .stars(0L)
                .description("desc")
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();
        when(repositoryInfoRepository.findRepositoryInfoByFullName(any())).thenReturn(Optional.of(repositoryInfo));

        RepositoryInfoDto result = service.getLocalRepository("oliq04", "UserManagment");
        Assertions.assertAll(
                () -> assertEquals("oliq04/UserManagment", result.getFullName()),
                () -> assertEquals("url", result.getCloneUrl()),
                () -> assertEquals(0L, result.getStars()),
                () -> assertEquals(LocalDate.of(2025, 3, 3), result.getCreatedAt()),
                () -> assertEquals("desc", result.getDescription())
        );
    }

    @Test
    void getRepositoryInfo_CorrectData_RepositoryInfoDtoReturned() {
        RepositoryInfoCommand repositoryInfoCommand = RepositoryInfoCommand.builder()
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfoCommand);

        RepositoryInfoDto result = service.getRepositoryInfo("oliq04", "UserManagment");

        Assertions.assertAll(
                () -> assertEquals("oliq04/UserManagment", result.getFullName()),
                () -> assertEquals("desc", result.getDescription()),
                () -> assertEquals("url", result.getCloneUrl()),
                () -> assertEquals(0L, result.getStars()),
                () -> assertEquals(LocalDate.of(2025, 3, 3), result.getCreatedAt())
        );

    }

    @Test
    void createRepository_CorrectData_CreatedRepositoryReturned() {
        RepositoryInfoCommand repositoryInfoCommand = RepositoryInfoCommand.builder()
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();

        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .id(1L)
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfoCommand);
        when((repositoryInfoRepository.save(any()))).thenReturn(repositoryInfo);

        RepositoryInfoDto result = service.createRepository("oliq04", "UserManagment");

        Assertions.assertAll(
                () -> assertEquals("oliq04/UserManagment", result.getFullName()),
                () -> assertEquals("desc", result.getDescription()),
                () -> assertEquals("url", result.getCloneUrl()),
                () -> assertEquals(0L, result.getStars()),
                () -> assertEquals(LocalDate.of(2025, 3, 3), result.getCreatedAt())
        );
    }

    @Test
    void updateRepository_CorrectData_UpdatedRepositoryReturned() {
        RepositoryInfoCommand repositoryInfoCommand = RepositoryInfoCommand.builder()
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();

        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .id(1L)
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();

        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfoCommand);
        when(repositoryInfoRepository.findRepositoryInfoByFullName("oliq04/UserManagment")).thenReturn(Optional.of(repositoryInfo));
        when((repositoryInfoRepository.save(any()))).thenReturn(repositoryInfo);

        RepositoryInfoDto result = service.updateRepository("oliq04", "UserManagment");

        Assertions.assertAll(
                () -> assertEquals("oliq04/UserManagment", result.getFullName()),
                () -> assertEquals("desc", result.getDescription()),
                () -> assertEquals("url", result.getCloneUrl()),
                () -> assertEquals(0L, result.getStars()),
                () -> assertEquals(LocalDate.of(2025, 3, 3), result.getCreatedAt())
        );
    }

    @Test
    void deleteLocalRepositoryInfo_CorrectData_RepositoryDeletedNoContent() {
        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .id(1L)
                .fullName("oliq04/UserManagment")
                .description("desc")
                .cloneUrl("url")
                .stars(0L)
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();
        when(repositoryInfoRepository.findRepositoryInfoByFullName("oliq04/UserManagment")).thenReturn(Optional.of(repositoryInfo));
        service.deleteLocalRepositoryInfo("oliq04", "UserManagment");
        verify(repositoryInfoRepository).delete(repositoryInfo);
    }


}
