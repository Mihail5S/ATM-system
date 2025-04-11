import Model.BankAccount;
import Model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Tests {
    private BankAccount bankAccount;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        bankAccount = new BankAccount(user);
    }

    @Test
    void TryWithdraw_SufficientBalance_ShouldUpdateBalance() {
        bankAccount.TryDeposit(100);

        boolean result = bankAccount.TryWithdraw(50);

        assertTrue(result);
        assertEquals(50, bankAccount.getBalance());
        assertEquals(2, bankAccount.getTransactions().size());
        assertEquals("Withdrawn: 50.0", bankAccount.getTransactions().get(1));
    }

    @Test
    void TryWithdraw_InsufficientBalance_ShouldReturnFalse() {
        boolean result = bankAccount.TryWithdraw(50);

        assertFalse(result);
        assertEquals(0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());
    }

    @Test
    void TryDeposit_PositiveAmount_ShouldUpdateBalance() {
        boolean result = bankAccount.TryDeposit(100);

        assertTrue(result);
        assertEquals(100, bankAccount.getBalance());
        assertEquals(1, bankAccount.getTransactions().size());
        assertEquals("Deposited: 100.0", bankAccount.getTransactions().get(0));
    }

    @Test
    void TryDeposit_NegativeAmount_ShouldReturnFalse() {
        boolean result = bankAccount.TryDeposit(-50);

        assertFalse(result);
        assertEquals(0, bankAccount.getBalance());
        assertEquals(0, bankAccount.getTransactions().size());
    }
}
