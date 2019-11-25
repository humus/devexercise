package my.standalonebank.verifier.users;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;

@Service
public class DatabaseUsers {

    public static final Logger log = LoggerFactory.getLogger(DatabaseUsers.class);

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

    private BankUser createUser(String username, String password) {
        BankUser bankUser = new BankUser();
        bankUser.setPassword(password);
        bankUser.setUsername(username);
        return bankUser;
    }


}
