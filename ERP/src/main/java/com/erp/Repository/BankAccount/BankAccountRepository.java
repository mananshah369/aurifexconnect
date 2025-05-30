package com.erp.Repository.BankAccount;

import com.erp.Model.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount, Long>{
    Optional<BankAccount> findByAccountNumber(String accountNumber);
}
