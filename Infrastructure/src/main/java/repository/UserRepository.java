package repository;

import Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, String> {
    User findByLogin(String login);

    @Query("SELECT u.login FROM User u")
    List<String> getAllLogins();

    @Modifying
    @Query(value = "DELETE FROM user_friends WHERE (user_id = ?1 AND friend_id = ?2) OR (user_id = ?2 AND friend_id = ?1)", nativeQuery = true)
    void removeFriendDirect(String userLogin, String friendLogin);

    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.bankAccounts")
    List<User> findAllWithBankAccounts();
}
