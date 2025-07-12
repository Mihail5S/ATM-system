package Services;

import Model.Gender;
import Model.HairColor;
import Model.User;
import dto.ClientEvent;
import event.EventPublisher;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final EventPublisher publisher;

    public UserService(UserRepository userRepository, EventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Transactional
    public boolean TryAddUser(User req) {
        if (req == null) return false;
        User newUser = new User(req.getName(), req.getLogin(), req.getAge(),
                Gender.valueOf(req.getGender().toString()),
                HairColor.valueOf(req.getHaircolor().toString()));
        userRepository.save(newUser);
        ClientEvent ev = new ClientEvent(
                newUser.getLogin(),
                "CREATE",
                Map.of(
                        "login", newUser.getLogin(),
                        "name", newUser.getName(),
                        "age", newUser.getAge(),
                        "friends", newUser.getFriends()
                ),
                Instant.now()
        );
        publisher.publishClientEvent(ev);
        return true;
    }

    public List<User> getAllUsersWithAccounts() {
        return userRepository.findAllWithBankAccounts();
    }

    @Transactional
    public boolean TryDeleteAccount(String login) {
        if (!userRepository.existsById(login)) return false;
        userRepository.deleteById(login);
        return true;
    }

    public User FindUserByLogin(String login) {
        return userRepository.findById(login).orElse(null);
    }

    public List<User> getFilteredUsers(HairColor hairColor, Gender gender) {
        List<User> users = GetAllUsers();

        if (hairColor != null) {
            users = users.stream()
                    .filter(u -> u.getHaircolor() == hairColor)
                    .toList();
        }
        if (gender != null) {
            users = users.stream()
                    .filter(u -> u.getGender() == gender)
                    .toList();
        }
        return users;
    }

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void RemoveFriendDirect(String login1, String login2) {
        userRepository.removeFriendDirect(login1, login2);
    }
}
