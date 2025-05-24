package org.example.gateway.client;

import org.example.gateway.dto.OperationDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Component
public class OperationApiClient {


    private WebClient operationWebClient;

    public OperationApiClient(WebClient operationWebClient) {
        this.operationWebClient = operationWebClient;
    }

    public List<OperationDto> getOperationsByAccountId(Long accountId) {
        return operationWebClient.get()
                .uri("/accounts/{id}/operations", accountId)
                .retrieve()
                .bodyToFlux(OperationDto.class)
                .collectList()
                .block();
    }
}
