package my.standalonebank.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;

import my.standalonebank.verifier.users.DatabaseUsers;

@Component
public class InitializeBean {

    public static final Logger log = LoggerFactory.getLogger(InitializeBean.class);

    private static final String ADMIN_USER = "admin";
    private static final String SAMPLE_USER = "scott";

    @Autowired
    private DatabaseUsers users;

    @Value("${scott.password}")
    private String scottPassword;

    @Value("${admin.password}")
    private String adminPassword;

    public void initializeData() {
        log.info("saving user admin");
        users.prepareUser(ADMIN_USER, adminPassword);
        users.prepareUser("scott", scottPassword);
        users.prepareAccount("scott");
    }

}
