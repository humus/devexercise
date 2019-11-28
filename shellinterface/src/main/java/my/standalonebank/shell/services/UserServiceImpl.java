package my.standalonebank.shell.services;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import my.standalonebank.domain.Account;
import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.AccountRepository;
import my.standalonebank.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    public static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public BankUser saveUserAccount(Account account) {
        log.debug("saving user");
        BankUser bankUser = new BankUser();
        bankUser.setUsername(account.getUsername());
        bankUser.setPassword(account.getPassword());
        bankUser = userRepository.save(bankUser);

        log.debug("saving account");
        BankAccount bankAccount = new BankAccount();
        bankAccount.setPin(encoder.encode(account.getPin()));
        bankAccount.setBankUser(bankUser);
        bankAccount.setDocId(account.getIdentifier());
        bankAccount.setFirstName(account.getFirstName());
        bankAccount.setLastName(account.getLastName());
        bankAccount.setBalance(BigDecimal.ZERO);

        log.info("bank and account saved to database");
        accountRepository.save(bankAccount);

        return bankUser;
    }

}
