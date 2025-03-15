package ConsoleInterface.Commands;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.Scanner;

/**
 * Команда для пополнения банковского счета.
 * <p>
 * Эта команда позволяет пользователю пополнить свой банковский счет на заданную сумму.
 * Пользователь должен ввести свой логин, ID счета и сумму пополнения.
 * После успешного пополнения выводится новый баланс счета.
 * </p>
 */
public class DepositCommand implements IConsoleCommand {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    private final Scanner scanner;

    /**
     * Конструктор для создания команды пополнения счета.
     *
     * @param bankAccountRepository репозиторий для работы с банковскими счетами
     * @param userRepository репозиторий для работы с пользователями
     * @param scanner сканер для ввода данных с консоли
     */
    public DepositCommand(BankAccountRepository bankAccountRepository, UserRepository userRepository, Scanner scanner) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /**
     * Выполняет команду пополнения банковского счета.
     * <p>
     * Запрашивает у пользователя логин, ID счета и сумму пополнения.
     * Проверяет, что указанный счет принадлежит пользователю, и пополняет счет на указанную сумму.
     * После успешного пополнения выводится новый баланс счета.
     * </p>
     */
    @Override
    public void execute() {
        System.out.print("Введите логин пользователя: ");
        String login = scanner.nextLine();

        // Поиск пользователя по логину
        User user = userRepository.findByLogin(login);
        if (user == null) {
            System.out.println("Ошибка! Пользователь не найден.");
            return;
        }

        // Поиск счета по ID
        System.out.print("Введите ID счета: ");
        String accountId = scanner.nextLine();
        BankAccount account = bankAccountRepository.findById(accountId);
        if (account == null || !account.getOwnerLogin().equals(login)) {
            System.out.println("Ошибка! Счет не найден или не принадлежит пользователю.");
            return;
        }

        // Ввод суммы для пополнения
        System.out.print("Введите сумму для пополнения: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Для очистки буфера

        // Пополнение счета
        if (account.TryDeposit(amount)) {
            System.out.println("Счет успешно пополнен! Новый баланс: " + account.getBalance());
        } else {
            System.out.println("Ошибка при пополнении.");
        }
    }
}
