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
    
}
