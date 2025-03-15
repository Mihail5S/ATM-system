package ConsoleInterface.Commands;

import Service.BankAccountService;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.Scanner;

/**
 * Команда для создания банковского счета пользователя.
 * <p>
 * Эта команда позволяет пользователю ввести логин и создать банковский счет для найденного пользователя.
 * При успешном создании счета выводится его ID и логин владельца.
 * </p>
 */
public class CreateBankAccountCommand implements IConsoleCommand {
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
    public CreateBankAccountCommand(BankAccountRepository bankAccountRepository,
                                    UserRepository userRepository,
                                    Scanner scanner)
    {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /**
     * Выполняет команду для создания банковского счета для пользователя.
     * <p>
     * Запрашивает логин пользователя, проверяет существование пользователя,
     * создает счет с использованием сервиса {@link BankAccountService} и сохраняет его в репозитории.
     * После успешного создания счета выводит его ID и логин владельца.
     * </p>
     */
    @Override
    public void execute() {
        System.out.print("Введите логин пользователя: ");
        String login = scanner.nextLine();

        var user = userRepository.findByLogin(login);
        if (user != null) {
            boolean bankAccount = new BankAccountService(bankAccountRepository).TryAddBankAccount(user);
            if (bankAccount) {
                    System.out.println("Счет создан!");
            }
             else {
                System.out.println("Не удалось создать счет!");
            }
        } else {
            System.out.println("Ошибка! Пользователь не найден.");
        }
    }
}
