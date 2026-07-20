package com.example.githubrest.model;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
public class RepositoryInfoDto {
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDate createdAt;
}
