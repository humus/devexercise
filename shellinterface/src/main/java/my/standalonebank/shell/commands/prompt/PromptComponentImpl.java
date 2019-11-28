package my.standalonebank.shell.commands.prompt;

import java.io.PrintWriter;

import org.apache.commons.lang3.StringUtils;
import org.jline.reader.LineReader;
import org.jline.terminal.Terminal;
import org.jline.utils.AttributedStringBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import my.standalonebank.exception.ManyUserInputAttemptsException;

public class PromptComponentImpl implements PromptComponent {

    public static final Logger log = LoggerFactory.getLogger(PromptComponentImpl.class);

    private static final int MAX_TRIES = 3;
    private static final Character MASK = '*';

    private LineReader lineReader;
    private Terminal terminal;

    public PromptComponentImpl(LineReader lineReader, Terminal terminal) {
        this.lineReader = lineReader;
        this.terminal = terminal;
    }

    @Override
    public String promptPassword(String message) {
        log.debug("getting password for message: {}", message);
        String answer = null;
        int counter = 0;
        while (StringUtils.isEmpty(answer)) {
            if (counter >= 3) {
                throw new ManyUserInputAttemptsException();
            }
            counter++;

            answer = lineReader.readLine(String.format("%s: ", message), MASK);
        }
        return answer;
    }

    @Override
    public String promptText(String message) {
        log.info("getting text for message: {}", message);
        String answer = null;
        int counter = 0;
        while (StringUtils.isEmpty(answer)) {

            if (counter >= 3) {
                throw new ManyUserInputAttemptsException();
            }
            counter++;

            answer = lineReader.readLine(String.format("%s: ", message));
        }
        return answer;
    }

    @Override
    public String promptPasswordConfirm(String message, String confirmMessage) {
        log.info("promptPasswordConfirm message: {}", message);
        log.info("promptPasswordConfirm confirmMessage: {}", confirmMessage);
        log.debug("lineReader: {}", lineReader);

        String password = null;
        String passwordConfirm = null;

        int counter = 0;
        while (true) {

            if (counter >= MAX_TRIES) {
                throw new ManyUserInputAttemptsException();
            }

            log.info("before asking for password");
            password = lineReader.readLine(String.format("%s: ", message), MASK);
            log.info("after asking for password");
            passwordConfirm = lineReader.readLine(
                    String.format("%s: ", confirmMessage),
                    MASK);

            if (isOkPasswordConfirm(password, passwordConfirm)) {
                break;
            }
            println("Input and confirmation does not match");
            counter++;
        }

        return password;
    }

    @Override
    public void println(String message) {
        AttributedStringBuilder builder = new AttributedStringBuilder().append(message);
        PrintWriter out = terminal.writer();
        out.println(builder);
    }

    private boolean isOkPasswordConfirm(String password, String passwordConfirm) {
        return !StringUtils.isEmpty(passwordConfirm) && password.equals(passwordConfirm);
    }


}
