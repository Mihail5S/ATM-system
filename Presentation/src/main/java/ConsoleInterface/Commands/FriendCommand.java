package ConsoleInterface.Commands;

import Model.User;
import Service.FriendService;
import repository.UserRepository;

import java.util.List;
import java.util.Scanner;

/**
 * Команда для управления списком друзей пользователя.
 * <p>
 * Эта команда позволяет пользователю добавлять и удалять друзей, а также просматривать список друзей.
 * </p>
 */
public class FriendCommand implements IConsoleCommand {
    private final UserRepository userRepository;
    private final FriendService friendService;
    private final Scanner scanner;

    /**
     * Конструктор для создания команды управления друзьями.
     *
     * @param userRepository репозиторий для работы с пользователями
     * @param scanner сканер для ввода данных с консоли
     */
    public FriendCommand(UserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.friendService = new FriendService();
        this.scanner = scanner;
    }

    /**
     * Выполняет команду для добавления, удаления или отображения списка друзей.
     * <p>
     * Запрашивает у пользователя логин, затем позволяет выбрать одно из действий:
     * 1) Добавить друга,
     * 2) Удалить друга,
     * 3) Просмотреть список друзей.
     * </p>
     */
    @Override
    public void execute() {
        System.out.print("Введите логин пользователя: ");
        String userLogin = scanner.nextLine();
        User user = userRepository.findByLogin(userLogin);
        if (user == null) {
            System.out.println("Ошибка! Пользователь не найден.");
            return;
        }

        // Вывод меню действий
        System.out.println("Выберите действие: ");
        System.out.println("1 - Добавить друга");
        System.out.println("2 - Удалить друга");
        System.out.println("3 - Посмотреть список друзей");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Очистка буфера

        switch (choice) {
            case 1:
                addFriend(user);
                break;
            case 2:
                removeFriend(user);
                break;
            case 3:
                showFriends(user);
                break;
            default:
                System.out.println("Ошибка! Неверный выбор.");
        }
    }

    /**
     * Добавляет пользователя в друзья.
     * <p>
     * Запрашивает логин друга и, если друг найден, добавляет его в список друзей пользователя.
     * Если друг уже добавлен, выводится сообщение об этом.
     * </p>
     *
     * @param user пользователь, который добавляет друга
     */
    private void addFriend(User user) {
        System.out.print("Введите логин друга: ");
        String friendLogin = scanner.nextLine();
        User friend = userRepository.findByLogin(friendLogin);

        if (friend == null) {
            System.out.println("Ошибка! Друг не найден.");
            return;
        }

        // Попытка добавить друга
        if (friendService.TryAddFriends(user, friend)) {
            System.out.println(friendLogin + " добавлен в друзья.");
        } else {
            System.out.println(friendLogin + " уже в друзьях.");
        }
    }

    /**
     * Удаляет пользователя из списка друзей.
     * <p>
     * Запрашивает логин друга и, если друг найден в списке друзей, удаляет его.
     * Если друг не был в списке, выводится сообщение об этом.
     * </p>
     *
     * @param user пользователь, который удаляет друга
     */
    private void removeFriend(User user) {
        System.out.print("Введите логин друга: ");
        String friendLogin = scanner.nextLine();
        User friend = userRepository.findByLogin(friendLogin);

        if (friend == null) {
            System.out.println("Ошибка! Друг не найден.");
            return;
        }

        // Попытка удалить друга
        if (friendService.removeFriend(user, friend)) {
            System.out.println(friendLogin + " удален из друзей.");
        } else {
            System.out.println(friendLogin + " не был в друзьях.");
        }
    }

    /**
     * Отображает список друзей пользователя.
     * <p>
     * Выводит список всех друзей пользователя. Если друзья отсутствуют, выводится соответствующее сообщение.
     * </p>
     *
     * @param user пользователь, чьи друзья отображаются
     */
    private void showFriends(User user) {
        List<User> friends = user.getFriends();
        if (friends.isEmpty()) {
            System.out.println("У вас ещё нет друзей :(");
        } else {
            System.out.println("Ваши друзья:");
            for (User friend : friends) {
                System.out.println("- " + friend.getLogin());
            }
        }
    }
}
