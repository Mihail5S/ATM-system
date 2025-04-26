package Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;



@Entity
@Table(name = "bank_accounts")
@DynamicUpdate
public class BankAccount {
    @Id
    @Column(name = "id", updatable = false, nullable = false)
    private String id;

    @Column(name = "balance")
    private double balance;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "transactions", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "transaction")
    private List<String> transactions;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User owner;


    public BankAccount() {
    }

    public BankAccount(User owner) {
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.owner = owner;
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


    public String getOwnerLogin() {
        return owner.getLogin();
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }


    public List<String> getTransactions() {
        return transactions;
    }


    public void addTransaction(String transaction){
        this.transactions.add(transaction);
    }

    @Transactional
    public boolean TryDeposit(double amount){
        if (amount <= 0){
            return false;
        }
        this.balance += amount;
        this.addTransaction("Deposited: " + amount);
        return true;
    }

    @Transactional
    public boolean TryWithdraw(double amount){
        if (amount <= 0){
            return false;
        }

        if (amount > this.balance){
            return false;
        }

        this.balance -= amount;
        this.addTransaction("Withdrawn: " + amount);
        return true;
    }
}
