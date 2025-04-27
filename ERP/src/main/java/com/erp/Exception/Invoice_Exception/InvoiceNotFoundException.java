package com.erp.Exception.Invoice_Exception;

import lombok.Getter;

@Getter
public class InvoiceNotFoundException extends RuntimeException {

  private final String message;

    public InvoiceNotFoundException(String message) {
        this.message = message;
    }
}
