package Service;

import Model.User;
import repository.UserRepository;

import java.util.Set;

/**
 * Класс {@code FriendService} предоставляет методы для управления списком друзей пользователей.
 * <p>
 * Этот класс позволяет добавлять и удалять друзей пользователей.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     FriendService service = new FriendService();
 *     User user1 = new User("John", "john123", 30, Gender.MALE, HairColor.BLACK);
 *     User user2 = new User("Alice", "alice123", 25, Gender.FEMALE, HairColor.WHITE);
 *     service.TryAddFriends(user1, user2); // Добавить друга
 *     service.removeFriend(user1, user2); // Удалить друга
 * </pre>
 */
public class FriendService {

    private final UserRepository userRepository;

    public FriendService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    /**
     * Попытка добавить пользователя в друзья.
     * Если один из пользователей равен {@code null}, либо друзья уже добавлены, операция не выполняется.
     *
     * @param user основной пользователь, который добавляет друга
     * @param friend пользователь, который будет добавлен в друзья
     * @return {@code true}, если операция добавления друга прошла успешно, {@code false} в противном случае
     */
    public boolean TryAddFriends(User user, User friend) {
        if (user == null || friend == null) {
            return false;
        }

        // Проверяем, что друзья еще не добавлены
        if (user.getFriends().contains(friend) || friend.getFriends().contains(user)) {
            return false;
        }

        Set<User> friends = user.getFriends();
        if (!friends.contains(friend)) {
            friends.add(friend);
            friend.getFriends().add(user);
            return userRepository.TryUpdate(user) && userRepository.TryUpdate(friend);
        }
        return false;
    }

    /**
     * Попытка удалить пользователя из списка друзей.
     * Если один из пользователей равен {@code null}, операция не выполняется.
     *
     * @param user основной пользователь, который удаляет друга
     * @param friend пользователь, которого нужно удалить из друзей
     * @return {@code true}, если операция удаления друга прошла успешно, {@code false} в противном случае
     */
    public boolean removeFriend(User user, User friend) {
        if (user == null || friend == null) {
            return false;
        }
        Set<User> friends = user.getFriends();
        friends.remove(friend);
        friend.getFriends().remove(user);
        return userRepository.removeFriendDirect(user.getLogin(), friend.getLogin());
    }
}
