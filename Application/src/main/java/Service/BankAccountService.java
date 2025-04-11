package Service;

import Model.BankAccount;
import Model.User;
import repository.BankAccountRepository;

import java.util.List;


public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    public boolean TryAddBankAccount(User user) {
        if (user == null) {
            return false;
        }
        BankAccount account = new BankAccount(user);
        user.getBankAccounts().add(account);
        return bankAccountRepository.TryAdd(account); // Только один вызов
    }



    public boolean TryDeleteAccount(User user, BankAccount account) {
        if (user == null || account == null) {
            return false;
        }

        List<BankAccount> accounts = user.getBankAccounts();
        if (!accounts.contains(account)) {
            return false;
        }

        accounts.remove(account);
        bankAccountRepository.TryDelete(account.getId());
        return true;
    }


    public BankAccount FindById(String id) {
        return bankAccountRepository.findById(id);
    }


    public List<BankAccount> GetAllBankAccounts() {
        return bankAccountRepository.getAllAccounts();
    }

    public boolean TryDeposit(BankAccount account, double amount){
        if (amount <= 0  || account == null) {
            return false;
        }
        account.TryDeposit(amount);

        return bankAccountRepository.TryUpdate(account);
    }

    public boolean TryWithdraw(BankAccount account, double amount){
        if (amount <= 0  || account == null) {
            return false;
        }
        account.TryWithdraw(amount);
        return bankAccountRepository.TryUpdate(account);
    }
}
