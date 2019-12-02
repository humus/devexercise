package my.standalonebank.shell.services;

import java.math.BigDecimal;

import my.standalonebank.model.BankAccount;

public interface AccountService {

    void deposit(BankAccount account, BigDecimal amount);

    void withdraw(BankAccount account, BigDecimal amount);

    BankAccount getAccount(String accountNumber);

    boolean accountExists(String accountNumber);

}
