package my.standalonebank.shell.services;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import my.standalonebank.model.BankAccount;
import my.standalonebank.repository.AccountRepository;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private static final String ACCOUNT = "0000000001";

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @Mock
    private AccountRepository accountRepository;

    @Captor
    private ArgumentCaptor<BankAccount> accountCaptor;

    private BankAccount bankAccount;

    @Before
    public void setUp() {
        bankAccount = new BankAccount();
        bankAccount.setBalance(BigDecimal.ZERO);
        bankAccount.setAccountNumber(ACCOUNT);
    }

    @Test
    public void testAccountExists() {
        accountService.accountExists(ACCOUNT);
        verify(accountRepository).findByAccountNumber(any(String.class));
    }

    @Test
    public void testGetAccount() {
        accountService.getAccount(ACCOUNT);
        verify(accountRepository).findByAccountNumber(any(String.class));
    }

    @Test
    public void testDeposit() {
        when(accountRepository.findByAccountNumber(ACCOUNT))
                .thenReturn(bankAccount);

        accountService.deposit(bankAccount, new BigDecimal(10000));
        verify(accountRepository).findByAccountNumber(ACCOUNT);
        verify(accountRepository).save(accountCaptor.capture());

        BankAccount capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getBalance(), is(new BigDecimal(10000)));
    }

    @Test
    public void testWitdraw() {
        bankAccount.setBalance(new BigDecimal(10000));
        when(accountRepository.findByAccountNumber(ACCOUNT))
                .thenReturn(bankAccount);

        accountService.withdraw(bankAccount, new BigDecimal(10000));

        verify(accountRepository).save(accountCaptor.capture());
        BankAccount capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getBalance(), is(BigDecimal.ZERO));
    }

}
