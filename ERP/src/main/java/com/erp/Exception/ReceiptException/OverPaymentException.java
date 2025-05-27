package com.erp.Exception.ReceiptException;

import lombok.Getter;

@Getter
public class OverPaymentException extends RuntimeException {
    private String message;
    public OverPaymentException(String message) {
        this.message = message;
    }
}
