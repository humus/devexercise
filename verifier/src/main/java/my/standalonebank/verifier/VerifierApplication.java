package my.standalonebank.verifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class VerifierApplication implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(VerifierApplication.class);

    @Autowired
    private InitializeBean initializeBean;

    public static void main(String[] args) {
        log.info("starting verifier application");
        SpringApplication.run(VerifierApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        initializeBean.initializeData();
    }

}
