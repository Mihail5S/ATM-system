package ConsoleInterface;

import ConsoleInterface.Commands.*;
import repository.BankAccountRepository;
import repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Класс, представляющий консольный интерфейс для взаимодействия с банковской системой.
 * <p>
 * Этот класс отвечает за взаимодействие с пользователем, предоставляя меню и обрабатывая выбор действий.
 * Действия, доступные пользователю, включают создание пользователей, создание счетов, депозиты, переводы и другие функции.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     UserRepository userRepository = new UserRepository();
 *     BankAccountRepository bankAccountRepository = new BankAccountRepository();
 *     Console console = new Console(userRepository, bankAccountRepository);
 *     console.start(); // Запуск консольного интерфейса
 * </pre>
 */
public class Console {

    private final Scanner scanner;
    private final Map<String, IConsoleCommand> commands;

    /**
     * Конструктор, инициализирующий консольный интерфейс.
     * <p>
     * Инициализирует репозитории пользователей и банковских счетов и заполняет список команд,
     * которые пользователь может выполнить через консоль.
     * </p>
     *
     * @param userRepository репозиторий для хранения пользователей
     * @param bankAccountRepository репозиторий для хранения банковских счетов
     */
    public Console(UserRepository userRepository, BankAccountRepository bankAccountRepository) {
        this.scanner = new Scanner(System.in);
        this.commands = new HashMap<>();

        // Инициализация команд
        commands.put("1", new CreateUserCommand(userRepository, scanner));
        commands.put("2", new CreateBankAccountCommand(bankAccountRepository, userRepository, scanner));
        commands.put("3", new DepositCommand(bankAccountRepository, userRepository, scanner));
        commands.put("4", new WithdrawCommand(bankAccountRepository, userRepository, scanner));
        commands.put("5", new CheckBalanceCommand(bankAccountRepository, userRepository, scanner));
        commands.put("6", new TransferCommand(bankAccountRepository, userRepository, scanner));
        commands.put("7", new FriendCommand(userRepository, scanner));
        commands.put("8", new ListUsersAndBankAccountsCommand(userRepository));
    }

    /**
     * Метод для запуска консольного интерфейса.
     * <p>
     * Запускает цикл, который показывает меню и ждет пользовательский ввод. В зависимости от выбора пользователя
     * выполняются соответствующие команды. Цикл продолжается до тех пор, пока пользователь не выберет выход.
     * </p>
     */
    public void start() {
        System.out.println("Добро пожаловать в банковскую систему!");
        while (true) {
            System.out.println("""
                Выберите действие:
                1 - Создать пользователя
                2 - Создать счет
                3 - Пополнить счет
                4 - Снять деньги
                5 - Показать баланс
                6 - Перевести деньги
                7 - Изменить список друзей
                8 - Показать все банковские счета
                9 - Выйти
                """);

            String choice = scanner.nextLine();

            if (choice.equals("9")) {
                System.out.println("Выход...");
                break;
            }

            IConsoleCommand command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else {
                System.out.println("Неверный ввод, попробуйте снова.");
            }
        }
    }
}
