package my.standalonebank.shell.commands;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.standalonebank.domain.Account;
import my.standalonebank.exception.UserAlreadyExistsException;
import my.standalonebank.shell.services.UserService;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandsTest {

    public static final Logger log = LoggerFactory.getLogger(UserCommandsTest.class);

    @Mock
    private UserCommandProvider userCommandProvider;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserCommands userCommands;

    @Test
    public void testCreateUserCommand() {
        log.info("testing create user command");

        when(userCommandProvider.isUserPresent("scott"))
                .thenReturn(false);

        when(userCommandProvider.promptUserInformation())
                .thenReturn(new Account());

        userCommands.createUser("scott");

        verify(userService).saveUserAccount(any(Account.class));
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testCreateUserCommand_userFound() {
        log.info("testing create user command");

        when(userCommandProvider.isUserPresent("scott"))
                .thenReturn(true);

        userCommands.createUser("scott");
    }
}
