package org.example.gateway.dto;

public class BankAccountDto {
    private String id;
    private double balance;
    private String ownerLogin;

    public BankAccountDto() {}

    public BankAccountDto(String id, double balance, String ownerLogin) {
        this.id = id;
        this.balance = balance;
        this.ownerLogin = ownerLogin;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String ownerLogin) {
        this.ownerLogin = ownerLogin;
    }
}
