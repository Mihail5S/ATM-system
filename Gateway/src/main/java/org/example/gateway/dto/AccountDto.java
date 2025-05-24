package org.example.gateway.dto;

public class AccountDto {
    private String id;
    private String userId;
    private Double balance;

    public AccountDto() {}

    public AccountDto(String id, String userId, Double balance) {
        this.id = id;
        this.userId = userId;
        this.balance = balance;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
}
