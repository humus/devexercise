package my.standalonebank.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VerifierApplication {

    public static final Logger log = LoggerFactory.getLogger(VerifierApplication.class);

    public static void main(String[] args) {
        log.info("starting verifier application");
        SpringApplication.run(VerifierApplication.class, args);
    }

}
