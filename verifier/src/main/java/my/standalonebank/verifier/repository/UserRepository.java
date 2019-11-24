package my.standalonebank.verifier.repository;

import my.standalonebank.verifier.model.BankUser;

public interface UserRepository {

    BankUser findByUserName(String username);
    
}
