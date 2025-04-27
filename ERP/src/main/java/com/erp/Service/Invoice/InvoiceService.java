package com.erp.Service.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse create(InvoiceRequest request, long customerId);

    InvoiceResponse findById(long invoiceId);

    List<InvoiceResponse> findByCustomer_Name(String customerName);

    InvoiceResponse delete(long invoiceId);

    List<InvoiceResponse> findByAllInvoice();
}
