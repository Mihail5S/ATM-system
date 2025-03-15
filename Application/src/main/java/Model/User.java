package Model;

import java.util.ArrayList;
import java.util.List;

/**
 * Класс {@code User} представляет пользователя в системе.
 * <p>
 * Пользователь имеет уникальные данные, такие как имя, возраст, пол, цвет волос, а также может иметь несколько друзей и банковских счетов.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     User user = new User("John Doe", "john123", 25, Gender.MALE, HairColor.BLACK);
 *     user.getFriends(); // Получить список друзей
 * </pre>
 */
public class User {
    private String Login;
    private String Name;
    private int Age;
    private Gender Gender;
    private HairColor Haircolor;
    private List<User> Friends;
    private List<BankAccount> BankAccounts;

    /**
     * Конструктор для создания нового пользователя.
     *
     * @param name имя пользователя
     * @param login уникальный логин пользователя
     * @param age возраст пользователя
     * @param gender пол пользователя
     * @param haircolor цвет волос пользователя
     */
    public User(String name, String login, int age, Gender gender, HairColor haircolor) {
        this.Name = name;
        this.Age = age;
        this.Gender = gender;
        this.Haircolor = haircolor;
        this.Login = login;
        this.BankAccounts = new ArrayList<>();
        this.Friends = new ArrayList<>();
    }

    /**
     * Получает логин пользователя.
     *
     * @return логин пользователя.
     */
    public String getLogin() {
        return Login;
    }

    /**
     * Получает имя пользователя.
     *
     * @return имя пользователя.
     */
    public String getName() {
        return Name;
    }

    /**
     * Получает возраст пользователя.
     *
     * @return возраст пользователя.
     */
    public int getAge() {
        return Age;
    }

    /**
     * Получает пол пользователя.
     *
     * @return пол пользователя.
     */
    public Gender getGender() {
        return Gender;
    }

    /**
     * Получает цвет волос пользователя.
     *
     * @return цвет волос пользователя.
     */
    public HairColor getHaircolor() {
        return Haircolor;
    }

    /**
     * Получает список друзей пользователя.
     *
     * @return список друзей.
     */
    public List<User> getFriends() {
        return Friends;
    }

    /**
     * Получает список банковских счетов пользователя.
     *
     * @return список банковских счетов.
     */
    public List<BankAccount> getBankAccounts() {
        return BankAccounts;
    }
}
