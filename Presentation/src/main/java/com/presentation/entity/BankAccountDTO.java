package com.presentation.entity;

import Model.BankAccount;

public class BankAccountDTO {
    private String id;
    private double balance;
    private String ownerLogin;

    public BankAccountDTO() {}

    public BankAccountDTO(String id, double balance, String ownerLogin) {
        this.id = id;
        this.balance = balance;
        this.ownerLogin = ownerLogin;
    }

    public static BankAccountDTO toBankAccountDto(BankAccount account) {
        if (account == null || account.getId() == null) return null;

        return new BankAccountDTO(
                account.getId().toString(),
                account.getBalance(),
                account.getOwnerLogin() != null ? account.getOwnerLogin() : null
        );
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