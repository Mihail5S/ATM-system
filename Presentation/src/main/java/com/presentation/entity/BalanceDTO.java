package com.presentation.entity;

import java.util.List;

public class BalanceDTO {
    private double balance;
    private List<String> transactions;

    public BalanceDTO(double balance, List<String> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }


    public double getBalance() {
        return balance;
    }

    public List<String> getTransactions() {
        return transactions;
    }
}