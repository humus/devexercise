package my.standalonebank.verifier.users;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Set;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.AccountRepository;
import my.standalonebank.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUsersTest {

    @InjectMocks
    private DatabaseUsers users;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository repository;

    private BankAccount scottAccount;

    @Before
    public void setUp() {
        String username = "scott";
        scottAccount = new BankAccount();
        scottAccount.setFirstName(username);
        scottAccount.setLastName("pilgrim");
        scottAccount.setPin("$2a$10$pLCdKXe59KhhnQfnnn0Vq.n4k3Bi1UkhiBdBtu4nVyyhUSdGhZXRK");
        scottAccount.setDocId("432143214321");

    }

    @Test
    public void testNotHavingAdminUser() {
        when(repository.findByUsername("admin")).thenReturn(null);
        users.prepareUser("admin", ".$2a$10$1xZCDrCTlQ2UQsIWmvjWYuXji/UQYKRKcWEKtuXoKmo5l.e1qOh/2");
        verify(repository).save(any(BankUser.class));
    }

    @Test
    public void testHavingAdminUser() {
        when(repository.findByUsername("admin")).thenReturn(new BankUser());
        users.prepareUser("admin", ".$2a$10$1xZCDrCTlQ2UQsIWmvjWYuXji/UQYKRKcWEKtuXoKmo5l.e1qOh/2");
        verify(repository, never()).save(any(BankUser.class));
    }

    @Test
    public void testUserAccountExists() {

        String username = "scott";
        when(repository.findByUsername(username)).thenReturn(createBankUserAccount());
        users.prepareAccount(username, scottAccount);

        verify(repository).findByUsername(username);
        verify(accountRepository, never()).save(isA(BankAccount.class));
    }

    @Test
    public void testUserAccountNotExists() {
        String username = "scott";
        when(repository.findByUsername(username)).thenReturn(createBankUser());
        users.prepareAccount(username, scottAccount);

        verify(repository).findByUsername(username);
        verify(accountRepository).save(isA(BankAccount.class));
    }

    private BankUser createBankUserAccount() {
        BankUser user = createBankUser();
        Set<BankAccount> accounts = new HashSet<>();
        accounts.add(new BankAccount());
        user.setAccounts(accounts);
        return user;
    }

    private BankUser createBankUser() {
        BankUser bankUser = new BankUser();
        bankUser.setId(1);
        bankUser.setUsername("scott");
        return bankUser;
    }


}
