package com.example.githubrest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface GithubRepository extends JpaRepository<RepositoryEntity, Long> {

    Optional<RepositoryEntity> findRepositoryInfoByFullName(String fullName);

    void deleteByFullName(String fullName);
}
