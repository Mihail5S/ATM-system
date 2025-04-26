package Services;

import Model.BankAccount;
import Model.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BankAccountRepository;

import java.util.Set;

@Service
public class TransactionService {

    private static final double BASE_COMMISSION = 0.0;
    private static final double FRIEND_COMMISSION = 0.03;
    private static final double STRANGER_COMMISSION = 0.10;

    private final BankAccountRepository bankAccountRepository;

    public TransactionService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public boolean tryTransfer(User sender, BankAccount from, BankAccount to, double amount) {
        if (sender == null || from == null || to == null || amount <= 0) {
            return false;
        }

        double commission = calculateCommission(sender, from, to);
        double totalAmount = amount + amount * commission;

        if (!from.TryWithdraw(totalAmount)) {
            return false;
        }

        to.TryDeposit(amount);

        from.addTransaction("Transferred " + amount + " to " + to.getOwnerLogin() + " with commission " + (commission * 100) + "%");
        to.addTransaction("Received " + amount + " from " + from.getOwnerLogin());

        bankAccountRepository.save(from);
        bankAccountRepository.save(to);
        return true;
    }

    public double calculateCommission(User sender, BankAccount from, BankAccount to) {
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
