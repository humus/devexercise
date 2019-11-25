package my.standalonebank.verifier.users;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUsersTest {

    @InjectMocks
    private DatabaseUsers users;

    @Mock
    private UserRepository repository;

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

}
