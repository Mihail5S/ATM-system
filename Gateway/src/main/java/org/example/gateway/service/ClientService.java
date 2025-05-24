package org.example.gateway.service;

import org.example.gateway.client.AccountApiClient;
import org.example.gateway.client.UserApiClient;
import org.example.gateway.dto.AccountDto;
import org.example.gateway.dto.BalanceDto;
import org.example.gateway.dto.UserDto;
import org.example.gateway.dto.UserWithAccountsDto;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {


    private UserApiClient userApiClient;


    private AccountApiClient accountApiClient;

    public ClientService(UserApiClient userApiClient, AccountApiClient accountApiClient) {
        this.userApiClient = userApiClient;
        this.accountApiClient = accountApiClient;
    }

    public UserDto getMyProfile() {
        return userApiClient.getCurrentUser();
    }


    public List<AccountDto> getAccountsByLogin(String login) {
        List<UserWithAccountsDto> all = userApiClient.getAllUsersWithAccounts();

        return all.stream()
                .filter(uwa -> uwa.getLogin().equals(login))
                .findFirst()
                .map(UserWithAccountsDto::getAccounts)
                .orElse(List.of())
                .stream()
                .map(ba -> new AccountDto(
                        ba.getId(),
                        login,
                        ba.getBalance()
                ))
                .collect(Collectors.toList());
    }


    public String createAccountForLogin(String login) {
        return accountApiClient.createAccountForUser(login);
    }

    public AccountDto getAccountByIdAndLogin(String accountId, String login) {
        return userApiClient.getAllUsersWithAccounts().stream()
                .filter(uwa -> uwa.getLogin().equals(login))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("User not found: " + login))
                .getAccounts().stream()
                .filter(ba -> ba.getId().equals(accountId))
                .findFirst()
                .map(ba -> new AccountDto(
                        ba.getId(),
                        login,
                        ba.getBalance()
                ))
                .orElseThrow(() -> new RuntimeException("Account not found: " + accountId));
    }





    public BalanceDto getBalance(String accountId) {
        return accountApiClient.getBalance(accountId);
    }


    public String transfer(String fromAccountId, String toAccountId, Double amount) {
        return accountApiClient.transfer(fromAccountId, toAccountId, amount);
    }


    public void deposit(String accountId, Double amount) {
        accountApiClient.deposit(accountId, amount);
    }


    public void withdraw(String accountId, Double amount) {
        accountApiClient.withdraw(accountId, amount);
    }

    public void addFriend(String friendId) {
        String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userApiClient.addFriend(myLogin, friendId);
    }

    public void removeFriend(String friendId) {
        String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        userApiClient.removeFriend(myLogin, friendId);
    }
}
