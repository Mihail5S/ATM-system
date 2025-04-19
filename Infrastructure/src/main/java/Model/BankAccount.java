package Model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


/**
 * Класс {@code BankAccount} представляет банковский счет с балансом и историей транзакций.
 * <p>
 * Этот класс позволяет добавлять транзакции, депозировать деньги и снимать средства с баланса.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     BankAccount account = new BankAccount("user123");
 *     account.TryDeposit(1000); // Депозит 1000
 *     account.TryWithdraw(500); // Снятие 500
 * </pre>
 */

@Entity
@Table(name = "bank_accounts")
@DynamicUpdate
public class BankAccount {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "balance")
    private double balance;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "transactions", joinColumns = @JoinColumn(name = "account_id"))
    @Column(name = "transaction")
    private List<String> transactions;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User owner;

    public BankAccount() {
        this.id = UUID.randomUUID().toString();
    }

    public BankAccount(User owner) {
        this.id = UUID.randomUUID().toString();
        this.balance = 0;
        this.transactions = new ArrayList<>();
        this.owner = owner;
    }


    public String getId() {
        return id;
    }


    public double getBalance() {
        return balance;
    }


    public String getOwnerLogin() {
        return owner.getLogin();
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
