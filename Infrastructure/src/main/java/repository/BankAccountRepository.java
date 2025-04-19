package repository;

import Model.BankAccount;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.List;

/**
 * Репозиторий для хранения и управления банковскими счетами.
 * <p>
 * Этот класс предоставляет методы для добавления, удаления и поиска банковских счетов по идентификатору.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     BankAccountRepository repository = new BankAccountRepository();
 *     BankAccount account = new BankAccount("john123");
 *     repository.TryAdd(account); // Добавление счета
 *     BankAccount foundAccount = repository.findById("john123"); // Поиск счета по ID
 * </pre>
 */
public class BankAccountRepository {


    /**
     * Добавляет новый банковский счет в репозиторий.
     * <p>
     * Если счет уже существует (по идентификатору), он не будет добавлен.
     * </p>
     *
     * @param account банковский счет, который нужно добавить
     * @return {@code true}, если счет был добавлен, {@code false}, если счет не был добавлен
     */
    public boolean TryAdd(BankAccount account) {
        if (account == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.save(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        }
    }

    /**
     * Удаляет банковский счет из репозитория по его идентификатору.
     * <p>
     * Если счет с данным идентификатором не существует, операция не будет выполнена.
     * </p>
     *
     * @param id идентификатор счета, который необходимо удалить
     * @return {@code true}, если счет был удален, {@code false}, если счет не был удален
     */
    public boolean TryDelete(String id) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            BankAccount account = session.get(BankAccount.class, id);
            if (account == null) return false;

            transaction = session.beginTransaction();
            session.delete(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        }
    }

    /**
     * Находит банковский счет по его идентификатору.
     *
     * @param id идентификатор счета
     * @return {@code BankAccount} с заданным идентификатором, или {@code null}, если счет не найден
     */
    public BankAccount findById(String id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(BankAccount.class, id);
        }
    }

    /**
     * Возвращает список всех банковских счетов в репозитории.
     *
     * @return список всех банковских счетов
     */
    public List<BankAccount> getAllAccounts() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<BankAccount> query = session.createQuery("FROM BankAccount", BankAccount.class);
            return query.list();
        }
    }

    /**
     * Возвращает список всех идентификаторов банковских счетов.
     *
     * @return список идентификаторов всех банковских счетов
     */
    public List<String> getAllAccountIds() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            Query<String> query = session.createQuery("SELECT b.id FROM BankAccount b", String.class);
            return query.list();
        }
    }

    public boolean TryUpdate(BankAccount account) {
        if (account == null) return false;

        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.update(account);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            return false;
        }
    }

}
