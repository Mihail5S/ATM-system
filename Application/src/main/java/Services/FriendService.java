package Services;

import Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.Set;

@Service
public class FriendService {

    private final UserRepository userRepository;

    public FriendService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public boolean tryAddFriends(User user, User friend) {
        if (user == null || friend == null) {
            return false;
        }

        if (user.getFriends().contains(friend) || friend.getFriends().contains(user)) {
            return false;
        }

        user.getFriends().add(friend);
        friend.getFriends().add(user);

        userRepository.save(user);
        userRepository.save(friend);
        return true;
    }

    @Transactional
    public boolean removeFriend(User user, User friend) {
        if (user == null || friend == null) {
            return false;
        }

        user.getFriends().remove(friend);
        friend.getFriends().remove(user);

        userRepository.save(user);
        userRepository.save(friend);
        return true;
    }
}
