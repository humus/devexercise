package my.standalonebank.shell.commands;

import my.standalonebank.shell.util.SecurityUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.shell.Availability;
import org.springframework.stereotype.Component;

@Component
public class BaseCommand {

    @Autowired
    private SecurityUtil securityUtil;

    public Availability userHasAccounts() {

        Authentication authentication = securityUtil.getAuthentication();

        if (authentication.isAuthenticated()) {
            return Availability.available();
        }

        return Availability.unavailable("User has no accounts");
    }
}
