package org.example.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;



@Configuration
public class WebClientConfig {

    @Value("${external.user-service.base-url}")
    private String userServiceUrl;

    @Value("${external.user-service.base-url}")
    private String accountServiceUrl;

    @Value("${external.user-service.base-url}")
    private String operationServiceUrl;

    @Bean
    public WebClient userWebClient(WebClient.Builder builder) {
        return builder.baseUrl(userServiceUrl).build();
    }

    @Bean
    public WebClient accountWebClient(WebClient.Builder builder) {
        return builder.baseUrl(accountServiceUrl).build();
    }

    @Bean
    public WebClient operationWebClient(WebClient.Builder builder) {
        return builder.baseUrl(operationServiceUrl).build();
    }
}
