package my.standalonebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.standalonebank.model.BankUser;

@Repository
public interface UserRepository extends JpaRepository<BankUser, Integer> {

    BankUser findByUsername(String username);

}
