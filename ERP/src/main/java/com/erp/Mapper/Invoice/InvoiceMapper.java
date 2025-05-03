package com.erp.Mapper.Invoice;

import com.erp.Dto.Request.InvoiceRequest;
import com.erp.Dto.Response.InvoiceResponse;
import com.erp.Model.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {

    Invoice mapToInvoice(InvoiceRequest invoiceRequest);

    Invoice mapToInvoice(InvoiceRequest invoiceRequest,@MappingTarget Invoice invoice);

    InvoiceResponse mapToInvoiceResponse(Invoice invoice);

    List<InvoiceResponse> mapToInvoicesResponse(List<Invoice> invoices);
}
