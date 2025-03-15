package repository;

import Model.BankAccount;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Репозиторий для хранения и управления банковскими счетами.
 * <p>
 * Этот класс предоставляет методы для добавления, удаления и поиска банковских счетов по идентификатору.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     BankAccountRepository repository = new BankAccountRepository();
 *     BankAccount account = new BankAccount("john123");
 *     repository.TryAdd(account); // Добавление счета
 *     BankAccount foundAccount = repository.findById("john123"); // Поиск счета по ID
 * </pre>
 */
public class BankAccountRepository {

    private final Map<String, BankAccount> bankAccounts = new HashMap<>();

    /**
     * Добавляет новый банковский счет в репозиторий.
     * <p>
     * Если счет уже существует (по идентификатору), он не будет добавлен.
     * </p>
     *
     * @param bankAccount банковский счет, который нужно добавить
     * @return {@code true}, если счет был добавлен, {@code false}, если счет не был добавлен
     */
    public boolean TryAdd(BankAccount bankAccount) {
        if (bankAccount == null) {
            return false;
        }
        bankAccounts.put(bankAccount.getId(), bankAccount);
        return true;
    }

    /**
     * Удаляет банковский счет из репозитория по его идентификатору.
     * <p>
     * Если счет с данным идентификатором не существует, операция не будет выполнена.
     * </p>
     *
     * @param id идентификатор счета, который необходимо удалить
     * @return {@code true}, если счет был удален, {@code false}, если счет не был удален
     */
    public boolean TryDelete(String id) {
        if (id == null) {
            return false;
        }
        bankAccounts.remove(id);
        return true;
    }

    /**
     * Находит банковский счет по его идентификатору.
     *
     * @param id идентификатор счета
     * @return {@code BankAccount} с заданным идентификатором, или {@code null}, если счет не найден
     */
    public BankAccount findById(String id) {
        return bankAccounts.get(id);
    }

    /**
     * Возвращает список всех банковских счетов в репозитории.
     *
     * @return список всех банковских счетов
     */
    public List<BankAccount> getAllAccounts() {
        return new ArrayList<>(bankAccounts.values());
    }

    /**
     * Возвращает список всех идентификаторов банковских счетов.
     *
     * @return список идентификаторов всех банковских счетов
     */
    public List<String> getAllAccountIds() {
        return new ArrayList<>(bankAccounts.keySet());
    }
}
