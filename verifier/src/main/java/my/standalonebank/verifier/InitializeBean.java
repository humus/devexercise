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
@Configuration
@EntityScan("my.standalonebank.model")
@EnableJpaRepositories("my.standalonebank.repository")
public class InitializeBean {

    public static final Logger log = LoggerFactory.getLogger(InitializeBean.class);

    @Autowired
    private DatabaseUsers users;

    @Value("${scott.password}")
    private String scottPassword;

    @Value("${admin.password}")
    private String adminPassword;

    public void initializeData() {
        log.info("saving user admin");
        users.prepareUser("admin", adminPassword);
        users.prepareUser("scott", scottPassword);
    }

}
