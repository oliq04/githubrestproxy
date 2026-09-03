package com.example.githubrest.client;

import com.example.githubrest.exception.RepositoryNotFoundException;
import com.example.githubrest.model.RepositoryInfo;
import com.example.githubrest.model.RepositoryInfoCommand;
import com.example.githubrest.model.RepositoryInfoDto;
import com.example.githubrest.repository.RepositoryInfoRepository;
import com.github.tomakehurst.wiremock.WireMockServer;
import feign.FeignException;
import feign.RetryableException;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.wiremock.spring.ConfigureWireMock;
import org.wiremock.spring.EnableWireMock;

import tools.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.Optional;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@EnableWireMock(@ConfigureWireMock(
        port = 8092
))
public class GithubFeignTest {

    @Autowired
    private GithubClient githubClient;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RepositoryInfoRepository repositoryInfoRepository;

    @Test
    void returns_503() {
        stubFor(get(urlEqualTo("/repos/oliq04/UserManagment")).willReturn(serviceUnavailable()));
        assertThrows(RepositoryNotFoundException.class, () -> githubClient.getRepositoryInfo(
                "oliq04", "UserManagment"));
        verify(exactly(5), getRequestedFor(urlEqualTo("/repos/oliq04/UserManagment")));
    }

    @Test
    void getRepositoryInfo_CorrectData_RepositoryInfoReturned() throws Exception {
        stubFor(get(urlEqualTo("/repos/oliq04/UserManagment")).willReturn(aResponse()
                .withBodyFile("github_response.json")
                .withHeader("Content-Type", "application/json")));


        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repoName}",
                        "oliq04", "UserManagment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"))
                .andExpect(jsonPath("$.stars").value(80))
                .andExpect(jsonPath("$.createdAt").value("2011-01-26"))
                .andExpect(jsonPath("$.cloneUrl").value("https://github.com/octocat/Hello-World.git"));

        verify(exactly(1), getRequestedFor(urlEqualTo("/repos/oliq04/UserManagment")));
    }

    @Test
    void createRepository_CorrectData_CreatedRepositoryReturned() throws Exception {
        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .fullName("octocat/Hello-World")
                .description("This your first repo!")
                .cloneUrl("https://github.com/octocat/Hello-World.git")
                .stars(80L)
                .createdAt(LocalDate.of(2011,1,26))
                .build();

        stubFor(get(urlEqualTo("/repos/oliq04/UserManagment")).willReturn(aResponse()
                .withBodyFile("github_response.json")
                .withHeader("Content-Type", "application/json")));

        when(repositoryInfoRepository.save(any())).thenReturn(repositoryInfo);

        mockMvc.perform(MockMvcRequestBuilders.post("/repositories/{owner}/{repository-name}",
                        "oliq04", "UserManagment"))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"))
                .andExpect(jsonPath("$.stars").value(80))
                .andExpect(jsonPath("$.createdAt").value("2011-01-26"))
                .andExpect(jsonPath("$.cloneUrl").value("https://github.com/octocat/Hello-World.git"));

        verify(exactly(1), getRequestedFor(urlEqualTo("/repos/oliq04/UserManagment")));
    }

    @Test
    void updateRepository_CorrectData_UpdatedRepositoryReturned() throws Exception {
        stubFor(get(urlEqualTo("/repos/oliq04/UserManagment")).willReturn(aResponse()
                .withBodyFile("github_response.json")
                .withHeader("Content-Type", "application/json")));

        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .id(1L)
                .fullName("octocat/Hello-World")
                .description("This your first repo!")
                .cloneUrl("https://github.com/octocat/Hello-World.git")
                .stars(80L)
                .createdAt(LocalDate.of(2011, 1, 26))
                .build();

        when(repositoryInfoRepository.findRepositoryInfoByFullName(any())).thenReturn(Optional.of(repositoryInfo));
        when(repositoryInfoRepository.save(any())).thenReturn(repositoryInfo);

        mockMvc.perform(MockMvcRequestBuilders.put("/repositories/{owner}/{repository-name}",
                        "oliq04", "UserManagment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"))
                .andExpect(jsonPath("$.stars").value(80))
                .andExpect(jsonPath("$.createdAt").value("2011-01-26"))
                .andExpect(jsonPath("$.cloneUrl").value("https://github.com/octocat/Hello-World.git"));

        verify(exactly(1), getRequestedFor(urlEqualTo("/repos/oliq04/UserManagment")));
    }

    @Test
    void fallback_Exception_LocalRepositoryReturned() throws Exception {
        stubFor(get(urlEqualTo("/repos/oliq04/UserManagment")).willReturn(serverError()));

        RepositoryInfo repositoryInfo = RepositoryInfo.builder()
                .id(1L)
                .fullName("octocat/Hello-World")
                .description("This your first repo!")
                .cloneUrl("https://github.com/octocat/Hello-World.git")
                .stars(80L)
                .createdAt(LocalDate.of(2011, 1, 26))
                .build();

        when(repositoryInfoRepository.findRepositoryInfoByFullName("oliq04/UserManagment"))
                .thenReturn(Optional.of(repositoryInfo));

        mockMvc.perform(MockMvcRequestBuilders.get("/repositories/{owner}/{repository-name}",
                        "oliq04", "UserManagment"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.fullName").value("octocat/Hello-World"))
                .andExpect(jsonPath("$.description").value("This your first repo!"))
                .andExpect(jsonPath("$.stars").value(80))
                .andExpect(jsonPath("$.createdAt").value("2011-01-26"))
                .andExpect(jsonPath("$.cloneUrl").value("https://github.com/octocat/Hello-World.git"));
    }
}
