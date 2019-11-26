package my.standalonebank.shell.commands;

import my.standalonebank.model.BankUser;


public interface UserCommandProvider {

    boolean isUserPresent(String username);

    BankUser promptUserInformation();

}
