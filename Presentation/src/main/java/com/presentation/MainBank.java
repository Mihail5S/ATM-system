package com.presentation;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@SpringBootApplication
@ComponentScan(basePackages = {"com.presentation", "Services", "repository", "event"})
@EntityScan(basePackages = "Model")
@EnableJpaRepositories(basePackages = "repository")
public class MainBank {


    public static void main(String[] args) {
        SpringApplication.run(MainBank.class, args);

    }
}
