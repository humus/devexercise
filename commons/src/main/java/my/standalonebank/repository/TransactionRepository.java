package my.standalonebank.repository;

import my.standalonebank.model.BankTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<BankTransaction, Integer> {
}
