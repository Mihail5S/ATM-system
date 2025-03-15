package Model;

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
public class BankAccount {
    private String id;
    private double balance;
    private String ownerLogin;
    private List<String> transactions;

    public BankAccount(String ownerLogin){
        this.ownerLogin = ownerLogin;
        this.transactions = new ArrayList<>();
        this.balance = 0;
        this.id = UUID.randomUUID().toString();
    }

    /**
     * Получает уникальный идентификатор счета.
     *
     * @return уникальный идентификатор счета.
     */
    public String getId() {
        return id;
    }

    /**
     * Получает текущий баланс счета.
     *
     * @return баланс счета.
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Получает логин владельца счета.
     *
     * @return логин владельца счета.
     */
    public String getOwnerLogin() {
        return ownerLogin;
    }

    /**
     * Получает список всех транзакций по счету.
     *
     * @return список строк, содержащих транзакции.
     */
    public List<String> getTransactions() {
        return transactions;
    }

    /**
     * Добавляет новую транзакцию в историю.
     *
     * @param transaction описание транзакции.
     */
    public void addTransaction(String transaction){
        this.transactions.add(transaction);
    }

    /**
     * Попытка пополнения счета на указанную сумму.
     * Если сумма меньше или равна нулю, операция не выполняется.
     *
     * @param amount сумма для пополнения счета.
     * @return {@code true} если операция прошла успешно, {@code false} в противном случае.
     */
    public boolean TryDeposit(double amount){
        if (amount <= 0){
            return false;
        }
        this.balance += amount;
        this.addTransaction("Deposited: " + amount);
        return true;
    }

    /**
     * Попытка снятия средств с счета.
     * Если сумма снятия больше текущего баланса или меньше или равна нулю, операция не выполняется.
     *
     * @param amount сумма для снятия.
     * @return {@code true} если операция прошла успешно, {@code false} в противном случае.
     */
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
