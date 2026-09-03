package com.example.githubrest.model;

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
public class RepositoryInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDate createdAt;

    public RepositoryInfo update(RepositoryInfoCommand newInfo) {
        this.fullName = newInfo.getFullName();
        this.description = newInfo.getDescription();
        this.cloneUrl = newInfo.getCloneUrl();
        this.stars = newInfo.getStars();
        this.createdAt = newInfo.getCreatedAt();
        return this;
    }
}
