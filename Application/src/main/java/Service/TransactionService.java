package Service;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;

import java.util.Set;

/**
 * Класс {@code TransactionService} предоставляет методы для обработки переводов между банковскими счетами.
 * <p>
 * Этот класс позволяет выполнять переводы средств с учетом комиссии. Комиссия зависит от того, является ли получатель другом пользователя или нет.
 * </p>
 * <p>
 * Пример использования:
 * </p>
 * <pre>
 *     TransactionService service = new TransactionService();
 *     User sender = new User("John", "john123", 30, Gender.MALE, HairColor.BLACK);
 *     BankAccount from = new BankAccount("john123");
 *     BankAccount to = new BankAccount("alice123");
 *     service.TryTransfer(sender, from, to, 100); // Попытка перевода 100 с учетом комиссии
 * </pre>
 */
public class TransactionService {

    private static final double BASE_COMMISSION = 0.0;
    private static final double FRIEND_COMMISSION = 0.03;
    private static final double STRANGER_COMMISSION = 0.10;


    private final BankAccountRepository bankAccountRepository;

    public TransactionService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    /**
     * Попытка перевода средств между двумя банковскими счетами с учетом комиссии.
     * Если сумма перевода или один из параметров некорректен, операция не выполняется.
     * <p>
     * Комиссия зависит от того, является ли получатель другом отправителя. Если получатель друг, комиссия составляет 3%, если нет — 10%.
     * </p>
     *
     * @param sender пользователь, который выполняет перевод
     * @param from счет, с которого происходит снятие средств
     * @param to счет, на который зачисляются средства
     * @param amount сумма перевода
     * @return {@code true}, если перевод выполнен успешно, {@code false} в противном случае
     */
    public boolean TryTransfer(User sender, BankAccount from, BankAccount to, double amount) {
        if (sender == null || from == null || to == null || amount <= 0) {
            return false;
        }


        double commission = CalculateCommission(sender, from, to);
        double totalAmount = amount + amount * commission;

        if (!from.TryWithdraw(totalAmount)) {
            return false;
        }

        to.TryDeposit(amount);
        from.addTransaction("Transferred " + amount + " to " + to.getOwnerLogin() + " with commission " + (commission * 100) + "%");
        to.addTransaction("Received " + amount + " from " + from.getOwnerLogin());
        bankAccountRepository.TryUpdate(to);
        bankAccountRepository.TryUpdate(from);
        return true;
    }

    /**
     * Вычисляет комиссию для перевода в зависимости от отношения между отправителем и получателем.
     * <p>
     * Если получатель является другом отправителя, комиссия составляет 3%, если не является — 10%.
     * </p>
     *
     * @param sender пользователь, который выполняет перевод
     * @param from счет, с которого происходит снятие средств
     * @param to счет, на который зачисляются средства
     * @return величина комиссии (в долях от 1)
     */
    public double CalculateCommission(User sender, BankAccount from, BankAccount to) {
        if (from.getOwnerLogin().equals(to.getOwnerLogin())) {
            return BASE_COMMISSION;
        }


        Set<User> friends = sender.getFriends();
        for (User friend : friends) {
            if (friend.getLogin().equals(to.getOwnerLogin())) {
                return FRIEND_COMMISSION;
            }
        }

        return STRANGER_COMMISSION;
    }
}
