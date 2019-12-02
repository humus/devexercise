package my.standalonebank.shell.commands;

public interface AccountCommandsProvider {
    void deposit(String account);

    void withdraw(String account);
}
