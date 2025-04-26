package Model;

import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;
import java.util.*;

@Entity
@Table(name = "users")
@DynamicUpdate
public class User {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    private String login;

    @Column(name = "name", nullable = false)
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
    private Set<User> friends = new HashSet<>();

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> bankAccounts = new LinkedList<>();


    public User() {
    }

    public User(String name, String login, int age, Gender gender, HairColor haircolor) {
        this.name = name;
        this.login = login;
        this.age = age;
        this.gender = gender;
        this.haircolor = haircolor;
    }

    public String getLogin() {
        return login;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Gender getGender() {
        return gender;
    }

    public HairColor getHaircolor() {
        return haircolor;
    }

    public Set<User> getFriends() {
        return friends;
    }

    public List<BankAccount> getBankAccounts() {
        return bankAccounts;
    }

}
