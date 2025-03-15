package ConsoleInterface.Commands;

import Model.BankAccount;
import Model.User;
import Service.UserService;
import repository.UserRepository;

/**
 * Команда для отображения списка всех пользователей и их банковских счетов.
 * <p>
 * Эта команда выводит в консоль информацию о каждом пользователе, а также список их банковских счетов
 * и балансов. Если у пользователя нет счетов, выводится соответствующее сообщение.
 * </p>
 */
public class ListUsersAndBankAccountsCommand implements IConsoleCommand {
    private final UserRepository userRepository;

    /**
     * Конструктор класса {@link ListUsersAndBankAccountsCommand}.
     *
     * @param userRepository Репозиторий пользователей, для получения списка всех пользователей.
     */
    public ListUsersAndBankAccountsCommand(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Выполняет команду, выводя список всех пользователей и их банковских счетов в консоль.
     * Для каждого пользователя отображается:
     * <ul>
     *   <li>Логин пользователя</li>
     *   <li>Если у пользователя нет счетов, выводится сообщение "Нет счетов"</li>
     *   <li>Если у пользователя есть счета, выводятся их ID и баланс</li>
     * </ul>
     */
    @Override
    public void execute() {
        System.out.println("Список пользователей и их банковских счетов:");
        UserService userService = new UserService(userRepository);
        for (User user : userService.GetAllUsers()) {
            System.out.println("👤 " + user.getLogin());
            if (user.getBankAccounts().isEmpty()) {
                System.out.println("   ❌ Нет счетов");
            } else {
                for (BankAccount account : user.getBankAccounts()) {
                    System.out.println("   💳 Счет ID: " + account.getId() + ", Баланс: " + account.getBalance());
                }
            }
        }
    }
}
