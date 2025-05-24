package org.example.gateway.dto;

import java.util.List;

public class BalanceDto {
    private double balance;
    private List<String> transactions;

    public BalanceDto() {}

    public BalanceDto(double balance, List<String> transactions) {
        this.balance = balance;
        this.transactions = transactions;
    }

    public double getBalance() {
        return balance;
    }

    public List<String> getTransactions() {
        return transactions;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void setTransactions(List<String> transactions) {
        this.transactions = transactions;
    }
}
