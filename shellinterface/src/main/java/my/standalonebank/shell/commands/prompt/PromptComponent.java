package my.standalonebank.shell.commands.prompt;

import org.springframework.stereotype.Component;

@Component
public interface PromptComponent {

    String promptPassword(String message);

    String promptText(String message);

    String promptPasswordConfirm(String message, String confirmMessage);

    void println(String message);
}
