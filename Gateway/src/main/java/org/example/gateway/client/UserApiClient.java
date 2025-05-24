package org.example.gateway.client;

import org.example.gateway.dto.FriendDto;
import org.example.gateway.dto.UserDto;
import org.example.gateway.dto.UserWithAccountsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Optional;

@Component
public class UserApiClient {


    private WebClient userWebClient;

    public UserApiClient(WebClient userWebClient) {
        this.userWebClient = userWebClient;
    }

    public String createUser(Object userDto) {
        return userWebClient.post()
                .uri("/users")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    public List<UserDto> getAllUsers(String gender, String hairColor) {
        return userWebClient.get()
                .uri(u -> u.path("/users/filter")
                        .queryParamIfPresent("gender", Optional.ofNullable(gender))
                        .queryParamIfPresent("hairColor", Optional.ofNullable(hairColor))
                        .build())
                .retrieve()
                .bodyToFlux(UserDto.class)
                .collectList()
                .block();
    }


    public UserDto getUserByUsername(String username) {
        return userWebClient.get()
                .uri(uriBuilder -> uriBuilder.path("/users")
                        .queryParam("id", username)
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public UserDto getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userWebClient
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/users")
                        .queryParam("id", username)
                        .build())
                .retrieve()
                .bodyToMono(UserDto.class)
                .block();
    }

    public List<FriendDto> getUserFriends(Long userId) {
        return userWebClient.get()
                .uri("/users/{userId}/friends", userId)
                .retrieve()
                .bodyToFlux(FriendDto.class)
                .collectList()
                .block();
    }

    public void addFriend(String userId, String friendId) {
        userWebClient.post()
                .uri("/users/{userId}/friends/{friendId}", userId, friendId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public void removeFriend(String userId, String friendId) {
        userWebClient.delete()
                .uri("/users/{userId}/friends/{friendId}", userId, friendId)
                .retrieve()
                .toBodilessEntity()
                .block();
    }

    public List<UserWithAccountsDto> getAllUsersWithAccounts() {
        return userWebClient.get()
                .uri("/users/with-accounts")
                .retrieve()
                .bodyToFlux(UserWithAccountsDto.class)
                .collectList()
                .block();
    }
}

