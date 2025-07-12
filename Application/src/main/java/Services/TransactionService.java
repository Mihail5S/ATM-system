package Services;

import Model.BankAccount;
import Model.User;
import dto.AccountEvent;
import event.EventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.BankAccountRepository;

import java.time.Instant;
import java.util.Map;
import java.util.Set;

@Service
public class TransactionService {

    private static final double BASE_COMMISSION = 0.0;
    private static final double FRIEND_COMMISSION = 0.03;
    private static final double STRANGER_COMMISSION = 0.10;

    private final BankAccountRepository bankAccountRepository;
    private final EventPublisher publisher;

    public TransactionService(BankAccountRepository bankAccountRepository, EventPublisher publisher) {
        this.bankAccountRepository = bankAccountRepository;
        this.publisher = publisher;
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
        AccountEvent evFrom = new AccountEvent(
                from.getId(),
                "TRANSFER_OUT",
                Map.of(
                        "toAccountId", to.getId(),
                        "amount", amount,
                        "newBalance", from.getBalance()
                ),
                Instant.now()
        );
        publisher.publishAccountEvent(evFrom);

        AccountEvent evTo = new AccountEvent(
                to.getId(),
                "TRANSFER_IN",
                Map.of(
                        "fromAccountId", from.getId(),
                        "amount", amount,
                        "newBalance", to.getBalance()
                ),
                Instant.now()
        );
        publisher.publishAccountEvent(evTo);
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
