package com.example.githubrest.repository;

import com.example.githubrest.Repository;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Builder
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class RepositoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDate createdAt;

    public static RepositoryEntity from(Repository repository) {
        return RepositoryEntity.builder()
                .id(repository.getId())
                .fullName(repository.getFullName())
                .description(repository.getDescription())
                .cloneUrl(repository.getCloneUrl())
                .stars(repository.getStars())
                .createdAt(repository.getCreatedAt())
                .build();
    }
}
