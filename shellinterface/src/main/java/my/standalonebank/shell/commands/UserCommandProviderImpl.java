package my.standalonebank.shell.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import my.standalonebank.model.BankUser;
import my.standalonebank.model.domain.Account;
import my.standalonebank.repository.UserRepository;
import my.standalonebank.shell.commands.prompt.PromptComponent;

@Service
public class UserCommandProviderImpl implements UserCommandProvider {

    public static final Logger log = LoggerFactory.getLogger(UserCommandProviderImpl.class);

    @Autowired
    private UserRepository repository;

    @Autowired
    private PromptComponent promptComponent;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Override
    public boolean isUserPresent(String username) {
        BankUser bankUser = repository.findByUsername(username);
        return bankUser != null;
    }

    @Override
    public BankUser promptUserInformation() {
        log.debug("asking for user information");
        BankUser bankUser = new BankUser();
        bankUser.setPassword(promptComponent.promptPasswordConfirm("Type User Password",
                    "Confirm User Password"));

        log.info("bank user password is: {}", bankUser.getPassword());

        Account account = new Account();
        account.setEncodedPassword(encoder.encode(bankUser.getPassword()));
        account.setFirstName(promptComponent.promptText("Type user first name"));
        account.setLastName(promptComponent.promptText("Type user last name"));
        account.setPin(promptComponent.promptPasswordConfirm("Type pin",
                    "Confirm pin"));
        account.setIdentifier(promptComponent.promptText("Type SSN/Voter Card Id"));

        log.debug("ok, user information collected");
        return bankUser;
    }
}
