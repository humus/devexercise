package my.standalonebank.shell.commands;

import my.standalonebank.domain.Account;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.doReturn;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;

import my.standalonebank.shell.exception.ShellException;
import my.standalonebank.shell.services.UserService;
import my.standalonebank.shell.util.SecurityUtil;

@RunWith(MockitoJUnitRunner.class)
public class AccountCommandsTest {

    private static final String ACCOUNT = "0000000001";
    private static final String ALICE = "alice";


    @InjectMocks
    private AccountCommands accountCommands = new AccountCommands();

    @Spy
    private SecurityUtil securityUtil;

    @Mock
    private AccountCommandsProvider accountCommandsProvider;

    @Mock
    private UserService userService;

    @Test(expected = ShellException.class)
    public void testBadWithdraw() {
        accountCommands.withdraw("");
    }

    @Test(expected = ShellException.class)
    public void testAccountDoesNotExists() {
        doReturn(ALICE)
                .when(securityUtil).getUserName();

        when(userService.getAccountByUserName(ALICE, ACCOUNT))
                .thenReturn(null);

        accountCommands.withdraw(ACCOUNT);
    }

    @Test
    public void testCompleteWithdraw() {

        doReturn(ALICE)
                .when(securityUtil).getUserName();

        when(userService.getAccountByUserName(ALICE, ACCOUNT))
                .thenReturn(createAccount());

        accountCommands.withdraw(ACCOUNT);

        verify(accountCommandsProvider).withdraw(ACCOUNT);
    }

    private Account createAccount() {
        Account account = new Account();
        account.setAccountNumber(ACCOUNT);
        return account;
    }

}
