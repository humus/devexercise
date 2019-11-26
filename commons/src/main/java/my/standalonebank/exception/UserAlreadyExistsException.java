package my.standalonebank.exception;

public class UserAlreadyExistsException extends RuntimeException {

    private static final long serialVersionUID = 5043808473977689111L;

    public UserAlreadyExistsException() {
        super("Username already registered in database, please use another username");
    }
}
