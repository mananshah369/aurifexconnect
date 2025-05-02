package com.erp.Service.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;

import java.util.List;

public interface InvoiceService {
    InvoiceResponse create(InvoiceRequest request, long ledgerId);

    InvoiceResponse findById(long invoiceId);

    List<InvoiceResponse> findByLedger_Name(String ledgerName);

    InvoiceResponse delete(long invoiceId);

    List<InvoiceResponse> findByAllInvoice();
}
