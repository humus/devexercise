package my.standalonebank.model;

import java.util.Set;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name="bank_users")
public class BankUser implements Serializable {

    private static final long serialVersionUID = 4727330211750403668L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @OneToMany(mappedBy = "bankUser")
    private Set<BankAccount> accounts;

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

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

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }



    /**
     * @return the accounts
     */
    public Set<BankAccount> getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(Set<BankAccount> accounts) {
        this.accounts = accounts;
    }

    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        BankUser other = (BankUser)o;
        return new EqualsBuilder()
                .append(this.id, other.id)
                .append(this.username, other.username)
                .append(this.password, other.password)
                .isEquals();
    }

    public int hashCode() {
        return new HashCodeBuilder(7, 3)
                .append(id)
                .append(username)
                .append(password)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("password", password)
                .toString();
    }

}
