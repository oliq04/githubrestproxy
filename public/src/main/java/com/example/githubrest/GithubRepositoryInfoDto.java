package com.example.githubrest;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class GithubRepositoryInfoDto {
    @JsonAlias("full_name")
    private String fullName;
    private String description;
    @JsonAlias("clone_url")
    private String cloneUrl;
    @JsonAlias("stargazers_count")
    private Long stars;
    @JsonAlias("created_at")
    private LocalDate createdAt;
}
