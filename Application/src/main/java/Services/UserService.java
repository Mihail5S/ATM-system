package Services;

import Model.Gender;
import Model.HairColor;
import Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean TryAddUser(User req) {
        if (req == null) return false;
        User newUser = new User(req.getName(), req.getLogin(), req.getAge(),
                Gender.valueOf(req.getGender().toString()),
                HairColor.valueOf(req.getHaircolor().toString()));
        userRepository.save(newUser);
        return true;
    }

    public List<User> getAllUsersWithAccounts() {
        return userRepository.findAllWithBankAccounts();
    }

    public boolean TryDeleteAccount(String login) {
        if (!userRepository.existsById(login)) return false;
        userRepository.deleteById(login);
        return true;
    }

    public User FindUserByLogin(String login) {
        return userRepository.findById(login).orElse(null);
    }

    public List<User> GetAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public void RemoveFriendDirect(String login1, String login2) {
        userRepository.removeFriendDirect(login1, login2);
    }
}
