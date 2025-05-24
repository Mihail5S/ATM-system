package org.example.gateway.controller;

import org.example.gateway.dto.UserDto;
import org.example.gateway.dto.AccountDto;
import org.example.gateway.dto.BalanceDto;
import org.example.gateway.service.ClientService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/client")
public class ClientController {


    private ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }


    @GetMapping("/me")
    public ResponseEntity<UserDto> getProfile() {
        UserDto dto = clientService.getMyProfile();
        return ResponseEntity.ok(dto);
    }


    @PostMapping("/accounts")
    public ResponseEntity<String> createMyAccount() {
        String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        String message = clientService.createAccountForLogin(myLogin);
        return ResponseEntity.status(HttpStatus.CREATED).body(message);
    }


    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getMyAccounts() {
        String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        List<AccountDto> accounts = clientService.getAccountsByLogin(myLogin);
        return ResponseEntity.ok(accounts);
    }



    @GetMapping("/accounts/{accountId}")
    public ResponseEntity<AccountDto> getAccountById(@PathVariable String accountId) {
        String myLogin = SecurityContextHolder.getContext().getAuthentication().getName();
        AccountDto account = clientService.getAccountByIdAndLogin(accountId, myLogin);
        return ResponseEntity.ok(account);
    }


    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<BalanceDto> getBalance(@PathVariable String accountId) {
        BalanceDto balance = clientService.getBalance(accountId);
        return ResponseEntity.ok(balance);
    }


    @PostMapping("/accounts/{accountId}/transfer")
    public ResponseEntity<String> transfer(
            @PathVariable String accountId,
            @RequestBody Map<String,Object> body
    ) {
        String toId    = body.get("toAccountId").toString();
        Double amount  = Double.valueOf(body.get("amount").toString());

        String message = clientService.transfer(accountId, toId, amount);
        return ResponseEntity.ok(message);
    }


    @PostMapping("/accounts/{accountId}/deposit")
    public ResponseEntity<Void> deposit(@PathVariable String accountId,
                                        @RequestParam Double amount) {
        clientService.deposit(accountId, amount);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/accounts/{accountId}/withdraw")
    public ResponseEntity<Void> withdraw(@PathVariable String accountId,
                                         @RequestParam Double amount) {
        clientService.withdraw(accountId, amount);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/friends")
    public ResponseEntity<Void> addFriend(@RequestBody Map<String,String> body) {
        String friendId = body.get("friendId");
        clientService.addFriend(friendId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/friends/{friendId}")
    public ResponseEntity<Void> removeFriend(@PathVariable String friendId) {
        clientService.removeFriend(friendId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out");
    }
}
