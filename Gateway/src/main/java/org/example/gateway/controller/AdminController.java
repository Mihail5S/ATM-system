package org.example.gateway.controller;

import org.example.gateway.dto.*;
import org.example.gateway.service.AdminService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class AdminController {


    private AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/users")
    public ResponseEntity<String> createUser(
            @RequestBody GatewayUserCreateRequest req
    ) {
        try {
            String msg = adminService.createClient(req, "ROLE_CLIENT");
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (WebClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        }
    }

    @PostMapping("/admins")
    public ResponseEntity<String> createAdmin(
            @RequestBody GatewayUserCreateRequest req
    ) {
        try {
            String msg = adminService.createClient(req, "ROLE_ADMIN");
            return ResponseEntity.status(HttpStatus.CREATED).body(msg);
        } catch (WebClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        }
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers(
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String hairColor) {
        List<UserDto> users = adminService.getAllUsers(gender, hairColor);
        return ResponseEntity.ok(users);
    }

    @PostMapping("/accounts")
    public ResponseEntity<String> createAccountForUser(@RequestBody Map<String,String> body) {
        try {
            String login = body.get("login");
            String message = adminService.createAccountForUser(login);
            return ResponseEntity.status(HttpStatus.CREATED).body(message);
        } catch (WebClientResponseException ex) {
            return ResponseEntity.status(ex.getStatusCode())
                    .body(ex.getResponseBodyAsString());
        }
    }


    @GetMapping("/users/{login}")
    public ResponseEntity<UserDto> getUserByLogin(@PathVariable String login) {
        UserDto dto = adminService.getUserByLogin(login);
        return ResponseEntity.ok(dto);
    }


    @GetMapping("/accounts")
    public ResponseEntity<List<AccountDto>> getAllAccounts() {
        List<AccountDto> accounts = adminService.getAllAccounts();
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/accounts/user/{userId}")
    public ResponseEntity<List<AccountDto>> getAccountsByUserId(@PathVariable String userId) {
        List<AccountDto> accounts = adminService.getAccountsByUserId(userId);
        return ResponseEntity.ok(accounts);
    }

    @GetMapping("/accounts/{accountId}/balance")
    public ResponseEntity<BalanceDto> getAccountBalance(@PathVariable String accountId) {
        BalanceDto balance = adminService.getAccountBalance(accountId);
        return ResponseEntity.ok(balance);
    }

    @GetMapping("/accounts/{accountId}/operations")
    public ResponseEntity<List<String>> getAccountOperations(@PathVariable String accountId) {
        BalanceDto balance = adminService.getAccountBalance(accountId);
        return ResponseEntity.ok(balance.getTransactions());
    }
    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        return ResponseEntity.ok("Logged out");
    }
}