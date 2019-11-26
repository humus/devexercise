package my.standalonebank.exception;

public class ManyUserInputAttemptsException extends RuntimeException {

    private static final long serialVersionUID = -7518621914205453430L;

    public ManyUserInputAttemptsException() {
        super("User has provided too many bad inputs");
    }
}
