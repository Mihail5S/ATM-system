package Service;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.List;

/**
 * Класс {@code BankAccountService} предоставляет методы для управления банковскими счетами пользователей.
 * <p>
 * Этот класс позволяет добавлять и удалять банковские счета пользователей.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     BankAccountService service = new BankAccountService();
 *     User user = new User("John", "john123", 30, Gender.MALE, HairColor.BLACK);
 *     BankAccount account = service.AddBankAccount(user); // Добавить новый счет
 *     service.TryDeleteAccount(user, account); // Удалить счет
 * </pre>
 */
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }
    /**
     * Добавляет новый банковский счет пользователю.
     *
     * @param user пользователь, для которого создается банковский счет
     * @return объект {@link BankAccount} нового банковского счета, или {@code null}, если пользователь равен {@code null}.
     */
    public boolean TryAddBankAccount(User user) {
        if (user == null) {
            return false;
        }
        BankAccount account = new BankAccount(user.getLogin());
        user.getBankAccounts().add(account);
        bankAccountRepository.TryAdd(account);
        return bankAccountRepository.TryAdd(account);
    }

    /**
     * Попытка удалить банковский счет пользователя.
     * Если пользователь или счет равен {@code null}, либо счет не найден в списке счетов пользователя, операция не выполняется.
     *
     * @param user пользователь, чей банковский счет нужно удалить
     * @param account банковский счет, который необходимо удалить
     * @return {@code true}, если операция удаления прошла успешно, {@code false} в противном случае
     */
    public boolean TryDeleteAccount(User user, BankAccount account) {
        if (user == null || account == null) {
            return false;
        }

        List<BankAccount> accounts = user.getBankAccounts();
        if (!accounts.contains(account)) {
            return false;
        }

        accounts.remove(account);
        bankAccountRepository.TryDelete(account.getId());
        return true;
    }

    /**
     * Находит банковский счет по его идентификатору.
     * @param id идентификатор счета
     * @return объект BankAccount или null, если счет не найден
     */
    public BankAccount FindById(String id) {
        return bankAccountRepository.findById(id);
    }

    /**
     * Получает список всех пользователей.
     * @return список пользователей
     */
    public List<BankAccount> GetAllBankAccounts() {
        return bankAccountRepository.getAllAccounts();
    }
}
