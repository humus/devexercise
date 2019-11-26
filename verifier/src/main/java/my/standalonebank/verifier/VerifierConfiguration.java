package my.standalonebank.verifier;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@Configuration
@EntityScan("my.standalonebank.model")
@EnableJpaRepositories("my.standalonebank.repository")
public class VerifierConfiguration {
}
