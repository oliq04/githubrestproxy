package com.example.githubrest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tools.jackson.databind.EnumNamingStrategies;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.annotation.JsonNaming;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode

public class RepositoryInfo {
    @JsonProperty(value = "full_name")
    private String fullName;
    private String description;
    @JsonProperty(value = "clone_url")
    private String cloneUrl;
    @JsonProperty(value = "stargazers_count")
    private Long stars;
    @JsonProperty(value ="created_at")
    private LocalDate createdAt;
}
