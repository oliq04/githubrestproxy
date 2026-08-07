package com.example.githubrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@ToString
public class RepositoryInfoCommand {
    @JsonProperty(value = "full_name")
    private String fullName;
    private String description;
    @JsonProperty(value = "clone_url")
    private String cloneUrl;
    @JsonProperty(value = "stargazers_count")
    private Long stars;
    @JsonProperty(value = "created_at")
    private LocalDate createdAt;
}
