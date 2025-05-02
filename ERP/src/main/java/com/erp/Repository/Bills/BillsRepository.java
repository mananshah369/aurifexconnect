package com.erp.Repository.Bills;

import com.erp.Model.Bills;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BillsRepository extends JpaRepository<Bills,Long> {

    List<Bills> findByLedger_Name(String supplierName);
}
