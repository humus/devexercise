package my.standalonebank.shell.commands;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import my.standalonebank.exception.UserAlreadyExistsException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RunWith(MockitoJUnitRunner.class)
public class UserCommandsTest {

    public static final Logger log = LoggerFactory.getLogger(UserCommandsTest.class);

    @Mock
    private UserCommandProvider userCommandProvider;

    @InjectMocks
    private UserCommands userCommands;

    @Test
    public void testCreateUserCommand() {
        log.info("testing create user command");

        when(userCommandProvider.isUserPresent("scott"))
                .thenReturn(false);

        userCommands.createUser("scott");

        verify(userCommandProvider).isUserPresent("scott");
        verify(userCommandProvider).promptUserInformation();
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void testCreateUserCommand_userFound() {
        log.info("testing create user command");

        when(userCommandProvider.isUserPresent("scott"))
                .thenReturn(true);

        userCommands.createUser("scott");
    }
}
