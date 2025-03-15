package ConsoleInterface.Commands;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.Scanner;

/**
 * Команда для снятия средств с банковского счета пользователя.
 * <p>
 * Эта команда позволяет пользователю снять средства с его банковского счета, проверяя наличие
 * пользователя, счета, а также достаточность средств на счете для выполнения операции.
 * </p>
 */
public class WithdrawCommand implements IConsoleCommand {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final Scanner scanner;

    /**
     * Конструктор класса {@link WithdrawCommand}.
     *
     * @param bankAccountRepository Репозиторий для работы с банковскими счетами.
     * @param userRepository Репозиторий для работы с пользователями.
     * @param scanner Объект для считывания ввода с консоли.
     */
    public WithdrawCommand(BankAccountRepository bankAccountRepository, UserRepository userRepository, Scanner scanner) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /**
     * Выполняет команду снятия средств с банковского счета пользователя.
     * Команда выполняет следующие шаги:
     * <ul>
     *   <li>Запрашивает логин пользователя и проверяет его существование</li>
     *   <li>Запрашивает ID счета и проверяет, принадлежит ли он пользователю</li>
     *   <li>Запрашивает сумму для снятия</li>
     *   <li>Проверяет, есть ли достаточно средств на счете для снятия</li>
     *   <li>Если операция успешна, выводит новый баланс счета</li>
     *   <li>В случае ошибок выводится сообщение о причине неудачи</li>
     * </ul>
     */
    @Override
    public void execute() {
        System.out.print("Введите логин пользователя: ");
        String login = scanner.nextLine();

        User user = userRepository.findByLogin(login);
        if (user == null) {
            System.out.println("Ошибка! Пользователь не найден.");
            return;
        }

        System.out.print("Введите ID счета: ");
        String accountId = scanner.nextLine();
        BankAccount account = bankAccountRepository.findById(accountId);
        if (account == null || !account.getOwnerLogin().equals(login)) {
            System.out.println("Ошибка! Счет не найден или не принадлежит пользователю.");
            return;
        }

        System.out.print("Введите сумму для снятия: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();

        if (account.TryWithdraw(amount)) {
            System.out.println("Деньги успешно сняты! Новый баланс: " + account.getBalance());
        } else {
            System.out.println("Ошибка при снятии. Проверьте баланс.");
        }
    }
}
