package ConsoleInterface;

import repository.BankAccountRepository;
import repository.UserRepository;

/**
 * Главный класс для запуска банковской системы.
 * <p>
 * Этот класс инициализирует репозитории пользователей и банковских счетов и запускает консольный интерфейс,
 * позволяющий пользователю взаимодействовать с системой.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     MainBank.main(args); // Запуск банковской системы
 * </pre>
 */
public class MainBank {

    /**
     * Главный метод, который инициализирует репозитории и запускает консольный интерфейс.
     * <p>
     * Этот метод служит точкой входа для программы. Он создаёт репозитории пользователей и счетов,
     * затем запускает консольный интерфейс для взаимодействия с пользователем.
     * </p>
     *
     * @param args аргументы командной строки (не используются в данном случае)
     */
    public static void main(String[] args) {
        UserRepository userRepository = new UserRepository();
        BankAccountRepository bankRepository = new BankAccountRepository();

        // Создание экземпляра консоли и запуск интерфейса
        Console console = new Console(userRepository, bankRepository);
        console.start();
    }
}
