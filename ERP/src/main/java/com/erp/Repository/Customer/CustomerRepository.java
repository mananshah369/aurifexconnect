package com.erp.Repository.Customer;

import com.erp.Model.Ledger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Ledger, Long>{

}
