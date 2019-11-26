package my.standalonebank.domain;

import java.io.Serializable;

public class Accounts implements Serializable {

    private static final long serialVersionUID = -2641656879004628446L;

    private String username;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
    }
}
