package my.standalonebank.verifier.users;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.standalonebank.model.BankAccount;
import my.standalonebank.model.BankUser;
import my.standalonebank.repository.AccountRepository;
import my.standalonebank.repository.UserRepository;

@Service
public class DatabaseUsers {

    public static final Logger log = LoggerFactory.getLogger(DatabaseUsers.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void prepareUser(String username, String password) {
        BankUser user = userRepository.findByUsername(username);

        if (user == null) {
            log.info("user {} not found, creating", username);
            userRepository.save(createUser(username, password));
            log.info("after user creation");
        }

        log.info("Finishing user preparation");
    }

    @Transactional
    public void prepareAccount(String username) {
        BankUser user = userRepository.findByUsername(username);

        Set<BankAccount> accounts = user.getAccounts();

        if (empty(accounts)) {
            addAccount(user);
        }
    }

    private <K> boolean empty(Set<K> aSet) {
        if (aSet == null) {
            return true;
        }
        return aSet.isEmpty();
    }

    private void addAccount(BankUser user) {
        if (user.getAccounts() == null) {
            user.setAccounts(new HashSet<BankAccount>());
        }
        Set<BankAccount> accounts = user.getAccounts();
        BankAccount account = new BankAccount();
        account.setBankUser(user);
        accounts.add(account);
        account.setBalance(new BigDecimal(0d));

        accountRepository.save(account);
    }

    private BankUser createUser(String username, String password) {
        BankUser bankUser = new BankUser();
        bankUser.setPassword(password);
        bankUser.setUsername(username);
        return bankUser;
    }


}
