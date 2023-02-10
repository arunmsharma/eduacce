package com.arun.eduacce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@SpringBootApplication
public class EduacceSchoolApplication {

	public static void main(String[] args) {
		SpringApplication.run(EduacceSchoolApplication.class, args);
	}

}
