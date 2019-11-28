package my.standalonebank.shell.commands;

import java.util.stream.Collectors;

import my.standalonebank.shell.services.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;

import my.standalonebank.exception.UserAlreadyExistsException;
import my.standalonebank.domain.Account;

@ShellComponent
public class UserCommands {

    public static final Logger log = LoggerFactory.getLogger(UserCommands.class);

    @Autowired
    private UserCommandProvider userCommandProvider;

    @Autowired
    private UserService userService;

    @ShellMethodAvailability("isUserAdmin")
    @ShellMethod("Command to create a user")
    public String createUser(@ShellOption({"-u", "--username"}) String username) {

        log.info("working with create user");
        if (userCommandProvider.isUserPresent(username)) {
            throw new UserAlreadyExistsException();
        }

        Account account = userCommandProvider.promptUserInformation();
        account.setUsername(username);
        userService.saveUserAccount(account);

        return "User successfully created";
    }

    @ShellMethod("login to bank application")
    public void login() {
        Authentication authentication = userCommandProvider.login();
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("user logged in");
    }

    public Availability isUserAdmin() {
        log.info("isUserAdmin");
        Authentication authentication = SecurityContextHolder
                .getContext()
                .getAuthentication();

        log.debug("authentication: {}", authentication);
        boolean authenticated = false;
        if (authentication != null && authentication instanceof UsernamePasswordAuthenticationToken) {
            log.debug("authentication.getName(): {}", authentication.getName());
            authenticated = authentication.getAuthorities()
                    .stream()
                    .map(authority -> authority.getAuthority())
                    .collect(Collectors.toList())
                    .contains("ROLE_ADMIN");
        }

        log.debug("authenticated: {}", authenticated);

        if (authenticated) {
            return Availability.available();
        }

        return Availability.unavailable("Only admin user can create new users");
    }
}
