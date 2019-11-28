package my.standalonebank.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bank_accounts")
public class BankAccount implements Serializable {

    private static final long serialVersionUID = 6996710188580304391L;

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="accounts_seq")
    private Integer id;

    @Column(name="account_number", insertable=false, updatable=false)
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private BankUser bankUser;

    @Column
    private BigDecimal balance;

    @Column
    private String pin;

    @Column(name="doc_id")
    private String docId;

    @Column(name="first_name")
    private String firstName;

    @Column(name="last_name")
    private String lastName;

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
     * @return the bankUser
     */
    public BankUser getBankUser() {
        return bankUser;
    }

    /**
     * @param bankUser the bankUser to set
     */
    public void setBankUser(BankUser bankUser) {
        this.bankUser = bankUser;
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

    /**
     * @return the docId
     */
    public String getDocId() {
        return docId;
    }

    /**
     * @param docId the docId to set
     */
    public void setDocId(String docId) {
        this.docId = docId;
    }

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

}
