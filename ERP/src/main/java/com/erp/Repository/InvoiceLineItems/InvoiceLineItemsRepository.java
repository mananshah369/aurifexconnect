package com.erp.Repository.InvoiceLineItems;

import com.erp.Model.InvoiceLineItems;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InvoiceLineItemsRepository extends JpaRepository<InvoiceLineItems,Long> {

    List<InvoiceLineItems> findByInvoice_invoiceId(Long invoiceId);
}
