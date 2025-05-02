package com.erp.Dto.Response;

import com.erp.Enum.InvoiceStatus;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class InvoiceResponse {

    private long invoiceId;

    private LocalDate invoiceDate;

    private String description;

    private long totalAmount;

    private InvoiceStatus invoiceStatus;

}
