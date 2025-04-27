package com.erp.Repository.Invoice;
import com.erp.Model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice,Long> {
    List<Invoice> findByCustomer_Name(String name);
}
