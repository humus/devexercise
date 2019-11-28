package my.standalonebank.shell.services;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import java.util.Collections;
import java.util.Map;
import java.util.AbstractMap;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.Spy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import my.standalonebank.domain.Account;
import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;
import my.standalonebank.repository.AccountRepository;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private UserRepository userRepository;

    @Spy
    private BCryptPasswordEncoder encoder;

    @Captor
    private ArgumentCaptor<BankUser> userCaptor;

    @Captor
    private ArgumentCaptor<BankAccount> accountCaptor;

    private Map<String, String> data;

    @Before
    public void setup() {
        data = Stream.of(entry("username", "alice"),
                entry("password", "Tiger360"),
                entry("pin", "0000"),
                entry("identifier", "112233344"),
                entry("firstName", "Alice"),
                entry("lastName", "Graham"))
            .collect(Collectors.toMap(e -> e.getKey(), e -> e.getValue()));
    }

    @Test
    public void testUserIsSavedWithDomainAccount() {
        userService.saveUserAccount(createAccount());

        verify(encoder).encode(any(String.class));
        verify(userRepository).save(userCaptor.capture());

        BankUser user = userCaptor.getValue();
        assertThat(user.getUsername(), is(data.get("username")));
        assertThat(user.getPassword(), is(data.get("password")));
        verify(accountRepository).save(accountCaptor.capture());

        BankAccount bankAccount = accountCaptor.getValue();
        assertThat(bankAccount.getPin(), is(not(data.get("pin"))));
        assertThat(bankAccount.getPin(), is(notNullValue()));
        assertThat(bankAccount.getDocId(), is(data.get("identifier")));
        assertThat(bankAccount.getFirstName(), is(data.get("firstName")));
        assertThat(bankAccount.getLastName(), is(data.get("lastName")));
        assertThat(bankAccount.getBalance(), is(notNullValue()));
    }

    private Account createAccount() {
        Account account = new Account();
        account.setUsername(data.get("username"));
        account.setPassword(data.get("password"));
        account.setPin(data.get("pin"));
        account.setIdentifier(data.get("identifier"));
        account.setFirstName(data.get("firstName"));
        account.setLastName(data.get("lastName"));
        return account;
    }

    private <K, V> AbstractMap.Entry<K, V> entry(K key, V value) {
        return new AbstractMap.SimpleEntry<>(key, value);
    }

}
