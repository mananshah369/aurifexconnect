package com.erp.Exception.ReceiptException;

import lombok.Getter;

@Getter
public class ReceiptNotFound extends RuntimeException {

    private String message;
    public ReceiptNotFound(String message) {
        this.message = message;
    }
}
