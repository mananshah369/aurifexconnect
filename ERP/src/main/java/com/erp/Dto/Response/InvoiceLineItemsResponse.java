package com.erp.Dto.Response;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class InvoiceLineItemsResponse {

    private long invoiceLineItemId;

    private LocalDate date;

    private String itemName;

    private long unitPrice;

    private double quantity;

    private long totalPrice;

    private InventoryResponse inventory;

}
