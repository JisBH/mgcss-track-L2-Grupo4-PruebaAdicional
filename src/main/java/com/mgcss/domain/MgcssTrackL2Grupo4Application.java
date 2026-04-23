package com.mgcss.domain;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.mgcss")
@EnableJpaRepositories(basePackages = "com.mgcss")
public class MgcssTrackL2Grupo4Application {

	public static void main(String[] args) {
		SpringApplication.run(MgcssTrackL2Grupo4Application.class, args);
	}

}
