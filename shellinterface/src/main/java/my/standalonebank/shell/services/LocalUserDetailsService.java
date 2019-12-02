package my.standalonebank.shell.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import my.standalonebank.model.BankUser;
import my.standalonebank.repository.UserRepository;

public class LocalUserDetailsService implements UserDetailsService {

    private static final Logger log = LoggerFactory.getLogger(LocalUserDetailsService.class);

    private UserRepository userRepository;

    public LocalUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BankUser user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(String.format("user %s not found", username));
        }

        log.debug("user.getPassword(): {}", user.getPassword());

        String[] roles = new String[]{"USER"};

        if ("admin".equals(username)) {
            roles = new String[]{"ADMIN", "USER"};
        }

         return User.withUsername(username)
                .password(user.getPassword())
                .roles(roles)
                .build();
    }

}
