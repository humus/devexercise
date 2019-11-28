package my.standalonebank.shell.services;

import my.standalonebank.domain.Account;
import my.standalonebank.model.BankUser;

public interface UserService {

    BankUser saveUserAccount(Account account);

}
