package repository;

import Model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Репозиторий для хранения и управления пользователями.
 * <p>
 * Этот класс предоставляет методы для добавления, удаления и поиска пользователей по их логинам.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     UserRepository repository = new UserRepository();
 *     User user = new User("john", "John Doe", 25, Gender.MALE, HairColor.BLACK);
 *     repository.TryAdd(user); // Добавление пользователя
 *     User foundUser = repository.findByLogin("john"); // Поиск пользователя по логину
 * </pre>
 */
public class UserRepository {

    /**
     * Добавляет нового пользователя в репозиторий.
     * <p>
     * Если пользователь с таким логином уже существует, он не будет добавлен.
     * </p>
     *
     * @param user пользователь, которого нужно добавить
     * @return {@code true}, если пользователь был добавлен, {@code false}, если пользователь не был добавлен
     */
    public boolean TryAdd(User user) {
        if (user == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        }
    }

    /**
     * Удаляет пользователя из репозитория по его логину.
     * <p>
     * Если пользователя с данным логином не существует, операция не будет выполнена.
     * </p>
     *
     * @param id логин пользователя, которого нужно удалить
     * @return {@code true}, если пользователь был удален, {@code false}, если пользователь не был удален
     */
    public boolean TryDelete(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            User user = session.get(User.class, id);
            if (user == null) return false;

            transaction = session.beginTransaction();

            Query<User> query = session.createQuery("FROM User", User.class);
            List<User> allUsers = query.list();
            for (User u : allUsers) {
                if (u.getFriends().remove(user)) {
                    session.update(u);
                }
            }

            user.getFriends().clear();
            session.update(user);

            session.delete(user);

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeFriendDirect(String userLogin, String friendLogin) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Transaction tx = session.beginTransaction();
            Query<?> query = session.createNativeQuery(
                    "DELETE FROM user_friends WHERE (user_id = ?1 AND friend_id = ?2) OR (user_id = ?2 AND friend_id = ?1)"
            );
            query.setParameter(1, userLogin);
            query.setParameter(2, friendLogin);
            query.executeUpdate();
            tx.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



    /**
     * Находит пользователя по его логину.
     *
     * @param login логин пользователя
     * @return {@code User} с заданным логином, или {@code null}, если пользователь не найден
     */
    public User findByLogin(String login) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(User.class, login);
        }
    }

    /**
     * Возвращает список всех логинов пользователей в репозитории.
     *
     * @return список логинов всех пользователей
     */
    public List<String> getAllLogins() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("SELECT u.login FROM User u", String.class);
            return query.list();
        }
    }

    /**
     * Возвращает список всех пользователей в репозитории.
     *
     * @return список всех пользователей
     */
    public List<User> getAllUsers() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<User> query = session.createQuery("FROM User", User.class);
            return query.list();
        }
    }

    public boolean TryUpdate(User account) {
        if (account == null || account.getLogin() == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();

            User managedUser = session.get(User.class, account.getLogin());
            if (managedUser == null) return false;

            managedUser.getFriends().clear();

            for (User friend : account.getFriends()) {
                User managedFriend = session.get(User.class, friend.getLogin());
                if (managedFriend != null) {
                    managedUser.getFriends().add(managedFriend);
                }
            }

            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            return false;
        }
    }

}
