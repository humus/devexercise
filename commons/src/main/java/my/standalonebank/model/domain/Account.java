package my.standalonebank.model.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class Account implements Serializable {

    private static final long serialVersionUID = -5422014521570944700L;

    private String firstName;
    private String lastName;
    private String identifier;
    private String accountNumber;
    private String encodedPassword;
    private BigDecimal balance;
    private String pin;
    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }
    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }
    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    /**
     * @return the identifier
     */
    public String getIdentifier() {
        return identifier;
    }
    /**
     * @param identifier the identifier to set
     */
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }
    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
    /**
     * @return the encodedPassword
     */
    public String getEncodedPassword() {
        return encodedPassword;
    }
    /**
     * @param encodedPassword the encodedPassword to set
     */
    public void setEncodedPassword(String encodedPassword) {
        this.encodedPassword = encodedPassword;
    }
    /**
     * @return the balance
     */
    public BigDecimal getBalance() {
        return balance;
    }
    /**
     * @param balance the balance to set
     */
    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    /**
     * @return the pin
     */
    public String getPin() {
        return pin;
    }
    /**
     * @param pin the pin to set
     */
    public void setPin(String pin) {
        this.pin = pin;
    }
    @Override
    public boolean equals(Object o) {
        if (o == null) { return false; }
        if (o == this) { return true; }
        if (this.getClass() != o.getClass()) { return false; }
        Account other = (Account)o;
        return new EqualsBuilder()
                .append(this.accountNumber, other.accountNumber)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(59, 13)
                .append(accountNumber)
                .hashCode();
    }

    public String toString() {
        return new ToStringBuilder(this)
                .append("firstName", firstName)
                .append("lastName", lastName)
                .append("pin", pin)
                .append("identifier", identifier)
                .append("accountNumber", accountNumber)
                .append("balance", balance)
                .toString();
    }

}
