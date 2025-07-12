package Services;

import Model.BankAccount;
import Model.User;
import dto.AccountEvent;
import event.EventPublisher;
import repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private UserRepository userRepository;
    private final EventPublisher publisher;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository , EventPublisher publisher) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
        this.publisher = publisher;
    }

    @Transactional
    public boolean tryAddBankAccount(String login) {
        User user = userRepository.findByLogin(login);
        if (user == null) return false;

        BankAccount account = new BankAccount(user);
        account.setId(UUID.randomUUID().toString());
        account.setOwner(user);
        user.getBankAccounts().add(account);
        bankAccountRepository.save(account);
        userRepository.save(user);
        AccountEvent ev = new AccountEvent(
                account.getId(),
                "CREATE",
                Map.of(
                        "owner", account.getOwnerLogin(),
                        "balance", account.getBalance()
                ),
                Instant.now()
        );
        publisher.publishAccountEvent(ev);
        return true;
    }


    @Transactional(readOnly = true)
    public BankAccount findById(String id) {
        try {
            return bankAccountRepository.findById(id).orElse(null);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }


    public List<BankAccount> getAllBankAccounts() {
        return bankAccountRepository.findAll();
    }

    @Transactional
    public boolean tryDeposit(BankAccount account, double amount) {
        if (account == null || amount <= 0) return false;

        boolean success = account.TryDeposit(amount);
        if (success) {
            bankAccountRepository.save(account);

            AccountEvent ev = new AccountEvent(
                    account.getId(),
                    "DEPOSIT",
                    Map.of(
                            "amount", amount,
                            "newBalance", account.getBalance()
                    ),
                    Instant.now()
            );
            publisher.publishAccountEvent(ev);
        }

        return success;
    }

    @Transactional
    public boolean tryWithdraw(BankAccount account, double amount) {
        if (account == null || amount <= 0) return false;

        boolean success = account.TryWithdraw(amount);
        if (success) {
            bankAccountRepository.save(account);

            AccountEvent ev = new AccountEvent(
                    account.getId(),
                    "WITHDRAW",
                    Map.of(
                            "amount", amount,
                            "newBalance", account.getBalance()
                    ),
                    Instant.now()
            );
            publisher.publishAccountEvent(ev);
        }
        return success;
    }
}
