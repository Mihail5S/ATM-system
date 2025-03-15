package ConsoleInterface.Commands;

import Model.Gender;
import Model.HairColor;
import Model.User;
import repository.UserRepository;

import java.util.Scanner;

/**
 * Команда для создания нового пользователя.
 * <p>
 * Эта команда позволяет пользователю ввести имя, логин, возраст, пол и цвет волос,
 * а затем создать нового пользователя в системе. Если логин уже занят, пользователю будет предложено ввести новый.
 * После успешного создания пользователя выводится информация о нем.
 * </p>
 */
public class CreateUserCommand implements IConsoleCommand {
    private final UserRepository userRepository;
    private final Scanner scanner;

    /**
     * Конструктор для создания команды создания пользователя.
     *
     * @param userRepository репозиторий для работы с пользователями
     * @param scanner сканер для ввода данных с консоли
     */
    public CreateUserCommand(UserRepository userRepository, Scanner scanner) {
        this.userRepository = userRepository;
        this.scanner = scanner;
    }

    /**
     * Выполняет команду для создания нового пользователя.
     * <p>
     * Запрашивает у пользователя имя, логин, возраст, пол и цвет волос,
     * создает нового пользователя и добавляет его в репозиторий.
     * Если логин уже занят, пользователю будет предложено ввести новый.
     * После успешного добавления выводится информация о пользователе.
     * </p>
     */
    @Override
    public void execute() {
        System.out.println("Введите имя: ");
        String name = scanner.nextLine();

        System.out.println("Придумайте login: ");
        String login = scanner.nextLine();

        // Проверка уникальности логина
        while (userRepository.findByLogin(login) != null) {
            System.out.println("Этот логин занят, Придумайте login: ");
            login = scanner.nextLine();
        }

        System.out.println("Введите возраст: ");
        int age = Integer.parseInt(scanner.nextLine());

        System.out.println("Ваш пол: 1) Мужской  2) Женский");
        System.out.println("Введите только число: ");
        int genderNum = Integer.parseInt(scanner.nextLine());
        Gender gender = (genderNum == 1) ? Gender.MALE : Gender.FEMALE;

        System.out.println("Ваш цвет волос: 1) Черный 2) Белый 3) другой");
        System.out.println("Введите только число: ");
        int hairNum = Integer.parseInt(scanner.nextLine());
        HairColor hairColor = (hairNum == 1) ? HairColor.BLACK : (hairNum == 2) ? HairColor.WHITE : HairColor.OTHER;

        // Создание пользователя
        var user = new User(name, login, age, gender, hairColor);

        // Добавление пользователя в репозиторий
        if (userRepository.TryAdd(user)) {
            System.out.println("Login:" + user.getLogin());
            System.out.println("Имя:" + user.getName());
            System.out.println("Возраст:" + user.getAge());
            System.out.println("Пол:" + user.getGender().toString());
            System.out.println("Цвет волос:" + user.getHaircolor().toString());
        } else {
            System.out.println("Ошибка!");
        }
    }
}
