package my.standalonebank.shell.commands;

import java.math.BigDecimal;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import my.standalonebank.model.BankAccount;
import my.standalonebank.shell.commands.prompt.PromptComponent;
import my.standalonebank.shell.exception.ShellException;
import my.standalonebank.shell.services.AccountService;

@Component
public class AccountCommandsProviderImpl implements AccountCommandsProvider {

    private static final Logger log = LoggerFactory.getLogger(AccountCommandsProviderImpl.class);

    private static final Pattern DIGITS = Pattern.compile("\\d{10}");

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PromptComponent promptComponent;

    @Override
    public void deposit(String account) {
        if (DIGITS.matcher(account).matches()) {
            processDeposit(account);
        }
    }

    @Override
    public void withdraw(String account) {
        if (DIGITS.matcher(account).matches()) {
            processWithdraw(account);
        }
    }

    private String processWithdraw(String account) {
        log.debug("processing withdraw from account: {}", account);

        BigDecimal withdrawAmount = promptAmount("Type amount to withdraw");
        log.info("withdrawAmount: {}", withdrawAmount);

        BankAccount bankAccount = accountService.getAccount(account);
        BigDecimal balance = bankAccount.getBalance();

        if (balance.compareTo(withdrawAmount) < 0) {
            throw new ShellException("Withdraw Ammount is higher than current balance");
        }

        promptPIN(bankAccount);

        accountService.withdraw(bankAccount, withdrawAmount);
        return "Successfully withdrawn";
    }

    private String processDeposit(String account) {
        log.debug("processing deposit to account: {}");

        BigDecimal depositAmount = promptAmount("Type amount to deposit");
        BankAccount bankAccount = accountService.getAccount(account);

        accountService.deposit(bankAccount, depositAmount);
        return "Successfuly deposited";
    }

    private void promptPIN(BankAccount bankAccount) {
        String typedNip = promptComponent.promptPassword("Type your nip before continue");
        boolean response = encoder.matches(typedNip, bankAccount.getPin());
        int counter = 0;
        while (!response) {
            promptComponent.println("Wrong nip entered");
            promptComponent.println("Type nip before continue");
            typedNip = promptComponent.promptPassword("Type your nip before continue");
            response = encoder.matches(typedNip, bankAccount.getPin());
            counter++;
            if (counter >= 3) {
                throw new ShellException("You have entered wrong nip too many times, exiting");
            }
        }
    }

    private BigDecimal promptAmount(String message) {
        log.info("starting prompt amount");
        String amount = "";
        int counter = 0;
        amount = promptComponent.promptText(message);
        while (!isValidAmount(amount)) {
            log.info("isValidAmount(amount)? {}", isValidAmount(amount));
            amount = promptComponent.promptText(message);
            log.debug("amount {}", amount);
            counter++;
            if (counter >= 2) {
                throw new ShellException("Amount should be valid positive number");
            }
            log.debug("isValidAmount(amount) {}", isValidAmount(amount));
        }
        return new BigDecimal(amount);
    }

    private boolean isValidAmount(String amountStr) {
        return StringUtils.isNumeric(amountStr)
                && !StringUtils.startsWith(amountStr, "0")
                && !StringUtils.startsWith(amountStr, "-");
    }

}
