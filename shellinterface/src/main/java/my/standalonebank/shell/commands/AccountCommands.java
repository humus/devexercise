package my.standalonebank.shell.commands;

import my.standalonebank.domain.Account;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import my.standalonebank.shell.util.SecurityUtil;
import my.standalonebank.shell.exception.ShellException;
import my.standalonebank.shell.services.AccountService;
import my.standalonebank.shell.services.UserService;

@ShellComponent
public class AccountCommands extends BaseCommand {

    private static final Logger log = LoggerFactory.getLogger(AccountCommands.class);

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private AccountCommandsProvider accountCommandsProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @ShellMethodAvailability("userHasAccounts")
    @ShellMethod("Let's an user to withdraw from an account")
    public String withdraw(@ShellOption(value={"-a", "--account"}, defaultValue="")
            String accountNumber) {

        if (StringUtils.isBlank(accountNumber)) {
            throw new ShellException("provide account");
        }
        String username = securityUtil.getUserName();

        Account account = userService.getAccountByUserName(username, accountNumber);

        if (account == null) {
            throw new ShellException("Account does not exists");
        }

        accountCommandsProvider.withdraw(accountNumber);

        return String.format("Withdraw from account %s was successful", account);
    }

    @ShellMethodAvailability("userHasAccounts")
    @ShellMethod("Let's an user to deposit to an account") 
    public String deposit(@ShellOption(value={"-a", "--account"}, defaultValue="")
            String accountNumber) {

        if (StringUtils.isBlank(accountNumber)) {
            throw new ShellException("provide account");
        }

        if (!accountService.accountExists(accountNumber)) {
            throw new ShellException("Account does not exists");
        }

        log.debug("processing deposit to account {}", accountNumber);
        accountCommandsProvider.deposit(accountNumber);
        return String.format("Deposit to account %s was successful", accountNumber);
    }

}
