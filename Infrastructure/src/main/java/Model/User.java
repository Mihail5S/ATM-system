package Model;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String login;

    @Column(name = "name")
    private String name;

    @Column(name = "age")
    private int age;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Enumerated(EnumType.STRING)
    @Column(name = "haircolor")
    private HairColor haircolor;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id")
    )
    private Set<User> friends;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<BankAccount> bankAccounts;


    public User() {
    }

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
        this.name = name;
        this.login = login;
        this.age = age;
        this.gender = gender;
        this.haircolor = haircolor;
        this.bankAccounts = new ArrayList<>();
        this.friends = new HashSet<User>();
    }

    /**
     * Получает логин пользователя.
     *
     * @return логин пользователя.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Получает имя пользователя.
     *
     * @return имя пользователя.
     */
    public String getName() {
        return name;
    }

    /**
     * Получает возраст пользователя.
     *
     * @return возраст пользователя.
     */
    public int getAge() {
        return age;
    }

    /**
     * Получает пол пользователя.
     *
     * @return пол пользователя.
     */
    public Gender getGender() {
        return gender;
    }

    /**
     * Получает цвет волос пользователя.
     *
     * @return цвет волос пользователя.
     */
    public HairColor getHaircolor() {
        return haircolor;
    }

    /**
     * Получает список друзей пользователя.
     *
     * @return список друзей.
     */
    public Set<User> getFriends() {
        return friends;
    }

    /**
     * Получает список банковских счетов пользователя.
     *
     * @return список банковских счетов.
     */
    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }
}
