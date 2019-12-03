package my.standalonebank.shell.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import my.standalonebank.shell.util.SecurityUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankTransaction;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.AccountRepository;
import my.standalonebank.repository.TransactionRepository;

import org.mockito.Spy;


@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    private static final String ACCOUNT = "0000000001";

    @InjectMocks
    private AccountService accountService = new AccountServiceImpl();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Spy
    private SecurityUtil securityUtil;

    @Captor
    private ArgumentCaptor<BankAccount> accountCaptor;

    @Captor
    private ArgumentCaptor<BankTransaction> transactionCaptor;

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

        doReturn(new BankUser())
                .when(securityUtil).getCurrentUser();

        accountService.deposit(bankAccount, new BigDecimal(10000));
        verify(accountRepository).save(accountCaptor.capture());
        verify(transactionRepository).save(transactionCaptor.capture());

        BankAccount capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getBalance(), is(new BigDecimal(10000)));
        BankTransaction capturedTx = transactionCaptor.getValue();
        assertThat(capturedTx.getBankAccount(), is(bankAccount));
        assertThat(capturedTx.getDescription(), is("deposit"));
        assertThat(capturedTx.getCreatedAt(), is(notNullValue()));
        assertThat(capturedTx.getBankUser(), is(notNullValue()));
    }

    @Test
    public void testWitdraw() {
        bankAccount.setBalance(new BigDecimal(10000));
        when(accountRepository.findByAccountNumber(ACCOUNT))
                .thenReturn(bankAccount);

        doReturn(new BankUser())
                .when(securityUtil).getCurrentUser();

        accountService.withdraw(bankAccount, new BigDecimal(10000));

        verify(accountRepository).save(accountCaptor.capture());
        BankAccount capturedAccount = accountCaptor.getValue();
        assertThat(capturedAccount.getBalance(), is(BigDecimal.ZERO));
        verify(transactionRepository).save(transactionCaptor.capture());

        BankTransaction capturedTx = transactionCaptor.getValue();
        assertThat(capturedTx.getBankAccount(), is(bankAccount));
        assertThat(capturedTx.getDescription(), is("withdraw"));
        assertThat(capturedTx.getCreatedAt(), is(notNullValue()));
        assertThat(capturedTx.getBankUser(), is(notNullValue()));
    }

}
