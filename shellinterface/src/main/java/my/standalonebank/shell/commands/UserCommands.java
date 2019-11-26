package my.standalonebank.shell.commands;

import my.standalonebank.exception.UserAlreadyExistsException;
import my.standalonebank.model.BankUser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class UserCommands {

    public static final Logger log = LoggerFactory.getLogger(UserCommands.class);

    @Autowired
    private UserCommandProvider userCommandProvider;

    @ShellMethod("Command to create a user")
    public String createUser(@ShellOption({"-u", "--username"}) String userName) {

        log.info("working with create user");
        if (userCommandProvider.isUserPresent(userName)) {
            throw new UserAlreadyExistsException();
        }

        BankUser bankUser = userCommandProvider.promptUserInformation();

        return userName;
    }
}
