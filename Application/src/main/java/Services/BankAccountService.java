package Services;

import Model.BankAccount;
import Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import repository.BankAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import repository.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class BankAccountService {

    private BankAccountRepository bankAccountRepository;
    private UserRepository userRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;
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
        if (success) bankAccountRepository.save(account);
        return success;
    }

    @Transactional
    public boolean tryWithdraw(BankAccount account, double amount) {
        if (account == null || amount <= 0) return false;

        boolean success = account.TryWithdraw(amount);
        if (success) bankAccountRepository.save(account);
        return success;
    }
}
