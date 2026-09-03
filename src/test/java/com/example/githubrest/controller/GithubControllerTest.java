package com.example.githubrest.controller;

import com.example.githubrest.client.GithubClient;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.service.GithubService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @MockitoBean
    private GithubService githubService;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRepositoryInfo_CorrectData_RepositoryInfoReturned() throws Exception {
        RepositoryInfoDto repositoryInfoDto = RepositoryInfoDto.builder()
                .fullName("oliq04/UserManagment")
                .cloneUrl("url")
                .stars(0L)
                .description("desc")
                .createdAt(LocalDate.of(2025, 3, 3))
                .build();
        when(githubService.getRepositoryInfo(any(),any())).thenReturn(repositoryInfoDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repoName}", "oliq04", "UserManagment")
                        .content(objectMapper.writeValueAsString(repositoryInfoDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.fullName").value("oliq04/UserManagment"))
                .andExpect(jsonPath("$.description").value("desc"))
                .andExpect(jsonPath("$.cloneUrl").value("url"))
                .andExpect(jsonPath("$.stars").value(0L))
                .andExpect(jsonPath("$.createdAt").value("2025-03-03"));
    }
}
