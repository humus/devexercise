package my.standalonebank.shell;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ShellinterfaceApplication {

    private static final Logger log = LoggerFactory.getLogger(ShellinterfaceApplication.class);

    public static void main(String... args) {
        log.info("starting shell application");
        SpringApplication.run(ShellinterfaceApplication.class, args);
    }

}


