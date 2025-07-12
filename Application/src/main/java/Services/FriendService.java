package Services;

import Model.User;
import dto.ClientEvent;
import event.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.time.Instant;
import java.util.Map;


@Service
public class FriendService {

    private final UserRepository userRepository;
    private final EventPublisher publisher;

    public FriendService(UserRepository userRepository, EventPublisher publisher) {
        this.userRepository = userRepository;
        this.publisher = publisher;
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
        ClientEvent ev = new ClientEvent(
                user.getLogin(),
                "ADD_FRIEND",
                Map.of(
                        "login", user.getLogin(),
                        "name", user.getName(),
                        "age", user.getAge(),
                        "friends", user.getFriends().toString()
                ),
                Instant.now()
        );
        publisher.publishClientEvent(ev);

        ClientEvent evf = new ClientEvent(
                friend.getLogin(),
                "ADD_FRIEND",
                Map.of(
                        "login", friend.getLogin(),
                        "name", friend.getName(),
                        "age", friend.getAge(),
                        "friends", friend.getFriends().toString()
                ),
                Instant.now()
        );
        publisher.publishClientEvent(evf);
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
        ClientEvent ev = new ClientEvent(
                user.getLogin(),
                "DELETE_FRIEND",
                Map.of(
                        "login", user.getLogin(),
                        "name", user.getName(),
                        "age", user.getAge(),
                        "friends", user.getFriends().toString()
                ),
                Instant.now()
        );
        publisher.publishClientEvent(ev);

        ClientEvent evf = new ClientEvent(
                friend.getLogin(),
                "DELETE_FRIEND",
                Map.of(
                        "login", friend.getLogin(),
                        "name", friend.getName(),
                        "age", friend.getAge(),
                        "friends", friend.getFriends().toString()
                ),
                Instant.now()
        );
        publisher.publishClientEvent(evf);

        return true;
    }
}
