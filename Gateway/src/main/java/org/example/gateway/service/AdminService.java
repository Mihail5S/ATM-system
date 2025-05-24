package org.example.gateway.service;

import org.example.gateway.client.AccountApiClient;
import org.example.gateway.client.UserApiClient;
import org.example.gateway.dto.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {


    private UserApiClient userApiClient;

    private AuthService authService;

    private AccountApiClient accountApiClient;

    public AdminService(UserApiClient userApiClient, AuthService authService, AccountApiClient accountApiClient) {
        this.userApiClient = userApiClient;
        this.authService = authService;
        this.accountApiClient = accountApiClient;
    }


    public String createClient(GatewayUserCreateRequest req, String role) {

        authService.createUser(req.getLogin(), req.getPassword(), role);


        UserDto dto = new UserDto();
        dto.setLogin(req.getLogin());
        dto.setName(req.getName());
        dto.setAge(req.getAge());
        dto.setGender(req.getGender());
        dto.setHaircolor(req.getHaircolor());


        return userApiClient.createUser(dto);
    }


    public String createAdmin(UserDto dto) {
        return userApiClient.createUser(dto);
    }



    public List<UserDto> getAllUsers(String gender, String hairColor) {
        return userApiClient.getAllUsers(gender, hairColor);
    }


    public UserDto getUserByLogin(String login) {
        return userApiClient.getUserByUsername(login);
    }

    public String createAccountForUser(String login) {
        return accountApiClient.createAccountForUser(login);
    }



    public List<AccountDto> getAllAccounts() {
        return userApiClient.getAllUsersWithAccounts().stream()
                .flatMap(uwa -> uwa.getAccounts().stream())
                .map(bankAccountDto -> new AccountDto(
                        bankAccountDto.getId(),
                        bankAccountDto.getOwnerLogin(),
                        bankAccountDto.getBalance()
                ))
                .collect(Collectors.toList());
    }


    public List<AccountDto> getAccountsByUserId(String userId) {
        return userApiClient.getAllUsersWithAccounts().stream()
                .filter(uwa -> uwa.getLogin().equals(userApiClient.getUserByUsername(userId).getLogin()))
                .findFirst()
                .map(UserWithAccountsDto::getAccounts)
                .orElse(List.of())
                .stream()
                .map(bankAccountDto -> new AccountDto(
                        bankAccountDto.getId(),
                        bankAccountDto.getOwnerLogin(),
                        bankAccountDto.getBalance()
                ))
                .collect(Collectors.toList());
    }


    public BalanceDto getAccountBalance(String accountId) {
        return accountApiClient.getBalance(accountId);
    }


    public String transfer(String from, String to, Double amount) {
        return accountApiClient.transfer(from, to, amount);
    }
}
