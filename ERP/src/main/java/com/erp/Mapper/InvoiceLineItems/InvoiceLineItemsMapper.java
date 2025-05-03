package com.erp.Mapper.InvoiceLineItems;

import com.erp.Dto.Response.InvoiceLineItemsResponse;
import com.erp.Model.InvoiceLineItems;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface InvoiceLineItemsMapper {

    InvoiceLineItemsResponse mapToLineItemsResponse(InvoiceLineItems invoiceLineItems);

    List<InvoiceLineItemsResponse> mapToLineItemsResponse(List<InvoiceLineItems> invoiceLineItems);
}
