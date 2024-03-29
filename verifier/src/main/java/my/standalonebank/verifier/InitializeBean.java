package my.standalonebank.verifier;

import my.standalonebank.model.BankAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import my.standalonebank.verifier.users.DatabaseUsers;

@Component
public class InitializeBean {

    private static final Logger log = LoggerFactory.getLogger(InitializeBean.class);

    private static final String ADMIN_USER = "admin";
    private static final String SCOTT = "scott";

    @Autowired
    private DatabaseUsers users;

    @Value("${scott.password}")
    private String scottPassword;

    @Value("${admin.password}")
    private String adminPassword;

    @Value("${scott.pin}")
    private String scottPin;

    @Value("${scott.doc_id}")
    private String docId;

    @Value("${scott.last_name}")
    private String scottLastName;

    public void initializeData() {
        log.info("saving user admin");
        users.prepareUser(ADMIN_USER, adminPassword);
        users.prepareUser(SCOTT, scottPassword);

        BankAccount scottAccount = new BankAccount();
        scottAccount.setFirstName(SCOTT);
        scottAccount.setLastName(scottLastName);
        scottAccount.setPin(scottPin);
        scottAccount.setDocId(docId);

        users.prepareAccount(SCOTT, scottAccount);
    }

}
