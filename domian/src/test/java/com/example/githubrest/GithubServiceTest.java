package com.example.githubrest;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class GithubServiceTest {

    private RepositoryProvider repositoryInfoRepository;
    private GithubService service;
    private GithubClientProvider githubClient;

    @BeforeEach
    void setup() {
        repositoryInfoRepository = Mockito.mock(RepositoryProvider.class);
        githubClient = Mockito.mock(GithubClientProvider.class);
        service = new GithubService(repositoryInfoRepository, githubClient);
    }

    @Test
    void getLocalRepositoryInfo_CorrectData_RepositoryInfoDtoReturned() {
        Repository repositoryInfo = new Repository(1L,"oliq04/UserManagment", "desc",
                "url", 0L, LocalDate.of(2025, 3, 3));

        when(repositoryInfoRepository.findRepositoryInfoByFullName(any())).thenReturn(Optional.of(repositoryInfo));

        Repository result = service.getLocalRepository("oliq04", "UserManagment");
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
        Repository repositoryInfo = new Repository(1L,"oliq04/UserManagment", "desc",
                "url", 0L, LocalDate.of(2025, 3, 3));
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfo);

        Repository result = service.getRepositoryInfo("oliq04", "UserManagment");

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
        Repository repositoryInfo = new Repository(1L,"oliq04/UserManagment", "desc",
                "url", 0L, LocalDate.of(2025, 3, 3));
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfo);
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfo);
        when((repositoryInfoRepository.save(any()))).thenReturn(repositoryInfo);

        Repository result = service.createRepository("oliq04", "UserManagment");

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
        Repository repositoryInfo = new Repository(1L,"oliq04/UserManagment", "desc",
                "url", 0L, LocalDate.of(2025, 3, 3));

        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfo);
        when(repositoryInfoRepository.findRepositoryInfoByFullName("oliq04/UserManagment")).thenReturn(Optional.of(repositoryInfo));
        when((repositoryInfoRepository.save(any()))).thenReturn(repositoryInfo);

        Repository result = service.updateRepository("oliq04", "UserManagment");

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
        Repository repositoryInfo = new Repository(1L,"oliq04/UserManagment", "desc",
                "url", 0L, LocalDate.of(2025, 3, 3));
        when(repositoryInfoRepository.findRepositoryInfoByFullName("oliq04/UserManagment")).thenReturn(Optional.of(repositoryInfo));
        service.deleteLocalRepositoryInfo("oliq04", "UserManagment");
        verify(repositoryInfoRepository).delete("oliq04/UserManagment");
    }
}
