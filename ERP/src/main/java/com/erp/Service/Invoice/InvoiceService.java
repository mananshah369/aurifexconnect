package com.erp.Service.Invoice;

import com.erp.Model.InvoiceGenerator;

public interface InvoiceService {

    InvoiceGenerator createInvoice(long masterId);

    InvoiceGenerator fetchInvoice(long masterId);


}
