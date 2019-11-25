package my.standalonebank.shell.commands;

import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@ShellComponent
public class EchoCommand {

    @ShellMethod("simple echo command")
    public String echo(@ShellOption({"-n", "--name"}) String name) {
        return String.format("Hello %s!", name);
    }

}
