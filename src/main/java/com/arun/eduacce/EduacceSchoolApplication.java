package com.arun.eduacce;

import jakarta.persistence.Entity;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.arun.eduacce.repository")
@EntityScan("com.arun.eduacce.model")
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
public class EduacceSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduacceSchoolApplication.class, args);
	}

}
