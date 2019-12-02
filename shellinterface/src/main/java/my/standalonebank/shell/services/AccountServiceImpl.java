package my.standalonebank.shell.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.standalonebank.model.BankAccount;
import my.standalonebank.repository.AccountRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Override
    @Transactional
    public void deposit(BankAccount account, BigDecimal amount) {
        String accountNumber = account.getAccountNumber();
        BankAccount repoAccount = getAccount(accountNumber);
        log.debug("deposit to account: {}", accountNumber);
        BigDecimal balance = repoAccount.getBalance().add(amount);

        repoAccount.setBalance(balance);

        accountRepository.save(repoAccount);
    }

    @Override
    public void withdraw(BankAccount account, BigDecimal amount) {
        String accountNumber = account.getAccountNumber();
        BankAccount repoAccount = getAccount(accountNumber);
        log.debug("withdraw from account: {}", accountNumber);
        BigDecimal balance = repoAccount.getBalance().subtract(amount);
        repoAccount.setBalance(balance);

        accountRepository.save(repoAccount);
    }

    @Override
    public BankAccount getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    @Override
    @Transactional
    public boolean accountExists(String accountNumber) {
        log.debug("checking existence of account: {}", accountNumber);
        return getAccount(accountNumber) != null;
    }
}
