package ConsoleInterface.Commands;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Команда для проверки баланса счета пользователя.
 * <p>
 * Эта команда позволяет пользователю ввести логин и ID банковского счета для проверки баланса.
 * Также выводится история транзакций для выбранного счета.
 * </p>
 * <p>
 * При выполнении команды будет запрашиваться логин пользователя и ID счета. Если счет принадлежит
 * пользователю, выводится текущий баланс и история транзакций.
 * </p>
 */
public class CheckBalanceCommand implements IConsoleCommand {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final Scanner scanner;

    /**
     * Конструктор для создания экземпляра команды.
     *
     * @param bankAccountRepository репозиторий для работы с банковскими счетами
     * @param userRepository репозиторий для работы с пользователями
     * @param scanner сканер для ввода данных с консоли
     */
    public CheckBalanceCommand(BankAccountRepository bankAccountRepository, UserRepository userRepository, Scanner scanner) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /**
     * Выполняет команду для проверки баланса счета пользователя.
     * <p>
     * Запрашивает логин пользователя и ID счета, проверяет существование счета и принадлежность
     * счета пользователю, после чего выводит баланс и историю транзакций.
     * </p>
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

        // Вывод баланса
        System.out.println("Текущий баланс: " + account.getBalance());

        // Вывод списка транзакций
        List<String> transactions = account.getTransactions();
        if (transactions.isEmpty()) {
            System.out.println("Транзакции отсутствуют.");
        } else {
            System.out.println("История транзакций:");
            transactions.forEach(System.out::println);
        }
    }
}
