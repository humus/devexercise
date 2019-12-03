package my.standalonebank.shell.util;

import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtil {

    @Autowired
    private UserRepository userRepository;

    public Authentication getAuthentication() {
        return SecurityContextHolder
                .getContext()
                .getAuthentication();
    }

    public String getUserName() {
        Authentication authentication = getAuthentication();
        return authentication.getName();
    }

    public BankUser getCurrentUser() {
        return userRepository.findByUsername(getUserName());
    }


}
