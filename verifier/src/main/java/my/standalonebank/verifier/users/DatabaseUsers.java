package my.standalonebank.verifier.users;

import my.standalonebank.verifier.repository.UserRepository;

public class DatabaseUsers {

    private UserRepository userRepository;

    public void prepareUser(String username) {
        userRepository.findByUserName(username);
    }
    
}
