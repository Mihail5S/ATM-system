package org.example.gateway.client;

import org.example.gateway.dto.AccountDto;
import org.example.gateway.dto.BalanceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Component
public class AccountApiClient {


    private WebClient accountWebClient;

    public AccountApiClient(WebClient accountWebClient) {
        this.accountWebClient = accountWebClient;
    }

    public String createAccountForUser(String login) {
        return accountWebClient.post()
                .uri("/accounts")
                .bodyValue(Map.of("login", login))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<AccountDto> getAccountsByUsername(String username) {
        return accountWebClient.get()
                .uri(uri -> uri
                        .path("/accounts")
                        .queryParam("username", username)
                        .build()
                )
                .retrieve()
                .bodyToFlux(AccountDto.class)
                .collectList()
                .block();
    }


    public BalanceDto getBalance(String accountId) {
        return accountWebClient.get()
                .uri("/accounts/{id}/balance", accountId)
                .retrieve()
                .bodyToMono(BalanceDto.class)
                .block();
    }

    public void deposit(String accountId, Double amount) {
        accountWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts/{accountId}/deposit")
                        .queryParam("amount", amount)
                        .build(accountId))
                .retrieve().toBodilessEntity().block();
    }

    public List<AccountDto> getMyAccounts() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return accountWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts")
                        .queryParam("username", username)
                        .build())
                .retrieve()
                .bodyToFlux(AccountDto.class)
                .collectList()
                .block();
    }

    public AccountDto getAccountById(String accountId) {
        return accountWebClient
                .get()
                .uri("/accounts/{id}", accountId)
                .retrieve()
                .bodyToMono(AccountDto.class)
                .block();
    }



    public void withdraw(String accountId, Double amount) {
        accountWebClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/accounts/{accountId}/withdraw")
                        .queryParam("amount", amount)
                        .build(accountId))
                .retrieve().toBodilessEntity().block();
    }

    public String transfer(String fromAccountId, String toAccountId, Double amount) {
        Map<String,Object> body = Map.of(
                "fromAccountId", fromAccountId,
                "toAccountId",   toAccountId,
                "amount",        amount
        );

        return accountWebClient.post()
                .uri("/accounts/transfer")
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
}