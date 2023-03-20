package com.search.blog;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;

@EnableJpaAuditing // JPA Auditing 활성화
@SpringBootApplication
public class SearchBlogApplication {

	public static void main(String[] args) {
		SpringApplication.run(SearchBlogApplication.class, args);
	}
}
