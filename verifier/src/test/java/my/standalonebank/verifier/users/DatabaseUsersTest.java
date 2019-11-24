package my.standalonebank.verifier.users;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import my.standalonebank.verifier.repository.UserRepository;
import org.mockito.Mock;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.runner.RunWith;
import org.junit.Test;

@RunWith(MockitoJUnitRunner.class)
public class DatabaseUsersTest {

    @InjectMocks
    private DatabaseUsers users;

    @Mock
    private UserRepository repository;

    @Test
    public void testHasAdminUser() {
        when(repository.findByUserName("admin"));
        users.prepareUser("admin");
    }

}
