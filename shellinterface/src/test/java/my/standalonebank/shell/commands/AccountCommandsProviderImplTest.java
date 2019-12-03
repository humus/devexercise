package my.standalonebank.shell.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.same;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import my.standalonebank.model.BankAccount;
import my.standalonebank.shell.commands.prompt.PromptComponent;
import my.standalonebank.shell.exception.ShellException;
import my.standalonebank.shell.services.AccountService;

@RunWith(MockitoJUnitRunner.class)
public class AccountCommandsProviderImplTest {

    @InjectMocks
    private AccountCommandsProvider accountCommandsProvider = new AccountCommandsProviderImpl();

    @Mock
    private AccountService accountService;

    @Mock
    private PromptComponent promptComponent;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Test
    public void testNoAccountNumber() {
        accountCommandsProvider.withdraw("");
        verify(accountService, never()).withdraw(any(BankAccount.class),
                any(BigDecimal.class));
    }

    @Test
    public void testWithDraw() {
        String amountStr = "250";
        String accountNumber = "0000000001";
        BigDecimal amount = new BigDecimal(amountStr);

        when(promptComponent.promptText(any(String.class)))
                .thenReturn(amountStr);

        when(promptComponent.promptPassword(any(String.class)))
                .thenReturn("000")
                .thenReturn("000")
                .thenReturn("0000");

        BankAccount bankAccount = createBankAccount(accountNumber, amount);
        when(accountService.getAccount(accountNumber))
            .thenReturn(bankAccount);

        accountCommandsProvider.withdraw(accountNumber);

        verify(promptComponent, times(3)).promptPassword(any(String.class));
        verify(encoder, times(3)).matches(any(String.class), any(String.class));
        verify(accountService).withdraw(same(bankAccount), eq(amount));
    }

    @Test(expected=ShellException.class)
    public void testNegativeBalanceWithdraw() {
        String amountStr = "250";
        String accountNumber = "0000000001";
        BigDecimal amount = new BigDecimal(amountStr);

        when(promptComponent.promptText(any(String.class)))
                .thenReturn(amountStr);

        BankAccount bankAccount = createBankAccount(accountNumber, amount.subtract(BigDecimal.ONE));
        when(accountService.getAccount(accountNumber))
                .thenReturn(bankAccount);

        accountCommandsProvider.withdraw(accountNumber);

        verify(accountService, never()).withdraw(any(BankAccount.class), eq(amount));
    }

    @Test
    public void testDepositNoAccount() {
        accountCommandsProvider.deposit("");
        verify(accountService, never()).deposit(any(BankAccount.class), any(BigDecimal.class));
    }

    @Test
    public void testDepositToAccount() {
        String amountStr = "1000";
        String accountNumber = "0000000001";
        BigDecimal amount = new BigDecimal(amountStr);
        when(promptComponent.promptText(any(String.class))).thenReturn(amountStr);
        BankAccount bankAccount = createBankAccount(accountNumber, amount);
        when(accountService.getAccount(accountNumber))
            .thenReturn(bankAccount);

        accountCommandsProvider.deposit("0000000001");

        verify(accountService).deposit(same(bankAccount), eq(amount));
    }

    private BankAccount createBankAccount(String accountNumber, BigDecimal amount) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(amount);
        bankAccount.setAccountNumber(accountNumber);
        bankAccount.setPin("$2a$10$pLCdKXe59KhhnQfnnn0Vq.n4k3Bi1UkhiBdBtu4nVyyhUSdGhZXRK");
        return bankAccount;
    }
}
