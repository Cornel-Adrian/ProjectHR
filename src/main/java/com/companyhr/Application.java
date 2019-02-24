package com.companyhr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
@ComponentScan("com")
@EntityScan("com.companyhr")
@EnableJpaRepositories("com.companyhr.repository")

public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}