package my.standalonebank.shell.commands;

import org.springframework.security.core.Authentication;

import my.standalonebank.domain.Account;


public interface UserCommandProvider {

    boolean isUserPresent(String username);

    Account promptUserInformation();

    Authentication login();

}
