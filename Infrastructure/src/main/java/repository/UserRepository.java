package repository;

import Model.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Репозиторий для хранения и управления пользователями.
 * <p>
 * Этот класс предоставляет методы для добавления, удаления и поиска пользователей по их логинам.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     UserRepository repository = new UserRepository();
 *     User user = new User("john", "John Doe", 25, Gender.MALE, HairColor.BLACK);
 *     repository.TryAdd(user); // Добавление пользователя
 *     User foundUser = repository.findByLogin("john"); // Поиск пользователя по логину
 * </pre>
 */
public class UserRepository {

    private final HashMap<String, User> users = new HashMap<>();

    /**
     * Добавляет нового пользователя в репозиторий.
     * <p>
     * Если пользователь с таким логином уже существует, он не будет добавлен.
     * </p>
     *
     * @param user пользователь, которого нужно добавить
     * @return {@code true}, если пользователь был добавлен, {@code false}, если пользователь не был добавлен
     */
    public boolean TryAdd(User user) {
        if (user == null) {
            return false;
        }

        users.put(user.getLogin(), user);
        return true;
    }

    /**
     * Удаляет пользователя из репозитория по его логину.
     * <p>
     * Если пользователя с данным логином не существует, операция не будет выполнена.
     * </p>
     *
     * @param id логин пользователя, которого нужно удалить
     * @return {@code true}, если пользователь был удален, {@code false}, если пользователь не был удален
     */
    public boolean TryDelete(String id) {
        if (id == null) {
            return false;
        }

        users.remove(id);
        return true;
    }

    /**
     * Находит пользователя по его логину.
     *
     * @param login логин пользователя
     * @return {@code User} с заданным логином, или {@code null}, если пользователь не найден
     */
    public User findByLogin(String login) {
        return users.get(login);
    }

    /**
     * Возвращает список всех логинов пользователей в репозитории.
     *
     * @return список логинов всех пользователей
     */
    public List<String> getAllLogins() {
        return new ArrayList<>(users.keySet());
    }

    /**
     * Возвращает список всех пользователей в репозитории.
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}
