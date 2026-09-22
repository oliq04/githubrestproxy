package com.example.githubrest;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
public class Repository {
    private Long id;
    private String fullName;
    private String description;
    private String cloneUrl;
    private Long stars;
    private LocalDate createdAt;

    public Repository update(Repository newInfo) {
        this.fullName = newInfo.getFullName();
        this.description = newInfo.getDescription();
        this.cloneUrl = newInfo.getCloneUrl();
        this.stars = newInfo.getStars();
        this.createdAt = newInfo.getCreatedAt();
        return this;
    }
}
