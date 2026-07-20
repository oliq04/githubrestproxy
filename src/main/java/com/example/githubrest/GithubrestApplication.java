package com.example.githubrest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class GithubrestApplication {

	public static void main(String[] args) {
		SpringApplication.run(GithubrestApplication.class, args);
	}

}
