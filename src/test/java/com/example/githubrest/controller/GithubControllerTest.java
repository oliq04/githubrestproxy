package com.example.githubrest.controller;

import com.example.githubrest.client.GithubClient;
import com.example.githubrest.model.RepositoryInfo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class GithubControllerTest {
    @MockitoBean
    private GithubClient githubClient;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getRepositoryInfo_CorrectData_RepositoryInfoReturned() throws Exception {
        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .fullName("Shop-Project")
                .description("description")
                .cloneUrl(null)
                .createdAt(LocalDate.of(2024, 2, 10))
                .build();
        when(githubClient.getRepositoryInfo(any(), any())).thenReturn(repositoryInfo);
        mockMvc.perform(MockMvcRequestBuilders.get("/repo/{owner}/{repoName}", "oliq04", "Shop-Project"))
                .andExpect(jsonPath("$.fullName").value("Shop-Project"))
                .andExpect(jsonPath("$.description").value("description"))
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.createdAt").value("2024-02-10"));
    }
}
