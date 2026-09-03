package com.example.githubrest.repository;

import com.example.githubrest.model.RepositoryInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface RepositoryInfoRepository extends JpaRepository<RepositoryInfo, Long> {

    Optional<RepositoryInfo> findRepositoryInfoByFullName(String fullName);
}
