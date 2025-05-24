package org.example.gateway;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;



@SpringBootApplication
public class GatewayApplication {

    @Bean
    public CommandLineRunner initAdmin(org.example.gateway.service.AuthService authService) {
        return args -> authService.initDefaultAdmin();
    }

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
