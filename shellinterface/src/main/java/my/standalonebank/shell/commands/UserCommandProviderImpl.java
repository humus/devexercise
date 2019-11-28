package my.standalonebank.shell.commands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import my.standalonebank.model.BankUser;
import my.standalonebank.domain.Account;
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

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public boolean isUserPresent(String username) {
        BankUser bankUser = repository.findByUsername(username);
        return bankUser != null;
    }

    @Override
    public Account promptUserInformation() {
        log.debug("asking for user information");
        String confirmedPassword = promptComponent.promptPasswordConfirm("Type User Password",
                    "Confirm User Password");
        Account account = new Account();

        account.setPassword(encoder.encode(confirmedPassword));
        account.setFirstName(promptComponent.promptText("Type user first name"));
        account.setLastName(promptComponent.promptText("Type user last name"));
        account.setPin(promptComponent.promptPasswordConfirm("Type pin",
                    "Confirm pin"));
        account.setIdentifier(promptComponent.promptText("Type SSN/Voter Card Id"));

        log.debug("ok, user information collected");
        return account;
    }

    @Override
    public Authentication login() {
        String username = promptComponent.promptText("username: ");
        String password = promptComponent.promptPassword("password: ");

        Authentication request = new UsernamePasswordAuthenticationToken(username, password);

        Authentication result = authenticationManager.authenticate(request);
        promptComponent.println("OK, user is logged in");
        return result;
    }
}
