package my.standalonebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import my.standalonebank.model.BankAccount;

@Repository
public interface AccountRepository extends JpaRepository<BankAccount, Integer> {
}
