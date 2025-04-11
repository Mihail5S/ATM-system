package Service;

import Model.BankAccount;
import Model.User;
import repository.UserRepository;

import java.util.List;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean TryAddUser(User user) {
        if (user == null) {
            return false;
        }
        return userRepository.TryAdd(user);
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


        return userRepository.TryDelete(account.getId());
    }

    /**
     * Находит пользователя по логину.
     * @param login логин пользователя
     * @return объект User или null, если пользователь не найден
     */
    public User FindUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    /**
     * Получает список всех пользователей.
     * @return список пользователей
     */
    public List<User> GetAllUsers() {
        return userRepository.getAllUsers();
    }
}
