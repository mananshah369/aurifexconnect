package com.erp.Service.InvoiceLineItemService;

import com.erp.Dto.Response.InvoiceLineItemsResponse;
import com.erp.Model.InvoiceLineItems;

import java.util.List;

public interface InvoiceLineItemService {
    InvoiceLineItemsResponse createInvoiceLineItems(long inventoryId, long invoiceId,long customerId , double quantity);

    List<InvoiceLineItemsResponse> findById(long invoiceId);

    InvoiceLineItemsResponse updateInvoiceItems(long inventoryId, long invoiceId, double quantity);

    InvoiceLineItemsResponse deleteInvoiceItems(long invoiceId,long inventoryId);
}
