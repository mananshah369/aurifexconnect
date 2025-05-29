package com.erp.Exception.Voucher;

public class VoucherNotFound extends RuntimeException {

    private final String message;

    public VoucherNotFound(String message) {
        this.message = message;
    }
}
