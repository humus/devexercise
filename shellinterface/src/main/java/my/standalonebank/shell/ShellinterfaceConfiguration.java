package my.standalonebank.shell;

import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import my.standalonebank.shell.commands.prompt.PromptComponent;
import my.standalonebank.shell.commands.prompt.PromptComponentImpl;

@Configuration
@EnableJpaRepositories("my.standalonebank.repository")
@EnableTransactionManagement
@EntityScan("my.standalonebank.model")
public class ShellinterfaceConfiguration {

    @Bean
    public PromptComponent inputReader(@Lazy LineReader lineReader, @Lazy Terminal terminal) {
        return new PromptComponentImpl(lineReader, terminal);
    }

}
