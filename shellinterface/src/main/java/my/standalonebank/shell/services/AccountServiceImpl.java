package my.standalonebank.shell.services;

import java.math.BigDecimal;

import java.time.LocalDateTime;

import my.standalonebank.shell.util.SecurityUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankTransaction;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.AccountRepository;
import my.standalonebank.repository.TransactionRepository;

@Service
public class AccountServiceImpl implements AccountService {

    private static final Logger log = LoggerFactory.getLogger(AccountServiceImpl.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private SecurityUtil securityUtil;

    @Override
    @Transactional
    public void deposit(BankAccount account, BigDecimal amount) {
        String accountNumber = account.getAccountNumber();
        BankAccount repoAccount = getAccount(accountNumber);
        log.debug("deposit to account: {}", accountNumber);
        BigDecimal balance = repoAccount.getBalance().add(amount);

        repoAccount.setBalance(balance);
        saveTransaction("deposit", amount, repoAccount);

        accountRepository.save(repoAccount);
    }

    @Override
    public void withdraw(BankAccount account, BigDecimal amount) {
        String accountNumber = account.getAccountNumber();
        BankAccount repoAccount = getAccount(accountNumber);
        log.debug("withdraw from account: {}", accountNumber);
        BigDecimal balance = repoAccount.getBalance().subtract(amount);
        repoAccount.setBalance(balance);

        saveTransaction("withdraw", amount, repoAccount);
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

    private void saveTransaction(String description, BigDecimal amount, BankAccount bankAccount) {

        BankUser bankUser = securityUtil.getCurrentUser();
        log.debug("transaction for user id: {}", bankUser.getId());
        BankTransaction bankTransaction = new BankTransaction();
        bankTransaction.setBankAccount(bankAccount);
        bankTransaction.setDescription(description);
        bankTransaction.setAmount(amount);
        bankTransaction.setCreatedAt(LocalDateTime.now());
        bankTransaction.setBankUser(bankUser);

        transactionRepository.save(bankTransaction);
    }
}
